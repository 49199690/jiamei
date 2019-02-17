var questionnaireBody = '<li id="#{questionnaireId}">'+
'<div class="good-list-wrap">'+
	'<a href="/questionnaire.html?id=#{questionnaireId}">'+
        '<div class="good-title"><p class="good-name">#{questionnaireName}</p></div>'+
    '</a>'+
'</div>'+
'</li>';

function createSkuNode(q){			
	var questionnaire = questionnaireBody.replace(/#{questionnaireId}/g, q.id);
	questionnaire = questionnaire.replace("#{questionnaireName}", q.name);	
	$("#questionnaireList").append( questionnaire );
}

$(function() {	
	$.getJSON("/mvc/close/questionnaire/usable", function(data){
		if( data.head.state=='success' ) {
			data.body.forEach(q=>createSkuNode(q));
		}
	});
	
	$("#slidemarginleft").on('touchmove', function() {
		var $marginlefty = $(this);
		$marginlefty.animate({
			marginLeft: $marginlefty.outerWidth()
		});
	});
	$("#focus").click(function() {
		$('.nph-qrcodebg').css('display', 'block');
	});
	$("#close").click(function() {
		$('.nph-qrcodebg').css('display', 'none');
	});
});