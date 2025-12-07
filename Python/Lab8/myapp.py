"""
Main application server for Lab8
HTTP server with MVC architecture for currency tracking application
"""
import os
import sys
from http.server import HTTPServer, BaseHTTPRequestHandler
from urllib.parse import urlparse, parse_qs
import json
import uuid
from typing import Dict, List, Any, Optional

# Add project root to path for imports
sys.path.append(os.path.dirname(os.path.abspath(__file__)))

from jinja2 import Environment, PackageLoader, select_autoescape
from models import Author, App, User, Currency, UserCurrency
from utils.currencies_api import get_currencies, get_currency_history


# Initialize Jinja2 environment
env = Environment(
    loader=PackageLoader("__main__"),
    autoescape=select_autoescape(['html', 'xml'])
)


class CurrencyRequestHandler(BaseHTTPRequestHandler):
    """
    HTTP request handler for the currency tracking application
    Implements routing and response handling
    """

    # Initialize global data stores (in production, use a database)
    users: Dict[str, User] = {}
    currencies: Dict[str, Currency] = {}
    author: Optional[Author] = None
    app: Optional[App] = None

    def __init__(self, *args, **kwargs):
        # Initialize author and app data
        if not CurrencyRequestHandler.author:
            CurrencyRequestHandler.author = Author("Зекин В.К.", "P3121")
            CurrencyRequestHandler.app = App("CurrenciesListApp", "1.0.0", self.author)
        super().__init__(*args, **kwargs)

    def do_GET(self) -> None:
        """Handle GET requests"""
        parsed_path = urlparse(self.path)
        path = parsed_path.path
        query_params = parse_qs(parsed_path.query)

        try:
            # Route handling
            if path == '/':
                self.handle_index()
            elif path == '/users':
                self.handle_users()
            elif path == '/user':
                self.handle_user_detail(query_params)
            elif path == '/currencies':
                self.handle_currencies()
            elif path == '/author':
                self.handle_author()
            elif path.startswith('/api/'):
                self.handle_api(path[5:], query_params)
            else:
                self.handle_404()

        except Exception as e:
            self.send_error(500, f"Internal Server Error: {str(e)}")

    def do_POST(self) -> None:
        """Handle POST requests"""
        parsed_path = urlparse(self.path)
        path = parsed_path.path

        try:
            if path == '/api/users':
                self.handle_create_user()
            elif path == '/api/subscriptions':
                self.handle_create_subscription()
            else:
                self.handle_404()

        except Exception as e:
            self.send_error(500, f"Internal Server Error: {str(e)}")

    def handle_index(self) -> None:
        """Handle index page"""
        template = env.get_template("index.html")
        html = template.render(
            app=self.app,
            navigation=[
                {'caption': 'Главная', 'href': '/'},
                {'caption': 'Пользователи', 'href': '/users'},
                {'caption': 'Валюты', 'href': '/currencies'},
                {'caption': 'Автор', 'href': '/author'}
            ]
        )
        self.send_html_response(html)

    def handle_users(self) -> None:
        """Handle users list page"""
        template = env.get_template("users.html")
        html = template.render(
            users=list(self.users.values()),
            navigation=self._get_navigation()
        )
        self.send_html_response(html)

    def handle_user_detail(self, query_params: Dict) -> None:
        """Handle individual user page"""
        user_id = query_params.get('id', [None])[0]
        if not user_id or user_id not in self.users:
            self.send_error(404, "User not found")
            return

        user = self.users[user_id]
        user_currencies = []
        for sub_id in user.subscriptions:
            if sub_id in self.currencies:
                currency = self.currencies[sub_id]
                subscription = user.get_subscription(sub_id)
                user_currencies.append({
                    'currency': currency,
                    'subscription': subscription
                })

        template = env.get_template("user_detail.html")
        html = template.render(
            user=user,
            user_currencies=user_currencies,
            navigation=self._get_navigation()
        )
        self.send_html_response(html)

    def handle_currencies(self) -> None:
        """Handle currencies list page"""
        try:
            # Fetch current currencies from API
            currencies_data = get_currencies()

            # Update or create currency objects
            for char_code, data in currencies_data.items():
                if char_code not in self.currencies:
                    currency = Currency(
                        num_code=data['num_code'],
                        char_code=data['char_code'],
                        name=data['name'],
                        value=data['value'],
                        nominal=data['nominal']
                    )
                    self.currencies[char_code] = currency
                else:
                    # Update existing currency value
                    self.currencies[char_code].value = data['value']

            template = env.get_template("currencies.html")
            html = template.render(
                currencies=list(self.currencies.values()),
                last_update=currencies_data.get('USD', {}).get('date'),
                navigation=self._get_navigation()
            )
            self.send_html_response(html)

        except Exception as e:
            # If API fails, show cached currencies
            template = env.get_template("currencies.html")
            html = template.render(
                currencies=list(self.currencies.values()),
                error=f"Не удалось обновить курсы: {str(e)}",
                navigation=self._get_navigation()
            )
            self.send_html_response(html)

    def handle_author(self) -> None:
        """Handle author page"""
        template = env.get_template("author.html")
        html = template.render(
            author=self.author,
            app=self.app,
            navigation=self._get_navigation()
        )
        self.send_html_response(html)

    def handle_api(self, endpoint: str, query_params: Dict) -> None:
        """Handle API endpoints"""
        if endpoint == 'users':
            users_data = [
                {
                    'id': user.id,
                    'name': user.name,
                    'subscriptions_count': len(user.subscriptions)
                }
                for user in self.users.values()
            ]
            self.send_json_response({'users': users_data})
        elif endpoint == 'currencies':
            try:
                currencies_data = get_currencies()
                self.send_json_response(currencies_data)
            except Exception as e:
                self.send_json_response({'error': str(e)}, status=500)
        else:
            self.handle_404()

    def handle_create_user(self) -> None:
        """Handle user creation via POST"""
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length)
        data = json.loads(post_data.decode('utf-8'))

        try:
            name = data.get('name', '').strip()
            if not name:
                raise ValueError("Name is required")

            user = User(name=name)
            self.users[user.id] = user

            self.send_json_response({
                'id': user.id,
                'name': user.name,
                'message': 'User created successfully'
            })

        except Exception as e:
            self.send_json_response({'error': str(e)}, status=400)

    def handle_create_subscription(self) -> None:
        """Handle subscription creation via POST"""
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length)
        data = json.loads(post_data.decode('utf-8'))

        try:
            user_id = data.get('user_id')
            currency_code = data.get('currency_code')

            if not user_id or user_id not in self.users:
                raise ValueError("Invalid user_id")
            if not currency_code:
                raise ValueError("currency_code is required")

            user = self.users[user_id]
            subscription = UserCurrency(
                user_id=user_id,
                currency_id=currency_code
            )
            user.add_subscription(subscription)

            self.send_json_response({
                'message': 'Subscription created successfully'
            })

        except Exception as e:
            self.send_json_response({'error': str(e)}, status=400)

    def handle_404(self) -> None:
        """Handle 404 Not Found"""
        self.send_error(404, "Page not found")

    def send_html_response(self, html: str, status: int = 200) -> None:
        """Send HTML response"""
        self.send_response(status)
        self.send_header('Content-type', 'text/html; charset=utf-8')
        self.end_headers()
        self.wfile.write(html.encode('utf-8'))

    def send_json_response(self, data: Any, status: int = 200) -> None:
        """Send JSON response"""
        json_data = json.dumps(data, ensure_ascii=False, indent=2)
        self.send_response(status)
        self.send_header('Content-type', 'application/json; charset=utf-8')
        self.end_headers()
        self.wfile.write(json_data.encode('utf-8'))

    def _get_navigation(self) -> List[Dict[str, str]]:
        """Get navigation menu items"""
        return [
            {'caption': 'Главная', 'href': '/'},
            {'caption': 'Пользователи', 'href': '/users'},
            {'caption': 'Валюты', 'href': '/currencies'},
            {'caption': 'Автор', 'href': '/author'}
        ]

    def log_message(self, format: str, *args) -> None:
        """Override log message to use proper encoding"""
        try:
            message = format % args
            print(f"{self.address_string()} - {message}")
        except UnicodeEncodeError:
            print(f"{self.address_string()} - Log message encoding error")


def run_server(host: str = 'localhost', port: int = 8000) -> None:
    """Run the HTTP server"""
    server_address = (host, port)
    httpd = HTTPServer(server_address, CurrencyRequestHandler)

    print(f"Сервер запущен на http://{host}:{port}")
    print("Нажмите Ctrl+C для остановки сервера")

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\nОстановка сервера...")
        httpd.server_close()


if __name__ == "__main__":
    run_server()