<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>日记类别列表</title>

    <!-- Bootstrap -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="./assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="./static/common/common.css" rel="stylesheet">
    <style type="text/css" rel="stylesheet">
        html,
        body {
            overflow-x: hidden; /* Prevent scroll on narrow devices */
        }
        body {
            padding-top: 0px;
        }
    </style>
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="panel panel-white">
    <div class="panel-heading">
        <div class="row">
            <div class="col-xs-10 col-sm-10"><h3 class="panel-title"><span class="glyphicon glyphicon-list"></span>&nbsp;日记列表</h3></div>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <c:forEach items="${diarys}" var="diary">
                <div class="col-sm-12">
                    <div class="row margin-bottom-10">
                        <div class="col-sm-3 text-center">『<fmt:formatDate value="${diary.diaryCreateTime}" pattern="yyyy-MM-dd"/>』</div>
                        <div class="col-sm-9">
                            <a target="_parent" href="<%=request.getContextPath()%>/diary?action=show&diaryId=${diary.diaryId}">${diary.diaryTitle}</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="row text-center">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${page.totalPage == 0}">
                        <li class="disabled">
                            <span>
                                <span aria-hidden="true">首页</span>
                            </span>
                        </li>
                    </c:if>
                    <c:if test="${page.totalPage > 0}">
                        <li>
                            <a href="<%=request.getContextPath()%>/main?page=1" aria-label="First" target="_parent" target="_parent">
                                <span aria-hidden="true">首页</span>
                            </a>
                        </li>
                    </c:if>
                    <li class="<c:if test='${page.page <= 1}'>disabled</c:if>">
                        <c:if test='${page.page > 1}'>
                            <a href="<%=request.getContextPath()%>/main?page=${page.page - 1 > 1 ? page.page-1:1}" aria-label="Previous" target="_parent">
                                <span aria-hidden="true">上一页</span>
                            </a>
                        </c:if>
                        <c:if test='${page.page <= 1}'>
                            <span>
                                <span aria-hidden="true">上一页</span>
                            </span>
                        </c:if>
                    </li>
                    <c:forEach var="p" begin="1" end="${page.totalPage}">
                        <c:if test='${page.page == p}'>
                            <li class="active">
                                <span>${p}</span>
                            </li>
                        </c:if>
                        <c:if test='${page.page != p}'>
                            <li>
                                <a href="<%=request.getContextPath()%>/main?page=${p}" target="_parent">${p}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                    <c:if test='${page.page < page.totalPage}'>
                        <li>
                            <a href="<%=request.getContextPath()%>/main?page=${page.page+1 < page.totalPage ? page.page+1:page.totalPage}" aria-label="Next" target="_parent">
                                <span aria-hidden="true">下一页</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test='${page.page >= page.totalPage}'>
                        <li class="disabled">
                            <span>
                                <span aria-hidden="true">下一页</span>
                            </span>
                        </li>
                    </c:if>
                    <c:if test="${page.totalPage == 0}">
                        <li class="disabled">
                            <span>
                                <span aria-hidden="true">尾页</span>
                            </span>
                        </li>
                    </c:if>
                    <c:if test="${page.totalPage > 0}">
                        <li>
                            <a href="<%=request.getContextPath()%>/main?page=${page.totalPage}" aria-label="Last" target="_parent">
                                <span aria-hidden="true">尾页</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./assets/js/vendor/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./dist/js/bootstrap.min.js"></script>
</body>
</html>
