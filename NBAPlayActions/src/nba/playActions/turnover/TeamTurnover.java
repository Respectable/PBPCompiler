package nba.playActions.turnover;

import visitors.Visitor;
import nba.playActions.Turnover;

public class TeamTurnover extends Turnover {
	
	public TeamTurnover(TurnoverType turnoverType)
	{
		super(turnoverType);
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
