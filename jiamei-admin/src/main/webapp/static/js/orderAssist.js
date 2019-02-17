var phones =[], names=[], sendeePhones=[], sendeeNames=[], communitys=[],addresses=[];
if( !sessionStorage.getItem("flatUsers") ){
	$.getJSON( ctx+"/mvc/users/flatUsers", 
    	function(users) {  
		 	sessionStorage.setItem("flatUsers", JSON.stringify(users) );
    		$.getJSON(ctx+"/mvc/community/all",
    			function(comms) { 	
    				sessionStorage.setItem("allCommunitys", JSON.stringify(comms))
    				initAutocomplete();
    			}
    		);
    	}
    ); 
} else {
	initAutocomplete();
}
function initAutocomplete(){
	var filterPhone = null;
	if( $("#phone").attr('data-modifiable')=='false' ){
		filterPhone = $("#phone").val();
	}
	
	var users = JSON.parse( sessionStorage.getItem("flatUsers") );
	for( var i = 0; i <users.length; i++ ) {
		if( users[i].addressInfo==null ) 
			continue;
		var p  = users[i].userInfo['phone'];
		if( filterPhone!=null && p!=filterPhone )
			continue;
		var n  = users[i].userInfo['name'];
		var sp = users[i].addressInfo['sendeePhone'];
		var sn = users[i].addressInfo['sendee'];
		var c  = users[i].addressInfo['community'];
		var d  = users[i].addressInfo['detailAddress'];
		
		names.push(n+","+p+","+sn+","+sp+","+c+","+d);
		phones.push(p+","+n+","+sn+","+sp+","+c+","+d);
		sendeeNames.push(sn+","+sp+","+c+","+d+","+n+","+p);
		sendeePhones.push(sp+","+sn+","+c+","+d+","+n+","+p);
		communitys.push(c+","+d+","+sn+","+sp+","+n+","+p);
		addresses.push(d+","+c+","+sn+","+sp+","+n+","+p);
	}
	
	var allCommunitys = JSON.parse( sessionStorage.getItem("allCommunitys") );
	$( "#phone" ).autocomplete({source:phones});
	$( "#name" ).autocomplete({source:names});
	$( "#sendeePhone" ).autocomplete({source:sendeePhones});
	$( "#sendeeName" ).autocomplete({source:sendeeNames});
	$( "#community" ).autocomplete({source:communitys.concat(allCommunitys)});
	$( "#address" ).autocomplete({source:addresses});
}

$("#name").blur( function (e){
	var name = $("#name").val();
	if( names.indexOf(name)>-1 ) {
		var v = name.split(",");
		$("#name").val(v[0]);
		$("#phone").val(v[1]);
		$("#sendeeName").val(v[2]);
		$("#sendeePhone").val(v[3]);
		$("#community").val(v[4]);
		$("#address").val(v[5]);
	}
});
$("#phone").blur( function (e){
	var phone = $("#phone").val();
	if( phones.indexOf(phone)>-1 ) {
		var v = phone.split(",");
		$("#phone").val(v[0]);
		$("#name").val(v[1]);
		$("#sendeeName").val(v[2]);
		$("#sendeePhone").val(v[3]);
		$("#community").val(v[4]);
		$("#address").val(v[5]);
	}
});
$("#sendeeName").blur( function (e){
	var sendeeName = $("#sendeeName").val();
	if( sendeeNames.indexOf(sendeeName)>-1 ) {
		var v = sendeeName.split(",");
		$("#sendeeName").val(v[0]);
		$("#sendeePhone").val(v[1]);
		$("#community").val(v[2]);
		$("#address").val(v[3]);
		$("#name").val(v[4]);
		$("#phone").val(v[5]);
	}
});
$("#sendeePhone").blur( function (e){
	var sendeePhone = $("#sendeePhone").val();
	if( sendeePhones.indexOf(sendeePhone)>-1 ) {
		var v = sendeePhone.split(",");
		$("#sendeePhone").val(v[0]);
		$("#sendeeName").val(v[1]);
		$("#community").val(v[2]);
		$("#address").val(v[3]);
		$("#name").val(v[4]);
		$("#phone").val(v[5]);
	}
});

$("#community").blur( function (e){
	var community = $("#community").val();
	if( communitys.indexOf(community)>-1 ) {
		var v = community.split(",");
		$("#community").val(v[0]);
		$("#address").val(v[1]);
		$("#sendeeName").val(v[2]);
		$("#sendeePhone").val(v[3]);
		$("#name").val(v[4]);
		$("#phone").val(v[5]);
	}
});

$("#address").blur( function (e){
	var address = $("#address").val();
	if( addresses.indexOf(address)>-1 ) {
		var v = address.split(",");
		$("#address").val(v[0]);
		$("#community").val(v[1]);
		$("#sendeeName").val(v[2]);
		$("#sendeePhone").val(v[3]);
		$("#name").val(v[4]);
		$("#phone").val(v[5]);
	}
});