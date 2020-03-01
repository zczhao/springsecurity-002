package zzc.security.springmvc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import zzc.security.springmvc.model.AuthenticationRequest;
import zzc.security.springmvc.model.UserDTO;
import zzc.security.springmvc.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	// 用户信息
	private Map<String, UserDTO> userMap = new HashMap<String, UserDTO>();
	{
		Set<String> authorities1 = new HashSet<String>();
		authorities1.add("p1");
		Set<String> authorities2 = new HashSet<String>();
		authorities2.add("p2");
		userMap.put("zhangsan", new UserDTO("1001", "zhangsan", "123456", "张三", "13188886666", authorities1));
		userMap.put("lisi", new UserDTO("1002", "lisi", "123456", "李四", "13166668888", authorities2));
	}

	@Override
	public UserDTO authentication(AuthenticationRequest authenticationRequest) {
		if (authenticationRequest == null || StringUtils.isEmpty(authenticationRequest.getUsername())
				|| StringUtils.isEmpty(authenticationRequest.getPassword())) {
			throw new RuntimeException("账号或密码为空");
		}

		UserDTO userDto = getUserDao(authenticationRequest.getUsername());
		if (userDto == null) {
			throw new RuntimeException("查询不到该用户");
		}

		if (!authenticationRequest.getPassword().equals(userDto.getPassword())) {
			throw new RuntimeException("账号或密码错误");
		}
		return userDto;
	}

	/**
	 * 模拟用户查询
	 * 
	 * @param username
	 * @return
	 */
	private UserDTO getUserDao(String username) {
		return userMap.get(username);
	}

}
