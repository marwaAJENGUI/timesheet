package tn.esprit.spring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.MissionDTO;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlTimesheet {

	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	static final String DATE_FORMAT="dd-mm-yyyy";
	// http://localhost:8081/SpringMVC/servlet/ajouterMission
	@PostMapping("/ajouterMission")
	@ResponseBody
	public int ajouterMission(@RequestBody MissionDTO missionDTO) {
		ModelMapper modelMapper=new ModelMapper();
		Mission mission = new Mission();
		modelMapper.map(missionDTO, mission);
		itimesheetservice.ajouterMission(mission);
		return mission.getId();
	}

	// http://localhost:8081/SpringMVC/servlet/affecterMissionADepartement/4/4
	@PutMapping(value = "/affecterMissionADepartement/{idmission}/{iddept}") 
	public void affecterMissionADepartement(@PathVariable("idmission") int missionId, @PathVariable("iddept") int depId) {
		itimesheetservice.affecterMissionADepartement(missionId, depId);
	}
	
	// http://localhost:8081/SpringMVC/servlet/ajouterTimesheet/1/1/03-10-2020/03-20-2020
	@PostMapping("/ajouterTimesheet/{idmission}/{idemp}/{dated}/{datef}")
	@ResponseBody
	public void ajouterTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId, @PathVariable("dated") String strDateDebut,@PathVariable("datef") String strDateFin) throws ParseException {
	    Date dateDebut=new SimpleDateFormat(DATE_FORMAT).parse(strDateDebut);  
	    Date dateFin=new SimpleDateFormat(DATE_FORMAT).parse(strDateFin);  
		itimesheetservice.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);
	}

	// http://localhost:8081/SpringMVC/servlet/validerTimesheet/1/1/03-10-2020/03-20-2020/1
	@PutMapping(value = "/validerTimesheet/{idmission}/{idemp}/{dated}/{datef}/{idval}") 
	public void validerTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId, @PathVariable("dated") String strDateDebut,@PathVariable("datef") String strDateFin, @PathVariable("idval") int validateurId) throws ParseException {
	    Date dateDebut=new SimpleDateFormat(DATE_FORMAT).parse(strDateDebut);  
	    Date dateFin=new SimpleDateFormat(DATE_FORMAT).parse(strDateFin);  
		itimesheetservice.validerTimesheet(missionId, employeId, dateDebut, dateFin, validateurId);
	}
	
	// URL : http://localhost:8081/SpringMVC/servlet/findAllMissionByEmployeJPQL/1
    @GetMapping(value = "findAllMissionByEmployeJPQL/{idemp}")
    @ResponseBody
	public List<Mission> findAllMissionByEmployeJPQL(@PathVariable("idemp") int employeId) {

		return itimesheetservice.findAllMissionByEmployeJPQL(employeId);
	}

    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployeByMission/1
    @GetMapping(value = "getAllEmployeByMission/{idmission}")
    @ResponseBody
	public List<Employe> getAllEmployeByMission(@PathVariable("idmission") int missionId) {

		return itimesheetservice.getAllEmployeByMission(missionId);
	}
}
