package tn.esprit.spring.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	private static Departement departementX=new Departement();
	private static Departement departementY=new Departement();
	
	@BeforeClass
	 public static void setUp() {
		/*Entreprise entreprise=new Entreprise();
		departementX=new Departement();
		departementY=new Departement();*/
		
	}
	@Test
	public void a_ajouterEntrepriseTest()
	{
		logger.info("In Test:ajouterEntreprise() : ");
		entreprise.setId(er.findTopByOrderByIdDesc().getId()+1);
		entreprise.setName("hello");
		entreprise.setRaisonSocial("bla");
		int ide=this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterEntreprise" ,entreprise,
				Integer.class);
		assertEquals("L'entreprise n'etait pas bien ajouté",ide,entreprise.getId());
		logger.info("Out Of Test:ajouterEntreprise() : ");
		System.out.println("this is entreprise expected:"+ entreprise.getId()+" found:"+ide);
		//System.out.println(ec.ajouterEntreprise(entreprise));
	}
	@Test
	public void b_ajouterDepartement() {
		logger.info("In Test:ajouterDepartement() : ");
		departementX.setId(dr.findTopByOrderByIdDesc().getId()+1);
		departementX.setName("départementX");
		int idX = this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterDepartement" ,departementX,
				Integer.class);
		assertEquals("la département n'était pas bien ajouté",idX,departementX.getId());
		System.out.println("this is DepX expected:"+ idX+" found:"+departementX.getId());
		departementY.setId(dr.findTopByOrderByIdDesc().getId()+1);
		departementY.setName("départementY");
		int idY = this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterDepartement" ,departementY,
				Integer.class);
		
		assertEquals(idY,departementY.getId());
		System.out.println("this is DepY expected:"+ idY+" found:"+departementY.getId());
		logger.info("Out of Test:ajouterDepartement() : ");
	}
	@Test 
	public void c_affecterDepartementAEntreprise() {
		logger.info("In Test:affecterDepartementAEntreprise() : ");
		ResponseEntity<String> response =this.restTemplate.exchange("http://localhost:" + port + "/SpringMVC/servlet/affecterDepartementAEntreprise/"+departementX.getId()+"/"+entreprise.getId(),
				HttpMethod.PUT,
	            new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		 /*response =this.restTemplate.exchange("http://localhost:" + port + "/SpringMVC/servlet/affecterDepartementAEntreprise/"+departementY.getId()+"/"+entreprise.getId(),
				HttpMethod.PUT,
	            new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());*/
		
		logger.info("Out of Test:affecterDepartementAEntreprise() : ");
	}
	@Test 
	public void d_getEntrepriseById() {
		logger.info("In Test:getEntrepriseById : ");
		assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/getEntrepriseById/"+entreprise.getId(),
				Entreprise.class).getId(),entreprise.getId() );
		
		logger.info("Out of Test:getEntrepriseById : ");
	}
	@Test
	public void e_getAllDepartementsNamesByEntreprise() {
		logger.info("In Test:getAllDepartementsNamesByEntreprise() : ");
		List<String> names= new ArrayList<String>();
		es.getEntrepriseById(entreprise.getId()).getDepartements().forEach(d ->{
			names.add(d.getName());
		});
		ObjectMapper mapper= new ObjectMapper();
		String json="";
		try {
			json = mapper.writeValueAsString(names);
		} catch (JsonProcessingException e) {
			logger.fatal("convertion of array list failed");
			e.printStackTrace();
		}
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/getAllDepartementsNamesByEntreprise/"+entreprise.getId(),
				String.class)).contains(json);
		logger.info("Out of getAllDepartementsNamesByEntreprise() : ");
		
	}
	
	@Test
	public void f_deleteEntrepriseById() {
		logger.info("In Test:deleteEntrepriseById() : ");
		ResponseEntity<String> response =this.restTemplate.exchange("http://localhost:" + port + "/SpringMVC/servlet/deleteEntrepriseById/"+entreprise.getId(),
				HttpMethod.DELETE,
	            new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		logger.info("Out of getAllDepartementsNamesByEntreprise() : ");
		
	}
	@Test
	public void g_deleteDepartementById() {
		logger.info("In Test:g_deleteDepartementById() : ");
		/* response =this.restTemplate.exchange("http://localhost:" + port + "/SpringMVC/servlet/deleteDepartementById/"+departementX.getId(),
				HttpMethod.DELETE,
	            new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());*/
		ResponseEntity<String> response =this.restTemplate.exchange("http://localhost:" + port + "/SpringMVC/servlet/deleteDepartementById/"+departementY.getId(),
				HttpMethod.DELETE,
	            new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		logger.info("Out of g_deleteDepartementById() : ");
		
	}
	
}
