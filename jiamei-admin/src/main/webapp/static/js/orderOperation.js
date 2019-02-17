function handleOrderOptResult(opt, name, result){
	if( result.head.state=='success' ) {
	  //alert( opt+"\n"+name+"\n的订单成功！");
		resubmitSearch(1);
	} else {
		if( result.head.code==0){
			location.href="${ctx}/page/login.jsp";
		} else if( result.head.code==500 ){
			alert( opt+"失败，系统内部错误！" + data.body );
		} else if( data.head.code==1001 ){
			alert( "订单状态已改变，"+opt+"失败!" );
			resubmitSearch(1);
		} else {
			alert( opt+"失败！错误码:"+data.head.code );
		}
	}
}