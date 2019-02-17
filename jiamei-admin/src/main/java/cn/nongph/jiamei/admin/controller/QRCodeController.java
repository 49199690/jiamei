package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.nongph.jiamei.core.service.CoreCooperationService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;

@RequestMapping("/qrcode")
public class QRCodeController {
	
	@Resource
	private CoreCooperationService cooperationService;
	@Resource
	private CoreQuestionnaireService questionnaireService; 
	
	@RequestMapping("/create")
	private String create(Long questionnaireId) {
		
		return "/page/QRCodeCreate.jsp";
	}
}
