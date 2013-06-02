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
	private ArrayList<Shot> shotsWithoutCoord;
	private Double currentPlayTime;
	private Player currentPlayer;
	
	public ShotLocationVisitor(String path, String userName, String password)
			throws ClassNotFoundException, SQLException 
	{
		super(path, userName, password);
	}
	
	public ShotLocationVisitor(String path, String userName, String password,
								ShotCompiler shotCompiler)
			throws ClassNotFoundException, SQLException 
	{
		this(path, userName, password);
		this.gameShots = shotCompiler.getShots();
		this.currentGame = new ArrayList<ShotData>();
		this.shotsWithoutCoord = new ArrayList<Shot>();
	}
	
	private Shot FindShot(ArrayList<ShotData> shotData, Shot shot)
	{
		return null;
	}
	
	private void removeGames(Shot shot)
	{
		for(ArrayList<ShotData> sd : gameShots)
		{
			Shot foundShot = FindShot(sd, shot);
			if (foundShot == null)
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
			p.accept(this);
		}
	}

	@Override
	public void visit(InstantReplay instantReplay) 
	{
		
	}

	@Override
	public void visit(Period period) 
	{
		for(Play p : period.getPlays())
		{
			p.accept(this);
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
