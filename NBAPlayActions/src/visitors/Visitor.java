package visitors;

import nba.*;
import nba.playActions.*;
import nba.playActions.foul.DoublePersonalFoul;
import nba.playActions.technical.*;
import nba.playActions.turnover.TeamTurnover;
import nba.timeStamps.*;

public interface Visitor {

	public void visit(Game game);
	public void visit(InstantReplay instantReplay);
	public void visit(Period period);
	public void visit(Play play);
	public void visit(PlayAction playAction);
	public void visit(Player player);
	public void visit(Possession possession);
	public void visit(Team team);
	public void visit(TimeStamp timeStamp);
	public void visit(Unit unit);
	public void visit(Ejection ejection);
	public void visit(Foul foul);
	public void visit(FreeThrow freeThrow);
	public void visit(JumpBall jumpBall);
	public void visit(Rebound rebound);
	public void visit(Shot shot);
	public void visit(Substitution substitution);
	public void visit(Technical technical);
	public void visit(Timeout timeout);
	public void visit(Turnover turnover);
	public void visit(Violation violation);
	public void visit(DoublePersonalFoul doublePersonal);
	public void visit(DoubleTechnical doubleTech);
	public void visit(DelayTechnical DelayTech);
	public void visit(TauntingTechnical tauntingTech);
	public void visit(USLTechnical uslTech);
	public void visit(TeamTurnover teamTurnover);
	public void visit(DelayTimeStamp delayTimeStamp);
	public void visit(DoubleTimeStamp doubleTimeStamp);
	public void visit(DPTimeStamp dpTimeStamp);
	public void visit(IRTimeStamp irTimeStamp);
	public void visit(JumpTimeStamp jumpTimeStamp);
	public void visit(PlayerTimeStamp playerTimeStamp);
	public void visit(TeamTimeStamp teamTimeStamp);
	public void visit(TimeoutTimeStamp timeoutTimeStamp);
	
}
