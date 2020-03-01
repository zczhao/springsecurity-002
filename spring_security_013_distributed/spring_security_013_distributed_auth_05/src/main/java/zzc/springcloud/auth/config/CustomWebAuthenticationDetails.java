package zzc.springcloud.auth.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 自定义WebAuthenticationDetails，将验证码和用户名、密码一同带入AuthenticationProvider中
 *
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = 1L;
	private final String verifyCode;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		verifyCode = request.getParameter("verifyCode");
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; verifyCode: ").append(this.getVerifyCode());
		return sb.toString();
	}

}
