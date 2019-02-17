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
					<li>用户</li>
					<li class="active">用户管理</li>
				</ol>
            </header>
            <form id="searchForm" action="${ctx}/mvc/users/manage" method="post">
            	<div class="input-group" style="margin-bottom: 5px;">
					<input id="search" class="form-control" name="nameOrPhone" type="text" placeholder="名字/手机/微信" value="${param['nameOrPhone']}"/>
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
		                        	<th></th>
		                        	<th>注册日期</th>
		                            <th>姓名</th>
		                            <th>电话</th>
									<th>微信昵称</th>
									<th>经验值</th>
									<th>来源</th>
									<th></th>
		                        </tr>
	                        </thead>
	                        <tbody>
	                        <c:forEach items="${users}" var="user">
	                            <tr>
	                            	<td style="width: 5px;">
		                            	<span class="glyphicon glyphicon-chevron-right" onclick="turnAddress(this, ${user.id});"></span>
	                            	</td>
	                            	<td>${DateUtils.formatDate(user.createTime)}</td>
	                            	<td>${user.name}</td>
	                                <td>${user.phone}</td>
	                                <td>${user.wechatName}</td>
	                                <td>${user.empiricValue}</td>
	                                <td>
	                                	<c:if test="${user.fromType=='ADVISER_INPUT'}">
	                                		顾问创建:${AdviserFacadeService.getInstance().getAdviserNameById(user.fromId) }
	                                	</c:if>
	                                	<c:if test="${user.fromType=='ADVISER_SPREAD'}">
	                                		顾问推广:${AdviserFacadeService.getInstance().getAdviserNameById(user.fromId) }
	                                	</c:if>
	                                	<c:if test="${user.fromType=='NPH_INPUT'}">
	                                		农品荟
	                                	</c:if>
	                                </td>
	                               	<td>
		                            	<span class="glyphicon glyphicon-pencil" onclick="modifyCustomer(${user.id});"></span>
		                            	<span class="glyphicon glyphicon-list" onclick="searchOrder(${user.id});"></span>
		                            	<span class="glyphicon glyphicon-yen" onclick="searchPayOrder(${user.id});"></span>
	                               	</td>
	                            </tr>
	                        </c:forEach>
	                        </tbody>
	                    </table>
	                    <web:pagination pageList="${users}" postParam="true"/>
	                </div>
                </div>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
</section>
<div class="modal fade" id="modifyCustomer" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">修改客户</h4>
			</div>
			<div class="modal-body panel-body">
				<form id="modifyCustomerForm" class="form-horizontal">
					<div class="form-group">
	                     <label class="col-sm-3 control-label"><span class="text-danger">*</span>客户：</label>
	                     <div class="col-sm-9">
	                     	 <input type="hidden" id="cid" name="id"/>
	                         <input type="tel"  class="form-control" required  name="phone" id="phone" placeholder="手机"/>
	                         <input type="text" style="margin-top:5px;" class="form-control" required name="name" id="name" placeholder="姓名"/>
	                     </div>
	                </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="modifyCustomerAction();">确认</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="addressModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="addressModalTitle">修改地址</h4>
			</div>
			<div class="modal-body panel-body">
				<form id="addressForm" class="form-horizontal">
					<div class="form-group">
                        <label class="col-sm-3 control-label"><span class="text-danger">*</span>配送地址：</label>
                        <div class="col-sm-9">
                        	<input type="hidden" name="aid" id="aid"/>
                            <input type="tel"  class="form-control" required name="sendeePhone" id="sendeePhone" placeholder="收件人手机"/>
                       	  	<input type="text" class="form-control" required name="sendeeName" id="sendeeName" maxlength="20"  placeholder="收件人姓名" style="margin-top:5px;"/>
                       	  	<input type="text" class="form-control" required name="community" id="community" maxlength="20" placeholder="社区" style="margin-top:5px;"/>
                       	  	<input type="text" class="form-control" required name="address" id="address" maxlength="50" placeholder="地址(幢#门牌号)" style="margin-top:5px;"/>
                        </div>
                    </div> 
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="addressAction();">确认</button>
			</div>
		</div>
	</div>
</div>
</body>
<footer>
	<%@include file="layout/footer.jsp" %>
<script type="text/javascript">
function resubmitSearch(page){
	location.href = "${ctx}/mvc/users/manage?nameOrPhone=${param['nameOrPhone']}&page=" + page;
}
function turnAddress(e,id){
	if( $(e).hasClass('glyphicon-chevron-right') ) {
		var address = $('tr[data-uid="'+id+'"]');
	    if( address.length==0 ) {//first, load
	    	$.getJSON( "${ctx}/mvc/users/"+id+"/addressInfo/", 
	   		    function(data) {
	   				$(e).parent().attr("rowspan", data.length+1);
	   				for(var i=0; i<data.length; i++){
	   					var a = data[i];
	   					var clk = "modifyAddress("+a.id+",'"+a.sendeePhone+"','"+a.sendee+"','"+a.community+"','"+ a.detailAddress+"')";
	   					$(e).parent().parent().after('<tr data-uid="'+id+'"><td style="border-style:none">'+a.community+'</td><td style="border-style:none">'+a.detailAddress+'</td><td style="border-style:none">'+a.sendee+'</td><td style="border-style:none">'+a.sendeePhone+'</td><td style="border-style:none">'+a.whenDelivery+'</td><td style="border-style:none">'+a.lastUsed+'</td><td style="border-style:none"><span class="glyphicon glyphicon-pencil" onclick="'+clk+'"></span></td></tr>');
	   				}
				}
	    	); 	
	    } else {//second, display directly
	    	address.css("display", "");
	    	$(e).parent().attr("rowspan", address.length+1);
	    }
	    $(e).removeClass('glyphicon-chevron-right');
	    $(e).addClass('glyphicon-chevron-down');
	} else {
		$('tr[data-uid="'+id+'"]').css("display", "none");
		$(e).parent().attr("rowspan", 1);
		$(e).removeClass('glyphicon-chevron-down');
		$(e).addClass('glyphicon-chevron-right');
	}
}

function modifyCustomer(cid){
	$.getJSON( "${ctx}/mvc/users/"+cid, 
    	function(data) { 
			$("#cid").val(cid);
			$("#phone").val(data.phone);
			$("#name").val(data.name);
		    $('#modifyCustomer').modal('toggle');
    	}
    ); 
}
function modifyCustomerAction(){
	if( $('#modifyCustomerForm')[0].reportValidity() ){
		showLoading();
		$.post("${ctx}/mvc/users/modify", $('#modifyCustomerForm').serialize(), function(data){
    		if( data.head.state=='success') {
    			resubmitSearch(1);
    		} else {
    			closeLoading();
    			if(data.head.code==0){
    				location.href="${ctx}/page/login.jsp";
    			} else if( data.head.code==500 ){
    				alert( "修改失败，系统内部错误！"  + data.body);
    			} else if( data.head.code==1001 ){
    				alert( "修改失败，该新手机号已存在!");
    			} 
    		}
    	},"json");
	}	 
}
function modifyAddress(aid, sp, sn, c, a){
	$('#aid').val(aid);
	$('#sendeePhone').val(sp);
	$('#sendeeName').val(sn);
	$('#community').val(c);
	$('#address').val(a);
	$('#addressModal').modal('toggle');
}
function addressAction(){
	if( $('#addressForm')[0].reportValidity() ){
		showLoading();
		$.post("${ctx}/mvc/users/address", $('#addressForm').serialize(), function(data){
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
function searchOrder(userId){
	location.href = "${ctx}/mvc/order/search/user/" + userId;
}
function searchPayOrder(userId){
	location.href = "${ctx}/mvc/order/pay/manage?userId=" + userId;
}
</script>
</footer>
</html>