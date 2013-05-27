package visitors;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import nba.*;
import nba.playActions.*;
import nba.playActions.foul.DoublePersonalFoul;
import nba.playActions.technical.*;
import nba.playActions.turnover.TeamTurnover;
import nba.timeStamps.*;
import java.util.Date;

public class GameSetupVisitor extends MySQLVisitor {

	private String fileName;
	private int seasonID, gameID;
	private int homeTeamID, awayTeamID;
	
	public GameSetupVisitor(String path, String userName, String password)
			throws ClassNotFoundException, SQLException 
	{
		super(path, userName, password);
		homeTeamID = -1;
		awayTeamID = -1;
	}
	
	public GameSetupVisitor(String path, String userName,
			String password, String fileName)
			throws ClassNotFoundException, SQLException 
	{
		super(path, userName, password);
		this.fileName = fileName;
	}

	@Override
	public void visit(Game game) 
	{
		String gameInfo = fileName.substring(fileName.lastIndexOf('/') + 1);
		Date gameDate = new Date();
		
		//get date of game from fileName argument
		try {
			gameDate = new SimpleDateFormat("yyyyMMdd").parse(gameInfo.substring(0,9));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		game.setGameDate(gameDate);
		
		//using game date, query for current season's ID
		try {
		    
		    stmt = conn.prepareStatement("SELECT * FROM season WHERE " +
		                            "start_date <= ? AND end_date >= ?");
		    stmt.setDate(1, ConvertDate(game.getGameDate()));
		    stmt.setDate(2, ConvertDate(game.getGameDate()));
		    rs = stmt.executeQuery();
		    rs.next();
		    this.seasonID = rs.getInt("season_id");
		    
		} 
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		//get teams participating in game
		game.setAwayTeam(new Team(gameInfo.substring(8, 11), gameDate));
		game.setHomeTeam(new Team(gameInfo.substring(11, 14), gameDate));
		//collect team info from database
		setAwayTeamID(game);
		game.getAwayTeam().accept(this);
		setTeamPlayers(game, game.getAwayTeam());
		setHomeTeamID(game);
		game.getHomeTeam().accept(this);
		setTeamPlayers(game, game.getAwayTeam());
		
		
		
	    
		try
		{
			//create new record in game table
			stmt = conn.prepareStatement("INSERT INTO `nba`.`game` (`date_played`) VALUES (?);");
			stmt.setDate(1, ConvertDate(gameDate));
			stmt.executeUpdate();
			
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

		    if (rs.next()) {
		        this.gameID = rs.getInt(1);
		    } else {
		        // throw an exception from here
		    }
		    
		    //create new record in game_season table, matching game to its season
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_season` (`game_id`,`season_id`) VALUES (?,?);");
		    stmt.setInt(1, this.gameID);
		    stmt.setInt(2, this.seasonID);
		    stmt.executeUpdate();
		    
		    //create new record in game_teams table, matching teams to game
		    stmt = conn.prepareStatement("INSERT INTO `nba`.`game_teams` (`game_id`,`home_team_id`,`away_team_id`) VALUES (?,?,?);");
		    stmt.setInt(1, this.gameID);
		    stmt.setInt(2, game.getHomeTeam().GetTeamID());
		    stmt.setInt(2, game.getAwayTeam().GetTeamID());
		    stmt.executeUpdate();
		    
		    this.closeConnection();
		}
		catch (SQLException ex) 
		{
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
	}
	
	private java.sql.Date ConvertDate(Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	public int getSeasonID()
	{
		return this.seasonID;
	}
	
	public int getGameID()
	{
		return this.gameID;
	}
	
	public int getHomeTeamID()
	{
		return this.homeTeamID;
	}
	
	public int getAwayTeamID()
	{
		return this.awayTeamID;
	}
	
	private int findTeamID(Team team)
	{
		int teamID = -1;
		
		try {
			stmt = conn.prepareStatement("SELECT * FROM team_season WHERE " +
			        "three_letter_code = ? AND season_id = ?");
			stmt.setString(1, team.GetThreeLetterCode());
		    stmt.setInt(2, this.seasonID);
		    rs = stmt.executeQuery();
		    teamID = rs.getInt("team_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return teamID;
	}
	
	private void setTeamPlayers(Game game, Team team)
	{
		Player player;
		//query that returns the players on the team at a given date
	    try {
			stmt = conn.prepareStatement("SELECT * FROM player_team WHERE " +
			        "team_id = ? AND start_date <= ? AND end_date >= ?");
			stmt.setInt(1, team.GetTeamID());
		    stmt.setDate(2, ConvertDate(game.getGameDate()));
		    stmt.setDate(3, ConvertDate(game.getGameDate()));
		    rs = stmt.executeQuery();
		    
		    while(rs.next())
		    {
		    	player = new Player(rs.getInt("player_id"));
		    	player.accept(this);
		    	team.addPlayer(player);
		    }
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	}
	
	private void setHomeTeamID(Game game)
	{
		this.homeTeamID = findTeamID(game.getHomeTeam());
	}
	
	private void setAwayTeamID(Game game)
	{
		this.awayTeamID = findTeamID(game.getAwayTeam());
	}

	@Override
	public void visit(InstantReplay instantReplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Period period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Play play) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlayAction playAction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Player player) 
	{
		
		try {
			stmt = conn.prepareStatement("SELECT * FROM player WHERE " +
                    "player_id = ?");
			stmt.setInt(1, player.getID());
		    rs = stmt.executeQuery();
		    rs.next();
		    player.setFirstName(rs.getString("first_name"));
		    player.setLastName(rs.getString("last_name"));
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void visit(Possession possession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Team team) 
	{
		try {
		    //query to return team name and location info
		    stmt = conn.prepareStatement("SELECT * FROM team_season WHERE " +
                    "three_letter_code = ? AND season_id = ?");
		    stmt.setString(1, team.GetThreeLetterCode());
		    stmt.setInt(2, this.seasonID);
		    rs = stmt.executeQuery();
		    team.setTeamName(rs.getString("team_name"));
		    team.setCity(rs.getString("city_state"));
		    
		} 
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	@Override
	public void visit(TimeStamp timeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Ejection ejection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Foul foul) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FreeThrow freeThrow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpBall jumpBall) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Rebound rebound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Shot shot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Substitution substitution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Technical technical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Timeout timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Turnover turnover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Violation violation) {
		// TODO Autogenerated method stub
		
	}

	@Override
	public void visit(DoublePersonalFoul doublePersonal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleTechnical doubleTech) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DelayTechnical DelayTech) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TauntingTechnical tauntingTech) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(USLTechnical uslTech) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TeamTurnover teamTurnover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DelayTimeStamp delayTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DoubleTimeStamp doubleTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(DPTimeStamp dpTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRTimeStamp irTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JumpTimeStamp jumpTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlayerTimeStamp playerTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TeamTimeStamp teamTimeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TimeoutTimeStamp timeoutTimeStamp) {
		// TODO Auto-generated method stub
		
	}
	
	

}
