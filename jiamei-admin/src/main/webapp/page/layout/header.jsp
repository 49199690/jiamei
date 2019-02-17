<%@page import="cn.nongph.jiamei.admin.security.PassportHandle" %>
<c:set var="au" value="${PassportHandle.getCurrentUser(pageContext.request)}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta charset="utf-8" />
<title></title>
<script type="text/javascript">
   var ctx = '${ctx}';
</script>
<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx}/static/js/jPlayer/jplayer.flat.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/js/bootstrap/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" type="text/css" />

<link rel="stylesheet" href="${ctx}/static/css/animate.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/simple-line-icons.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/font.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/app.css" type="text/css" />


<link rel="stylesheet" href="${ctx}/static/js/slider/slider.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/js/chosen/chosen.css" type="text/css" />

<%--myStyle.css--%>
<link rel="stylesheet" href="${ctx}/static/css/myStyel.css" type="text/css" />

<!--[if lt IE 9]-->
<script src="${ctx}/static/js/ie/html5shiv.js"></script>
<script src="${ctx}/static/js/ie/respond.min.js"></script>
<script src="${ctx}/static/js/ie/excanvas.js"></script>
<!--[endif]-->