package com.rest.webservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Aspect
@Configuration
@Log4j2
public class UserAOP {

	/**
	 * Before executing any method in the service classes 
	 */
	@Before("execution(* com.rest.webservice.services.*.*(..))")
	public void beforeServiceClassMethod(JoinPoint joinPoint) {
		log.info("============= START - Before Invoking A Service Class Method =============");
		System.out.println("Join point: " + joinPoint);
		System.out.println("Signature: " + joinPoint.getSignature());
		System.out.println("Arguments: ");
		for( Object obj : joinPoint.getArgs() ) {
			System.out.println(obj.toString());
		}
		log.info("============= END - Before Invoking A Service Class Method =============");
	}

	/**
	 * After returning from any method in the service classes 
	 */
	@AfterReturning(value = "execution(* com.rest.webservice.services.*.*(..))", returning = "results")
	public void afterReturningServiceClassMethod(JoinPoint joinPoint, Object results) {
		log.info("============= START - AfterReturning A Service Class Method =============");
		System.out.println("Join point: " + joinPoint);
		System.out.println("Signature: " + joinPoint.getSignature());
		System.out.println("Arguments: ");
		for( Object obj : joinPoint.getArgs() ) {
			System.out.println(obj.toString());
		}
		log.info("============= END - AfterReturning A Service Class Method =============");
	}

	/**
	 * Around any method in the service classes 
	 * @throws Throwable 
	 */
	@Around(value = "execution(* com.rest.webservice.services.*.*(..))")
	public Object aroundServiceClassMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		log.info("============= START - Around Invoking A Service Class Method =============");
		System.out.println("-- Method details --");
		System.out.println("Signature: " + proceedingJoinPoint.getSignature());
		System.out.println("Arguments: ");
		for( Object obj : proceedingJoinPoint.getArgs() ) {
			System.out.println(obj.toString());
		}
		
		System.out.println("Calculating consumed time by for the execution");
        long startTime = System.currentTimeMillis();

        // Proceed the method execution
        Object object = proceedingJoinPoint.proceed();
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Consumed time for the exection: " + (endTime - startTime) + " ms");
		log.info("============= END - Around Invoking A Service Class Method =============");
		
		// Return the result of execution
		return object;
	}
}
