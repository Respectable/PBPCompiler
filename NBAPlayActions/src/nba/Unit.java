package nba;

import java.util.ArrayList;

import visitors.Visitable;
import visitors.Visitor;

public class Unit implements Visitable {

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

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}
}
