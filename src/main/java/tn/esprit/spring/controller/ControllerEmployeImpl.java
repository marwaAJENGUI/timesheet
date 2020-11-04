package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.IEmployeService;

@Scope(value = "session")
@Controller(value = "employeController")
@ELBeanName(value = "employeController")
@Join(path = "/", to = "/login.jsf")
public class ControllerEmployeImpl {

	@Autowired
	IEmployeService employeService;

	private static final Logger logger = Logger.getLogger(ControllerEmployeImpl.class);

	private String login;
	private String password;
	private Boolean loggedIn;

	private Employe authenticatedUser = null;
	private String prenom;
	private String nom;
	private String email;
	private boolean actif;
	private Role role;

	public Role[] getRoles() {
		return Role.values();
	}

	private List<Employe> employes;

	private Integer employeIdToBeUpdated; // getter et setter

	public String doLogin() {
		logger.info("entering doLogin()...");
		try {
			String navigateTo = "null";
			logger.debug("getting user info...");
			authenticatedUser = employeService.authenticate(login, password);
			logger.debug("Authenticating user: " + login + "/" + password);
			if (authenticatedUser != null && authenticatedUser.getRole() == Role.ADMINISTRATEUR) {
				logger.debug("used Authenticated!");
				navigateTo = "/pages/admin/welcome.xhtml?faces-redirect=true";
				loggedIn = true;
			}

			else {
				logger.warn("Authentication failed of user " + login + "/" + password + " failed");
				FacesMessage facesMessage = new FacesMessage(
						"Login Failed: Please check your username/password and try again.");
				FacesContext.getCurrentInstance().addMessage("form:btn", facesMessage);
			}
			logger.warn("exiting doLogin() with failed authentication...");
			return navigateTo;
		} catch (Exception e) {
			logger.error("exiting doLogin() with " + e);
			return "null";
		}
	}

	public String doLogout() {
		logger.info("entering doLogout()...");
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("logging out...");
			logger.debug("exiting doLogout()");
			return "/login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			logger.error("exiting doLogout() with " + e);
			return "null";
		}
	}

	public String addEmploye() {
		logger.info("entering addEmploye()...");
		try {
			Employe employe = new Employe(nom, prenom, email, password, actif, role);
			if (authenticatedUser == null || !loggedIn) {
				logger.error("exiting addEmploye() after failed authentication...");
				return "/login.xhtml?faces-redirect=true";
			}
			logger.debug("adding employe " + employe);
			employeService.addOrUpdateEmploye(employe);
			logger.debug("employe added, exiting addEmploye()...");
			return "null";
		} catch (Exception e) {
			logger.error("exiting addEmploye() with " + e);
			return "null";
		}
	}

	public String removeEmploye(int employeId) {
		logger.info("entering removeEmploye()...");
		try {
			String navigateTo = "null";
			if (authenticatedUser == null || !loggedIn) {
				logger.error("exiting removeEmploye() after failed authentication...");
				return "/login.xhtml?faces-redirect=true";
			}

			logger.debug("deleting employe " + employeService.getEmployePrenomById(employeId));
			employeService.deleteEmployeById(employeId);
			logger.debug("employe deleted, exiting removeEmploye()...");
			return navigateTo;
		} catch (Exception e) {
			logger.error("exiting removeEmploye() with " + e);
			return "null";
		}
	}

	public String displayEmploye(Employe empl) {
		logger.info("entering diplayEmploye()...");
		try {
			String navigateTo = "null";
			if (authenticatedUser == null || !loggedIn) {
				logger.error("exiting displayEmploye() after failed authentication...");
				return "/login.xhtml?faces-redirect=true";
			}

			logger.debug("displaying employe " + empl);
			this.setPrenom(empl.getPrenom());
			this.setNom(empl.getNom());
			this.setActif(empl.isActif());
			this.setEmail(empl.getEmail());
			this.setRole(empl.getRole());
			this.setPassword(empl.getPassword());
			this.setEmployeIdToBeUpdated(empl.getId());

			logger.debug("exiting displayEmploye()");
			return navigateTo;
		} catch (Exception e) {
			logger.error("exiting displayEmploye() with " + e);
			return "null";
		}
	}

	public String updateEmploye() {
		logger.info("entering updateEmploye()...");
		try {
			String navigateTo = "null";
			Employe employe = new Employe(employeIdToBeUpdated, nom, prenom, email, password, actif, role);

			if (authenticatedUser == null || !loggedIn) {
				logger.error("exiting updateEmploye() after failed authentication...");
				return "/login.xhtml?faces-redirect=true";
			}

			logger.debug("updating employe to " + employe);
			employeService.addOrUpdateEmploye(employe);
			logger.debug("Employe updated, exiting updateEmploye()");
			return navigateTo;
		} catch (Exception e) {
			logger.error("exiting updateEmploye() with " + e);
			return "null";
		}
	}

	// getters and setters

	public IEmployeService getEmployeService() {
		return employeService;
	}

	public void setEmployeService(IEmployeService employeService) {
		this.employeService = employeService;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Employe> getAllEmployes() {
		return employeService.getAllEmployes();
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int ajouterEmploye(Employe employe) {
		employeService.addOrUpdateEmploye(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		employeService.mettreAjourEmailByEmployeId(email, employeId);

	}

	public void affecterEmployeADepartement(int employeId, int depId) {
		employeService.affecterEmployeADepartement(employeId, depId);

	}

	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		employeService.desaffecterEmployeDuDepartement(employeId, depId);
	}

	public int ajouterContrat(Contrat contrat) {
		employeService.ajouterContrat(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		employeService.affecterContratAEmploye(contratId, employeId);
	}

	public String getEmployePrenomById(int employeId) {
		return employeService.getEmployePrenomById(employeId);
	}

	public void deleteEmployeById(int employeId) {
		employeService.deleteEmployeById(employeId);

	}

	public void deleteContratById(int contratId) {
		employeService.deleteContratById(contratId);
	}

	public int getNombreEmployeJPQL() {

		return employeService.getNombreEmployeJPQL();
	}

	public List<String> getAllEmployeNamesJPQL() {

		return employeService.getAllEmployeNamesJPQL();
	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeService.getAllEmployeByEntreprise(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeService.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		employeService.deleteAllContratJPQL();

	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeService.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeService.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return employeService.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
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

	public List<Employe> getEmployes() {
		employes = employeService.getAllEmployes();
		return employes;
	}

	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}

	public Integer getEmployeIdToBeUpdated() {
		return employeIdToBeUpdated;
	}

	public void setEmployeIdToBeUpdated(Integer employeIdToBeUpdated) {
		this.employeIdToBeUpdated = employeIdToBeUpdated;
	}

	public Employe getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(Employe authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

}
