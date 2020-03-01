package zzc.security.springmvc.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import zzc.security.springmvc.config.ApplicationConfig;
import zzc.security.springmvc.config.WebConfig;

public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * spring容器，相当于加载applicationContext.xml
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class };
	}

	/**
	 * ServletContext 相当于加载springmvc.xml
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	/**
	 * url-mapping
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
