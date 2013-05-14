package nba.playActions.technical;

import nba.Player;
import nba.playActions.Technical;

public class DoubleTechnical extends Technical {

	private Player player1, player2;
	
	public DoubleTechnical(Player player1, Player player2)
	{
		super();
		this.player1 = player1;
		this.player2 = player2;
		this.technicalType = "Double";
	}
}
