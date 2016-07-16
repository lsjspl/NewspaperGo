package com.Master5.main.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.Master5.main.utils.constant.Key;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CheckPermission {

	public String name();

	public String method();

	public int state() default Key.STATE_DEFAULT_USER;
}
