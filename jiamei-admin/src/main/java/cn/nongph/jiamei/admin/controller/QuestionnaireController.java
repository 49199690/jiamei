package cn.nongph.jiamei.admin.controller;

import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import cn.nongph.jiamei.core.service.CoreEvaluateService;
import cn.nongph.jiamei.core.service.CoreQuestionService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;
import cn.nongph.jiamei.core.utils.DateUtils;
import cn.nongph.jiamei.core.vo.UniversalResult;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Resource
    private CoreQuestionnaireService service;
    
    @Resource
    private CoreQuestionService questionService;
    
    @Resource
	private CoreEvaluateService evaluateService;
    
    @RequestMapping("/manage")
    public String manage(HttpServletRequest request,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false) String name,
                         Model model){
        PageBounds pageBounds = new PageBounds(page, 30);

        PageList<CoreQuestionnaire> questionnairePage = service.findQuestionnaire(pageBounds ,name);
        model.addAttribute("questionnaire", questionnairePage);
        return "/page/QuestionnaireManage.jsp";
    }

    @RequestMapping("/add")
    public String add(){
        return "/page/QuestionnaireAdd.jsp";
    }

    @RequestMapping(value="/add/save", method = RequestMethod.POST)
    public String addSave(HttpServletRequest request,
                          @RequestParam String name,
                          @RequestParam String desc){
        CoreQuestionnaire q = new CoreQuestionnaire();
        q.setState( CoreQuestionnaire.STATE.PREPARE.name() );
        q.setCreateTime( new Date() );
        q.setName( name );
        q.setDesc( desc );
        service.createQuestionnaire(q);

        return "redirect:/mvc/questionnaire/manage";
    }

    @RequestMapping("/modify")
    public String modify(@RequestParam Long id, Model model){
        model.addAttribute("q",service.getQuestionnaireById(id));
        return "/page/QuestionnaireModify.jsp";
    }

    @RequestMapping(value="/modify/save", method = RequestMethod.POST)
    public String modifySave(HttpServletRequest request,
                          @RequestParam Long id,
                          @RequestParam String name,
                          @RequestParam String desc){
        CoreQuestionnaire q = service.getQuestionnaireById(id);
        q.setName( name );
        q.setDesc( desc );
        service.updateQuestionnaire(q);

        return "redirect:/mvc/questionnaire/manage";
    }
    
    @RequestMapping(value="/copy", method = RequestMethod.POST)
    @ResponseBody
    public UniversalResult copy(@RequestParam Long id) {
    	CoreQuestionnaire qn = service.getQuestionnaireById(id);
    	qn.getQuestions().forEach( q -> q.getOptions() );
    	qn.getEvaluates();
    	
    	qn.setId( null );
    	qn.setName( qn.getName() + "-" + DateUtils.formateDateTime( new Date() ));
    	qn.setState( CoreQuestionnaire.STATE.PREPARE.name() );
        qn.setCreateTime( new Date() );
        service.createQuestionnaire( qn );
        
        qn.getQuestions().forEach( q->{
        	q.setId( null );
        	q.setQuestionnaire( qn );
        	q.getOptions().forEach( o->{
        		o.setId( id );
        		o.setQuestion( null );
        	});
        	
        	questionService.createQuestion( q );
        });
        
        qn.getEvaluates().forEach( e->{
        	e.setId( null );
        	e.setQuestionnaire( qn );
        	
        	evaluateService.createEvaluate( e );
        });
        
        return UniversalResult.createSuccessResult();
    }
    
    @RequestMapping(value="/release", method = RequestMethod.POST)
    @ResponseBody
    public UniversalResult release(HttpServletRequest request,
                          @RequestParam Long id ){
        if( service.releaseQuestionnaire(id) ) {
        	return UniversalResult.createSuccessResult();
        } else {
        	return UniversalResult.createErrorResult(1001);
        }
    }
    
    @RequestMapping(value="/close", method = RequestMethod.POST)
    @ResponseBody
    public UniversalResult close( HttpServletRequest request,
    							  @RequestParam Long id ){
        if( service.closeQuestionnaire(id) ) {
        	return UniversalResult.createSuccessResult();
        } else {
        	return UniversalResult.createErrorResult(1001);
        }
    }
    
    @ResponseBody
    @RequestMapping("/unclosed")
    public UniversalResult getUnclosed() {
    	return UniversalResult.createSuccessResult( service.getUnclosedQuestionnaire().stream().map( q->{
    		QuestionnaireVO vo = new QuestionnaireVO();
    		vo.setId( q.getId() );
    		vo.setName( q.getName() );
    		return vo;
    	} ).toArray() );
    }
    
    @RequestMapping(value="/next", method = RequestMethod.POST)
    @ResponseBody
    public UniversalResult next( HttpServletRequest request,
    							 @RequestParam Long id,
    							 @RequestParam Long nextId){
    	service.setNextVersion(id, nextId);
        return UniversalResult.createSuccessResult();
    }
    
    public static class QuestionnaireVO{
    	private Long id;
    	private String name;
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
    }
}
