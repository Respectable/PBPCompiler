package nba.playActions.technical;

import visitors.Visitor;
import nba.playActions.Technical;

public class USLTechnical extends Technical{

	public USLTechnical()
	{
		super();
		super.technicalType = "Unsportsmanlike";
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
