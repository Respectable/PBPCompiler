package nba.playActions;

import nba.PlayAction;
import nba.Player;

public class Substitution extends PlayAction{

	private Player player;
	
	public Substitution(Player player)
	{
		this.player = player;
	}
}
