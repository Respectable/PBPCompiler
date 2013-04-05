package nba.playActions;

import nba.PlayAction;

public class Timeout extends PlayAction{

	private String timeoutType;
	
	public Timeout(String TimeoutType)
	{
		this.timeoutType = timeoutType;
	}
}
