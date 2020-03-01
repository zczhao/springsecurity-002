package zzc.security.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 配置自定义跳转页面
		registry.addViewController("/").setViewName("redirect:/login-view");
		registry.addViewController("/login-view").setViewName("login");
	}
}
