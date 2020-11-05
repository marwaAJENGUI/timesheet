package tn.esprit.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
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
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	EmployeRepository employeRepository;
	static ObjectMapper mapper;
	static int employeId;
	int idmission;
	static int departementId;
	static String requestJson;
	static HttpHeaders headers;
	static String strDateDebut;
	static String strDateFin;
	
	 @BeforeClass
	 public static void setUp() {
		mapper = new ObjectMapper();
		employeId=3;
		departementId=2;
		headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    strDateDebut="21-01-1992";
	    strDateFin="04-11-2020";
	}
	 
	 
	 @Test
	 public void testGetAllEmployeByMission() throws JsonProcessingException {
		this.setIdmission(14);
		List<Employe> employeesList = itimesheetservice.getAllEmployeByMission(idmission);
		System.out.println(employeesList);
		System.out.println(idmission);
		String json = mapper.writeValueAsString(employeesList);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/getAllEmployeByMission/"+idmission,
				String.class)).contains(json);
	}	 
	
	@Test
	public void testAjouterMission() {
		MissionDTO mission  = new MissionDTO();
		this.setIdmission(findLastIdMission()+ 1);
		mission.setName("mission Test "+ idmission);
		mission.setDescription("test ajouter mission");
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/SpringMVC/servlet/ajouterMission",mission,
				String.class)).contains(String.valueOf(idmission));
	}
	
	@Test
	public void testFindAllMissionByEmployeJPQL() throws JsonProcessingException {
		List<Mission> missionsList = itimesheetservice.findAllMissionByEmployeJPQL(employeId);
		String json = mapper.writeValueAsString(missionsList);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/findAllMissionByEmployeJPQL/"+employeId,
				String.class)).contains(json);
	}
	
	@Test
	public void testAffecterMissionADepartement() {
	    requestJson = "{}";
	    this.setIdmission(14);
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/affecterMissionADepartement/"+idmission+"/"+departementId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode()); 
	    
	}
	
	@Test
	public void testAjouterTimesheet() throws ParseException, JsonProcessingException {
		this.setIdmission(findLastIdMission());
		requestJson = "{}";
	    ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/ajouterTimesheet/"+idmission+"/"+employeId+"/"+strDateDebut+"/"+strDateFin, HttpMethod.POST,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());
	    
	    //assert that timesheet exists in the data base with expected values
	    Date dateDebut=new SimpleDateFormat("dd-MM-yyyy").parse(strDateDebut);  
	    Date dateFin=new SimpleDateFormat("dd-MM-yyyy").parse(strDateFin);  
	    TimesheetPK timesheetPK = new TimesheetPK(idmission, employeId, dateDebut, dateFin);
	    Timesheet expected=itimesheetservice.findBytimesheetPK(timesheetPK);
	    if (expected==null) {
	    	expected = new Timesheet();
		    expected.setTimesheetPK(timesheetPK);
		    Optional <Mission> opMission = missionRepository.findById(idmission);
			expected.setMission(opMission.get());
			Optional <Employe> opEmploye = employeRepository.findById(employeId);	
			expected.setEmploye(opEmploye.get());
		    expected.setValide(false);
	    } 
	    Timesheet actual =itimesheetservice.findBytimesheetPK(timesheetPK);
		
	    String expectedJson = mapper.writeValueAsString(expected);
		String actualJson = mapper.writeValueAsString(actual);
	    assertEquals(expectedJson,actualJson);    
	}

	@Test
	public void testAjouterTimesheet_INTERNAL_SERVER_ERROR() {
	    requestJson = "{}";
	    ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/ajouterTimesheet/"+(idmission+1)+"/"+employeId+"/"+strDateDebut+"/"+strDateFin, HttpMethod.POST,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());

	}
	
	@Test
	public void testValiderTimesheet() throws ParseException, JsonProcessingException{
		this.setIdmission(14);
		Date dateDebut=new SimpleDateFormat("dd-MM-yyyy").parse(strDateDebut);  
	    Date dateFin=new SimpleDateFormat("dd-MM-yyyy").parse(strDateFin);  
	    TimesheetPK timesheetPK = new TimesheetPK(idmission, employeId, dateDebut, dateFin);
	    Timesheet expected= itimesheetservice.findBytimesheetPK(timesheetPK);
	    if (expected.getEmploye().getRole()==Role.CHEF_DEPARTEMENT)	expected.setValide(true);
	    
	    requestJson = "{}";	   
		ResponseEntity<String> entity = new TestRestTemplate().exchange(
	            "http://localhost:" + this.port + "/SpringMVC/servlet/validerTimesheet/"+idmission+"/"+employeId+"/"+strDateDebut+"/"+strDateFin+"/"+employeId, HttpMethod.PUT,
	            new HttpEntity<String>(requestJson,headers),
	            String.class);
	    assertEquals(HttpStatus.OK, entity.getStatusCode());

	}

	public int getIdmission() {
		return idmission;
	}

	public void setIdmission(int idmission) {
		this.idmission = idmission;
	}
	
	public int findLastIdMission() {
		List <Mission> missionList=((List <Mission>)missionRepository.findAll());
		return missionList.get(missionList.size()-1).getId();
	}
	

}
