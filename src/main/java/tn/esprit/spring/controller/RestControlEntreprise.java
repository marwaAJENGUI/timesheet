package tn.esprit.spring.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlEntreprise {

	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	private static final Logger logger = Logger.getLogger(RestControlEntreprise.class);

	// Ajouter Entreprise :
	// http://localhost:8081/SpringMVC/servlet/ajouterEntreprise
	// {"id":1,"name":"SSII Consulting","raisonSocial":"Cite El Ghazela"}
	@PostMapping("/ajouterEntreprise")
	@ResponseBody
	public int ajouterEntreprise(@RequestBody Entreprise ssiiConsulting) {
		logger.info("In Controller:ajouterEntreprise() : ");
		ientrepriseservice.ajouterEntreprise(ssiiConsulting);
		logger.info("Out Of ajouterEntreprise() : ");
		return ssiiConsulting.getId();
	}

	// http://localhost:8081/SpringMVC/servlet/affecterDepartementAEntreprise/1/1
	@PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}")
	public void affecterDepartementAEntreprise(@PathVariable("iddept") int depId,
			@PathVariable("identreprise") int entrepriseId) {
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
		logger.info("Out Of Controller:affecterDepartementAEntreprise()");
	}

	// http://localhost:8081/SpringMVC/servlet/deleteEntrepriseById/1
	@DeleteMapping("/deleteEntrepriseById/{identreprise}")
	@ResponseBody
	public void deleteEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		logger.info("In Controller:deleteEntrepriseById() : ");
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
		logger.info("Out of Controller:deleteEntrepriseById() : ");
	}

	// http://localhost:8081/SpringMVC/servlet/getEntrepriseById/1
	@GetMapping(value = "getEntrepriseById/{identreprise}")
	@ResponseBody
	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		Entreprise e = ientrepriseservice.getEntrepriseById(entrepriseId);
		logger.info("In Controllere:affecterDepartementAEntreprise() : ");
		return e;
	}

	// http://localhost:8081/SpringMVC/servlet/ajouterDepartement
	// {"id":1,"name":"Telecom"}

	@PostMapping("/ajouterDepartement")
	@ResponseBody
	public int ajouterDepartement(@RequestBody Departement dep) {
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		int id = ientrepriseservice.ajouterDepartement(dep);
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		return id;
	}

	// http://localhost:8081/SpringMVC/servlet/getAllDepartementsNamesByEntreprise/1
	@GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
	@ResponseBody
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		List<String> entreprises = ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		return entreprises;
	}

	// URL : http://localhost:8081/SpringMVC/servlet/deleteDepartementById/3
	@DeleteMapping("/deleteDepartementById/{iddept}")
	@ResponseBody
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		logger.info("In Controller:affecterDepartementAEntreprise() : ");
		ientrepriseservice.deleteDepartementById(depId);
		logger.info("In Controller:affecterDepartementAEntreprise() : ");

	}
}
