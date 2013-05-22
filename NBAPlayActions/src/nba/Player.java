package nba;

import visitors.Visitable;
import visitors.Visitor;

public class Player implements Visitable {

	private String lastName, firstName;
	private int playerID;
	private boolean duplicateLastName;
	
	public Player(String playerName)
	{
		this.lastName = playerName.trim();
	}
	
	public Player(int playerID)
	{
		this.playerID = playerID;
	}
	
	public int getID()
	{
		return playerID;
	}
	
	public void SetDuplicate(boolean isDuplicate)
	{
		duplicateLastName = isDuplicate;
	}
	
	public boolean IsDuplicate()
	{
		return duplicateLastName;
	}
	
	public void setFirstName(String name)
	{
		this.firstName = name;
	}
	
	public void setLastName(String name)
	{
		this.lastName = name;
	}
	
	public String GetLastName()
	{
		return this.lastName;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Player p = (Player) obj;
        return this.lastName.equals(p.lastName) || this.playerID == p.getID();
                
    }

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}


			
}
