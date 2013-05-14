package compiler;

import java.io.BufferedReader;
import java.io.FileReader;

import nba.Game;
import nba.Period;

import java_cup.runtime.Symbol;
import compiler.parser.*;
import compiler.scanner.*;

public class Compiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game game;
		
		for(String s : args)
		{
			try
			{
				parser p = new parser(new Yylex(new BufferedReader(new FileReader(s))));
				Symbol parse_tree = p.parse();
				game = (Game)parse_tree.value;
		        game.setGameAttributes(s);
		        game.CompileGame(s);
			}
			catch(Exception e)
			{
				System.out.println(e.getLocalizedMessage());
			}
			
		}

	}
	

}
