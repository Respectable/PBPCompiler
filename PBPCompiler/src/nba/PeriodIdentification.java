package nba;

public class PeriodIdentification {

	private String periodIdentifier, periodType;
	
	public PeriodIdentification(String periodIdentifier, String periodType)
	{
		this.periodIdentifier = periodIdentifier;
		this.periodType = periodType;
	}
	
	public String GetPeriodNumber()
	{
		return this.periodIdentifier;
	}
	
	public String GetPeriodType()
	{
		return this.periodType;
	}
	
	public int GetPeriodInt()
	{
		return Integer.parseInt(this.periodIdentifier.substring(0, 1));
	}
	
	public boolean PeriodIsRegulation()
	{
		return !periodType.equals("Overtime");
	}
}
