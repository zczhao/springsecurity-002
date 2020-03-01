package zzc.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication_9001 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EurekaServerApplication_9001.class, args);
	}

}
