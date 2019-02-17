var BaiduMap = new function(){
	var android = function(){
		return !!navigator.userAgent.match(/Android|Adr/i);
	}
	
	var ios = function(){
		return !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
	}
	
	this.positionMap = function(l, c){
		if( android() ) {
			location.href = "bdapp://map/marker?location="   +l+"&title=社区位置&content="+c+"&src=nongph|admin";
		} else if( ios() ){
			location.href = "baidumap://map/marker?location="+l+"&title=社区位置&content="+c+"&src=nongph|admin";
		} else {
			//window.open("http://api.map.baidu.com/marker?location="+l+"&title=社区位置&content="+c+"&output=html&src=nongph|admin");
			window.open("http://map.baidu.com/?latlng="+l);
		}	 
	}
	
	this.searchMap = function(c){
		if( android() ) {
			location.href = "bdapp://map/place/search?region=suzhou&src=nongph|admin&query="+c;
		} else if( ios() ){
			location.href = "baidumap://map/place/search?region=suzhou&src=nongph|admin&query="+c;
		} else {
			window.open("http://api.map.baidu.com/place/search?region=苏州&output=html&src=nongph|admin&query="+c);
		}	 
	}
}