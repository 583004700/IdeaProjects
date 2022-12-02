### 授权码模式
#### 1、先获取到code
http://localhost:9098/oauth/authorize?client_id=client_3&response_type=code&scope=server&redirect_uri=http://www.baidu.com
#### 2、通过code获取到 access_token
localhost:9098/oauth/token?grant_type=authorization_code&scope=server&client_id=client_3&client_secret=123456&code=XPZ5I0&redirect_uri=https://www.baidu.com
#### 3、验证token信息
http://localhost:9098/oauth/check_token

#### 生成证书
keytool -genkeypair -alias test-jwt -validity 3650 -keyalg RSA -dname "CN=jwt,OU=jtw,L=zurich,S=zurich,C=CH" -keypass test123 -keystore test-jwt.jks -storepass test123

keytool -list -rfc --keystore test-jwt.jks | openssl x509 -inform pem -pubkey

-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmQc7dM4bHlFcH7b89nzg
UIGp95qQKJB7tUXgeCFDVgjqy8TkQE1wy0ncSZ5JTG5kNsDZv9mVefCegizpsiDT
n/POme7O6Z6YQw3dc7NjGmOnU+dD8BoI38TPlt3MursG/uwBPQbJPwSHGDLbgBq9
tvvi+eNcOEq8d0IiR0lsM8bou5qy2bQQadzHOI0XZMyOz7kftjB/sKHteP/G8DLx
lz73+fE4ObeFyH9pu4ZjBYY0LVgKo44gyOGy6cVQEoU16tpbQbJNDveExqTvU+ZR
ZgTNq6Dzely64quBp/slahXVHF9Om3hyBBrZxx0bjqCuqZJdNfxiEnReqgP+TwiF
7wIDAQAB
-----END PUBLIC KEY-----
-----BEGIN CERTIFICATE-----
MIIDNTCCAh2gAwIBAgIEH/w18TANBgkqhkiG9w0BAQsFADBLMQswCQYDVQQGEwJD
SDEPMA0GA1UECBMGenVyaWNoMQ8wDQYDVQQHEwZ6dXJpY2gxDDAKBgNVBAsTA2p0
dzEMMAoGA1UEAxMDand0MB4XDTIyMTIwMjAzMjMzNVoXDTMyMTEyOTAzMjMzNVow
SzELMAkGA1UEBhMCQ0gxDzANBgNVBAgTBnp1cmljaDEPMA0GA1UEBxMGenVyaWNo
MQwwCgYDVQQLEwNqdHcxDDAKBgNVBAMTA2p3dDCCASIwDQYJKoZIhvcNAQEBBQAD
ggEPADCCAQoCggEBAJkHO3TOGx5RXB+2/PZ84FCBqfeakCiQe7VF4HghQ1YI6svE
5EBNcMtJ3EmeSUxuZDbA2b/ZlXnwnoIs6bIg05/zzpnuzumemEMN3XOzYxpjp1Pn
Q/AaCN/Ez5bdzLq7Bv7sAT0GyT8Ehxgy24Aavbb74vnjXDhKvHdCIkdJbDPG6Lua
stm0EGncxziNF2TMjs+5H7Ywf7Ch7Xj/xvAy8Zc+9/nxODm3hch/abuGYwWGNC1Y
CqOOIMjhsunFUBKFNeraW0GyTQ73hMak71PmUWYEzaug83pcuuKrgaf7JWoV1Rxf
Tpt4cgQa2ccdG46grqmSXTX8YhJ0XqoD/k8Ihe8CAwEAAaMhMB8wHQYDVR0OBBYE
FHWgWE16UyMZLfJiCFt48WKElGESMA0GCSqGSIb3DQEBCwUAA4IBAQAo43tPVp3i
5XviBEDesc+IwjsR7pbic6JixQMaYvbAxSK7ej4064ypVnuD0rxI1CO26QzUG1vw
Au2q4qE6E2CTTNlUJYNJhumtlF5Zr6i1L3Gvf8lnmaF21791QoHBFL40iivtxvOi
0l7D/k8VtFrXE2ZzsDIhXzQqk/FcXYnwc2rWtWv4ps7IGb4RjKOEdyqVffhOqZFA
80hoA98emoSPQ7/FQxyHSLhG+FkfJGIiEHu0+rzVVrNZPKv5ue7VxHb8OndYqRX9
0vSv/raGmKz6OK4zTy3K3payxFbovQM01l96EUZCmCHMoazqBH1hyDEDRuoctO+3
HJn98W1MHtbM
-----END CERTIFICATE-----