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
                    <li class="active">题目</li>
                </ol>
            </header>
            <div class="row">
                <div class="col-sm-12">
                    <div class="table-responsive">
                        <table class="table table-striped b-t b-light b-b">
                            <thead>
                            <tr>
                            	<th>序号</th>
                                <th>类型</th>
                                <th>内容</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${questions}" var="q">
                                <tr>
                                	<td>${q.sequence}</td>
                                    <td>${ {"FILL":"填空",
                                            "SELECT":"单项选择"
                                            }[q.type] }</td>
                                    <td>${q.content}</td>
                                    <td rowspan="${q.options.size()+1}">
                                        <a href="javascript:modifyQuestion(${q.id});">修改</a>
                                    </td>
                                </tr>
                                <c:forEach items="${q.options}" var="o">
                                <tr>
                                	<td>${o.sequence}</td>
                                    <td>${o.score}</td>
                                    <td>${o.content}</td>
                                </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="navbar-right">
						<div class="btn-group">
							<button type="button" class="btn btn-default" onclick="addQuestion(${questionnaire.id});">添加题目</button>
						</div>
	                </div>
                </div>
            </div>
        </section>
    </section>
</section>
<div class="modal fade" id="modifyQuestion" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">添加/修改题目</h4>
			</div>
			<div class="modal-body panel-body">
				<form id="modifyQuestionForm" class="form-horizontal">
					<input type="hidden" id="qid"/>
	                <input type="hidden" id="qnId"/>
					<div class="form-group">
	                     <label class="col-sm-2 control-label"><span class="text-danger">*</span>题目：</label>
	                     <div class="col-sm-10">
	                     	 <input type="number" class="form-control" required id="sequence" min="1" step="1" placeholder="序号">
	                     	 <select style="margin-top:5px;" name="type" class="form-control" required>
	                     	 	<option value="SELECT">单项选择</option>
	                     	 </select>
	                     	 <textarea style="margin-top:5px;" class="form-control" required id="questionContent" placeholder="内容" rows="3"></textarea>
	                     </div>
	                </div>
	                <div class="line line-dashed b-b line-lg pull-in"></div>
	                <div class="form-group">
	                     <label class="col-sm-2 control-label"><span class="text-danger">*</span>选项：</label>
	                     <div class="col-sm-10" id="optionsNode">
	                     </div>
	                </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="addOption();">增加选择</button>
				<button type="button" class="btn btn-primary" onclick="delOption();" id="delOptionBtn">删除选项</button>
				<button type="button" class="btn btn-primary" onclick="modifyQuestionAction();">确认</button>
			</div>
		</div>
	</div>
</div>
</body>
<footer>
<%@include file="layout/footer.jsp" %>
<script type="text/javascript">
	function resubmitSearch(){
		 location.href = "${ctx}/mvc/question/manage?questionnaireId=${questionnaire.id}";
	}
	
	function modifyQuestion(qid){
		$.getJSON( "${ctx}/mvc/question/get?id="+qid, 
		    	function(data) { 
					$("#qid").val(qid);
					$("#qnId").val("");
					$("#sequence").val(data.body.sequence);
					$("#questionContent").val(data.body.content);
					$("#optionsNode").empty();
					data.body.options.forEach(o=>addOption(o.sequence,o.content, o.score));
					$('#modifyQuestion').modal('toggle');
		    	}
		    ); 
	}
	function addQuestion(qnId){
		$("#qid").val("");
		$("#qnId").val(qnId);
		$("#questionContent").val("");
		$("#optionsNode").empty();
		for( var i=0; i<4; i++)
			addOption();
		$('#modifyQuestion').modal('toggle');
	}
	
	function delOption(){
		$("#optionsNode").children().last().remove();
		setButtons();
	}
	
	function addOption(o, c, s){	
		$("#optionsNode").append( 
			'<div style="width: 100%;'+($( "#optionsNode").children().length>0?'margin-top:5px;':'')+'" class="input-group">'+
				'<input style="width: 10%;"  class="form-control" type="text"   value="'+(o?o:'')+'" required placeholder="序号"/>'+
                '<input style="width: 75%;" class="form-control" type="text"   value="'+(c?c:'')+'" required placeholder="内容"/>'+
                '<input style="width: 15%;" class="form-control" type="number" value="'+(s>=0?s:'')+'" required placeholder="分数"  min="0" max="20"/>'+
            '</div>'
				);
		setButtons();
	}
	
	function setButtons(){
		if( $("#optionsNode").children().length<3 ){
			$("#delOptionBtn").attr("disabled","disabled");  
		} else {
			$("#delOptionBtn").removeAttr("disabled");
		}
	}
	
	function modifyQuestionAction(){
		if( $('#modifyQuestionForm')[0].reportValidity() ){
			
			var options = [];
			$("#optionsNode").children().each(function(){ 
				options.push( { sequence: $(this).find("input[type=text]:first").val(),
					            content : $(this).find("input[type=text]:last").val(),
					            score   : $(this).find("input[type=number]").val() } );
			});
			var formDate = {
				id     :$("#qid").val(),
				qnId   :$("#qnId").val(),
				sequence:$("#sequence").val(),
				content:$("#questionContent").val(),
				options:JSON.stringify(options)
			};
			var url;
			if( formDate.id )
				url = "${ctx}/mvc/question/modify";
			else
				url = "${ctx}/mvc/question/create";
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
	    			} else if( data.head.code==1001 ){
	    				alert( "操作失败，请求参数错误！");
	    			} else if( data.head.code==1002 ){
	    				alert( "操作失败，选项重复！!");
	    			} 
	    		}
	    	},"json");
		}	 
	}
</script>
</footer>
</html>