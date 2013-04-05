package nba.playActions;

import nba.PlayAction;
import nba.playActions.ejection.EjectionEnding;

public class Ejection extends PlayAction{

	private EjectionEnding ejectionEnding;
	
	public Ejection(EjectionEnding ejectionEnding)
	{
		this.ejectionEnding = ejectionEnding;
	}
}
