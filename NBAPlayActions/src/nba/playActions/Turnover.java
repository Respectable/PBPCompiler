package nba.playActions;

import visitors.Visitor;
import nba.PlayAction;
import nba.playActions.turnover.Steal;
import nba.playActions.turnover.TurnoverType;


public class Turnover extends PlayAction{

	protected TurnoverType turnoverType;
	protected Steal steal;
	
	public Turnover(TurnoverType turnoverType) 
	{
		this.turnoverType = turnoverType;
		this.steal = null;
	}
	
	public Turnover(TurnoverType turnoverType, Steal steal)
	{
		this.turnoverType = turnoverType;
		this.steal = steal;
		
	}
	
	@Override
	public boolean TerminatesPossession()
	{
		return true;
	}
	
	@Override
	public boolean IdentifiesOffense()
	{
		return true;
	}
	
	@Override
	public String debug()
	{
		return "Turnover";
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

}
