package zzc.springcloud.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@PreAuthorize("hasAnyAuthority('p1')") // 拥有p1权限方可访问此url
	public String r1() {

		return "访问资源r1";
	}

	@GetMapping("/r2")
	@PreAuthorize("hasAnyAuthority('p2')") 
	public String r2() {
		return "访问资源r2";
	}
	
	@GetMapping("/r3")
	@PreAuthorize("hasAnyAuthority('p3')")
	public String r3() {
		return "访问资源r3";
	}
}
