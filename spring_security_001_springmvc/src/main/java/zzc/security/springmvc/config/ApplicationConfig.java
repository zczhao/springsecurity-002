package zzc.security.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Spring容器配置
 */
@Configuration // 相当于applicationContext.xml
@ComponentScan(basePackages = "zzc.security.springmvc", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) })
public class ApplicationConfig {

	// 在些配置除了Controller的其他bean，比如：数据库连接池、事务管事器、业务bean等
}
