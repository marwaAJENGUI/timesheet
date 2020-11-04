package tn.esprit.spring.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class MissionExterneTest {

	private static int id;

	private static String name;

	private static String description;
	
	private static Departement departement;
	
	private static List<Timesheet> timesheets;
	
	private static String emailFacturation;

	private static float tauxJournalierMoyen;

	private static MissionExterne missionExterne;
	
	@BeforeClass
	public static void setUp() {
		name="mission name";
		description="mission description";
		id=1;
		missionExterne =new MissionExterne();
		departement=new Departement();
		timesheets=new ArrayList<Timesheet>(); 
	}
	
	@Test
	public void missionExterneConstructorTest() {
		MissionExterne actual=new MissionExterne();
		assertNotEquals(null, actual);
	}
	
	@Test
	public void missionExterneConstructorParamTest() {
		MissionExterne actual=new MissionExterne(name, description, emailFacturation, tauxJournalierMoyen);
		assertNotEquals(null, actual);
		assertEquals(name, actual.getName());
		assertEquals(description, actual.getDescription());
		assertEquals(emailFacturation, actual.getEmailFacturation());
		assertEquals(tauxJournalierMoyen, actual.getTauxJournalierMoyen());
	}
	
	@Test
	public void emailFacturationTest() {
		missionExterne.setEmailFacturation(emailFacturation);
		assertEquals(emailFacturation, missionExterne.getEmailFacturation());
	}
	
	@Test
	public void tauxJournalierMoyenTest() {
		missionExterne.setTauxJournalierMoyen(tauxJournalierMoyen);
		assertEquals(tauxJournalierMoyen, missionExterne.getTauxJournalierMoyen());
	}
}
