package cn.nongph.jiamei.admin.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns={"/mvc/*", "/page/*"}, initParams={@WebInitParam(name="excludeURL", value="/page/login.jsp,/mvc/login")})
public class URLPermissionsFilter implements Filter {
	
	private String loginPagePath = null;
	
	private List<String> excludeURL = new ArrayList<String>();
	
    public boolean isExcludedURI(HttpServletRequest request) {
    	return excludeURL.contains( request.getRequestURI() );
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String contextPath = filterConfig.getServletContext().getContextPath();
		loginPagePath = contextPath + "/page/login.jsp";
		for( String url : filterConfig.getInitParameter( "excludeURL" ).split(", ?") )
			excludeURL.add( contextPath + url );
		
		PassportHandle.init( filterConfig.getServletContext() );
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if( isExcludedURI( (HttpServletRequest)request) ) {
			chain.doFilter(request, response);
		} else {
			if( PassportHandle.getCurrentUser( (HttpServletRequest)request ) == null ){
				String requestType = ((HttpServletRequest)request).getHeader("X-Requested-With");
				if( "XMLHttpRequest".equals( requestType )  ) {
					((HttpServletResponse)response).setHeader("Content-Type", "application/json;charset=UTF-8");
					response.getWriter().write("{\"head\":{\"state\":\"error\",\"code\":0}}");
				} else {
					( (HttpServletResponse)response ).sendRedirect( loginPagePath );
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		excludeURL.clear();
	}
}
