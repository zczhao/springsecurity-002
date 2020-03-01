package zzc.springcloud.auth.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 异常信息统一返回 https://blog.csdn.net/c5113620/article/details/89576545
 *
 */
public class MyWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomOauthException(e.getMessage()));
	}

}
