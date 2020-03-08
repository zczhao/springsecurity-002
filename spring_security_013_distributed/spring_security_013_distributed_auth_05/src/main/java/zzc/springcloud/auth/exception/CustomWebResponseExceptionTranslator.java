package zzc.springcloud.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomOauthException(e.getMessage()));
	}

}
