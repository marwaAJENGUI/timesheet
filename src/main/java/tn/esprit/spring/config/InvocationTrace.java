package tn.esprit.spring.config;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class InvocationTrace implements Ordered {

	private static Logger log = Logger.getLogger(InvocationTrace.class);
	Object obj;
	private int order;

	@Around("execution(* tn.esprit.spring.entities.Departement.*(..)) "
			+ "|| execution(* tn.esprit.spring.controller.RestControlTimesheet.*(..)) "
			+ "|| execution(* tn.esprit.spring.services.TimesheetServiceImpl.*(..)) "
			+ "|| execution(* tn.esprit.spring.repository.TimesheetRepository.*(..)) "
			+ "|| execution(* *Test(..))")
	public Object methodTrace(final ProceedingJoinPoint joinpoint) throws Throwable {
		String methodName = joinpoint.getTarget().getClass().getSimpleName() + "." + joinpoint.getSignature().getName();

		final Object[] args = joinpoint.getArgs();
		final StringBuilder sb = new StringBuilder();
		sb.append(joinpoint.getSignature().toString());
		sb.append(" with parameters : (");

		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		log.info("****************************************************************\n start method : " + sb);
		try {
			obj = joinpoint.proceed();
		} finally {
			log.info("****************************************************************\n  end method : " + methodName
					+ " --> returned: " + obj);
		}
		return obj;
	}

	@AfterThrowing(pointcut = "execution(* tn.esprit.spring.entities.Departement.*(..)) "
			+ "|| execution(* tn.esprit.spring.controller.RestControlTimesheet.*(..))", throwing = "exception")
	public void logExceptionTrace(final StaticPart staticPart, final Exception exception) throws Throwable {
		String methodeName = staticPart.getSignature().toLongString();
		log.error("****************************************************************\n"
				+ " Exception occurred in method :  " + methodeName, exception);
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Value("2")
	public void setOrder(final int order) {
		this.order = order;
	}

}