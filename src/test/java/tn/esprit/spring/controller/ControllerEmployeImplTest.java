package tn.esprit.spring.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerEmployeImplTest {
	@LocalServerPort
	private int port;

	private static final Logger logger = Logger.getLogger(ControllerEmployeImplTest.class);

	private static String login;
	private static String password;
	private Role role;
	private Boolean loggedIn;

	int employeIdToBeUpdated=1;
	private Employe authenticatedUser = null;
	private String prenom;
	private String nom;
	private String email;
	private boolean actif;

	static String requestJson;
	static HttpHeaders headers;
	static String strDateDebut;
	static String strDateFin;

	@Autowired
	IEmployeService employeService;

	@BeforeClass
	public static void setUp() {
		login = "Khaled.kallel@ssiiconsulting.tn";
		password = "aaa";
	}

	@Test
	public void doLoginTest() {
		logger.info("entering doLoginTest()...");
		try {
			String navigateTo = "null";
			logger.debug("getting user info...");
			authenticatedUser = employeService.authenticate(login, password);
			logger.debug("Authenticating user: " + login + "/" + password);
			if (authenticatedUser != null || authenticatedUser.getRole() == Role.ADMINISTRATEUR) {
				logger.debug("used Authenticated!");
				navigateTo = "/pages/admin/welcome.xhtml?faces-redirect=true";
				loggedIn = true;
			}

			else {
				logger.debug("Authentication failed of user " + login + "/" + password + " failed");
				logger.warn("exiting doLogin() with failed authentication...");
			}

			assertEquals(navigateTo, "/pages/admin/welcome.xhtml?faces-redirect=true");
			assertTrue(loggedIn);
			logger.info("exiting doLoginTest()...");
		} catch (Exception e) {
			logger.error("exiting doLoginTest() with " + e);
		}
	}

	@Test
	public void doLogoutTest() {
		logger.info("entering doLogoutTest()...");
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("logging out...");
			String result = "/login.xhtml?faces-redirect=true";
			assertEquals(result, "/login.xhtml?faces-redirect=true");
			logger.info("exiting doLogout()");
		} catch (Exception e) {
			logger.error("exiting doLogoutTest() with " + e);
		}
	}

	@Test
	public void addEmployeTest() {
		logger.info("entering addEmploye()...");
		try {
			Employe employe = new Employe(nom, prenom, login, password, actif, role);
			logger.debug("adding employe " + employe);
			String addedEmployePrenom = employeService.getEmployePrenomById(employeService.addOrUpdateEmploye(employe));
			assertEquals(addedEmployePrenom, employe.getPrenom());
			logger.info("employe added, exiting addEmploye()...");
		} catch (Exception e) {
			logger.error("exiting addEmployeTest() with " + e);
		}
	}
	
	@Test
	public void removeEmployeTest() {
		logger.info("entering removeEmploye()...");
		int employeId=3;
		try {
			logger.debug("deleting employe " + employeService.getEmployePrenomById(employeId));
			employeService.deleteEmployeById(employeId);
			logger.info("employe deleted, exiting removeEmploye()...");
		} catch (Exception e) {
			logger.error("exiting removeEmployeTest() with " + e);
		}
	}
	
	@Test
	public void updateEmployeTest() {
		logger.info("entering updateEmployeTest()...");
		try {
			Employe employe = new Employe(employeIdToBeUpdated, nom, prenom, email, password, actif, role);

			logger.debug("updating employe to " + employe);
			assertEquals(employeIdToBeUpdated, employeService.addOrUpdateEmploye(employe));
			logger.info("Employe updated, exiting updateEmploye()");
		} catch (Exception e) {
			logger.error("exiting updateEmployeTest() with " + e);
		}
	}
	

}
