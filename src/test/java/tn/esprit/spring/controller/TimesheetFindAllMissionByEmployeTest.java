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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.ITimesheetService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimesheetFindAllMissionByEmployeTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ITimesheetService itimesheetservice;
	int employeId;

	@Test
	public void findAllMissionByEmployeJPQLTest() throws JsonProcessingException {
		employeId=1;
		List<Mission> missionsList = itimesheetservice.findAllMissionByEmployeJPQL(employeId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(missionsList);
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/SpringMVC/servlet/findAllMissionByEmployeJPQL/"+employeId,
				String.class)).contains(json);
	}
	

}
