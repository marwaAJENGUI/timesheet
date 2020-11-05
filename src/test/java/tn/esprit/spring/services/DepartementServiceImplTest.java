package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DepartementServiceImplTest{
	@Autowired
	DepartementRepository deptRepoistory;
	
	@Autowired
	DepartementServiceImpl deptService;
	
	/*
	@Test
	public void testGetAllDepartements() {
		assertEquals(((List<Departement>)deptRepoistory.findAll()).size(),deptService.getAllDepartements().size());	
	}
	*/
	
}
