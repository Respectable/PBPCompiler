package nba;

public class Play {

	private TimeStamp timeStamp;
	private PlayAction playAction;
	
	public Play(TimeStamp timeStamp, PlayAction playAction)
	{
		this.timeStamp = timeStamp;
		this.playAction = playAction;
	}
}
