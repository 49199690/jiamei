package cn.nongph.jiamei.admin.controller;

import cn.nongph.jiamei.core.domain.CoreOption;
import cn.nongph.jiamei.core.domain.CoreQuestion;
import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import cn.nongph.jiamei.core.service.CoreQuestionService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;
import cn.nongph.jiamei.core.vo.UniversalResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private CoreQuestionService service;
    
    @Resource
    private CoreQuestionnaireService questionnaireService;
    
    @RequestMapping("/manage")
    public String manage(@RequestParam  Long questionnaireId,
                          Model model){
        List<CoreQuestion> result = service.getQuestionsByQuestionnaire(questionnaireId);
        model.addAttribute( "questions", result );
        model.addAttribute( "questionnaire", questionnaireService.getQuestionnaireById(questionnaireId) );
        return "/page/QuestionManage.jsp";

    }
    
    @RequestMapping(value="/create", method=RequestMethod.POST)
    @ResponseBody
    public UniversalResult create(@RequestParam Long qnId,
    							  @RequestParam Integer sequence,
    							  @RequestParam String content,
    							  @RequestParam String options) {
    	List<CoreOption> optionList = parseOptions( options );
    	
    	for( int i=0; i<optionList.size()-1; i++ ) {
    		for( int j=i+1; j<optionList.size(); j++ )
    			if( optionList.get(i).getContent().equals( optionList.get(j).getContent() ) )
    				return UniversalResult.createErrorResult(1002);
    	}
    	
    	CoreQuestion question = new CoreQuestion();
    	question.setQuestionnaire( new CoreQuestionnaire(qnId) );
    	question.setSequence( sequence );
    	question.setType( CoreQuestion.TYPE.SELECT.name() );
    	question.setContent( content );
    	question.setOptions( optionList );
    	
    	service.createQuestion( question );
    	return UniversalResult.createSuccessResult();
    }
    
    @RequestMapping(value="/modify", method=RequestMethod.POST)
    @ResponseBody
    public UniversalResult modify(@RequestParam Long id,
    							  @RequestParam Integer sequence,
								  @RequestParam String content,
								  @RequestParam String options) {
    	List<CoreOption> optionList = parseOptions( options );
    	
    	for( int i=0; i<optionList.size()-1; i++ ) {
    		for( int j=i+1; j<optionList.size(); j++ )
    			if( optionList.get(i).getContent().equals( optionList.get(j).getContent() ) )
    				return UniversalResult.createErrorResult(1002);
    	}
    	
    	CoreQuestion q = service.getQuestionById(id);
    	q.setSequence( sequence );
    	q.setContent( content );
    	
    	service.modifyQuestion( q );
    	
    	List<CoreOption> oldList = q.getOptions();
    	optionList.forEach( o->{
    		Optional<CoreOption> opt = oldList.stream().filter( oo->oo.getContent().equals(o.getContent() ) ).findAny();
    		if( opt.isPresent() ) {
    			opt.get().setSequence( o.getSequence() );
    			opt.get().setContent( o.getContent() );
    			opt.get().setScore( o.getScore() );
    			service.modifyOption( opt.get() );
    			oldList.remove( opt.get() );
    		} else {
    			o.setQuestion( q );
    			service.createOption( o );
    		}
    	});
    	oldList.forEach( oo-> service.deleteOption( oo.getId() ) );
    	return UniversalResult.createSuccessResult();
    	
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public UniversalResult getQuestion(@RequestParam Long id) {
    	CoreQuestion question = service.getQuestionById(id);
    	
    	QuestionVO vo = new QuestionVO();
    	vo.setId( question.getId() );
    	vo.setSequence( question.getSequence() );
    	vo.setType( question.getType() );
    	vo.setContent( question.getContent() );
    	 
    	question.getOptions().forEach( o->{
    		OptionVO ovo = new OptionVO();
    		ovo.setSequence( o.getSequence() );
    		ovo.setContent( o.getContent() );
    		ovo.setScore( o.getScore() );
    		 
    		vo.getOptions().add( ovo );
    	});
    	
    	return UniversalResult.createSuccessResult( vo );
    }
    
    private List<CoreOption> parseOptions(String options){
    	List<CoreOption> optionList = new ArrayList<CoreOption>();
		JSONArray ja = JSON.parseArray(options);
		for( int i=0; i<ja.size(); i++ ) {
    		JSONObject om = ja.getJSONObject( i );
    		
    		CoreOption o = new CoreOption();
    		o.setSequence( om.getString("sequence"));;
    		o.setContent( om.getString("content") );
    		o.setScore( om.getInteger("score" ));
    		optionList.add( o );
    	}
    	return optionList;
    }
}

class QuestionVO{
	private Long id;
	private Integer sequence;
	private String type;
    private String content;
    
    List<OptionVO> options = new ArrayList<>();
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<OptionVO> getOptions() {
		return options;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}

class OptionVO{
	private String content;
	private Integer score;
	private String sequence;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}