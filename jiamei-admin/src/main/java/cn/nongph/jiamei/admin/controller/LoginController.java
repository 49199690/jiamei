package cn.nongph.jiamei.admin.controller;

import cn.nongph.jiamei.admin.security.PassportHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger( LoginController.class );
    
	/**
     * 完成登录操作
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse respone, @RequestParam String username, @RequestParam String password, Model model) {
    	logger.info( "{} try login", username );
    	String token = PassportHandle.doLogin(username, password, request, respone);
        if( token!=null ){
        	logger.info( "{} try login success!", username );
        	return "redirect:/page/home.jsp" ;
        } else {
        	logger.info( "{} try login fail!", username );
        	model.addAttribute( "errorMessage", "user or password error" );
        	return "/page/login.jsp";
        }    
    }
    
    /**
     * 登出
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse respone) {
    	PassportHandle.doLogout(request, respone);
        return "redirect:/page/login.jsp";
    }

    /**
     * 修改密码界面
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam(value = "oldPassword", required = false)String oldPassword, @RequestParam(value = "newPassword", required = false)String newPassword, Model model){
        //boolean result = platformUserService.changPassword(oldPassword, newPassword);
//        if(result){
//            model.addAttribute("successMessage", "修改成功！");
//        }else {
            model.addAttribute("errorMessage", "原始密码输入有误！");
//        }
        return "admin/changePassword";
    }
}
