package tn.esprit.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
public class ControllerEntrepriseImplTest {
	
	@Autowired 
	IEntrepriseService es;
	
	@Autowired
	DepartementRepository deprepo;
	
	
	Entreprise e=new Entreprise("nom entreprise test","raison sociale test");
	Departement d=new Departement("nom department test");
	
	int idE;
	int idD;
	
	private static final Logger logger = Logger.getLogger(ControllerEntrepriseImplTest.class);

	
	@Test
	public void testajouterEntreprise() throws ParseException {
        try {
		logger.debug("Teste de la methode ajouter Entreptrise");
		idE=es.ajouterEntreprise(e);
		Entreprise e1=es.getEntrepriseById(idE);
		assertEquals(e.getName(), e1.getName());
		logger.debug(e+"ajoutée");
        
	} catch (Exception e) {
		logger.error("methode ajouter Entreprise a echouée: " + e );
	}
	}
	
	@Test
	public void testajouterDepartement() throws ParseException {
        
		logger.debug("Teste de la methode ajouter Departement");
		
		try {
		idD=es.ajouterDepartement(d);
		Departement d1=deprepo.findById(idD).get();
		assertEquals(d.getName(), d1.getName());
		logger.debug(d+"ajouté");
		}
		catch(Exception e) {
			logger.error("methode ajouter Departement a echouée: " + e );
		}
	}
	
	@Test
	public void testaffecterDepartementAEntreprise() throws ParseException {
		
		
		
	}
	
	/*@Test
	public void testgetAllDepartementsNamesByEntreprise() {
		
		logger.debug("Teste de la methode get All Departements Names By Entreprise");
		Entreprise e = es.getEntrepriseById(1); 
		List<String> depNames = new ArrayList<>();
		for(Departement dep : e.getDepartements()){
			depNames.add(dep.getName());
		}
		
		logger.debug("Liste des departement de l'entreprise 1:"+depNames);
		
		List<String> testlist= new ArrayList<String>();
		testlist.add("Telecom");
		testlist.add("RH");
		assertEquals(testlist,depNames);
		logger.debug("Succes");
	}*/

	/*@Test
	public void testGetEntrpriseById() {
		Entreprise e = es.getEntrepriseById(1); 
		assertEquals(1, e.getId());
	}*/
	
	
	/*@Test
	public void testdeleteEntrepriseById()
	{

		es.deleteEntrepriseById(idE);
		Entreprise e1 = es.getEntrepriseById(idE); 
		assertNull(e1.getName());
	}*/
	
	/*@Test
	public void testdeleteDepartementById() {
		
		es.deleteDepartementById(idD);
		//Departement d1=deprepo.findById(idD).get();
		assertNull(deprepo.findById(idD).get());

	}*/

}
