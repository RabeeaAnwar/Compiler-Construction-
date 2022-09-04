package test;

import java.io.Console;


public class Compiler {

	public static void main(String[] args) {
		Edge lex=new Edge();
		Syntaxtification syn = new Syntaxtification();
		Console con= System.console();
		
		lex.LexicalsMain();
		for(int i=0;i<5;i++)
			System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------LEXICAL---------------------------------------------------");
		
		for(int i=0;i<5;i++)
			System.out.println("------------------------------------------------------------------------------------------------------------");
		if(Edge.AllRight)
		syn.SyntaxMain();
		else
			System.out.println("Syntax can't process"+Edge.AllRight);
	}

}
