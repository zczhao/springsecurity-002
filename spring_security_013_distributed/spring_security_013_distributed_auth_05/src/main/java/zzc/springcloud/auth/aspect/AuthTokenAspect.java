package zzc.springcloud.auth.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import zzc.springcloud.auth.entity.Result;
import zzc.springcloud.auth.entity.StatusCode;

@Component
@Aspect
public class AuthTokenAspect {

	/**
	 * 处理oauth/token返回数据的格式
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
	public Object handleOauthTokenMethod(ProceedingJoinPoint pjp) throws Throwable {
		Result result = null;
		Object proceed = pjp.proceed();
		if (Objects.nonNull(proceed)) {
			ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>)proceed;
			OAuth2AccessToken body = responseEntity.getBody();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				result = Result.success(body);
			} else {
				result = Result.error(StatusCode.ERROR, "获取授权码失败");
			}
		}
		return ResponseEntity.status(200).body(result);
	}
	
	/**
	 *   处理oauth/check_token返回数据的格式
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint.checkToken(..))")
	public Map<String,Object> handleCheckTokenMethod(ProceedingJoinPoint pjp) throws Throwable {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Object proceed = pjp.proceed();
		if (Objects.nonNull(proceed)) {
			try {
				Map<String,Object> proceedMap = (Map<String, Object>) proceed;
				resultMap.put("flag", true);
				resultMap.put("code", StatusCode.OK);
				resultMap.put("message", "处理成功");
				resultMap.put("data", proceedMap);
			} catch (Exception e) {
				resultMap.put("flag", true);
				resultMap.put("code", StatusCode.ERROR);
				resultMap.put("message", "token无效");
			}
		}
		return resultMap;
	}
}
