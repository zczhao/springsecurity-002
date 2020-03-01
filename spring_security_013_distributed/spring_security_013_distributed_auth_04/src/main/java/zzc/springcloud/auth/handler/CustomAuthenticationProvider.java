package zzc.springcloud.auth.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import zzc.springcloud.auth.service.CustomUserDetailsService;

/**
 * 自定义SpringSecurity的认证器
 *
 */
@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 用户输入的用户名
		String username = authentication.getName();
		// 用户输入的密码
		String password = authentication.getCredentials().toString();
		System.out.println("username=[" + username + "] password=[" + password + "]");
		
		// 通过自定义的CustomUserDetailsService，以用户输入的用户名查询用户信息
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		if (!password.equals("123456")) {
			throw new BadCredentialsException("密码错误");
		}		
		Object principalToReturn = userDetails;
		// 将用户信息塞到SecurityContext中，方便获取当前用户信息
		return this.createSuccessAuthentication(principalToReturn, authentication, userDetails);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
