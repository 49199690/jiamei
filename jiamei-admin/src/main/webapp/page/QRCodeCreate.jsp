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
                    <li><a href="#">问卷</a></li>
                    <li class="active">合作二维码</li>
                </ol>
            </header>
            <div class="row">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-8">
                    <form class="form-horizontal" action="${ctx}/mvc/qrcode/create/qrcode.png" method="POST" target="_">
                    	<input type="hidden" name="questionnaireId" value="${questionnaire.id}"/>
                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <strong>二维码信息：</strong>
                            </header>
                            <div class="panel-body">
                                <div class="form-group">
                                     <label class="col-sm-3 control-label">问卷：</label>
                                     <div class="col-sm-9">
                                     	<input type="text" readonly="readonly" class="form-control" value="${questionnaire.name}"/>
                                     </div>
                                </div>
                                <div class="line line-dashed b-b line-lg pull-in"></div>
                                <div class="form-group">
                                     <label class="col-sm-3 control-label"><span class="text-danger">*</span>合作方：</label>
                                     <div class="col-sm-9">
                                         <select name="cooperationId" class="form-control">
                                    		<c:forEach items="${cooperations}" var="coop">
                                    		<option value="${coop.id}">${coop.name}</option>
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
</section>
<%@include file="layout/footer.jsp" %>
</body>
</html>