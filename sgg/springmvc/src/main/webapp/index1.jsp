<%--
  Created by IntelliJ IDEA.
  User: zhuwb
  Date: 2019/3/25
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
    <a href="/testRedirect">Test Redirect</a>
    <br/>
    <a href="/testView">Test View</a>

    <form action="testModelAttribute" method="post">
        <input type="hidden" name="id" value="2"/>
        username:<input type="text" name="username" value="Tom"/>
        <br/>
        email:<input type="text" name="email" value="aaa@bbb.com"/>
        <br/>
        age:<input type="text" name="age" value="12"/>
        <br/>
        <input type="submit" value="Submit"/>
    </form>

    <br/>
    <a href="/testSessionAttribute">testSessionAttribute</a>
    <br/>
    <a href="/testMap">testMap</a>
    <br/>
    <a href="/testModelAndView">testModelAndView</a>
    <br/>
    <a href="/testServletAPI">TestServlet API</a>
    <br/>
    <form action="/testPojo" method="post">
        username:<input type="text" name="username"/>
        <br/>
        password:<input type="password" name="password"/>
        <br/>
        email:<input type="text" name="email" />
        <br/>
        age:<input type="text" name="age" />
        <br/>
        city:<input type="text" name="address.city" />
        <br/>
        province:<input type="text" name="address.province" />
        <br/>
        <input type="submit" value="Submit" />
    </form>

    <br/><br/>

    <a href="/testCookieValue">/testCookieValue</a><br/>

    <a href="/testRequestHeader">/testRequestHeader</a><br/>

    <a href="/testRequestParam?username=zs&age=12">testRequestParam</a>

    <form action="/testRest" method="post">
        <input type="submit" value="TestRest POST"/>
    </form>
    <form action="/testRest/1" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="submit" value="TestRest DELETE"/>
    </form>
    <form action="/testRest/1" method="post">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="submit" value="TestRest PUT"/>
    </form>
    <br/>
        <a href="/testRest/1">TestRest GET</a>
    <br/>

    <a href="/testPathVariable/1">testPathVariable</a>
    <br/>
    <a href="/testAntPath/sdfsf/abc">testAntPath</a>
    <br/>
    <a href="helloworld">Hello World</a>
    <form action="/testMethod" method="post">
        <input type="submit"/>
    </form>

    <a href="/testParamsAndHeaders?username=atguigu&age=20">testParamsAndHeaders</a>
</body>
</html>
