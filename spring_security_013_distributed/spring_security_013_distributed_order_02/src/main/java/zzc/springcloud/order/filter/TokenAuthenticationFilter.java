package zzc.springcloud.order.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;

import zzc.springcloud.order.model.User;



@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
			Authentication userAuthentication = oauth2Authentication.getUserAuthentication();
			// 取出用户身份信息
			String principal = (String) userAuthentication.getPrincipal();
			// 用户身份信息
			User user = JSON.parseObject(principal, User.class);
			// 取出用户权限
			List<String> authoritiesList = new ArrayList<String>();
			userAuthentication.getAuthorities().stream()
					.forEach(s -> authoritiesList.add(((GrantedAuthority) s).getAuthority()));
			String[] authorities = authoritiesList.toArray(new String[authoritiesList.size()]);
	
			// 将用户信息和权限填充到用户身份token对象中
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(authorities));
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// 将authentication保存进安全上下文
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		filterChain.doFilter(request, response);
	}

}
