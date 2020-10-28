package tn.esprit.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

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

import tn.esprit.spring.dto.MissionDTO;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.ITimesheetService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControlTimesheetTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ITimesheetService itimesheetservice;
	static ObjectMapper mapper;
	static int employeId;
	static int idmission;
	static int departementId;
	static String requestJson;
	static HttpHeaders headers;
	static String strDateDebut;
	static String strDateFin;
	
	 @BeforeClass
	 public static void setUp() {
		mapper = new ObjectMapper();
		idmission=10;
		employeId=1;
		departementId=1;
		headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    strDateDebut="28-10-2020";
	    strDateFin="03-11-2020";
	}
		@Test
		public void getAllEmployeByMissionTest() throws JsonProcessingException {
			List<Employe> employeesList = itimesheetservice.getAllEmployeByMission(idmission);
			System.out.println(employeesList);
			System.out.println(idmission);
			String json = mapper.writeValueAsString(employeesList);
			assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/getAllEmployeByMission/"+idmission,
					String.class)).contains(json);
		}	 
	
	@Test
	public void ajouterMissionTest() {
		MissionDTO mission  = new MissionDTO();
		mission.setId(idmission);
		mission.setName("mission Test "+ idmission);
		mission.setDescription("test ajouter mission");
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterMission",mission,
				String.class)).contains(String.valueOf(idmission));
	}
	
	@Test
	public void findAllMissionByEmployeJPQLTest() throws JsonProcessingException {
		List<Mission> missionsList = itimesheetservice.findAllMissionByEmployeJPQL(employeId);
		String json = mapper.writeValueAsString(missionsList);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/findAllMissionByEmployeJPQL/"+employeId,
				String.class)).contains(json);
	}
	
	
	@Test
	public void affecterMissionADepartementTest() {
	    requestJson = "{}";
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/affecterMissionADepartement/"+idmission+"/"+departementId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());  
	}
	
	@Test
	public void ajouterTimesheet() {
	    requestJson = "{}";
	    ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/ajouterTimesheet/"+idmission+"/"+departementId+"/"+strDateDebut+"/"+strDateFin, HttpMethod.POST,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	@Test
	public void validerTimesheetTest(){
	    requestJson = "{}";	   
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/validerTimesheet/"+idmission+"/"+departementId+"/"+strDateDebut+"/"+strDateFin+"/"+employeId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());  
	}
	

}
