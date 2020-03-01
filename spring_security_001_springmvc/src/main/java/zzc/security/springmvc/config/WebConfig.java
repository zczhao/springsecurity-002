package zzc.security.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import zzc.security.springmvc.interceptor.SimpleAuthentionInterceptor;

/**
 * ServletContext配置
 *
 */
@Configuration // 相当于springmvc.xml文件
@EnableWebMvc
@ComponentScan(basePackages = "zzc.security.springmvc", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) })
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private SimpleAuthentionInterceptor simpleAuthentionInterceptor;

	// 视图解析器
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(simpleAuthentionInterceptor).excludePathPatterns("/", "/login", "/logout");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
	}

}
