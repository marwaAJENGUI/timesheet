package tn.esprit.spring.dto;

import java.util.List;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

public class DepartementDTO  extends Departement{


	public DepartementDTO() {
		super();
	}
	
	public DepartementDTO(String name) {
		super(name);
	}
	
}
