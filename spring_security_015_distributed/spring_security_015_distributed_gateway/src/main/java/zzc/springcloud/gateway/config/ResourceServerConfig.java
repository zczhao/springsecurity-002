package zzc.springcloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *  资源服务配置
 *
 */
@Configuration
public class ResourceServerConfig {
	
	public static final String RESOURCE_ID = "res1";
	
	@Autowired
	private TokenStore tokenStore;
	
	@Configuration
	@EnableResourceServer
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class AuthServerConfig extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID)
			.tokenStore(tokenStore)
			.stateless(true);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/auth/**").permitAll();
		}
	}
	
	/**
	   *  订单服务
	 *
	 */
	@Configuration
	@EnableResourceServer
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class OrderServerConfig extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID)
			.tokenStore(tokenStore)
			.stateless(true);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");
		}
	}
	
	// 配置其他资源服务...
}
