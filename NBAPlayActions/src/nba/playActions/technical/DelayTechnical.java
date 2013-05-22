package nba.playActions.technical;

import visitors.Visitor;
import nba.playActions.Technical;

public class DelayTechnical extends Technical{

	public DelayTechnical()
	{
		super();
		super.technicalType = "Delay Of Game";
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
