package com.easyrr.EasyResponse;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface EasyRegistredAction {

	String request();
	
	/**
	 * Specify recivers of this registred action, if non then all biders will reveice 
	 * @return
	 */
	String[] receivers() default {};
}
