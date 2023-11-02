package com.learning.bookcatalog.config;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

@Component
public class DataSourceProxyBeanProcessor implements BeanPostProcessor{
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof DataSource) {
			ProxyFactory proxyFactory = new ProxyFactory(bean);
			
			proxyFactory.setProxyTargetClass(true);
			proxyFactory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
			
			return proxyFactory.getProxy(); 
		}
		
		return bean;
	}
	
	private static class ProxyDataSourceInterceptor implements MethodInterceptor {
		private final DataSource dataSource;
			
		public ProxyDataSourceInterceptor(final DataSource dataSource) {
			super();
			this.dataSource = (DataSource) ProxyDataSourceBuilder
				.create(dataSource)
				.countQuery()
				.logQueryBySlf4j(SLF4JLogLevel.INFO)
				.build();
		}

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			 Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), invocation.getMethod().getName());
			 
			 if (proxyMethod != null) {
				 return proxyMethod.invoke(dataSource, invocation.getArguments());
			 }
			 
			 return invocation.proceed();
		}
		
	}
}
