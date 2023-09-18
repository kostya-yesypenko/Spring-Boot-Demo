package com.example.configuration;
import org.springframework.beans.factory.wiring.BeanConfigurerSupport;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class BeanWirer {    
	
	private static BeanConfigurerSupport beanConfigurerSupport = new BeanConfigurerSupport();
	static {
		beanConfigurerSupport.setBeanWiringInfoResolver(new ClassNameBeanWiringInfoResolver());
	}
	
	public static void wire(Object beanInstance) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		if(applicationContext == null) throw new IllegalStateException("Application context is null");
		beanConfigurerSupport.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
		beanConfigurerSupport.configureBean(beanInstance);
	}
}