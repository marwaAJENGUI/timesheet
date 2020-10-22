package tn.esprit.spring.config;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MonitorPerformance implements Ordered {
  private static Logger log = Logger.getLogger(MonitorPerformance.class);
  private int order;
 
  @Around("monitorPerfPointcut()")
  public Object executer(final ProceedingJoinPoint joinpoint) throws Throwable {
    Object returnValue;
    StopWatch clock = new StopWatch(getClass().getName());
    try {
      clock.start(joinpoint.toString());
      returnValue = joinpoint.proceed();
    } finally {
      clock.stop();
      log.info("temps d'execution : " + clock.prettyPrint());
    }
    return returnValue;
  }
  
  @Pointcut("execution(* tn.esprit.spring.entities.Departement.*(..)) "
	  		+ "|| execution(* tn.esprit.spring.controller.RestControlTimesheet.*(..))")
public void monitorPerfPointcut() {
	  //Aspect pointcut
}
  
  @Override
  public int getOrder() {
    return order;
  }
 
  @Value("1")
  public void setOrder(final int order) {
    this.order = order;
  }
}