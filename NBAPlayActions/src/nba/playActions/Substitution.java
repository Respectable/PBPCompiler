package nba.playActions;

import visitors.Visitor;
import nba.PlayAction;
import nba.Player;

public class Substitution extends PlayAction{

	private Player player;
	
	public Substitution(Player player)
	{
		this.player = player;
	}
	
	@Override
	public String debug()
	{
		return "Substitution";
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
