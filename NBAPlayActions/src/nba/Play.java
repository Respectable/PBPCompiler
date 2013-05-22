package nba;

import visitors.Visitable;
import visitors.Visitor;

public class Play implements Visitable {

	private TimeStamp timeStamp;
	private PlayAction playAction;
	
	public Play(TimeStamp timeStamp, PlayAction playAction)
	{
		this.timeStamp = timeStamp;
		this.playAction = playAction;
	}
	
	public PlayAction GetPlayAction()
	{
		return this.playAction;
	}
	
	public TimeStamp GetTimeStamp()
	{
		return this.timeStamp;
	}
	
	public boolean IdentifiesOffense()
	{
		return playAction.IdentifiesOffense();
	}
	
	public Team GetTeam()
	{
		return timeStamp.GetTeam();
	}
	
	public void debugPlay(String indent)
	{
		System.out.println(indent + "(" + timeStamp.debug() +
				") " + playAction.debug());
	}
	
	public void compile(Possession possession, Team home, Team away)
	{
		timeStamp.compile(possession,home,away);
		playAction.compile(possession,home,away, timeStamp);
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
