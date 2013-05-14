package nba.playActions.freeThrow;

public class FreeThrowPredicate {
	
	private Boolean made;
	private int currentShot, outOf;
	
	
	public FreeThrowPredicate(Boolean made)
	{
		this.made = made;
		this.currentShot = 1;
		this.outOf = 1;
	}
	
	public FreeThrowPredicate(int currentShot, int outOf, Boolean made)
	{
		this.made = made;
		this.currentShot = currentShot;
		this.outOf = outOf;
	}

	public boolean LastFreeThrow()
	{
		return currentShot == outOf;
	}
	
	public boolean made()
	{
		return made;
	}
	
	public int currentShotNumber()
	{
		return this.currentShot;
	}
	
	public int outOfNumber()
	{
		return this.outOf;
	}
}
