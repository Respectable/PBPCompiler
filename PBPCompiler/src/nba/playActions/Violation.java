package nba.playActions;

import nba.PlayAction;
import nba.playActions.violation.ViolationType;

public class Violation extends PlayAction{

	protected ViolationType violationtype;
	
	public Violation()
	{
		
	}
	
	public Violation(ViolationType violationType)
	{
		this.violationtype = violationType;
	}
	
	@Override
	public String debug()
	{
		return "Violation";
	}
}
