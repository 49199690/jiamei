//含有中文的最大长度验证
function validateChineseText(maxLength, value, errorSpanId){
    var errorSpan = document.getElementById(errorSpanId);
    //清空错误信息
    errorSpan.innerText = "";

    var sum = 0;
    for(var i=0; i<value.length; i++){
        if(value.charCodeAt(i)>127){
            sum += 3;
        }else{
            sum ++;
        }
    }

    if(sum <= maxLength){
        return true;
    }

    errorSpan.innerText = "该项的最大长度不能超过"+maxLength+"个字符，中文算作三个";
    return false;
}

//含有中文的最大长度验证(中文算作两个)
function validateChineseTextForTwo(maxLength, obj, errorSpanId){
    var errorSpan = document.getElementById(errorSpanId);
    //清空错误信息
    errorSpan.innerText = "";

    //去空格
    obj.value = obj.value.trim();

    var value = obj.value;

    var sum = 0;
    for(var i=0; i<value.length; i++){
        if(value.charCodeAt(i)>127){
            sum += 2;
        }else{
            sum ++;
        }
    }

    if(sum <= maxLength){
        return true;
    }

    errorSpan.innerText = "该项的最大长度不能超过"+maxLength+"个字符，中文算作两个";

    return false;
}

//去空格
function trimText(obj){
    obj.value = obj.value.trim();
}

var spinLoading;
function showLoading(){
	if( !spinLoading )
		spinLoading = new Spinner();
	var target = document.getElementById('content');
	spinLoading.spin(target);
}
function closeLoading(){
	if( spinLoading )
		spinLoading.stop();
}
function disableSubmit(){
	$("form :button").attr("disabled","disabled");  
}
function enableSubmit(){
	$("form :button").removeAttr("disabled");  
}
