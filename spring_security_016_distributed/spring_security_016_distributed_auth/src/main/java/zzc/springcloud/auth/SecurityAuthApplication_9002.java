package zzc.springcloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SecurityAuthApplication_9002 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecurityAuthApplication_9002.class, args);
	}

}
