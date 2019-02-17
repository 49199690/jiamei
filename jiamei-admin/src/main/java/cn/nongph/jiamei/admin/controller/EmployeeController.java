package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.nongph.jiamei.admin.domain.AdminUser;
import cn.nongph.jiamei.admin.service.AdminUserService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Resource
	private AdminUserService adminUserService;
	
	@RequestMapping("/manage")
	public String employeeManage(HttpServletRequest request,Model model){
		model.addAttribute( "employees", adminUserService.getAllUsers() );
		return "/page/EmployeeManage.jsp";
	}
	
	@RequestMapping("modify")
	public String modify(@RequestParam Long id, Model model){
		model.addAttribute("employee", adminUserService.getAdminUserById(id));
		model.addAttribute("allRoles", adminUserService.getAllRoles());
		return "/page/EmployeeModify.jsp";
	}
	
	@RequestMapping(value="saveModify", method=RequestMethod.POST)
	public String saveModify( @RequestParam Long id, 
			                  @RequestParam String phone, 
			                  @RequestParam Long roleId){
		AdminUser au = adminUserService.getAdminUserById( id );
		au.setMobile( phone );
		adminUserService.saveAdminUser(au);
		
		if( au.getRoleList().stream().noneMatch( r-> r.getId().equals(roleId) ) ){
			adminUserService.bindRoleToAdminUser(au.getId(), roleId);
			au.getRoleList().forEach( r-> adminUserService.cancelRoleFromAdminUser(au.getId(), r.getId()) );
		}
		return "redirect:/mvc/employee/manage";
	}
}
