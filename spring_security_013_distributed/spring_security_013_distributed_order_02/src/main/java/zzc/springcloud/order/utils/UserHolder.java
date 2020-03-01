package zzc.springcloud.order.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import zzc.springcloud.order.model.User;

public class UserHolder {

	public static User getUserDetail() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		return user;
	}
	
	public static String getUserId() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		User user = (User) auth.getPrincipal();
		return user.getId();
	}
	
}
