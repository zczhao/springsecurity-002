package zzc.springcloud.auth.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * oauth_code 授权码操作
 *
 */
public class CustomAuthorizationCodeServices extends RandomValueAuthorizationCodeServices{

	protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<String, OAuth2Authentication>();

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		System.out.println("生成授权码:" + code);
		this.authorizationCodeStore.put(code, authentication);
	}

	@Override
	public OAuth2Authentication remove(String code) {
		System.out.println("删除授权码:" + code);
		OAuth2Authentication auth = this.authorizationCodeStore.remove(code);
		return auth;
	}
}
