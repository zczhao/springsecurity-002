package zzc.springcloud.auth.config;

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
		String clientId = "c1";
		String clientSecret = new BCryptPasswordEncoder().encode("secret");
		String resourceIds = "res1";
		String grantTypes = "authorization_code,password,client_credentials,implicit,refresh_token";
		String scopes = "all";
		String redirectUris = "http://www.baidu.com";
		String authorities = null;
		BaseClientDetails clientDetails = new BaseClientDetails(clientId, resourceIds,
				 scopes,  grantTypes,  authorities,
				 redirectUris);
		clientDetails.setClientSecret(clientSecret);
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
