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
    <script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#testJson").click(function(){
                var url = this.href;
                var args = {};
                $.post(url,args,function(data){
                    for(var i=0;i<data.length;i++){
                        var id = data[i].id;
                        var lastName = data[i].lastName;

                        alert(id + "：" + lastName);
                    }
                });
                return false;
            });
        });
    </script>
</head>
<body>
    <form action="/testFileUpload" method="post" enctype="multipart/form-data">
        File：<input type="file" name="file" />
        Desc：<input type="text" name="desc" />
        <input type="submit" value="Submit" />
    </form>
    <br/>

    <a href="/emps">List All Employees</a>
    <br/><br/>
    <a href="/testJson" id="testJson">Test Json</a>
    <br/><br/>
    <form action="/testHttpMessageConverter" method="post" enctype="multipart/form-data">
        File：<input type="file" name="file" />
        Desc：<input type="text" name="desc" />
        <input type="submit" value="Submit" />
    </form>

    <a href="/testResponseEntity">testResponseEntity</a>
    <br/><br/>

    <!--
        关于国际化：
        1.在页面上能够根据浏览器语言设置的情况对文本（不是内容），时间，数值进行本地化处理
        2.可以在bean中获取国际化资源文件Locale 对应的消息
        3.可以通过超链接切换Locale，而不再依赖于浏览器的语言设置情况

        解决：
        1.使用JSTL 的 fmt 标签
        2.在bean中注入ResourceBundleMessageSource 的示例，使用其对应的 getMessage方法即可
        3.配置LocalResolver 和 LocaleChangeInterceptor
    -->
    <br/><br/>
    <a href="i18n">I18N PAGE</a>
    <br/><br/>
    <a href="/testExceptionHandlerExceptionResolver?i=10">Test ExceptionHandlerExceptionResolver</a>

    <br/><br/>
    <a href="/testResponseStatusExceptionResolver?i=10">Test ResponseStatusExceptionResolver</a>

    <br/><br/>
    <a href="/testDefaultHandlerExceptionResolver?i=10">Test DefaultHandlerExceptionResolver</a>

    <br/><br/>
    <a href="/testSimpleMappingExceptionResolver?i=10">Test SimpleMappingExceptionResolver</a>
</body>
</html>
