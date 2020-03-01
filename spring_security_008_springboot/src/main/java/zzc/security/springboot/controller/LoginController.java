package zzc.security.springboot.controller;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@PostMapping(value = "/login-success", produces = { "text/plain;charset=UTF-8" })
	public String loginSuccess() {
		return getUsername() + "登录成功";
	}

	@GetMapping(value = "/r/r1", produces = { "text/plain;charset=UTF-8" })
	public String r1() {
		return getUsername() + "访问资源r1";
	}

	@GetMapping(value = "/r/r2", produces = { "text/plain;charset=UTF-8" })
	public String r2() {
		return getUsername() + "访问资源r2";
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @return
	 */
	private String getUsername() {
		String username = null;
		// 得到认证通过的用户身份
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 获取用户身份
		Object principal = authentication.getPrincipal();
		if (Objects.isNull(principal)) {
			username = "匿名";
		} else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			username = userDetails.getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

}
