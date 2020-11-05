package tn.esprit.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.TimesheetServiceImpl;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTimesheetImplTest {
	
	@Autowired
	IDepartementService departementService; 
	
	@Autowired
	TimesheetServiceImpl timesheetserviceimpl;
	@Autowired
	MissionRepository missionrepository;
	@Autowired
	TimesheetRepository timesheetpository;
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	EntrepriseRepository entrepriseRepository;
	@Autowired
	TimesheetServiceImpl timesheetService;
	private static final Logger logger = Logger.getLogger(ControllerTimesheetImplTest.class);
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
	@BeforeEach
	public void init() {
		
	}
	
	/*
	@Test
	public void ajouterTimesheet() 
	{	
		Mission mission= missionrepository.findById(2).get();
		Employe employe = employeRepository.findById(1).get();
		Departement departement= deptRepoistory.findById(1).get();
		TimesheetPK timesheetpk = new TimesheetPK();
		
		timesheetpk.setDateDebut(new Date());
		timesheetpk.setDateFin(new Date());
		timesheetpk.setIdEmploye(employe.getId());
		timesheetpk.setIdMission(mission.getId());
		logger.info("In Test:ajouterTimesheet : ");
		System.out.println(mission.getId());
		timesheetService.ajouterTimesheet(mission.getId(), employe.getId(), new Date(), new Date());
		logger.debug("timesheet a été ajouté");
		assertEquals(timesheetService.findBytimesheetPK(timesheetpk).getEmploye().getId(),employe.getId());
		logger.info("Out of Test:ajouterTimesheet : ");
		
	}
	@Test
	public void validerTimesheet() 
	{
		Mission mission= missionrepository.findById(1).get();
		Employe employe = employeRepository.findById(1).get();
		Departement departement= deptRepoistory.findById(1).get();
		TimesheetPK timesheetpk = new TimesheetPK();

		timesheetpk.setDateDebut(new Date());
		timesheetpk.setDateFin(new Date());
		timesheetpk.setIdEmploye(employe.getId());
		timesheetpk.setIdMission(mission.getId());
		
		logger.info("In Test:validerTimesheet : ");
		timesheetService.validerTimesheet(mission.getId(), employe.getId(), new Date(), new Date(),employe.getId());
		System.out.println("-----------------------------"+timesheetService.findBytimesheetPK(timesheetpk).getEmploye().getId());
		assertEquals(timesheetService.findBytimesheetPK(timesheetpk).isValide(),true);
		logger.info("Out of Test:validerTimesheet : ");
		
	}
	
	
	

	 	@Test
	public void affecterMissionADepartementTest() {
		Mission m = new Mission(61,"llo","jdj");
		logger.info("affecterMissionADepartementTest()...");
		Entreprise e = new Entreprise (3,"yy","uu");
		entrepriseRepository.save(e);
		Departement d = new Departement (1,"helmi",e);
		logger.debug("updating employe to " + e);
		timesheetService.affecterMissionADepartement(m.getId(), d.getId());
		deptRepoistory.findById(d.getId()).get().getMissions().forEach(x -> {
			System.out.println(x.getId());
		});
		Boolean ok=false;
		for(Mission mission : deptRepoistory.findById(d.getId()).get().getMissions() ){
			if(mission.getId()==m.getId())
				ok=true;
		}
		assert(ok);
		logger.info("Employe updated, exiting updateEmploye()");

	} 
	
	*
	*/
	
}
