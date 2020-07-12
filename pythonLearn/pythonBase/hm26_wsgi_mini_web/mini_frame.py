def login():
    return "这是登录页"


def index():
    return "这是主页"


def application(environ, start_response):
    start_response("200 OK", [("Content-Type", "text/html;charset=utf-8")])
    file_name = environ["PATH_INFO"]
    if file_name == "login.py":
        return login()
    elif file_name == "index.py":
        return index()
    return "Hello World,我爱你中国"
