package visitors;

import nba.Game;
import nba.InstantReplay;
import nba.Period;
import nba.Play;
import nba.PlayAction;
import nba.Player;
import nba.Possession;
import nba.Team;
import nba.TimeStamp;
import nba.Unit;
import nba.playActions.Ejection;
import nba.playActions.Foul;
import nba.playActions.FreeThrow;
import nba.playActions.JumpBall;
import nba.playActions.Rebound;
import nba.playActions.Shot;
import nba.playActions.Substitution;
import nba.playActions.Technical;
import nba.playActions.Timeout;
import nba.playActions.Turnover;
import nba.playActions.Violation;
import nba.playActions.foul.DoublePersonalFoul;
import nba.playActions.technical.DelayTechnical;
import nba.playActions.technical.DoubleTechnical;
import nba.playActions.technical.TauntingTechnical;
import nba.playActions.technical.USLTechnical;
import nba.playActions.turnover.TeamTurnover;
import nba.timeStamps.DPTimeStamp;
import nba.timeStamps.DelayTimeStamp;
import nba.timeStamps.DoubleTimeStamp;
import nba.timeStamps.IRTimeStamp;
import nba.timeStamps.JumpTimeStamp;
import nba.timeStamps.PlayerTimeStamp;
import nba.timeStamps.TeamTimeStamp;
import nba.timeStamps.TimeoutTimeStamp;

public class ShotAssignmentVisitor extends MySQLVisitor {

	@Override
	public void visit(Game game) {
		// TODO Auto-generated method stub
		
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
