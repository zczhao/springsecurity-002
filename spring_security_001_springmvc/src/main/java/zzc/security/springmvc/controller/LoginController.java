package zzc.security.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.security.springmvc.model.AuthenticationRequest;
import zzc.security.springmvc.model.UserDTO;
import zzc.security.springmvc.service.AuthenticationService;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = "/login", produces = { "text/plain;charset=UTF-8" })
	public String login(AuthenticationRequest authenticationRequest, HttpSession session) {
		UserDTO userDto = authenticationService.authentication(authenticationRequest);
		session.setAttribute(UserDTO.SESSION_USER_KEY, userDto);
		return userDto + "登录成功";
	}

	@GetMapping(value = "/logout", produces = { "text/plain;charset=UTF-8" })
	public String logout(HttpSession session) {
		session.invalidate();
		return "退出成功";
	}

	@GetMapping(value = "/r/r1", produces = { "text/plain;charset=UTF-8" })
	public String r1(HttpSession session) {
		String fullname = null;
		Object object = session.getAttribute(UserDTO.SESSION_USER_KEY);
		if (object == null) {
			fullname = "匿名";
		} else {
			UserDTO userDto = (UserDTO) object;
			fullname = userDto.getFullname();
		}
		return fullname + "访问资源r1";
	}
	
	@GetMapping(value = "/r/r2", produces = { "text/plain;charset=UTF-8" })
	public String r2(HttpSession session) {
		String fullname = null;
		Object object = session.getAttribute(UserDTO.SESSION_USER_KEY);
		if (object == null) {
			fullname = "匿名";
		} else {
			UserDTO userDto = (UserDTO) object;
			fullname = userDto.getFullname();
		}
		return fullname + "访问资源r2";
	}

}
