package zzc.security.springmvc.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	public static final String SESSION_USER_KEY = "_user";
	private String id;
	private String username;
	private String password;
	private String fullname;
	private String mobile;
	private Set<String> authorities; // 用户权限
}
