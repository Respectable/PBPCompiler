package compiler;

import java.io.BufferedReader;
import java.io.FileReader;

import codeGenerator.CodeGenerator;

import nba.Game;

import java_cup.runtime.Symbol;
import parser.*;
import scanner.*;
import shotCompiler.ShotCompiler;

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
				ShotCompiler shotCompiler = new ShotCompiler();
				shotCompiler.main("/home/anthony/Desktop/Data/2010-2011/Shots");
				CodeGenerator generator = new CodeGenerator("jdbc:mysql://localhost/nba", "root",
																"ao4132", game, s, shotCompiler);
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
