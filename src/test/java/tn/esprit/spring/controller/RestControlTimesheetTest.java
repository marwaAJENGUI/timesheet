package tn.esprit.spring.controller;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;
@WebMvcTest(value = ControllerTimesheetImpl.class)
class RestControlTimesheetTest {
	@MockBean
	IEmployeService iemployeservice;
	@MockBean
	IEntrepriseService ientrepriseservice;
	@MockBean
	ITimesheetService itimesheetservice;
	@Test
	void test() {
		//itimesheetservice.ajouterMission(Any)
	}

}
