package tn.esprit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import tn.esprit.spring.services.DepartementServiceImpl;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan({"tn.esprit.spring.config,tn.esprit.spring.controller,tn.esprit.spring.entities,tn.esprit.spring.repository,tn.esprit.spring.services"})

public class TimesheetApplication {

	@Autowired
	static DepartementServiceImpl departementServiceImpl;
	public static void main(String[] args) {
		SpringApplication.run(TimesheetApplication.class, args);
		
		System.out.println(departementServiceImpl.getAllDepartements());
		}

	

 
}
