package zzc.springcloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;
	
	@Autowired
	private AuthenticationManager authenticationManager;
		
	/**
	 * 配置客户端详细信息服务，使用内存方式
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // 使用in-memory存储
				.withClient("c1")// (必须的)用来标识客户的id
				.secret(new BCryptPasswordEncoder().encode("secret"))// (需要值得信任的客户端)客户端密钥，
				.resourceIds("res1")// 资源列表
				.authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit",
						"refresh_token") // 此客户端可以使用的授权类型，默认为空
				.scopes("all") // 允许的授权范围，用来限制客户端的访问范围，如果为空(默认)的话，那么客户端拥有全部的访问范围
				.autoApprove(false) // 跳转到授权页面
				.redirectUris("http://www.baidu.com"); // 验证回调地址
				// authorities //此客户端可以使用的权限(基于Spring Security authorities)
			
	}
	
	
	/**
	 * 令牌管理服务
	 * @return
	 */
	@Bean
	public AuthorizationServerTokenServices tokenServices() {
		DefaultTokenServices service = new DefaultTokenServices();
		service.setClientDetailsService(clientDetailsService); // 客户端信息服务
		service.setSupportRefreshToken(true); // 是否产生刷新令牌
		service.setTokenStore(tokenStore);// 令牌存储策略
		service.setAccessTokenValiditySeconds(7200); // 令牌(access_token)默认有效期2小时
		service.setRefreshTokenValiditySeconds(259200); // 刷新令牌(refresh_token)默认有效期3天
		return service;
	}

	/**
	 * 令牌访问端点
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.authenticationManager(authenticationManager) // 密码模式需要
		.authorizationCodeServices(authorizationCodeServices) // 授权码模式需要
		.tokenServices(tokenServices()) // 令牌管理服务
		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); // 允许请求方式
	}
	
	/**
	 * 设置授权码模式的授权码如何存取，采用内存方式
	 * @return
	 */
	@Bean
	public AuthorizationCodeServices authorizationCodeServices () {
		return new InMemoryAuthorizationCodeServices();
	}
	
	/**
	 * 令牌访问端点安全策略
	 *  /oauth/authorize 授权端点
	 *  /oauth/token  令牌端点
	 *  /oauth/confirm_access 用户确认授权提交端点
	 *  /oauth/error  授权服务错误信息端点
	 *  /oauth/check_token 用于资源服务访问的令牌解析端点
	 *  /oauth/token_key 提供公有密匙的端点，如果使用JWT令牌的话。
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()") // /oauth/token_key
			.checkTokenAccess("permitAll()") // /oauth/check_token 
			.allowFormAuthenticationForClients(); // 表单认证(申请令牌)
	}

}
