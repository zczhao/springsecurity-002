package zzc.springcloud.common.model;

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
	/**
	 * FastJson过滤字段
	 * 1、在对象对应字段前面加transient，表示该字段不用序列化，即在生成json的时候就不会包含该字段了。
	 * private transient String password;
	 * 2、在对象响应字段前加注解，这样生成的json也不包含该字段
	 * @JSONField(serialize=false)
	 * private String password;
	 */
	private transient String password;
	private String fullname;
	private String mobile;
	
	
	private Set<String> authorities; // 用户权限
}
