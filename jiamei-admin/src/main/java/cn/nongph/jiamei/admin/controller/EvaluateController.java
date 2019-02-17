package cn.nongph.jiamei.admin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.core.domain.CoreEvaluate;
import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import cn.nongph.jiamei.core.service.CoreEvaluateService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;
import cn.nongph.jiamei.core.vo.UniversalResult;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {
	
	@Resource
	private CoreEvaluateService service;
	
	@Resource
    private CoreQuestionnaireService questionnaireService;
	
	@RequestMapping("/manage")
    public String manage( @RequestParam  Long questionnaireId,
                          Model model ){
        List<CoreEvaluate> result = service.getEvaluatesByQuestionnaire(questionnaireId);
        model.addAttribute( "evaluates", result );
        model.addAttribute( "questionnaire", questionnaireService.getQuestionnaireById(questionnaireId) );
        return "/page/EvaluateManage.jsp";
    }
	
    @RequestMapping(value="/create", method=RequestMethod.POST)
    @ResponseBody
    public UniversalResult create(@RequestParam Long qnId,
    							  @RequestParam String name,
    							  @RequestParam String rule,
    							  @RequestParam String result) {
    	CoreEvaluate eva = new CoreEvaluate();
    	eva.setQuestionnaire( new CoreQuestionnaire(qnId) );
    	eva.setName( name );
    	eva.setRule( rule );
    	eva.setResult( result );

    	service.createEvaluate(eva);
    	return UniversalResult.createSuccessResult();
    }
    
    @RequestMapping(value="/modify", method=RequestMethod.POST)
    @ResponseBody
    public UniversalResult modify(@RequestParam Long id,
    							  @RequestParam String name,
								  @RequestParam String rule,
								  @RequestParam String result) {
    	CoreEvaluate eva = service.getEvaluateById(id);
    	eva.setName( name );
    	eva.setRule( rule );
    	eva.setResult( result );
    	service.modifyEvaluate( eva );

    	return UniversalResult.createSuccessResult();
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public UniversalResult getQuestion(@RequestParam Long id) {
    	CoreEvaluate eva = service.getEvaluateById(id);
    	
    	EvaluateVO vo = new EvaluateVO();
    	vo.setId( eva.getId() );
    	vo.setName( eva.getName() );
    	vo.setRule( eva.getRule() );
    	vo.setResult( eva.getResult() );
    	
    	return UniversalResult.createSuccessResult( vo );
    }
}

class EvaluateVO{
	private Long id;
	private String name;
	private String rule;
    private String result;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}

