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
					<li>问卷</li>
					<li class="active">答卷</li>
				</ol>
            </header>
            <form id="searchForm" action="${ctx}/mvc/answer/manage" method="post">
            	<input type="hidden" name="page" value="1"/>
            	<div class="input-group" style="margin-bottom: 5px;">
            		<div class="input-group date" style="width:30%;float: left;">
                    	<input type="text" class="form-control" name="startTime" placeholder="开始时间" value="${param['startTime']}"/>
                    	<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                    </div>
					<div class="input-group date" style="width:30%;float: left;">
                    	<input type="text" class="form-control" name="endTime"   placeholder="结束时间" value="${param['endTime']}"/>
                    	<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                    </div>
                    <select name="cooperation" class="form-control" style="width:40%;float: left;">
						<option></option>
						<c:forEach items="${cooperations}" var="coop">
							<option value="${coop.id}"  <c:if test="${param['cooperation']==coop.id}">selected</c:if>>${coop.name}</option>
					    </c:forEach>
					</select>
					<span class="input-group-btn">
						<button class="btn btn-default" type="submit">搜索</button>
					</span>
				</div>
			</form>
            <div class="row">
                <div class="col-sm-12">
	                <div class="table-responsive">
	                    <table class="table table-striped b-t b-light b-b">
	                        <thead>
		                        <tr>
		                        	<th>来源</th>
		                        	<th>提交时间</th>
		                            <th>用户手机</th>
		                            <th>调查问卷</th>
									<th>分数</th>
									<th>评估</th>
									<th></th>
		                        </tr>
	                        </thead>
	                        <tbody>
	                        <c:forEach items="${answers}" var="answer">
	                            <tr>
	                            	<td>${answer.fromDesc}</td>
	                            	<td>${DateUtils.formateDateTime(answer.submitTime)}</td>
	                            	<td>${answer.user.phone}</td>
	                                <td>${answer.questionnaire.name}</td>
	                                <td>${answer.score}</td>
	                                <td>${answer.evaluate.name}</td>
	                               	<td>
	                               	</td>
	                            </tr>
	                        </c:forEach>
	                        </tbody>
	                    </table>
	                    <web:pagination pageList="${answers}" postParam="true"/>
	                </div>
                </div>
            </div>
        </section>
    </section>
</section>
</body>
<footer>
	<%@include file="layout/footer.jsp" %>
<script type="text/javascript">
$('.date').datetimepicker({
    format: "yyyy-mm-dd hh:mm",
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
}); 

function resubmitSearch(page){
	$("input[name=page]").attr("value",page);
	$("#searchForm").submit();
}
</script>
</footer>
</html>