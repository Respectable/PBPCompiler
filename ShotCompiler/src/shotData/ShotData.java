package shotData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShotData {
	
	String playerName, time;
	int quarter, x, y;
	boolean madeShot;
	boolean homeTeam;
	boolean overtime;
	
	public ShotData()
	{
		
	}
	
	public ShotData(String player, String description, int quarter,
			int x, int y, String madeShot, String homeTeam)
	{
		this.playerName = player;
		this.time = parseTime(description);
		this.quarter = setQuarter(quarter, description);
		this.x = x;
		this.y = y;
		this.madeShot = parseMadeShot(madeShot);
		this.homeTeam = parseHomeTeam(homeTeam);
		this.overtime = false;
	}
	
	private String parseTime(String description)
	{
		Pattern p = Pattern.compile("[0-9]*:[0-9]*");
		Matcher m = p.matcher(description);
		if (m.find())
			return m.group();
		else
			return "";
	}
	
	private int setQuarter(int quarter, String description)
	{
		if (quarter == 5)
		{
			this.overtime = true;
			if (description.contains("1st"))
			{
				return 1;
			}
			else if (description.contains("2nd"))
			{
				return 2;
			}
			else if (description.contains("3rd"))
			{
				return 3;
			}
			else if (description.contains("4th"))
			{
				return 4;
			}
			else if (description.contains("5th"))
			{
				return 5;
			}
			else if (description.contains("6th"))
			{
				return 6;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			return quarter;
		}
	}
	
	private boolean parseMadeShot(String madeShot)
	{
		return madeShot.equals("true");
	}
	
	private boolean parseHomeTeam(String homeTeam)
	{
		return homeTeam.equals("h");
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.x) + "\t" +
				Integer.toString(getAdjustedY()) + "\t" + Double.toString(getTimeDouble()) +
				"\t" + Boolean.toString(this.madeShot);
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getAdjustedY()
	{
		if (homeTeam)
		{
			return 94 - this.y;
		}
		else
		{
			return this.y;
		}
	}
	
	private double getTimeDouble()
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
		
		return 720 - ((60.0 * Double.parseDouble(min)) + Double.parseDouble(sec) + (Double.parseDouble(tenth) / 10.0)) +
				(720.0 * (this.quarter - 1));
	}

}
