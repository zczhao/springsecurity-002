package zzc.springcloud.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	// 和spring_security_012_distributed_auth的zzc.springcloud.auth.config.AuthorizationServer.configure(ClientDetailsServiceConfigurer)资源列表配置的一致
	public static final String RESOURCE_ID = "res1";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID) // 资源id
		.tokenServices(tokenServices()) // 验证令牌的服务
		.stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**").access("#oauth2.hasScope('all')")
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * 资源服务令牌解析服务
	 * @return
	 */
	@Bean
	public ResourceServerTokenServices tokenServices() {
		// 使用远程服务请求授权服务器校验token,必须指定校验token的url、client_id、client_secret
		RemoteTokenServices services = new RemoteTokenServices();
		services.setCheckTokenEndpointUrl("http://localhost:9002/oauth/check_token");
		services.setClientId("c1");
		services.setClientSecret("secret");
		return services;
	}
	

}
