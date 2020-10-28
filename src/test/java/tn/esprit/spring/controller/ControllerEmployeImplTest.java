package tn.esprit.spring.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		login="test";
		password="test";
		headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    strDateDebut="28-10-2020";
	    strDateFin="03-11-2020";
	}
	
	@Test
	public void doLoginTest() {
		logger.debug("entering doLoginTest()...");
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
				logger.debug("Authentication failed of user " + login + "/" + password + " failed");
				FacesMessage facesMessage = new FacesMessage(
						"Login Failed: Please check your username/password and try again.");
				FacesContext.getCurrentInstance().addMessage("form:btn", facesMessage);
				logger.warn("exiting doLogin() with failed authentication...");
			}
			
			assertEquals(navigateTo,"/pages/admin/welcome.xhtml?faces-redirect=true");
			assertTrue(loggedIn);
		} catch (Exception e) {
			logger.error("exiting doLogin() with "+ e + "error");
		}
	}
	
	@Test
	public void doLogoutTest() {
		logger.debug("entering doLogoutTest()...");
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			logger.debug("logging out...");
			logger.debug("exiting doLogout()");
			String result = "/login.xhtml?faces-redirect=true";
			assertEquals(result, "/login.xhtml?faces-redirect=true");
		} catch (Exception e) {
			logger.error("exiting doLogoutTest() with "+ e + "error");
		}
	}
	
	

}
