package zzc.springcloud.auth.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import zzc.springcloud.auth.handler.CustomAuthenticationProvider;
import zzc.springcloud.auth.service.CustomUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 认证管理器
	 */
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
		
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	/**
	 * 密码编码器
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 将自定义的CustomAuthenticationProvider装配到AuthenticationManagerBuilder
		auth.authenticationProvider(customAuthenticationProvider);
		// 将自定的CustomUserDetailsService装配到AuthenticationManagerBuilder
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	
	/**
	 * 安全拦截机制
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors() //开启跨域
		.and()
		.csrf().disable() // 关闭csrf防护
		.httpBasic().authenticationEntryPoint(authenticationEntryPoint()) // 未登录
		.and()
		.authorizeRequests()
			 // 匿名请求：不需要进行登录拦截的url
			.antMatchers("/login").permitAll() // 都可以访问
			.anyRequest().authenticated() //其他的路径都是登录后才可访问
			.and()
			// 登录配置
			.formLogin()
			.successHandler(authenticationSuccessHandler())//登录成功处理
			.failureHandler(authenticationFailureHandler())//登录失败处理
			.authenticationDetailsSource(authenticationDetailsSource) // 自定义验证逻辑，增加验证码信息
			.permitAll()
			.and()
			// 登出配置
			.logout()
			.permitAll()
			.logoutSuccessHandler(logoutSuccessHandler());
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); // 无权访问
	}

	/**
	 * 登入成功
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			
			/**
			 * 处理登入成功的请求
			 */
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("{\"status\":\"success\",\"msg\":\"登录成功\"}");
				out.flush();
				out.close();
			}
		};
	}
	
	/**
	 * 登录失败
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
			
			/**
			 * 处理登录失败的请求
			 */
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
				out.flush();
				out.close();
			}
		};
	}
	/**
	 * 登出处理
	 * @return
	 */
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler() {
			
			/**
			 * 处理登出成功的请求
			 */
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("{\"status\":\"success\",\"msg\":\"登出成功\"}");
				out.flush();
				out.close();
			}
		};
	}
	
	/**
	 * 无权访问处理
	 * @return
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			
			/**
			 * 处理无权访问的请求
			 */
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("{\"status\":\"error\",\"msg\":\"没有权限访问\"}");
				out.flush();
				out.close();
			}
		};
	}
	
	/**
	 * 未登录
	 * @return
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			
			/**
			 * 处理未登录的请求
			 */
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write("{\"status\":\"error\",\"msg\":\"无访问权限，请先登录\"}");
				out.flush();
				out.close();
			}
		};
	}
}
