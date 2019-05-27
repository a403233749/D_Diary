<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>写日记</title>

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
            <div class="col-xs-10 col-sm-10">
                <h3 class="panel-title">
                    <c:if test="${diary != null && diary.diaryId == 0}"><span class="glyphicon glyphicon-pencil"></span>&nbsp;写日记</c:if>
                    <c:if test="${diary != null && diary.diaryId > 0}"><span class="glyphicon glyphicon-edit"></span>&nbsp;修改日记</c:if>
                </h3>
            </div>
            <div class="col-xs-2 col-sm-2"></div>
        </div>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" action="<%=request.getContextPath()%>/diary?action=save" target="_parent" method="post">
            <input type="hidden" name="diaryId" value="${diary.diaryId}"/>
            <div class="row margin-bottom-10">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="diaryTitle" name="diaryTitle" value="${diary.diaryTitle}"/>
                </div>
            </div>
            <div class="row margin-bottom-10">
                <div class="col-sm-12">
                    <textarea name="diaryContent" id="diaryContent">${diary.diaryContent}</textarea>
                </div>
            </div>
            <div class="row margin-bottom-10">
                <div class="col-sm-4">
                    <select class="form-control" id="diaryTypeId" name="diaryTypeId">
                        <option value="">请选择日记类别</option>
                        <c:forEach items="${diaryTypes}" var="diaryType">
                            <c:if test="${diaryType.diaryTypeId == diary.diaryTypeId}">
                                <option value="${diaryType.diaryTypeId}" selected>${diaryType.diaryTypeName}</option>
                            </c:if>
                            <c:if test="${diaryType.diaryTypeId != diary.diaryTypeId}">
                                <option value="${diaryType.diaryTypeId}">${diaryType.diaryTypeName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row margin-bottom-10">
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
                <div class="col-sm-1">
                    <a class="btn btn-primary" target="_parent" href="<%=request.getContextPath()%>/main?all=true">取消</a>
                </div>
            </div>
            <div class="error row text-danger">${error}</div>
        </form>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./assets/js/vendor/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./dist/js/bootstrap.min.js"></script>
<script src="./assets/js/ckeditor/ckeditor.js"></script>
<script src="./assets/js/ckeditor/config.js"></script>
<script type="text/javascript">
    $(function () {
       CKEDITOR.replace('diaryContent')
    });
</script>
</body>
</html>
