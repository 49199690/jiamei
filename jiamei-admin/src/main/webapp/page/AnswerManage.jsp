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
            	<div class="input-group" style="margin-bottom: 5px;">
					<input id="search" class="form-control" name="nameOrPhone" type="text" placeholder="时间，合作渠道" value="${param['nameOrPhone']}"/>
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
	                            	<td>${DateUtils.formatDate(answer.submitTime)}</td>
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
function resubmitSearch(page){
	location.href = "${ctx}/mvc/answer/manage?nameOrPhone=${param['nameOrPhone']}&page=" + page;
}
</script>
</footer>
</html>