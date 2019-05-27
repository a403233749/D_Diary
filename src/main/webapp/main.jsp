<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
    <link href="./static/pages/main/main.css" rel="stylesheet">
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="navbar-text">个人日记本系统</div>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li id="main" class="active"><a href="<%=request.getContextPath()%>/main?all=true"><span class="glyphicon glyphicon-home"></span>&nbsp;主页</a></li>
                    <li id="diary"><a href="<%=request.getContextPath()%>/diary?action=preSave"><span class="glyphicon glyphicon-pencil"></span>&nbsp;写日记</a></li>
                    <li id="diaryType"><a href="<%=request.getContextPath()%>/diaryType?action=list" onclick="navChange('diarytype_manager')"><span class="glyphicon glyphicon-book"></span>&nbsp;日记分类管理</a></li>
                    <li id="user"><a href="<%=request.getContextPath()%>/user?action=preSave"><span class="glyphicon glyphicon-user"></span>&nbsp;个人中心</a></li>
                </ul>
                <form action="<%=request.getContextPath()%>/main?all=true" class="navbar-form navbar-right" method="post">
                    <div class="form-group">
                        <input name="s_diaryTitle" type="text" placeholder="搜索" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span>&nbsp;搜索日记</button>
                </form>
            </div><!--/.nav-collapse -->
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-sm-9">
                <iframe id="pageIframe" src="diary_list.jsp" frameborder="0" width="100%" height="1000px"></iframe>
            </div><!--/.col-xs-12.col-sm-9-->

            <div class="col-sm-3">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="panel panel-white">
                            <div class="panel-heading">
                                <h3 class="panel-title"><span class="glyphicon glyphicon-user"></span>&nbsp;个人中心</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-sm-12 text-center">
                                        <div class="thumbnail">
                                            <img  src="${imageFile}${currentUser.imageName}" alt="..." style="height: 300px;">
                                        </div>
                                        <div>${currentUser.nickName}</div>
                                        <div>( ${currentUser.mood} )</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-white">
                            <div class="panel-heading">
                                <h3 class="panel-title"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;按日记类别</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <ul class="list-unstyled">
                                            <c:forEach items="${diaryTypes}" var="diaryType">
                                                <li><a href="<%=request.getContextPath()%>/main?s_typeId=${diaryType.diaryTypeId}">${diaryType.diaryTypeName} (${diaryType.diaryCount}) </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-white">
                            <div class="panel-heading">
                                <h3 class="panel-title"><span class="glyphicon glyphicon-calendar"></span>&nbsp;按日记日期</h3>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <ul class="list-unstyled">
                                            <c:forEach items="${diaryCreateTimes}" var="dct">
                                                <li><a href="<%=request.getContextPath()%>/main?s_releaseDateStr=${dct.create_time}">${dct.create_time} (${dct.totalCount}) </a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!--/.col-xs-12.col-sm-12 -->
                </div>
            </div><!--/.col-xs-12.col-sm-3-->
        </div><!--/row-->
    </div><!--/.container -->

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="./assets/js/vendor/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="./dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var pageName = '${pageName}';
            var pageAction = '${pageAction}';
            changePageIframe(pageName,pageAction);
        });

        function navChange(pageName) {
            $("#main").removeClass("active");
            $("#diary").removeClass("active");
            $("#diaryType").removeClass("active");
            $("#user").removeClass("active");
            $("#"+pageName).addClass("active");
        }

        function changePageIframe(pageName,pageAction) {
            if(pageName != '' && pageAction != '')
            {
                navChange(pageName);
                $("#pageIframe").attr("src",pageName+'_'+pageAction+".jsp");
            }else if (pageName == 'main' || pageName == 'login') {
                navChange(pageName);
                $("#pageIframe").attr("src","diary_list.jsp");
            }
        }

    </script>
</body>
</html>