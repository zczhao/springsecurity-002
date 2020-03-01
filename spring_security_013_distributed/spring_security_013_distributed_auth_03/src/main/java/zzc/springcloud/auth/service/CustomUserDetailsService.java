package zzc.springcloud.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 自定义UserDetailsService，从数据库读取用户信息，实现登录验证
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	/**
	 * 根据账号查询用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 登录账号
		System.out.println("username = " + username);
		// 根据账号去数据库查询
		zzc.springcloud.auth.model.User user = new zzc.springcloud.auth.model.User(); 
		user.setId("1001");
		user.setUsername(username);
		user.setFullname("李四");
		user.setMobile("13138881254");
		user.setPassword("$2a$10$u4sXWV/RH2XQKnuub/IOX.kLToj1zc36cDx5B9MYU8fgipTn1E.Ty");
				
		// 根据用户id去数据库查询用户的权限
		List<String> permissions = new ArrayList<>();
		permissions.add("p1");
		permissions.add("p2");
		// 将permissions转为数组
		String[] permissionArray = new String[permissions.size()];
		permissions.toArray(permissionArray);
		String principal = JSON.toJSONString(user);
		// UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(permissionArray).build();
		UserDetails userDetails = User.withUsername(principal).password(user.getPassword()).authorities(permissionArray).build();
		return userDetails;
	}

}
