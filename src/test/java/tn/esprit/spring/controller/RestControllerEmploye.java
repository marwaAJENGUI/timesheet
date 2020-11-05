package tn.esprit.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;



import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControllerEmploye {
	static int employeId;
	static int departementId;
	static String email;
	static int contratRef;
	int iddep;
	public int getIddep() {
		return iddep;
	}

	public void setIddep(int iddep) {
		this.iddep = iddep;
	}

	@BeforeClass
	 public static void setUp() {
		headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    employeId=323;
		  departementId=2;
		  email="mehdi@gmail.com";
		  contratRef=8;
	}
	
	
	static ObjectMapper mapper;
	static String requestJson;
	static HttpHeaders headers;

	@LocalServerPort
	int randomServerPort;
	private static Employe e;
	private static Entreprise t;
	private static Departement d;
	
	
	private static final Logger logger = Logger.getLogger(RestControllerEmploye.class);

	@Autowired
	private TestRestTemplate rest;

	@Autowired
	EmployeRepository employeRepository;

	@Autowired
	EmployeServiceImpl employeserviceimpl;

	@Test
	public void testAjoutEmploye() {
		logger.info( "in testAjoutEmploye");
		e = new Employe();
		e.setPrenom("Hemdani");
		e.setNom("mehdi");
		e.setEmail("mehdi@gmail.com");
		e.setPassword("123");
		e.setActif(true);
		e.setRole(Role.CHEF_DEPARTEMENT);
		e.setId(employeRepository.findTopByOrderByIdDesc().getId() + 1);
		assertThat(this.rest.postForObject(
				"http://localhost:" + randomServerPort + "/SpringMVC/servlet/ajouterEmployer", e, String.class));
		logger.info("out AjoutEmploye");

	}

	@Test
	public void testAjoutContrat() {
		logger.info( "in AjoutContrat");
		Contrat c = new Contrat();
		Date currentdate = new Date();
		c.setDateDebut(currentdate);
		c.setTypeContrat("fulltime");
		c.setSalaire(200);
		assertThat(this.rest.postForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/ajouterContrat",
				c, String.class));
		logger.info( "out AjoutContrat");
	}

	@Test
	public void testgetAllEmployes() throws JsonProcessingException {
		logger.info( "in testgetAllEmployes");
		List<Employe> employeesList = employeserviceimpl.getAllEmployes();
		System.out.println(employeesList);
		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employeesList);
		assertThat(this.rest.getForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/getAllEmployes/",
				String.class)).contains(json);
		logger.info( "out testgetAllEmployes");
	}

	@Test
	public void testgetEmployePrenomById() throws JsonProcessingException {
		logger.info( "in EmployePrenomById()");
		String employees = employeserviceimpl.getEmployePrenomById(e.getId());
		System.out.println(employees);
		System.out.println(e.getId());
		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employees);
		assertThat((json).contains(this.rest.getForObject(
				"http://localhost:" + randomServerPort + "/SpringMVC/servlet/getEmployePrenomById/" + e.getId(),
				String.class)));
		logger.info( "out EmployePrenomById()");
	}
	@Test
	public void testgetNombreEmployeJPQL() throws JsonProcessingException{
		logger.info("in testgetNombreEmployeJPQL()");
		int employees =employeserviceimpl.getNombreEmployeJPQL();
		System.out.println(employees);
		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employees);
		assertThat(this.rest.getForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/getNombreEmployeJPQL/",
				String.class)).contains(json);
		logger.info("out testgetNombreEmployeJPQL()");
		
	}
	@Test
	public void testgetAllEmployeNamesJPQL() throws JsonProcessingException{
		logger.info("in getAllEmployeNamesJPQ");
		List<String> employeesListJpql = employeserviceimpl.getAllEmployeNamesJPQL();
		System.out.println(employeesListJpql);
		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employeesListJpql);
		assertThat(this.rest.getForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/getAllEmployeNamesJPQL/",
				String.class)).contains(json);
		logger.info("out getAllEmployeNamesJPQ");
	}
	/*@Test
	public void testdeleteEmployeById(){
		ResponseEntity<String> response =this.rest.exchange("http://localhost/:" + randomServerPort + "/SpringMVC/servlet/deleteEmployeById/"+employeId,
                HttpMethod.DELETE,
                new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		
        assertEquals(HttpStatus.OK, response.getStatusCode());	
	}*/
	/*@Test
    public void testmettreAjourEmailByEmployeId(){
		e.setEmail("amine@gmail.com");
		requestJson = "{}";
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.randomServerPort + "/SpringMVC/servlet/modifyEmail/"+email+"/"+departementId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	    //when
	   
	    
    }*/
	@Test
	public void TestaffecterContratAEmploye(){
		logger.info(" in TestaffecterContratAEmploye()");
		requestJson = "{}";
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.randomServerPort + "/SpringMVC/servlet/affecterContratAEmploye/"+contratRef+"/"+employeId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	    logger.info(" out TestaffecterContratAEmploye()");
	}
	
	@Test
	public void testaffecterEmployeADepartement(){
		logger.info(" in TestaffecterContratAEmploye()");
		requestJson = "{}";
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.randomServerPort + "/SpringMVC/servlet/affecterMissionADepartement/"+employeId+"/"+departementId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	    logger.info(" out TestaffecterContratAEmploye()");
	    
	}
	@Test
	public void testdesaffecterEmployeDuDepartement(){
		logger.info("in testdesaffecterEmployeDuDepartement()");
		requestJson = "{}";
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.randomServerPort + "/SpringMVC/servlet/desaffecterEmployeDuDepartement/"+employeId+"/"+departementId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	    logger.info("out testdesaffecterEmployeDuDepartement()");
	    
	}
    
	/*@Test
	public void testdeleteAllContratJPQL(){
		logger.info(" in deleteAllContratJPQL");
		ResponseEntity<String> response =this.rest.exchange("http://localhost/:" + randomServerPort + "/SpringMVC/servlet/deleteAllContratJPQL/",
                HttpMethod.DELETE,
                new HttpEntity<String>("{}",new HttpHeaders()),String.class);
		logger.info("mahdi + ");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        logger.info(" out deleteAllContratJPQL");
	}*/
	
	@Test
	public void testgetAllEmployeByEntreprise()throws JsonProcessingException{
		logger.info("in testgetAllEmployeByEntreprise()");
		List<Employe> employeesListByEntreprise =employeserviceimpl.getAllEmployeByEntreprise(t);
		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employeesListByEntreprise);
		assertThat((json).contains(this.rest.getForObject(
				"http://localhost:" + randomServerPort + "/SpringMVC/servlet/getAllEmployeByEntreprise/" + t,
				String.class)));
		logger.info("out testgetAllEmployeByEntreprise()");
	}
	/*@Test
	public void testgetSalaireByEmployeIdJPQL() throws JsonProcessingException{
	float employeesjpql= employeserviceimpl.getSalaireByEmployeIdJPQL(employeId);
	mapper = new ObjectMapper();
	String json = mapper.writeValueAsString(employeesjpql);
	assertThat(this.rest.getForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/getSalaireByEmployeIdJPQL/"+employeId,
			String.class)).contains(json);
	}*/

   @Test
   public void testgetSalaireMoyenByDepartementId() throws JsonProcessingException{
	logger.info("in testgetSalaireMoyenByDepartementId()"); 
    Double employeessalaire =employeserviceimpl.getSalaireMoyenByDepartementId(departementId);
   /*mapper = new ObjectMapper();
	String json = mapper.writeValueAsString(employeessalaire);*/
	assertEquals(this.rest.getForObject("http://localhost:" + randomServerPort + "/SpringMVC/servlet/getSalaireMoyenByDepartementId/"+departementId,
			Double.class),employeessalaire);
	logger.info("out testgetSalaireMoyenByDepartementId()");
   }
}
