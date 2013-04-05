package nba.playActions.shot;

public class ShotOutcome {

	private boolean shotMade;
	private Assist assist;
	private Block block;
	
	public ShotOutcome(boolean shotMade, Assist assist)
	{
		this.shotMade = shotMade;
		this.assist = assist;
	}
	
	public ShotOutcome(boolean shotMade, Block block)
	{
		this.shotMade = shotMade;
		this.block = block;
	}
}
