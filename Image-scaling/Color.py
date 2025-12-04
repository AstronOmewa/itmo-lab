class Color:
    
    def __init__(self, r:int, g: int, b: int):
        self.r = min(max(r, 0), 255)
        self.g = min(max(g, 0), 255)
        self.b = min(max(b, 0), 255)
        
    def __add__(self, other: 'Color')->'Color':
        return Color(
            min(max(self.r + other.r,0), 255),
            min(max(self.g + other.g,0), 255),
            min(max(self.b + other.b,0), 255)
        )
    
    def __sub__(self, other: 'Color')->'Color':
        return Color(
            max(min(self.r - other.r,255), 0),
            max(min(self.g - other.g,255), 0),
            max(min(self.b - other.b,255), 0)
        )
    
    def to_string(self)->str:
        print(f"Color(r: {self.r}, g: {self.g}, b: {self.b})")
    
print((Color(10,20,30) - Color(255,255,255)).to_string())