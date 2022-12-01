### 授权码模式
#### 1、先获取到code
http://localhost:9098/oauth/authorize?client_id=client_3&response_type=code&scope=server&redirect_uri=http://www.baidu.com
#### 2、通过code获取到 access_token
localhost:9098/oauth/token?grant_type=authorization_code&scope=server&client_id=client_3&client_secret=123456&code=XPZ5I0&redirect_uri=https://www.baidu.com
#### 3、验证token信息
http://localhost:9098/oauth/check_token