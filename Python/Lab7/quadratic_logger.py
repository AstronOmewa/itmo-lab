import sys, logging
from functools import wraps

def quadratic_logger(func = None, *, handle = sys.stdout):
    if func is None:
        return lambda f: quadratic_logger(f, handle=handle)
    
    if isinstance(handle, logging.quadratic_logger):
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.info("Started logging using logger output\n")
            try:
                res = func(*args, **kwargs)
            except ValueError as e:
                handle.warning(e)
            except TypeError as e:
                handle.error(e)
                raise
            except ArithmeticError as e:
                handle.critical(e)
                raise
            handle.info(f"Result: {func(*args, **kwargs)}")
            handle.info("Finishing logging using logger output: success\n")
            return res
        return wrapper
    else:
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.write("Started logging using logger output\n")
            try:
                res = func(*args, **kwargs)
            except ValueError as e:
                handle.write(e)
            except TypeError as e:
                handle.write(e)
                raise
            except ArithmeticError as e:
                handle.write(e)
                raise
            handle.write(f"Result: {func(*args, **kwargs)}")
            handle.write("Finishing logging using logger output: success\n")
            return res
        return wrapper

