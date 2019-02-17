package cn.nongph.jiamei.admin.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.nongph.jiamei.admin.domain.AdminRole;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {
	AdminRole.KEY[] value();
}
