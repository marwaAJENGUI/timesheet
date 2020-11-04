package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.controller.RestControlEntreprise;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	private static final Logger logger = Logger.getLogger(EntrepriseServiceImpl.class);
	
	public int ajouterEntreprise(Entreprise entreprise) {
		logger.info("In Service:ajouterEntreprise() : ");
		Entreprise e=entrepriseRepoistory.save(entreprise);
		if(e.getName().equals(entreprise.getName())) {
			logger.debug("l'entreprise "+e.getName()+" id:"+e.getId()+" a été bien enregistrée dans la BD");
		}
		else {
			logger.warn("opération d'enregistrement de données a été echouée");
		}
		logger.info("Out of ajouterEntreprise()");
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		logger.info("In Service:ajouterEntreprise() : ");
		Departement d=deptRepoistory.save(dep);
		if(!d.getName().equals(dep.getName())){
			logger.warn("opération d'enregistrement de données a été echouée");
		}
		logger.info("Out of Service:ajouterEntreprise() : ");
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		Entreprise entrepriseManagedEntity;
		Departement depManagedEntity;
				logger.info("In Service:affecterDepartementAEntreprise: ");
				if(entrepriseRepoistory.findById(entrepriseId).isPresent() && deptRepoistory.findById(depId).isPresent()) {
					logger.debug("entreprise d'id:"+entrepriseId+" a été récupérer de la base de données");
					entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
					depManagedEntity = deptRepoistory.findById(depId).get();
					logger.debug("Département d'id:"+depId+" a été récupérer de la base de données");
					depManagedEntity.setEntreprise(entrepriseManagedEntity);
					Departement dept=deptRepoistory.save(depManagedEntity);	
					if(dept.getId()==depId) {
						logger.debug("Département d'id:"+depId+" a été affecter à l'entreprise d'id: "+entrepriseId);
					}
					else {
						logger.warn("opération d'enregistrement de données a été echouée");
					}
				}
				else {
					if(!deptRepoistory.findById(depId).isPresent()) {
					logger.warn("l'id: "+depId+" ne correspond à aucune departement");}
					if(!deptRepoistory.findById(depId).isPresent()) {
					logger.warn("l'id: "+entrepriseId+" ne correspond à aucune entreprise");}						
				}
				logger.info("Out of Service:affecterDepartementAEntreprise: ");		
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		logger.info("In Service:getAllDepartementsNamesByEntreprise: ");
		List<String> depNames = new ArrayList<>();
		if(!entrepriseRepoistory.findById(entrepriseId).isPresent()) {
			logger.warn("l'id: "+entrepriseId+" ne correspond à aucune entreprise");
		}
		else {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		logger.debug("Récupération des noms de départements");
		}
		logger.info("Out of Service:getAllDepartementsNamesByEntreprise: ");
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		logger.info("In Service:deleteEntrepriseById: ");
		if(entrepriseRepoistory.findById(entrepriseId).isPresent()) {
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());
		logger.info("supression de l'entreprise "+entrepriseId);		
		}
		else {
			logger.warn("aucune entreprise correspond à l'id "+entrepriseId);
		}
		
		logger.info("Out of Service:deleteEntrepriseById: ");
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		logger.info("In Service:deleteDepartementById: ");
		if(deptRepoistory.findById(depId).isPresent()) {
			deptRepoistory.delete(deptRepoistory.findById(depId).get());
			logger.info("supression du département "+depId);		
		}
		else {
			logger.warn("aucun département correspond à l'id "+depId);
		}
		
		logger.info("Out of Service:deleteDepartementById: ");
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		logger.info("In Service:getEntrepriseById: ");
		if(!entrepriseRepoistory.findById(entrepriseId).isPresent()) {
			logger.warn("aucune entreprise correspond à l'id "+entrepriseId);			
		}
		logger.info("Out of Service:getEntrepriseById: ");
		return entrepriseRepoistory.findById(entrepriseId).get();
		
		
		
	}
	
	public List<Entreprise> getAllEnterprise(){
		List<Entreprise> entreprises =(List<Entreprise>) entrepriseRepoistory.findAll();
		return entreprises;
	}

}
