package zzc.springcloud.gateway.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import zzc.springcloud.common.utils.EncryptUtil;

/**
 * token传递拦截
 */
public class AuthFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public Object run() throws ZuulException {
		// 1、获取令牌内容
		RequestContext ctx = RequestContext.getCurrentContext();
		// 从安全上下文中拿到用户身份对象
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
			Authentication userAuthentication = oauth2Authentication.getUserAuthentication();
			// 取出用户身份信息
			Object principal = userAuthentication.getPrincipal();
			
			// 取出用户权限
			List<String> authorities = new ArrayList<String>();
			userAuthentication.getAuthorities().stream()
					.forEach(s -> authorities.add(((GrantedAuthority) s).getAuthority()));
			
			// 2、组装明文token，转发给微服务，放入header，名称为json-token
			OAuth2Request oAuth2Request = oauth2Authentication.getOAuth2Request();
			Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
			Map<String, Object> jsonToken = new HashMap<String, Object>(requestParameters);
			if (Objects.nonNull(userAuthentication)) {
				jsonToken.put("principal", principal);
				jsonToken.put("authorities", authorities);
			}
			// 把身份信息和权限信息放在json中，加入http的header中，转发给微服务
			ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));
		}
		// 无token访问网关内资源的情况，目前只有auth服务直接暴露
		return null;
	}

}
