<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="cn.nongph.jiamei.core.utils.DateUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/page/layout/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<%@include file="layout/header.jsp" %>
</head>
<body class="">
<%@include file="layout/navigation.jsp" %>	
<section id="content">
    <section class="vbox">
        <section class="padder">
            <header class="panel-heading">
				<ol class="breadcrumb">
					<li><a href="#">老师</a></li>
					<li class="active">老师管理</li>
				</ol>
            </header>
            <div class="row">
                <div class="col-sm-12">
	                <div class="table-responsive">
	                    <table class="table table-striped b-t b-light b-b">
	                        <thead>
	                        <tr>
                        		<th>姓名</th>	
	                        	<th>登录名</th>
								<th>电话</th>
								<th>角色</th>
	                            <th>操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <c:forEach items="${employees}" var="employe">
	                            <tr>
	                        		<td>${employe.name}</td>
	                                <td>${employe.username}</td>
	                                <td>${employe.mobile}</td>
	                                <td>${employe.roleList.stream().map(r->r.rolename).toList()}</td>
									<td>
										<a href="${ctx}/mvc/employee/modify?id=${employe.id}">修改</a>
									</td>
	                            </tr>
	                        </c:forEach>
	                        </tbody>
	                    </table>
	                </div>
                </div>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
</section>
</body>
<footer>
	<%@include file="layout/footer.jsp" %>
</footer>
</html>