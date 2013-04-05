package nba.playActions.foul;

import nba.Player;
import nba.playActions.Foul;

public class DoublePersonalFoul extends Foul {

	private Player player1, player2;
	
	public DoublePersonalFoul(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
	}
	
}
