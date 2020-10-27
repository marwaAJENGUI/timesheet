package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Departement implements Serializable {

		private static final long serialVersionUID = -357738161698377833L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	//@JsonManagedReference 
	@JsonIgnore
	@ManyToMany
	private List<Employe> employes;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="departement")
	private List<Mission> missions;
	
	@ManyToOne
	private Entreprise entreprise;

	public Departement() {
		super();
	}
	
	public Departement(String name) {
		this.name = name;
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

	public List<Employe> getEmployes() {
		return employes;
	}

	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employes == null) ? 0 : employes.hashCode());
		result = prime * result + ((entreprise == null) ? 0 : entreprise.hashCode());
		result = prime * result + id;
		result = prime * result + ((missions == null) ? 0 : missions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departement other = (Departement) obj;
		if (employes == null) {
			if (other.employes != null)
				return false;
		} else if (!employes.equals(other.employes))
			return false;
		if (entreprise == null) {
			if (other.entreprise != null)
				return false;
		} else if (!entreprise.equals(other.entreprise))
			return false;
		if (id != other.id)
			return false;
		if (missions == null) {
			if (other.missions != null)
				return false;
		} else if (!missions.equals(other.missions))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



}
