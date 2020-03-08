package zzc.springcloud.auth.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 自定义AuthenticationDetailsSource，将HttpServletRequest注入到CustomWebAuthenticationDetails，使其能获取到请求中的验证码等其他信息
 *
 */
@Component
public class CustomAuthenticationDetailsSource
		implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new CustomWebAuthenticationDetails(request);
	}

}
