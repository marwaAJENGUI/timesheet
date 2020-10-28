package tn.esprit.spring.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.services.ITimesheetService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimesheetGetAllEmployeByMissionTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ITimesheetService itimesheetservice;
	int idmission;
	
	@Test
	public void getAllEmployeByMissionTest() throws JsonProcessingException {
		List<Employe> employeesList = itimesheetservice.getAllEmployeByMission(idmission);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(employeesList);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/findAllMissionByEmployeJPQL/"+idmission,
				String.class)).contains(json);
	}
	
	
}
