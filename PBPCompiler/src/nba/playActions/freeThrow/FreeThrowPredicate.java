package nba.playActions.freeThrow;

public class FreeThrowPredicate {
	
	private Boolean made;
	private int currentShot, outOf;
	
	
	public FreeThrowPredicate(Boolean made)
	{
		this.made = made;
	}
	
	public FreeThrowPredicate(int currentShot, int outOf, Boolean made)
	{
		this.made = made;
		this.currentShot = currentShot;
		this.outOf = outOf;
	}

}
