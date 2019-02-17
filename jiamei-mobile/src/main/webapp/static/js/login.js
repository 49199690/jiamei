function checkPhone(){
	var p1 = $('#phone1');
	var m1 = $.trim(p1.val());
    if (m1 == "") {
        nph.showMessage("手机号不能为空");
        p1.focus();
    } else {
    	if( !nph.validatePhone(m1) ){
    		nph.showMessage("手机号格式错误，请重新输入");
    		p1.focus();
    	} else {   
    		var p2 = $('#phone2');
    		var m2 = $.trim(p2.val());
    	    if (m2 == "") {
    	        nph.showMessage("确认手机号不能为空");
    	        p2.focus();
    	    } else {
    	    	if( !nph.validatePhone(m2) ){
    	    		nph.showMessage("确认手机号格式错误，请重新输入");
    	    		p2.focus();
    	    	} else {
    	    		if( m1!=m2 ) {
    	    			nph.showMessage("两次输入的手机号不相同，请输入相同手机号");
    	    		} else {
    	    			return true;
    	    		} 
    	    	}
    	    }
    	}
    }
    return false;
}

function enableBtn(){
	if( $("#phone1").val()!="" && $("#phone2").val()!="" ) 
		$("#loginBtn").removeClass("btn_disabled");
	else
		$("#loginBtn").addClass("btn_disabled");
}

$(function(){
	var from = decodeURIComponent(nph.getQueryParam("from"));
	
    $("#phone1").keyup(enableBtn);
    $("#phone2").keyup(enableBtn);
    $("#loginBtn").click(function () {
        if (!checkPhone()) {
            return;
        }
        nph.showLoading();
        $.ajax({
        	type:"POST",
            url: "/mvc/close/user/login",
            data: {
                "phone1": $("#phone1").val(),
                "phone2": $('#phone2').val(),
                "aid": sessionStorage.getItem("aid")
            },
            dataType: "json",
            success: function (data) {
                nph.hideLoading();
                if( data.head.state=='success'){
                	nph.ss.setItem("phone", data.body);
                	location.href = from;
                } else {
                    if (data.head.code == 202) {
                        nph.showMessage("手机号码格式错误！");
                    } else if(data.head.code == 203){
                    	nph.showMessage("确认手机号码格式错误！");
                    } else if(data.head.code == 204){
                    	nph.showMessage("两个手机号码不相同！");
                    } else {
                    	nph.showMessage(data.body);
                    }
                }
            }
        });
    });
});