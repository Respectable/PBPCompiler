package nba;

import visitors.Visitable;
import visitors.Visitor;

public class TimeStamp implements Visitable {

	protected String time;
	
	public TimeStamp()
	{
		time = null;
	}
	
	public TimeStamp(String time)
	{
		this.time = time;
	}
	
	public Team GetTeam()
	{
		return null;
	}
	
	public String GetTime()
	{
		return time;
	}
	
	public double GetTimeDouble()
	{
		String min,sec,tenth;
		
		min = this.time.substring(0, this.time.indexOf(":"));
		if (this.time.indexOf(".") == -1)
		{
			sec = this.time.substring(this.time.indexOf(":") + 1).trim();
			tenth = "0";
		}
		else
		{
			sec = this.time.substring(this.time.indexOf(":") + 1, this.time.indexOf("."));
			tenth = sec = this.time.substring(this.time.indexOf(".") + 1).trim();
		}
		
		return (60.0 * Double.parseDouble(min)) + Double.parseDouble(sec) + (Double.parseDouble(tenth) / 10.0);
	}
	
	public String debug()
	{
		return time;
	}
	
	public void compile(Possession possession, Team home, Team away)
	{
		
	}

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
