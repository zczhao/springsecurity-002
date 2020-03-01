package zzc.security.springmvc.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import zzc.security.springmvc.model.UserDTO;

/**
 *  此拦截器实现的功能：
 *  1、校验用户是否登录
 *  2、校验用户是否拥有操作权限 
 */
@Component
public class SimpleAuthentionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUrl = request.getRequestURI();
		// 在这个方法中校验用户请求的url是否在用户的权限范围内
		// 取出用户的身份信息
		Object object = request.getSession().getAttribute(UserDTO.SESSION_USER_KEY);
		if (object == null) {
			// 没有登录，提示登录
			writeContent(response, "请登录");
		}
		UserDTO userDto = (UserDTO) object;
		if (userDto.getAuthorities().contains("p1") && requestUrl.contains("/r/r1")) {
			return true;
		} else if (userDto.getAuthorities().contains("p2") && requestUrl.contains("/r/r2")) {
			return true;
		}
		writeContent(response, "没有权限，拒绝访问");
		return false;
	}

	/**
	 * 响应信息给客户端
	 * 
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void writeContent(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(msg);
		writer.close();
	}

}
