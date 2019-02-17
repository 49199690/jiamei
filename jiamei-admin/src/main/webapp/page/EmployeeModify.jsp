<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/page/layout/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<%@include file="layout/header.jsp" %>
</head>
<body>
<%@include file="layout/navigation.jsp" %>	
<section id="content">
    <section class="vbox">
        <section class="padder">
            <header class="panel-heading">
                <ol class="breadcrumb">
                    <li><a href="#">老师</a></li>
                    <li class="active">老师修改</li>
                </ol>
            </header>
            <div class="row">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-8">
                    <form class="form-horizontal" data-validate="parsley" action="${ctx}/mvc/employee/saveModify" method="POST">
                    	<input type="hidden" name="id" value="${employee.id}"/>
                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <strong>老师信息：</strong>
                            </header>
                            <div class="panel-body">
                                <div class="form-group">
                                     <label class="col-sm-3 control-label">姓名：</label>
                                     <div class="col-sm-9">
                                        ${employee.name}
                                     </div>
                                </div>
                                <div class="form-group">
                                     <label class="col-sm-3 control-label">登录名：</label>
                                     <div class="col-sm-9">
                                        ${employee.username}
                                     </div>
                                </div>
                                <div class="form-group">
                                     <label class="col-sm-3 control-label"><span class="text-danger">*</span>电话：</label>
                                     <div class="col-sm-9">
                                         <input type="text" class="form-control" data-required="true" name="phone" id="phone" data-type="phone" onblur="trimText(this)" value="${employee.mobile}"/>
                                         <span id="phoneError" class="text-danger"></span>
                                     </div>
                                </div>
                                <div class="line line-dashed b-b line-lg pull-in"></div>
                                <div class="form-group">
                                     <label class="col-sm-3 control-label"><span class="text-danger">*</span>角色：</label>
                                     <div class="col-sm-9">
                                         <select name="roleId">
                                    		<c:forEach items="${allRoles}" var="role">
                                    		<option value="${role.id}" <c:if test="${employee.roleList.contains(role)}">selected = "selected" </c:if>>${role.rolename}</option>
                                    		</c:forEach>
										 </select>
                                     </div>
                                </div>	
                            </div>
                            <footer class="panel-footer text-right bg-light lter">
                                <button type="submit" class="btn btn-success btn-s-xs">确定</button>
                            </footer>
                        </section>
                    </form>
                </div>
                <div class="col-sm-2">
                </div>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
</section>
<%@include file="layout/footer.jsp" %>
</body>
</html>