package tn.esprit.spring.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class DepartementTest {
	private static int id;
	private static String name;
	private static List<Employe> employes=new ArrayList<Employe>();
	private static List<Mission> missions= new ArrayList<Mission>();
	private static Entreprise entreprise;
	private static Departement departement;
	
	@BeforeClass
	public static void setUp() {
		name="employee name";
		id=3;
		departement=new Departement();
		String nb;
		for (int i = 1;i<4;i++) {
			nb=Integer.toString(i);
			missions.add(new Mission("mission"+nb,"description"));
			employes.add(new Employe("nom"+nb,"prenom"+nb,"email"+nb+"@mail.com",true,Role.INGENIEUR)); 
		}
		
	}
	
	@Test
	public void DepartementConstructorTest() {
		Departement actual=new Departement();
		assertNotEquals(null, actual);
	}
	@Test
	public void DepartementConstructorParamTest() {
		Departement actual=new Departement(name);
		assertNotEquals(null, actual);
		assertEquals(name, actual.getName());
	}
	
	@Test
	public void idTest() {
		departement.setId(id);
		assertEquals(id, departement.getId());
	}
	
	@Test
	public void nameTest() {
		departement.setName(name);
		assertEquals(name,departement.getName());
	}

	@Test
	public void employesTest() {
		departement.setEmployes(employes);
		assertEquals(employes, departement.getEmployes());
	}
	
	@Test
	public void missionsTest() {
		departement.setMissions(missions);
		assertEquals(missions, departement.getMissions());
	}

	@Test
	public void entrepriseTest() {
		departement.setEntreprise(entreprise);
		assertEquals(entreprise, departement.getEntreprise());
	}


}
