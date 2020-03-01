package zzc.springcloud.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import zzc.springcloud.order.model.User;
import zzc.springcloud.order.utils.UserHolder;

@RestController
@RequestMapping("/order")
@Api(tags = "订单相关操作", description = "OrderController")
public class OrderController {

	/**
	 * <pre>
	 * http://localhost:9003/order/r1 
	 * 	请求资源需要携带token 
	 * 	在headers增加参数名为：Authorization，值为：Bearer token值
	 * </pre>
	 * 
	 * @return
	 */
	@ApiOperation(value = "订单r1资源 ", notes = "资源r1", httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单id", paramType = "query", required = true, dataType = "int")})
	@GetMapping("/r1")
	@PreAuthorize("hasAnyAuthority('p1')") // 拥有p1权限方可访问此url
	public String r1(Integer orderId) {
		User user = UserHolder.getUserDetail();
		return user.getFullname() + "访问资源r1 orderId=[" + orderId + "]";
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
