
public class Teams {

	private String awayTeam;
	private String homeTeam;
	private String awayTeamThreeLetter;
	private String homeTeamThreeLetter;
	
	public Teams()
	{
		awayTeam = "";
		homeTeam = "";
		awayTeamThreeLetter = "";
		homeTeamThreeLetter = "";
	}
	
	public Teams(String away, String home)
	{
		awayTeam = away;
		homeTeam = home;
	}
	
	public void SetHomeTeam(String homeTeam)
	{
		this.homeTeam = homeTeam;
	}
	
	public void SetAwayTeam(String awayTeam)
	{
		this.awayTeam = awayTeam;
	}
	
	public void SetAwayThreeLetter(String awayTeam)
	{
		this.awayTeamThreeLetter = awayTeam;
	}
	
	public void SetHomeThreeLetter(String homeTeam)
	{
		this.homeTeamThreeLetter = homeTeam;
	}
	
	public String HomeTeam()
	{
		return homeTeam;
	}
	
	public String AwayTeam()
	{
		return awayTeam;
	}
	
	public String HomeThreeLetter()
	{
		return homeTeamThreeLetter;
	}
	
	public String AwayThreeLetter()
	{
		return awayTeamThreeLetter;
	}
}
