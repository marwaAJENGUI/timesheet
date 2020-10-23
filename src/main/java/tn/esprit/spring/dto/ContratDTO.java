package tn.esprit.spring.dto;

import java.util.Date;

public class ContratDTO {
	
	private int reference;
	
	private Date dateDebut;
	
	private String typeContrat;
	
	private float telephone;
	
	private EmployeDTO employe;

	private float salaire;

	public ContratDTO() {
		super();
	}
	
	public ContratDTO(Date dateDebut, String typeContrat, float salaire) {
		this.dateDebut = dateDebut;
		this.typeContrat = typeContrat;
		this.salaire = salaire;
	}


	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	public EmployeDTO getEmploye() {
		return employe;
	}

	public void setEmploye(EmployeDTO employe) {
		this.employe = employe;
	}

	public float getTelephone() {
		return telephone;
	}

	public void setTelephone(float telephone) {
		this.telephone = telephone;
	}
	
	
}
