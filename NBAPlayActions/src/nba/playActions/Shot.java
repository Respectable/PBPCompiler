package nba.playActions;

import visitors.Visitor;
import nba.PlayAction;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.playActions.shot.ShotOutcome;
import nba.playActions.shot.ShotType;

public class Shot extends PlayAction{

	private ShotType shotType;
	private ShotOutcome shotOutcome;
	private int shotID;
	
	public Shot(ShotType shotType, ShotOutcome shotOutcome)
	{
		this.shotType = shotType;
		this.shotOutcome = shotOutcome;
	}
	
	public boolean ShotMade()
	{
		return shotOutcome.ShotMade();
	}
	
	public int getShotID()
	{
		return this.shotID;
	}
	
	@Override
	public boolean IdentifiesOffense()
	{
		return true;
	}
	
	@Override
	public String debug()
	{
		return "Shot";
	}
	
	@Override
	public void compile(Possession possession, Team home, Team away, TimeStamp timestamp)
	{
		
	}
	
	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
