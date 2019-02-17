<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/page/layout/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="UTF-8">
    <title>佳美管理系统 | 登录</title>
    <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <link rel="stylesheet" href="${ctx}/static/js/jPlayer/jplayer.flat.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/js/bootstrap/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/animate.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/simple-line-icons.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/font.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/css/app.css" type="text/css" />
</head>
<body  class="dker">

<section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <div class="container aside-xl">
        <a class="navbar-brand block" href="#"><span class="h2 font-bold">佳美管理系统</span></a>
        <section class="m-b-lg">
            <form action="${ctx}/mvc/login" method="post" parsley-validate>
                <c:if test="${not empty errorMessage}">
                    <p class="text-danger">${errorMessage}</p>
                </c:if>
                <div class="form-group">
                    <input type="text"   name="username" placeholder="用户名" class="form-control rounded input-lg text-center no-border" required>
                </div>
                <div class="form-group">
                    <input type="password" name="password" placeholder="密码" class="form-control rounded input-lg text-center no-border" required>
                </div>
                <button type="submit" class="btn btn-lg btn-warning lt b-white b-2x btn-block btn-rounded">
                	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg">登 录</span>
                </button>
            </form>
        </section>
    </div>
</section>
<!-- parsley -->
<script src="${ctx}/static/js/jquery2/jquery-2.1.4.js"></script>
<script src="${ctx}/static/js/parsley/parsley.min.js"></script>
<script src="${ctx}/static/js/parsley/parsley.extend.min.js"></script>
</body>
</html>