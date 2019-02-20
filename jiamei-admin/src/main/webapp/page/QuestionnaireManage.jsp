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
                    <li class="active">管理</li>
                </ol>
            </header>
            <form id="searchForm" action="${ctx}/mvc/questionnaire/manage" method="post">
                <div class="input-group" style="margin-bottom: 5px;">
                    <input id="search" class="form-control" name="name" type="text" placeholder="名称" value="${param['name']}"/>
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
                                <th>创建时间</th>
                                <th>名称</th>
                                <th>状态</th>
                                <th>下一版本</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${questionnaire}" var="q">
                                <tr>
                                    <td>${DateUtils.formatDate(q.createTime)}</td>
                                    <td>${q.name}</td>
                                    <td>${ {"PREPARE":"准备中",
                                            "RELEASE":"已发布",
                                            "LOCKED" :"已填写",
                                            "CLOSED" :"已关闭"}[q.state] }</td>
                                    <td>${q.nextVersion.name}</td>
                                    <td>
                                        <a href="${ctx}/mvc/questionnaire/modify?id=${q.id}">修改</a>
                                        <c:if test="${q.state=='PREPARE'}">
                                        &nbsp;&nbsp;
                                        <a href="javascript:release(${q.id},'${q.name}')">发布</a>
                                        </c:if>
                                        <c:if test="${q.state=='RELEASE'}">
                                        &nbsp;&nbsp;
                                        <a href="javascript:close(${q.id},'${q.name}')">关闭</a>
                                        </c:if>
                                        <c:if test="${q.state=='CLOSED'}">
                                        &nbsp;&nbsp;
                                        <a href="javascript:nextVersion(${q.id})">下个版本</a>
                                        </c:if>
                                        &nbsp;&nbsp;
                                        <a href="${ctx}/mvc/question/manage?questionnaireId=${q.id}">题目</a>
                                        &nbsp;&nbsp;
                                        <a href="${ctx}/mvc/evaluate/manage?questionnaireId=${q.id}">评估</a>
                                        &nbsp;&nbsp;
                                        <a href="${ctx}/mvc/qrcode/create?questionnaireId=${q.id}">合作二维码</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <web:pagination pageList="${questionnaire}" postParam="true"/>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
<div class="modal fade" id="selectQuestionnaire" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择下个版本问卷</h4>
			</div>
			<div class="modal-body panel-body">
				<form id="selectQuestionnaireForm" class="form-horizontal">
	                <input type="hidden" id="qnId"/>
					<div class="form-group">
	                     <label class="col-sm-3 control-label"><span class="text-danger">*</span>下个版本：</label>
	                     <div class="col-sm-9">
	                     	 <select name="type" class="form-control" id="nextId" required></select>
	                     </div>
	                </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="selectQuestionnaireAction();">确认</button>
			</div>
		</div>
	</div>
</div>
</body>
<footer>
    <%@include file="layout/footer.jsp" %>
    <script type="text/javascript">
        function resubmitSearch(page){
            location.href = "${ctx}/mvc/questionnaire/manage?name=${param['name']}&page=" + page;
        }
        
        function release(id, name){
        	if(confirm("一旦发布，用户将参与调查。\n一旦有用户填写调查，将不能修改。\n确认发布【"+name+"】吗？")){
    	    	showLoading();
    	    	$.post( "${ctx}/mvc/questionnaire/release", {id:id}, function(data){
    	    		if( data.head.state=='success') {
    	    			resubmitSearch(1);
    	    		} else {
    	    			closeLoading();
    	    			if(data.head.code==0){
    	    				location.href="${ctx}/page/login.jsp";
    	    			} else if( data.head.code==500 ){
    	    				alert( "操作失败，系统内部错误！"  + data.body);
    	    			} else if( data.head.code==1001 ){
    	    				alert( "操作失败，请求状态错误！");
    	    			} 
    	    		}
    	    		
    	    	},"json");
    	    }
        }
        
        function close(id, name){
        	if(confirm("一旦关闭，需要设置其下一版本，否则发行的二维码将无法使用。\n确认关闭【"+name+"】吗？")){
    	    	showLoading();
    	    	$.post( "${ctx}/mvc/questionnaire/close", {id:id}, function(data){
    	    		if( data.head.state=='success') {
    	    			resubmitSearch(1);
    	    		} else {
    	    			closeLoading();
    	    			if(data.head.code==0){
    	    				location.href="${ctx}/page/login.jsp";
    	    			} else if( data.head.code==500 ){
    	    				alert( "操作失败，系统内部错误！"  + data.body);
    	    			} else if( data.head.code==1001 ){
    	    				alert( "操作失败，请求状态错误！");
    	    			} 
    	    		}
    	    		
    	    	},"json");
    	    }
        }
        
        function nextVersion(id){
       		$.getJSON( "${ctx}/mvc/questionnaire/unclosed", 
  		    	function(data) { 
  					if( data.body.length>0 ) {
  						$("#qnId").val(id);	
  						$("#nextId").empty();
      					data.body.forEach( o=>$("#nextId").append('<option value="'+o.id+'">'+o.name+'</option>') );
      					$('#selectQuestionnaire').modal('toggle');
  					} else {
  						alert( "没有未关闭的问卷供选择！请先创建问卷！");
  					}
  		    	}
  		    ); 
        }
        
        function selectQuestionnaireAction(){
        	if( $('#selectQuestionnaireForm')[0].reportValidity() ){
    			var formDate = {
    				id   :$("#qnId").val(),
    				nextId :$("#nextId").val()
    			};
    			showLoading();
    			$.post( "${ctx}/mvc/questionnaire/next", formDate, function(data){
    	    		if( data.head.state=='success') {
    	    			resubmitSearch(1);
    	    		} else {
    	    			closeLoading();
    	    			if(data.head.code==0){
    	    				location.href="${ctx}/page/login.jsp";
    	    			} else if( data.head.code==500 ){
    	    				alert( "操作失败，系统内部错误！"  + data.body);
    	    			}
    	    		}
    	    	},"json");
    		}	 
        }
    </script>
</footer>
</html>