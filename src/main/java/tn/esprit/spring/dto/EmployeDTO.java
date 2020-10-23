package tn.esprit.spring.dto;

import java.util.List;

import tn.esprit.spring.entities.Role;
public class EmployeDTO {
	
	private int id;
	
	private String prenom;
	
	private String nom;
	
	private String email;

	private String password;
	
	private boolean actif;
	
	private Role role;
	
	private List<DepartementDTO> departements;
	
	private ContratDTO contrat;
	
	private List<TimesheetDTO> timesheets;
	
	
	public EmployeDTO() {
		super();
	}
	
		
	public EmployeDTO(int id, String prenom, String nom, String email, String password, boolean actif, Role role) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.password = password;
		this.actif = actif;
		this.role = role;
	}



	public EmployeDTO(String nom, String prenom, String email, String password, boolean actif, Role role) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.actif = actif;
		this.role = role;
	}
	
	public EmployeDTO(String nom, String prenom, String email, boolean actif, Role role) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.actif = actif;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}
	 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActif() {
		return actif;
	}


	public void setActif(boolean actif) {
		this.actif = actif;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<DepartementDTO> getDepartements() {
		return departements;
	}

	public void setDepartements(List<DepartementDTO> departement) {
		this.departements = departement;
	}

	public ContratDTO getContrat() {
		return contrat;
	}

	public void setContrat(ContratDTO contrat) {
		this.contrat = contrat;
	}

	public List<TimesheetDTO> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<TimesheetDTO> timesheets) {
		this.timesheets = timesheets;
	}


	@Override
	public String toString() {
		return "Employe [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", email=" + email + ", password="
				+ password + ", actif=" + actif + ", role=" + role + "]";
	}
	
	
}
