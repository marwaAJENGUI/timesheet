package tn.esprit.spring.dto;




public class TimesheetDTO {
	private TimesheetPKDTO timesheetPK;
	
	private MissionDTO mission;
	
	
	private EmployeDTO employe;
	
	
	private boolean isValide;
	

	public boolean getIsValide() {
		return isValide;
	}

	public void setValide(boolean isValide) {
		this.isValide = isValide;
	}

	public TimesheetPKDTO getTimesheetPK() {
		return timesheetPK;
	}

	public void setTimesheetPK(TimesheetPKDTO timesheetPK) {
		this.timesheetPK = timesheetPK;
	}

	public MissionDTO getMission() {
		return mission;
	}

	public void setMission(MissionDTO mission) {
		this.mission = mission;
	}

	public EmployeDTO getEmploye() {
		return employe;
	}

	public void setEmploye(EmployeDTO employe) {
		this.employe = employe;
	}
}
