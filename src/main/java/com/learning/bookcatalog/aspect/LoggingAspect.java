package com.learning.bookcatalog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	@Pointcut("execution(* com.learning.bookcatalog.web.AuthorResource.*(..))")
	private void restAPI() {
	}

	@Pointcut("within(com.learning.bookcatalog.web.*)")
	private void withinPointcutExample() {
	}

	@Pointcut("args(com.learning.bookcatalog.dto.PublisherCreateRequestDTO)")
	private void argsPointcutExample() {
	}

	@Pointcut("@args(com.learning.bookcatalog.annotation.LogThisArg)")
	private void addArgsPointcutExample() {
	}

	@Pointcut("@annotation(com.learning.bookcatalog.annotation.LogThisMethod)")
	private void annotationPointcutExample() {
	}

	@Before("restAPI() && annotationPointcutExample()")
	public void beforeExecutedLogging() {
		log.info("This is log from aspect before the method is executed");
	}

	@After("restAPI() && annotationPointcutExample()")
	public void afterExecutedLogging() {
		log.info("This is log from aspect after the method is executed");
	}

	@AfterReturning("restAPI() && annotationPointcutExample()")
	public void afterReturningLogging() {
		log.info("This is log from aspect after the method returned");
	}

	@AfterThrowing("restAPI() && annotationPointcutExample()")
	public void afterThrowingLogging() {
		log.info("This is log from aspect after the method threw an exception");
	}

	@Around("restAPI()")
	public Object processingTimeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		
		try {
			log.info("Start {}, {}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
			stopWatch.start();
			
			return joinPoint.proceed();
		} finally {
			log.info("Stop {}, {}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
			stopWatch.stop();
		}
	}
}
