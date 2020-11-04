package tn.esprit.spring.controller;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControlEntrepriseTest {
	@Autowired
	private EntrepriseServiceImpl es;
	@Autowired
	private EntrepriseRepository er;
	@Autowired
	private DepartementRepository dr;
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	private static final Logger logger = Logger.getLogger(RestControlEntrepriseTest.class);
	private static Entreprise entreprise=new Entreprise();
	private static Departement departement=new Departement();
	
	@BeforeClass
	 public static void setUp() {
		
		
	}
	@Test
	public void ajouterEntrepriseTest()
	{
		logger.info("In Test:ajouterEntreprise() : ");
		entreprise.setId(er.findTopByOrderByIdDesc().getId()+1);
		entreprise.setName("hello");
		entreprise.setRaisonSocial("bla");
		assertEquals("L'entreprise n'etait pas bien ajouté",this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterEntreprise" ,entreprise,
				String.class),String.valueOf(entreprise.getId()));
		logger.info("Out Of Test:ajouterEntreprise() : ");
		System.out.println();
		//System.out.println(ec.ajouterEntreprise(entreprise));
	}
	@Test
	public void ajouterDepartement() {
		logger.info("In Test:ajouterDepartement() : ");
		departement.setEntreprise(entreprise);
		departement.setId(dr.findTopByOrderByIdDesc().getId()+1);
		departement.setName("départementX");
		
		logger.info("Out of Test:ajouterDepartement() : ");
	}
}
