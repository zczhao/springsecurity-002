package zzc.springcloud.auth.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

/**
 * oauth_client_details：接入客户端信息
 *
 */
@Component
public class CustomClientDetailsService implements ClientDetailsService {

	private Map<String, BaseClientDetails> clientDetailsStore = new HashMap<String, BaseClientDetails>();
	{
		//clientId：主键，必须唯一，不能为空，用于唯一标识每一个客户端(client)
		String clientId = "c1"; 
		// clientSecret：必须填写，注册填写或者服务端自动生成，实际应用也有叫app_secret, 必须要有前缀代表加密方式
		String clientSecret = new BCryptPasswordEncoder().encode("secret");
		// resourceIds：不能为空，客户端能访问的资源id集合
		String resourceIds = "res1"; 
		// grantTypes：不能为空，授权码模式:authorization_code,密码模式:password,刷新token: refresh_token, 隐式模式: implicit: 客户端模式: client_credentials。支持多个用逗号分隔
		String grantTypes = "authorization_code,password,client_credentials,implicit,refresh_token";
		// scopes：不能为空，指定client的权限范围，比如读写权限
		String scopes = "all"; 
		// redirectUris：授权码模式：验证回调地址
		String redirectUris = "http://www.baidu.com"; 
		// authorities：可为空，指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要
		String authorities = null;
		BaseClientDetails clientDetails = new BaseClientDetails(clientId, resourceIds,
				 scopes,  grantTypes,  authorities,
				 redirectUris);
		clientDetails.setClientSecret(clientSecret);
		// 是否跳转到授权页面，默认跳转
		clientDetails.setAutoApproveScopes(Arrays.asList(scopes)); 
		clientDetailsStore.put("c1", clientDetails);
	}

	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetails details = clientDetailsStore.get(clientId);
		if (details == null) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}
		return details;
	}

	public void setClientDetailsStore(Map<String, BaseClientDetails> clientDetailsStore) {
		this.clientDetailsStore = new HashMap<String, BaseClientDetails>(clientDetailsStore);
	}

}
