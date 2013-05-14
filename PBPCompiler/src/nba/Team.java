package nba;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Team {

	private String teamName, threeLetterCode, city, pbpName;
	private int teamID;
	private ArrayList<Player> players;
	
	
	public Team(String threeLetterCode, Date gameDate, int seasonID)
	{
		this.threeLetterCode = threeLetterCode;
		this.pbpName = "";
		players = new ArrayList<Player>();
		initializeTeam(seasonID, gameDate);
	}
	
	public Team(String teamName)
	{
		pbpName = teamName.trim();
		city = "";
		this.teamName = "";
		threeLetterCode = "";
		teamID = -1;
	}
	
	private void initializeTeam(int seasonID, Date gameDate)
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
		    rs = stmt.executeQuery("SELECT * FROM team_season WHERE " +
		                            "three_letter_code = '" + threeLetterCode +
		                            "' AND season_id = " + seasonID);
		    rs.next();
		    this.teamName = rs.getString("team_name");
		    this.teamID = rs.getInt("team_id");
		    this.city = rs.getString("city_state");
		    
		    java.sql.Date sqlDate = new java.sql.Date(gameDate.getTime());
		    
		    rs = stmt.executeQuery("SELECT * FROM player_team WHERE " +
                    "team_id = " + this.teamID + " AND start_date <= '"
	                + sqlDate + "' AND end_date >= '"
	                + sqlDate + "'");
		    
		    while(rs.next())
		    {
		    	players.add(new Player(rs.getInt("player_id")));
		    }
		    
		    CheckDuplicatePlayers();
		    
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
	
	private void CheckDuplicatePlayers()
	{
		for (Player p : this.players)
		{
			p.SetDuplicate(players.indexOf(p) != players.lastIndexOf(p));
			if (p.IsDuplicate())
			{
				System.out.println("Duplicate Player with last name " + p.GetLastName() + " found. Exiting program.");
				System.exit(1);
			}
		}
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
	
	
}
