package Assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CP {
	ArrayList<String> cp=new ArrayList();
	public void ReadCP()
	{
		try {
			Scanner in=new Scanner(new File("cp.txt"));
			while(in.hasNextLine())
			{
				cp.add(in.nextLine());		
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
