package cn.nongph.jiamei.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.core.domain.CoreUser;
import cn.nongph.jiamei.admin.domain.AdminRole;
import cn.nongph.jiamei.admin.security.RequiredRole;
import cn.nongph.jiamei.core.service.CoreUserService;
import cn.nongph.jiamei.core.vo.UniversalResult;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Resource
	private CoreUserService userService;
		
	@RequestMapping("/manage")
	public String customerQuery( HttpServletRequest request, 
			                     @RequestParam(required = false, defaultValue = "1") int page,
			                     @RequestParam(required = false) String nameOrPhone,
			                     Model model ){
		 PageBounds pageBounds = new PageBounds(page, 50);
		 
		 PageList<CoreUser>  usersPage = userService.findUsers(pageBounds ,nameOrPhone);
		 model.addAttribute("users", usersPage);
		 return "/page/userManage.jsp";
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public UserInfoVO getCustomer(@PathVariable Long id ){
		CoreUser user = userService.getUserById(id);
		
		UserInfoVO vo = new UserInfoVO();
		vo.setName( user.getName() );
		vo.setPhone( user.getPhone() );
		vo.setId( user.getId() );
		
		return vo;
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@RequiredRole(AdminRole.KEY.ADMIN)
	@ResponseBody
	public UniversalResult modify( HttpServletRequest request, 
			                       @RequestParam Long id, 
								   @RequestParam String phone, 
								   @RequestParam String name ){

		CoreUser current = userService.getUserById(id);
		boolean changed = false;
		if( !current.getPhone().equals(phone) ){
			if( userService.findUserByPhone(phone)!=null ) {
				return UniversalResult.createErrorResult(1001);
			} else {
				current.setPhone(phone);
				changed = true;
			}
		}
		if( !current.getName().equals(name) ){
			current.setName(name);
			changed = true;
		}
		if( changed ) {
			userService.updateUser(current);
		}
		
		return UniversalResult.createSuccessResult();
	}
	
	public static class FlatUserVO implements Cloneable{
		private UserInfoVO userInfo;
		private AddressInfoVO addressInfo;
		
		public UserInfoVO getUserInfo() {
			return userInfo;
		}

		public void setUserInfo(UserInfoVO userInfo) {
			this.userInfo = userInfo;
		}

		public AddressInfoVO getAddressInfo() {
			return addressInfo;
		}

		public void setAddressInfo(AddressInfoVO addressInfo) {
			this.addressInfo = addressInfo;
		}
		
		public FlatUserVO clone(){
			FlatUserVO vo = new FlatUserVO();
			vo.setUserInfo( this.getUserInfo() );
			return vo;
		}
	}
	
	public static class UserInfoVO{
		Long id;
		String name;
		String phone;
		String weixin;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			if(name!=null)
				return name;
			else
				return "";
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getWeixin() {
			if( weixin!=null )
				return weixin;
			else
				return "";
		}
		public void setWeixin(String weixin) {
			this.weixin = weixin;
		}
	}
	
	public static class AddressInfoVO{
		private Long id;
		private String sendee;
		private String sendeePhone;
		private String community;
		private String detailAddress;
		private String whenDelivery;
		private String lastUsed;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getSendee() {
			if( sendee==null )
				return "";
			else
				return sendee;
		}
		public void setSendee(String sendee) {
			this.sendee = sendee;
		}
		public String getSendeePhone() {
			if( sendeePhone==null )
				return "";
			else
				return sendeePhone;
		}
		public void setSendeePhone(String sendeePhone) {
			this.sendeePhone = sendeePhone;
		}
		public String getCommunity() {
			return community;
		}
		public void setCommunity(String community) {
			this.community = community;
		}
		public String getDetailAddress() {
			return detailAddress;
		}
		public void setDetailAddress(String detailAddress) {
			this.detailAddress = detailAddress;
		}
		public String getWhenDelivery() {
			return whenDelivery;
		}
		public void setWhenDelivery(String whenDelivery) {
			this.whenDelivery = whenDelivery;
		}
		public String getLastUsed() {
			return lastUsed;
		}
		public void setLastUsed(String lastUsed) {
			this.lastUsed = lastUsed;
		}
	}
}
