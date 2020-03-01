package zzc.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApplication_9004 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayApplication_9004.class, args);
	}

}
