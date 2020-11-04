package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.config.InvocationTrace;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
  private static Logger log = Logger.getLogger(InvocationTrace.class);

	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		Optional<Mission> oppMission= missionRepository.findById(missionId);
		if (oppMission.isPresent()){
			Mission mission = oppMission.get();
			Optional<Departement> oppDepartement= deptRepoistory.findById(depId);
			if (oppDepartement.isPresent()){
				Departement dep = oppDepartement.get();
				mission.setDepartement(dep);
				missionRepository.save(mission);
			}
		}
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		Optional <Mission> opMission = missionRepository.findById(missionId);
		if (opMission.isPresent()) {
			timesheet.setMission(opMission.get());
		}else throw new IllegalArgumentException("Invalide mission Id: mission does not exist");
		Optional <Employe> opEmploye = employeRepository.findById(employeId);	
		if (opEmploye.isPresent()) {
			timesheet.setEmploye(opEmploye.get());
		}else throw new IllegalArgumentException("Invalide employe Id: employee does not exist");			
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
		
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		log.info("In valider Timesheet");
		Optional<Employe> oppEmploye= employeRepository.findById(validateurId);
		if(oppEmploye.isPresent()) {
			Employe validateur = oppEmploye.get();
			log.info("role validateur = "+validateur.getRole());
			//verifier s'il est un chef de departement (interet des enum)
			if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
				log.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
				return;
			}
			//verifier s'il est le chef de departement de la mission en question
			boolean chefDeLaMission = false;
			Optional<Mission> oppMission= missionRepository.findById(missionId);
			if (oppMission.isPresent()){
				Mission mission = oppMission.get();
				for(Departement dep : validateur.getDepartements()){
				if(dep.getId() == mission.getDepartement().getId()){
					chefDeLaMission = true;
					log.info("pour departement:"+dep+"chef de la mission = "+chefDeLaMission);
					break;
				}
			}
			}
			if(!chefDeLaMission){
				log.info("l'employe doit etre chef de departement de la mission en question");
				return;
			}
		} else log.debug("Employee not found");
		
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		log.info("timesheet= "+timesheet);
		timesheet.setValide(true);
		timesheetRepository.save(timesheet);
		log.info("timesheet= "+timesheet);		
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		log.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
	}

	@Override
	public Timesheet findBytimesheetPK(TimesheetPK timesheetPK) {
		return timesheetRepository.findBytimesheetPK(timesheetPK);
	}
	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
