package nba;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import visitors.Visitable;
import visitors.Visitor;

public class Team implements Visitable {

	private String teamName, threeLetterCode, city, pbpName;
	private int teamID;
	private ArrayList<Player> players;
	
	
	public Team(String threeLetterCode, Date gameDate)
	{
		this.threeLetterCode = threeLetterCode;
		this.pbpName = "";
		players = new ArrayList<Player>();
	}
	
	public Team(String teamName)
	{
		pbpName = teamName.trim();
		city = "";
		this.teamName = "";
		threeLetterCode = "";
		teamID = -1;
	}
	
	public String GetTeamName()
	{
		return this.teamName;
	}
	
	public String FullName()
	{
		return this.city + " " + this.teamName;
	}
	
	public int GetTeamID()
	{
		return this.teamID;
	}
	
	public String GetThreeLetterCode()
	{
		return this.threeLetterCode;
	}
	
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	public boolean HasDuplicatePlayers()
	{
		for (Player p : this.players)
		{
			p.SetDuplicate(players.indexOf(p) != players.lastIndexOf(p));
			if (p.IsDuplicate())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean HasPlayer(Player player)
	{
		return players.contains(player);
	}
	
	public Player GetPlayer(Player player)
	{
		return players.get(players.indexOf(player));
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Team t = (Team) obj;
        return this.FullName().equals(t.FullName()) || this.teamID == t.GetTeamID()
        		|| this.FullName().equals(t.pbpName) || this.pbpName.equals(t.FullName());
                
    }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
	
	
}
