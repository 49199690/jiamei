package cn.nongph.jiamei.admin.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.nongph.jiamei.admin.domain.AdminRole;

public class RoleInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		if( handler instanceof HandlerMethod ) {
			HandlerMethod hm = (HandlerMethod)handler;
			RequiredRole rq = hm.getMethod().getAnnotation(RequiredRole.class);
			if( rq!=null && rq.value()!=null && rq.value().length>0 ) {
				for( AdminRole.KEY key : rq.value() ){
					if( PassportHandle.getCurrentUser(request).canPerform(key) ){
						return true;
					}
				}
				throw new RuntimeException( "privilege reject!" );
			}
		}
		return true;
	}
}
