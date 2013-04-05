package nba.playActions;

import nba.PlayAction;
import nba.Player;
import nba.playActions.jumpBall.JumpBallEnding;

public class JumpBall extends PlayAction {

	Player player1, player2;
	JumpBallEnding ending;
	
	public JumpBall(Player player1, Player player2, JumpBallEnding ending)
	{
		this.player1 = player1;
		this.player2 = player2;
		this.ending = ending;
	}
}
