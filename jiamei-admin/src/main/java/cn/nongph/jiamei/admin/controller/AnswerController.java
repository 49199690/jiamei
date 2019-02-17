package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.service.CoreAnswerService;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	
	@Resource
	private CoreAnswerService service;
	
	@RequestMapping("/manage")
	public String manage( @RequestParam(required = false, defaultValue = "1") int page,
            			  @RequestParam(required = false) Long questionnaireId,
            			  Model model ) {
		PageBounds pageBounds = new PageBounds(page, 50);
		PageList<CoreAnswer>  answersPage = service.findAnswers(pageBounds, questionnaireId);
		model.addAttribute( "answers", answersPage );
		return "/page/AnswerManage.jsp";
	}
}
