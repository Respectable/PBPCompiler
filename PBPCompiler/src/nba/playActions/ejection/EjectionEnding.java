package nba.playActions.ejection;

public class EjectionEnding {
	
	private String EjectionType;
	
	public EjectionEnding()
	{
		EjectionType = "";
	}
	
	public EjectionEnding(String EjectionType)
	{
		this.EjectionType = EjectionType;
	}
	
	public String GetEjectionType()
	{
		return EjectionType;
	}

}
