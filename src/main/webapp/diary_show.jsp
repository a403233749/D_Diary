<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>日记信息</title>

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
                    <span class="glyphicon glyphicon-th-large"></span>&nbsp;日记信息
                </h3>
            </div>
            <div class="col-xs-2 col-sm-2"></div>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-sm-12 text-center">
                <h3>${diary.diaryTitle}</h3>
            </div>
            <div class="col-sm-12 text-center">
                发布时间:『<fmt:formatDate value="${diary.diaryCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>』&nbsp;&nbsp;日记类别:『${diaryType.diaryTypeName}』
            </div>
            <div class="col-sm-12">
                ${diary.diaryContent}
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6">
                <a href="<%=request.getContextPath()%>/diary?action=preSave&diaryId=${diary.diaryId}" class="btn btn-primary" target="_parent">修改日记</a>
                <a href="<%=request.getContextPath()%>/main?all=true" class="btn btn-primary" target="_parent">取消</a>
                <button type="button" class="btn btn-danger" onclick="onDelete(${diary.diaryId})">删除日记</button>
            </div>
        </div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./assets/js/vendor/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function onDelete(diaryId) {
        var flag = confirm("您确定要删除这个日记吗？");
        if(flag) {
            window.parent.location.href = "<%=request.getContextPath()%>/diary?action=delete&diaryId="+diaryId;
        }
    }
	$(window.parent.document).find("#pageIframe").load(function(){ 
		var pageIframe = $(window.parent.document).find("#pageIframe"); 
		var thisheight = $(document).height()+30; 
		pageIframe.height(thisheight); 
	});
</script>
</body>
</html>
