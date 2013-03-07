package compiler;

import java.io.BufferedReader;
import java.io.FileReader;
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
				p.parse();
			}
			catch(Exception e)
			{
				
			}
			
		}

	}

}
