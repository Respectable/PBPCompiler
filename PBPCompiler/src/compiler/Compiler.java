package compiler;

import java.io.BufferedReader;
import java.io.FileReader;

import codeGenerator.CodeGenerator;

import nba.Game;

import java_cup.runtime.Symbol;
import parser.*;
import scanner.*;

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
				CodeGenerator generator = new CodeGenerator("jdbc:mysql://localhost/nba", "root",
																"ao4132", game, s);
				generator.generateCode();
		        //game.CompileGame(s);
			}
			catch(Exception e)
			{
				System.out.println(e.getLocalizedMessage());
			}
			
		}

	}
	

}
