package visitors;

import java.sql.SQLException;

import nba.*;
import nba.playActions.*;
import nba.playActions.foul.DoublePersonalFoul;
import nba.playActions.foul.OffensiveChargeFoulType;
import nba.playActions.foul.OffensiveFoulType;
import nba.playActions.technical.*;
import nba.playActions.turnover.TeamTurnover;
import nba.timeStamps.*;

public class PossessionVisitor extends MySQLVisitor {

	Team homeTeam, awayTeam;
	
	public PossessionVisitor(String path, String userName, String password)
			throws ClassNotFoundException, SQLException 
	{
		super(path, userName, password);
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
	
	@Override
	public void visit(Game game) 
	{
		homeTeam = game.getHomeTeam();
		awayTeam = game.getAwayTeam();
		for (Period p : game.getPeriods())
		{
			p.accept(this);
		}
	}

	@Override
	public void visit(InstantReplay instantReplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Period period) 
	{
		Possession currentPossession = new Possession();
		boolean madeShot = false;
		boolean missedFirstFT = false;
		
		for (Play p : period.getPlays())
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
						period.addPossession(currentPossession);
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
						period.addPossession(currentPossession);
						currentPossession = new Possession();
						madeShot = false;
						missedFirstFT = false;
						currentPossession.AddPlay(p);
						currentPossession = assignTeamRoles(currentPossession, p, homeTeam, awayTeam);
						period.addPossession(currentPossession);
						currentPossession = new Possession();
					}
					else
					{
						period.addPossession(currentPossession);
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

	@Override
	public void visit(Play play) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlayAction playAction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Possession possession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Team team) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
