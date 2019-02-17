$(function(){
	var from = nph.ss.getItem("from");
	if( !from ) {
		from=nph.getQueryParam("from");
		if( from )
			nph.ss.setItem("from", from);
	}
	
	if( nph.isLogined() ){
		$.getJSON("/mvc/close/user/base",function(data){
			var li = parseInt(data.body.experience/5000);
			$("#baseInfo").html("<span width='40%'>手机："+data.body.phone+"</span><span style='margin-left: 10px;'>姓名："+data.body.name+"</span>");
		});
	} else {
		location.href="login.html?from=profile.html";
	}
});