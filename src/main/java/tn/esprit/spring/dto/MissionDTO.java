package tn.esprit.spring.dto;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Timesheet;

public class MissionDTO {
	
	private int id;

	private String name;

	private String description;
	
	private Departement departement;
	
	private  List<Timesheet> timesheets;
	
	public MissionDTO() {
		super();
	}

	public MissionDTO(String name, String description){
		this.name = name;
		this.description = description;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public List<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}
}
