package tn.esprit.spring.dto;

public class MissionExterneDTO extends MissionDTO{
	
	private String emailFacturation;
	
	
	private float tauxJournalierMoyen;

	
	public MissionExterneDTO() {
		super();
	}


	public MissionExterneDTO(String name, String description, String emailFacturation, float tauxJournalierMoyen) {
		super(name, description);
		this.emailFacturation = emailFacturation;
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}


	public String getEmailFacturation() {
		return emailFacturation;
	}


	public void setEmailFacturation(String emailFacturation) {
		this.emailFacturation = emailFacturation;
	}


	public float getTauxJournalierMoyen() {
		return tauxJournalierMoyen;
	}


	public void setTauxJournalierMoyen(float tauxJournalierMoyen) {
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}
}
