package visitors;

import java.sql.SQLException;
import java.util.ArrayList;

import shotCompiler.ShotCompiler;
import shotData.ShotData;

import nba.*;
import nba.playActions.*;
import nba.playActions.foul.DoublePersonalFoul;
import nba.playActions.technical.*;
import nba.playActions.turnover.TeamTurnover;
import nba.timeStamps.*;

public class ShotLocationVisitor extends MySQLVisitor{

	private ArrayList<ArrayList<ShotData>> gameShots;
	private ArrayList<ShotData> currentGame;
	private Double currentPlayTime;
	private Player currentPlayer;
	private int currentPeriod;
	private boolean overtime;
	
	public ShotLocationVisitor(String path, String userName, String password)
			throws ClassNotFoundException, SQLException 
	{
		super(path, userName, password);
		this.gameShots = new ArrayList<ArrayList<ShotData>>();
		this.currentGame = new ArrayList<ShotData>();
	}
	
	public ShotLocationVisitor(String path, String userName, String password,
								ShotCompiler shotCompiler)
			throws ClassNotFoundException, SQLException 
	{
		this(path, userName, password);
		this.gameShots = shotCompiler.getShots();
	}
	
	public ArrayList<ShotData> getCurrentGame()
	{
		return this.currentGame;
	}
	
	/**
	 * @param shotData the shot data (x,y coordinates) for a single game
	 * @param shot the shot being searched for in the shotData argument
	 * @return true if shot was found in ShotData
	 */
	private boolean FindShot(ArrayList<ShotData> shotData, Shot shot)
	{
		//search the shots of a game (shotData)
		for (ShotData data : shotData)
		{
			if (data.getPlayerName().contains(currentPlayer.GetLastName()) 
					&& ((data.getTimeDouble() - currentPlayTime()) < 1));
				return true;
		}
		return false;
	}
	
	private double currentPlayTime()
	{
		final double quarterLength = 720.0; //length of an NBA quarter in seconds
		final double overtimeLength = 300.0; //length of an NBA overtime in seconds
		
		if (this.overtime)
		{
			return (quarterLength * 4) + ((this.currentPeriod - 1) * overtimeLength)
					+ (overtimeLength - currentPlayTime);
		}
		else
		{
			return ((this.currentPeriod - 1) * quarterLength)
					+ (quarterLength - currentPlayTime);
		}
	}
	
	private void removeGames(Shot shot)
	{
		for(ArrayList<ShotData> sd : gameShots)
		{
			if (!FindShot(sd, shot))
			{
				gameShots.remove(sd);
			}
		}
	}
	
	@Override
	public void visit(Game game) 
	{
		for(Period p : game.getPeriods())
		{
			if (this.currentGame.size() > 0)
			{
				return;
			}
			else
			{
				p.accept(this);
			}
		}
	}

	@Override
	public void visit(InstantReplay instantReplay) 
	{
		
	}

	@Override
	public void visit(Period period) 
	{
		this.currentPeriod = period.getPeriodInt();
		this.overtime = period.isInRegulation();
		for(Play p : period.getPlays())
		{
			if (this.currentGame.size() > 0)
			{
				return;
			}
			else
			{
				p.accept(this);
			}
		}
	}

	@Override
	public void visit(Play play) 
	{
		play.GetTimeStamp().accept(this);
		play.GetPlayAction().accept(this);
	}

	@Override
	public void visit(PlayAction playAction)
	{
		
	}

	@Override
	public void visit(Player player) 
	{
		
	}

	@Override
	public void visit(Possession possession) 
	{
		
	}

	@Override
	public void visit(Team team) 
	{
		
	}

	@Override
	public void visit(TimeStamp timeStamp) 
	{
		
	}

	@Override
	public void visit(Unit unit) 
	{
		
	}

	@Override
	public void visit(Ejection ejection) 
	{
		
	}

	@Override
	public void visit(Foul foul) 
	{
		
	}

	@Override
	public void visit(FreeThrow freeThrow) 
	{
		
	}

	@Override
	public void visit(JumpBall jumpBall) 
	{
		
	}

	@Override
	public void visit(Rebound rebound) 
	{
		
	}

	@Override
	public void visit(Shot shot) 
	{
		if (this.gameShots.size() == 1)
		{
			return;
		}
		else if (this.gameShots.size() > 1)
		{
			removeGames(shot);
			if (this.gameShots.size() == 1)
			{
				currentGame  = gameShots.get(1);
			}
		}
		else
		{
			// TODO eeeeerrroooorr
		}
	}

	@Override
	public void visit(Substitution substitution) 
	{
		
	}

	@Override
	public void visit(Technical technical) 
	{
		
	}

	@Override
	public void visit(Timeout timeout) 
	{
		
	}

	@Override
	public void visit(Turnover turnover) 
	{
		
	}

	@Override
	public void visit(Violation violation) 
	{
		
	}

	@Override
	public void visit(DoublePersonalFoul doublePersonal) 
	{
		
	}

	@Override
	public void visit(DoubleTechnical doubleTech) 
	{
		
	}

	@Override
	public void visit(DelayTechnical DelayTech) 
	{
		
	}

	@Override
	public void visit(TauntingTechnical tauntingTech) 
	{
		
	}

	@Override
	public void visit(USLTechnical uslTech) 
	{
		
	}

	@Override
	public void visit(TeamTurnover teamTurnover) 
	{
		
	}

	@Override
	public void visit(DelayTimeStamp delayTimeStamp) 
	{
		
	}

	@Override
	public void visit(DoubleTimeStamp doubleTimeStamp) 
	{
		
	}

	@Override
	public void visit(DPTimeStamp dpTimeStamp) 
	{
		
	}

	@Override
	public void visit(IRTimeStamp irTimeStamp) 
	{
		
	}

	@Override
	public void visit(JumpTimeStamp jumpTimeStamp) 
	{
		
	}

	@Override
	public void visit(PlayerTimeStamp playerTimeStamp) 
	{
		this.currentPlayTime = playerTimeStamp.GetTimeDouble();
		this.currentPlayer = playerTimeStamp.GetPlayer();
	}

	@Override
	public void visit(TeamTimeStamp teamTimeStamp) 
	{
		
	}

	@Override
	public void visit(TimeoutTimeStamp timeoutTimeStamp) 
	{
		
	}

}
