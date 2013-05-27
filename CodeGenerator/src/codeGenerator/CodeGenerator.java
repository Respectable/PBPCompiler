package codeGenerator;

import visitors.*;
import nba.Game;

public class CodeGenerator 
{
	private Game game;
	private String path, userName, password, 
					fileName;
	
	public CodeGenerator(String path, String userName, 
			String password, Game game, String fileName)
	{
		this.game = game;
		this.path = path;
		this.userName = userName;
		this.password = password;
		this.fileName = fileName;
	}
	
	public void generateCode()
	{
		GameSetupVisitor gameSetup;
		PossessionVisitor possessionSetup;

		try
		{
			gameSetup = new GameSetupVisitor(path, userName, password, fileName);
			game.accept(gameSetup);
			possessionSetup = new PossessionVisitor(path, userName, password);
			game.accept(possessionSetup);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
