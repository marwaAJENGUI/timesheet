package tn.esprit.spring.controller;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTimesheetImplTest {
	@Autowired
	TimesheetServiceImpl timesheetserviceimpl;
	@Autowired
	MissionRepository missionrepository;
	
	
	@Test
	public void ajouterMission() 
	{
		//set variable
		Mission m = new Mission();
		m.setName("first");
		m.setDescription("this is first mission");
		//when
		int id= timesheetserviceimpl.ajouterMission(m);
		Optional<Mission> me = missionrepository.findById(id);
		//then
	assertThat(me.get().getName()).isEqualTo("first");
	assertThat(me.get().getDescription()).isEqualTo("this is first mission");	}
	
}
