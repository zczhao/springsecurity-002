package zzc.security.springboot.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import zzc.security.springboot.dao.UserDao;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 根据账号查询用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 登录账号
		System.out.println("username = " + username);
		// 根据账号去数据库查询
		zzc.security.springboot.model.User user = userDao.getUserByUsername(username);
		if (Objects.isNull(user)) {
			// 如果用户查询不到，返回null,由provider来抛异常
			return null;
		}
		UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword())
				.authorities("p2").build();
		return userDetails;
	}
}
