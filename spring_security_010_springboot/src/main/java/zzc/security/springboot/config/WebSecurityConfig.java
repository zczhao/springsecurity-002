package zzc.security.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// 密码编码器
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 安全拦截机制
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // 关闭CSRF
			.authorizeRequests()
			.antMatchers("/r/r1").hasAuthority("p1")
			.antMatchers("/r/r2").hasAuthority("p2")
			.antMatchers("/r/**").authenticated() // 所有/r/**的请求必须认证通过
			.anyRequest().permitAll() // 除了/r/**，其它的请求可以访问
			.and()
				.formLogin() // 允许表单登录
				.loginPage("/login-view") // 指定自定义的登录页，sprirng security以重定向方式跳转到/login-view
				.loginProcessingUrl("/login") // 指定登录处理的URL，也就是用户名，密码表单提交的目的路径
				.successForwardUrl("/login-success") // 指定登录成功后的跳转URL
				.permitAll() // 必须允许所有用户访问登录页（例如为验证的用户），这个formLogin().permitAll()方法允许任意用户访问基于表单登录的所有的URL
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login-view?logout"); // 跳转到 /login-view?logout
	}

}

