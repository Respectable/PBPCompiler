package nba;

import java.util.ArrayList;

public class Period {

	private PeriodIdentification periodIdentification;
	private ArrayList<Play> plays;
	
	public Period()
	{
		
	}
	
	public Period(PeriodIdentification periodIdentification, ArrayList<Play> plays)
	{
		this.periodIdentification = periodIdentification;
		this.plays = plays;
	}
}
