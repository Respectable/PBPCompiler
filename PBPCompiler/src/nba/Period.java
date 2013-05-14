package nba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nba.playActions.*;
import nba.playActions.foul.OffensiveChargeFoulType;
import nba.playActions.foul.OffensiveFoulType;

public class Period {

	private PeriodIdentification periodIdentification;
	private ArrayList<Play> plays;
	private ArrayList<Possession> possessions;
	private int periodID;
	
	public Period()
	{
		
	}
	
	public Period(PeriodIdentification periodIdentification, ArrayList<Play> plays)
	{
		this.periodIdentification = periodIdentification;
		this.plays = plays;
		this.possessions = new ArrayList<Possession>();
		this.periodID = -1;
	}
	
	public void AssignPossessions(int gameID, Team homeTeam, Team awayTeam)
	{
		Possession currentPossession = new Possession();
		boolean madeShot = false;
		boolean missedFirstFT = false;
		
		for (Play p : plays)
		{
			if (!currentPossession.TeamsSet() && p.IdentifiesOffense())
			{
				currentPossession = assignTeamRoles(currentPossession, p, homeTeam, awayTeam);
			}
			
			if (!madeShot)
			{
				if (p.GetPlayAction().TerminatesPossession())
				{
					if ((p.GetPlayAction() instanceof Rebound) && missedFirstFT)
					{
						currentPossession.AddPlay(p);
						missedFirstFT = false;
					}
					else
					{
						currentPossession.AddPlay(p);
						possessions.add(currentPossession);
						madeShot = false;
						missedFirstFT = false;
						currentPossession = new Possession();
					}
				}
				else
				{
					if((p.GetPlayAction() instanceof Shot))
					{
						madeShot = ((Shot)p.GetPlayAction()).ShotMade();
					}
					if((p.GetPlayAction() instanceof FreeThrow))
					{
						FreeThrow ft = (FreeThrow)p.GetPlayAction();
						if (!ft.lastFreeThrow())
						{
							missedFirstFT = !ft.made();
						}
					}
					currentPossession.AddPlay(p);
				}
			}
			else
			{
				if (PossessionContinuation(currentPossession, p, homeTeam, awayTeam))
				{
					if((p.GetPlayAction() instanceof FreeThrow))
					{
						FreeThrow ft = (FreeThrow)p.GetPlayAction();
						if (!ft.lastFreeThrow())
						{
							missedFirstFT = !ft.made();
						}
					}
					currentPossession.AddPlay(p);
				}
				else
				{
					if ((p.GetPlayAction() instanceof Rebound) && missedFirstFT)
					{
						currentPossession.AddPlay(p);
						missedFirstFT = false;
					}
					else if (p.GetPlayAction().TerminatesPossession())
					{
						possessions.add(currentPossession);
						currentPossession = new Possession();
						madeShot = false;
						missedFirstFT = false;
						currentPossession.AddPlay(p);
						currentPossession = assignTeamRoles(currentPossession, p, homeTeam, awayTeam);
						possessions.add(currentPossession);
						currentPossession = new Possession();
					}
					else
					{
						possessions.add(currentPossession);
						currentPossession = new Possession();
						madeShot = false;
						missedFirstFT = false;
						currentPossession.AddPlay(p);
						if((p.GetPlayAction() instanceof Shot))
						{
							madeShot = ((Shot)p.GetPlayAction()).ShotMade();
						}
						if (p.IdentifiesOffense())
						{
							currentPossession = assignTeamRoles(currentPossession, p, homeTeam, awayTeam);
						}
					}
				}
			}
		}
	}
	
	public void debugPossessions(String indent, Team homeTeam, Team awayTeam)
	{
		System.out.println(indent + this.periodIdentification.GetPeriodNumber() + " " 
				+ this.periodIdentification.GetPeriodType());
		indent = indent+ "\t";
		
		for (Possession p : this.possessions)
		{
			p.debugPossession(indent, homeTeam, awayTeam);
		}
	}
	
	public Possession assignTeamRoles(Possession poss, Play play, Team home, Team away)
	{
		if (play.GetPlayAction() instanceof JumpBall)
		{
			JumpBall jBall = (JumpBall)play.GetPlayAction();
			Player player = jBall.GetPossessionPlayer();
			if (home.HasPlayer(player) && !away.HasPlayer(player))
			{
				poss.SetTeamRoles(home.GetTeamID(), 
						away.GetTeamID());
			}
			else if (away.HasPlayer(player) && !home.HasPlayer(player))
			{
				poss.SetTeamRoles(away.GetTeamID(),
						home.GetTeamID());
			}
		}
		else
		{
			if (play.GetTeam().equals(home))
			{
				poss.SetTeamRoles(home.GetTeamID(), 
						away.GetTeamID());
			}
			else if (play.GetTeam().equals(away))
			{
				poss.SetTeamRoles(away.GetTeamID(),
						home.GetTeamID());
			}
			else
			{
				System.out.println("Unknown Team Found");
				System.exit(-1);
			}
		}
		
		return poss;
	}
	
	public void Compile(int gameID, Team homeTeam, Team awayTeam)
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
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`period` (`period_identifier`,`regulation`) VALUES (?,?);");
		    stmt.setInt(1, this.periodIdentification.GetPeriodInt());
		    stmt.setBoolean(2, this.periodIdentification.PeriodIsRegulation());
		    stmt.executeUpdate();
		    
		    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.periodID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_periods` (`game_id`,`period_id`) VALUES (?,?);");
		    stmt.setInt(1, gameID);
		    stmt.setInt(2, this.periodID);
		    stmt.executeUpdate();
		    
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
		
		for (Possession p : possessions)
		{
			p.compile(this.periodID, homeTeam, awayTeam);
		}
	}
	
	private Team GetTeam(int teamID, Team homeTeam, Team awayTeam)
	{
		if (homeTeam.GetTeamID() == teamID)
		{
			return homeTeam;
		}
		else
		{
			return awayTeam;
		}
	}
	
	private boolean PossessionContinuation(Possession currentPossession, Play play, Team homeTeam, Team awayTeam)
	{
		boolean defensiveFoul, timeout, sub, freeThrow, defensiveTech;
		
		defensiveFoul =  ((play.GetPlayAction() instanceof Foul) 
				&& GetTeam(currentPossession.GetDefenseID(), homeTeam, awayTeam).equals(play.GetTeam()));
		if (defensiveFoul)
		{
			Foul foul = (Foul)play.GetPlayAction();
			if (foul.getFoulType() instanceof OffensiveFoulType || foul.getFoulType() instanceof OffensiveChargeFoulType)
			{
				defensiveFoul = false;
			}
		}
		
		timeout = play.GetPlayAction() instanceof Timeout;
		sub = play.GetPlayAction() instanceof Substitution;
		freeThrow = ((play.GetPlayAction() instanceof FreeThrow) &&
				GetTeam(currentPossession.GetOffenseID(), homeTeam, awayTeam).equals(play.GetTeam()));
		defensiveTech = ((play.GetPlayAction() instanceof Technical) 
				&& GetTeam(currentPossession.GetDefenseID(), homeTeam, awayTeam).equals(play.GetTeam()));
		
		return defensiveFoul || timeout || sub || freeThrow || defensiveTech;
	}
}
