package tn.esprit.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
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
	private Boolean loggedIn;

	int employeIdToBeUpdated = 305;
	private Employe authenticatedUser = null;
	private boolean actif;

	static String requestJson;
	static HttpHeaders headers;
	static String strDateDebut;
	static String strDateFin;

	@Autowired
	IEmployeService employeService;

	@BeforeClass
	public static void setUp() {
		login = "test@lo.tn";
		password = "password";
	}

	@Test
	public void doLoginTest() {
		logger.info("entering doLoginTest()...");

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

	}

	@Test
	public void doLogoutTest() {
		logger.info("entering doLogoutTest()...");
		logger.debug("logging out...");
		String result = "/login.xhtml?faces-redirect=true";
		assertEquals(result, "/login.xhtml?faces-redirect=true");
		logger.info("exiting doLogout()");

	}

	@Test
	public void addEmployeTest() {
		logger.info("entering addEmploye()...");
		Employe employe = new Employe("TestNom", "TestPrenom", "Test@mail.tn", "pwd", actif, Role.INGENIEUR);
		logger.debug("adding employe " + employe);
		String addedEmployePrenom = employeService.getEmployePrenomById(employeService.addOrUpdateEmploye(employe));
		assertEquals(addedEmployePrenom, employe.getPrenom());
		logger.info("employe added, exiting addEmploye()...");

	}

	/*@Test
	public void removeEmployeTest() {
		logger.info("entering removeEmploye()...");
		//int employeId = 3;
		//logger.debug("deleting employe " + employeService.getEmployePrenomById(employeId));
		//employeService.deleteEmployeById(employeId);
		
		List<Employe> employes = employeService.getAllEmployes();
		int employeListSize= employes.size();
		logger.debug("current number of employes:" + employeListSize);
		int employeId = employes.get(employeListSize - 1).getId();
		logger.debug("deleting employe with id " + employeId + "...");
		employeService.deleteEmployeById(employeId);
		
		List<Employe> employesNewList = employeService.getAllEmployes();
		int employeNewListSize = employesNewList.size();
		
		logger.debug("Table Entreprise current size is now:" + employeNewListSize);
		assertEquals(employeListSize - 1, employeNewListSize);
		logger.info("employe deleted, exiting removeEmploye()...");

	}
*/

	@Test
	public void updateEmployeTest() {
		logger.info("entering updateEmployeTest()...");
		Employe employe = new Employe(employeIdToBeUpdated, "ModifyTestNom", "ModifyTestPrenom", "test@lo.tn", "newpwd", actif, Role.INGENIEUR);
		logger.debug("updating employe to " + employe);
		assertEquals(employeIdToBeUpdated, employeService.addOrUpdateEmploye(employe));
		logger.info("Employe updated, exiting updateEmploye()");

	}

}
