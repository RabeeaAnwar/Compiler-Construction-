package Assets;

public class KeyWords
{
	String _access[]={"local","hide","protected"};
	String _static[]={"stable",null};	
	String _import[]={"inherits","interned",null};
	String _TypeModifies[]={"empty","dig","alpha","beta","frac"};
	String _classInterface[]={"group","interface"};
	String _function[]={"main"};
	String _uniOp[]={"~",":)",":("};
	S_st sst = new S_st();
	public boolean isParentFollow(String ch,String follows[])
	{
		if(follows==null) return false;
		for(int i=0;i<follows.length;i++)
		{
			if(ch.equals(follows[i]))
				return true;
		}
		return false;
	}
	public boolean isConst(String word)
	{
		if(word.contains("String")) return true;
		else if(word.contains("Char")) return true;
		else if(word.contains("Float")) return true;
		else if(word.contains("int")) return true;
		else if(word.contains("boolean")) return true;
		else
		return false;
	}
	public String getConst(String word)
	{
		if(word.contains("String")) return "beta";
		else if(word.contains("Char")) return "alpha";
		else if(word.contains("Float")) return "frac";
		else if(word.contains("int")) return "dig";
		else if(word.contains("boolean")) return "boolean";
		else
		return null;
	}
	public boolean isUniOp(String word)
	{
		if(word.equals("Incdec")) return true;
		else if(word.equals(":)")) return true;
		else if(word.equals(":(")) return true;
		
		else
		return false;
	}
/*	public boolean isConst(String word)
	{
		if(word.charAt(0)=='\"') return true;
		else if(word.charAt(0)=='\'') return true;
		else if(word.contains(".")) return true;
		else if(word.charAt(0)>='0' && word.charAt(0)<='9' ) return true;
		else
		return false;
	}
	public boolean isUniOp(String word)
	{
		if(word.equals("~")) return true;
		else if(word.equals(":)")) return true;
		else if(word.equals(":(")) return true;
		
		else
		return false;
	}*/
	
	static class S_st
	{
		static String _if[]={"si","sino"};
		static String _switch[]={"test","check"};
		static String _loops[]={"until","to"};
		static String _others[]={"continue","return","break"};
	}
	
}
