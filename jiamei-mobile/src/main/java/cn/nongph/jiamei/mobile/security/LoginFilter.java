package cn.nongph.jiamei.mobile.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.nongph.jiamei.core.domain.CoreUser;
import cn.nongph.jiamei.core.service.CoreUserService;
import cn.nongph.jiamei.mobile.util.CookieUtil;

@WebFilter(urlPatterns={"/mvc/close/*"}, 
initParams={@WebInitParam(name="excludeURL", value="/mvc/close/user/login,"
		+ "/mvc/close/user/login2,"
		+ "/mvc/close/user/loginState,"
		+ "/mvc/close/questionnaire/usable,"
		+ "/mvc/close/pay/wechat/notify")})
public class LoginFilter implements Filter{
	
	private List<String> excludeURL = new ArrayList<String>();
	
	private CoreUserService userServcie;
	
    public boolean isExcludedURI(HttpServletRequest request) {
    	return excludeURL.contains( request.getRequestURI() );
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String contextPath = filterConfig.getServletContext().getContextPath();
		for( String url : filterConfig.getInitParameter( "excludeURL" ).split(", ?") )
			excludeURL.add( contextPath + url );
		userServcie = (CoreUserService)WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext()).getBean( CoreUserService.class );
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		if( isExcludedURI( req ) ) {
			chain.doFilter(request, response);
		} else {
			String pid =   CookieUtil.getCookie(req, "pid");
			String phone = req.getHeader( "phone");
			CoreUser u = null;
			if( StringUtils.isNotBlank( pid ) ) 
				u = userServcie.getUserByPid( pid );
			else if( StringUtils.isNoneBlank( phone ) ){
				u = userServcie.findUserByPhone(phone);
			}
			
			if( u!=null ){
				request.setAttribute( "current_user", u);
				chain.doFilter(request, response);
			} else {
				response.getOutputStream().write("{head:{state:\"error\",code:201}}".getBytes());
			}
		}
	}

	@Override
	public void destroy() {
		excludeURL.clear();
	}
}
