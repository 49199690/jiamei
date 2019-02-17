package cn.nongph.jiamei.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.domain.CoreQuestionAnswer;
import cn.nongph.jiamei.core.service.CoreAnswerService;

public class EvaluateTest extends BaseJunit4Test{
	
	@Resource
	private CoreAnswerService service;
	
	private ExpressionParser parser = new SpelExpressionParser();
	
	@Test
	public void testEva() {
		CoreAnswer answer = service.getAnswerById(2L);
		answer.getQuestionAnswers().add(0, new CoreQuestionAnswer() );
		EvaluationContext context = new StandardEvaluationContext();  
		context.setVariable("answer", answer); 
		
		System.out.println( parser.parseExpression( "#answer.questionAnswers[1].choice.sequence" ).getValue(context, String.class) ); 
		System.out.println( parser.parseExpression( "#answer.questionAnswers[1].choice.sequence=='A'" ).getValue(context, Boolean.class) ); 
		
	}
}
