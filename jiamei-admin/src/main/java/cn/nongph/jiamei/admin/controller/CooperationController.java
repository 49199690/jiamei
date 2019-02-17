package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.nongph.jiamei.core.domain.CoreCooperation;
import cn.nongph.jiamei.core.service.CoreCooperationService;

@Controller
@RequestMapping("/cooperation")
public class CooperationController {
	
	@Resource
	private CoreCooperationService service;
	
	@RequestMapping("/manage")
	public String employeeManage(HttpServletRequest request,Model model){
		model.addAttribute( "cooperations", service.getAllCooperations() );
		return "/page/CooperationManage.jsp";
	}
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request,Model model){
		return "/page/CooperationCreate.jsp";
	}
	
	@RequestMapping("/create/save")
	public String createSave(HttpServletRequest request,
			                 @RequestParam String name){
		CoreCooperation coop = new CoreCooperation();
		coop.setName( name );
		service.createCooperation(coop);
		return "redirect:/mvc/cooperation/manage";
	}
	
	@RequestMapping("/modify")
	public String modify(@RequestParam Long id,Model model){
		model.addAttribute( "coop", service.getCooperationById(id) );
		return "/page/CooperationModify.jsp";
	}
	
	@RequestMapping("/modify/save")
	public String modifySave(HttpServletRequest request,
							 @RequestParam Long id,	
			                 @RequestParam String name){
		CoreCooperation coop = service.getCooperationById(id);
		coop.setName( name );
		service.updateCooperation(coop);
		return "redirect:/mvc/cooperation/manage";
	}
}
