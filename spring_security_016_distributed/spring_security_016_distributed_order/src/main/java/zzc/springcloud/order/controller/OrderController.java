package zzc.springcloud.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.springcloud.common.model.UserDTO;

@RestController
public class OrderController {

	/**
	 * <pre>
	 * http://localhost:9003/r1 
	 * 	请求资源需要携带token 
	 * 	在headers增加参数名为：Authorization，值为：Bearer token值
	 * </pre>
	 * 
	 * @return
	 */
	@GetMapping("/r1")
	@PreAuthorize("hasAuthority('p1')") // 拥有p1权限方可访问此url
	public String r1() {
		// 获取用户身份信息
		UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDTO.getFullname() + "访问资源r1";
	}

	@GetMapping("/r2")
	@PreAuthorize("hasAuthority('p2')")
	public String r2() {
		return "访问资源r2";
	}

	@GetMapping("/r3")
	@PreAuthorize("hasAuthority('p3')")
	public String r3() {
		return "访问资源r3";
	}
}
