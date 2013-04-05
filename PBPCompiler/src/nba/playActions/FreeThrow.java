package nba.playActions;

import nba.PlayAction;
import nba.playActions.freeThrow.FreeThrowPredicate;

public class FreeThrow extends PlayAction{
	
	String freeThrowType;
	FreeThrowPredicate outcome;
	
	public FreeThrow(String freeThrowType, FreeThrowPredicate outcome)
	{
		this.freeThrowType = freeThrowType;
		this.outcome = outcome;
	}

}
