package tn.esprit.spring.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;


public class MissionTest {

	private static int id;

	private static String name;

	private static String description;
	
	private static Departement departement;
	
	private static List<Timesheet> timesheets;
	
	private static Mission mission;
	
	@BeforeClass
	public static void setUp() {
		name="mission name";
		description="mission description";
		id=1;
		mission=new Mission();
		departement=new Departement();
		timesheets=new ArrayList<Timesheet>(); 
	}
	
	@Test
	public void missionConstructorTest() {
		Mission actual=new Mission();
		assertNotEquals(null, actual);
	}
	
	@Test
	public void missionConstructorParamTest() {
		Mission actual=new Mission(name,description);
		assertNotEquals(null, actual);
		assertEquals(name, actual.getName());
		assertEquals(description, actual.getDescription());
	}
	
	@Test
	public void idTest() {
		mission.setId(id);
		assertEquals(id, mission.getId());
	}

	@Test
	public void nameTest() {
		mission.setName(name);
		assertEquals(id, mission.getName());
	}
	
	@Test
	public void descriptionTest() {
		mission.setDescription(description);
		assertEquals(id, mission.getDescription());
	}
	
	@Test
	public void departementTest() {
		mission.setDepartement(departement);
		assertEquals(departement, mission.getDepartement());
	}
	
	@Test
	public void timesheetsTest() {
		mission.setTimesheets(timesheets);
		assertEquals(timesheets,mission.getTimesheets());
	}
}
