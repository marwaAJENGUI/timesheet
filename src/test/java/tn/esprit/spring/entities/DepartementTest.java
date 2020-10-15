package tn.esprit.spring.entities;



import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import tn.esprit.spring.config.InvocationTestTrace;
import tn.esprit.spring.controller.ControllerEntrepriseImpl;
import tn.esprit.spring.entities.Departement;

@Import(AnnotationAwareAspectJAutoProxyCreator.class) // activate aspect
@SpringBootTest	()
public class DepartementTest {
	@Resource
	Departement departement;
	@Autowired (required=true)
	ControllerEntrepriseImpl entrepriseImpl;
	private static final Logger log =	Logger.getLogger(DepartementTest.class);
/*	@BeforeAll
	public static void setUp() {
		departement=new Departement();
	}
	*/
	@Bean
    public  InvocationTestTrace loggingAspectInvocationTrace() {
        return new InvocationTestTrace();
    }
	
	@Test
	public void mainTest(){
		departement=new Departement("departementName");
//		log.info(entrepriseImpl.ajouterDepartement(departement));
	}
}
