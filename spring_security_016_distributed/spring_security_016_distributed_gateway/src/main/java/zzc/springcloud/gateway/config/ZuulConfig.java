package zzc.springcloud.gateway.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import zzc.springcloud.gateway.filter.AuthFilter;

@Configuration
public class ZuulConfig {

	@Bean
	public AuthFilter authFilter() {
		return new AuthFilter();
	}

	@Bean
	public FilterRegistrationBean<Filter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addAllowedOrigin("*");
		config.setMaxAge(18000L);
		source.registerCorsConfiguration("/**", config);
		CorsFilter corsFilter = new CorsFilter(source);
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>(corsFilter);
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
