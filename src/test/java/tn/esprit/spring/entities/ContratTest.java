package tn.esprit.spring.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class ContratTest {
	
	private static int reference;
	private static Date dateDebut;
	private static String typeContrat;
	private static float telephone;
	private static Employe employe;
	private static float salaire;
	
	private static Contrat contrat;
	
	@BeforeClass
	public static void setUp() {
		reference=2;
		typeContrat="type contrat";
		salaire=1000;
		dateDebut=new Date(2020,11,5);
		telephone=99999999;
		employe=new Employe("nom","prenom","email@mail.com",true,Role.INGENIEUR);
		

		contrat=new Contrat();

}
	
	@Test
	public void testContratConstructor() {
		Contrat actual=new Contrat();
		assertNotEquals(null, actual);
	}
	@Test
	public void testContratConstructorParam() {
		Contrat actual=new Contrat(dateDebut,typeContrat,salaire);
		assertNotEquals(null, actual);
		assertEquals(dateDebut, actual.getDateDebut());
		assertEquals(typeContrat, actual.getTypeContrat());
		assertEquals(salaire, actual.getSalaire(), 0.0001);
		
	}
	
	@Test
	public void testReference() {
		contrat.setReference(reference);
		assertEquals(reference, contrat.getReference());
	}
	
	@Test
	public void testDateDebut() {
		contrat.setDateDebut(dateDebut);
		assertEquals(dateDebut, contrat.getDateDebut());
	}
	
	@Test
	public void testTypeContrat() {
		contrat.setTypeContrat(typeContrat);
		assertEquals(typeContrat, contrat.getTypeContrat());
	}
	
	@Test
	public void testSalaire() {
		contrat.setSalaire(salaire);
		assertEquals(salaire, contrat.getSalaire(), 0.0001);
	}
	
	@Test
	public void testEmploye() {
		contrat.setEmploye(employe);
		assertEquals(employe, contrat.getEmploye());
	}
}
