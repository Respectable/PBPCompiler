package nba;

import visitors.Visitable;
import visitors.Visitor;

public class PlayAction implements Visitable 
{

	public PlayAction()
	{
		
	}
	
	public boolean TerminatesPossession()
	{
		return false;
	}
	
	public boolean IdentifiesOffense()
	{
		return false;
	}
	
	public String debug()
	{
		return "";
	}
	
	public void compile(Possession possession, Team home, Team away, TimeStamp timeStamp)
	{
		
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
