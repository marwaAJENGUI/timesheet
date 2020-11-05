package tn.esprit.spring.services;

import java.util.Date;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.*;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {

	@Autowired
	EmployeServiceImpl employeserviceimpl;

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	ContratRepository contratTepository;

	/*@BeforeEach
	public void intial(){
	employeRepository.deleteAll();	
	}*/
	
	@Test
	public void should_be_able_to_add_an_empoyer() {
		// GIVEN
		Employe e = new Employe();
		e.setPrenom("Hemdani");
		e.setNom("mehdi");
		e.setEmail("mehdi@gmail.com");
		e.setPassword("123");
		e.setActif(true);
		e.setRole(Role.CHEF_DEPARTEMENT);

		// WHEN
		int id = employeserviceimpl.addOrUpdateEmploye(e);
		Optional<Employe> s = employeRepository.findById(id);
		// THEN
		assertThat(s.isPresent()).isTrue();
		assertThat(s.get().getPrenom()).isEqualTo("Hemdani");
		assertThat(s.get().getNom()).isEqualTo("mehdi");
		assertThat(s.get().getEmail()).isEqualTo("mehdi@gmail.com");
		assertThat(s.get().getPassword()).isEqualTo("123");
		assertThat(s.get().isActif()).isTrue();
		assertThat(s.get().getRole()).isEqualTo(Role.CHEF_DEPARTEMENT);

	}
	
	public void should_be_able_to_add_an_contrat(){
		// GIVEN
		Contrat c =new Contrat();
		Date currentdate = new Date();
		c.setDateDebut(currentdate);
		c.setTypeContrat("fulltime");
		c.setSalaire(200);
		//WHEN
		int id = employeserviceimpl.ajouterContrat(c);
		Optional<Contrat> e = contratTepository.findById(id);
		//then
		assertThat(e.get().getDateDebut()).isEqualTo(currentdate);
		assertThat(e.get().getTypeContrat()).isEqualTo("fulltime");
		assertThat(e.get().getSalaire()).isEqualTo(200);
			
	}
	
	
}
