package tn.esprit.spring.controller;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerEntrepriseImplTest {

	@Autowired
	IEntrepriseService es;

	@Autowired
	DepartementRepository deprepo;

	Entreprise e = new Entreprise("Entreprise test", "test");
	Departement d = new Departement("Department test");

	int idE;
	int idD;
	int sizeListE;
	int sizeListD;

	private static final Logger logger = Logger.getLogger(ControllerEntrepriseImplTest.class);

	@BeforeClass
	public static void setup() {
		System.setProperty("log4j.configurationFile", "log4j2-testConfig.xml");
		logger.info("Launching Test Class for ControllerEntrepriseImp...");
	}

	@Test
	public void testajouterEntreprise() throws ParseException {
		logger.info("Test of ajouter Entreptrise method...");
		logger.debug("adding new Entreprise (" + e.getName() + "," + e.getRaisonSocial() + "...");
		idE = es.ajouterEntreprise(e);
		logger.debug("added Entreprise index: " + idE);

		logger.debug("fetching Entreprise of index " + idE + "...");
		Entreprise e1 = es.getEntrepriseById(idE);
		logger.debug("Entreprise of index " + idE + " (" + e1.getName() + "," + e1.getRaisonSocial() + " fetched.");
		assertEquals(e.getName(), e1.getName());
		logger.debug("Test successful.");

	}

	@Test
	public void testajouterDepartement() throws ParseException {

		logger.info("Test of ajouter Departement method...");
		logger.debug("adding new Department " + d.getName() + "...");
		idD = es.ajouterDepartement(d);
		logger.debug("added Department index: " + idD);

		logger.debug("fetching Department of index " + idD + "...");
		Departement d1 = deprepo.findById(idD).get();
		logger.debug("Department of index " + idD + " " + d1.getName() + " fetched.");
		assertEquals(d.getName(), d1.getName());
		logger.debug("Test successful.");

	}

	@Test
	public void testaffecterDepartementAEntreprise() throws ParseException {

		logger.info("Test of affecter Departement a Entreprise method...");

		List<Entreprise> listentreprises = es.getAllEnterprise();
		sizeListE = listentreprises.size();

		e = listentreprises.get(sizeListE - 1);

		List<Departement> listdep = (List<Departement>) deprepo.findAll();
		sizeListD = listdep.size();
		
		if(sizeListD>=1) {

		d = listdep.get(0);

		logger.debug("affecting Departement (" + d.getId() + "," + d.getName() + ") to Entreprise (" + e.getId() + ","
				+ e.getName() + ")...");

		es.affecterDepartementAEntreprise(d.getId(), e.getId());

		logger.debug("fetching Department of index " + d.getId() + "...");
		Departement d1 = deprepo.findById(d.getId()).get();
		logger.debug(
				"Department (" + d1.getId() + "," + d1.getName() + "," + d1.getEntreprise().getId() + ") fetched.");

		logger.info("Comparing fetched department's entreprise with the initial enterprise...");
		assertEquals(d1.getEntreprise().getId(), e.getId());
		logger.debug("Test successful.");
		
		}
		else logger.info("Check database before proceding.");


	}

	@Test
	public void testgetAllDepartementsNamesByEntreprise() {

		logger.info("Test of get All Departements Names By Entreprise method...");
		logger.info("fetching all departments of Entreprise 1...");
		Entreprise e = es.getEntrepriseById(1);
		List<String> depNames = new ArrayList<>();
		for (Departement dep : e.getDepartements()) {
			depNames.add(dep.getName());
		}

		logger.debug("List of department names of entreprise 1:" + depNames);

		List<String> testlist = new ArrayList<String>();
		testlist.add("Telecom");
		testlist.add("RH");

		logger.info("Comparing to our set list...");
		assertEquals(testlist, depNames);
		logger.debug("Test successful.");
	}

	@Test
	public void testGetEntrpriseById() {

		logger.info("Test of get entreprise by id method...");
		logger.info("Fetching of Entreprise of index 1...");
		Entreprise e = es.getEntrepriseById(1);
		logger.debug("Entreprise of index 1 fetched.");
		assertEquals(1, e.getId());
		logger.debug("Test successful.");
	}

	@Test
	public void testdeleteEntrepriseById() {
		logger.info("Test of delete Entreprise By id method...");

		List<Entreprise> listentreprises = es.getAllEnterprise();
		sizeListE = listentreprises.size();

		logger.debug("Table Entreprise current size:" + sizeListE);
		
		if (sizeListE>=3) {

		idE = listentreprises.get(2).getId();

		logger.debug("deleting row of [" + idE + "] index...");

		es.deleteEntrepriseById(idE);
		// Entreprise e1 = es.getEntrepriseById(idE);
		List<Entreprise> newlistentreprises = es.getAllEnterprise();
		int sizeNewList = newlistentreprises.size();

		logger.debug("Table Entreprise current size is now:" + sizeNewList);

		assertEquals(sizeListE - 1, sizeNewList);
		logger.debug("Delete method executed successfully.");
		
		}
		else logger.info("Please check your database before proceding with the delete method test.");

	}

	@Test
	public void testdeleteDepartementById() {

		logger.info("Test of delete Department By id method...");

		List<Departement> listdep = (List<Departement>) deprepo.findAll();
		sizeListD = listdep.size();

		logger.debug("Table Departement current size:" + sizeListD);

		idD = listdep.get(sizeListD - 1).getId();

		logger.debug("deleting row of [" + idD + "] index...");

		es.deleteDepartementById(idD);

		List<Departement> newlistdep = (List<Departement>) deprepo.findAll();
		int sizeNewListD = newlistdep.size();

		logger.debug("Table Departement current size is now:" + sizeNewListD);

		assertEquals(sizeListD - 1, sizeNewListD);
		logger.debug("Delete method executed successfully.");

	}

}
