package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.admin.qrcode.QRCodeParam;
import cn.nongph.jiamei.admin.qrcode.QRCodeService;
import cn.nongph.jiamei.core.service.CoreCooperationService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
	
	@Resource
	private CoreCooperationService cooperationService;
	@Resource
	private CoreQuestionnaireService questionnaireService; 
	@Resource
	private QRCodeService qrcodeService;
	
	@RequestMapping("/create")
	private String create(Long questionnaireId, Model model) {
		model.addAttribute( "questionnaire", questionnaireService.getQuestionnaireById(questionnaireId) );
		model.addAttribute( "cooperations", cooperationService.getAllCooperations() );
		return "/page/QRCodeCreate.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="/create/qrcode.png", produces="image/png")
	public byte[] qrcode(Long questionnaireId, Long cooperationId){
		QRCodeParam param = new QRCodeParam();
		param.setContent("http://jiamei.nongph.cn/questionnaire.html?id="+questionnaireId + "&cid=" +  cooperationId);
		param.setCharset("UTF-8");
		param.setFormat("png");
		param.setMargin(0);
		param.setWidth(430);
		param.setHeight(430);
		param.setLogoPath("/jiamei.jpg");
		param.setLogoCornerRadius(16);
		
		try {
			return qrcodeService.encode( param );
		} catch (Exception e) {
			return null;
		}
	}
}
