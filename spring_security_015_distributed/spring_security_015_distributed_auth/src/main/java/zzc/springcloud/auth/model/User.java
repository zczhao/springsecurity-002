package zzc.springcloud.auth.model;

import lombok.Data;

@Data
public class User {

	private String id;
	private String username;
	private String password;
	private String fullname;
	private String mobile;
}
