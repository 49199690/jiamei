var nph = new function(){
	this.isWechatBrowser = function () {
	    var ua = window.navigator.userAgent.toLowerCase();
	    return ua.match(/MicroMessenger/i) == 'micromessenger';
	};
    
    this.showMessage = function (msg, timeout) {
        if (!timeout) {
            timeout = 2000;
        }
        if ($('#notification_bar_new').length == 0) {
            $('body').append("<div id='notification_bar_new' style='margin: auto;position: fixed;top: 0; left: 0; bottom: 0; right: 0;max-height:80px; z-index:100000; text-align: center;'>" +
                "<div id='notification_msg' style='max-width:80%;max-height:80px; line-height: 20px;background-color:#111;color:#fff;opacity:0.8;font-size:16px;z-index:100000;border-radius:5px;padding:10px;margin: 0 auto;'></div>" +
                "</div>");
        }
        $('#notification_msg').html(msg);
        $('#notification_bar_new').fadeIn('slow').delay(timeout).fadeOut('slow');
    }
    
    this.showLoading = function () {
		if ($('#loading').length == 0) {
		    $('body').append(
		        '<style>.loading{position:fixed;left:0;top:0;display:table;width:100%;height:100%;text-align:center;z-index:999;background:#fff;opacity:0.9;}.content{width:40px;height:40px;vertical-align:middle;display:table-cell;}.spinner{margin:auto;width:40px;height:40px;position:relative;}.container1>div,.container2>div,.container3>div{width:10px;height:10px;background-color:#ee9c24;border-radius:100%;position:absolute;-webkit-animation:bouncedelay 1.2s infinite ease-in-out;animation:bouncedelay 1.2s infinite ease-in-out;-webkit-animation-fill-mode:both;animation-fill-mode:both;}.spinner .spinner-container{position:absolute;width:100%;height:100%;}.container2{-webkit-transform:rotateZ(45deg);transform:rotateZ(45deg);}.container3{-webkit-transform:rotateZ(90deg);transform:rotateZ(90deg);}.circle1{top:0;left:0;}.circle2{top:0;right:0;}.circle3{right:0;bottom:0;}.circle4{left:0;bottom:0;}.container2 .circle1{-webkit-animation-delay:-1.1s;animation-delay:-1.1s;}.container3 .circle1{-webkit-animation-delay:-1.0s;animation-delay:-1.0s;}.container1 .circle2{-webkit-animation-delay:-0.9s;animation-delay:-0.9s;}.container2 .circle2{-webkit-animation-delay:-0.8s;animation-delay:-0.8s;}.container3 .circle2{-webkit-animation-delay:-0.7s;animation-delay:-0.7s;}.container1 .circle3{-webkit-animation-delay:-0.6s;animation-delay:-0.6s;}.container2 .circle3{-webkit-animation-delay:-0.5s;animation-delay:-0.5s;}.container3 .circle3{-webkit-animation-delay:-0.4s;animation-delay:-0.4s;}.container1 .circle4{-webkit-animation-delay:-0.3s;animation-delay:-0.3s;}.container2 .circle4{-webkit-animation-delay:-0.2s;animation-delay:-0.2s;}.container3 .circle4{-webkit-animation-delay:-0.1s;animation-delay:-0.1s;}@-webkit-keyframes bouncedelay{0%,80%,100%{-webkit-transform:scale(0.0)}40%{-webkit-transform:scale(1.0)}}@keyframes bouncedelay{0%,80%,100%{transform:scale(0.0);-webkit-transform:scale(0.0);}40%{transform:scale(1.0);-webkit-transform:scale(1.0);}}</style><div id="loading" class="loading"><div class="content"><div class="spinner"><div class="spinner-container container1"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div><div class="spinner-container container2"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div><div class="spinner-container container3"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div></div></div></div>'
		    );
		}
		$('#loading').fadeIn('slow');
    }
   
    this.hideLoading = function () {
        if ($('#loading').length != 0) {
            $('#loading').fadeOut('slow');
        }
    }
    
    this.alert = function(title, content, cb) {	
        if( $('.weui_dialog_alert').length == 0 ) {
            $('body').append(
                '<div class="weui_dialog_alert" style="display: none;">'+ 
                	'<div class="weui_mask"></div>'+ 
                	'<div class="weui_dialog">'+ 
                		'<div class="weui_dialog_hd"><strong class="weui_dialog_title">'+title+'</strong></div>'+ 
                		'<div class="weui_dialog_bd">'+content+'</div>'+ 
                		'<div class="weui_dialog_ft">'+ 
                			'<a href="javascript:void(0);" class="weui_btn_dialog primary">确定</a>'+ 
                		'</div>'+ 
                	'</div>'+ 
                '</div>'
            );
        } else {
            $('.weui_dialog_alert .weui_dialog_title').text(title);
            $('.weui_dialog_alert .weui_dialog_bd').text(content);
        }
      //$('.weui_dialog_alert').fadeIn();
        $('.weui_dialog_alert').css("display", "block");

        $('.weui_dialog_alert .primary').on("click", function() {
            //$('.weui_dialog_alert').fadeOut();
            $('.weui_dialog_alert').css("display", "none");
            if( cb )
            	cb();
        })
    };
    
    this.confirm = function(title, content, confirmHandler) {
            if ($('.weui_dialog_confirm').length == 0) {
                $('body').append(
                    '<div class="weui_dialog_confirm" style="display: none;">'
                    +'<div class="weui_mask"></div>'
                    +'<div class="weui_dialog">'
                    +'<div class="weui_dialog_hd"><strong class="weui_dialog_title">'+title+'</strong></div>'
                    +'<div class="weui_dialog_bd">'+content+'</div>'
                +'<div class="weui_dialog_ft">'
                    +'<a href="javascript:;" class="weui_btn_dialog default cancel">取消</a>'
                    +'<a href="javascript:;" class="weui_btn_dialog primary confirm">确定</a>'
                    +'</div>'
                    +'</div>'
                    +'</div>'
                );
            } else {
                $('.weui_dialog_confirm .weui_dialog_title').text(title);
                $('.weui_dialog_confirm .weui_dialog_bd').text(content);
            }
            $('.weui_dialog_confirm').css("display", "block");

            $('.weui_dialog_confirm .cancel').on("click", function() {
                $('.weui_dialog_confirm').css("display", "none");
            })
            $('.weui_dialog_confirm .confirm').on("click", function() {
                //$('.weui_dialog_confirm').fadeOut();
                $('.weui_dialog_confirm').css("display", "none");
                confirmHandler();
            })
        };
    this.getWechatUserInfo = function(toUrl){
    	$.getJSON("/mvc/open/api/wechat/authorize/info/url?au="+encodeURIComponent(toUrl), function(data){
    		if( data.head.state=='success' ) {
    			location.href = data.body;
    		} else {
    			location.href = toUrl;
    		}
    	}); 
    };
    var isPaying = false;
    this.launchWechatPay=function(id){
    	if( !nph.isWechatBrowser() ) {
    		nph.alert("微信支付","请通过微信发起支付！");
    		return;
    	}
		if (isPaying) {
            return;
        }
        isPaying = true;
        $.ajax({type:"GET", url:"/mvc/close/api/pay/wechat/prepare/"+id, dataType: "json",
            success: function(data) {
            	console.log("success: " + JSON.stringify(data));
            	if( data.head.state=="success"){
            		WeixinJSBridge.invoke('getBrandWCPayRequest', {
                        debug:true,
                        appId: data.body.appId,
                        timeStamp: data.body.timeStamp,
                        nonceStr: data.body.nonceStr,
                        package: data.body.package,
                        signType: data.body.signType,
                        paySign: data.body.paySign
                    }, function (res) {
                        isPaying = false;
                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                            $.getJSON("/mvc/close/api/pay/wechat/check/" + id, function(data){
                            	if( data.head.state=="success" ){
                            		location.href="paydone.html?no="+data.body.no+"&fee="+data.body.paidAmount;
                            	} else {
                            		nph.alert("微信支付","支付失败，请联系客服！");
                            	}
                            });
                        } else {
                            if (res.err_msg == "get_brand_wcpay_request:cancel"){
                            	nph.alert("微信支付","取消支付，请重新发起支付，或者在“我的”页面查询订单并发起支付！");
                            }
                        }
                    });
            	} else {
            		isPaying = false;
                	if(data.head.code==201){
                		nph.alert("微信支付","用户未登录，请先登录");
                	} else if(data.head.code==501 ){
                		nph.alert("微信支付","系统内部错误");
                	} else if(data.head.code==502 ){
                		nph.alert("微信支付","请通过微信发起支付");
                	} else if(data.head.code==503 ){
                		nph.alert("微信支付","无法获得用户ID，请通过微信访问");
                	} else if(data.head.code==504 ){
                		nph.alert("微信支付","请不要恶意访问");
                	} else {
                		$("#wechatPay").css("background", "#33cc99");
                		nph.alert("支付失败，请联系客服！")
                	}
            	}  
            },
            error: function(data) {
                isPaying= false;
                console.log("error: " + JSON.stringify(data));
            }
        });	
    };
    	
   this.checkJsApi = function(api, success, fail) {
        wx.checkJsApi({
            jsApiList: [api],
            success: function (res) {
                if (res.checkResult[api]) {
                    success();
                } else {
                    fail();
                }
            }
        });
    } 
    this.getLocation = function(options) {
	   var defaults = {
           success: function (resp) {
               console.info(JSON.stringify(resp))
           },
           fail: function (resp) {
               console.info(JSON.stringify(resp))
           },
           cancel: function (resp) {
               console.info(JSON.stringify(resp))
           },
           error: function (resp) {
               console.info(JSON.stringify(resp))
           }
       };
        var opts = $.extend(defaults, options);
        initWechat(location.href, ["getLocation"], function () {
            wx.getLocation({
                type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: opts.success,
                fail: opts.fail,
                cancel: opts.cancel
            });
        }, opts.error)
    }; 

    this.getQueryParam=function(name) {
 	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
 	   var r = window.location.search.substr(1).match(reg);
 	   if (r!=null) 
 		return (r[2]);
 	   return null;
 	};
    
    this.isLogined = function(){
    	var loginState = $.ajax({url: "/mvc/close/user/loginState", dataType: "json", async:false});
    	if( loginState.status==200 ) {
	    	var result = loginState.responseJSON;
	    	if( result.head.state=="success" ) 
	    		return result.body=="logined";
    	}
    	return false;
    };
    
    this.validatePhone = function(mob) {
        var reg = /^1[34578]\d{9}$/;
        if (reg.test(mob)) {
        	return true;
        } else {
        	return false;
        }
    };
    
    var initWechat = function (url, apiList, ready, error) {
        requestJssdkSign(url, function (data) {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appid, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.sign,// 必填，签名，见附录1
                jsApiList: apiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            wx.error(function (err) {
                error(err)
            });
            wx.ready(function () {
                ready(data)
            })
        })
    };
    
    var requestJssdkSign = function (url, success, error) {
        $.ajax({
            url: "/api/requestJssdkSign",
            type: "POST",
            dataType: "json",
            data: {
                url: url
            },
            success: success,
            error: error
        });
   };
};

nph.ss = new function(){
	this.getItem = function(key){
		var item = sessionStorage.getItem(key);
		if( item )
			return JSON.parse(item);
		else
			return null;
	}
	
	this.setItem = function(key, value){
		sessionStorage.setItem( key, JSON.stringify(value) );
	}
	
	this.removeItem = function(key){
		sessionStorage.removeItem(key);
	}
};

nph.sc = new function(){
	var onsaleSkus = null;
	
	this.getOnsaleSkus = function(){
		if( onsaleSkus==null ) {
			onsaleSkus = nph.ss.getItem("onsale");
		}
		return onsaleSkus;
	}

	this.getSkuById = function(skuId){
		return this.getOnsaleSkus()[skuId];
	}
	this.increaseItem = function(skuId){
		var cart = nph.ss.getItem("cart");
		if( cart == null ) {
			cart = {};
		}  
		if( cart[skuId] ) 
			cart[skuId]=cart[skuId]+1;
		else
			cart[skuId]=1;
		nph.ss.setItem("cart", cart);
		return cart[skuId];
	}
	this.decreaseItem = function(skuId){
		var cart = nph.ss.getItem("cart");
		if( cart!=null && cart[skuId] ) {
			if( cart[skuId]>1 )
				cart[skuId] = cart[skuId]-1;
			else
				cart[skuId] = null;
			nph.ss.setItem("cart", cart );
			return cart[skuId];
		}
	}
	this.removeItem = function(skuId){
		var cart = nph.ss.getItem("cart");
		if( cart!=null && cart[skuId] ) {
			delete cart[skuId];
			nph.ss.setItem("cart", cart );
		}
	}
	this.getItemPrice = function(skuId){
		var sku = this.getSkuById(skuId);
		return nph.ss.getItem("cart")[skuId]*sku.price;
	}
	this.getItemCount = function(skuId){
		var cart = nph.ss.getItem("cart");
		if( cart!=null && cart[skuId] ) {
			return cart[skuId];
		} else {
			return 0;
		} 
	}	
	this.setItemCount = function(skuId, count){
		var cart = nph.ss.getItem("cart");
		if( cart == null )cart = {};
		cart[skuId]=count;
		nph.ss.setItem("cart", cart);
	}
	this.isExistItems = function(){
		return sessionStorage.getItem("cart")!=null;
	}
	this.getAllItems = function(){
		return nph.ss.getItem("cart");
	}
	this.remove = function(){
		sessionStorage.removeItem("cart");
	}
	this.getTotalPrice = function(){
		var result = 0;		
		var items = nph.sc.getAllItems();
		for (skuId in items){  
			result=result+items[skuId]*this.getSkuById(skuId).price;
		}
		return result;
	}
	this.getTotalOriginalPrice = function(){
		var result = 0;		
		var items = nph.sc.getAllItems();
		for (skuId in items){  
			result=result+items[skuId]*this.getSkuById(skuId).originalPrice;
		}
		return result;
	}
	this.getTotalItemCount = function(){
		var result = 0;		
		var items = nph.sc.getAllItems();
		for (skuId in items){  
			result=result+items[skuId];
		}
		return result;
	}
	this.toString = function(){
		return sessionStorage.getItem("cart");
	}
};

nph.ssc= new function(){
	this.setPageGroups = function(page, pg){
		var groups = nph.ss.getItem("groups");
		if( groups == null )
			groups = {};
		groups[page] = pg;
		nph.ss.setItem("groups", groups);
		
		var onsale = nph.ss.getItem("onsale");
		if( onsale==null )
			onsale = {};
		pg.forEach(function(g){
			g.onsaleSkus.forEach(function(s){
				onsale[s.id]=s;
			});
		});
		nph.ss.setItem("onsale", onsale);
	}
	
	this.getPageGroups = function(page){
		var groups = nph.ss.getItem("groups");
		if( groups && groups[page] )
			return groups[page];
		else
			return null;
	}
	
	var doUpdate = function(odata, ndata){
		odata.state       = ndata.state;
		odata.quantity    = ndata.quantity;
		odata.batchNum    = ndata.batchNum;
		odata.batchArrive = ndata.batchArrive;
	}
	
	this.updateSkuData = function(skuData){
		var os = nph.ss.getItem("onsale");
		doUpdate(os[skuData.id], skuData);
		nph.ss.setItem("onsale", os);
		
		var groups = nph.ss.getItem("groups");
		for( p in groups ){
			groups[p].forEach(function(pg){  
				pg.onsaleSkus.forEach(function(s){  
					if( s.id==skuData.id ) {
						doUpdate(s, skuData);
					}
				});   
			});
		}
		nph.ss.setItem("groups", groups);
	}
	
	this.deleteSkuData=function(skuId){
		var os = nph.ss.getItem("onsale");
		delete os[skuId];
		nph.ss.setItem("onsale", os);
		
		var groups = nph.ss.getItem("groups");
		for( p in groups ){
			groups[p].forEach(function(pg){  
				for(var i=pg.onsaleSkus.length-1; i>=0; i--){
					if( pg.onsaleSkus[i].id==skuId ) {
						pg.onsaleSkus.splice(i,1);
					}
				}
			});
		}
		nph.ss.setItem("groups", groups);
	}
	
	this.remove = function(){
		nph.ss.removeItem("onsale");
		nph.ss.removeItem("groups");
	}
}

$(function(){
	$(document).scroll(function(){
        if($(document).scrollTop() > 0){
            $('#top-btn').css('display','block');
        } else {
            $('#top-btn').css('display','none');
        }
    });
    $("#top-btn").on("click", function(){
        $('#top-btn').css('display','none');
    });
    var aid = nph.getQueryParam("aid");
	if( aid ) {
		sessionStorage.setItem( "aid", aid );
	}
    if(nph.isWechatBrowser()){
    	if( $.cookie("wid") ) {
    		var winfo = nph.ss.getItem($.cookie("wid"));
    		if(winfo){
    			if(!winfo.isSubscribe){
    				$('#slidemarginleft').show();
					$('.nph-qrcodebg').css('display', 'block');
    			}
    		}else{
    			$.getJSON("/mvc/open/api/wechat/user/isSubscribe", function(data){
        			if(data.head.state=='success'){
        				nph.ss.setItem($.cookie("wid"), {"isSubscribe":data.body});
        				if(!data.body){
        					$('#slidemarginleft').show();
        					$('.nph-qrcodebg').css('display', 'block');
        				} 
        			}else{
        				nph.alert("提示","非法访问！");
        			}
        		});
    		}
    	} else {
    		nph.getWechatUserInfo(location.href);
    	}
    }
});