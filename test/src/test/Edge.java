package test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Edge {
	public static boolean AllRight=true;
	public  void LexicalsMain() {
		 try {
			Working lexical =new Working();
			
		try{
			lexical.ReadFile();
			lexical.WordFinder();
			lexical.Validate_Word();
			lexical.PrintDetails_Word_Gathered();
			lexical.makeToken();
			lexical.writeToken();
			if(lexical.AllRight)
			{
				lexical.writeCP();
				lexical.writeVP();
				lexical.writeLineNumber();
			}
			AllRight=lexical.AllRight;
		}
		catch(Exception e)
		{
		
			
			e.printStackTrace();
		}
			lexical.PrintDetails_Word_Gathered();
			
	      } catch (Exception e) {
	      }

	}
	

}
class Working
{
	static int counter=0; //Total Number of lines within a code.
	static ArrayList<String> Lineword = new ArrayList<>(); //Each line of code
	static ArrayList<Integer> Linenum = new ArrayList<>(); //LineNumber of each line
	static ArrayList<String> Words = new ArrayList<>(); //Each and every word
	static ArrayList<Integer> linen = new ArrayList<>(); //Linenumber of each Word
	static ArrayList<Boolean> Valid_word_info=new ArrayList<>(); //Contains information about each word is a lexeme or not;
	static ArrayList<String> Class=new ArrayList<>(); //Contains information about the class of the word;
	static int index_WordContainer=0;
	static boolean SL_Comment=false,ML_Comment=false,CheckNextChar=false,SQ=false,DQ=false,BL=false;
	static int upperBond=0,lowerBond=0,charCount=0,sqchar_counter=0;
	static char prevChar=0;
	static BufferedWriter out;
	static int state=0;
	static int[][] Int_State = {{2,3,4},{2,4,4},{2,4,4},{4,4,4}};
	static int[][] Float_State = {{4,2,3,6},{4,6,3,4},{5,6,3,4},{6,6,5,4},{6,6,5,6},{6,6,6,6}};
	static ArrayList<String> Tokens=new ArrayList<>(); //Contains Tokens;
	static ArrayList<Character> WordInformation = new ArrayList<>(); //WordInformation
	static boolean AllRight=true;
	static boolean isNumber(char ch)
	{
		for(int i=48;i<=57;i++)
			if(ch==i) return true;
		return false;
	}
	static boolean onlyNumber(String ch)
	{
		boolean result=true;
		for(int i=0;i<ch.length();i++)
		{
			if(ch.charAt(i)<=57 && ch.charAt(i)>=48)
				result=true;
			else
				return false;
		}
		return false;
	}
	public static void WordFinder()
	{
		int count=0;
		for(int i=0;i<Lineword.size();i++)
		{
			
			
			WordChecker(Lineword.get(i),i);
			lowerBond=0;
			upperBond=0;
			SQ=false;
			DQ=false;
			charCount=0;
			BL=false;
			SL_Comment=false;
			sqchar_counter=0;
		}

	}
	static void WordChecker(String line,int lineNumber)
	{
		
		char ch=0;	charCount=0;
		for(int i=0;i<line.length();i++)
		{
		
			
			if(SL_Comment)
			{
				//System.out.println(ch);
				SL_Comment=false;
				
				return;
				
			}
			ch=line.charAt(i);
		
			charCount++;
		boolean v=WordAlarm(ch,i,lineNumber,line);
	
		
			if(v==false)
			{
				
				continue;
			}
			else 
			{
				
				WordBreakInitialization(i,lineNumber,line,charCount);
			}
		
		
			
		}
	
	}
	public static void Validate_Word()
	{
		for(int i=0;i<Words.size();i++)
		{
			if(!Words.get(i).trim().contentEquals(" ") )
			Valid_word_info.add(IsValid(Words.get(i),i));
		}
	}
		static char char_String_differentiator='L'; //Agar c hai to char nhi hai and agar s hai toh string nhi hai.
	public static boolean IsValid(String word,int index)
	{
		boolean valid=false;
		if(intCheck(word))
		{
			valid=true;
			Class.add("int");
		}
		else if(FloatCheck(word))
		{
			valid=true;
			Class.add("Float");
		}
		else if(CharCheck(word.trim()))
		{
			valid=true;
			Class.add("Char");
			char_String_differentiator='C';
		}
		else if(StringCheck(word.trim()))
		{
			valid=true;
			Class.add("String");
			char_String_differentiator='S';
		}
		else if(isPunctuation(word.trim()) && !word.equals("~"))
		{
			valid=true;
			Class.add(classPunctuation(word));
		
		}
		else if(isKeyWord(word.trim()))
		{
			valid=true;
			Class.add(ClassKeyWord(word));
		
		}
		else if(isOperators(word.trim())) //
		{
			valid=true;
			Class.add(classOperator(word));
		
		}
		else if(IdenCheck(word.trim()) && word.contains("\'")!=true)
		{
			valid=true;
			Class.add("ID");
		
		}
		else if(word.trim().contentEquals("."))
		{
			valid=true;
			Class.add("Dot operator");
		}
		
		
		if(!valid)
		{
			try{
				if(word.contains(".") &&  word.contains("\'")!=true &&  word.contains("\"")!=true)
				{
					WordInformation.add('.');
					Class.add("Float Constant");
				}
				else if(word.contains("\'")==true && word.charAt(0)=='\'')
				{
					WordInformation.add('C');
					Class.add("Char Constant");
				}
				else if(onlyNumber(word.trim()))//(word.contains(".")!=true  &&  word.contains("\"")!=true)
				{
					WordInformation.add('I');
					Class.add("Int Constant");
				}
				else if( word.contains("\"")==true)
				{
					WordInformation.add('S');
					Class.add("String Constant");
				}
				else if(getPunc(word)==true )
				{
					WordInformation.add('u');
					Class.add("Identifier");
				}
				else
				{
					WordInformation.add('q');
					Class.add("Unknown");
				}
			
				}
			catch(Exception e){e.printStackTrace();}
		}

		return valid;
	}
	
	

	
	static String KeyWords[]={ 
			"main","void","si","sino","dig","ddig","si-no-si","frac","dfrac","binary","stable","vacant","fixed","local","hide","until","to"/*,"suma","ken"*/,"check","test","attempt","achieve",
			"and","or","back","backup","group","inherits","interned","break","continue","bitAnd","bitOr","new","alpha","beta","empty","sculpture"
			
	};
	
	static boolean getPunc(String word)
	{
		for (int i = 0; i < punct.length; i++) {
			if(word.contains(punct[i])) 
			{
				
				return true;
			}
		}
		return false;
	}

	static String ClassKeyWord(String word)
	{
		String Class=word;
		switch(Class)
		{
		case "main": Class="main"; break;
		case "sculpture": Class="sculpture"; break;
		case "bitAnd":case "bitOr": case "bitNot":  Class="BWOP"; break;
		/*case "void": Class="void"; break;*/
		case "si": Class="si"; break;
		case "check": Class="check"; break;
		case "sino": Class="sino"; break;
		case "si-no-si": Class="si-no-si"; break;
		case "break": Class="break"; break;
		case "continue": Class="continue"; break;
		case "test": Class="test"; break;
		case "empty": Class="void"; break;
		case "dig": Class="datatype"; break;
		case "ddig": Class="datatype"; break;
		case "frac": Class="datatype"; break;
		case "dfrac": Class="datatype"; break;
		case "alpha": Class="datatype"; break;
		case "beta": Class="datatype"; break;
		case "binary": Class="datatype"; break;
		case "stable": Class="type modifier"; break;
		case "fixed": Class="type modifier"; break;
		case "suma": Class="arithmetic operator"; break;
		case "ken": Class="arithmetic operator"; break;
		/*case "mux": Class="arithmetic operator"; break;
		case "demux": Class="arithmetic operator"; break;*/
		case "group": Class="class"; break;
		case "inherits": Class="inheritance"; break;
		case "interned": Class="interface"; break;
		case "attempt": Class="try"; break;
		case "achieve": Class="catch"; break;
		case "back": Class="return"; break;
		case "backup": Class="default"; break;
		case "until": Class="while loop"; break;
		case "to": Class="for loop"; break;
		case "and": Class="LO"; break;
		case "or": Class="LO"; break;
		case "local": Class="acces modifier"; break;
		case "hide": Class="acces modifier"; break;
		case "vacant": Class="abstract class"; break;
			
			
							
			
		}	
		return Class;
		
	}		
	static boolean isKeyWord(String word)
	{
		boolean haha=false;
		for (int i = 0; i < KeyWords.length; i++) {
			if(word.contentEquals(KeyWords[i]))
			{
				haha=true;
				break;
			}
		}
		return haha;
	}
	static String classPunctuation(String cha)
	{
		String Class="";
		char ch=' ';
		if(cha.length()<1) return Class;
	
		ch=cha.charAt(0);
		switch(ch)
		{	
		 case '(': 
			 Class="(";
		        break;
		 case ')': 
			 Class=")";
		        break;
		 case '{': 
			 Class="{";
		        break;
		 case '}': 
			 Class="}";
		        break;
		 case '[': 
			 Class="[";
		        break;
		 case ']': 
			 Class="]";
		        break;
		 case '$': 
		 		Class="Terminator";
		 		break;
		 case ',':
		  	    Class=",";
		  	    break;
		}
		return Class;
	}
	static boolean isPunctuation(String cha)
	{
		char ch;
	if(cha.length()<1) return false;
		ch=cha.charAt(0);
		boolean sach_hai_ya_nhi=false;
		switch(ch)
		{	
		 case '(': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case ')': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case '{': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case '}': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case '[': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case ']': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case '$': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case ',': 
		 		sach_hai_ya_nhi=true;
		        break;
		 case '~': 
		 		sach_hai_ya_nhi=true;
		        break;
		}
		return sach_hai_ya_nhi;
		
	}
	
	static int[][] iden_State={{3,2,2,3},{2,2,3,2},{3,3,3,3}};
	public static boolean IdenCheck(String word)
	{
		boolean check=false;
		int state=1, FS = 2,Ds=3;
		for(int i=0;i<word.length();i++)
		{
			state=trans_Iden(state,word.charAt(i));
			if(Ds==state)
		    {
				ErrorBuffer='u'; //u for identifiers
				break;
			}
		}
		if(FS==state)
		{
			ErrorBuffer='R';
			check=true;
		}
		else if(Ds!=state)
			ErrorBuffer='W';
		return check;
	}
	public static int trans_Iden(int state,char ch)
	{
		state--;
	    if((ch>='a' && ch<='z')|| (ch>='A' && ch<='Z'))
	    {
	    	
	    	return iden_State[state][1];
	    }
	    else if((ch>='0' && ch<='9')) 
	    {
	    	
	    	return iden_State[state][0];
	    }	
		else if((ch=='_'))
		 {
			
			return iden_State[state][2];
		 }
		else 
		 {
			
			return iden_State[state][3];
		 }
		
	}
	static String[] Operators={"+",":(",":)","->","===","=","=<","=>","+=","-=","*=","/=","demux","mux","suma","ken","<",">","%=","%",":","~"};
	static boolean isOperators(String cha)
	{
		if(cha.length()<1) return false;
		for(int i=0;i<Operators.length;i++)
		{
			if(Operators[i].contentEquals(cha))
			{
				return true;
			}
		}
		return false;
	}
	static String classOperator(String cha)
	{
		String Class="";
		if(cha.length()<1) return Class;
		switch(cha)
		{	
		 case "+": 
			 Class="Concantenation";
		        break;
		 case ".": 
			 Class="Dot operator";
		        break;
		 case "~": 
			 Class="Not operator";
		        break;
		 case ":(": 
			 Class="Incdec";
		        break;
		 case ":)": 
			 Class="Incdec";
		        break;
		 case "->": 
			 Class="Assign Op";
		        break;
		 case "===": 
			 Class="RO";
		        break;
		 case "=": 
			 Class="Assignment";
		        break;
		 case "=>": 
			 Class="RO";
		        break;
		 case "=<": 
			 Class="RO";
		        break;
		 case ">": 
			 Class="RO";
		        break;
		 case "<": 
			 Class="RO";
		        break;
		 case "+=": 
			 Class="RO";
		        break;
		 case "-=": 
			 Class="RO";
		        break;
		 case "*=": 
			 Class="RO";
		        break;
		 case "/=": 
			 Class="RO";
		        break;
		 case "%=": 
			 Class="RO";
		        break;
		 case "%": 
			 Class="DIVMOD";
		        break;
		 case "mux": 
			 Class="Mux";
		        break;
		 case "demux": 
			 Class="DIVMOD";
		        break;
		 case "suma": 
			 Class="AddSum";
		        break;
		 case "ken": 
			 Class="AddSum";
		        break;
		 case ":": 
			 Class=":";
		        break;
		}
		return Class;
	}
	static int[][] Char_State = {{2,6,6,6,6,6,6,6},{4,2,2,2,2,3,2,6},{2,6,6,2,2,2,6,6},{6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6}};
	static char escape[]={'n','r','0','1','2','3','4','5','6','7','8','9','\\','\'','\"'};
	static boolean isEscape(char ch)
	{
		for(int i=0;i<escape.length;i++)
		{
			if(ch==escape[i]) return true;
		}
		return false;
	}	
	public static int trans_Char(int state,char ch)
	{
		state--;																										// Space till /				:   till @
	    if((ch>='0' && ch<='9'))
	    {
	    	
	    	return Char_State[state][1];
	    }	
	    else if((ch>='a' && ch<'z' && ch!='n' && ch!='r' ))// || (ch>='a' && ch<='z')|| (ch>='A' && ch<='Z') || (isPunctuation(String.valueOf(ch))) ||  (ch>=32 && ch <=47) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95)  ||  (ch>=123 && ch<=126))
	    {
	    	
	    	return Char_State[state][2];
	    }
	    else if((ch>='A' && ch<='Z'))
	    {
	    	
	    	return Char_State[state][2];
	    }
	    else if((ch>='n'))
	    {
	    	
	    	return Char_State[state][3];
	    }
	    else if((ch>='r'))
	    {
	    	
	    	return Char_State[state][4];
	    }
		else if( (ch>=' ' && ch <='/' && ch!=39) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95 && ch!=92)  ||  (ch>=123 && ch<=126) )
		 {
			
			return Char_State[state][6];
		 }
		else if((ch=='\\'))
		 {
			
			return Char_State[state][5];
		 }
		else if((ch=='\''))
		 {
			
			return Char_State[state][0];
		 }
		else 
		 {
		
			return Char_State[state][7];
		 }
		
	}
	
	
	
	
	
	
	public static boolean FloatCheck(String word)
	{
		boolean check=false;
		int state=1, FS = 5,Ds=6;
		for(int i=0;i<word.length();i++)
		{
			state=trans_Float(state,word.charAt(i));
			if(Ds==state)
		    {
				ErrorBuffer='.';
				break;
			}
		}
		if(FS==state)
		{
			ErrorBuffer='R';
			check=true;
		}
		else if(Ds!=state)
			ErrorBuffer='W';
		return check;
	}
	public static int trans_Float(int state,char ch)
	{
		state--;
	    if((ch>='0' && ch<='9') )
	    {
	    
	    	return Float_State[state][2];
	    }	
		else if((ch=='+') || (ch=='-') )
		 {
	    
			return Float_State[state][1];
		 }
		else if((ch=='.'))
		 {
	    
			return Float_State[state][0];
		 }
		else 
		 {
	    
			return Float_State[state][3];
		 }
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static char ErrorBuffer='k';
	
	
	public static boolean intCheck(String word)
	{
		boolean check=false;
		int state=0, FS = 2,Ds=4;
		for(int i=0;i<word.length();i++)
		{
			state=trans_int(state,word.charAt(i));
			if(Ds==state)
		    {
				ErrorBuffer='I';
				break;
			}
		}
		if(FS==state)
		{
			ErrorBuffer='R';
			check=true;
		}
		else if(Ds!=state)
			ErrorBuffer='W';
		return check;
	}
	public static boolean CharCheck(String word)
	{
		boolean check=false;
		int state=1, FS = 4,Ds=6;
		for(int i=0;i<word.length();i++)
		{
			state=trans_Char(state,word.charAt(i));
			if(Ds==state)
		    {
				ErrorBuffer='C';
				break;
			}
			
		}
	
		if(FS==state)
		{
			ErrorBuffer='R';
			check=true;
		}
		else if(Ds!=state)
			ErrorBuffer='W';
		return check;
	}
	public static boolean StringCheck(String word)
	{
		boolean check=false;
		int state=1, FS = 4,Ds=6;
		for(int i=0;i<word.length();i++)
		{
			state=trans_String(state,word.charAt(i));
		
			if(Ds==state)
		    {
				ErrorBuffer='S';
				break;
			}
			
		}
	
		if(FS==state)
		{
			ErrorBuffer='R';
			check=true;
		}
		else if(Ds!=state)
			ErrorBuffer='W';
		return check;
	}							 // " C \ + A
	/*static int[][] String_State = {{2,6,6,6},{4,2,3,6},{2,2,2,6},{6,6,6,6},{2,6,6,6},{6,6,6,6}};
	public static int trans_String(int state,char ch)
	{
		state--;																										// Space till /				:   till @
	    if((ch>='0' && ch<='9') || (ch>='a' && ch<='z')|| (ch>='A' && ch<='Z') || (isPunctuation(String.valueOf(ch))) ||  (ch>=32 && ch <=47) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95)  ||  (ch>=123 && ch<=126))
	    {
	    	
	    	return String_State[state][1];
	    }	
		else if((ch=='\\'))
		 {
			
			return String_State[state][2];
		 }
		else if((ch=='\"'))
		 {
		
			return String_State[state][0];
		 }
		else 
		 {
			
			return String_State[state][3];
		 }
		
	}*/
	static int[][] String_State = {{2,6,6,6,6,6,6,6,6},{4,2,2,2,2,3,2,6,6},{2,6,6,2,2,2,6,6,2},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6}};
	public static int trans_String(int state,char ch)
	{
		state--;																										// Space till /				:   till @
	    if((ch>='0' && ch<='9'))// || (ch>='a' && ch<='z')|| (ch>='A' && ch<='Z') || (isPunctuation(String.valueOf(ch))) ||  (ch>=32 && ch <=47) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95)  ||  (ch>=123 && ch<=126))
	    {
	    	
	    	return String_State[state][1];
	    }	
	    else if((ch>='a' && ch<'z' && ch!='n' && ch!='r' ))// || (ch>='a' && ch<='z')|| (ch>='A' && ch<='Z') || (isPunctuation(String.valueOf(ch))) ||  (ch>=32 && ch <=47) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95)  ||  (ch>=123 && ch<=126))
	    {
	    	
	    	return String_State[state][2];
	    }
	    else if((ch>='A' && ch<='Z'))
	    {
	    	
	    	return String_State[state][2];
	    }
	    else if((ch>='n'))
	    {
	    	
	    	return String_State[state][3];
	    }
	    else if((ch>='r'))
	    {
	    	
	    	return String_State[state][4];
	    }
		else if( (ch>=' ' && ch <='/' && ch!=34 && ch!=39) ||  (ch>=58 && ch<=64) ||  (ch>=91 && ch<=95 && ch!=92)  ||  (ch>=123 && ch<=126) )
		 {
			
			return String_State[state][6];
		 }
		else if((ch=='\\'))
		 {
			
			return String_State[state][5];
		 }
		else if((ch=='\"'))
		 {
			
			return String_State[state][0];
		 }
		else if((ch=='\''))
		 {
			
			return String_State[state][8];
		 }
		else 
		 {
		
			return String_State[state][7];
		 }
		
	}
			
	public static int trans_int(int state,char ch)
	{
		//Float_State
	    if((ch>='0' && ch<='9') )
	    {
	    
	    	return Int_State[state][0];
	    }
			
		else if((ch=='+') || (ch=='-') )
		 {
	    
			return Int_State[state][1];
		 }
		else 
		 {
	    	
			return Int_State[state][2];
		 }
		
	}
	public static void makeToken()
	{
		
		for(int i=0;i<Words.size();i++)
		{
			if(Valid_word_info.get(i)==true)
			{
				Tokens.add("("+Class.get(i)+","+Words.get(i)+","+linen.get(i)+")");
			}
			else
			{
				AllRight=false;
				Tokens.add("Invalid "+Class.get(i)+" at line number: "+linen.get(i));
			}
		}
	}
	public static void writeCP()
	{
		try {
			out = new BufferedWriter(new FileWriter("cp.txt"));
	         for(int i=0;i<Class.size();i++)
	         {
	        	 out.write(Class.get(i));
	        	 out.newLine();
	        	 
	         }
	        out.close();  
	    
	        
		}
	     
	      catch (IOException e) {
	         System.out.println("exception occoured"+ e);
	      }
	}
	public static void writeVP()
	{
		try {
			out = new BufferedWriter(new FileWriter("vp.txt"));
	         for(int i=0;i<Words.size();i++)
	         {
	        	 out.write(Words.get(i));
	        	 out.newLine();
	        	 
	         }
	        out.close();  
	    
	        
		}
	     
	      catch (IOException e) {
	         System.out.println("exception occoured"+ e);
	      }
	}
	public static void writeLineNumber()
	{
		try {
			out = new BufferedWriter(new FileWriter("linen.txt"));
	         for(int i=0;i<linen.size();i++)
	         {
	        	 out.write(String.valueOf(linen.get(i)));
	        	 out.newLine();
	        	 
	         }
	        out.close();  
	    
	        
		}
	     
	      catch (IOException e) {
	         System.out.println("exception occoured"+ e);
	      }
	}
	public static void writeToken()
	{
		try {
			out = new BufferedWriter(new FileWriter("Token.txt"));
	         for(int i=0;i<Tokens.size();i++)
	         {
	        	 out.write(Tokens.get(i));
	        	 out.newLine();
	        	 
	         }
	        out.close();  
	    
	        
		}
	     
	      catch (IOException e) {
	         System.out.println("exception occoured"+ e);
	      }
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	

	
	
	static boolean isPunc(String word)
	{
		for (int i = 0; i < punct.length; i++) {
			if(word.contains(punct[i])) return true;
		}
		return false;
	}
	static String punct[]={"(",")","[","]","{","}","$",",","~"};
	static String[] Operator={"+",":","=","-","*","/","<",">","%"};
	static boolean AssignmentOccurred=false;
	static boolean isOperator(String cha)
	{
		if(cha.length()<1) return false;
		for(int i=0;i<Operator.length;i++)
		{
			if(Operator[i].contentEquals(cha))
			{
				return true;
			}
		}
		return false;
	}
	static boolean dotCount=false;
	static boolean WordAlarm(char ch,int index,int linenumber,String line)
	{
		
		
		if(ch!='/' && ch!='#' && ch!='\"' && ch!='\'' && !BL )
		{
			if(ML_Comment) return false;
			else if((SL_Comment || ML_Comment || DQ) && ch!='\\')
			{
				if(index==line.length()-1)
					return true;
				
				return false;
			}
			else if(CheckNextChar && dotCount)
			{
				if(prevChar=='.')
				{
					if(ch!='0'  && ch!='1' && ch!='2' && ch!='3' && ch!='4' && ch!='5' && ch!='6' && ch!='7' && ch!='8' && ch!='9'  && ch!='.' )
					{
						dotCount=false;
						CheckNextChar=false;
						prevChar=0;
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						if(index==line.length()-1)
						{
							WordBreakInitialization(index, linenumber,line,charCount);
							return false;
						}
						charCount=1;
						
						return false;
					}
					else if((ch=='0'  || ch=='1' || ch=='2' || ch=='3' || ch=='4' || ch=='5' || ch=='6' || ch=='7' || ch=='8' || ch=='9' ) && ch!='.')
					{
						if(index<=line.length()-2)
						{
						
							return false;
						}
						else
						{
							dotCount=false;
							CheckNextChar=false;
							prevChar=0;
							
							return true;
						}
					}
				}
			}
			else if(!SL_Comment && !ML_Comment)
			{
				
				if(DQ)
				{
					if(ch=='\\' && !BL)
					{
						BL=true;
						prevChar='\\';
						CheckNextChar=true;
						
						return false;
					}
					else if(ch=='\\' && BL)
					{
						BL=false;
						prevChar=0;
						CheckNextChar=false;
					
						return false;
					}
				}
				else if(SQ)
				{
					if(ch=='\\' && !BL)
					{
						BL=true;
						prevChar='\\';
						sqchar_counter++;
				
					
						CheckNextChar=true;
				
						return false;
					}
					else if(ch=='\\' && BL)
					{
						BL=false;
						prevChar=0;
						sqchar_counter++;
						CheckNextChar=false;
						if(sqchar_counter>=3)
						{
							sqchar_counter=0;
							BL=false;
							SQ=false;
							return true;
						}
						return false;
					}
				}
			}
		}
	
		else if(ch=='/')
		{
			
			if(!ML_Comment && ! SL_Comment && !SQ && !DQ)
			{
				prevChar=ch;
				CheckNextChar=true;
				return false;
			}
			else if(ML_Comment && ! SL_Comment && !SQ && !DQ)
			{
				if(prevChar=='#')
				{
					ML_Comment=false;
					CheckNextChar=false;
					prevChar=0;
					
					return false;
				}
			}
		}
		else if(ch=='#')
		{
			
			if(CheckNextChar)
			{
				
				if(!ML_Comment && ! SL_Comment && !SQ && !DQ)
				{
				
					if(prevChar=='/')
					{
						ML_Comment=true;
						prevChar=0;
						if(charCount>2)
						{
							
							WordBreakInitialization(index, linenumber,line,charCount-2);
							charCount=0;
						}
						System.out.println(ch);
						//charCount=0;
						CheckNextChar=false;
						return false;	
					}
					else if(prevChar=='#')
					{
						SL_Comment=true;
						prevChar=0;
						CheckNextChar=false;
						
						if(charCount>2)
						{
														
						WordBreakInitialization(index, linenumber,line,charCount-3);	
						}
						
						
						charCount=0;
						return false;	
					}
					else if(prevChar==0)
					{
						prevChar=ch;
						CheckNextChar=true;
						
						return false;	
					}
				}
				else if(ML_Comment && ! SL_Comment && !SQ && !DQ)
				{
					prevChar=ch;
					CheckNextChar=true;
					return false;	
				}
				
			}
			else if(!CheckNextChar)
			{
				prevChar=ch;
				CheckNextChar=true;
				return false;	
			}
			
		}
		else if(ch=='\"')
		{
			
			if(!ML_Comment && ! SL_Comment && !SQ && !DQ && !BL)
			{ 
				
				if(charCount>1)
				{
					if(index==line.length()-1)
					{
						return true;
					}
					else
					{
						WordBreakInitialization(index-1, linenumber,line,charCount-1);
						charCount=1;
					}
					
				}
				
				DQ=true;
		
			if(index==line.length()-1 && !ML_Comment)
			{
				WordBreakInitialization(index-1, linenumber,line,charCount);
			}
			
				return false;	
			}
			else if(!ML_Comment && ! SL_Comment && !SQ && DQ && !BL)
			{
				
				DQ=false;
			
				
				return true;	
			}
			else if(!ML_Comment && ! SL_Comment && !SQ && DQ && BL)
			{
				BL=false;
			
				return false;	
			}
			
			
		}
		else if(ch=='\'')
		{
			
			if(!DQ && !SL_Comment && !ML_Comment && !SQ && !BL)
			{
				if(charCount>1){WordBreakInitialization(index-1, linenumber,line,charCount-1); }
				SQ=true;
				sqchar_counter++;
				charCount=1;
				if(index==line.length()-1) return true;
				return false;
			}
			else if(!DQ && !SL_Comment && !ML_Comment && SQ && !BL)
			{
				
				SQ=false;
				sqchar_counter=0;
				return true;
			
			}
			else if(!DQ && !SL_Comment && !ML_Comment && SQ && BL)
			{
				
				BL=false;
				sqchar_counter++;
			
				if(sqchar_counter>=3)
				{
					sqchar_counter=0;
					BL=false;
					SQ=false;
					return true;
				}
				return false;
			}
			else if(!DQ && !SL_Comment && !ML_Comment && !SQ && BL)
			{
				
				BL=false;
			
				return false;
			
			}
		}
		  if(ch==' ' && !DQ && !SL_Comment && !ML_Comment && !SQ && !BL)
		  {	
			if(index<=line.length()-1) 
			{
				prevChar=0;
				CheckNextChar=false;
				return true;
			}
			
		  }
		  else if(isPunc(String.valueOf(ch)) && !DQ && !SL_Comment && !ML_Comment && !SQ && !BL)
		  {
				if(index<=line.length()-2)
				{
					
					if(prevChar==':')
					{
						if(ch=='(' || ch==')')
						{
							if(index<line.length()-2)
							{
								if(charCount>2)
								{ 
									
									WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2;
									WordBreakInitialization(index, linenumber,line,charCount); 
									prevChar=0;
									CheckNextChar=false;
									
									return false;
								}
								else if(charCount>1)
								{
									 
									WordBreakInitialization(index, linenumber,line,charCount);
									prevChar=0;
								   CheckNextChar=false;
								  }
								else 
									System.out.println(charCount+","+ch);
								
										prevChar=0;
										CheckNextChar=false;
										 
										return false;
							}
							else if(index==line.length()-2)
							{
							//1272	//System.out.println(String.copyValueOf(line.toCharArray(), index-1, charCount)+","+ch);
								
								WordBreakInitialization(index, linenumber,line,charCount);
								
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
								
							return false;	
						}
				
					}
					
					if(charCount>1){ WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; }
					
					prevChar=0;
					CheckNextChar=false;
					return true;
				}
				else if(index==line.length()-1)
				{
					if(prevChar==':')
					{
						WordBreakInitialization(index-1, linenumber,line,charCount);
						prevChar=0;
						CheckNextChar=false;
						return false;
					}
					
					if(charCount>1){WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; }
					WordBreakInitialization(index, linenumber,line,charCount);
					prevChar=0;
					CheckNextChar=false;
					return true;
				}
				
		  }
		  else if(ch=='.')
		  {
			if(index<=line.length()-1)
			{
				 if( !DQ && !SL_Comment && !ML_Comment && !SQ && !BL)
				  {
					  if(dotCount)
					  {
						  dotCount=false;
						
							  WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
						  
						
							dotCount=true;
						  return false;
					  }
					  else if(!dotCount)
					  {
						  CheckNextChar=true;
						  prevChar=ch;
						dotCount=true;
						  if(charCount>1 && !isNumber(line.charAt(index-1)))
						  {
							  WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
						  }
					
						  return false;
					  }
				  }
			}
		  }
		  else if(isOperator(String.valueOf(ch)) && !DQ && !SL_Comment && !ML_Comment && !SQ && !BL)
			{
				if(index<=line.length()-1)
				{
					if(ch=='+')
					{
						
						if(!CheckNextChar)
						{
							prevChar='+';
							CheckNextChar=true;
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='+')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								WordBreakInitialization(index, linenumber,line,charCount);
								CheckNextChar=false;
								prevChar=0;
								return false;
							}
							else if(prevChar=='=')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								
								CheckNextChar=true;
								prevChar='+';
								return false;
							}
							
						}
					}
					else if(ch=='%')
					{
						
						if(!CheckNextChar)
						{
							prevChar='%';
							CheckNextChar=true;
							//System.out.println(index+"_"+ch);
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='%')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								WordBreakInitialization(index, linenumber,line,charCount);
								CheckNextChar=false;
								prevChar=0;
								return false;
							}
							else if(prevChar=='=')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								
								CheckNextChar=true;
								prevChar='%';
								return false;
							}
							
						}
					}
					else if(ch=='>')
					{
						
						if(!CheckNextChar)
						{
							prevChar='>';
							CheckNextChar=true;
							//System.out.println(index+"_"+ch);
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='-')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								//WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								WordBreakInitialization(index, linenumber,line,charCount);
								CheckNextChar=false;
								prevChar=0;
								return false;
							}
							else if(prevChar=='=')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								
								CheckNextChar=true;
								prevChar='>';
								return false;
							}
							
						}
					}
					else if(ch=='<')
					{
						
						if(!CheckNextChar)
						{
							prevChar='<';
							CheckNextChar=true;
							//System.out.println(index+"_"+ch);
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='<')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								WordBreakInitialization(index, linenumber,line,charCount);
								CheckNextChar=false;
								prevChar=0;
								return false;
							}
							else if(prevChar=='=')
							{
							
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								
								CheckNextChar=true;
								prevChar='<';
								return false;
							}
							
						}
					}
					else if(ch=='-')
					{
						if(!CheckNextChar)
						{
							prevChar='-';
							CheckNextChar=true;
							
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='=')
							{
								WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=1;
										
							}
						
							WordBreakInitialization(index-1, linenumber,line,charCount-1);
							charCount=1;
							prevChar=ch;
							CheckNextChar=true;
							return false;
						}
							
					
					}
					else if(ch=='*')
					{
						if(!CheckNextChar)
						{
							prevChar='*';
							CheckNextChar=true;
							
							return false;
						}
						else if(CheckNextChar)
						{
							if(prevChar=='=')
							{
								WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=1;
										
							}
						
							WordBreakInitialization(index-1, linenumber,line,charCount-1);
							charCount=1;
							prevChar=ch;
							CheckNextChar=true;
							return false;
						}
							
					
					}
					else if(ch==':')
					{
						if(!CheckNextChar)
						{
							
							if(charCount>1)
							{
								
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								
							}
						
							prevChar=':';
							CheckNextChar=true;
							
							return false;
						}
						else if(CheckNextChar)
						{
							
							if(prevChar==':')
							{
								
								
								if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index-1, linenumber,line,charCount-1);charCount=1;
								//WordBreakInitialization(index, linenumber,line,charCount);
								
								/*CheckNextChar=false;
								prevChar=0;*/
								return false;
							}
							
							
						}
					
							
					
					}
					else if(ch=='=')
					{
						if(CheckNextChar)
						{
							if(prevChar=='+'  || prevChar=='%')
							{
								int tind=index-2;
							
								if(charCount>2){WordBreakInitialization(tind, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
							else if(prevChar=='>')
							{
								int tind=index-2;
							
								if(charCount>2){WordBreakInitialization(tind, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
							else if(prevChar=='<')
							{
								int tind=index-2;
							
								if(charCount>2){WordBreakInitialization(tind, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
							else if(prevChar=='-' || prevChar=='/')
							{
								int tind=index-2;
						
								if(charCount>2){WordBreakInitialization(tind, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
							else if(prevChar=='*')
							{
								int tind=index-2;
						
								if(charCount>2){WordBreakInitialization(tind, linenumber,line,charCount-2); charCount=2; }
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
							}
							else if(prevChar=='=' && !AssignmentOccurred)
							{
								if(index==line.length()-1)
								{
									WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; 
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								CheckNextChar=false;
								return false;
								}
								AssignmentOccurred=true;
								return false;
							}
							else if(prevChar=='=' && AssignmentOccurred)
							{
								AssignmentOccurred=false;
								
								if(charCount>3){WordBreakInitialization(index-3, linenumber,line,charCount-3); charCount=3; }
								WordBreakInitialization(index, linenumber,line,charCount);//charCount=1;
								
								CheckNextChar=false;
								prevChar=0;
								return false;
								
							}
						}
						else if(!CheckNextChar)
						{
							if(index==line.length()-1)
							{
								//System.out.println(ch);
								WordBreakInitialization(index, linenumber,line,charCount);
								prevChar=0;
								
								return false;
								
							}
							else if(index<line.length()-1)
							{
								//System.out.println(ch);
								WordBreakInitialization(index-1, linenumber,line,charCount-1);
								charCount=1;
								prevChar=0;
								
							
								
							}
							prevChar=ch;
							CheckNextChar=true;
							return false;
						}
					}
					
					
					
				}
				
			}
	
	
	
		 if(SQ &&  ! ML_Comment && !SL_Comment)
			{
			 
				
				if(!BL)
				{
					
				
					if(sqchar_counter==2)
					{
						SQ=false;
						sqchar_counter=0;
						BL=false;
						return true;//word break;
					}
					else
						sqchar_counter++;
				}
				else if(BL)
				{
				
					if(sqchar_counter==3)
					{
					
						SQ=false;
						sqchar_counter=0;
						BL=false;
						return true; //char break;
					}
					else
						sqchar_counter++;
				
				}
			}
			else if(BL && prevChar=='\\' && !ML_Comment && !SQ && !SL_Comment)
			{
				
				BL=false;
				prevChar=0;
				CheckNextChar=false;
				
				
			}
		 if(CheckNextChar)
		 {
			 if(index<=line.length()-2)
			 {
				 if(prevChar=='+' || prevChar=='%' || prevChar==':')
				 {
					 if(charCount>1){ WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='>')
				 {
					 if(charCount>1){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='<')
				 {
					 if(charCount>1){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='-' || prevChar=='/')
				 {
					
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='*')
				 {
					
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				/* else  if(prevChar==':')
				 {
					
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }*/
				 else  if(prevChar=='=' && AssignmentOccurred && ch!='=')
				 {
					/* System.out.println(ch);
					 if(index==line.length()-1)
					 {
						 WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
							WordBreakInitialization(index, linenumber,line,charCount); 
							prevChar=0;
							CheckNextChar=false;
							AssignmentOccurred=false;
							return false;
					 }*/
					 if(charCount>3){WordBreakInitialization(index-3, linenumber,line,charCount-3); charCount=3; }
						WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2;
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						prevChar=0;
						CheckNextChar=false;
						AssignmentOccurred=false;
						return false;
				 }
				 else  if(prevChar=='=' && !AssignmentOccurred && ch!='=')
				 {
					 if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						prevChar=0;
						CheckNextChar=false;
						AssignmentOccurred=false;
						return false;
				 }
				 
			 }
			 else  if(index==line.length()-1)
			 {
				 if(prevChar=='+' || prevChar=='%')
				 {
					 if(charCount>1){WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; }
						WordBreakInitialization(index, linenumber,line,charCount); 
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='>' || prevChar=='<')
				 {
					 if(charCount>1){WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; }
						WordBreakInitialization(index, linenumber,line,charCount); 
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='-')
				 {
					 
						WordBreakInitialization(index, linenumber,line,charCount); 
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='*')
				 {
					 
						WordBreakInitialization(index, linenumber,line,charCount); 
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar==':')
				 {
					 if(charCount>1){WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1; }
						WordBreakInitialization(index, linenumber,line,charCount); 
						prevChar=0;
						CheckNextChar=false;
						return false;
				 }
				 else  if(prevChar=='=' && AssignmentOccurred && ch!='=')
				 {
					 if(charCount>3){WordBreakInitialization(index-3, linenumber,line,charCount-3); charCount=3; }
						WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2;
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						WordBreakInitialization(index, linenumber,line,charCount);
						prevChar=0;
						CheckNextChar=false;
						AssignmentOccurred=false;
						return false;
				 }
				 else  if(prevChar=='=' && !AssignmentOccurred && ch!='=')
				 {
					 if(charCount>2){WordBreakInitialization(index-2, linenumber,line,charCount-2); charCount=2; }
						WordBreakInitialization(index-1, linenumber,line,charCount-1); charCount=1;
						WordBreakInitialization(index, linenumber,line,charCount);
						prevChar=0;
						CheckNextChar=false;
						AssignmentOccurred=false;
						return false;
				 }
				 if(SQ)
				 {
					
					 System.out.println(ch);
							sqchar_counter=0;
							BL=false;
							SQ=false;
							return true;
						
					 
				 }
			 }
				 
			 
		 }
		if(index==line.length()-1 && !ML_Comment)
		{
			
			return true;
		}
		prevChar=0;
		CheckNextChar=false;
		
		return false;
	}
	static boolean ContainsOnlyNotSpace(String a)
	{
		for (int i = 0; i < a.length(); i++) {
			if(a.charAt(i)!=' ')
				return true;
		}
		return false;
	}
	static void WordBreakInitialization(int index,int linenumber,String line,int chCount)
	{
		
		upperBond=chCount;

		String a=String.copyValueOf(line.toCharArray(),lowerBond, upperBond).trim();

		if(ContainsOnlyNotSpace(a))
		{
		
			wordInsert(a,linenumber);
		
		}
		

		lowerBond=index+1;
		charCount=0;
		
	}
	static public void ReadFile()
	{
		try {
			Scanner in=new Scanner(new File("code.txt"));
			while(in.hasNextLine())
			{
				Lineword.add(in.nextLine());
				Linenum.add(counter);
				
				counter++;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	static	public void PrintDetails_Word_Gathered()
	{
		
	int o=0;
	while(o<index_WordContainer)
	{
		System.out.println("word:"+Words.get(o)+" Line#"+linen.get(o));
		o++;
	}

	}
	static	void wordInsert(String word,int linenumber)
	{
		
		if(word.contentEquals(" ") || word.contentEquals("")  || word.contentEquals("  ") ) return ;
		
		linen.add(linenumber);
		word=word.trim();
		Words.add(word);
		int o=0;
	
		index_WordContainer++;
		
	}
}



