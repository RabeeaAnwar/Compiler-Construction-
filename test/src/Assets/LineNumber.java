package Assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LineNumber {
	ArrayList<String> linen=new ArrayList();
	public void ReadLineNumber()
	{
		try {
			Scanner in=new Scanner(new File("linen.txt"));
			while(in.hasNextLine())
			{
				linen.add(in.nextLine());		
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
