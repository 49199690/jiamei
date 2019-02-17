package cn.nongph.jiamei.admin.controller;

import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;
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
}
