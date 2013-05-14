package nba.playActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nba.PlayAction;
import nba.Player;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.playActions.jumpBall.JumpBallEnding;
import nba.timeStamps.PlayerTimeStamp;

public class JumpBall extends PlayAction {

	Player player1, player2;
	JumpBallEnding ending;
	int jumpBallID;
	
	public JumpBall(Player player1, Player player2, JumpBallEnding ending)
	{
		this.player1 = player1;
		this.player2 = player2;
		this.ending = ending;
	}
	
	@Override
	public boolean IdentifiesOffense()
	{
		return ending.IdentifiesOffense();
	}
	
	public Player GetPossessionPlayer()
	{
		return ending.GetPlayer();
	}
	
	@Override
	public String debug()
	{
		return "Jump Ball";
	}
	
	@SuppressWarnings("resource")
	@Override
	public void compile(Possession possession, Team home, Team away, TimeStamp timeStamp)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
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
		    
		  
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`jump_ball` VALUES (DEFAULT);");
		    stmt.executeUpdate();
		    
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.jumpBallID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`jump_ball_possession` (`jump_ball_id`,`possession_id`" +
		    								",`time_of_foul`) VALUES (?,?,?);");
		    stmt.setInt(1, this.jumpBallID);
		    stmt.setInt(2, possession.GetPossessionID());
		    stmt.setDouble(3, timeStamp.GetTimeDouble());
		    stmt.executeUpdate();
		    
		    
		    
		    Player idPlayer1;
		    Player idPlayer2;
		    Player possessionPlayer;
		    
		    if (home.HasPlayer(this.player1))
		    {
		    	idPlayer1 = home.GetPlayer(this.player1);
		    	idPlayer2 = away.GetPlayer(this.player2);
		    }
		    else
		    {
		    	idPlayer1 = home.GetPlayer(this.player2);
		    	idPlayer2 = away.GetPlayer(this.player1);
		    }
		    
		    if (this.ending.GetPlayer() != null)
		    {
		    	if (home.HasPlayer(this.ending.GetPlayer()))
			    {
			    	possessionPlayer = home.GetPlayer(this.ending.GetPlayer());
			    }
			    else
			    {
			    	possessionPlayer = away.GetPlayer(this.ending.GetPlayer());
			    }
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`jump_ball_players` (`jump_ball_id`,`player_1_id`," +
						"`player_2_id`, `player_gaining_possession`) VALUES (?,?,?,?);");
			    stmt.setInt(1, this.jumpBallID);
			    stmt.setInt(2, idPlayer1.getID());
			    stmt.setInt(3, idPlayer2.getID());
			    stmt.setInt(4, possessionPlayer.getID());
			    stmt.executeUpdate();
		    }
		    else
		    {
		    	stmt = conn.prepareStatement("INSERT INTO `nba`.`jump_ball_players` (`jump_ball_id`,`player_1_id`," +
						"`player_2_id`) VALUES (?,?,?);");
			    stmt.setInt(1, this.jumpBallID);
			    stmt.setInt(2, idPlayer1.getID());
			    stmt.setInt(3, idPlayer2.getID());
			    stmt.executeUpdate();
		    }
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
}
