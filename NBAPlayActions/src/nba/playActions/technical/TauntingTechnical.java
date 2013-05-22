package nba.playActions.technical;

import visitors.Visitor;
import nba.playActions.Technical;

public class TauntingTechnical extends Technical{

	public TauntingTechnical()
	{
		super();
		super.technicalType = "Taunting";
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
