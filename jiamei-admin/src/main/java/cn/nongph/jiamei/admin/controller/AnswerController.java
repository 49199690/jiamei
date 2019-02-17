package cn.nongph.jiamei.admin.controller;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.admin.utils.StringUtils;
import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.service.CoreAnswerService;
import cn.nongph.jiamei.core.service.CoreCooperationService;
import cn.nongph.jiamei.core.utils.DateUtils;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	private Logger logger = LoggerFactory.getLogger( AnswerController.class );
	
	@Resource
	private CoreAnswerService service;
	
	@Resource
	private CoreCooperationService cooperationService;
	
	@RequestMapping("/manage")
	public String manage( @RequestParam(required = false, defaultValue = "1") int page,
            			  @RequestParam(required = false) Long questionnaireId,
            			  @RequestParam(required = false) String startTime, 
            			  @RequestParam(required = false) String endTime, 
            			  @RequestParam(required = false) Long cooperation,
            			  Model model ) {
		PageBounds pageBounds = new PageBounds(page, 50);
		Date submitStart = null, submitEnd = null;
		if( StringUtils.isNotBlank(startTime) ) {
			try {
				submitStart = DateUtils.parseDate( startTime, "yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				logger.error("time format error", e);
			}
		}
		
		if( StringUtils.isNotBlank(endTime) ) {
			try {
				submitEnd = DateUtils.parseDate( endTime, "yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				logger.error("time format error", e);
			}
		}
		
		PageList<CoreAnswer>  answersPage = service.findAnswers(pageBounds, questionnaireId, submitStart, submitEnd, cooperation);
		model.addAttribute( "answers", answersPage );
		model.addAttribute( "cooperations", cooperationService.getAllCooperations() );
		return "/page/AnswerManage.jsp";
	}
}
