package zzc.springcloud.auth.model;

import lombok.Data;

@Data
public class Permission {
	private String id;
	private String code;
	private String description;
	private String url;
}
