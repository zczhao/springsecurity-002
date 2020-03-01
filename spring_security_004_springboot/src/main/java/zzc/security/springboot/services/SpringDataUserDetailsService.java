package zzc.security.springboot.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

	/**
	 * 根据账号查询用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 登录账号
		System.out.println("username = " + username);
		// 根据账号去数据库查询
		// 这里暂时使用静态数据
		UserDetails userDetails = User.withUsername(username).password("123456").authorities("p2").build();
		return userDetails;
	}
}
