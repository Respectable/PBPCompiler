package nba;

import java.util.ArrayList;

public class Unit {

	private ArrayList<Player> players;
	
	public Unit()
	{
		players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) throws Exception
	{
		if (players.size() < 5)
		{
			players.add(player);
		}
		else
		{
			throw new Exception();
		}
	}
	
	public ArrayList<Player> unitPlayers()
	{
		return players;
	}
	
	public boolean unitHasPlayer(Player player)
	{
		return players.contains(player);
	}
}
