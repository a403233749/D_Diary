<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>个人日记本系统</title>

    <!-- Bootstrap -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="./assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="./static/common/common.css" rel="stylesheet">
    <link href="./static/pages/index/index.css" rel="stylesheet">
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <%
        String userName = (String)request.getAttribute("userName");
        String password = (String)request.getAttribute("password");
        if(userName == null && password == null){
            Cookie[] cookies = request.getCookies();
            for(int i=0;cookies != null && i<cookies.length;i++){
                if("userNameAndPwd".equals(cookies[i].getName())){
                    request.setAttribute("rememberMe","remember-me");
                    request.setAttribute("userName",cookies[i].getValue().split("-")[0]);
                    request.setAttribute("password",cookies[i].getValue().split("-")[1]);
                }
            }
        }
    %>
    <div class="container">
        <div class="signin_bg" style="background-image: url('./static/images/bg_signin.jpg')"></div>
        <form action="login" class="form-signin" method="post">
            <h2 class="form-signin-heading">个人日记本</h2>
            <label for="userName" class="sr-only">用户名</label>
            <input type="text" id="userName" name="userName" class="form-control" placeholder="用户名" value="${userName}" required autofocus>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码" value="${password}" required>
            <div class="checkbox">
                <label>
                    <input name="remember-me" type="checkbox" value="remember-me" <c:if test="${rememberMe eq 'remember-me'}">checked</c:if>/> 记住我
                </label>
            </div>
            <button class="btn btn-sm btn-primary btn-block" type="submit">登录</button>
            <button class="btn btn-sm btn-block" type="button" onclick="onReset()">重置</button>
            <div id="error" class="error text-danger">${error}</div>
        </form>


    </div> <!-- /container -->
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./assets/js/vendor/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function onReset() {
        $("#userName").val("");
        $("#password").val("");
        $("#error").html("");
    }
</script>
</body>
</html>