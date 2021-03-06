<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加日记类别</title>

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

                    <c:if test="${diaryType != null && diaryType.diaryTypeId == 0}"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加日记类别</c:if>
                    <c:if test="${diaryType != null && diaryType.diaryTypeId > 0}"><span class="glyphicon glyphicon-edit"></span>&nbsp;修改日记类别</c:if>
                </h3>
            </div>
            <div class="col-xs-2 col-sm-2"></div>
        </div>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" action="<%=request.getContextPath()%>/diaryType?action=save" method="post" target="_parent">
            <input type="hidden" name="diaryTypeId" value="${diaryType.diaryTypeId}"/>
            <div class="form-group">
                <div class="col-sm-2"></div>
                <label for="diaryTypeName" class="col-sm-2 control-label">类别名称：</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="diaryTypeName" name="diaryTypeName" placeholder="类别名称" value="${diaryType.diaryTypeName}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"></div>
                <div class="col-sm-2 text-right">
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
                <div class="col-sm-2">
                    <a target="_parent" href="<%=request.getContextPath()%>/diaryType?action=list" class="btn btn-primary">取消</a>
                </div>
            </div>
        </form>
        <div class="error text-danger">${error}</div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./assets/js/vendor/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(window.parent.document).find("#pageIframe").load(function(){
        var pageIframe = $(window.parent.document).find("#pageIframe");
        var thisheight = $(document).height()+30;
        pageIframe.height(thisheight);
    });
</script>
</body>
</html>
