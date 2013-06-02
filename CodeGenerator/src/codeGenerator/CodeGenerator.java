package codeGenerator;

import shotCompiler.ShotCompiler;
import visitors.*;
import nba.Game;

public class CodeGenerator 
{
	private Game game;
	private String path, userName, password, 
					pbpFileName;
	private ShotCompiler shotCompiler;
	
	public CodeGenerator(String path, String userName, 
			String password, Game game, String pbpFileName,
			ShotCompiler shotCompiler)
	{
		this.game = game;
		this.path = path;
		this.userName = userName;
		this.password = password;
		this.pbpFileName = pbpFileName;
		this.shotCompiler = shotCompiler;
	}
	
	public void generateCode()
	{
		GameSetupVisitor gameSetup;
		PossessionVisitor possessionSetup;
		ShotLocationVisitor shotLocationSetup;

		try
		{
			gameSetup = new GameSetupVisitor(path, userName, password, pbpFileName);
			game.accept(gameSetup);
			possessionSetup = new PossessionVisitor(path, userName, password);
			game.accept(possessionSetup);
			shotLocationSetup = new ShotLocationVisitor(path, userName, password, shotCompiler);
			game.accept(shotLocationSetup);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
