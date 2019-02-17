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
                    <li>${questionnaire.name}</li>
                    <li class="active">评估</li>
                </ol>
            </header>
            <div class="row">
                <div class="col-sm-12">
                    <div class="table-responsive">
                        <table class="table table-striped b-t b-light b-b">
                            <thead>
                            <tr>
                            	<th>名称</th>
                                <th>规则</th>
                                <th>结果</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${evaluates}" var="e">
                                <tr>
                                    <td>${e.name}</td>
                                    <td>${e.rule}</td>
                                    <td>${e.result}</td>
                                    <td>
                                        <a href="javascript:modifyEvaluate(${e.id});">修改</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="navbar-right">
						<div class="btn-group">
							<button type="button" class="btn btn-default" onclick="addEvaluate(${questionnaire.id});">添加评估</button>
						</div>
	                </div>
                </div>
            </div>
        </section>
    </section>
</section>
<div class="modal fade" id="modifyEvaluate" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">添加/修改评估</h4>
			</div>
			<div class="modal-body panel-body">
				<form id="modifyEvaluateForm" class="form-horizontal">
					<input type="hidden" id="eid"/>
	                <input type="hidden" id="qnId"/>
	                <div class="form-group">
	                     <label class="col-sm-2 control-label"><span class="text-danger">*</span>名字：</label>
	                     <div class="col-sm-10">
	                     	<input type="text" class="form-control" required id="name"/>
	                     </div>
	                </div>
					<div class="form-group">
	                     <label class="col-sm-2 control-label"><span class="text-danger">*</span>规则：</label>
	                     <div class="col-sm-10">
	                     	<textarea class="form-control" required id="rule" rows="3"></textarea>
	                     </div>
	                </div>
	                <div class="line line-dashed b-b line-lg pull-in"></div>
	                <div class="form-group">
	                     <label class="col-sm-2 control-label"><span class="text-danger">*</span>结果：</label>
	                     <div class="col-sm-10">
	                     	<textarea class="form-control" required id="result" rows="10" cols="100"></textarea>
	                     </div>
	                </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="modifyEvaluateAction();">确认</button>
			</div>
		</div>
	</div>
</div>
</body>
<footer>
<%@include file="layout/footer.jsp" %>
<script type="text/javascript">
	function resubmitSearch(){
		 location.href = "${ctx}/mvc/evaluate/manage?questionnaireId=${questionnaire.id}";
	}
	
	function modifyEvaluate(eid){
		$.getJSON( "${ctx}/mvc/evaluate/get?id="+eid, 
		    	function(data) { 
					$("#eid").val(eid);
					$("#qnId").val("");
					$("#name").val(data.body.name);
					$("#rule").val(data.body.rule);
					$("#result").val(data.body.result);
					$('#modifyEvaluate').modal('toggle');
		    	}
		    ); 
	}
	function addEvaluate(qnId){
		$("#eid").val("");
		$("#qnId").val(qnId);
		$("#name").val("");
		$("#rule").empty();
		$("#result").val("");
		$('#modifyEvaluate').modal('toggle');
	}
	
	function modifyEvaluateAction(){
		if( $('#modifyEvaluateForm')[0].reportValidity() ){
			var formDate = {
				id     :$("#eid").val(),
				qnId   :$("#qnId").val(),
				name   :$("#name").val(),
				rule   :$("#rule").val(),
				result :$("#result").val()
			};
			var url;
			if( formDate.id )
				url = "${ctx}/mvc/evaluate/modify";
			else
				url = "${ctx}/mvc/evaluate/create";
			showLoading();
			$.post( url, formDate, function(data){
	    		if( data.head.state=='success') {
	    			resubmitSearch();
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