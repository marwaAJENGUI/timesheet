package tn.esprit.spring.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.junit.BeforeClass;
import org.junit.Test;


import nl.jqno.equalsverifier.EqualsVerifier;


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
	public void testDepartementConstructor() {
		Departement actual=new Departement();
		assertNotEquals(null, actual);
	}
	@Test
	public void testDepartementConstructorParam() {
		Departement actual=new Departement(name);
		assertNotEquals(null, actual);
		assertEquals(name, actual.getName());
	}
	
	@Test
	public void testId() {
		departement.setId(id);
		assertEquals(id, departement.getId());
	}
	
	@Test
	public void testName() {
		departement.setName(name);
		assertEquals(name,departement.getName());
	}

	@Test
	public void testEmployes() {
		departement.setEmployes(employes);
		assertEquals(employes, departement.getEmployes());
	}
	
	@Test
	public void TestMissions() {
		departement.setMissions(missions);
		assertEquals(missions, departement.getMissions());
	}

	@Test
	public void testEntreprise() {
		departement.setEntreprise(entreprise);
		assertEquals(entreprise, departement.getEntreprise());
	}

	@Test
	public void testEqualsAndHashCode() {
		List<Employe> listEmployes=new ArrayList<Employe>();
		listEmployes.add(new Employe("nom","prenom","email@mail.com",true,Role.INGENIEUR));
		departement.setEmployes(employes);
	    EqualsVerifier.simple().forClass(Departement.class)
	    .withPrefabValues(List.class, departement.getEmployes(), listEmployes)
	    .withIgnoredAnnotations(Entity.class, Id.class)
	    //.suppress(Warning.STRICT_INHERITANCE)
	    //.suppress(Warning.NONFINAL_FIELDS)
	    .verify();
	}
	
}
