<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="cn.nongph.jiamei.core.utils.DateUtils" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/page/layout/taglib.jsp" %>
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
					<li>系统参数</li>
					<li class="active">系统参数</li>
				</ol>
            </header>
            <div class="row">
                <div class="col-sm-12">
	                <div class="table-responsive">
	                    <table class="table table-striped b-t b-light b-b">
	                        <thead>
	                        <tr>
	                        	<th>名字</th>
	                            <th>值</th>
	                            <th></th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <c:forEach items="${properties}" var="p">
	                            <tr>
	                            	<td>${p.name}</td>
	                            	<td>${p.value}</td>
			    					<td></td>
			    				</tr>
	                        </c:forEach>
	                        </tbody>
	                    </table>
	                </div>
                </div>
            </div>
        </section>
    </section>
</section>
</body>
<footer>
	<%@include file="layout/footer.jsp" %>
</footer>
</html>