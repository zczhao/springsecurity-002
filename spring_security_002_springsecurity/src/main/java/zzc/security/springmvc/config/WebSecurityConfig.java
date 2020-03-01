package zzc.security.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// 定义用户信息服务（查询用户信息）
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("zhangsan").password("123456").authorities("p1").build());
		manager.createUser(User.withUsername("lisi").password("123456").authorities("p2").build());
		return manager;
	}

	// 密码编码器
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	// 安全拦截机制
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/r/r1").hasAuthority("p1")
			.antMatchers("/r/r2").hasAuthority("p2")
			.antMatchers("/login","/logout", "/js/**").permitAll() // 都可以访问
			//.antMatchers("/r/**").authenticated() // 所有/r/**的请求必须认证通过
			.and()
			.formLogin() // 基于Form表单登录验证
			.successForwardUrl("/login-success"); // 自定义登录成功的页面地址
			// .loginPage("/login").failureUrl("/login-error"); // 自定义登录界面
	}

}
