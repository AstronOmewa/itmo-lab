"""
Test script to verify server functionality
"""
import requests
import time
import subprocess
import sys
import os

def test_server():
    """Test the server endpoints"""
    base_url = "http://localhost:8000"

    print("Testing server endpoints...")

    try:
        # Test index page
        response = requests.get(f"{base_url}/")
        print(f"✓ Index page: {response.status_code}")

        # Test author page
        response = requests.get(f"{base_url}/author")
        print(f"✓ Author page: {response.status_code}")

        # Test users page
        response = requests.get(f"{base_url}/users")
        print(f"✓ Users page: {response.status_code}")

        # Test currencies page
        response = requests.get(f"{base_url}/currencies")
        print(f"✓ Currencies page: {response.status_code}")

        # Test API users
        response = requests.get(f"{base_url}/api/users")
        print(f"✓ API users: {response.status_code}")

        # Test API currencies
        response = requests.get(f"{base_url}/api/currencies")
        print(f"✓ API currencies: {response.status_code}")

        print("\n✅ All tests passed!")

    except requests.exceptions.ConnectionError:
        print("❌ Server is not running. Please start the server first:")
        print("   python myapp.py")
    except Exception as e:
        print(f"❌ Error: {e}")

if __name__ == "__main__":
    test_server()