package nba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player {

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
		getName();
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
	
	private void getName()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/nba",
		                                   "root","*******");
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM player WHERE " +
		                            "player_id = " + this.playerID);
		    rs.next();
		    this.firstName = rs.getString("first_name");
		    this.lastName = rs.getString("last_name");
		    
		} 
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally
		{
			if (rs != null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null)
			{
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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


			
}
