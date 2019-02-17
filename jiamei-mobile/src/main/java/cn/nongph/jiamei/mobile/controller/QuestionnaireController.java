package cn.nongph.jiamei.mobile.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.domain.CoreEvaluate;
import cn.nongph.jiamei.core.domain.CoreOption;
import cn.nongph.jiamei.core.domain.CoreQuestion;
import cn.nongph.jiamei.core.domain.CoreQuestionAnswer;
import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import cn.nongph.jiamei.core.domain.CoreUser;
import cn.nongph.jiamei.core.service.CoreAnswerService;
import cn.nongph.jiamei.core.service.CoreCooperationService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;
import cn.nongph.jiamei.core.vo.UniversalResult;
import cn.nongph.jiamei.mobile.vo.OptionVO;
import cn.nongph.jiamei.mobile.vo.QuestionVO;
import cn.nongph.jiamei.mobile.vo.QuestionnaireBaseVO;
import cn.nongph.jiamei.mobile.vo.QuestionnaireVO;

@RestController
@RequestMapping("/close/questionnaire")
public class QuestionnaireController {
	private Logger logger = LoggerFactory.getLogger( QuestionnaireController.class );
	
	@Resource
	private CoreQuestionnaireService service;
	
	@Resource
	private CoreAnswerService answerService;
	
	@Resource
	private CoreCooperationService cooperationService;
	
	private ExpressionParser parser = new SpelExpressionParser();
	
	@RequestMapping("/usable")
	public UniversalResult usable() {
		return UniversalResult.createSuccessResult( 
				service.getUsableQuestionnaire().stream().map( q->{
					QuestionnaireBaseVO vo = new QuestionnaireBaseVO();
					vo.setId( q.getId() );
					vo.setName( q.getName() );
					return vo;
				} ).toArray()
		);
	}
	
	@RequestMapping("/{id}")
	public UniversalResult getDetailQuestionnaire(@PathVariable Long id ) {
		CoreQuestionnaire qn = service.getQuestionnaireById(id);
		
		QuestionnaireVO vo = new QuestionnaireVO();
		vo.setId( qn.getId() );
		vo.setName( qn.getName() );
		vo.setDesc( qn.getDesc() );
		vo.setState( qn.getState() );
		
		qn.getQuestions().forEach( q->{
			QuestionVO qvo = new QuestionVO();
			qvo.setId( q.getId() );
			qvo.setSequence( q.getSequence() );
			qvo.setType( q.getType() );
			qvo.setContent( q.getContent() );
			
			q.getOptions().forEach( o->{
				OptionVO ovo = new OptionVO();
				ovo.setId( o.getId() );
				ovo.setSequence( o.getSequence() );
				ovo.setContent( o.getContent() );
				
				qvo.getOptions().add( ovo );
			});
			vo.getQuestions().add( qvo );
		});
		
		return UniversalResult.createSuccessResult( vo );
	}
	
	@RequestMapping(value="/{id}/answer", method=RequestMethod.POST)
	public UniversalResult auestionnaireAnswer(HttpServletRequest req, 
			                                   @PathVariable Long id, 
			                                   @RequestParam(required=false) Long cid,
			                                   @RequestParam String answer) {
		CoreUser user = (CoreUser)req.getAttribute("current_user");
		CoreQuestionnaire qn = service.getQuestionnaireById(id);
		
		CoreAnswer an = new CoreAnswer();
		an.setUser( user );
		an.setQuestionnaire( qn );
		an.setStartTime( new Date() );
		an.setSubmitTime( new Date() );
		if( cid!=null && 
			cooperationService.getCooperationById( cid )!=null ) {
			an.setFromType(CoreAnswer.FROM_TYPE.COOPERATION.name() );
			an.setFromId( cid );
		}
		
		an.setQuestionAnswers( new ArrayList<>() );

		JSONArray array = JSON.parseArray( answer );
		array.forEach( e->{
			JSONObject o = (JSONObject)e;
			Long qid = o.getLong( "questionId" );
			Long oid = o.getLong( "answer" );
			
			CoreQuestionAnswer qa = new CoreQuestionAnswer();
			qa.setQuestion( new CoreQuestion(qid) );
			qa.setChoice( new CoreOption(oid));
			
			an.getQuestionAnswers().add( qa );
		});
		
		an.setScore( an.getQuestionAnswers().stream().mapToInt( qa->qa.getChoice().getScore() ).sum() );
		
		answerService.createAnswer( an );
		return UniversalResult.createSuccessResult( an.getId() );
	}
	
	@RequestMapping(value="/answer/{id}")
	public UniversalResult getEvaluate(@PathVariable Long id) {
		CoreAnswer answer = answerService.getAnswerById(id);

		answer.getQuestionAnswers().add(0, new CoreQuestionAnswer() );
		
		EvaluationContext context = new StandardEvaluationContext();  
		context.setVariable("answer", answer); 
		
		if( answer.getEvaluate() == null ) {
			CoreEvaluate defaultEva = null;
			CoreEvaluate match = null;
			for( CoreEvaluate e : answer.getQuestionnaire().getEvaluates() ) {
				if( "default".equals( e.getRule() ) )
					defaultEva = e;
				else if( parser.parseExpression( e.getRule() ).getValue(context, Boolean.class) ) {
					if( match!=null )
						logger.error( "Questionnaire {}'s evaluation rule is overlapping", answer.getQuestionnaire().getId() );
					match = e;
				}
			}
			if( match==null )
				match = defaultEva;
			if( match==null )
				logger.error( "can find match evaluation for questionnaire {}'s answer {}", answer.getQuestionnaire().getId(), answer.getId() );
			else {
				answer.setEvaluate( match );
				answerService.updateEvaluate( answer );
			}
		}
		
		String result = null;
		if( answer.getEvaluate()!=null ) {
			result = answer.getEvaluate().getResult();
		} else { 
			result = "结果还未完成，请稍后查看";
		}
		
		return UniversalResult.createSuccessResult( result );
	}
}
