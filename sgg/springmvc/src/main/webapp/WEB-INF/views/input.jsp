<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>input</title>
</head>
<body>
    <form action="testConversionServiceConverter" method="post">
        <!--lastname-email-gender-department.id 例如：gg-gg@qq.com-0-105-->
        Employee：<input type="text" name="employee"/>
        <input type="submit" value="Submit"/>
    </form>

    <br/><br/>

    <!--modelAttribute代表请求域中的变量-->
    <form:form action="${pageContext.request.contextPath}/emp" method="post" modelAttribute="employee">
        <form:errors path="*"></form:errors>

        <br/>
        <c:if test="${employee.id == null}">
            LastName：<form:input path="lastName"/>
            <form:errors path="lastName" />
        </c:if>
        <c:if test="${employee.id != null}">
            <form:hidden path="id"/>
            <input type="hidden" name="_method" value="PUT"/>
        </c:if>
        <br/>
        Email:<form:input path="email"/>
        <form:errors path="email" />
        <br/>
        <%
            Map<String,String> genders = new HashMap<String,String>();
            genders.put("1","Male");
            genders.put("0","Female");
            request.setAttribute("genders",genders);
        %>
        Gender:<form:radiobuttons path="gender" items="${genders}"/>
        <br/>
        Department：<form:select path="department.id" items="${departments}" itemLabel="departmentName"
        itemValue="id"></form:select>
        <br/>
        Birth：<form:input path="birth" />
        <form:errors path="email" />
        <br/>
        Salary：<form:input path="salary" />
        <br/>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
