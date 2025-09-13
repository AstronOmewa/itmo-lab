class NoMatchingIndiciesFoundException(Exception):
    def __init__(self, *args):
        super().__init__(*args)
        
class NumsNotListError(Exception):
    def __init__(self, *args):
        super().__init__(*args)
        
class NumsContainsNotIntError(Exception):
    def __init__(self, *args):
        super().__init__(*args)
        
class TargetIsNANError(Exception):
    def __init__(self, *args):
        super().__init__(*args)