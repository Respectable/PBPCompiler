package nba.playActions.ejection;

public class FlagrantEjectionEnding extends EjectionEnding {

	private int flagrantNum;
	
	public FlagrantEjectionEnding(int flagrantNum)
	{
		this.flagrantNum = flagrantNum;
	}
	
	public int GetFlagrantNum()
	{
		return this.flagrantNum;
	}
}
