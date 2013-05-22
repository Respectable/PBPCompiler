package nba;

import visitors.Visitable;
import visitors.Visitor;

public class InstantReplay implements Visitable {

	@Override
	public void accept(Visitor visitor) 
	{
		visitor.visit(this);
	}

}
