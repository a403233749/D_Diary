<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>个人信息设置</title>

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
        <h3 class="panel-title"><span class="glyphicon glyphicon-wrench"></span>&nbsp;个人信息设置</h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-sm-3">
                <div class="thumbnail">
                    <img src="${imageFile}${currentUser.imageName}" alt="..." style="height: 300px;">
                </div>
            </div>
            <div class="col-sm-9">
                <form class="form-horizontal" action="<%=request.getContextPath()%>/user?action=save" method="post" enctype="multipart/form-data" target="_parent">
                    <div class="form-group">
                        <label for="headPath" class="col-sm-2 control-label">头像路径：</label>
                        <div class="col-sm-9">
                            <input type="file" name="headPath" id="headPath" accept="image/jpeg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="nickName" class="col-sm-2 control-label">个人昵称：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" name="nickName" id="nickName" prefix="个人昵称" value="${currentUser.nickName}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="nickName" class="col-sm-2 control-label">个人心情：</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" name="mood" id="mood" rows="10" prefix="个人心情">${currentUser.mood}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2"><button type="submit" class="btn btn-primary">保存</button></div>
                        <div class="col-sm-9">
                            <a href="<%=request.getContextPath()%>/main?all=true" class="btn btn-primary" target="_parent">取消</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
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
