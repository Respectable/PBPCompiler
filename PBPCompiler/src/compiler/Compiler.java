package compiler;

import java.io.BufferedReader;
import java.io.FileReader;

import java_cup.runtime.Symbol;
import compiler.parser.*;
import compiler.scanner.*;

public class Compiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(String s : args)
		{
			try
			{
				parser p = new parser(new Yylex(new BufferedReader(new FileReader(s))));
				Symbol parse_tree = p.debug_parse();
				System.out.print(parse_tree.toString());
			}
			catch(Exception e)
			{
				
			}
			
		}

	}

}
