package zzc.springcloud.order.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import zzc.springcloud.common.model.UserDTO;
import zzc.springcloud.common.utils.EncryptUtil;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 解析header中的token
		String token = request.getHeader("json-token");
		if (Objects.nonNull(token)) {
			token = EncryptUtil.decodeUTF8StringBase64(token);
			System.out.println("token = " + token);
			// 将token转为json对象
			JSONObject jsonObject = JSON.parseObject(token);
			// 用户身份信息
			UserDTO userDTO = JSON.parseObject(jsonObject.getString("principal"), UserDTO.class);
			// 用户权限
			JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
			String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
			// 将用户信息和权限填充到用户身份token对象中
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDTO, null, AuthorityUtils.createAuthorityList(authorities));
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// 将authentication保存进安全上下文
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
