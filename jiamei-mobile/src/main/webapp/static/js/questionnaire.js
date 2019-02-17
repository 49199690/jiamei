var question_totol;

function freshRemaining(){
	let c = $(":checked").length;
	$("#remaining").html(question_totol - c);
}

$(function() {
	var qid = nph.getQueryParam("id");
	var cid = nph.getQueryParam("cid");
	if( cid ) 
		sessionStorage.setItem( "cid", cid );
	if( nph.isLogined() ){
		$.getJSON("/mvc/close/questionnaire/"+qid, function(data){
			if( data.head.state=='success'){
				$("#questionnaireTitle").html( data.body.name );
				$("#desc").html( data.body.desc );
				question_totol = data.body.questions.length;
				data.body.questions.forEach(q=>{
					let question = '<div class="detail">'+
					                   '<div class="question">'+q.sequence+'.&nbsp;'+q.content+'</div><div>'
					q.options.forEach(o=>{
						question += '<div class="option">'+
	        							'<input type="radio" name="question-'+q.id+'" data-question="'+q.id+'" value="'+o.id+'">&nbsp;'+o.sequence+'.&nbsp;'+o.content+
	        		                '</div>';
					})
					question+='</div></div>';
					
					$("#questions").append(question);
				});
				freshRemaining();
				$(":radio").click(freshRemaining);
			}
		});
	} else {
		location.href="login.html?from="+encodeURIComponent("questionnaire.html"+window.location.search);;
	}
	
	$("#summitQuestionnaire").click(function(){
		if($(":checked").length<question_totol ) {
			nph.showMessage("有题目没有完成，请完成所有答题在提交！");
		} else {
			let answer = [];
			$(":checked").each(function(){
				let qid = $(this).attr("data-question");
				let oid = $(this).val();
				answer.push({questionId:qid, answer:oid});
			});
			
			$.ajax({
	        	type:"POST",
	            url: "/mvc/close/questionnaire/"+qid+"/answer",
	            data: {
	                "answer": JSON.stringify(answer),
	                "cid": sessionStorage.getItem("cid")
	            },
	            dataType: "json",
	            success: function (data) {
	                nph.hideLoading();
	                if( data.head.state=='success'){
	                	location.href = "answerdone.html?id="+data.body;
	                } else {
	                    if (data.head.code == 1001) {
	                        nph.showMessage("参数错误！");
	                    } else {
	                    	nph.showMessage(data.body);
	                    }
	                }
	            }
	        });
		}
	});
});