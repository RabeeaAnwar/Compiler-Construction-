package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Assets.KeyWords;









public class Syntaxtification {
static int counter=0,scope=0;
static ArrayList<String> cp = new ArrayList();
static ArrayList<String> vp = new ArrayList();
/*public static ArrayList<String> varTable = new ArrayList();
public static ArrayList<Boolean> isVarArrayTable = new ArrayList();
public static ArrayList<Boolean> varScope = new ArrayList();*/
static ArrayList<Integer> Linenum = new ArrayList();
static tableVar tv=new tableVar();
static tableClass ct=new tableClass();
static tableInterface interfacet=new tableInterface();
	public  void SyntaxMain()
	{
		ReadCP();
		ReadVP();
		/*ReadVarTable*/

		InterfaceModel im = new InterfaceModel(cp,vp,scope);
		MainClassModel cm= new MainClassModel(cp,vp,scope); 
		ClassModel ncm = new ClassModel(cp,vp,scope,false);
		boolean hasOccured=false;
		int counter=0;
		int brackets=0;
		
		while(true)
		{
			if(counter>=cp.size())
				break;
			
			if(cp.get(counter).equals("acces modifier"))
			{
				
				if(vp.get(counter+1).equals("stable"))
				{
					
					if(cp.get(counter+2).equals("class") && vp.get(counter).equals("local"))
					{
						if(!hasOccured)
						{
							if(cm.mainClassFunction(cp, counter))
							{
								
								counter=cm.count-1;
									System.out.println("Main Class: Successful ");
									hasOccured=true;
									
							}
							else
								System.out.println("Main Class: not Successful  "+vp.get(counter));
						}
						else
						{
							System.out.println("Main Class cannot occur more than once  "+vp.get(counter));
						}
					}
					else if(cp.get(counter+2).equals("class") && !vp.get(counter).equals("local"))
					{
						if(ncm.classFunction(cp, counter))
						{
							
							counter=ncm.count-1;
								System.out.println("Class: Successful ");
						
								
						}
						else
							System.out.println("Class: not Successful  "+vp.get(counter));
					}
					else if(cp.get(counter+2).equals("sculpture"))
					{
						System.out.println(cp.get(counter+2));
						if(im.interfaceFunction(cp, counter))
						{
							
							counter=im.count-1;
							
								System.out.println("interface: Successful ");
						
								
						}
						else
							System.out.println("interface: not Successful  "+vp.get(counter));
					}
				}
				else if(cp.get(counter+1).equals("class"))
				{
					if(ncm.classFunction(cp, counter))
					{
						
						counter=ncm.count-1;
							System.out.println("Class: Successful ");
					
							
					}
					else
						System.out.println("Class: not Successful  "+vp.get(counter));
				}
				else if(cp.get(counter+1).equals("sculpture"))
				{
					if(im.interfaceFunction(cp, counter))
					{
						
						counter=im.count-1;
							System.out.println("interface: Successful ");
					
							
					}
					else
						System.out.println("interface: not Successful  "+vp.get(counter));
				}
			}
			else if(vp.get(counter).equals("stable"))
			{
				if(cp.get(counter+1).equals("class"))
				{
					if(ncm.classFunction(cp, counter))
					{
						
						counter=ncm.count-1;
							System.out.println("Class: Successful ");
					
							
					}
					else
						System.out.println("Class: not Successful  "+vp.get(counter));
				}
				else if(cp.get(counter+1).equals("sculpture"))
				{
					if(im.interfaceFunction(cp, counter))
					{
						
						counter=im.count-1;
							System.out.println("interface: Successful ");
					
							
					}
					else
						System.out.println("interface: not Successful  "+vp.get(counter));
				}
			}
				else if(cp.get(counter).equals("class"))
				{
					if(ncm.classFunction(cp, counter))
					{
						
						counter=ncm.count-1;
							System.out.println("Class: Successful ");
					
							
					}
					else
						System.out.println("Class: not Successful  "+vp.get(counter));
				}
				else if(cp.get(counter).equals("sculpture"))
				{
					if(im.interfaceFunction(cp, counter))
					{
						
						counter=im.count-1;
							System.out.println("interface: Successful ");
					
							
					}
					else
						System.out.println("interface: not Successful  "+vp.get(counter));
				}
			
			
	
		counter++;
		
		}
	
			
	}
	static public void ReadCP()
	{
		try {
			Scanner in=new Scanner(new File("cp.txt"));
			while(in.hasNextLine())
			{
				cp.add(in.nextLine());
				Linenum.add(counter);
				counter++;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	static public void ReadVP()
	{
		try {
			Scanner in=new Scanner(new File("vp.txt"));
			while(in.hasNextLine())
			{
				vp.add(in.nextLine());
				
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
//---------------------------------------------------------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------------------------------------------------------//





//////////////////////////////////////.............................\\\\\\\\\\\\\\\\\\\\\\\\\\
//Global Interface


//Interface
class InterfaceModel {
	private KeyWords words = new KeyWords();
	public int count=0,count_int=0,failedCount=0,scope=0;
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private	String follow_for_body[]={"]"};
		 InterfaceModelSemantic ims;
		 private interfaceBodyModel body1;
		 private InterfaceModel(){}
	 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			ims= new InterfaceModelSemantic(cp, vp,  scope);
			this.scope=scope;
			body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
			follow_of_Parent=null;
	 }
	 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			ims= new InterfaceModelSemantic(cp, vp,  scope);
			body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
			
	 }
	
	 public boolean interfaceFunction(ArrayList<String> cp,int c)
	 {
		 int start=c;
		 
		 if(cp.get(c).equals("acces modifier"))
		 {
			 
			 c++;
			 
			 if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											if(ims.interfaceFunction(cp, start))
											{
												System.out.println("Semantic is correct of interface "+start);
											}
											else
												System.out.println("Semantic is not correct of interface "+start);
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   	
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }//if stable not present.
			 if(cp.get(c).equals("sculpture"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.InternBody(cp,c))
								 {
									c=body1.count;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										if(ims.interfaceFunction(cp, start))
										{
											System.out.println("Semantic is correct of interface "+start);
										}
										else
											System.out.println("Semantic is not correct of interface "+start);
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }	
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 } // if access modifier not present
		 else if(cp.get(c).equals("type modifier"))
		 {
			 c++;
			 if(cp.get(c).equals("sculpture"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.InternBody(cp,c))
								 {
									c=body1.count;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										if(ims.interfaceFunction(cp, start))
										{
											System.out.println("Semantic is correct of interface "+start);
										}
										else
											System.out.println("Semantic is not correct of interface "+start);
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
	
		 }//if stable+access modifier not present.
		 if(cp.get(c).equals("sculpture"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				
				 c++;
				 if(S1(cp,c)) //Confirmation of params - (
				 {
					 c=count_int;
						 if(cp.get(c).equals("["))
						 {
							 c++;
							 
							 if(body1.InternBody(cp,c))
							 {
								c=body1.count;
								
								if(cp.get(c).equals("]"))
								{
									c++;
									count=count_int=c;
									if(ims.interfaceFunction(cp, start))
									{
										System.out.println("Semantic is correct of interface "+start);
									}
									else
										System.out.println("Semantic is not correct of interface "+start);
									return true; //Successful parsed.
									
								}
								if(failedCount<c)
						   			failedCount=c;
							   	
							 }
							 if(failedCount<c && body1.failedCount<c)
						   			failedCount=c;
							   	else if(body1.failedCount>c)
							   			failedCount=body1.failedCount;
						 }
						 if(failedCount<c)
					   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		
		 }

		
		 return false;

	 }
	 private boolean S1_List(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals(","))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean S1(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("interface"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
		
				 
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 if(cp.get(c).equals("inheritance"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				
				 c++;
				 if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 return false;
	 }
	 
	 
	 
	 

	//Semantic Interface Start

	//Interfaces Semantic Start
		
		class InterfaceModelSemantic {
			private KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 private ArrayList<String> cp = new ArrayList();
			 private ArrayList<String> vp = new ArrayList();
				 private String follow_of_Parent[];
				 private String follow_for_exp[]={")","["};
				 private	String follow_for_body[]={"]"};
				 tableInterface ti = new tableInterface();
				 private interfaceBodyModel1 body1;
				 private InterfaceModelSemantic(){}
			 public InterfaceModelSemantic(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					
					this.scope=scope;
					body1=new interfaceBodyModel1(cp,vp,follow_for_body,scope);
					follow_of_Parent=null;
			 }
			 public InterfaceModelSemantic(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					this.scope=scope;
					
					//body=new BodyModel(cp,vp,follow_for_body);
					body1=new interfaceBodyModel1(cp,vp,follow_for_body,scope);
					
			 }
			
			 public boolean interfaceFunction(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("acces modifier"))
				 {
					
					 c++;
					 
					 if(cp.get(c).equals("type modifier"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("sculpture"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 if(!lookUpClass(vp.get(c)))
								 {
									 if(!lookUpInterface(vp.get(c)))	
									 {
										 Syntaxtification.interfacet.isPrivate.add(true);
										 Syntaxtification.interfacet.isStable.add(true);
										 Syntaxtification.interfacet.nameInterfaces.add(vp.get(c));
										 
									 }
									 else
										 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
								 }
								 else
									 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
								 c++;
								 if(S1(cp,c)) //Confirmation of params - (
								 {
									 c=count_int;
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 
											 if(body1.InternBody(cp,c))
											 {
												c=body1.count;
												Syntaxtification.interfacet.prototypes.add(body1.tf);
												if(cp.get(c).equals("]"))
												{
													c++;
													count=count_int=c;
													return true; //Successful parsed.
													
												}
												if(failedCount<c)
										   			failedCount=c;
											   	
											 }
											 if(failedCount<c && body1.failedCount<c)
										   			failedCount=c;
											   	else if(body1.failedCount>c)
											   			failedCount=body1.failedCount;
										 }
										 if(failedCount<c)
									   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
							   	
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }//if stable not present.
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookUpClass(vp.get(c)))
							 {
								 if(!lookUpInterface(vp.get(c)))
								 {
									 Syntaxtification.interfacet.isPrivate.add(true);
									 Syntaxtification.interfacet.isStable.add(true);
									 Syntaxtification.interfacet.nameInterfaces.add(vp.get(c));
									 
								 }
								 else
									 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
							 }
							 else
								 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											Syntaxtification.interfacet.prototypes.add(body1.tf);
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }	
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 } // if access modifier not present
				 else if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookUpClass(vp.get(c)))
							 {
								 if(!lookUpInterface(vp.get(c)))
								 {
									 Syntaxtification.interfacet.isPrivate.add(false);
									 Syntaxtification.interfacet.isStable.add(true);
									 Syntaxtification.interfacet.nameInterfaces.add(vp.get(c));
									 
								 }
								 else
									 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
							 }
							 else
								 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											Syntaxtification.interfacet.prototypes.add(body1.tf);
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
			
				 }//if stable+access modifier not present.
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
						 {
							 if(!lookUpInterface(vp.get(c)))
							 {
								 Syntaxtification.interfacet.isPrivate.add(true);
								 Syntaxtification.interfacet.isStable.add(true);
								 Syntaxtification.interfacet.nameInterfaces.add(vp.get(c));
								 
							 }
							 else
								 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
						 }
						 else
							 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										Syntaxtification.interfacet.prototypes.add(body1.tf);
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				
				 }

				
				 return false;

			 }
			 private boolean S1_List(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals(","))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpInterface(vp.get(c)))
							 System.out.println("No interface found Error "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1_List(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						 
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("inheritance"))
				 {
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
				 return false;
			 }
			 private boolean S1(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("interface"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpInterface(vp.get(c)))
							 System.out.println("No interface found "+ vp.get(c)+ " at char number: "+c);
						 
						 c++;
						 if(S1_List(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("inheritance"))
							 {
								 c++;
								 if(cp.get(c).equals("ID"))
								 {
									 if(!lookUpClass(vp.get(c)))
										 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
									 c++;
									 if(cp.get(c).equals("["))
									 {
										 count_int=c;
										 return true;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 else if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("inheritance"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
							 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(cp.get(c).equals("["))
						 {
							 count_int=c;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 return false;
			 }
			 boolean lookUpInterface(String name)
			 {
				 if( Syntaxtification.interfacet.nameInterfaces.size()>0)
				 {
					 for(int i=0;i< Syntaxtification.interfacet.nameInterfaces.size();i++)
					 {
						 if(Syntaxtification.interfacet.nameInterfaces.get(i).equals("name"))
							 return true;
					 }
				 }
				 return false;
			 }
			 boolean lookUpClass(String name)
			 {
				 if( Syntaxtification.ct.Cname.size()>0)
				 {
					 for(int i=0;i< Syntaxtification.ct.Cname.size();i++)
					 {
						 if(Syntaxtification.ct.Cname.get(i).equals("name"))
							 return true;
					 }
				 }
				 return false;
			 }
			 

			//Interface Body Semantic Start



			class interfaceBodyModel1 {
				private KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				tableFunction interfaces_tf = new tableFunction();
				 private ArrayList<String> cp = new ArrayList();
				 private ArrayList<String> vp = new ArrayList();
				 private tableFunction tf = new tableFunction();
					 private String follow_of_Parent[];
					 private String follow_for_exp[]={")","["};
					 private interfaceBodyModel1(){}
				 public interfaceBodyModel1(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						follow_of_Parent=null;
						this.scope=scope;
				 }
				 public interfaceBodyModel1(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						follow_of_Parent=Follow;
						this.scope=scope;
				 }
				 public boolean InternBody(ArrayList<String> cp,int c)
				 {
					 if(multiprototypes(cp,c))
					 {
						count=c=count_int;
						interfaces_tf.paramList.add(getParams());
						
						return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 private boolean multiprototypes(ArrayList<String>cp,int c)
				 {
					 if(prototypeFun(cp,c))
					 {
						c=count_int;
						if(multiprototypes(cp,c))
						 {
							c=count_int;
							return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 private boolean prototypeFun(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("ID"))
						 {
							 tf.isPrivate.add(false);
							 tf.isStable.add(false);
							 tf.ReturnType.add(vp.get(c-1));
							 tf.nameFunction.add(vp.get(c));
							 tf.varScope.add(scope);
							 c++;
							 
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 c++;
								 
								 if(params(cp, c))
								 {
									 
									 c=count_int;
									 if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										count_int=c;
										return true;
										 
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   
					 }
					 return false;
				 }
				 private  ArrayList<String> params = new ArrayList();	
				 private ArrayList<String> getParams()
				 {
					 return params;
				 }
				 private void SetParams(String param)
				 {
					 params.add(param);
				 }
				 private tableVar tv = new tableVar();
				 private boolean lookUpVar(String name)
			 	 {
					 if(tv.varDT.size()>0)
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return true;
			 		 return false;
			 	 }
				 private boolean params(ArrayList<String> cp,int c)
				 {
					 if(fields(cp,c))
					 {
						 c=count_int;
						 
						 return true;
					 }
					 else if(Object(cp, c))
					 {
						 c=count_int;
							count_int=c;
							return true;
					 }
					 else if(cp.get(c).equals(")"))
					 {
						 count_int=++c;
						 
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   	
					 return false;
				 }
				 private boolean list(ArrayList<String> classPart,int count)
				 {
					 
					 if(classPart.get(count).equals(","))
					 {
						 count++;
						 if(cp.get(count).equals("datatype"))
						 {
							 count++;
							 if(classPart.get(count).equals("ID"))
								{
								 if(!lookUpVar(vp.get(count)))
									{
										tv.varTable.add(vp.get(count));
										tv.varDT.add(vp.get(count-1));
										tv.varScope.add(scope);
										tv.isVarArrayTable.add(false);
										SetParams(vp.get(count-1));
									}
									else
										System.out.println("Redeclaration of variable "+vp.get(count)+" at char number: "+count);
									count++;
									
									if(list(classPart,count))
									{
										count=count_int;
										count_int=count;
										//System.out.println(count+":rr:"+cp.get(count));
										return true;
									}
									/*else
									{
										
										count--;
										//System.out.println(count+":rr:"+cp.get(count));
										if(Object(classPart, count))
										 {
											 count=count_int;
												count_int=count;
												
												return true;
										 }
									}*/
									if(failedCount<count)
							   			failedCount=count;
								}
							 if(failedCount<count)
						   			failedCount=count;
						 }
						 else if(Object(classPart, count))
						 {
							 count=count_int;
								count_int=count;
								
								return true;
						 }
						 if(failedCount<count)
					   			failedCount=count;
						 
					 }
					 else if(classPart.get(count).equals(")"))
					 {
						 count_int=count;
							return true;
					 }
					 if(failedCount<count)
				   			failedCount=count;
					   
					 return false;
				 }
				 private boolean Object(ArrayList<String> cp,int c)
				 {
					 if(c>=cp.size())
					 {
						 return true;
					 }
					
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(lookUpClass(vp.get(c-1)))
			 				 	{
			 				 		if(!lookUpVar(vp.get(c)))
			 						 {
			 							 
			 							 tv.varTable.add(vp.get(c));
			 							 tv.varScope.add(scope);
			 							 tv.varDT.add("class_"+vp.get(c-1));
			 							 SetParams(vp.get(c-1));
			 						 }
			 						 else
			 						 {
			 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
			 						 }
			 				 	}
			 				 	 else
								 {
									 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
								 }
							 c++;
							
								 if(Ob(cp, c))
								 {
									c=count_int;
									 if(c>=cp.size())
									 {
										 if(cp.get(c-1).equals(")"))
										 {
											 count_int=c;
											 return true;
										 }
										 if(failedCount<c)
									   			failedCount=c;
										 
									 }
									 else
									 {
										 
										 if(cp.get(c-1).equals(")"))
										 {
											 count_int=c;
											 return true;
										 }
										 else if(cp.get(c).equals(")"))
										 {
											 
											 count_int=c=c+1;
											 
											 return true;
										 }
										 if(failedCount<c)
									   			failedCount=c;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
					 return false;
				 }
				 private boolean Ob(ArrayList<String> cp,int c)
				 {
					 if(c>=cp.size())
					 {
						 return true;
					 }
					 else
					 {
						 
						 if(cp.get(c).equals(","))
						 {
							 c++;
							
							 if(cp.get(c).equals("ID")) //obj1
							 {
								 c++;
								 
								if(cp.get(c).equals("ID"))
								{
									if(lookUpClass(vp.get(c-1)))
				 				 	{
				 				 		if(!lookUpVar(vp.get(c)))
				 						 {
				 							 
				 							 tv.varTable.add(vp.get(c));
				 							 tv.varScope.add(scope);
				 							 tv.varDT.add("class_"+vp.get(c-1));
				 							 SetParams(vp.get(c-1));
				 						 }
				 						 else
				 						 {
				 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
				 						 }
				 				 	}
				 				 	 else
			 						 {
			 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
			 						 }
									c++;
								
									 if(Ob(cp,c))
									 {
										 c=count_int;
										 if(cp.get(c).equals(")"))
										 {
											 c++;
											 count_int=c;
											 return true;
										 }
										 else
											 if(cp.get(c-1).equals(")"))
											 {
												 
												 count_int=c;
												 return true;
											 }
												
									
										 
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 
									
								}
								if(failedCount<c)
						   			failedCount=c;
							 }
							 else if(cp.get(c).equals("datatype"))
							 {
							
								 if(fields(cp, c))
								 {
									 c=count_int;
										count_int=c;
										return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 else  if(cp.get(c).equals(")"))
						 {
							 c++;
							 count_int=c;
							 
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   	
						 return false;
				 }

				 private boolean fields(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("datatype"))
					 {
						 c++;
						
						if(cp.get(c).equals("ID"))
						{
							
							if(!lookUpVar(vp.get(c)))
							{
								tv.varTable.add(vp.get(c));
								tv.varDT.add(vp.get(c-1));
								tv.varScope.add(scope);
								tv.isVarArrayTable.add(false);
								SetParams(vp.get(c-1));
							}
							else
								System.out.println("Redeclaration of variable "+vp.get(c)+" at char number: "+c);
							c++;
							
							 if(list(cp,c))
							{
								c=count_int;
								
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										
										count_int=c;
										
										return true;
									}
									if(failedCount<c)
							   			failedCount=c;
								}
								else if(cp.get(c).equals(")"))
								{
									c++;
									
									count_int=c;

									return true;
								}
								else if(cp.get(c-1).equals(")"))
								{

									count_int=c;

									return true;
								}
								if(failedCount<c)
						   			failedCount=c;
							}
							 if(failedCount<c)
						   			failedCount=c;
						}
						if(failedCount<c)
				   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 
					 return false;
				 }
			}
			//Interface Body Semantic End
		}
		
	//Interfaces Semantic End
		
}

//Interfaces  End

//Interface Body  Start
class interfaceBodyModel {
	private KeyWords words = new KeyWords();
	public int count=0,count_int=0,failedCount=0,scope=0;
	
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
	 private tableFunction tf = new tableFunction();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private interfaceBodyModel(){}
	 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			follow_of_Parent=null;
	 }
	 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			follow_of_Parent=Follow;			
	 }
	 public boolean InternBody(ArrayList<String> cp,int c)
	 {
		 if(multiprototypes(cp,c))
		 {
			count=c=count_int;
			
			
			return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 return false;
	 }
	 private boolean multiprototypes(ArrayList<String>cp,int c)
	 {
		 if(prototypeFun(cp,c))
		 {
			c=count_int;
			if(multiprototypes(cp,c))
			 {
				c=count_int;
				return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 return false;
	 }
	 private boolean prototypeFun(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
		 {
			 c++;
			 
			 if(cp.get(c).equals("ID"))
			 {
			
				 c++;
				 
				 if(cp.get(c).equals("(")) //Confirmation of params - (
				 {
					 c++;
					 
					 if(params(cp, c))
					 {
						 
						 c=count_int;
						 if(cp.get(c).equals("Terminator"))
						 {
							 c++;
							count_int=c;
							return true;
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
		 }
		 return false;
	 }
	 private  ArrayList<String> params = new ArrayList();	
	 private ArrayList<String> getParams()
	 {
		 return params;
	 }
	 private void SetParams(String param)
	 {
		 params.add(param);
	 }
	 private tableVar tv = new tableVar();
	 private boolean lookUpVar(String name)
	 {
		 if(tv.varDT.size()>0)
		 for(int i=0;i<tv.varTable.size();i++)
			 if(tv.varTable.get(i).equals(name))
				 return true;
		 return false;
	 }
	 private boolean params(ArrayList<String> cp,int c)
	 {
		 if(fields(cp,c))
		 {
			 c=count_int;
			 
			 return true;
		 }
		 else if(Object(cp, c))
		 {
			 c=count_int;
				count_int=c;
				return true;
		 }
		 else if(cp.get(c).equals(")"))
		 {
			 count_int=++c;
			 
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean list(ArrayList<String> classPart,int count)
	 {
		 
		 if(classPart.get(count).equals(","))
		 {
			 count++;
			 if(cp.get(count).equals("datatype"))
			 {
				 count++;
				 if(classPart.get(count).equals("ID"))
					{
						count++;
						
						if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							//System.out.println(count+":rr:"+cp.get(count));
							return true;
						}
					
						if(failedCount<count)
				   			failedCount=count;
					}
				 if(failedCount<count)
			   			failedCount=count;
			 }
			 else if(Object(classPart, count))
			 {
				 count=count_int;
					count_int=count;
					
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			 
		 }
		 else if(classPart.get(count).equals(")"))
		 {
			 count_int=count;
				return true;
		 }
		 if(failedCount<count)
	   			failedCount=count;
		   
		 return false;
	 }
	 private boolean Object(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		
		 if(cp.get(c).equals("ID"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				
					 if(Ob(cp, c))
					 {
						c=count_int;
						 if(c>=cp.size())
						 {
							 if(cp.get(c-1).equals(")"))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
							 
						 }
						 else
						 {
							 
							 if(cp.get(c-1).equals(")"))
							 {
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals(")"))
							 {
								 
								 count_int=c=c+1;
								 
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   
		 return false;
	 }
	 private boolean Ob(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 else
		 {
			 
			 if(cp.get(c).equals(","))
			 {
				 c++;
				
				 if(cp.get(c).equals("ID")) //obj1
				 {
					 c++;
					 
					if(cp.get(c).equals("ID"))
					{
						
						c++;
					
						 if(Ob(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals(")"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
							 else
								 if(cp.get(c-1).equals(")"))
								 {
									 
									 count_int=c;
									 return true;
								 }
									
						
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 
						
					}
					if(failedCount<c)
			   			failedCount=c;
				 }
				 else if(cp.get(c).equals("datatype"))
				 {
				
					 if(fields(cp, c))
					 {
						 c=count_int;
							count_int=c;
							return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else  if(cp.get(c).equals(")"))
			 {
				 c++;
				 count_int=c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
			 return false;
	 }

	 private boolean fields(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("datatype"))
		 {
			 c++;
			
			if(cp.get(c).equals("ID"))
			{
				
				
				c++;
				
				 if(list(cp,c))
				{
					c=count_int;
					
					if(c>=cp.size())
					{
						if(cp.get(c-1).equals(")"))
						{
							
							count_int=c;
							
							return true;
						}
						if(failedCount<c)
				   			failedCount=c;
					}
					else if(cp.get(c).equals(")"))
					{
						c++;
						
						count_int=c;

						return true;
					}
					else if(cp.get(c-1).equals(")"))
					{

						count_int=c;

						return true;
					}
					if(failedCount<c)
			   			failedCount=c;
				}
				 if(failedCount<c)
			   			failedCount=c;
			}
			if(failedCount<c)
	   			failedCount=c;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 
		 return false;
	 }
}
//Interface Body  End






//End Interface

	
	
	

//Semantic Interface End





















////////////////////////////////////////////................Main Class .............................\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//Main Class Start
class MainClassModel 
{

	private KeyWords words = new KeyWords();
public	int count=0,count_int=0,failedCount,scope=0;
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private	String follow_for_body[]={"]"};
		 private	ExpModel exp ;
		 private	BodyModel body;
		 private mainClassBodyModel body1;
		 public boolean isMainDefined=false;
		 MainClassModel_Semantic mcms;
		 private MainClassModel(){}
	 public MainClassModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true);
			body=new BodyModel(cp,vp,follow_for_body,scope);
			body1=new mainClassBodyModel(cp,vp,follow_for_body,scope);
			
			follow_of_Parent=null;
	 }
	 public MainClassModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp,true);
			body=new BodyModel(cp,vp,follow_for_body,scope);
			body1=new mainClassBodyModel(cp,vp,follow_for_body,scope);
			
	 }
	 public boolean mainClassFunction(ArrayList<String> cp,int c)
	 {
		 int start=c;
		 if(vp.get(c).equals("local"))
		 {
			
			 c++;
			 
			 if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 //////////System.out.println(""+cp.get(c));
				 if(cp.get(c).equals("class"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.mainClassBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											isMainDefined=body1.isMainFunctionDefined;
											mcms=new MainClassModel_Semantic(cp,vp,scope);
											if(mcms.mainClassFunction(cp, start))
											{
												System.out.println("Main Class Semantic is Correct started at char number: "+start);
											}
											else
												System.out.println("Main Class Semantic is not Correct started at char number: "+start);
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }//if stable not present.
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.mainClassBody(cp,c))
								 {
									 c=body1.count;
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										isMainDefined=body1.isMainFunctionDefined;
										mcms=new MainClassModel_Semantic(cp,vp,scope);
										if(mcms.mainClassFunction(cp, start))
										{
											System.out.println("Main Class Semantic is Correct started at char number: "+start);
										}
										else
											System.out.println("Main Class Semantic is not Correct started at char number: "+start);
										
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 if(failedCount<c)
				failedCount=c;
		 return false;

	 }
	 private boolean S1_List(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals(","))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean S1(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("interface"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 if(cp.get(c).equals("inheritance"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }

	
//MainClassBodyModel Start

	 class mainClassBodyModel 
	 {
	 	KeyWords words = new KeyWords();
	 	public int count=0,count_int=0,failedCount=0,scope=0;
	 	ArrayList<String> cp = new ArrayList();
	 	ArrayList<String> vp = new ArrayList();
	 	String follow_of_Parent[]; //Usually ]
	 	String follow_for_body_After[]={"]"};
	 	String follow_for_body_Before[]={"]","acces modifier"};
	 	boolean isMainFunctionDefined=false;
	 	
	 	mainClassBodyModel(){}
	  public mainClassBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	  {
	 		this.cp = cp;
	 		this.vp = vp;
	 		this.scope=scope;
	  }
	  public mainClassBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	  {
	 		this.cp = cp;
	 		this.vp = vp;
	 		follow_of_Parent=Follow;
	 		this.scope=scope;
	  }
	 public boolean mainClassBody(ArrayList<String>cp,int c)
	 {
	 	if(mb_mst(cp,c))
	 	 {
	 		 c=count_int;
	 		 count=c;
	 		 //System.out.println(c+":mainclassbody Passed: "+cp.get(c));
	 		 return true;
	 	 }
	 	if(failedCount<c)
	 			failedCount=c;
	 	 return false;
	 }
	 public boolean mb_mst(ArrayList<String>cp,int c)
	 {
	 	classBodyModel body1;
	 	if(isMainFunctionDefined)
	 	{
	 		body1=new classBodyModel(cp,vp,follow_for_body_After,scope);
	 	}
	 	else
	 		body1=new classBodyModel(cp,vp,follow_for_body_Before,scope);
	 	
	 	if(body1.classbody(cp, c) && !isMainFunctionDefined)
	 	{
	 		//System.out.println(c+"gff:"+cp.get(c));
	 		count_int=c=body1.count;
	 		//System.out.println(c+"gg:"+cp.get(c));
	 		mainFuncModel mfm = new mainFuncModel(cp,vp,scope);
	 		if(mfm.mainFunc(cp,c))
	 		{
	 			c=count_int=mfm.count;
	 			isMainFunctionDefined=true;
	 			if(mb_mst(cp, c))
	 			{
	 				 c=count_int;
	 				 return true;
	 			}
	 			if(failedCount<c)
	 	   			failedCount=c;
	 		}
	 		else if(cp.get(c).equals("]"))
	 		{
	 			
	 			 c=count_int;
	 			 return true;
	 		}
	 		if(failedCount<c)
	    			failedCount=c;
	 	}
	 	else if(body1.classbody(cp, c) && isMainFunctionDefined)
	 	{
	 		count_int=c=body1.count;
	 		//System.out.println("gg:"+cp.get(c));
	 		if(cp.get(c).equals("]"))
	 		{
	 			
	 				 c=count_int;
	 				 return true;
	 		}
	 		if(failedCount<c)
	    			failedCount=c;
	 	}
	 	else if(cp.get(c).equals("]"))
	 	{
	 		
	 		 c=count_int;
	 		 return true;
	 	}
	 	if(failedCount<c && body1.failedCount<c)
	 			failedCount=c;
	 	else if(failedCount<c && body1.failedCount>c)
	 		failedCount=body1.failedCount;
	 	//System.out.println(c+","+failedCount+","+body1.failedCount);
	 	return false;
	 }

	 }

//MainClassBodyModel End
	 
//mainFuncModel Start
	 class mainFuncModel
	 {
	 	KeyWords words = new KeyWords();
	 	public int count=0,count_int=0,failedCount=0,scope=0;
	 	 ArrayList<String> cp = new ArrayList();
	 		 ArrayList<String> vp = new ArrayList();
	 	String follow_of_Parent[];
	 	String follow_for_exp[]={"["};
	 	private	String follow_for_body[]={"]"};
	 	ExpModel exp ;
	 	declarationModel decl;
	 	private	BodyModel body;
	 	mainFuncModel(){}
	 	 public mainFuncModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp,false);
	 			decl = new declarationModel(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	 	 }
	 	 public mainFuncModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			follow_of_Parent=Follow;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp);
	 			decl = new declarationModel(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	 	 }
	 	 public boolean mainFunc(ArrayList<String> cp,int c)
	 	 {
	 		 
	 		 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
	 		 {
	 			
	 			 c++;
	 			 
	 			 if(cp.get(c).equals("type modifier") && vp.get(c).equals("stable"))
	 			 {
	 				 c++;
	 				
	 				 if(cp.get(c).equals("void"))
	 				 {
	 					 c++;
	 					 
	 					 if(cp.get(c).equals("main"))
	 					 {
	 						 c++;
	 						 
	 						 if(cp.get(c).equals("(")) //Confirmation of params - (
	 						 {
	 							 c++;
	 							 if(cp.get(c).equals(")"))
	 							 {
	 								 c++;
	 								 
	 								 if(cp.get(c).equals("["))
	 								 {
	 									 c++;
	 									 
	 									 if(body.body(cp,c))
	 									 {
	 										c=body.count;
	 									
	 										if(cp.get(c).equals("]"))
	 										{
	 											c++;
	 											count=count_int=c;
	 											return true; //Successful parsed.
	 											
	 										}
	 										if(failedCount<c)
	 								   			failedCount=c;
	 									 }
	 									 if(failedCount<c && body.failedCount<c)
	 								   			failedCount=c;
	 									   	else if(body.failedCount>c)
	 									   			failedCount=body.failedCount;
	 								 }
	 								 if(failedCount<c)
	 							   			failedCount=c;
	 							 }
	 							 if(failedCount<c)
	 						   			failedCount=c;
	 						 }
	 						 if(failedCount<c)
	 					   			failedCount=c;
	 					 }
	 					 if(failedCount<c)
	 				   			failedCount=c;
	 				 }
	 				 if(failedCount<c)
	 			   			failedCount=c;
	 			 }
	 			 if(failedCount<c)
	 		   			failedCount=c;
	 		 } 
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		 return false;
	 	 }


	 }

	 
//mainFuncModel End 
	 
	 
	 
//For Loop Start
	 class ForModel 
	 {
	  private KeyWords words = new KeyWords();
	  public int count=0,failedCount=0,scope=0;
	  private int count_int=0;
	  private ArrayList<String> cp = new ArrayList();
	  private ArrayList<String> vp = new ArrayList();
	  private String follow_of_Parent[];
	  private String follow_for_body[]={"]"};
	  private BodyModel body;
	  private tableVar tv = new tableVar();
	  private ForModel(){}
	  public ForModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	  {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	 			follow_of_Parent=null;
	   }
	   public ForModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	   {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			follow_of_Parent=Follow;	
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	   }
	   	public boolean For(ArrayList<String>cp,int c)
	   	{
	   		if(cp.get(c).equals("for loop"))
	   		{
	   			
	   			c++;
	   			
	   			if(cp.get(c).equals("("))
	   			{
	   				c++;
	   				
	   				if(X(cp,c))
	   				{
	   					c=count_int;
	   					
	   					if(Y(cp,c))
	   					{
	   						c=count_int;
	   						
	   						if(Z(cp,c))
	   						{
	   							c=count_int;
	   							//System.out.println(c+"Here:"+cp.get(c));
	   							if(cp.get(c).equals(")"))
	   							{
	   								c++;
	   								if(cp.get(c).equals("["))
	   								{
	   									c++;
	   									if(body.body(cp, c))
	   									{
	   										c=body.count;
	   										if(cp.get(c).equals("]"))
	   										{
	   											c++;
	   											count=count_int=c;
	   											return true;
	   										}
	   									}
	   								}
	   							}
	   						}
	   					}
	   				}
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean X(ArrayList<String>cp,int c)
	   	{
	   		declarationModel decl=new declarationModel(cp,vp,scope);
	   		
	   		if(decl.decl(cp, c))
	   		{
	   			
	   			c=decl.count;
	   			////System.out.println("Here"+cp.get(c-1));
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   			else if(cp.get(c-1).equals("Terminator"))
	   			{
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		else if(Assign(cp,c,"Terminator".split("\\ ")))
	   		{
	   			c=count_int;
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		else if(cp.get(c).equals("Terminator"))
	 		{
	   			c++;
	 			count_int=c;
	 			return true;
	 		}
	   		return false;
	   	}
	   	private  boolean Assign(ArrayList<String>cp,int c,String follow_for_exp_Assign[])
	   	{
	   		
	   		ExpModel expMain = new ExpModel(cp,vp,follow_for_exp_Assign,false);
	   		if(cp.get(c).equals("ID"))
	   		{
	   			c++;
	   			
	   			if(expMain.isOp(cp.get(c), c) && !cp.get(c).equals("Assignment"))
	   			{
	   				
	   				c++;
	   				if(cp.get(c).equals("Assignment"))
	   				{
	   					c++;
	   					if(expMain.exp(cp, c))
	   					{
	   						c=expMain.count;
	   						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	 	  						{
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	 	  						{
	 	  							c=c-1;
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	   							
	   					}
	   				}
	   			}
	   			else if(cp.get(c).equals("Assignment"))
	 			{
	   				
	 					c++;
	 					
	 					if(expMain.exp(cp, c))
	 					{
	 						c=expMain.count;
	 						//System.out.println(c+" Z:"+cp.get(c));
	 						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	 	  						{
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	 	  						{
	 	  							c=c-1;
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 							
	 					}
	 			}
	   			else if(cp.get(c).equals("Dot operator"))//if is object attribute;
	   			{
	   				c++;
	   				if(cp.get(c).equals("ID"))
	   		  		{
	   					c++;
	   		  			if(expMain.isOp(cp.get(c), c))
	   		  			{
	   		  				c++;
	   		  				if(cp.get(c).equals("Assignment"))
	   		  				{
	   		  					c++;
	   		  					if(expMain.exp(cp, c))
	   		  					{
	   		  						c=expMain.count;
	   		  						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   		  						{
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   		  						{
	   		  							c=c-1;
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  							
	   		  					}
	   		  				}
	   		  			}
	   		  			else if(cp.get(c).equals("Assignment"))
	   					{
	   							c++;
	   							if(expMain.exp(cp, c))
	   							{
	   								c=expMain.count;
	   								if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   		  						{
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   		  						{
	   		  							c=c-1;
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   									
	   							}
	   					}
	   		  			else if(cp.get(c).equals("["))//If Array exists
	   		  			{
	   		  				c++;
	   		  				String[] follow_for_exp_Assignment={"]"};
	   		  				ExpModel exp1 = new ExpModel(cp,vp,follow_for_exp_Assignment,false);
	   		  				if(exp1.exp(cp, c))
	   		  				{
	   		  					c=exp1.count;
	   		  					if(cp.get(c).equals("]"))
	   		  					{
	   		  						c++;
	   		  					if(exp1.isOp(cp.get(c), c))
	   		  		  			{
	   		  		  				c++;
	   		  		  				if(cp.get(c).equals("Assignment"))
	   		  		  				{
	   		  		  					c++;
	   		  		  					if(expMain.exp(cp, c))
	   		  		  					{
	   		  		  						c=expMain.count;
	   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  		  							
	   		  		  					}
	   		  		  				}
	   		  		  			}
	   		  		  			else if(cp.get(c).equals("Assignment"))
	   		  					{
	   		  							c++;
	   		  							if(expMain.exp(cp, c))
	   		  							{
	   		  								c=expMain.count;
	   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  									
	   		  							}
	   		  					}
	   		  					}
	   		  					else if(cp.get(c-1).equals("]"))
	   		  					{
	   		  						
	   		  					if(exp1.isOp(cp.get(c), c))
	   		  		  			{
	   		  		  				c++;
	   		  		  				if(cp.get(c).equals("Assignment"))
	   		  		  				{
	   		  		  					c++;
	   		  		  					if(expMain.exp(cp, c))
	   		  		  					{
	   		  		  						c=expMain.count;
	   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  		  							
	   		  		  					}
	   		  		  				}
	   		  		  			}
	   		  		  			else if(cp.get(c).equals("Assignment"))
	   		  					{
	   		  							c++;
	   		  							if(expMain.exp(cp, c))
	   		  							{
	   		  								c=expMain.count;
	   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  									
	   		  							}
	   		  					}
	   		  					}
	   		  				}
	   		  			}
	   		  		}
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean Y(ArrayList<String>cp,int c)
	   	{
	   		String [] follow_for_exp={"Terminator"};
	   		ExpModel exp = new ExpModel(cp,vp,follow_for_exp,false);
	   		
	   		if(exp.exp(cp, c))
	   		{
	   			c=exp.count;
	   			
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   			else if(cp.get(c-1).equals("Terminator"))
	   			{
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean Z(ArrayList<String>cp,int c)
	   	{
	   		
	   		
	   		
	   		if(cp.get(c).equals("Incdec"))
	   		{
	   			c++;
	   			if(cp.get(c).equals("ID"))
	   			{
	   				c++;
	   				if(cp.get(c).equals(")"))
	   				{
	   					count_int=c;
	   					return true;
	   				}
	   			}
	   		}
	   		else if(cp.get(c).equals("ID"))
	 		{
	   			
	 				c++;
	 				
	 				if(cp.get(c).equals("Incdec"))
	 		  		{
	 		  			c++;
	 		  				if(cp.get(c).equals(")"))
	 		  				{
	 		  					count_int=c;
	 		  					return true;
	 		  				}
	 		  			
	 		  		}
	 				else if(Assign(cp,c-1,")".split("\\ "))) //Passed ID's index.
	 		  		{
	 					
	 		  			c=count_int;
	 		  			if(cp.get(c).equals(")"))
	 		  			{
	 		  				
	 		  				count_int=c;
	 		  				return true;
	 		  			}
	 		  		}
	 				
	 		}
	   		return false;
	   	}
	 }
	 
//For Loop End
	 

	//ClassBodyModel Start
	class classBodyModel {
		KeyWords words = new KeyWords();
		int count=0,count_int=0,failedCount=0,scope=0;
		ArrayList<String> cp = new ArrayList();
		ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[]; //Usually ]
		String follow_for_exp[]={"Terminator"};
		ExpModel exp ;
		declarationModel decl;
		//functionCallModel functCall;
		
		classBodyModel(){}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	{
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);
		
	}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	{
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);

	}
	public boolean classbody(ArrayList<String> cp,int c)
	{
		
		 if(b_mst(cp,c))
		 {
			 c=count_int;
			 count=c;
			 
			 return true;
		 }
		 return false;
	}
	public boolean b_mst(ArrayList<String> cp,int c)
	{
		 if(cp.get(c).equals("datatype"))
		 {
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 
			 //Declaration can occur
			 if(decl.decl(cp, c))
			 {
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 	 else if(fdm.func_def(cp, c))
			 {
				 
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && fdm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && decl.failedCount<fdm.failedCount)
					failedCount=fdm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>fdm.failedCount)
					failedCount=decl.failedCount;
		 }
		 else if(cp.get(c).equals("ID"))
		 {
			 ConstructorModel cm= new ConstructorModel(cp,vp,scope);
			 //Declaration can occur
			 
			 if(decl.decl(cp, c))
			 {
				 
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cm.construct_def(cp, c))
			 {
				 c=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && decl.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>cm.failedCount)
					failedCount=decl.failedCount;
		 }
		 else if(cp.get(c).equals("acces modifier") || cp.get(c).equals("type modifier") )
		 {
			 ClassModel cm= new ClassModel(cp,vp,scope+1,true);
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				
				 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
				 {
					 int temp=c+1;
					 
					 if(cp.get(temp).equals("type modifier") && vp.get(temp).equals("stable"))
					 {
						 temp++;
						 
						 if(cp.get(temp).equals("void"))
						 {
							 temp++;
							
							 if(cp.get(temp).equals("main"))
							 { 
								 
								 count_int=c;
								
								 return true;
							 }
						 }
						 
					 }
				 }
				 else if(!cp.get(c).equals("acces modifier"))
				 {
					 count_int=c;
					 return true; 
				 }
				 
			 }
			 else if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(fdm.func_def(cp, c))
			 {
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && im.failedCount<c && fdm.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && im.failedCount<cm.failedCount  && fdm.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<im.failedCount && im.failedCount>c && im.failedCount>cm.failedCount  && im.failedCount<fdm.failedCount)
					failedCount=im.failedCount;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && fdm.failedCount>cm.failedCount  && im.failedCount>fdm.failedCount)
					failedCount=im.failedCount;
		 }
		 else if(cp.get(c).equals("class"))
		 {
			 ClassModel cm= new ClassModel(cp,vp,scope,true);
			 if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<cm.failedCount)
		   			failedCount=cm.failedCount;
			 else
				 cm.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("sculpture"))
		 {
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<im.failedCount)
		   			failedCount=im.failedCount;
			 else
				 im.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("void"))
		 {
			
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 if(fdm.func_def(cp, c))
			 {
				 
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<fdm.failedCount)
		   			failedCount=fdm.failedCount;
			 else
				 fdm.failedCount=failedCount;
		 } 
		 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
		 {
			 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
			 {
				 int temp=c+1;
				 if(cp.get(temp).equals("type modifier") && cp.get(temp).equals("stable"))
				 {
					 temp++;
					 if(cp.get(temp).equals("main"))
					 { 
						 count_int=c;
						 return true;
					 }
					 if(failedCount<temp)
				   			failedCount=temp;
				 }
			 }
			 else if(!cp.get(c).equals("acces modifier"))
			 {
				 count_int=c;
				 return true; 
			 }
			 
		 }/*
		 if(failedCount<c)
				failedCount=c;
		
		 */
		 return false;
	}

	}

	//ClassBodyModel End


	 
	 //Interface
	class InterfaceModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private interfaceBodyModel body1;
			 private InterfaceModel(){}
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp);
				this.scope=scope;
				body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
				follow_of_Parent=null;
		 }
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp);
				//body=new BodyModel(cp,vp,follow_for_body);
				body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
				
		 }
		
		 public boolean interfaceFunction(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   	
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }//if stable not present.
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }	
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
		
			 }//if stable+access modifier not present.
			 if(cp.get(c).equals("sculpture"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 
								 if(body1.InternBody(cp,c))
								 {
									c=body1.count;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }

			
			 return false;

		 }
		 private boolean S1_List(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean S1(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("interface"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
			
					 
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 if(cp.get(c).equals("inheritance"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 
								 c++;
								 if(cp.get(c).equals("["))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 count_int=c;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
	}
	
//Interfaces  End
	
//Interface Body Semantic Start
	class interfaceBodyModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
		 private tableFunction tf = new tableFunction();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private interfaceBodyModel(){}
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=null;
		 }
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;			
		 }
		 public boolean InternBody(ArrayList<String> cp,int c)
		 {
			 if(multiprototypes(cp,c))
			 {
				count=c=count_int;
				
				
				return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean multiprototypes(ArrayList<String>cp,int c)
		 {
			 if(prototypeFun(cp,c))
			 {
				c=count_int;
				if(multiprototypes(cp,c))
				 {
					c=count_int;
					return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean prototypeFun(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
				
					 c++;
					 
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 c++;
						 
						 if(params(cp, c))
						 {
							 
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								count_int=c;
								return true;
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 return false;
		 }
		 private  ArrayList<String> params = new ArrayList();	
		 private ArrayList<String> getParams()
		 {
			 return params;
		 }
		 private void SetParams(String param)
		 {
			 params.add(param);
		 }
		 private tableVar tv = new tableVar();
		 private boolean lookUpVar(String name)
	 	 {
			 if(tv.varDT.size()>0)
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
	 		 return false;
	 	 }
		 private boolean params(ArrayList<String> cp,int c)
		 {
			 if(fields(cp,c))
			 {
				 c=count_int;
				 
				 return true;
			 }
			 else if(Object(cp, c))
			 {
				 c=count_int;
					count_int=c;
					return true;
			 }
			 else if(cp.get(c).equals(")"))
			 {
				 count_int=++c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(cp.get(count).equals("datatype"))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							count++;
							
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								//System.out.println(count+":rr:"+cp.get(count));
								return true;
							}
							/*else
							{
								
								count--;
								//System.out.println(count+":rr:"+cp.get(count));
								if(Object(classPart, count))
								 {
									 count=count_int;
										count_int=count;
										
										return true;
								 }
							}*/
							if(failedCount<count)
					   			failedCount=count;
						}
					 if(failedCount<count)
				   			failedCount=count;
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
						count_int=count;
						
						return true;
				 }
				 if(failedCount<count)
			   			failedCount=count;
				 
			 }
			 else if(classPart.get(count).equals(")"))
			 {
				 count_int=count;
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			   
			 return false;
		 }
		 private boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					
						 if(Ob(cp, c))
						 {
							c=count_int;
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
								 
							 }
							 else
							 {
								 
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals(")"))
								 {
									 
									 count_int=c=c+1;
									 
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
			 return false;
		 }
		 private boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 
				 if(cp.get(c).equals(","))
				 {
					 c++;
					
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 
						if(cp.get(c).equals("ID"))
						{
							
							c++;
						
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals(")"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
								 else
									 if(cp.get(c-1).equals(")"))
									 {
										 
										 count_int=c;
										 return true;
									 }
										
							
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 
							
						}
						if(failedCount<c)
				   			failedCount=c;
					 }
					 else if(cp.get(c).equals("datatype"))
					 {
					
						 if(fields(cp, c))
						 {
							 c=count_int;
								count_int=c;
								return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else  if(cp.get(c).equals(")"))
				 {
					 c++;
					 count_int=c;
					 
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
				 return false;
		 }

		 private boolean fields(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					
					c++;
					
					 if(list(cp,c))
					{
						c=count_int;
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								
								count_int=c;
								
								return true;
							}
							if(failedCount<c)
					   			failedCount=c;
						}
						else if(cp.get(c).equals(")"))
						{
							c++;
							
							count_int=c;

							return true;
						}
						else if(cp.get(c-1).equals(")"))
						{

							count_int=c;

							return true;
						}
						if(failedCount<c)
				   			failedCount=c;
					}
					 if(failedCount<c)
				   			failedCount=c;
				}
				if(failedCount<c)
		   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 
			 return false;
		 }
	}
//Interface Body  End
	 
	 
	 
	 
	 
	 
	 
	 
	///DECLARATION START
	class declarationModel 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
			 ArrayList<String> varTable = new ArrayList();
		String follow_of_Parent;
		String follow_for_exp[]={"Terminator","]"};
		ExpModel exp ;


		declarationModel(){}
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			exp = new ExpModel(cp,vp,follow_for_exp);
			
	 }
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp);
			
	 }
	 private boolean field(ArrayList<String> classPart,int count)
	 {
		 if(classPart.get(count).equals("datatype"))
		 {
			 count++;
			
			if(classPart.get(count).equals("ID"))
			{
				
				count++;
				
				if(init(classPart,count))
				{
					count=count_int;
					
					if(list(classPart,count))
					{
						
						count=count_int;
						
						//System.out.println(count+"e"+classPart.get(count));
						if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
							  
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						this.count=count;
						count_int=count;
						exp.count_int=0;
						
						return true;
					}
				}
				else if(list(classPart,count))
				{
					count=count_int;
					if(count>=classPart.size())
					{
						if(classPart.get(count-1).equals("Terminator"))
						{
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
					if(classPart.get(count).equals("Terminator"))
					{
						
						count++;
						
						count_int=count;
						exp.count_int=0;
						//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
						return true;
					}
				}
				else if(classPart.get(count).equals("Terminator"))
				{
					count++;
					//this.count=count;
					count_int=count;
					exp.count_int=0;
					return true;
				}
			}
		 }
		 return false;
	 }
	 public boolean decl(ArrayList<String> classPart,int count)
	 {
		
		 
		 if(field(classPart,count))
		 {
			 	count=count_int;
			 	this.count=count;
				count_int=0;
				exp.count_int=0;
			
	
				return true;
		 }
		 else if(Object(classPart, count))
		 {
			 count=count_int;
			 this.count=count;
			
				count_int=0;
				exp.count_int=0;
				
				return true;
		 }

		 return false;
	 }
	 boolean init(ArrayList<String> classPart,int count)
	 {
		 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
		    if(classPart.get(count).equals("Assignment"))
			{
		    	
				count++;
				
				if(exp.exp(classPart, count))
				{
					//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			}
			else if(array(classPart,count))
			{
				count=count_int;
				count_int=count;
				return true;
			}
			else if(classPart.get(count).equals(","))
			{
				count++;
				count_int=count;
				return true;
			}
			
				return false;
	 }
	 boolean list(ArrayList<String> classPart,int count)
	 {
		 
		 //System.out.println(count+"e"+classPart.get(count));
		 if(classPart.get(count).equals(","))
		 {
			 count++;
			 if(classPart.get(count).equals("ID"))
				{
					count++;
					if(init(classPart,count))
					{
						count=count_int;
						if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
						
					}
					else if(list(classPart,count))
					{
						count=count_int;
						count_int=count;
						return true;
					}
				}
		 }
		 else if(classPart.get(count).equals("Terminator"))
		 {
			// count++;
			 count_int=count;
				return true;
		 }
		 return false;
	 }
	 
	 //Other models
	 boolean array(ArrayList<String> classPart,int count)
	 {
		 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
		 if(classPart.get(count).equals("Assign Op"))
		 {
			count++;
			if(exp.exp(classPart, count))
			{
				count_int=exp.count;
				count=count_int;
				count_int=count;
				return true;
			}
		 }
		 else if(classPart.get(count).equals("Assignment"))
		 {
			 count++;
			 if(classPart.get(count).equals("["))
			 {
				 count++;
				 if(words.isConst(classPart.get(count)))
				 {
					 count++;
					 if(clist1(classPart,count))
					 {
						 count=count_int;
						 return true;
					 }
				 }
				 else if(classPart.get(count).equals("]"))
				 {
					 count=count_int;
						count_int=count;
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 boolean clist1(ArrayList<String> cp,int c)
	 {
	 	if(c>=cp.size()){ c=cp.size()-1; return true;}
	 	 if(cp.get(c).equals(","))			//Call to clist1
	 	 {
	 		 c++;
	 		 if(words.isConst(cp.get(c)))
	 		 {
	 			 c++;
	 			 if(clist1(cp,c))
	 			 {
	 				 c=count_int;
					 count_int=c;
					 return true;
				 
	 			 }
	 			 
	 		 }
	 	 }
	 	 else if(cp.get(c).equals("]"))
	 	 {
	 		c++;
			 count_int=c;
			 return true;
	 	 }
	 	return false;
	 } 
	 boolean Object(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		
		 if(cp.get(c).equals("ID"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 
				 c++;
				 
				 if(obj1(cp,c))
				 {
					 c=count_int;
					 
					 if(Ob(cp, c))
					 {

						 if(c>=cp.size())
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
						 }
						 else
						 {
							
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								 c++;
							
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
			 }
		 }
		 return false;
	 }
	 boolean obj1(ArrayList<String> cp, int c)
	 {
		 if(cp.get(c).equals("Assignment"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 if(exp.exp(cp, c))
				 {
					 c=exp.count;
					 count_int=c;
					return true;
				 }
			 }
			
		 }
		 else if(cp.get(c).equals("["))
		 {
			 
			 c++;
			 //System.out.println("GG"+cp.get(c));
			 if(words.isConst(cp.get(c)))
			 {
				 c++;
				 if(cp.get(c).equals("]"))
				 {
					 c++;
					 //Here init_ob
					 
					if(init_ob(cp,c))
					{
						c=count_int;
						 return true;
					}
				 }
			 }
			 else if(exp.exp(cp, c))
			 {
				 
				 c=exp.count;
				
				 count_int=c;
				 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
				 else
				 {
					 
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
				 }
				
			 }
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 
			 c++;
			 count_int=c;
			 
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean init_ob(ArrayList<String> cp,int c)
	 {
		if(c>=cp.size()) return true; 
		 if(cp.get(c).equals("Assignment"))
		 {
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 
				 if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 count_int=c;
					return true;
				 }
		/*		 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						 c=exp.count;
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 c++;
						 if(words.isConst(cp.get(c)))
						 {
							 c++;
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
		*/		 
			 }
			 
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean Ob(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 else
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID")) //obj1
				 {
					 c++;
					 if(obj1(cp,c))
					 {
						 c=count_int;
						 if(Ob(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
							 else  if(cp.get(c-1).equals("Terminator"))
							 {
								
								 
								 count_int=c;
								 return true;
							 }
						 }
					 }
						
				 }
			 }
			 else  if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				
				 
				 count_int=c;
				 return true;
			 }

		 }
			 return false;
	 }
	}
	//DECLARATION COMPLETED

	

	//EXP START

	//EXP START

	class ExpModel 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,FailedCounter=0;
		 String follow_of_Parent[];
		 ArrayList<String> cp = new ArrayList();
		 ArrayList<String> vp = new ArrayList();
		 boolean ifParams=false;
		 public boolean AllowAssignation=true;
		ExpModel(){}
		public ExpModel(ArrayList cp,ArrayList vp)
		{
			this.cp=cp;
			this.vp=vp;
		}
		public ExpModel(ArrayList cp,ArrayList vp,String follow[])
		{
			this.cp=cp;
			this.vp=vp;
			
			follow_of_Parent=follow;
		}
		public ExpModel(ArrayList cp,ArrayList vp,boolean allow)
		{
			this.cp=cp;
			this.vp=vp;
			AllowAssignation=allow;
		}
		public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow)
		{
			this.cp=cp;
			this.vp=vp;
			
			follow_of_Parent=follow;
			AllowAssignation=allow;
		}
		
	public 	boolean exp(ArrayList<String> ClassPart,int count)
		{
			if(isUniOp(ClassPart.get(count)))
			{
				count++;
				if(exp1(ClassPart,count))
				{
					count=count_int;
					this.count=count;
					return true;
				}
			}
			else if(exp1(ClassPart,count))
			{
				
				count=count_int;
				count_int=0;
				this.count=count;
				return true;
			}
			else if(words.isParentFollow(cp.get(count), follow_of_Parent))
			{
				count=count_int;
				this.count=count;
				return true;
			}
			if(FailedCounter<count)
				FailedCounter=count;
			return false;
		}

	boolean exp1(ArrayList<String> cp,int c)
	{
			if(cp.size()<=c){count_int=cp.size()-1;return true; } 
			if(cp.get(c).equals("ID"))
			{
				
				c++;
				
				if(exp21(cp,c))
				{
					c=count_int;
					return true;
				}
			}
			else if(words.isConst(cp.get(c)))
			{
				
				c++;
				
				if(exp2(cp,c))
				{
					int cc=c;
					c=count_int;
					return true;
				}
			}
			else if(cp.get(c).equals("("))
			{
				c++;
				if(news(cp,c))
				{
				
					c=count_int;
					return true;
				}
			}
			if(FailedCounter<c)
			FailedCounter=c;
			return false;
	}
	boolean exp2(ArrayList<String>  cp,int c)
	{
		
			if(cp.size()<=c){count_int=cp.size()-1; return true; } 
			else
			{
				if(isOp(cp.get(c),c))
				{
					c++;
					if(exp1(cp,c))
					{
						c=count_int;
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
				}
				else if(cp.get(c).equals(")"))
				{
					count_int=c;
					return true;			
				}
				else if(words.isParentFollow(cp.get(c),follow_of_Parent))
				{

					count_int=c;
					return true;
							
				}
				else if(cp.get(c).equals(",") && !ifParams)
				{
					count_int=c;
					return true;
				}
				else if(cp.get(c).equals("]"))
				{
					count_int=c;
					return true;
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean exp21(ArrayList<String>  cp,int c)
		{
			if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
			
			if(exp2(cp,c))
			{
				c=count_int;
				return true;
			}
			else if(cp.get(c).equals("("))
			{
				if(params(cp,c))
				{
					c=count_int; 
					
					if(c<cp.size())
					{
						
						if(cp.get(c).equals(")"))
						{
							c++;
						
							if(cp.get(c).equals("Dot operator"))
							{
								c++;
								if(cp.get(c).equals("("))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										return true;
									}
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(exp2(cp,c))
							{
								c=count_int; 	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c-1).equals(")"))
						{

							if(cp.get(c).equals("Dot operator"))
							{
								c++;
								if(cp.get(c).equals("("))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(exp2(cp,c))
							{
								c=count_int; 	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(words.isParentFollow(cp.get(c),follow_of_Parent))
						{
							

							count_int=c;
							
							return true;
									
						}
					}
					else if(cp.get(c-1).equals(")"))
				    {
						c=count_int;
						
						return true;
					}
				
				}
			}
			else if(cp.get(c).equals("["))
			{
				
				c++;
				
				if(words.isConst(cp.get(c)))
				{
					
					c++;
					
					if(cp.get(c).equals("]"))
					{
						c++;
						if(exp2(cp,c))
						{
							
							c=count_int;
							
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;	
					}
					if(FailedCounter<c)
						FailedCounter=c;
				}
				else if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					if(exp1(cp,c))
					{
						
						c=count_int;
				
							//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
						if(cp.get(c).equals("]"))
						{
							
							c++;
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c-1).equals("]"))
						{
							
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					if(FailedCounter<c)
						FailedCounter=c;
				
				}
				if(FailedCounter<c)
					FailedCounter=c;
			}
			
			else if(O1(cp,c))
			{
				
				c=count_int;
				if(exp2(cp,c))
				{
					
					c=count_int;
					count_int=c;
				
					if(c>=cp.size()){c=count_int=cp.size()-1;}
					return true;
				}
				else if(cp.get(c).equals("Dot operator"))
				{
					if(exp21(cp,c))
					{
						c=count_int;
						count_int=c;

						if(c>=cp.size()){c=count_int=cp.size()-1;}
						return true;
					}
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean params(ArrayList<String>  cp,int c)
		{
		
			if(cp.size()<=c){count_int=cp.size()-1;return true; }
			ifParams=true;

			if(cp.get(c).equals(")"))
			{
				c++;
				count_int=c;
				
				ifParams=false;
				return true;
			}
			else if(exp1(cp,c))
			{
				
				c=count_int;
				//System.out.println(cp.get(c)+":exp1 of params"+c);
				if(c<cp.size())
				{
					
					if(cp.get(c).equals(")"))
					{
						c++;
						count_int=c;
						//System.out.println(cp.get(c)+":dF"+c);
						ifParams=false;
						return true;
					}
					else if(cp.get(c).equals("]"))
					{
						c++;
						count_int=c;
						//System.out.println("___"+cp.get(c));
						ifParams=false;
						return true;
					}
					else if(cp.get(c).equals(","))
					{
						
						c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 c++;
								 if(params(cp,c))
								 {
										
										c=count_int;
										ifParams=false;
										return true;
							     }
							 }
						 }
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
				
					c=count_int;
					ifParams=false;
					return true;
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean news(ArrayList<String>  cp,int c)
		{
			
		if(isUniOp(cp.get(c)))
		{
			c++;
			if(exp1(cp,c))
			{
				
				c=count_int; 
				
				if(c>=cp.size())
				{
					if(cp.get(c-1).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c=count_int;
						
						return true;
					}
				}
				else
				if(cp.get(c).equals(")"))
				{
					if(ifParams){count_int=c;return true;}
					c++;
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
					//c++;
					if(ifParams){count_int=c;return true;}
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
			}
		
		}
		else if(exp1(cp,c))
			{
				
				c=count_int; 
				//System.out.println(c+":33:"+cp.get(c));
				if(c>=cp.size())
				{
					if(cp.get(c-1).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c=count_int;
						
						return true;
					}
					else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
					{
					   c=count_int;	
						return true;
					}
				}
				else
				if(cp.get(c).equals(")"))
				{
					if(ifParams){count_int=c;return true;}
					c++;
					
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
					//c++;
					if(ifParams){count_int=c;return true;}
					if(exp2(cp,c))
					{
						
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
					else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					{
						//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
					   c=count_int;	
						return true;
					}
				}
				else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				{
					//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
				   c=count_int;	
					return true;
				}
			
			}
			if(cp.get(c).equals(")"))
			{
				if(ifParams){count_int=c;return true;}
				c++;
				
				if(exp2(cp,c))
				{
					c=count_int; //count_int=0; //count_int=0;
					//count_int=0;
					
					return true;
				}
			}
			else if(cp.get(c).equals("datatype"))
			{
				
				c++;
				if(cp.get(c).equals(")"))
				{
					
					c++;
					if(exp1(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						return true;
					}
				}
			}
			else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			{
				//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
			   c=count_int;	
				return true;
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean O1(ArrayList<String>  cp,int c) // Incomplete
		{
			if(cp.get(c).equals("Dot operator"))
			{
				
				c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					if(cp.get(c).equals("("))
					{
						
						if(params(cp,c))
						{
							
							c=count_int; //count_int=0; 
							//System.out.println(c+":ttt:"+cp.get(c));
							if(cp.get(c).equals(")"))
							{
								c++;
								if(cp.get(c).equals("Terminator"))
								{
									c++;
									count_int=c;
									
									return true;
								}
								else
								{
									count_int=c;
									
									return true;
								}
							}
							else if(cp.get(c).equals("Terminator"))
							{
								//System.out.println(count_int+"He"+cp.get(count_int));
								//c++;
							c=count_int;
							////System.out.println("444,"+c+","+cp.get(c));
								return true;
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"d3dHe"+cp.get(count_int));
							   c=count_int;	
							
								return true;
							}
							else if(cp.get(c-1).equals(")"))
							{
								
								if(cp.get(c).equals("Terminator"))
								{
									c++;
									count_int=c;
									
									return true;
								}
								else
								{
									count_int=c;
									
									return true;
								}
							}
						}
						
					}
					else  if(cp.get(c).equals("["))
					{
						
						c++;
						//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
						if(words.isConst(cp.get(c)))
						{
							c++;
							
							if(cp.get(c).equals("]"))
							{
								c++;
								count_int=c;
								//System.out.println("333,"+c+","+cp.get(c));
								return true;
							}
						}
					}
					else if(cp.get(c).equals("&"))
					{
						c++;
						count_int=c;
						return true;
					}
					else if(cp.get(c).equals("Dot operator"))
					{
						if(O1(cp,c))
						{
							c=count_int;
							count_int=c;
							//System.out.println(c+":fff:"+cp.get(c));
							return true;
						}
					}
				}
				else if(words.isConst(cp.get(c)))
				{
					c++;
					count_int=c;
					return true;
				}
			}
			else if(cp.get(c).equals("&"))
			{
				c++;
				count_int=c;
				return true;
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	String valueOp(int index)
		{
			
			return vp.get(index);
		}
	boolean isOp(String cp,int c)
		{
			
			if(cp.equals("RO"))
			{
				 return true;
			}
			else if(cp.equals("Assignment") && AllowAssignation)
			{
				return true;
			}
			else if(cp.equals("BWOP"))
			{
				if(valueOp(c).equals("bitOr"))
				 return true;
			}
			else if(cp.equals("BWOP"))
			{
				if(valueOp(c).equals("bitAnd"))
				 return true;
			}
			else if(cp.equals("OR"))
			{
				 return true;
			}
			else if(cp.equals("AddSum"))
			{
				 return true;
			}
			else if(cp.equals("Mux"))
			{
				
				 return true;
			}
			else if(cp.equals("DIVMOD"))
			{
				 return true;
			}
			else if(cp.equals("Incdec") || cp.equals("Not operator"))
			{
				 return true;
			}
			else if(cp.equals("LO"))
			{
				 return true;
			}

			
			
			return false;
		}
		
	boolean isUniOp(String a)
		{
			if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
			{
				return true;
			}
			return false;
		}
	}

	
	//While Loop Start
	class WhileModel 
	{
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private	BodyModel body;
			 private WhileModel(){}
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				body=new BodyModel(cp,vp,follow_for_body,scope);
				follow_of_Parent=null;
		 }
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				
		 }
		 public boolean test(ArrayList<String>cp,int c)
		 {
				
				if(cp.get(c).equals("while loop"))
				 {
					 c++;
					 if(cp.get(c).equals("("))
					 {
						 
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 //System.out.println("FF"+c);
							 if(cp.get(c).equals(")"))
							{
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //call for body
									  
									   if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		
									   		c++;
									   		if(c>cp.size()-1){c=count_int=c;
								   			count=count_int;
								   			return true;}
									   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   	}
									   	if(failedCount<c)
								   			failedCount=c;
									   }
									   if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
									  
								 }
								 if(failedCount<c)
							   			failedCount=c;
							}
							 //if(c.prev is equal to )
							 if(cp.get(c-1).equals(")"))
								{
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 //call for body
										  if(body.body(cp,c))
										   {
										   	c=body.count;
										   	if(cp.get(c).equals("]"))
										   	{
										   		c++;
										   		if(c>cp.size()-1){c=count_int=c;
									   			count=count_int;
									   			return true;}
										   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		else
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   	}
										   	if(failedCount<c)
									   			failedCount=c;
										   }
										  if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				if(failedCount<c)
		   			failedCount=c;
				 return false;
				 
		 }
		
	}
	//While Loop End
	
	//IF-ELSE Start
	class IfElseModel {
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={")","["};
		String follow_for_body[]={"]"};
		ExpModel exp ;
		
		IfElseModel(){}
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				this.scope=scope;
				follow_of_Parent=null;
		 }
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				
		 }
		public  boolean ifElse(ArrayList<String> cp,int c)
		 {
			
			
			
			if(cp.get(c).equals("si"))
			 {
				
				 c++;
				 if(cp.get(c).equals("("))
				 {
					 
					 if(exp.exp(cp, c))
					 {
						
						 c=exp.count;
						 
						 if(cp.get(c).equals(")"))
						{
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 //call for body
								  if(!cp.get(c).equals("]"))
								  {
									  BodyModel body;
									  body=new BodyModel(cp,vp,follow_for_body,scope);
									  if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		c++;
									   		if(oelse(cp,c))
									   		{
									   			c=count_int;
									   			count=count_int;
									   		
									   			return true;
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		
									   		
									   	}
									   	if(failedCount<c)
									   	{
									   		failedCount=c;
									   	}
									   }
									  if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c && failedCount<body.failedCount)
									   			failedCount=body.failedCount;
								  }
								  else if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								  if(failedCount<c )
							   			failedCount=c;
							
							 }
							 if(failedCount<c )
						   			failedCount=c;
						}
						 //if(c.prev is equal to )
						 if(cp.get(c-1).equals(")"))
							{
							 if(cp.get(c).equals("["))
							 {
								 c++;
							  if(!cp.get(c).equals("]"))
							  {
								  BodyModel body;
								  body=new BodyModel(cp,vp,follow_for_body,scope);
								  //System.out.println("Here idiot: "+cp.get(c));
								  if(body.body(cp,c))
								   {
								   	c=body.count;
								   	
								   	if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								   	if(failedCount<c)
								   		failedCount=c;
								   }
								  if(failedCount<c && body.failedCount<c)
							   			failedCount=c;
								   	else if(body.failedCount>c && failedCount<body.failedCount)
								   			failedCount=body.failedCount;
							  }
							  else if(cp.get(c).equals("]"))
							   	{
							   		c++;
							   		if(oelse(cp,c))
							   		{
							   			c=count_int;
							   			count=count_int;
							   			
							   			return true;
							   		}
							   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   		else
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   	}
							  if(failedCount<c )
						   			failedCount=c;
							   
							 }
							 	if(failedCount<c)
						   			failedCount=c;
							   
							}
							if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c && exp.FailedCounter<c)
				   			failedCount=c;
					   	else if(exp.FailedCounter>c && failedCount<exp.FailedCounter)
					   			failedCount=exp.FailedCounter;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				  
			 }
			
			 return false;
			 
		 }
		 boolean oelse(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("sino"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("["))
				 {
					 c++;
					 
					if(!cp.get(c).equals("]"))
					{
						 //call for body
						
						 BodyModel body=new BodyModel(cp,vp,follow_for_body,scope);
						 
						  if(body.body(cp,c))
						   {
						   	c=body.count;
						   	
						   	//System.out.println("ddd:"+cp.get(c));
						   	if(cp.get(c).equals("]"))
						   	{
						   		c++;
						   		if(c>=cp.size()){count_int=c;return true;}
						   		
						   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
						   		{
						   			count_int=c;	
						   			return true;
						   		}
						   		else
						   		{
						   			count_int=c;
						   			return true;
						   		}
						   	}
						   	if(failedCount<c && body.failedCount<c)
					   			failedCount=c;
						   	else if(body.failedCount>c && body.failedCount>failedCount)
						   			failedCount=body.failedCount;
						   	}
						  if(failedCount<c)
					   			failedCount=c;
						   	
					}
					else  	if(cp.get(c).equals("]"))
				   	{
				   		c++;
				   		if(c>=cp.size()){count_int=c;return true;}
				   		
				   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
				   		{
				   			count_int=c;	
				   			return true;
				   		}
				   		else
				   		{
				   			count_int=c;
				   			return true;
				   		}
				   			}
					   }
				 if(failedCount<c)
			   			failedCount=c;
				 }
			
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
			 
		 }
	}

	//IF-ELSE END
	
	//Function Def Start
	class FunctionDefModel 
	{

		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={"["};
		private	String follow_for_body[]={"]"};
		ExpModel exp ;
		declarationModel decl;
		private	BodyModel body;
		FunctionDefModel(){}
		 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope);
		 }
		 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope);
		 }
		 public boolean func_def(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 //System.out.println(""+cp.get(c));
					 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										 if(body.body(cp,c))
										 {
											c=count_int;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
										 if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
									 }
								 }
							 }
						 }
					 }
				 }//if stable not present.
				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 c++;
							 
							 if(params(cp, c))
							 {
								 
								 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //System.out.println(c+"HerEFF:"+cp.get(c));
									 if(body.body(cp,c))
									 {
										
										c=body.count;
									
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
									 if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
								 }
							 }
						 }
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 
				 c++;
				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 c++;
							 
							 if(params(cp, c))
							 {
								 
								 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //System.out.println(c+"HerEFF:"+cp.get(c));
									 if(body.body(cp,c))
									 {
										
										c=body.count;
									
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
									 if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
								 }
							 }
						 }
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
				  
			 }//if stable+access modifier not present.
			 else  if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 c++;
						 
						 if(params(cp, c))
						 {
							 
							 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 //System.out.println(c+"HerEFF:"+cp.get(c));
								 if(body.body(cp,c))
								 {
									
									c=body.count;
								
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
								 }
								 if(failedCount<c && body.failedCount<c)
							   			failedCount=c;
								   	else if(body.failedCount>c)
								   			failedCount=body.failedCount;
							 }
						 }
					 }
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }


			 return false;
		 }
		 boolean fields(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					 if(list(cp,c))
					{
						c=count_int;
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								
								count_int=c;
								
								return true;
							}
						}
						else if(cp.get(c).equals(")"))
						{
							c++;
							
							count_int=c;

							return true;
						}
						else if(cp.get(c-1).equals(")"))
						{

							count_int=c;

							return true;
						}
					}
					
				}
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 
			 return false;
		 }
		 boolean params(ArrayList<String> cp,int c)
		 {
			 if(fields(cp,c))
			 {
				 c=count_int;
				 
				 return true;
			 }
			 else if(Object(cp, c))
			 {
				 c=count_int;
					count_int=c;
					return true;
			 }
			 else if(cp.get(c).equals(")"))
			 {
				 count_int=++c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(cp.get(count).equals("datatype"))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							count++;
							
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								//System.out.println(count+":rr:"+cp.get(count));
								return true;
							}
							/*else
							{
								
								count--;
								//System.out.println(count+":rr:"+cp.get(count));
								if(Object(classPart, count))
								 {
									 count=count_int;
										count_int=count;
										
										return true;
								 }
							}*/
						}
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
						count_int=count;
						
						return true;
				 }
				 
			 }
			 else if(classPart.get(count).equals(")"))
			 {
				 count_int=count;
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			   
			 return false;
		 }
		 boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					
						 if(Ob(cp, c))
						 {
							c=count_int;
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 
							 }
							 else
							 {
								 
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals(")"))
								 {
									 
									 count_int=c=c+1;
									 
									 return true;
								 }
							 }
						 }
					 
				 }
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
			 return false;
		 }
		 boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 
				 if(cp.get(c).equals(","))
				 {
					 c++;
					
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 
						if(cp.get(c).equals("ID"))
						{
							c++;
						
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals(")"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
								 else
									 if(cp.get(c-1).equals(")"))
									 {
										 
										 count_int=c;
										 return true;
									 }
										
							
								 
							 }
						 
							
						}
					 }
					 else if(cp.get(c).equals("datatype"))
					 {
					
						 if(fields(cp, c))
						 {
							 c=count_int;
								count_int=c;
								return true;
						 }
					 }
				 }
				 else  if(cp.get(c).equals(")"))
				 {
					 c++;
					 count_int=c;
					 
					 return true;
				 }
			
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
				 return false;
		 }


		}

	//Function Def End
	//Simple Body  Start
		class BodyModel {
			KeyWords words = new KeyWords();
			int count=0,count_int=0,scope=0;
			public int failedCount=0;
			public boolean isSomethingOpen=false;
			ArrayList<String> cp = new ArrayList();
			ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[]; //Usually ]
			String follow_for_exp[]={"Terminator"};
			ExpModel exp ;
			declarationModel decl;
			functionCallModel functCall;
			BodyModel(){}
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp);
				decl = new declarationModel(cp,vp,scope);
				functCall= new functionCallModel(cp,vp,scope);
		 }
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp);
				decl = new declarationModel(cp,vp,scope);
				functCall= new functionCallModel(cp,vp,scope);
		 }
		 public boolean getIsSomeThingOpen()
		 {
			 return isSomethingOpen; 
		 }
		 public void setIsSomeThingOpen(boolean tf)
		 {
			 isSomethingOpen=tf; 
		 }
		 public boolean body(ArrayList<String> cp,int c)
		 {

			 if(m_mst(cp,c))
			 {
				 c=count_int;
				 count=c;
				 //System.out.println(c+"body Passed: "+cp.get(c));
				 return true;
			 }
			 return false;
		 }
		 boolean m_mst(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 //Declaration can occur
				int temp=c;
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
				//	 System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				// System.out.println("Declaration Failed at Line number: "+temp);
				 if(failedCount<c && c< decl.failedCount)
			   			failedCount=decl.failedCount;
				 else if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("ID"))
			 {
				 //Declaration can occur
				int temp=c;
				int check=0;
				
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					// System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				else if(functCall.functionCall(cp,c))
				 {
					 c=functCall.count;
					// System.out.println("Function Calling Passed at Line number: "+c);
					if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 //System.out.println("Expression Passed at Line number: "+temp);
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					 {
						
						 count_int=c;
						 return true;
					 }
					 else 
					 {
						 
						 if(m_mst(cp,c))
						 {
							 
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 
					check++; 
				 }
				 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					 
					 count_int=c;
					 return true;
				 }
				 if(exp.FailedCounter>decl.failedCount && exp.FailedCounter> functCall.failedCount && exp.FailedCounter>c && exp.FailedCounter> failedCount)
				 {
					 failedCount=exp.FailedCounter;
					// System.out.println("Invalid expression: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter<decl.failedCount && decl.failedCount> functCall.failedCount && c<decl.failedCount && failedCount< decl.failedCount)
				 {
					 failedCount=decl.failedCount;
					 //System.out.println("Invalid Declaration at line number: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter< functCall.failedCount && decl.failedCount< functCall.failedCount && c<functCall.failedCount && failedCount< functCall.failedCount)
				 {
					 failedCount= functCall.failedCount;
					// System.out.println("Invalid FunctionCalling at line number: "+(failedCount+1));
				 }
				 else if(failedCount<c)
				 {
					 failedCount=c;
				 }
					 
			 }
			 else if(cp.get(c).equals("si")) //if-else
			 {
				int temp=c;
				 IfElseModel ifelse;
				 ifelse = new IfElseModel(cp,vp,scope);
				 int check=0;
				 if(ifelse.ifElse(cp,c))
				 {
					 c=ifelse.count;
					 check=1;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
					
				 }
				
				 if(failedCount<c && c> ifelse.failedCount)
			   			failedCount=c;
				 else if(failedCount<ifelse.failedCount && c< ifelse.failedCount)
				 {
					 failedCount=ifelse.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("check")) //switch
			 {
				 int temp=c;
				 boolean check=false;
				 SwitchModel switch1 =new SwitchModel(cp, vp,scope);
				 if(switch1.Switch(cp,c))
				 {
					 c=switch1.count;
					// System.out.println("Switch Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
				 }
			
				 if(failedCount<c && c> switch1.failedCount)
			   			failedCount=c;
				 else if(failedCount<switch1.failedCount && c< switch1.failedCount)
				 {
						failedCount=switch1.failedCount;
						 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("for loop")) //for Loop
			 {
				 int temp=c;
				 boolean check=false;
				 ForModel to = new ForModel(cp,vp,scope);
				 if(to.For(cp,c))
				 {
					 c=to.count;
					// System.out.println("For Loop Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> to.failedCount)
			   			failedCount=c;
				 else if(failedCount<to.failedCount && c< to.failedCount)
				 {
					 failedCount=to.failedCount;
					 isSomethingOpen=true;
			   	}
			 }
			 else if(cp.get(c).equals("while loop")) //while Loop
			 {
				 boolean check=false;
				 int temp=c;
				 WhileModel w=new WhileModel(cp,vp,scope);
				 if(w.test(cp,c))
				 {
					 c=w.count;
					// System.out.println("While Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> w.failedCount)
			   			failedCount=c;
				 else if(failedCount<w.failedCount && c< w.failedCount)
				 {
					 failedCount=w.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("break")) //break
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 //System.out.println("Break Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("continue")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Continue Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("return")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Return Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						// System.out.println("Expression Passed at Line number: "+temp);
						 check=true;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 else if(failedCount<c)
							 failedCount=c;
					 }
				 }
				 if(!check)
				 {
					// System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(exp.exp(cp, c) && !cp.get(c).equals("Terminator") ) //Exp
			 {
				 
				 boolean check=false;
				 c=exp.count;
				 
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 //System.out.println("Expression Passed after at Line number: "+c);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 else if( cp.get(c-1).equals("Terminator") )
				 {
					// System.out.println("Expression Passed after at Line number: "+(c-1));
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					 System.out.println("Terminator missing at Line number: "+c);
					 if(failedCount<c)
						 failedCount=c;
				 }
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 
				 count_int=c;
				 return true;
			 } 
			
			 if(failedCount<c)
				 failedCount=c;
			 if(exp.FailedCounter>c)
			 {
				// System.out.println("invalid exp at Line number: "+exp.FailedCounter);
				 failedCount=exp.FailedCounter;
			 }
			 else
			 {
				// System.out.println(failedCount+"Missing at Line number: "+c);
				 if(failedCount<c)
					 failedCount=c;
			 }
			 
			 return false;
		 }
		 
		}
	//Simple Body  End


		//SwitchModel  Start
			class SwitchModel {
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={")"};
				String follow_for_body[]={"]"};
				ExpModel exp ;
				BodyModel body;
				
				SwitchModel(){}
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						exp = new ExpModel(cp,vp,follow_for_exp,false);
						body=new BodyModel(cp,vp,follow_for_body,scope);
						follow_of_Parent=null;
				 }
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						body=new BodyModel(cp,vp,follow_for_body,scope);
						exp = new ExpModel(cp,vp,follow_for_exp,false);
						
				 }
				 public boolean Switch(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("check"))
					 {
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								
								if(cp.get(c).equals(")"))
								{
									c++;
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										if(test(cp,c))
										{
											c=count_int;
											count=count_int;
											return true;
										}
										 else if(cp.get(c).equals("]"))
										 {
										   		c++;
										   		count_int=c;
											   count=count_int;
												return true;
						   	
										}
										if(failedCount<c)
								   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								else if(cp.get(c-1).equals(")"))
								{
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(test(cp,c))
											{
												c=count_int;
												count=count_int;
												return true;
											}
											 else if(cp.get(c).equals("]"))
											 {
											   		c++;
											   		count_int=c;
												   count=count_int;
													return true;
							   	
											}
										 if(failedCount<c)
									   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								if(failedCount<c)
						   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 boolean test(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("test"))
					 {
						 c++;
						 String[] temp={":"};
						 ExpModel exp1=new ExpModel(this.cp,vp,temp,false);
					
						 if(exp1.exp(cp, c))
						 {
							 c=exp1.count;
							
							 if(cp.get(c).equals(":"))
							 {
								 
								 c++;
								//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
								   if(failedCount<c)
							   			failedCount=c;
							 }
							 else  if(cp.get(c-1).equals(":"))
							 {
				//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
							 }
							 if(failedCount<c)
						   			failedCount=c;
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 
			}

			//SwitchModel  End
		//Constructor Start
		class ConstructorModel {


			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[];
			String follow_for_exp[]={"["};
			private	String follow_for_body[]={"]"};
			ExpModel exp ;
			declarationModel decl;
			private	BodyModel body;
			ConstructorModel(){}
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope);
			 }
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					exp = new ExpModel(cp,vp,follow_for_exp);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope);
			 }
			 public boolean construct_def(ArrayList<String> cp,int c)
			 {
				 
				 if(cp.get(c).equals("acces modifier"))
				 {
					
					 c++;
					 
					 if(cp.get(c).equals("type modifier"))
					 {
						 c++;
						
							 if(cp.get(c).equals("ID"))
							 {
								 c++;
								 if(cp.get(c).equals("(")) //Confirmation of params - (
								 {
									 
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 
											 if(body.body(cp,c))
											 {
												c=count_int;
												if(cp.get(c).equals("]"))
												{
													c++;
													count=count_int=c;
													return true; //Successful parsed.
													
												}
											 }
										 }
									 }
								 }
							 }
						 
					 }//if stable not present.
					 else if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body.body(cp,c))
										 {
											 c=body.count;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
									 }
								 }
							 }
						 }
					 
				 } // if access modifier not present
				 else if(cp.get(c).equals("type modifier"))
				 {
					 c++;

						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body.body(cp,c))
										 {
											 c=body.count;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
									 }
								 }
							 }
						 }
					 
				 }//if stable+access modifier not present.

				 else if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body.body(cp,c))
									 {
										
										c=body.count;
										 //System.out.println(c+":tt:"+cp.get(c));
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
								 }
							 }
						 }
					 }
				 


				 return false;
			 }

		}
		//Constructor  End
	
	//Function Call	
		 class functionCallModel {

				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={"Terminator"};
				ExpModelFunction exp ;
				
				functionCallModel(){}
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						
					
				 }
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						
						
				 }
		
				 public boolean functionCall(ArrayList<String> cp,int c)
				 {
					 
					
					 if(cp.get(c).equals("ID"))
					 {
						
							
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							
							 if(params(cp,c))
							 {
								 
								 c=count_int;
								 
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count=count_int=c;
									 
									 return true; 
								 }
								 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								 {
									// c++;
									 count=count_int=c;
									
									 return true; 
								 }
							 }
						 }
					 }
					 
					 return false;
				 }
				 boolean params(ArrayList<String> cp,int c)
				 {
					 //Pass the params in exp and test to see if it exits or not?
					 
					 exp = new ExpModelFunction(cp,vp,follow_for_exp,false,scope);
					 if(exp.exp(cp, c))
					 {
							
						 c=exp.count;
						
						 if(c<cp.size())
						 {
							 //System.out.println("Exp chal gai function mey paframs:"+cp.get(c));
							if(c<cp.size()-2)
							{
								  if(cp.get(c).equals(","))
									{
										
										c++;
										 if(params(cp, c))
										 {
											 c=count_int;
												count=count_int;
												return true;
											 
											 
										 }
									}
								 else if(cp.get(c).equals(")"))
								 {
									
										count_int=++c;
									
										return true;
									 
								 }
								  
							}
							 else if(cp.get(c).equals(")"))
							 {
								
									c=count_int=c;
								
									return true;
								 
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								   
									count_int=c;
								
									return true;
								 
							 }
							 
						
						 }
					 }
		
					 return false;
				 }


//EXP  Function  START

				 class ExpModelFunction 
					{
						KeyWords words = new KeyWords();
						public int count=0,count_int=0,FailedCounter=0,scope=0;
						 String follow_of_Parent[];
						 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
					
						 boolean ifParams=false;
					
						 public boolean AllowAssignation=true;
						ExpModelFunction(){}
						public ExpModelFunction(ArrayList<String> cp,ArrayList<String> vp,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
						
							follow_of_Parent=follow;
							
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							AllowAssignation=allow;
							this.scope=scope;
						
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							follow_of_Parent=follow;
							AllowAssignation=allow;
						
						}
						
					public 	boolean exp(ArrayList<String> ClassPart,int count)
						{
						
							if(isUniOp(ClassPart.get(count)))
							{
								count++;
								if(exp1(ClassPart,count))
								{
									count=count_int;
									this.count=count;
									return true;
								}
							}
							else if(exp1(ClassPart,count))
							{
								
								count=count_int;
								count_int=0;
								this.count=count;
								return true;
							}
							else if(words.isParentFollow(cp.get(count), follow_of_Parent))
							{
								count=count_int;
								this.count=count;
								return true;
							}
							if(FailedCounter<count)
								FailedCounter=count;
							return false;
						}

					boolean exp1(ArrayList<String> cp,int c)
					{
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
						
							if(cp.get(c).equals("ID"))
							{
								
								if(vp.get(c+1).equals("("))
								{
									String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
									functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
									fcm.follow_for_exp[0]=")";
									
									if(fcm.functionCall(cp, c))
									{	
										c=fcm.count-1;
									}
								}//Not function
							
								c++;
								
								if(exp21(cp,c))
								{
									c=count_int;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								c++;
								
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
							}
							else if(cp.get(c).equals("("))
							{
								c++;
								if(news(cp,c))
								{
								
									c=count_int;
									return true;
								}
							}
							if(FailedCounter<c)
							FailedCounter=c;
							return false;
					}
	
					boolean exp2(ArrayList<String>  cp,int c)
					{
						
							if(cp.size()<=c){count_int=cp.size()-1; return true; } 
							else
							{
								if(isOp(cp.get(c),c))
								{
									//..........
									c++;
									if(exp1(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals(")"))
								{
									count_int=c;
									return true;			
								}
								else if(words.isParentFollow(cp.get(c),follow_of_Parent))
								{

									count_int=c;
									return true;
											
								}
								else if(cp.get(c).equals(",") && !ifParams)
								{
									count_int=c;
									
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									count_int=c;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean exp21(ArrayList<String>  cp,int c)
						{
							if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
							
							if(exp2(cp,c))
							{
								c=count_int;
								
								return true;
							}
							else if(cp.get(c).equals("("))
							{
								
								if(params(cp,c))
								{
									
									c=count_int; 
									
									if(c<cp.size())
									{
										
										if(cp.get(c).equals(")"))
										{
											c++;
										
											if(cp.get(c).equals("Dot operator"))
											{
												c++;
												if(cp.get(c).equals("("))
												{
													if(exp21(cp,c))
													{
														c=count_int;
														return true;
													}
												}
												if(FailedCounter<c)
													FailedCounter=c;
											}
											else if(exp2(cp,c))
											{
												c=count_int; 	
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals(")"))
										{

											if(cp.get(c).equals("Dot operator"))
											{
												c++;
												if(cp.get(c).equals("("))
												{
													if(exp21(cp,c))
													{
														c=count_int;
														return true;
													}
													if(FailedCounter<c)
														FailedCounter=c;
												}
												if(FailedCounter<c)
													FailedCounter=c;
											}
											else if(exp2(cp,c))
											{
												c=count_int; 	
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(words.isParentFollow(cp.get(c),follow_of_Parent))
										{
											

											count_int=c;
											
											return true;
													
										}
									}
									else if(cp.get(c-1).equals(")"))
								    {
										c=count_int;
										
										return true;
									}
								
								}
							}
							else if(cp.get(c).equals("["))
							{
								
								c++;
								
								if(words.isConst(cp.get(c)))
								{
									
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;	
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals("ID"))
								{

								
									c++;
									
									if(exp1(cp,c))
									{
										
										c=count_int;
								
											//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
										if(cp.get(c).equals("]"))
										{
											
											c++;
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals("]"))
										{
											
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							
							else if(O1(cp,c))
							{
								
								
								c=count_int;
								if(exp2(cp,c))
								{
									
									c=count_int;
									count_int=c;
								
									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										count_int=c;

										if(c>=cp.size()){c=count_int=cp.size()-1;}
										return true;
									}
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean params(ArrayList<String>  cp,int c)
						{
						
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
							ifParams=true;

							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								
								ifParams=false;
								return true;
							}
							else if(exp1(cp,c))
							{
								
								c=count_int;
								//System.out.println(cp.get(c)+":exp1 of params"+c);
								if(c<cp.size())
								{
									
									if(cp.get(c).equals(")"))
									{
										c++;
										count_int=c;
										//System.out.println(cp.get(c)+":dF"+c);
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("___"+cp.get(c));
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals(",")) //what if datatype?
									{
										
										c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(cp.get(c).equals("ID"))
											 {
												 c++;
												 if(params(cp,c))
												 {
														
														c=count_int;
														ifParams=false;
														return true;
											     }
											 }
										 }
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
								
									c=count_int;
									ifParams=false;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					String valueOp(int index)
					{
						
						return vp.get(index);
					}
			
				boolean isOp(String cp,int c)
					{
						
						if(cp.equals("RO"))//
						{
							 return true;
						}
						else if(cp.equals("Assignment") && AllowAssignation)
						{
							return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitOr"))
							 return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitAnd"))
							 return true;
						}
						else if(cp.equals("OR"))
						{
							 return true;
						}
						else if(cp.equals("AddSum"))
						{
							 return true;
						}
						else if(cp.equals("Mux"))
						{
							
							 return true;
						}
						else if(cp.equals("DIVMOD"))
						{
							 return true;
						}
						else if(cp.equals("Incdec") || cp.equals("Not operator"))
						{
							 return true;
						}
						else if(cp.equals("LO"))
						{
							 return true;
						}

						
						
						return false;
					}
					
				boolean isUniOp(String a)
					{
						if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
						{
							return true;
						}
						return false;
					}
					boolean news(ArrayList<String>  cp,int c)
						{
							
						if(isUniOp(cp.get(c)))
						{
							c++;
							if(exp1(cp,c))
							{
								
								c=count_int; 
								
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
							}
						
						}
						else if(exp1(cp,c))
							{
								
								c=count_int; 
								//System.out.println(c+":33:"+cp.get(c));
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
									{
									   c=count_int;	
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									{
										//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
									   c=count_int;	
										return true;
									}
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							
							}
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c).equals("datatype"))
							{
								
								c++;
								if(cp.get(c).equals(")"))
								{
									
									c++;
									if(exp1(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										return true;
									}
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean O1(ArrayList<String>  cp,int c) // Incomplete
						{
							if(cp.get(c).equals("Dot operator"))
							{
								
								c++;
								
								if(cp.get(c).equals("ID"))
								{
									
								
									if(exp1(cp,c))
									{
										
										c=count_int;
										
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
									/*if(cp.get(c).equals("("))
									{
										
										if(params(cp,c))
										{
											
											c=count_int; //count_int=0; 
											//System.out.println(c+":ttt:"+cp.get(c));
											if(cp.get(c).equals(")"))
											{
												c++;
												if(cp.get(c).equals("Terminator"))
												{
													c++;
													count_int=c;
													
													return true;
												}
												else
												{
													count_int=c;
													
													return true;
												}
											}
											else if(cp.get(c).equals("Terminator"))
											{
												//System.out.println(count_int+"He"+cp.get(count_int));
												//c++;
											c=count_int;
											////System.out.println("444,"+c+","+cp.get(c));
												return true;
											}
											else if(words.isParentFollow(cp.get(c), follow_of_Parent))
											{
												//System.out.println(count_int+"d3dHe"+cp.get(count_int));
											   c=count_int;	
											
												return true;
											}
											else if(cp.get(c-1).equals(")"))
											{
												
												if(cp.get(c).equals("Terminator"))
												{
													c++;
													count_int=c;
													
													return true;
												}
												else
												{
													count_int=c;
													
													return true;
												}
											}
										}
										
									}
									else*/  if(cp.get(c).equals("["))
									{
										
										c++;
										//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
										if(words.isConst(cp.get(c)))
										{
											c++;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count_int=c;
												//System.out.println("333,"+c+","+cp.get(c));
												return true;
											}
										}
									}
									else if(cp.get(c).equals("&"))
									{
										c++;
										count_int=c;
										return true;
									}
									else if(cp.get(c).equals("Dot operator"))
									{
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
								}
								else if(words.isConst(cp.get(c)))
								{
									c++;
									count_int=c;
									return true;
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							else if(cp.get(c).equals(")"))
							{
								//c++;
								count_int=c;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
			
					}

//isArrayType

//EXP  Function END

				 
				 
				 
			}

	//FunctionCallModel  End
	 
	 
}

//Main Class End

//........................... Semantic of Main Class ......................................................................//
//MainClass Semantic End
class MainClassModel_Semantic
{

	private KeyWords words = new KeyWords();
public	int count=0,count_int=0,failedCount,scope=0;
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private	String follow_for_body[]={"]"};
		 private	ExpModel exp ;
		 private	BodyModel body;
		 private mainClassBodyModel body1;
		 public boolean isMainDefined=false;
		 tableVar tv = new tableVar();
		  tableInterface tI = new tableInterface();
		  tableClass innerClassTable = new tableClass();
		  tableFunction tFunction = new tableFunction();
		String CurrentPname;
		  String CurrentName;
		  int CurrentScope;
		  boolean CurrentisPrivate;
		  boolean CurrentisStable;
		  boolean isInner=false;
		 private MainClassModel_Semantic(){}
	 public MainClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			body1=new mainClassBodyModel(cp,vp,follow_for_body,scope);
			follow_of_Parent=null;
	 }
	 public MainClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			body1=new mainClassBodyModel(cp,vp,follow_for_body,scope);
			
	 }
	 public boolean lookUpClass(String name)
	 {
		 for (int i = 0; i < Syntaxtification.ct.Cname.size(); i++) {
		if(Syntaxtification.ct.Cname.get(i).equals(name) && Syntaxtification.ct.classScope.get(i)<=scope )
		{
			
			return true;
		}
		}
		 return false;
	 }
	 public boolean lookUpInterface(String name)
	 {
		 if(Syntaxtification.interfacet.nameInterfaces.size()>0)
		 {
			 for (int i = 0; i < Syntaxtification.interfacet.nameInterfaces.size(); i++) {
					if(Syntaxtification.interfacet.nameInterfaces.get(i).equals(name))
						return true;
					}
		 }
		 else if(Syntaxtification.ct.Interfacesname.size()>0)
		 {
			 for (int i = 0; i < Syntaxtification.ct.Interfacesname.get(ClassIndex).nameInterfaces.size(); i++) {
					if(Syntaxtification.ct.Interfacesname.get(ClassIndex).nameInterfaces.get(i).equals(name))
						return true;
					}
			 
		 }
		 return false;
	 }
	 public boolean lookUpInnerInterface(String name) //Issue
	 {
		 for (int i = 0; i < tI.nameInterfaces.size(); i++) {
		if(tI.nameInterfaces.get(i).equals(name))
			return true;
		}
		 return false;
	 }
	 public boolean lookUpFunction(String name) //Issue
	 {
		 for (int i = 0; i < tFunction.nameFunction.size(); i++) {
		if(tFunction.nameFunction.get(i).equals(name))
			return true;
		}
		 return false;
	 }
	 public int ClassIndex=0;
	 public boolean mainClassFunction(ArrayList<String> cp,int c)
	 {
		 if(vp.get(c).equals("local"))
		 {
			
			 c++;
			 
			 if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 //////////System.out.println(""+cp.get(c));
				 if(cp.get(c).equals("class"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {

						 if(!lookUpClass(vp.get(c)) )
						 {
							 if( !isInner)
							 {
								 Syntaxtification.ct.isPrivate.add(false);
								 Syntaxtification.ct.isStable.add(true);
								 Syntaxtification.ct.Cname.add(vp.get(c));
								 Syntaxtification.ct.classScope.add(scope); 
							 }
							 else  if( isInner)
							 {
								 CurrentName=vp.get(c);
								 CurrentisPrivate=false;
								 CurrentisStable=true;
								 CurrentScope=scope;
							 }
							 ClassIndex=Syntaxtification.ct.Cname.size()-1;
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at char number:"+(c+1));
						 }
						 
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.mainClassBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											isMainDefined=body1.isMainFunctionDefined;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }//if stable not present.
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)) )
					 {
						 if( !isInner)
						 {
							 Syntaxtification.ct.isPrivate.add(false);
							 Syntaxtification.ct.isStable.add(false);
							 Syntaxtification.ct.Cname.add(vp.get(c));
							 Syntaxtification.ct.classScope.add(scope); 
						 }
						 else  if( isInner)
						 {
							 CurrentName=vp.get(c);
							 CurrentisPrivate=false;
							 CurrentisStable=false;
							 CurrentScope=scope;
						 }
						 ClassIndex=Syntaxtification.ct.Cname.size()-1;
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at char number:"+(c+1));
					 }
					 
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.mainClassBody(cp,c))
								 {
									 c=body1.count;
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										isMainDefined=body1.isMainFunctionDefined;
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 if(failedCount<c)
				failedCount=c;
		 return false;

	 }
	 private boolean S1_List(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals(","))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
					 System.out.println("No interface found Error");
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean S1(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("interface"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
					 System.out.println("No interface found Error");
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 if(cp.get(c).equals("inheritance"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(lookUpClass(vp.get(c)))
							 {
								 Syntaxtification.ct.Pname.add(vp.get(c));
								 CurrentPname=vp.get(c);
								 
							 }
							 else
							 {
								 System.out.println("No Class found Error");
							 }
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(lookUpClass(vp.get(c)))
				 {
					 Syntaxtification.ct.Pname.add(vp.get(c));
					 CurrentPname=vp.get(c);
					 
				 }
				 else
				 {
					 System.out.println("No Class found Error");
				 }
				 c++;
				 if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 Syntaxtification.ct.Pname.add("-");
			 CurrentPname="-";
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }

	
//MainClassBodyModel Start

	 class mainClassBodyModel 
	 {
	 	KeyWords words = new KeyWords();
	 	public int count=0,count_int=0,failedCount=0,scope=0;
	 	ArrayList<String> cp = new ArrayList();
	 	ArrayList<String> vp = new ArrayList();
	 	String follow_of_Parent[]; //Usually ]
	 	String follow_for_body_After[]={"]"};
	 	String follow_for_body_Before[]={"]","acces modifier"};
	 	boolean isMainFunctionDefined=false;
	 	
	 	mainClassBodyModel(){}
	  public mainClassBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	  {
	 		this.cp = cp;
	 		this.vp = vp;
	 		this.scope=scope;
	  }
	  public mainClassBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	  {
	 		this.cp = cp;
	 		this.vp = vp;
	 		follow_of_Parent=Follow;
	 		this.scope=scope;
	  }
	 public boolean mainClassBody(ArrayList<String>cp,int c)
	 {
	 	if(mb_mst(cp,c))
	 	 {
	 		 c=count_int;
	 		 count=c;
	 		/*Syntaxtification.ct.innerClassList.add(innerClassTable);
			 Syntaxtification.ct.functionslist.add(tFunction);*/
	 		 //System.out.println(c+":mainclassbody Passed: "+cp.get(c));
	 		 return true;
	 	 }
	 	if(failedCount<c)
	 			failedCount=c;
	 	 return false;
	 }
	 public boolean mb_mst(ArrayList<String>cp,int c)
	 {
	 	classBodyModel body1;
	 	if(isMainFunctionDefined)
	 	{
	 		body1=new classBodyModel(cp,vp,follow_for_body_After,scope);
	 	}
	 	else
	 		body1=new classBodyModel(cp,vp,follow_for_body_Before,scope);
	 	
	 	if(body1.classbody(cp, c) && !isMainFunctionDefined)
	 	{
	 		//System.out.println(c+"gff:"+cp.get(c));
	 		count_int=c=body1.count;
	 		//System.out.println(c+"gg:"+cp.get(c));
	 		mainFuncModel mfm = new mainFuncModel(cp,vp,scope);
	 		if(mfm.mainFunc(cp,c))
	 		{
	 			c=count_int=mfm.count;
	 			isMainFunctionDefined=true;
	 			if(mb_mst(cp, c))
	 			{
	 				 c=count_int;
	 				 return true;
	 			}
	 			if(failedCount<c)
	 	   			failedCount=c;
	 		}
	 		else if(cp.get(c).equals("]"))
	 		{
	 			
	 			 c=count_int;
	 			 return true;
	 		}
	 		if(failedCount<c)
	    			failedCount=c;
	 	}
	 	else if(body1.classbody(cp, c) && isMainFunctionDefined)
	 	{
	 		count_int=c=body1.count;
	 		//System.out.println("gg:"+cp.get(c));
	 		if(cp.get(c).equals("]"))
	 		{
	 			
	 				 c=count_int;
	 				 return true;
	 		}
	 		if(failedCount<c)
	    			failedCount=c;
	 	}
	 	else if(cp.get(c).equals("]"))
	 	{
	 		
	 		 c=count_int;
	 		 return true;
	 	}
	 	if(failedCount<c && body1.failedCount<c)
	 			failedCount=c;
	 	else if(failedCount<c && body1.failedCount>c)
	 		failedCount=body1.failedCount;
	 	//System.out.println(c+","+failedCount+","+body1.failedCount);
	 	return false;
	 }

	 }

//MainClassBodyModel End

		//ClassBodyModel Start
		class classBodyModel {
			KeyWords words = new KeyWords();
			int count=0,count_int=0,failedCount=0,scope=0;
			ArrayList<String> cp = new ArrayList();
			ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[]; //Usually ]
			String follow_for_exp[]={"Terminator"};
			ExpModel exp ;
			declarationModel decl;
			//functionCallModel functCall;
			
			classBodyModel(){}
		public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		{
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				decl = new declarationModel(cp, vp, scope);
				//functCall= new functionCallModel(cp,vp);
			
		}
		public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		{
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				decl = new declarationModel(cp, vp, scope);
				//functCall= new functionCallModel(cp,vp);

		}
		public boolean classbody(ArrayList<String> cp,int c)
		{
			 
			 if(b_mst(cp,c))
			 {
				 c=count_int;
				 count=c;
				 Syntaxtification.ct.innerClassList.add(innerClassTable);
				 Syntaxtification.ct.functionslist.add(tFunction);
				
				 return true;
			 }
			 return false;
		}
		public boolean b_mst(ArrayList<String> cp,int c)
		{
			 if(cp.get(c).equals("datatype"))
			 {
				FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
				 
				 //Declaration can occur
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 	 else if(fdm.func_def(cp, c))
				 {
					 
					 c=count_int=fdm.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c && fdm.failedCount<c && decl.failedCount<c)
						failedCount=c;
				 else if(failedCount<fdm.failedCount && fdm.failedCount>c && decl.failedCount<fdm.failedCount)
						failedCount=fdm.failedCount;
				 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>fdm.failedCount)
						failedCount=decl.failedCount;
			 }
			 else if(cp.get(c).equals("ID"))
			 {
				 ConstructorModel cm= new ConstructorModel(cp,vp,scope);
				 //Declaration can occur
				
				 if(decl.decl(cp, c))
				 {
					 
					 c=decl.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cm.construct_def(cp, c))
				 {
					 c=cm.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c && cm.failedCount<c && decl.failedCount<c)
						failedCount=c;
				 else if(failedCount<cm.failedCount && cm.failedCount>c && decl.failedCount<cm.failedCount)
						failedCount=cm.failedCount;
				 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>cm.failedCount)
						failedCount=decl.failedCount;
			 }
		 else if(cp.get(c).equals("acces modifier") || cp.get(c).equals("type modifier") )
			 {
				 ClassModel_Semantic cm= new ClassModel_Semantic(cp,vp,scope+1,true);
				 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
				 InterfaceModel im=new InterfaceModel(cp,vp,scope);
				 if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					
					 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
					 {
						 int temp=c+1;
						 
						 if(cp.get(temp).equals("type modifier") && vp.get(temp).equals("stable"))
						 {
							 temp++;
							 
							 if(cp.get(temp).equals("void"))
							 {
								 temp++;
								
								 if(cp.get(temp).equals("main"))
								 { 
									 
									 count_int=c;
									
									 return true;
								 }
							 }
							 
						 }
					 }
					 else if(!cp.get(c).equals("acces modifier"))
					 {
						 count_int=c;
						 return true; 
					 }
					 
				 } 
				 else if(cm.classFunction(cp, c))
				 {
					 c=count_int=cm.count;
					 
				
					 innerClassTable.Cname.add(cm.CurrentName);
					 innerClassTable.classScope.add(cm.CurrentScope);
					 innerClassTable.isStable.add(cm.CurrentisStable);
					 innerClassTable.isPrivate.add(cm.CurrentisPrivate);
					 innerClassTable.Cvariable.add(cm.tv);
					 innerClassTable.Cvariable.add(cm.tv);
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(fdm.func_def(cp, c))
				 {
					 c=count_int=fdm.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(im.interfaceFunction(cp, c))
				 {
					 c=count_int=im.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c && cm.failedCount<c && im.failedCount<c && fdm.failedCount<c)
						failedCount=c;
				 else if(failedCount<cm.failedCount && cm.failedCount>c && im.failedCount<cm.failedCount  && fdm.failedCount<cm.failedCount)
						failedCount=cm.failedCount;
				 else if(failedCount<im.failedCount && im.failedCount>c && im.failedCount>cm.failedCount  && im.failedCount<fdm.failedCount)
						failedCount=im.failedCount;
				 else if(failedCount<fdm.failedCount && fdm.failedCount>c && fdm.failedCount>cm.failedCount  && im.failedCount>fdm.failedCount)
						failedCount=im.failedCount;
			 }
			 else if(cp.get(c).equals("class"))
			 {
				 ClassModel_Semantic cm= new ClassModel_Semantic(cp,vp,scope+1,true);
				 int temp=c+1;
				 if(cm.classFunction(cp, c))
				 {
					 c=count_int=cm.count;
					 innerClassTable.Cname.add(cm.CurrentName);
					 innerClassTable.classScope.add(cm.CurrentScope);
					 innerClassTable.isStable.add(cm.CurrentisStable);
					 innerClassTable.isPrivate.add(cm.CurrentisPrivate);
					 innerClassTable.Cvariable.add(cm.tv);
					 
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<cm.failedCount)
			   			failedCount=cm.failedCount;
				 else
					 cm.failedCount=failedCount;
			 }
			 else if(cp.get(c).equals("sculpture"))
			 {
				 InterfaceModel im=new InterfaceModel(cp,vp,scope);
				 if(im.interfaceFunction(cp, c))
				 {
					 c=count_int=im.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<im.failedCount)
			   			failedCount=im.failedCount;
				 else
					 im.failedCount=failedCount;
			 }
			 else if(cp.get(c).equals("void"))
			 {
				
				 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
				 if(fdm.func_def(cp, c))
				 {
					 c=count_int=fdm.count;
					 if(b_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<fdm.failedCount)
			   			failedCount=fdm.failedCount;
				 else
					 fdm.failedCount=failedCount;
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
				 {
					 int temp=c+1;
					 if(cp.get(temp).equals("type modifier") && cp.get(temp).equals("stable"))
					 {
						 temp++;
						 if(cp.get(temp).equals("main"))
						 { 
							 count_int=c;
							 return true;
						 }
						 if(failedCount<temp)
					   			failedCount=temp;
					 }
				 }
				 else if(!cp.get(c).equals("acces modifier"))
				 {
					 count_int=c;
					 return true; 
				 }
				 
			 }
			 if(failedCount<c)
					failedCount=c;
			
			 
			 return false;
		}

		}

		//ClassBodyModel End
	 
//mainFuncModel Start
	 class mainFuncModel
	 {
	 	KeyWords words = new KeyWords();
	 	public int count=0,count_int=0,failedCount=0,scope=0;
	 	 ArrayList<String> cp = new ArrayList();
	 		 ArrayList<String> vp = new ArrayList();
	 	String follow_of_Parent[];
	 	String follow_for_exp[]={"["};
	 	private	String follow_for_body[]={"]"};
	 	ExpModel exp ;
	 	declarationModelSemantic decl;
	 	private	BodyModel body;
	 	tableVar tv= new tableVar();
	 	mainFuncModel(){}
	 	 public mainFuncModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
	 			decl = new declarationModelSemantic(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
	 	 }
	 	 public mainFuncModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			follow_of_Parent=Follow;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
	 			decl = new declarationModelSemantic(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
	 	 }
	 	 public boolean mainFunc(ArrayList<String> cp,int c)
	 	 {
	 		 
	 		 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
	 		 {
	 			
	 			 c++;
	 			 
	 			 if(cp.get(c).equals("type modifier") && vp.get(c).equals("stable"))
	 			 {
	 				 c++;
	 				
	 				 if(cp.get(c).equals("void"))
	 				 {
	 					 c++;
	 					 
	 					 if(cp.get(c).equals("main"))
	 					 {
	 						 tFunction.isPrivate.add(false);
							 tFunction.isStable.add(true);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add(vp.get(c-1));
							 tFunction.paramList.add(null);
	 						 c++;
	 						 
	 						 if(cp.get(c).equals("(")) //Confirmation of params - (
	 						 {
	 							 c++;
	 							 if(cp.get(c).equals(")"))
	 							 {
	 								 c++;
	 								 
	 								 if(cp.get(c).equals("["))
	 								 {
	 									 c++;
	 									 
	 									 if(body.body(cp,c))
	 									 {
	 										c=body.count;
	 										tFunction.variablelist.add(body.tv);
	 										if(cp.get(c).equals("]"))
	 										{
	 											c++;
	 											count=count_int=c;
	 											return true; //Successful parsed.
	 											
	 										}
	 										if(failedCount<c)
	 								   			failedCount=c;
	 									 }
	 									 if(failedCount<c && body.failedCount<c)
	 								   			failedCount=c;
	 									   	else if(body.failedCount>c)
	 									   			failedCount=body.failedCount;
	 								 }
	 								 if(failedCount<c)
	 							   			failedCount=c;
	 							 }
	 							 if(failedCount<c)
	 						   			failedCount=c;
	 						 }
	 						 if(failedCount<c)
	 					   			failedCount=c;
	 					 }
	 					 if(failedCount<c)
	 				   			failedCount=c;
	 				 }
	 				 if(failedCount<c)
	 			   			failedCount=c;
	 			 }
	 			 if(failedCount<c)
	 		   			failedCount=c;
	 		 } 
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		 return false;
	 	 }


	 }

	 
//mainFuncModel End 
	 
		

		//IF-ELSE Semantic Start
		class IfElseModel {
			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount,scope=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[];
			String follow_for_exp[]={")","["};
			String follow_for_body[]={"]"};
			ExpModel exp ;
			tableVar tv= new tableVar();
			IfElseModel(){}
			 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					this.scope=scope;
					follow_of_Parent=null;
			 }
			 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					
			 }
			public  boolean ifElse(ArrayList<String> cp,int c)
			 {
				
				
				
				if(cp.get(c).equals("si"))
				 {
					 c++;
					 if(cp.get(c).equals("("))
					 {
						 c++;
						 if(exp.exp(cp, c))
						 {
							//.............Semantic to be done..................
							if(exp.returnType!="boolean") { System.out.println("Boolean expected at char number: "+c); return false;}
							 //...............................
							 c=exp.count;
							
							 if(cp.get(c).equals(")"))
							{
								 c++;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //call for body
									  if(!cp.get(c).equals("]"))
									  {
										  BodyModel body;
										  body=new BodyModel(cp,vp,follow_for_body,scope,tv);
										  if(body.body(cp,c))
										   {
										   	c=body.count;
										   	if(cp.get(c).equals("]"))
										   	{
										   		c++;
										   		if(oelse(cp,c))
										   		{
										   			c=count_int;
										   			count=count_int;
										   		
										   			return true;
										   		}
										   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		else
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		
										   		
										   	}
										   	if(failedCount<c)
										   	{
										   		failedCount=c;
										   	}
										   }
										  if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c && failedCount<body.failedCount)
										   			failedCount=body.failedCount;
									  }
									  else if(cp.get(c).equals("]"))
									   	{
									   		c++;
									   		if(oelse(cp,c))
									   		{
									   			c=count_int;
									   			count=count_int;
									   			return true;
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   	}
									  if(failedCount<c )
								   			failedCount=c;
								
								 }
								 if(failedCount<c )
							   			failedCount=c;
							}
							 //if(c.prev is equal to )
							 if(cp.get(c-1).equals(")"))
								{
								 if(cp.get(c).equals("["))
								 {
									 c++;
								  if(!cp.get(c).equals("]"))
								  {
									  BodyModel body;
									  body=new BodyModel(cp,vp,follow_for_body,scope,tv);
									  //System.out.println("Here idiot: "+cp.get(c));
									  if(body.body(cp,c))
									   {
									   	c=body.count;
									   	
									   	if(cp.get(c).equals("]"))
									   	{
									   		c++;
									   		
									   		if(oelse(cp,c))
									   		{
									   			c=count_int;
									   			count=count_int;
									   			
									   			return true;
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   	}
									   	if(failedCount<c)
									   		failedCount=c;
									   }
									  if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c && failedCount<body.failedCount)
									   			failedCount=body.failedCount;
								  }
								  else if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								  if(failedCount<c )
							   			failedCount=c;
								   
								 }
								 	if(failedCount<c)
							   			failedCount=c;
								   
								}
								if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c && exp.FailedCounter<c)
					   			failedCount=c;
						   	else if(exp.FailedCounter>c && failedCount<exp.FailedCounter)
						   			failedCount=exp.FailedCounter;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					  
				 }
				
				 return false;
				 
			 }
			 boolean oelse(ArrayList<String> cp,int c)
			 {
				 
				 if(cp.get(c).equals("sino"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("["))
					 {
						 c++;
						 
						if(!cp.get(c).equals("]"))
						{
							 //call for body
							
							 BodyModel body=new BodyModel(cp,vp,follow_for_body,scope,tv);
							 
							  if(body.body(cp,c))
							   {
							   	c=body.count;
							   	
							   	//System.out.println("ddd:"+cp.get(c));
							   	if(cp.get(c).equals("]"))
							   	{
							   		c++;
							   		if(c>=cp.size()){count_int=c;return true;}
							   		
							   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
							   		{
							   			count_int=c;	
							   			return true;
							   		}
							   		else
							   		{
							   			count_int=c;
							   			return true;
							   		}
							   	}
							   	if(failedCount<c && body.failedCount<c)
						   			failedCount=c;
							   	else if(body.failedCount>c && body.failedCount>failedCount)
							   			failedCount=body.failedCount;
							   	}
							  if(failedCount<c)
						   			failedCount=c;
							   	
						}
						else  	if(cp.get(c).equals("]"))
					   	{
					   		c++;
					   		if(c>=cp.size()){count_int=c;return true;}
					   		
					   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
					   		{
					   			count_int=c;	
					   			return true;
					   		}
					   		else
					   		{
					   			count_int=c;
					   			return true;
					   		}
					   			}
						   }
					 if(failedCount<c)
				   			failedCount=c;
					 }
				
				 if(failedCount<c)
			   			failedCount=c;
				 return false;
				 
			 }
			 
			//EXP Semantic if  START

			 class ExpModel
				{
					KeyWords words = new KeyWords();
					public int count=0,count_int=0,FailedCounter=0,scope=0;
					 String follow_of_Parent[];
					 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				
					 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
					 String returnType="",ChangeType="";
					
					 String leftOp="",rightOp="";
					 public boolean AllowAssignation=true;
					ExpModel(){}
					public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;

					}
					public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;
						follow_of_Parent=follow;
						
					}
					public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						AllowAssignation=allow;
						this.scope=scope;
					}
					public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;
						follow_of_Parent=follow;
						AllowAssignation=allow;
						
					}
					
				public 	boolean exp(ArrayList<String> ClassPart,int count)
					{
					
						if(isUniOp(ClassPart.get(count)))
						{
						
							startUniOp=true;
							count++;
							if(exp1(ClassPart,count))
							{
								count=count_int;
								this.count=count;
							
								return true;
							}
						}
						else if(exp1(ClassPart,count))
						{
							
							count=count_int;
							count_int=0;
							this.count=count;
							
							
							return true;
						}
						else if(words.isParentFollow(cp.get(count), follow_of_Parent))
						{
							count=count_int;
							this.count=count;
							
							return true;
						}
						if(FailedCounter<count)
							FailedCounter=count;
					
						return false;
					}

				boolean exp1(ArrayList<String> cp,int c)
				{
						if(cp.size()<=c){count_int=cp.size()-1;return true; }
					
						if(cp.get(c).equals("ID"))
						{
							
							if(vp.get(c+1).equals("("))
							{
								String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
								functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
								fcm.follow_for_exp[0]=")";
								
								if(fcm.functionCall(cp, c))
								{
									
									String dt=getFuncReturnType(vp.get(c));
									String rdt=getFuncReturnType(vp.get(c));
									
									c=fcm.count-1;
									if(!opFlag) //No Left Operator Exists right now
									{
										
										 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
											{
										 		startUniOp=false;
													System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
													return false;
													
				
											}
											else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
											{
												startUniOp=false;
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
										else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
															leftOp=dt; //left operator
															opFlag=true;
															
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
										{
												System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
												return false;
												
			
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										
							
									}
									else if(opFlag) //Left Operator Exists
									{
										
										{
											if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
											{

												rightOp=rdt;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												leftOp=returnType;
												opFlag=true;
												
											}
											else //No more operators
											{

												
														
														if(!typeChanged)
														{
															
															
																rightOp=rdt;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
																 
															
														}
														else if(typeChanged)
														{
															typeChanged=false;
															String temp=rdt;
															if(!compTypeCast(temp,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															
															else
															{
																rightOp=ChangeType;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
															}
														}
														
													
												
											}
										}
										
									}
								
								}
								/*
								if(!lookupFunction(vp.get(c)))
								{
									System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								else
								{}
								*/
							}//Not function
							else //Maybe variable?
							{
								if(!lookUpVar(vp.get(c)))
								{
									System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								else
								{
									if(!opFlag) //No Left Operator Exists right now
									{
										String dt=getReturnTypeVar(vp.get(c));
										returnType=dt;
										 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
											{
											 startUniOp=false;
												if(dt.equals("dig") || dt.equals("frac"))
												{
													returnType="dig";
												}
												else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
												{
													System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
											{
												startUniOp=false;
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
										else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
															leftOp=dt; //left operator
															opFlag=true;
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
										{
											if(dt.equals("dig") || dt.equals("frac"))
											{
												returnType="dig";
											}
											else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(!vp.get(c+1).equals("."))
										{

												
												if(!typeChanged)
												{
												
														if(cp.get(c+1).equals("["))
														if(!isArrayType(vp.get(c)))
														{
															System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
															return false;
														}
												
												}
												else if(typeChanged)
												{
													typeChanged=false;
													
													if(!compTypeCast(dt,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
												
												}
													
												
											
										}
									}
									else if(opFlag) //Left Operator Exists
									{
										String rdt=getReturnTypeVar(vp.get(c));
										if(cp.get(c+1).equals("["))
										{
											if(!isArrayType(vp.get(c)))
											{
												System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
												return false;
											}
										}
										else
										{
											if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
											{
											
												
												rightOp=rdt;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												leftOp=returnType;
												opFlag=true;
												
											}
											else //No more operators
											{
														if(!typeChanged)
														{
															
															
																rightOp=rdt;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
														
														}
														else if(typeChanged)
														{
															typeChanged=false;
															String temp=rdt;
															if(!compTypeCast(temp,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															
															else
															{
																rightOp=ChangeType;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
															}
														}
														
													
											
											}
										}
										
									}
								}
								//....................................................................................................
								/*if(listcounter<paramsList.size())
								{
									if(!lookUpVar(vp.get(c)))
									{
										System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									else
									{
										if(!typeChanged)
										{
											if(!getReturnTypeVar(vp.get(c)).equals(paramsList.get(listcounter)))
											{
												System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											else
											{
												if(cp.get(c+1).equals("["))
												if(!isArrayType(vp.get(c)))
												{
													System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												listcounter++;
											}
										}
										else if(typeChanged)
										{
											typeChanged=false;
											String temp=getReturnTypeVar(vp.get(c));
											if(!compTypeCast(temp,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											else if(!ChangeType.equals(paramsList.get(listcounter)))
											{
												System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											else
												listcounter++;
										}
										
									}
								}
								else
								{
									System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								*/
							}
							c++;
							
							if(exp21(cp,c))
							{
								c=count_int;
								return true;
							}
						}
						else if(words.isConst(cp.get(c)))
						{
							
							if(!opFlag) //No Left Operator Exists right now
							{
								
								 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
									{
									 startUniOp=false;
									 
										if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
										{
											returnType="dig";
										}
										else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
									{
										
										startUniOp=false;
										
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										
											
									}
								else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
													leftOp=words.getConst(cp.get(c)); //left operator
													opFlag=true;
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
								{
									if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
									{
										returnType="dig";
									}
									else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
									{
										System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
									if(words.getConst(cp.get(c)).equals("boolean"))
									{
										returnType="boolean";
									}
									else 
									{
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else
								{

										
											if(!typeChanged)
											{
												returnType=words.getConst(cp.get(c));
											}
											else if(typeChanged)
											{
												typeChanged=false;
												String temp=words.getConst(cp.get(c));
												if(!compTypeCast(temp,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												
												else
													{
													returnType=ChangeType;
													}
											}
											
										
									
								}
							}
							else if(opFlag) //Left Operator Exists
							{
								if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
								{

									rightOp=words.getConst(cp.get(c));
									returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
									if(returnType.equals("x"))
									{
										System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									leftOp=returnType;
									opFlag=true;
									
								}
								else //No more operators
								{
		
											if(!typeChanged)
											{
													rightOp=words.getConst(cp.get(c));
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													opFlag=false;
												
													 
												
											}
											else if(typeChanged)
											{
												typeChanged=false;
												String temp=words.getConst(cp.get(c));
												if(!compTypeCast(temp,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												
												else
												{
													rightOp=ChangeType;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													opFlag=false;
													
												}
											}
											
								
								}
							}
							c++;
							
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
						}
						else if(cp.get(c).equals("("))
						{
							c++;
							if(news(cp,c))
							{
							
								c=count_int;
								return true;
							}
						}
						if(FailedCounter<c)
						FailedCounter=c;
						return false;
				}
				boolean compTypeCast(String func,String Modified)
				{
					if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
					{
						if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
							return true;
						
					}
					else if(Modified.equals("beta"))
					{
						
						if(func.equals("beta"))
							return true;
					}
					
					return false;
				}
				boolean exp2(ArrayList<String>  cp,int c)
				{
					
						if(cp.size()<=c){count_int=cp.size()-1; return true; } 
						else
						{
							if(isOp(cp.get(c),c))
							{
								//..........
								c++;
								if(exp1(cp,c))
								{
									c=count_int;
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(cp.get(c).equals(")"))
							{
								count_int=c;
								return true;			
							}
							else if(words.isParentFollow(cp.get(c),follow_of_Parent))
							{

								count_int=c;
								return true;
										
							}
							else if(cp.get(c).equals(",") && !ifParams)
							{
								count_int=c;
								
								return true;
							}
							else if(cp.get(c).equals("]"))
							{
								count_int=c;
								return true;
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean exp21(ArrayList<String>  cp,int c)
					{
						if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
						
						if(exp2(cp,c))
						{
							c=count_int;
							
							return true;
						}
					
						else if(cp.get(c).equals("["))
						{
							
							c++;
							
							if(words.isConst(cp.get(c)))
							{
								
								c++;
								
								if(cp.get(c).equals("]"))
								{
									c++;
									if(exp2(cp,c))
									{
										
										c=count_int;
										
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;	
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(cp.get(c).equals("ID"))
							{

								if(vp.get(c+1).equals("("))
								{
									if(!lookupFunction(vp.get(c)))
									{
										System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									
								}//Not function
								else //Maybe variable?
								{
									
										if(!lookUpVar(vp.get(c)))
										{
											System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
									
								}
								c++;
								
								if(exp1(cp,c))
								{
									
									c=count_int;
							
										//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
									if(cp.get(c).equals("]"))
									{
										
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									else if(cp.get(c-1).equals("]"))
									{
										
										if(exp2(cp,c))
										{
											
											c=count_int;
											return true;
										}
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						
						else if(O1(cp,c))
						{
							
							c=count_int;
							if(exp2(cp,c))
							{
								
								c=count_int;
								count_int=c;
							
								if(c>=cp.size()){c=count_int=cp.size()-1;}
								return true;
							}
							else if(cp.get(c).equals("Dot operator"))
							{
								if(exp21(cp,c))
								{
									c=count_int;
									count_int=c;

									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean params(ArrayList<String>  cp,int c)
					{
					
						if(cp.size()<=c){count_int=cp.size()-1;return true; }
						ifParams=true;

						if(cp.get(c).equals(")"))
						{
							c++;
							count_int=c;
							
							ifParams=false;
							return true;
						}
						else if(exp1(cp,c))
						{
							
							c=count_int;
							//System.out.println(cp.get(c)+":exp1 of params"+c);
							if(c<cp.size())
							{
								
								if(cp.get(c).equals(")"))
								{
									c++;
									count_int=c;
									//System.out.println(cp.get(c)+":dF"+c);
									ifParams=false;
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									c++;
									count_int=c;
									//System.out.println("___"+cp.get(c));
									ifParams=false;
									return true;
								}
								else if(cp.get(c).equals(",")) //what if datatype?
								{
									
									c++;
									 if(cp.get(c).equals("ID"))
									 {
										 c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(params(cp,c))
											 {
													
													c=count_int;
													ifParams=false;
													return true;
										     }
										 }
									 }
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
							
								c=count_int;
								ifParams=false;
								return true;
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean news(ArrayList<String>  cp,int c)
					{
						
					if(isUniOp(cp.get(c)))
					{
						c++;
						if(exp1(cp,c))
						{
							
							c=count_int; 
							
							if(c>=cp.size())
							{
								if(cp.get(c-1).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c=count_int;
									
									return true;
								}
							}
							else
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
								//c++;
								if(ifParams){count_int=c;return true;}
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
						}
					
					}
					else if(exp1(cp,c))
						{
							
							c=count_int; 
							//System.out.println(c+":33:"+cp.get(c));
							if(c>=cp.size())
							{
								if(cp.get(c-1).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c=count_int;
									
									return true;
								}
								else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
								{
								   c=count_int;	
									return true;
								}
							}
							else
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
								//c++;
								if(ifParams){count_int=c;return true;}
								if(exp2(cp,c))
								{
									
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
						
						}
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c).equals("datatype"))
						{
							ChangeType=vp.get(c);
							typeChanged=true;
							c++;
							if(cp.get(c).equals(")"))
							{
								
								c++;
								if(exp1(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									return true;
								}
							}
						}
						else if(words.isParentFollow(cp.get(c), follow_of_Parent))
						{
							//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
						   c=count_int;	
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean O1(ArrayList<String>  cp,int c) // Incomplete
					{
						if(cp.get(c).equals("Dot operator"))
						{
							
							c++;
							
							if(cp.get(c).equals("ID"))
							{
								
							
								if(exp1(cp,c))
								{
									c=count_int;
									if(O1(cp,c))
									{
										c=count_int;
										count_int=c;
										//System.out.println(c+":fff:"+cp.get(c));
										return true;
									}
								}
							 if(cp.get(c).equals("["))
								{
									
									c++;
									//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
									if(words.isConst(cp.get(c)))
									{
										c++;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count_int=c;
											//System.out.println("333,"+c+","+cp.get(c));
											return true;
										}
									}
								}
								else if(cp.get(c).equals("&"))
								{
									c++;
									count_int=c;
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(O1(cp,c))
									{
										c=count_int;
										count_int=c;
										//System.out.println(c+":fff:"+cp.get(c));
										return true;
									}
								}
								else if(cp.get(c).equals(")"))
								{
								
									c=count_int=c;
									if(ifParams)
									ifParams=false;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								c++;
								count_int=c;
								return true;
							}
						}
						else if(cp.get(c).equals("&"))
						{
							c++;
							count_int=c;
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				String OperationOnOperands(String left,String right,String op,int cpInd)
				{
					if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
					{
						if(left.equals("dig"))
						{
							if(right.equals("dig"))
								return "dig";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "dig";
							
						}
						else if(left.equals("frac"))
						{
							if(right.equals("dig"))
								return "frac";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "frac";
							
						}
						else if(left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "alpha";
							else if(right.equals("frac"))
								return "alpha";
							else if(right.equals("alpha"))
								return "alpha";
							
						}
						
					}
					else if(op.equals("suma"))
					{
						if(left.equals("dig"))
						{
							if(right.equals("dig"))
								return "dig";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "dig";
							
						}
						else if(left.equals("frac"))
						{
							if(right.equals("dig"))
								return "frac";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "frac";
							
						}
						else if(left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "alpha";
							else if(right.equals("frac"))
								return "alpha";
							else if(right.equals("alpha"))
								return "alpha";
							
						}
						else if(left.equals("beta"))
						{
							if(right.equals("dig"))
								return "beta";
							else if(right.equals("frac"))
								return "beta";
							else if(right.equals("alpha"))
								return "beta";
							
							
						}
						
					}
					else if(cp.get(cpInd).equals("RO"))
					{
						if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "boolean";
							else if(right.equals("frac"))
								return "boolean";
							else if(right.equals("alpha"))
								return "boolean";
							
						}
						
					}
					else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
					{
						return "boolean" ;
					}
					return "x";
				}
				String valueOp(int index)
					{
						
						return vp.get(index);
					}
			
				boolean isOp(String cp,int c)
					{
						
						if(cp.equals("RO"))//
						{
							 return true;
						}
						else if(cp.equals("Assignment") && AllowAssignation)
						{
							return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitOr"))
							 return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitAnd"))
							 return true;
						}
						else if(cp.equals("OR"))
						{
							 return true;
						}
						else if(cp.equals("AddSum"))
						{
							 return true;
						}
						else if(cp.equals("Mux"))
						{
							
							 return true;
						}
						else if(cp.equals("DIVMOD"))
						{
							 return true;
						}
						else if(cp.equals("Incdec") || cp.equals("Not operator"))
						{
							 return true;
						}
						else if(cp.equals("LO"))
						{
							 return true;
						}

						
						
						return false;
					}
					
				boolean isUniOp(String a)
					{
						if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
						{
							
							return true;

						}
						
						return false;
					}
				
				
				
				 //<-- Finding functionExist or Not?
				 private boolean lookupFunction(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return true;
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return true;
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
					 					return true;
					 			} 
				 		}
				 		
				 		return false;
				 }

				private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					int i= indexOfParent(Parent,nameFunction);
					 if(i!=-1) 
						 return true;
					 return false;
				 }
				 private int indexOfParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
								 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
									 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
									 	return i;
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									return k;
							 }
						 }
						
					 return -1;
				 }
				
		//Finding functionExist or Not? -->
				 
		//<-- Getting paramList
				 private ArrayList<String> getFuncParams(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 					{
					 						//for(int p=0;p<tFunction.paramList.size();i++)
					 							
					 						return tFunction.paramList.get(i);
					 					}
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getParamsFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getParamsParent(Parent,nameFunction);
					 
				 }
				 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting paramList  -->	
				 
		//<-- Getting returnType
				 private String getFuncReturnType(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return tFunction.ReturnType.get(i);
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getReturnTypeParent(Parent,nameFunction);
					 
				 }
				 private String getReturnTypeParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
							
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting returnType  -->	
				private boolean lookUpVar(String name)
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return true;
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return true;
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				if(LookUpVarin_ClassParent(name,CurrentPname))
			 					return true;
			 			} 
			 		 return false;
			 	 }
				private boolean LookUpVarin_ClassParent(String name,String Parent)
				 {
					int i= VarindexOfParent(Parent,name);
					 if(i!=-1) 
						 return true;
					 return false;
				 }
				private int VarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return i;
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									return k;
							 }
						 }
						
					 return -1;
				 }
		
				private String getReturnTypeVar(String name) //Method to call
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return tv.varDT.get(i);
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				return returnTypeVarin_ClassParent(name,CurrentPname);
			 					
			 			} 
			 		 return null;
			 	 }
				private String returnTypeVarin_ClassParent(String name,String Parent)
				 {
					return returnTypeVarindexOfParent(Parent,name);
					
				 }
				private String returnTypeVarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									
							 }
						 }
						
					 return null;
				 }

				private boolean isArrayType(String name) //Method to call
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return (tv.isVarArrayTable.get(i));
			 				 
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
			 					
			 			} 
			 		 return false;
			 	 }
				private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
				 {
					return isArrayTypeVarindexOfParent(Parent,name);
					
				 }
				private boolean isArrayTypeVarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									
							 }
						 }
						
					 return false;
				 }
		
				}

	//isArrayType

	//EXP Semantic if END


			 
		}

		//IF-ELSE Semantic END
		
		
		//FunctionCallModel Semantic Start
			 class functionCallModel {

					KeyWords words = new KeyWords();
					public int count=0,count_int=0,failedCount=0,scope=0;
					 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
					String follow_of_Parent[];
					String follow_for_exp[]={"Terminator"};
					ExpModelFunction exp ;
					int lc=0;
					functionCallModel(){}
					 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
					 {
							this.cp = cp;
							this.vp = vp;
							this.scope=scope;
							
						
					 }
					 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
					 {
							this.cp = cp;
							this.vp = vp;
							this.scope=scope;
							follow_of_Parent=Follow;
							
							
					 }
			 //<-- Finding functionExist or Not?
					 private boolean lookupFunction(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 						return true;
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return true;
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
						 					return true;
						 			} 
					 		}
					 		
					 		return false;
					 }

					private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						int i= indexOfParent(Parent,nameFunction);
						 if(i!=-1) 
							 return true;
						 return false;
					 }
					 private int indexOfParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
									 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
										 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
										 	return i;
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										return k;
								 }
							 }
							
						 return -1;
					 }
					
			//Finding functionExist or Not? -->
					 
			//<-- Getting paramList
					 private ArrayList<String> getFuncParams(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 					{
						 						//for(int p=0;p<tFunction.paramList.size();i++)
						 							
						 						return tFunction.paramList.get(i);
						 					}
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				
						 					return getParamsFunctionin_ClassParent(name,CurrentPname);
						 			} 
					 		}
					 		
					 		return null;
					 }

					private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						return getParamsParent(Parent,nameFunction);
						 
					 }
					 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
										 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
											 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										
								 }
							 }
							
						 return null;
					 }
					
			//Getting paramList  -->	
					 
			//<-- Getting returnType
					 private String getFuncReturnType(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 						return tFunction.ReturnType.get(i);
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				
						 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
						 			} 
					 		}
					 		
					 		return null;
					 }

					private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						return getReturnTypeParent(Parent,nameFunction);
						 
					 }
					 private String getReturnTypeParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
								
										 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
											 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										
								 }
							 }
							
						 return null;
					 }
					
			//Getting returnType  -->	
					 ArrayList<String> params =new ArrayList<>();
					 public boolean functionCall(ArrayList<String> cp,int c)
					 {
						 
						
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookupFunction(vp.get(c)))
							 {
								 System.out.println("No function Found "+vp.get(c)+" at char number :"+c);
								 return false;
							 }
							 if(vp.get(c).equals("main"))
							 {
								 System.out.println("main function can't be called "+vp.get(c)+" at char number :"+c);
								 return false;
							 }
							
								 params=getFuncParams(vp.get(c));
								
							 c++;
							 if(cp.get(c).equals("("))
							 {
								 c++;
								
								 if(params(cp,c))
								 {
									 
									 c=count_int;
									 
									 if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										 count=count_int=c;
										 
										 return true; 
									 }
									 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									 {
										// c++;
										 count=count_int=c;
										
										 return true; 
									 }
								 }
							 }
						 }
						 
						 return false;
					 }
					 boolean params(ArrayList<String> cp,int c)
					 {
						 //Pass the params in exp and test to see if it exits or not?
						 
						 exp = new ExpModelFunction(cp,vp,follow_for_exp,false,scope,params,lc);
						 if(exp.exp(cp, c))
						 {
								
							 c=exp.count;
							
							 if(c<cp.size())
							 {
								 //System.out.println("Exp chal gai function mey paframs:"+cp.get(c));
								if(c<cp.size()-2)
								{
									  if(cp.get(c).equals(","))
										{
											lc++;
											c++;
											 if(params(cp, c))
											 {
												 c=count_int;
													count=count_int;
													return true;
												 
												 
											 }
										}
									 else if(cp.get(c).equals(")"))
									 {
										
											count_int=++c;
										
											return true;
										 
									 }
									  
								}
								 else if(cp.get(c).equals(")"))
								 {
									
										c=count_int=c;
									
										return true;
									 
								 }
								 else if(cp.get(c).equals("Terminator"))
								 {
									   
										count_int=c;
									
										return true;
									 
								 }
								 
							
							 }
						 }
						 if(lc<params.size() && !exp.extra)
							{
								System.out.println("few params at char number: "+c);
								return false;
							}
						 return false;
					 }


	//EXP Semantic Function  START

					 class ExpModelFunction 
						{
							KeyWords words = new KeyWords();
							public int count=0,count_int=0,FailedCounter=0,scope=0;
							 String follow_of_Parent[];
							 ArrayList<String> cp = new ArrayList();
							 ArrayList<String> vp = new ArrayList();
							 ArrayList<String> paramsList = new ArrayList();
							 boolean ifParams=false,typeChanged=false,opFlag=false,extra;
							 String returnType="",ChangeType="";
							int listcounter=0;
							 String leftOp="",rightOp="";
							 public boolean AllowAssignation=true;
							ExpModelFunction(){}
							public ExpModelFunction(ArrayList<String> cp,ArrayList<String> vp,int scope,ArrayList<String> params,int lc)
							{
								this.cp=cp;
								this.vp=vp;
								this.scope=scope;
								paramsList=params;
								listcounter=lc;
							}
							public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],int scope,ArrayList<String> params,int lc)
							{
								this.cp=cp;
								this.vp=vp;
								this.scope=scope;
								paramsList=params;
								follow_of_Parent=follow;
								listcounter=lc;
							}
							public ExpModelFunction(ArrayList cp,ArrayList vp,boolean allow,int scope,ArrayList<String> params,int lc)
							{
								this.cp=cp;
								this.vp=vp;
								AllowAssignation=allow;
								this.scope=scope;
								paramsList=params;
								listcounter=lc;
							}
							public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope,ArrayList<String> params,int lc)
							{
								this.cp=cp;
								this.vp=vp;
								this.scope=scope;
								paramsList=params;
								follow_of_Parent=follow;
								AllowAssignation=allow;
								listcounter=lc;
							}
							
						public 	boolean exp(ArrayList<String> ClassPart,int count)
							{
							
								if(isUniOp(ClassPart.get(count)))
								{
									count++;
									if(exp1(ClassPart,count))
									{
										count=count_int;
										this.count=count;
										returnType="boolean";
										return true;
									}
								}
								else if(exp1(ClassPart,count))
								{
									
									count=count_int;
									count_int=0;
									this.count=count;
									returnType="boolean";
									
									return true;
								}
								else if(words.isParentFollow(cp.get(count), follow_of_Parent))
								{
									count=count_int;
									this.count=count;
									returnType="boolean";
									return true;
								}
								if(FailedCounter<count)
									FailedCounter=count;
								returnType=null;
								return false;
							}

						boolean exp1(ArrayList<String> cp,int c)
						{
								if(cp.size()<=c){count_int=cp.size()-1;return true; }
							
								if(cp.get(c).equals("ID"))
								{
									
									if(vp.get(c+1).equals("("))
									{
										String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
										functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
										fcm.follow_for_exp[0]=")";
										
										if(fcm.functionCall(cp, c))
										{
											
											String dt=getFuncReturnType(vp.get(c));
											String rdt=getFuncReturnType(vp.get(c));
											
											c=fcm.count-1;
											if(!opFlag) //No Left Operator Exists right now
											{
												
												
												if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
												{
																	leftOp=dt; //left operator
																	opFlag=true;
																	
												}
												else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
												{
														System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
														return false;
					
												}
												else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
												{
													if(dt.equals("boolean"))
													{
														returnType="boolean";
													}
													else 
													{
														System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
												else
												{
													
													if(listcounter<paramsList.size())
													{
														
														if(!typeChanged)
														{
															if(!dt.equals(paramsList.get(listcounter)))
															{
																System.out.println("invalid parameter error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else
																listcounter++;
														}	
														else if(typeChanged)
														{
															typeChanged=false;
															if(!compTypeCast(dt,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else if(!ChangeType.equals(paramsList.get(listcounter)))
															{
																System.out.println("invalid parameter error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else
																listcounter++;
														}		
													}
													else
													{
														System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
														extra=true;
														return false;
													}
												}
									
											}
											else if(opFlag) //Left Operator Exists
											{
												
												{
													if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
													{
			
														rightOp=rdt;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														leftOp=returnType;
														opFlag=true;
														
													}
													else //No more operators
													{

														if(listcounter<paramsList.size())
														{
															
																
																if(!typeChanged)
																{
																	if(!rdt.equals(paramsList.get(listcounter)))
																	{
																		System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
																		return false;
																	}
																	else
																	{
																		rightOp=rdt;
																		returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																		if(returnType.equals("x"))
																		{
																			System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																			return false;
																		}
																		opFlag=false;
																		listcounter+=1;
																		 
																	}
																}
																else if(typeChanged)
																{
																	typeChanged=false;
																	String temp=rdt;
																	if(!compTypeCast(temp,ChangeType))
																	{
																		System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	else if(!ChangeType.equals(paramsList.get(listcounter)))
																	{
																		System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	else
																	{
																		rightOp=ChangeType;
																		returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																		if(returnType.equals("x"))
																		{
																			System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																			return false;
																		}
																		opFlag=false;
																		listcounter++;
																	}
																}
																
															
														}
														else
														{
															System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
															extra=true;
															return false;
														}
													}
												}
												
											}
										
										}
										/*
										if(!lookupFunction(vp.get(c)))
										{
											System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										else
										{}
										*/
									}//Not function
									else //Maybe variable?
									{
										if(!lookUpVar(vp.get(c)))
										{
											System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										else
										{
											if(!opFlag) //No Left Operator Exists right now
											{
												String dt=getReturnTypeVar(vp.get(c));
												if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
												{
																	leftOp=dt; //left operator
																	opFlag=true;
												}
												else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
												{
													if(dt.equals("dig") || dt.equals("frac"))
													{
														returnType="dig";
													}
													else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
													{
														System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
												else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
												{
													if(dt.equals("boolean"))
													{
														returnType="boolean";
													}
													else 
													{
														System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
												else if(!vp.get(c+1).equals("."))
												{

													if(listcounter<paramsList.size())
													{
														
															
														if(!typeChanged)
														{
															if(!dt.equals(paramsList.get(listcounter)))
															{
																System.out.println("invalidat parameter error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else
															{
																if(cp.get(c+1).equals("["))
																if(!isArrayType(vp.get(c)))
																{
																	System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																listcounter++;
															}
														}
														else if(typeChanged)
														{
															typeChanged=false;
															
															if(!compTypeCast(dt,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else if(!ChangeType.equals(paramsList.get(listcounter)))
															{
																System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															else
																listcounter++;
														}
															
														
													}
													else
													{
														System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
														extra=true;
														return false;
													}
												}
											}
											else if(opFlag) //Left Operator Exists
											{
												String rdt=getReturnTypeVar(vp.get(c));
												if(cp.get(c+1).equals("["))
												{
													if(!isArrayType(vp.get(c)))
													{
														System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
														return false;
													}
												}
												else
												{
													if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
													{
													
														
														rightOp=rdt;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														leftOp=returnType;
														opFlag=true;
														
													}
													else //No more operators
													{

														if(listcounter<paramsList.size())
														{
															
																
																if(!typeChanged)
																{
																	if(!rdt.equals(paramsList.get(listcounter)))
																	{
																		System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
																		return false;
																	}
																	else
																	{
																		rightOp=rdt;
																		returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																		if(returnType.equals("x"))
																		{
																			System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																			return false;
																		}
																		opFlag=false;
																		listcounter+=1;
																		 
																	}
																}
																else if(typeChanged)
																{
																	typeChanged=false;
																	String temp=rdt;
																	if(!compTypeCast(temp,ChangeType))
																	{
																		System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	else if(!ChangeType.equals(paramsList.get(listcounter)))
																	{
																		System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	else
																	{
																		rightOp=ChangeType;
																		returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																		if(returnType.equals("x"))
																		{
																			System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																			return false;
																		}
																		opFlag=false;
																		listcounter++;
																	}
																}
																
															
														}
														else
														{
															System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
															extra=true;
															return false;
														}
													}
												}
												
											}
										}
									
									}
									c++;
									
									if(exp21(cp,c))
									{
										c=count_int;
										return true;
									}
								}
								else if(words.isConst(cp.get(c)))
								{
									if(!opFlag) //No Left Operator Exists right now
									{
										if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
															leftOp=words.getConst(cp.get(c)); //left operator
															opFlag=true;
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
										{
											if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
											{
												returnType="dig";
											}
											else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
											if(words.getConst(cp.get(c)).equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else
										{

											if(listcounter<paramsList.size())
											{
												
													
													if(!typeChanged)
													{
														if(!words.getConst(cp.get(c)).equals(paramsList.get(listcounter)))
														{
															System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
															return false;
														}
														else
														{
															
															listcounter+=1;
															 
														}
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=words.getConst(cp.get(c));
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else if(!ChangeType.equals(paramsList.get(listcounter)))
														{
															System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
															listcounter++;
													}
													
												
											}
											else
											{
												System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
												extra=true;
												return false;
											}
										}
									}
									else if(opFlag) //Left Operator Exists
									{
										if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
										{

											rightOp=words.getConst(cp.get(c));
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											leftOp=returnType;
											opFlag=true;
											
										}
										else //No more operators
										{

											if(listcounter<paramsList.size())
											{
												
													
													if(!typeChanged)
													{
														if(!words.getConst(cp.get(c)).equals(paramsList.get(listcounter)))
														{
															System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
															return false;
														}
														else
														{
															rightOp=words.getConst(cp.get(c));
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															listcounter+=1;
															 
														}
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=words.getConst(cp.get(c));
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else if(!ChangeType.equals(paramsList.get(listcounter)))
														{
															System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
														{
															rightOp=ChangeType;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															listcounter++;
														}
													}
													
												
											}
											else
											{
												System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
												extra=true;
												return false;
											}
										}
									}
									c++;
									
									if(exp2(cp,c))
									{
										
										c=count_int;
										return true;
									}
								}
								else if(cp.get(c).equals("("))
								{
									c++;
									if(news(cp,c))
									{
									
										c=count_int;
										return true;
									}
								}
								if(FailedCounter<c)
								FailedCounter=c;
								return false;
						}
						boolean compTypeCast(String func,String Modified)
						{
							if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
							{
								if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
									return true;
								
							}
							else if(Modified.equals("beta"))
							{
								
								if(func.equals("beta"))
									return true;
							}
							
							return false;
						}
						boolean exp2(ArrayList<String>  cp,int c)
						{
							
								if(cp.size()<=c){count_int=cp.size()-1; return true; } 
								else
								{
									if(isOp(cp.get(c),c))
									{
										//..........
										c++;
										if(exp1(cp,c))
										{
											c=count_int;
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									else if(cp.get(c).equals(")"))
									{
										count_int=c;
										return true;			
									}
									else if(words.isParentFollow(cp.get(c),follow_of_Parent))
									{

										count_int=c;
										return true;
												
									}
									else if(cp.get(c).equals(",") && !ifParams)
									{
										count_int=c;
										
										return true;
									}
									else if(cp.get(c).equals("]"))
									{
										count_int=c;
										return true;
									}
								}
								if(FailedCounter<c)
									FailedCounter=c;
								return false;
							}
						boolean exp21(ArrayList<String>  cp,int c)
							{
								if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
								
								if(exp2(cp,c))
								{
									c=count_int;
									
									return true;
								}
								
								else if(cp.get(c).equals("["))
								{
									
									c++;
									
									if(words.isConst(cp.get(c)))
									{
										
										c++;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											if(exp2(cp,c))
											{
												
												c=count_int;
												
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;	
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									else if(cp.get(c).equals("ID"))
									{

										if(vp.get(c+1).equals("("))
										{
											if(!lookupFunction(vp.get(c)))
											{
												System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
										}//Not function
										else //Maybe variable?
										{
											
												if(!lookUpVar(vp.get(c)))
												{
													System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												
											
										}
										c++;
										
										if(exp1(cp,c))
										{
											
											c=count_int;
									
												//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
											if(cp.get(c).equals("]"))
											{
												
												c++;
												if(exp2(cp,c))
												{
													
													c=count_int;
													return true;
												}
												if(FailedCounter<c)
													FailedCounter=c;
											}
											else if(cp.get(c-1).equals("]"))
											{
												
												if(exp2(cp,c))
												{
													
													c=count_int;
													return true;
												}
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								
								else if(O1(cp,c))
								{
									
									c=count_int;
									if(exp2(cp,c))
									{
										
										c=count_int;
										count_int=c;
									
										if(c>=cp.size()){c=count_int=cp.size()-1;}
										return true;
									}
									else if(cp.get(c).equals("Dot operator"))
									{
										if(exp21(cp,c))
										{
											c=count_int;
											count_int=c;

											if(c>=cp.size()){c=count_int=cp.size()-1;}
											return true;
										}
									}
								}
								if(FailedCounter<c)
									FailedCounter=c;
								return false;
							}
						boolean params(ArrayList<String>  cp,int c)
							{
							
								if(cp.size()<=c){count_int=cp.size()-1;return true; }
								ifParams=true;

								if(cp.get(c).equals(")"))
								{
									c++;
									count_int=c;
									
									ifParams=false;
									return true;
								}
								else if(exp1(cp,c))
								{
									
									c=count_int;
									//System.out.println(cp.get(c)+":exp1 of params"+c);
									if(c<cp.size())
									{
										
										if(cp.get(c).equals(")"))
										{
											c++;
											count_int=c;
											//System.out.println(cp.get(c)+":dF"+c);
											ifParams=false;
											return true;
										}
										else if(cp.get(c).equals("]"))
										{
											c++;
											count_int=c;
											//System.out.println("___"+cp.get(c));
											ifParams=false;
											return true;
										}
										else if(cp.get(c).equals(",")) //what if datatype?
										{
											
											c++;
											 if(cp.get(c).equals("ID"))
											 {
												 c++;
												 if(cp.get(c).equals("ID"))
												 {
													 c++;
													 if(params(cp,c))
													 {
															
															c=count_int;
															ifParams=false;
															return true;
												     }
												 }
											 }
										}
									}
									else if(cp.get(c-1).equals(")"))
									{
									
										c=count_int;
										ifParams=false;
										return true;
									}
								}
								if(FailedCounter<c)
									FailedCounter=c;
								return false;
							}
						boolean news(ArrayList<String>  cp,int c)
							{
								
							if(isUniOp(cp.get(c)))
							{
								c++;
								if(exp1(cp,c))
								{
									
									c=count_int; 
									
									if(c>=cp.size())
									{
										if(cp.get(c-1).equals(")"))
										{
											if(ifParams){count_int=c;return true;}
											c=count_int;
											
											return true;
										}
									}
									else
									if(cp.get(c).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c++;
										if(exp2(cp,c))
										{
											c=count_int; //count_int=0; //count_int=0;
											//count_int=0;
											
											return true;
										}
									}
									else if(cp.get(c-1).equals(")"))
									{
										//c++;
										if(ifParams){count_int=c;return true;}
										if(exp2(cp,c))
										{
											c=count_int; //count_int=0; //count_int=0;
											//count_int=0;
											
											return true;
										}
									}
								}
							
							}
							else if(exp1(cp,c))
								{
									
									c=count_int; 
									//System.out.println(c+":33:"+cp.get(c));
									if(c>=cp.size())
									{
										if(cp.get(c-1).equals(")"))
										{
											if(ifParams){count_int=c;return true;}
											c=count_int;
											
											return true;
										}
										else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
										{
										   c=count_int;	
											return true;
										}
									}
									else
									if(cp.get(c).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c++;
										
										if(exp2(cp,c))
										{
											c=count_int; //count_int=0; //count_int=0;
											//count_int=0;
											
											return true;
										}
									}
									else if(cp.get(c-1).equals(")"))
									{
										//c++;
										if(ifParams){count_int=c;return true;}
										if(exp2(cp,c))
										{
											
											c=count_int; //count_int=0; //count_int=0;
											//count_int=0;
											
											return true;
										}
										else if(words.isParentFollow(cp.get(c), follow_of_Parent))
										{
											//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
										   c=count_int;	
											return true;
										}
									}
									else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									{
										//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
									   c=count_int;	
										return true;
									}
								
								}
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c).equals("datatype"))
								{
									ChangeType=vp.get(c);
									typeChanged=true;
									c++;
									if(cp.get(c).equals(")"))
									{
										
										c++;
										if(exp1(cp,c))
										{
											c=count_int; //count_int=0; //count_int=0;
											//count_int=0;
											return true;
										}
									}
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
								return false;
							}
						boolean O1(ArrayList<String>  cp,int c) // Incomplete
							{
								if(cp.get(c).equals("Dot operator"))
								{
									
									c++;
									
									if(cp.get(c).equals("ID"))
									{
										
									
										if(exp1(cp,c))
										{
											c=count_int;
											if(O1(cp,c))
											{
												c=count_int;
												count_int=c;
												//System.out.println(c+":fff:"+cp.get(c));
												return true;
											}
										}
										  if(cp.get(c).equals("["))
										{
											
											c++;
											//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
											if(words.isConst(cp.get(c)))
											{
												c++;
												
												if(cp.get(c).equals("]"))
												{
													c++;
													count_int=c;
													//System.out.println("333,"+c+","+cp.get(c));
													return true;
												}
											}
										}
										else if(cp.get(c).equals("&"))
										{
											c++;
											count_int=c;
											return true;
										}
										else if(cp.get(c).equals("Dot operator"))
										{
											if(O1(cp,c))
											{
												c=count_int;
												count_int=c;
												//System.out.println(c+":fff:"+cp.get(c));
												return true;
											}
										}
										else if(cp.get(c).equals(")"))
										{
										
											c=count_int=c;
											if(ifParams)
											ifParams=false;
											return true;
										}
									}
									else if(words.isConst(cp.get(c)))
									{
										c++;
										count_int=c;
										return true;
									}
								}
								else if(cp.get(c).equals("&"))
								{
									c++;
									count_int=c;
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
								return false;
							}
						String OperationOnOperands(String left,String right,String op,int cpInd)
						{
							if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
							{
								if(left.equals("dig"))
								{
									if(right.equals("dig"))
										return "dig";
									else if(right.equals("frac"))
										return "frac";
									else if(right.equals("alpha"))
										return "dig";
									
								}
								else if(left.equals("frac"))
								{
									if(right.equals("dig"))
										return "frac";
									else if(right.equals("frac"))
										return "frac";
									else if(right.equals("alpha"))
										return "frac";
									
								}
								else if(left.equals("alpha"))
								{
									if(right.equals("dig"))
										return "alpha";
									else if(right.equals("frac"))
										return "alpha";
									else if(right.equals("alpha"))
										return "alpha";
									
								}
								
							}
							else if(op.equals("suma"))
							{
								if(left.equals("dig"))
								{
									if(right.equals("dig"))
										return "dig";
									else if(right.equals("frac"))
										return "frac";
									else if(right.equals("alpha"))
										return "dig";
									
								}
								else if(left.equals("frac"))
								{
									if(right.equals("dig"))
										return "frac";
									else if(right.equals("frac"))
										return "frac";
									else if(right.equals("alpha"))
										return "frac";
									
								}
								else if(left.equals("alpha"))
								{
									if(right.equals("dig"))
										return "alpha";
									else if(right.equals("frac"))
										return "alpha";
									else if(right.equals("alpha"))
										return "alpha";
									
								}
								else if(left.equals("beta"))
								{
									if(right.equals("dig"))
										return "beta";
									else if(right.equals("frac"))
										return "beta";
									else if(right.equals("alpha"))
										return "beta";
									
									
								}
								
							}
							else if(cp.get(cpInd).equals("RO"))
							{
								if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
								{
									if(right.equals("dig"))
										return "boolean";
									else if(right.equals("frac"))
										return "boolean";
									else if(right.equals("alpha"))
										return "boolean";
									
								}
								
							}
							else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
							{
								return "boolean" ;
							}
							return "x";
						}
						String valueOp(int index)
							{
								
								return vp.get(index);
							}
					
						boolean isOp(String cp,int c)
							{
								
								if(cp.equals("RO"))//
								{
									 return true;
								}
								else if(cp.equals("Assignment") && AllowAssignation)
								{
									return true;
								}
								else if(cp.equals("BWOP"))
								{
									if(valueOp(c).equals("bitOr"))
									 return true;
								}
								else if(cp.equals("BWOP"))
								{
									if(valueOp(c).equals("bitAnd"))
									 return true;
								}
								else if(cp.equals("OR"))
								{
									 return true;
								}
								else if(cp.equals("AddSum"))
								{
									 return true;
								}
								else if(cp.equals("Mux"))
								{
									
									 return true;
								}
								else if(cp.equals("DIVMOD"))
								{
									 return true;
								}
								else if(cp.equals("Incdec") || cp.equals("Not operator"))
								{
									 return true;
								}
								else if(cp.equals("LO"))
								{
									 return true;
								}

								
								
								return false;
							}
							
						boolean isUniOp(String a)
							{
								if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
								{
									return true;
								}
								return false;
							}
						private boolean lookUpVar(String name)
					 	 {
							 if(tv.varDT.size()>0) //Current class;
					 		 for(int i=0;i<tv.varTable.size();i++)
					 			 if(tv.varTable.get(i).equals(name))
					 				 return true;
							//TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
					 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
					 						return true;
					 	 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				if(LookUpVarin_ClassParent(name,CurrentPname))
					 					return true;
					 			} 
					 		 return false;
					 	 }
						private boolean LookUpVarin_ClassParent(String name,String Parent)
						 {
							int i= VarindexOfParent(Parent,name);
							 if(i!=-1) 
								 return true;
							 return false;
						 }
						private int VarindexOfParent(String Parent,String name)
						 {
							 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
								 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
								 {
									 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
											 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
											 	return i;
									 //Didn't find now look in its parent
									 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
									 {
										 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),name);
											return k;
									 }
								 }
								
							 return -1;
						 }
				
						private String getReturnTypeVar(String name) //Method to call
					 	 {
							 if(tv.varDT.size()>0) //Current class;
					 		 for(int i=0;i<tv.varTable.size();i++)
					 			 if(tv.varTable.get(i).equals(name))
					 				 return tv.varDT.get(i);
							//TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
					 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
					 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
					 	 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				return returnTypeVarin_ClassParent(name,CurrentPname);
					 					
					 			} 
					 		 return null;
					 	 }
						private String returnTypeVarin_ClassParent(String name,String Parent)
						 {
							return returnTypeVarindexOfParent(Parent,name);
							
						 }
						private String returnTypeVarindexOfParent(String Parent,String name)
						 {
							 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
								 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
								 {
									 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
											 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
											 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
									 //Didn't find now look in its parent
									 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
									 {
										 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
											
									 }
								 }
								
							 return null;
						 }

						private boolean isArrayType(String name) //Method to call
					 	 {
							 if(tv.varDT.size()>0) //Current class;
					 		 for(int i=0;i<tv.varTable.size();i++)
					 			 if(tv.varTable.get(i).equals(name))
					 				 return (tv.isVarArrayTable.get(i));
					 				 
							//TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
					 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
					 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
					 	 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
					 					
					 			} 
					 		 return false;
					 	 }
						private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
						 {
							return isArrayTypeVarindexOfParent(Parent,name);
							
						 }
						private boolean isArrayTypeVarindexOfParent(String Parent,String name)
						 {
							 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
								 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
								 {
									 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
											 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
											 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
									 //Didn't find now look in its parent
									 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
									 {
										 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
											
									 }
								 }
								
							 return false;
						 }
				
						}

	//isArrayType

	//EXP Semantic Function END

					 
					 
					 
				}

		//FunctionCallModel Semantic End
		
			//SwitchModel Semantic Start
				class SwitchModel {
					KeyWords words = new KeyWords();
					public int count=0,count_int=0,failedCount=0,scope=0;
					 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
					String follow_of_Parent[];
					String follow_for_exp[]={")"};
					String follow_for_body[]={"]"};
					ExpModel exp ;
					BodyModel body;
					tableVar tv=new tableVar();
					SwitchModel(){}
					 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
					 {
							this.cp = cp;
							this.vp = vp;
							this.scope=scope;
							exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
							body=new BodyModel(cp,vp,follow_for_body,scope,tv);
							follow_of_Parent=null;
					 }
					 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
					 {
							this.cp = cp;
							this.vp = vp;
							this.scope=scope;
							follow_of_Parent=Follow;
							body=new BodyModel(cp,vp,follow_for_body,scope,tv);
							exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
							
					 }
					 public boolean Switch(ArrayList<String> cp,int c)
					 {
						 if(cp.get(c).equals("check"))
						 {
							 c++;
							 if(cp.get(c).equals("("))
							 {
								 c++;
								 if(exp.exp(cp, c))
								 {
									//...............Semantic To be Done ....................................................
									  if(exp.returnType!="dig" && exp.returnType!="alpha" && exp.returnType!="beta"){
									  System.out.println("Invalid operand "+ vp.get(c)+" at char number:"+c); return false;}
									 //.......................................................................................
									 c=exp.count;
									
									if(cp.get(c).equals(")"))
									{
										c++;
										
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 
											if(test(cp,c))
											{
												c=count_int;
												count=count_int;
												return true;
											}
											 else if(cp.get(c).equals("]"))
											 {
											   		c++;
											   		count_int=c;
												   count=count_int;
													return true;
							   	
											}
											if(failedCount<c)
									   			failedCount=c;
											  
										 }
										 if(failedCount<c)
									   			failedCount=c;
									}
									else if(cp.get(c-1).equals(")"))
									{
										
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 if(test(cp,c))
												{
													c=count_int;
													count=count_int;
													return true;
												}
												 else if(cp.get(c).equals("]"))
												 {
												   		c++;
												   		count_int=c;
													   count=count_int;
														return true;
								   	
												}
											 if(failedCount<c)
										   			failedCount=c;
											  
										 }
										 if(failedCount<c)
									   			failedCount=c;
									}
									if(failedCount<c)
							   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						 return false;
					 }
					 boolean test(ArrayList<String> cp,int c)
					 {
						 if(cp.get(c).equals("test"))
						 {
							 c++;
							 String[] temp={":"};
							 ExpModel exp1=new ExpModel(this.cp,vp,temp,false,scope);
						
							 if(exp1.exp(cp, c))
							 {
								
								//...............Semantic To be Done ....................................................
								  if(exp.returnType!="dig" && exp.returnType!="alpha" && exp.returnType!="beta"){
								  System.out.println("Invalid operand "+ vp.get(c)+" at char number:"+c); return false;}
								 //.......................................................................................
								 c=exp1.count;
								
								 if(cp.get(c).equals(":"))
								 {
									 
									 c++;
									//call for body
									 
									 String follow_for_body1[]={"]","default","test"};
									 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope,tv);
									   if(body1.body(cp,c))
									   {
									   	c=body1.count;
									   
									   		
									   		if(cp.get(c).equals("default"))
									   		{
									   			c++;
									   			if(cp.get(c).equals(":"))
									   			{
									   				c++;
									   				if(body.body(cp, c))
									   				{
									   					c=body.count;
									   					if(cp.get(c).equals("]"))
									   					{	
													   			count_int=c;
													   			//count=count_int;
													   			return true;	
									   					}
									   					if(failedCount<c)
												   			failedCount=c;
									   				}
									   				if(failedCount<c && body.failedCount<c)
											   			failedCount=c;
												   	else if(body.failedCount>c)
												   			failedCount=body.failedCount;
									   			}
									   			if(failedCount<c)
										   			failedCount=c;
									   			
									   		}
									   		else if (cp.get(c).equals("test"))
									   		{
									   			
									   			if(test(cp,c))
									   			{
									   				c=count_int;
										   			return true;
									   			}
									   			if(failedCount<c)
										   			failedCount=c;
									   			
									   		}
									   		else if(cp.get(c).equals("]"))
									   		{	
									   			count_int=c;
									   			return true;	
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			count_int=c;
									   			return true;
									   		}
									   		else
									   		{
									   			count_int=c;
									   			return true;
									   		}
								}
									   if(failedCount<c)
								   			failedCount=c;
								 }
								 else  if(cp.get(c-1).equals(":"))
								 {
					//call for body
									 
									 String follow_for_body1[]={"]","default","test"};
									 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope,tv);
									   if(body1.body(cp,c))
									   {
									   	c=body1.count;
									   
									   		
									   		if(cp.get(c).equals("default"))
									   		{
									   			c++;
									   			if(cp.get(c).equals(":"))
									   			{
									   				c++;
									   				if(body.body(cp, c))
									   				{
									   					c=body.count;
									   					if(cp.get(c).equals("]"))
									   					{	
													   			count_int=c;
													   			//count=count_int;
													   			return true;	
									   					}
									   					if(failedCount<c)
												   			failedCount=c;
									   				}
									   				if(failedCount<c && body.failedCount<c)
											   			failedCount=c;
												   	else if(body.failedCount>c)
												   			failedCount=body.failedCount;
									   			}
									   			if(failedCount<c)
										   			failedCount=c;
									   			
									   		}
									   		else if (cp.get(c).equals("test"))
									   		{
									   			
									   			if(test(cp,c))
									   			{
									   				
									   				c=count_int;
										   			return true;
									   			}
									   			if(failedCount<c)
										   			failedCount=c;
									   		}
									   		else if(cp.get(c).equals("]"))
									   		{	
									   			count_int=c;
									   			return true;	
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			count_int=c;
									   			return true;
									   		}
									   		else
									   		{
									   			count_int=c;
									   			return true;
									   		}
								}
								 }
								 if(failedCount<c)
							   			failedCount=c;
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						 return false;
					 }
					 
				}

				//SwitchModel Semantic End
		//While Loop Semantic Start
		class WhileModel 
		{
			private KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 private ArrayList<String> cp = new ArrayList();
			 private ArrayList<String> vp = new ArrayList();
				 private String follow_of_Parent[];
				 private String follow_for_exp[]={")","["};
				 private	String follow_for_body[]={"]"};
				 private	ExpModel exp ;
				 private	BodyModel body;
				 tableVar tv=new tableVar();
				 private WhileModel(){}
			 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope,tv);
					follow_of_Parent=null;
			 }
			 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					follow_of_Parent=Follow;
					body=new BodyModel(cp,vp,follow_for_body,scope,tv);
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					
			 }
			 public boolean test(ArrayList<String>cp,int c)
			 {
					
					if(cp.get(c).equals("while loop"))
					 {
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 
							 if(exp.exp(cp, c))
							 {
								 //.............Semantic to be done..................
								  if(exp.returnType!="boolean") { System.out.println("Boolean expected at char number: "+c); return false;}
								 //...............................
								 c=exp.count;
								
								 
								 if(cp.get(c).equals(")"))
								{
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 //call for body
										  
										   if(body.body(cp,c))
										   {
										   	c=body.count;
										   	if(cp.get(c).equals("]"))
										   	{
										   		
										   		c++;
										   		if(c>cp.size()-1){c=count_int=c;
									   			count=count_int;
									   			return true;}
										   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		else
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   	}
										   	if(failedCount<c)
									   			failedCount=c;
										   }
										   if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								 //if(c.prev is equal to )
								 if(cp.get(c-1).equals(")"))
									{
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 //call for body
											  if(body.body(cp,c))
											   {
											   	c=body.count;
											   	if(cp.get(c).equals("]"))
											   	{
											   		c++;
											   		if(c>cp.size()-1){c=count_int=c;
										   			count=count_int;
										   			return true;}
											   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
											   		{
											   			c=count_int=c;
											   			count=count_int;
											   			return true;
											   		}
											   		else
											   		{
											   			c=count_int=c;
											   			count=count_int;
											   			return true;
											   		}
											   	}
											   	if(failedCount<c)
										   			failedCount=c;
											   }
											  if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
										 }
										 if(failedCount<c)
									   			failedCount=c;
									}
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					if(failedCount<c)
			   			failedCount=c;
					 return false;
					 
			 }
		
		}
		//While Loop Semantic End
		//For Loop Semantic Start
			 class ForModel 
			 {
			  private KeyWords words = new KeyWords();
			  public int count=0,failedCount=0,scope=0;
			  private int count_int=0;
			  private ArrayList<String> cp = new ArrayList();
			  private ArrayList<String> vp = new ArrayList();
			  private String follow_of_Parent[];
			  private String follow_for_body[]={"]"};
			  private BodyModel body;
			  private tableVar tv = new tableVar();
			  private tableVar ptv = new tableVar();
			  private ForModel(){}
			  public ForModel(ArrayList<String> cp, ArrayList<String> vp,int scope,tableVar ptv)
			  {
			 			this.cp = cp;
			 			this.vp = vp;
			 			this.scope=scope;
			 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			 			this.ptv=ptv;
			 			follow_of_Parent=null;
			   }
			   public ForModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,tableVar ptv)
			   {
			 			this.cp = cp;
			 			this.vp = vp;
			 			this.scope=scope;
			 			follow_of_Parent=Follow;	
			 			this.ptv=ptv;
			 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			   }
			   	public boolean For(ArrayList<String>cp,int c)
			   	{
			   		if(cp.get(c).equals("for loop"))
			   		{
			   			
			   			c++;
			   			
			   			if(cp.get(c).equals("("))
			   			{
			   				c++;
			   				
			   				if(X(cp,c))
			   				{
			   					c=count_int;
			   					
			   					if(Y(cp,c))
			   					{
			   						c=count_int;
			   						
			   						if(Z(cp,c))
			   						{
			   							c=count_int;
			   							//System.out.println(c+"Here:"+cp.get(c));
			   							if(cp.get(c).equals(")"))
			   							{
			   								c++;
			   								if(cp.get(c).equals("["))
			   								{
			   									c++;
			   									if(body.body(cp, c))
			   									{
			   										c=body.count;
			   										if(cp.get(c).equals("]"))
			   										{
			   											c++;
			   											count=count_int=c;
			   											return true;
			   										}
			   									}
			   								}
			   							}
			   						}
			   					}
			   				}
			   			}
			   		}
			   		
			   		return false;
			   	}
			   	
			   	private  boolean X(ArrayList<String>cp,int c)
			   	{
			   		declarationModelSemanticFor decl=new declarationModelSemanticFor(cp,vp,scope);
			   	
			   		if(decl.decl(cp, c))
			   		{
			   			
			   			c=decl.count;
			   			////System.out.println("Here"+cp.get(c-1));
			   			if(cp.get(c).equals("Terminator"))
			   			{
			   				c++;
			   				count_int=c;
			   				return true;
			   			}
			   			else if(cp.get(c-1).equals("Terminator"))
			   			{
			   				count_int=c;
			   				return true;
			   			}
			   		}
			   		else if(Assign(cp,c,"Terminator".split("\\ ")))
			   		{
			   			
			   			c=count_int;
			   			if(cp.get(c).equals("Terminator"))
			   			{
			   				c++;
			   				count_int=c;
			   				return true;
			   			}
			   		}
			   		else if(cp.get(c).equals("Terminator"))
			 		{
			   			c++;
			 			count_int=c;
			 			return true;
			 		}
			   		return false;
			   	}
	private boolean lookUpVar(String name)
			 	 {
					 if(tv.varDT.size()>0)
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return true;
					 
			 		 return false;
			 	 }
			   	private int findclassIndex(String name)
			 	 {
			   		String[] n=name.split("\\class_");
			   	
			   	
			   		if(n.length>1)
			 		 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
			 			 if(Syntaxtification.ct.Cname.equals(name))
			 				 return i;
					 
			 		 return -1;
			 	 }
			   	private int findInnerclassIndex(String name)
			 	 {
			   		String[] n=name.split("\\class_");
			   		
			   	
			   		if(n.length>1)
					 if(Syntaxtification.ct.innerClassList.size()>0)
			 		 for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++)
			 			 if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.equals(n[1]))
			 				 return i;
					 
			 		 return -1;
			 	 }
		
				private  boolean LookupVarP(String name)
				 {
					 for(int i=0; i<ptv.isVarArrayTable.size();i++)
					 {
						 if(name.contentEquals(ptv.varTable.get(i)))
						 {
							 
							 return true;
					 
						 }
					 }
					// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
					 return false;
				 }
			   	private boolean lookUpDeclareVar(String name)
			 	 {
			   		if(Syntaxtification.ct.Cvariable.size()>0)
					 if(Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.size()>0)
			 		 for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.size();i++)
			 			 if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 			 {
			 				 if(Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i).equals("dig") || Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i).equals("frac") || Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i).contains("class"))
			 				 return true;
			 			}
					 for(int i=0; i<ptv.isVarArrayTable.size();i++)
					 {
						 if(name.contentEquals(ptv.varTable.get(i)))
						 {
							 
							 return true;
					 
						 }
					 }
			 		 return false;
			 	 }
			   	private  boolean Assign(ArrayList<String>cp,int c,String follow_for_exp_Assign[])
			   	{
			   		
			   		ExpModel expMain = new ExpModel(cp,vp,follow_for_exp_Assign,false,scope);
			   		if(cp.get(c).equals("ID"))
			   		{
			   			
			   			if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
			   			{
			   				System.out.println("No Variable found/Incompatible datatypes: "+vp.get(c)+ " at char number: "+c);
			   			}
			   			c++;
			   			
			   			if(expMain.isOp(cp.get(c), c) && !cp.get(c).equals("Assignment"))
			   			{
			   				
			   				c++;
			   				if(cp.get(c).equals("Assignment"))
			   				{
			   					c++;
			   					if(expMain.exp(cp, c))
			   					{
			   						c=expMain.count;
			   						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			 	  						{
			 	  							count_int=c;
			 	  							return true;
			 	  						}
			 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			 	  						{
			 	  							c=c-1;
			 	  							count_int=c;
			 	  							return true;
			 	  						}
			   							
			   					}
			   				}
			   			}
			   			else if(cp.get(c).equals("Assignment"))
			 			{
			   				
			 					c++;
			 					
			 					if(expMain.exp(cp, c))
			 					{
			 						c=expMain.count;
			 						//System.out.println(c+" Z:"+cp.get(c));
			 						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			 	  						{
			 	  							count_int=c;
			 	  							return true;
			 	  						}
			 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			 	  						{
			 	  							c=c-1;
			 	  							count_int=c;
			 	  							return true;
			 	  						}
			 							
			 					}
			 			}
			   			else if(cp.get(c).equals("Dot operator"))//if is object attribute;
			   			{
			   				c++;
			   				if(cp.get(c).equals("ID"))
			   		  		{
			   					//Here 2 c-2
			   					if(findclassIndex(vp.get(c-2))!=-1)
			   					{
			   						int ind=findclassIndex(vp.get(c-2));
			   						if(cp.get(c+1).equals("("))
			   						{
			   							for(int i=0;i<Syntaxtification.ct.functionslist.get(ind).nameFunction.size();i++)
			   								if(!Syntaxtification.ct.functionslist.get(ind).nameFunction.equals(vp.get(c)))
			   								{
			   									System.out.println("No Method found "+vp.get(c)+" at char number :" +c);
			   									return false;
			   								}
			   						}
			   						else if(!cp.get(c+1).equals("("))
			   						{
			   							for(int i=0;i<Syntaxtification.ct.Cvariable.get(ind).varTable.size();i++)
			   								if(!Syntaxtification.ct.Cvariable.get(ind).varTable.equals(vp.get(c)))
			   								{
			   									System.out.println("No field found "+vp.get(c)+" at char number :" +c);
			   									return false;
			   								}
			   						}
			   						
			   					}
			   					else if(findInnerclassIndex(vp.get(c-2))!=-1)
			   					{
			   						int ind=findInnerclassIndex(vp.get(c-2));
			   						if(cp.get(c+1).equals("("))
			   						{
			   							for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).functionslist.get(ind).nameFunction.size();i++)
			   								if(!Syntaxtification.ct.innerClassList.get(ClassIndex).functionslist.get(ind).nameFunction.equals(vp.get(c)))
			   								{
			   									System.out.println("No Method found "+vp.get(c)+" at char number :" +c);
			   									return false;
			   								}
			   						}
			   						else if(!cp.get(c+1).equals("("))
			   						{
			   							for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cvariable.get(ind).varTable.size();i++)
			   								if(!Syntaxtification.ct.innerClassList.get(ClassIndex).Cvariable.get(ind).varTable.equals(vp.get(c)))
			   								{
			   									System.out.println("No field found "+vp.get(c)+" at char number :" +c);
			   									return false;
			   								}
			   						}
			   					}
			   					c++;
			   					
			   		  			if(expMain.isOp(cp.get(c), c))
			   		  			{
			   		  				c++;
			   		  				if(cp.get(c).equals("Assignment"))
			   		  				{
			   		  					c++;
			   		  					if(expMain.exp(cp, c))
			   		  					{
			   		  						c=expMain.count;
			   		  						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   		  						{
			   		  							count_int=c;
			   		  							return true;
			   		  						}
			   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   		  						{
			   		  							c=c-1;
			   		  							count_int=c;
			   		  							return true;
			   		  						}
			   		  							
			   		  					}
			   		  				}
			   		  			}
			   		  			else if(cp.get(c).equals("Assignment"))
			   					{
			   							c++;
			   							if(expMain.exp(cp, c))
			   							{
			   								c=expMain.count;
			   								if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   		  						{
			   		  							count_int=c;
			   		  							return true;
			   		  						}
			   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   		  						{
			   		  							c=c-1;
			   		  							count_int=c;
			   		  							return true;
			   		  						}
			   									
			   							}
			   					}
			   		  			else if(cp.get(c).equals("["))//If Array exists
			   		  			{
			   		  				c++;
			   		  				String[] follow_for_exp_Assignment={"]"};
			   		  				ExpModel exp1 = new ExpModel(cp,vp,follow_for_exp_Assignment,false,scope);
			   		  				if(exp1.exp(cp, c))
			   		  				{
			   		  					c=exp1.count;
			   		  					if(cp.get(c).equals("]"))
			   		  					{
			   		  						c++;
			   		  					if(exp1.isOp(cp.get(c), c))
			   		  		  			{
			   		  		  				c++;
			   		  		  				if(cp.get(c).equals("Assignment"))
			   		  		  				{
			   		  		  					c++;
			   		  		  					if(expMain.exp(cp, c))
			   		  		  					{
			   		  		  						c=expMain.count;
			   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							c=c-1;
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   		  		  							
			   		  		  					}
			   		  		  				}
			   		  		  			}
			   		  		  			else if(cp.get(c).equals("Assignment"))
			   		  					{
			   		  							c++;
			   		  							if(expMain.exp(cp, c))
			   		  							{
			   		  								c=expMain.count;
			   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							c=c-1;
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   		  									
			   		  							}
			   		  					}
			   		  					}
			   		  					else if(cp.get(c-1).equals("]"))
			   		  					{
			   		  						
			   		  					if(exp1.isOp(cp.get(c), c))
			   		  		  			{
			   		  		  				c++;
			   		  		  				if(cp.get(c).equals("Assignment"))
			   		  		  				{
			   		  		  					c++;
			   		  		  					if(expMain.exp(cp, c))
			   		  		  					{
			   		  		  						c=expMain.count;
			   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							c=c-1;
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   		  		  							
			   		  		  					}
			   		  		  				}
			   		  		  			}
			   		  		  			else if(cp.get(c).equals("Assignment"))
			   		  					{
			   		  							c++;
			   		  							if(expMain.exp(cp, c))
			   		  							{
			   		  								c=expMain.count;
			   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
			   	  		  						{
			   	  		  							c=c-1;
			   	  		  							count_int=c;
			   	  		  							return true;
			   	  		  						}
			   		  									
			   		  							}
			   		  					}
			   		  					}
			   		  				}
			   		  			}
			   		  		}
			   			}
			   		}
			   		return false;
			   	}
			   	private  boolean Y(ArrayList<String>cp,int c)
			   	{
			   		String [] follow_for_exp={"Terminator"};
			   		ExpModel exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
			   		
			   		if(exp.exp(cp, c))
			   		{
			   			c=exp.count;
			   			if(!exp.returnType.equals("boolean"))
			   			{
			   				System.out.println("Expecting Boolean at char no: "+c);
			   				return false;
			   			}
			   			if(cp.get(c).equals("Terminator"))
			   			{
			   				c++;
			   				count_int=c;
			   				return true;
			   			}
			   			else if(cp.get(c-1).equals("Terminator"))
			   			{
			   				count_int=c;
			   				return true;
			   			}
			   		}
			   		return false;
			   	}
			   	private  boolean Z(ArrayList<String>cp,int c)
			   	{
			   		
			   		
			   		
			   		if(cp.get(c).equals("Incdec"))
			   		{
			   			c++;
			   			if(cp.get(c).equals("ID"))
			   			{
			   				if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
							{
								System.out.println("No variable found Error at line number:"+c+",,"+vp.get(c));
								return false;
							}
			   				c++;
			   				if(cp.get(c).equals(")"))
			   				{
			   					count_int=c;
			   					return true;
			   				}
			   			}
			   		}
			   		else if(cp.get(c).equals("ID"))
			 		{
			   			
			   			if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
						{
							System.out.println("No variable found Error at line number:"+c+",,"+vp.get(c));
							return false;
						}
			 				c++;
			 				
			 				if(cp.get(c).equals("Incdec"))
			 		  		{
			 		  			c++;
			 		  				if(cp.get(c).equals(")"))
			 		  				{
			 		  					count_int=c;
			 		  					return true;
			 		  				}
			 		  			
			 		  		}
			 				else if(Assign(cp,c-1,")".split("\\ "))) //Passed ID's index.
			 		  		{
			 					
			 		  			c=count_int;
			 		  			if(cp.get(c).equals(")"))
			 		  			{
			 		  				
			 		  				count_int=c;
			 		  				return true;
			 		  			}
			 		  		}
			 				
			 		}
			   		return false;
			   	}
			   	
	//DEclaration for loop
				class declarationModelSemanticFor 
				{
					KeyWords words = new KeyWords();
					public int count=0,count_int=0,failedCount=0;
					 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
						 ArrayList<String> varTable = new ArrayList();
					String follow_of_Parent;
					String follow_for_exp[]={"Terminator","]"};
					ExpModel exp ;
					int start=0,end,scope;
					String tempDT="";
					declarationModelSemanticFor(){}
				 public declarationModelSemanticFor(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						this.scope=scope;
						exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				 }
				 public declarationModelSemanticFor(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
				 {
						this.cp = cp;;
						this.vp = vp;
						follow_of_Parent=Follow;
						
						this.scope=scope;
						exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				 }
				 private boolean LookupVar(String name)
				 {
					 for(int i=0; i<tv.isVarArrayTable.size();i++)
					 {
						 if(name.contentEquals(tv.varTable.get(i)))
						 {
							 if(tv.varDT.get(i).equals("dig") || tv.varDT.get(i).equals("frac"))
							 return true;
					 
						 }
					 }
					// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
					 return false;
				 }
				private  boolean LookupVarP(String name)
				 {
					 for(int i=0; i<ptv.isVarArrayTable.size();i++)
					 {
						 if(name.contentEquals(ptv.varTable.get(i)))
						 {
							 if(ptv.varDT.get(i).equals("dig") || ptv.varDT.get(i).equals("frac"))
							 return true;
					 
						 }
					 }
					// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
					 return false;
				 }
				 private boolean lookUpClass(String name)
			 	 {
			 		if(Syntaxtification.ct.Cname.size()>0 )
			 		{
			 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
			 			if(Syntaxtification.ct.Cname.get(i).equals(name))
				 			 return true;
			 			
				 		 
				 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
				 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
				 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
				 		 			 return true;
				 		 
			 		}
			 		
			 		return false;
			 	 }
				 private boolean field(ArrayList<String> classPart,int count)
				 {
					 if(classPart.get(count).equals("datatype"))
					 {
						 
						 count++;
						
						if(classPart.get(count).equals("ID"))
						{
							if(LookupVar(vp.get(count)))
							{
								System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
								return false;
							}
							else if(LookupVarP(vp.get(count)))
							{
								System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
								return false;
							}
							else
							{
								if(!vp.get(count-1).equals("dig") && !vp.get(count-1).equals("frac")) 
								{
									System.out.println("Incompatible datatype:"+count+",,"+vp.get(count-1));
									return false;
								}
								tv.varTable.add(vp.get(count)); //Adding name
								tv.varScope.add(scope); //Adding Scope
								tv.varDT.add(vp.get(count-1)); //Adding DataType
								tempDT=vp.get(count-1);
								//Syntaxtification.varTable.add(vp.get(count));
							}
							count++;
							
							if(init(classPart,count))
							{
								count=count_int;
								
								if(list(classPart,count))
								{
									
									count=count_int;
									
									//System.out.println(count+"e"+classPart.get(count));
									if(classPart.get(count).equals("Terminator"))
									{
										count++;
										//this.count=count;
										count_int=count;
										exp.count_int=0;
										
										return true;
									}
										  
								}
								else if(classPart.get(count).equals("Terminator"))
								{
									count++;
									this.count=count;
									count_int=count;
									exp.count_int=0;
									
									return true;
								}
							}
							else if(list(classPart,count))
							{
								count=count_int;
								if(count>=classPart.size())
								{
									if(classPart.get(count-1).equals("Terminator"))
									{
										//this.count=count;
										count_int=count;
										exp.count_int=0;
										return true;
									}
								}
								if(classPart.get(count).equals("Terminator"))
								{
									
									count++;
									
									count_int=count;
									exp.count_int=0;
									//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
									return true;
								}
							}
							else if(classPart.get(count).equals("Terminator"))
							{
								count++;
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								return true;
							}
						}
					 }
					 return false;
				 }
				 public boolean decl(ArrayList<String> classPart,int count)
				 {
					
				
					 if(field(classPart,count))
					 {
						 	count=count_int;
						 	this.count=count;
							count_int=0;
							exp.count_int=0;	
							return true;
					 }
					 else if(cp.get(count).equals("ID"))
					 {
						 
								System.out.println("Incompatible datatype:"+count+",,"+vp.get(count));
								return false;
							
					 }
					 
					 return false;
				 }
				 boolean init(ArrayList<String> classPart,int count)
				 {
					 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
					    if(classPart.get(count).equals("Assignment"))
						{
					    	
							count++;
							tv.isVarArrayTable.add(false);
							if(exp.exp(classPart, count))
							{
								//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
								count_int=exp.count;
								if(!tempDT.equals(exp.returnType))
								{
									System.out.println("Invalid assignation "+vp.get(count)+" at char number: "+count);
								}
								count=count_int;
								count_int=count;
								return true;
							}
						}
						else if(array(classPart,count))
						{
							tv.isVarArrayTable.add(true);
							count=count_int;
							count_int=count;
							return true;
						}
						else if(classPart.get(count).equals(","))
						{
							tv.isVarArrayTable.add(false);
							count++;
							count_int=count;
							return true;
						}
					    tv.isVarArrayTable.add(false);
							return false;
				 }
				 boolean list(ArrayList<String> classPart,int count)
				 {
					 
					 //System.out.println(count+"e"+classPart.get(count));
					 if(classPart.get(count).equals(","))
					 {
						 count++;
						 if(classPart.get(count).equals("ID"))
							{
								if(LookupVar(vp.get(count)))
								{
									System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
									return false;
								}
								else
								{
									
									tv.varTable.add(vp.get(count)); //Adding name
									tv.varDT.add(vp.get(count-1)); //Adding DataType
									tv.varScope.add(scope); //Adding Scope
									tempDT=vp.get(count-1);
								}
								count++;
								if(init(classPart,count))
								{
									count=count_int;
									if(list(classPart,count))
									{
										count=count_int;
										count_int=count;
										return true;
									}
									
								}
								else if(list(classPart,count))
								{
									count=count_int;
									count_int=count;
									return true;
								}
							}
					 }
					 else if(classPart.get(count).equals("Terminator"))
					 {
						// count++;
						 count_int=count;
							return true;
					 }
					 return false;
				 }
				 
				 //Other models
				 boolean array(ArrayList<String> classPart,int count)
				 {
					 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
					 if(classPart.get(count).equals("Assign Op"))
					 {
						count++;
						//Syntaxtification.tv.isVarArrayTable.add(true);
						if(exp.exp(classPart, count))
						{
							if(!exp.returnType.equals("dig"))
							{
								System.out.println("Expecting integer value at char count: "+count);
								return false;
							}
							count_int=exp.count;
							count=count_int;
							count_int=count;
							return true;
						}
					 }
					 else if(classPart.get(count).equals("Assignment"))
					 {
						 count++;
						 if(classPart.get(count).equals("["))
						 {
							 count++;
							 if(words.isConst(classPart.get(count)))
							 {
								 if(!words.getConst(classPart.get(count)).equals(tempDT))
								 {
									 System.out.println("Expecting "+tempDT+" value at char count: "+count);
										return false; 
								 }
								 count++;
								 if(clist1(classPart,count))
								 {
									 count=count_int;
									 return true;
								 }
							 }
							 else if(classPart.get(count).equals("]"))
							 {
								 count=count_int;
									count_int=count;
								 return true;
							 }
						 }
					 }
					 return false;
				 }
				 boolean clist1(ArrayList<String> cp,int c)
				 {
				 	if(c>=cp.size()){ c=cp.size()-1; return true;}
				 	 if(cp.get(c).equals(","))			//Call to clist1
				 	 {
				 		 c++;
				 		 if(words.isConst(cp.get(c)))
				 		 {
				 			 if(!words.getConst(cp.get(c)).equals(tempDT))
							 {
								 System.out.println("Expecting "+tempDT+" value at char count: "+c);
									return false; 
							 }
				 			 c++;
				 			 if(clist1(cp,c))
				 			 {
				 				 c=count_int;
								 count_int=c;
								 return true;
							 
				 			 }
				 			 
				 		 }
				 	 }
				 	 else if(cp.get(c).equals("]"))
				 	 {
				 		c++;
						 count_int=c;
						 return true;
				 	 }
				 	return false;
				 } 
				 boolean Object(ArrayList<String> cp,int c)
				 {
					 
					 if(c>=cp.size())
					 {
						 return true;
					 }
					 
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
							{
								System.out.println("No class found Error at char number:"+c+",,"+vp.get(c));
								return false;
							}
							
						 
						 
						  c++;
						 if(cp.get(c).equals("ID"))
						 {
							
							 
							  	if(LookupVar(vp.get(c)))
								{
									System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
									return false;
								}
								else
								{
									tv.varTable.add(vp.get(c)); //Adding name
									tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
									tv.varScope.add(scope); //Adding Scope
									tempDT="class_"+vp.get(c-1);
								}
						  
							  	c++;
							
							 if(obj1(cp,c))
							 {
								 
								 c=count_int;
								 
								 if(Ob(cp, c))
								 {
									 
									 if(c>=cp.size())
									 {
										 if(cp.get(c-1).equals("Terminator"))
										 {
											 count_int=c;
											 return true;
										 }
									 }
									 else
									 {
										 if(cp.get(c-1).equals("Terminator"))
										 {
											 count_int=c;
											 return true;
										 }
										 else if(cp.get(c).equals("Terminator"))
										 {
											 c++;
											 count_int=c;
											 return true;
										 }
									 }
								 }
							 }
						 }
					 }
					 return false;
				 }
				 boolean LookUpClassParent(String child,String Parent)
				 {
					 int indexOfChild=0,i=0;
					 indexOfChild=indexOfChild(child);
					i= indexOfParent(Parent,indexOfChild);
					 if(i!=-1)
					 {
						 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
						 return true;
					 }
						
					
					 
					 return false;
				 }
				 int indexOfParent(String Parent,int indexOfChild)
				 {
					 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
					 {
						
						 return indexOfChild;
					 }
					 else
					 {
						 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
							 return -1;
						 else
						 {
							 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
							
						 }
									 
					 }
					 
				 }
				 int indexOfChild(String Child)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 {
						 if(Syntaxtification.ct.Cname.get(i).equals(Child))
						 {
							 return i;
						 }
					 }
					 return -1;
				 }
				 boolean obj1(ArrayList<String> cp, int c)
				 {
					 
					 if(cp.get(c).equals("Assignment"))
					 {
						 Syntaxtification.tv.isVarArrayTable.add(false);
						 c++;
						 if(cp.get(c).equals("new"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 
								 if(vp.get(c).equals(vp.get(c-4)))
									{
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
									}
								 else if(!vp.get(c).equals(vp.get(c-4)))
									{
									  
									 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
										{
											
										 if(exp.exp(cp, c))
										 {
											 c=exp.count;
											 count_int=c;
											return true;
										 }
											
											
											
										}
									 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
										{
											
											
												System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
												return false;
											
											
											
										}
									  else
									  {
										System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
										return false;
									  }
									}
									
							 }
							
						 }
						
					 }
					 else if(cp.get(c).equals("["))
					 {
						 
						 c++;
					
						 Syntaxtification.tv.isVarArrayTable.add(true);
						 if(words.isConst(cp.get(c)))
						 {
							 c++;
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 //Here init_ob
								 
								if(init_ob(cp,c))
								{
									c=count_int;
								
									 return true;
								}
							 }
						 }
						 else if(exp.exp(cp, c))
						 {
							 
							 c=exp.count;
							
							 count_int=c;
							 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
							 else
							 {
								 
								 if(cp.get(c).equals("]"))
								 {
									 c++;
									 //Here init_ob
									if(init_ob(cp,c))
									{
										c=count_int;
										 return true;
									}
								 }
								 else  if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 //Here init_ob
									if(init_ob(cp,c))
									{
										c=count_int;
										 return true;
									}
								 }
							 }
							
						 }
					 }
					 else if(cp.get(c).equals("Terminator"))
					 {
						 tv.isVarArrayTable.add(false);
						 c++;
						 count_int=c;
						 return true;
					 }
					 else  if(cp.get(c-1).equals("Terminator"))
					 {
						 tv.isVarArrayTable.add(false);
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals(","))
					 {
						 tv.isVarArrayTable.add(false);
						 c++;
						 count_int=c;
						 return true;
					 }
					 
					 return false;
				 }
				 boolean init_ob(ArrayList<String> cp,int c)
				 {
					if(c>=cp.size()) return true; 
					
					 if(cp.get(c).equals("Assignment"))
					 {
						 tv.isVarArrayTable.add(false);
						 c++;
						 if(cp.get(c).equals("new"))
						 {
							 c++;
							 
							 if(cp.get(c).equals("ID"))
							 {
								 if(vp.get(c).equals(vp.get(c-4)))
									{
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
									}
								 else if(!vp.get(c).equals(vp.get(c-4)))
									{
									  
									 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
										{
											
										 if(exp.exp(cp, c))
										 {
											 c=exp.count;
											 count_int=c;
											return true;
										 }
											
											
											
										}
									 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
										{
											
											
												System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
												return false;
											
											
											
										}
									  else
									  {
										System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
										return false;
									  }
									}
							 }
					
						 }
						 
					 }
					 else if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 count_int=c;
						 return true;
					 }
					 else  if(cp.get(c-1).equals("Terminator"))
					 {
						
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals(","))
					 {
						 c++;
						 count_int=c;
						 return true;
					 }
					 return false;
				 }
				 boolean Ob(ArrayList<String> cp,int c)
				 {
					 if(c>=cp.size())
					 {
						 return true;
					 }
					 else
					 {
						 if(cp.get(c).equals(","))
						 {
							 c++;
							 if(cp.get(c).equals("ID")) //obj1
							 {
								
									{
										tv.varTable.add(vp.get(c)); //Adding name
										tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
										tv.varScope.add(scope); //Adding Scope
									}
							  
								 c++;
								 if(obj1(cp,c))
								 {
									 c=count_int;
									 if(Ob(cp,c))
									 {
										 c=count_int;
										 if(cp.get(c).equals("Terminator"))
										 {
											 c++;
											 count_int=c;
											 return true;
										 }
									 }
								 }
									
							 }
						 }
						 else  if(cp.get(c).equals("Terminator"))
						 {
							 c++;
							 count_int=c;
						
							 
							 return true;
						 }
						 else  if(cp.get(c-1).equals("Terminator"))
						 {
							 
							 count_int=c;
						
							 
							 return true;
						 }
					 }
					 
						 return false;
				 }
				}

				//DECLARATION For SEMANTIC END


				 class ExpModel
					{
						KeyWords words = new KeyWords();
						public int count=0,count_int=0,FailedCounter=0,scope=0;
						 String follow_of_Parent[];
						 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
					
						 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
						 String returnType="",ChangeType="";
						
						 String leftOp="",rightOp="";
						 public boolean AllowAssignation=true;
						ExpModel(){}
						public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;

						}
						public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							follow_of_Parent=follow;
							
						}
						public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							AllowAssignation=allow;
							this.scope=scope;
						}
						public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							follow_of_Parent=follow;
							AllowAssignation=allow;
							
						}
						
					public 	boolean exp(ArrayList<String> ClassPart,int count)
						{
						
							if(isUniOp(ClassPart.get(count)))
							{
							
								startUniOp=true;
								count++;
								if(exp1(ClassPart,count))
								{
									count=count_int;
									this.count=count;
								
									return true;
								}
							}
							else if(exp1(ClassPart,count))
							{
								
								count=count_int;
								count_int=0;
								this.count=count;
								
								
								return true;
							}
							else if(words.isParentFollow(cp.get(count), follow_of_Parent))
							{
								count=count_int;
								this.count=count;
								
								return true;
							}
							if(FailedCounter<count)
								FailedCounter=count;
						
							return false;
						}

					boolean exp1(ArrayList<String> cp,int c)
					{
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
						
							if(cp.get(c).equals("ID"))
							{
								
								if(vp.get(c+1).equals("("))
								{
									String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
									functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
									fcm.follow_for_exp[0]=")";
									
									if(fcm.functionCall(cp, c))
									{
										
										String dt=getFuncReturnType(vp.get(c));
										String rdt=getFuncReturnType(vp.get(c));
										
										c=fcm.count-1;
										if(!opFlag) //No Left Operator Exists right now
										{
											
											 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
												{
											 		startUniOp=false;
														System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
														return false;
														
					
												}
												else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
												{
													startUniOp=false;
													if(dt.equals("boolean"))
													{
														returnType="boolean";
													}
													else 
													{
														System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
											else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
																leftOp=dt; //left operator
																opFlag=true;
																
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
											{
													System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
													return false;
													
				
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											
								
										}
										else if(opFlag) //Left Operator Exists
										{
											
											{
												if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
												{

													rightOp=rdt;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													leftOp=returnType;
													opFlag=true;
													
												}
												else //No more operators
												{

													
															
															if(!typeChanged)
															{
																
																
																	rightOp=rdt;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	
																	 
																
															}
															else if(typeChanged)
															{
																typeChanged=false;
																String temp=rdt;
																if(!compTypeCast(temp,ChangeType))
																{
																	System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																
																else
																{
																	rightOp=ChangeType;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	
																}
															}
															
														
													
												}
											}
											
										}
									
									}
									/*
									if(!lookupFunction(vp.get(c)))
									{
										System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									else
									{}
									*/
								}//Not function
								else //Maybe variable?
								{
									if(!lookUpVar(vp.get(c)))
									{
										System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									else
									{
										if(!opFlag) //No Left Operator Exists right now
										{
											String dt=getReturnTypeVar(vp.get(c));
											returnType=dt;
											 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
												{
												 startUniOp=false;
													if(dt.equals("dig") || dt.equals("frac"))
													{
														returnType="dig";
													}
													else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
													{
														System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
												else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
												{
													startUniOp=false;
													if(dt.equals("boolean"))
													{
														returnType="boolean";
													}
													else 
													{
														System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
														return false;
													}
														
												}
											else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
																leftOp=dt; //left operator
																opFlag=true;
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
											{
												if(dt.equals("dig") || dt.equals("frac"))
												{
													returnType="dig";
												}
												else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
												{
													System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(!vp.get(c+1).equals("."))
											{

													
													if(!typeChanged)
													{
													
															if(cp.get(c+1).equals("["))
															if(!isArrayType(vp.get(c)))
															{
																System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
																return false;
															}
													
													}
													else if(typeChanged)
													{
														typeChanged=false;
														
														if(!compTypeCast(dt,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
													
													}
														
													
												
											}
										}
										else if(opFlag) //Left Operator Exists
										{
											String rdt=getReturnTypeVar(vp.get(c));
											if(cp.get(c+1).equals("["))
											{
												if(!isArrayType(vp.get(c)))
												{
													System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
													return false;
												}
											}
											else
											{
												if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
												{
												
													
													rightOp=rdt;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													leftOp=returnType;
													opFlag=true;
													
												}
												else //No more operators
												{
															if(!typeChanged)
															{
																
																
																	rightOp=rdt;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
															
															}
															else if(typeChanged)
															{
																typeChanged=false;
																String temp=rdt;
																if(!compTypeCast(temp,ChangeType))
																{
																	System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																
																else
																{
																	rightOp=ChangeType;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	
																}
															}
															
														
												
												}
											}
											
										}
									}
									//....................................................................................................
									/*if(listcounter<paramsList.size())
									{
										if(!lookUpVar(vp.get(c)))
										{
											System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										else
										{
											if(!typeChanged)
											{
												if(!getReturnTypeVar(vp.get(c)).equals(paramsList.get(listcounter)))
												{
													System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												else
												{
													if(cp.get(c+1).equals("["))
													if(!isArrayType(vp.get(c)))
													{
														System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													listcounter++;
												}
											}
											else if(typeChanged)
											{
												typeChanged=false;
												String temp=getReturnTypeVar(vp.get(c));
												if(!compTypeCast(temp,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												else if(!ChangeType.equals(paramsList.get(listcounter)))
												{
													System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												else
													listcounter++;
											}
											
										}
									}
									else
									{
										System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									*/
								}
								c++;
								
								if(exp21(cp,c))
								{
									c=count_int;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								
								if(!opFlag) //No Left Operator Exists right now
								{
									
									 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
										{
										 startUniOp=false;
										 
											if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
											{
												returnType="dig";
											}
											else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
										{
											
											startUniOp=false;
											
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											
												
										}
									else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=words.getConst(cp.get(c)); //left operator
														opFlag=true;
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
										if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
										{
											returnType="dig";
										}
										else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(words.getConst(cp.get(c)).equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else
									{

											
												if(!typeChanged)
												{
													returnType=words.getConst(cp.get(c));
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=words.getConst(cp.get(c));
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													
													else
														{
														returnType=ChangeType;
														}
												}
												
											
										
									}
								}
								else if(opFlag) //Left Operator Exists
								{
									if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
									{

										rightOp=words.getConst(cp.get(c));
										returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
										if(returnType.equals("x"))
										{
											System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										leftOp=returnType;
										opFlag=true;
										
									}
									else //No more operators
									{

												if(!typeChanged)
												{
														rightOp=words.getConst(cp.get(c));
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
													
														 
													
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=words.getConst(cp.get(c));
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													
													else
													{
														rightOp=ChangeType;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														
													}
												}
												
									
									}
								}
								c++;
								
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
							}
							else if(cp.get(c).equals("("))
							{
								c++;
								if(news(cp,c))
								{
								
									c=count_int;
									return true;
								}
							}
							if(FailedCounter<c)
							FailedCounter=c;
							return false;
					}
					boolean compTypeCast(String func,String Modified)
					{
						if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
						{
							if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
								return true;
							
						}
						else if(Modified.equals("beta"))
						{
							
							if(func.equals("beta"))
								return true;
						}
						
						return false;
					}
					boolean exp2(ArrayList<String>  cp,int c)
					{
						
							if(cp.size()<=c){count_int=cp.size()-1; return true; } 
							else
							{
								if(isOp(cp.get(c),c))
								{
									//..........
									c++;
									if(exp1(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals(")"))
								{
									count_int=c;
									return true;			
								}
								else if(words.isParentFollow(cp.get(c),follow_of_Parent))
								{

									count_int=c;
									return true;
											
								}
								else if(cp.get(c).equals(",") && !ifParams)
								{
									count_int=c;
									
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									count_int=c;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean exp21(ArrayList<String>  cp,int c)
						{
							if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
							
							if(exp2(cp,c))
							{
								c=count_int;
								
								return true;
							}
						
							else if(cp.get(c).equals("["))
							{
								
								c++;
								
								if(words.isConst(cp.get(c)))
								{
									
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;	
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals("ID"))
								{

									if(vp.get(c+1).equals("("))
									{
										if(!lookupFunction(vp.get(c)))
										{
											System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
									}//Not function
									else //Maybe variable?
									{
										
											if(!lookUpVar(vp.get(c)))
											{
												System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
										
									}
									c++;
									
									if(exp1(cp,c))
									{
										
										c=count_int;
								
											//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
										if(cp.get(c).equals("]"))
										{
											
											c++;
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals("]"))
										{
											
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							
							else if(O1(cp,c))
							{
								
								c=count_int;
								if(exp2(cp,c))
								{
									
									c=count_int;
									count_int=c;
								
									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										count_int=c;

										if(c>=cp.size()){c=count_int=cp.size()-1;}
										return true;
									}
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean params(ArrayList<String>  cp,int c)
						{
						
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
							ifParams=true;

							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								
								ifParams=false;
								return true;
							}
							else if(exp1(cp,c))
							{
								
								c=count_int;
								//System.out.println(cp.get(c)+":exp1 of params"+c);
								if(c<cp.size())
								{
									
									if(cp.get(c).equals(")"))
									{
										c++;
										count_int=c;
										//System.out.println(cp.get(c)+":dF"+c);
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("___"+cp.get(c));
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals(",")) //what if datatype?
									{
										
										c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(cp.get(c).equals("ID"))
											 {
												 c++;
												 if(params(cp,c))
												 {
														
														c=count_int;
														ifParams=false;
														return true;
											     }
											 }
										 }
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
								
									c=count_int;
									ifParams=false;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean news(ArrayList<String>  cp,int c)
						{
							
						if(isUniOp(cp.get(c)))
						{
							c++;
							if(exp1(cp,c))
							{
								
								c=count_int; 
								
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
							}
						
						}
						else if(exp1(cp,c))
							{
								
								c=count_int; 
								//System.out.println(c+":33:"+cp.get(c));
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
									{
									   c=count_int;	
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									{
										//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
									   c=count_int;	
										return true;
									}
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							
							}
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c).equals("datatype"))
							{
								ChangeType=vp.get(c);
								typeChanged=true;
								c++;
								if(cp.get(c).equals(")"))
								{
									
									c++;
									if(exp1(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										return true;
									}
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean O1(ArrayList<String>  cp,int c) // Incomplete
						{
							if(cp.get(c).equals("Dot operator"))
							{
								
								c++;
								
								if(cp.get(c).equals("ID"))
								{
									
								
									if(exp1(cp,c))
									{
										c=count_int;
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
								 if(cp.get(c).equals("["))
									{
										
										c++;
										//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
										if(words.isConst(cp.get(c)))
										{
											c++;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count_int=c;
												//System.out.println("333,"+c+","+cp.get(c));
												return true;
											}
										}
									}
									else if(cp.get(c).equals("&"))
									{
										c++;
										count_int=c;
										return true;
									}
									else if(cp.get(c).equals("Dot operator"))
									{
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
									else if(cp.get(c).equals(")"))
									{
									
										c=count_int=c;
										if(ifParams)
										ifParams=false;
										return true;
									}
								}
								else if(words.isConst(cp.get(c)))
								{
									c++;
									count_int=c;
									return true;
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					String OperationOnOperands(String left,String right,String op,int cpInd)
					{
						if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
						{
							if(left.equals("dig"))
							{
								if(right.equals("dig"))
									return "dig";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "dig";
								
							}
							else if(left.equals("frac"))
							{
								if(right.equals("dig"))
									return "frac";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "frac";
								
							}
							else if(left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "alpha";
								else if(right.equals("frac"))
									return "alpha";
								else if(right.equals("alpha"))
									return "alpha";
								
							}
							
						}
						else if(op.equals("suma"))
						{
							if(left.equals("dig"))
							{
								if(right.equals("dig"))
									return "dig";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "dig";
								
							}
							else if(left.equals("frac"))
							{
								if(right.equals("dig"))
									return "frac";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "frac";
								
							}
							else if(left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "alpha";
								else if(right.equals("frac"))
									return "alpha";
								else if(right.equals("alpha"))
									return "alpha";
								
							}
							else if(left.equals("beta"))
							{
								if(right.equals("dig"))
									return "beta";
								else if(right.equals("frac"))
									return "beta";
								else if(right.equals("alpha"))
									return "beta";
								
								
							}
							
						}
						else if(cp.get(cpInd).equals("RO"))
						{
							if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "boolean";
								else if(right.equals("frac"))
									return "boolean";
								else if(right.equals("alpha"))
									return "boolean";
								
							}
							
						}
						else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
						{
							return "boolean" ;
						}
						return "x";
					}
					String valueOp(int index)
						{
							
							return vp.get(index);
						}
				
					boolean isOp(String cp,int c)
						{
							
							if(cp.equals("RO"))//
							{
								 return true;
							}
							else if(cp.equals("Assignment") && AllowAssignation)
							{
								return true;
							}
							else if(cp.equals("BWOP"))
							{
								if(valueOp(c).equals("bitOr"))
								 return true;
							}
							else if(cp.equals("BWOP"))
							{
								if(valueOp(c).equals("bitAnd"))
								 return true;
							}
							else if(cp.equals("OR"))
							{
								 return true;
							}
							else if(cp.equals("AddSum"))
							{
								 return true;
							}
							else if(cp.equals("Mux"))
							{
								
								 return true;
							}
							else if(cp.equals("DIVMOD"))
							{
								 return true;
							}
							else if(cp.equals("Incdec") || cp.equals("Not operator"))
							{
								 return true;
							}
							else if(cp.equals("LO"))
							{
								 return true;
							}

							
							
							return false;
						}
						
					boolean isUniOp(String a)
						{
							if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
							{
								
								return true;

							}
							
							return false;
						}
					
					
					
					 //<-- Finding functionExist or Not?
					 private boolean lookupFunction(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 						return true;
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return true;
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
						 					return true;
						 			} 
					 		}
					 		
					 		return false;
					 }

					private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						int i= indexOfParent(Parent,nameFunction);
						 if(i!=-1) 
							 return true;
						 return false;
					 }
					 private int indexOfParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
									 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
										 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
										 	return i;
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										return k;
								 }
							 }
							
						 return -1;
					 }
					
			//Finding functionExist or Not? -->
					 
			//<-- Getting paramList
					 private ArrayList<String> getFuncParams(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 					{
						 						//for(int p=0;p<tFunction.paramList.size();i++)
						 							
						 						return tFunction.paramList.get(i);
						 					}
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				
						 					return getParamsFunctionin_ClassParent(name,CurrentPname);
						 			} 
					 		}
					 		
					 		return null;
					 }

					private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						return getParamsParent(Parent,nameFunction);
						 
					 }
					 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
										 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
											 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										
								 }
							 }
							
						 return null;
					 }
					
			//Getting paramList  -->	
					 
			//<-- Getting returnType
					 private String getFuncReturnType(String name)
					 {
						 //1)CurrentClass--tFunction
						 	if(Syntaxtification.ct.Cname.size()>0 )
						 	{
						 			if(tFunction.nameFunction.size()>0)
						 				for(int i=0;i<tFunction.nameFunction.size();i++)
						 				{
						 					if(tFunction.nameFunction.get(i).equals(name))
						 						return tFunction.ReturnType.get(i);
						 				}
							 //TopHierarchical CLass
						 			if(isInner)
						 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
						 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
						 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
						 			
							 //ParentClass of Current Class
						 			if(!CurrentPname.equals("-"))
						 			{
						 				
						 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
						 			} 
					 		}
					 		
					 		return null;
					 }

					private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
					 {
						return getReturnTypeParent(Parent,nameFunction);
						 
					 }
					 private String getReturnTypeParent(String Parent,String nameFunction)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
								
										 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
											 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
										
								 }
							 }
							
						 return null;
					 }
					
			//Getting returnType  -->	
					private boolean lookUpVar(String name)
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return true;
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return true;
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				if(LookUpVarin_ClassParent(name,CurrentPname))
				 					return true;
				 			} 
				 		 return false;
				 	 }
					private boolean LookUpVarin_ClassParent(String name,String Parent)
					 {
						int i= VarindexOfParent(Parent,name);
						 if(i!=-1) 
							 return true;
						 return false;
					 }
					private int VarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return i;
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
										return k;
								 }
							 }
							
						 return -1;
					 }

					private String getReturnTypeVar(String name) //Method to call
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return tv.varDT.get(i);
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				return returnTypeVarin_ClassParent(name,CurrentPname);
				 					
				 			} 
				 		 return null;
				 	 }
					private String returnTypeVarin_ClassParent(String name,String Parent)
					 {
						return returnTypeVarindexOfParent(Parent,name);
						
					 }
					private String returnTypeVarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
										
								 }
							 }
							
						 return null;
					 }

					private boolean isArrayType(String name) //Method to call
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return (tv.isVarArrayTable.get(i));
				 				 
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
				 					
				 			} 
				 		 return false;
				 	 }
					private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
					 {
						return isArrayTypeVarindexOfParent(Parent,name);
						
					 }
					private boolean isArrayTypeVarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
										
								 }
							 }
							
						 return false;
					 }

					}

	//Exp for for
			 }
			 
		//For Loop Semantic End
			 

	//Interfaces Semantic Start
		
		class InterfaceModel {
			private KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 private ArrayList<String> cp = new ArrayList();
			 private ArrayList<String> vp = new ArrayList();
				 private String follow_of_Parent[];
				 private String follow_for_exp[]={")","["};
				 private	String follow_for_body[]={"]"};
				 private	ExpModel exp ;
				 private interfaceBodyModel body1;
				 private InterfaceModel(){}
			 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope+1);
					this.scope=scope;
					body1=new interfaceBodyModel(cp,vp,follow_for_body);
					follow_of_Parent=null;
			 }
			 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope+1);
					//body=new BodyModel(cp,vp,follow_for_body);
					body1=new interfaceBodyModel(cp,vp,follow_for_body);
					
			 }
			
			 public boolean interfaceFunction(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("acces modifier"))
				 {
					
					 c++;
					 
					 if(cp.get(c).equals("type modifier"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("sculpture"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 if(!lookUpClass(vp.get(c)))
								 {
									 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
									 {
										 tI.isPrivate.add(true);
										 tI.isStable.add(true);
										 tI.nameInterfaces.add(vp.get(c));
										 
									 }
									 else
										 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
								 }
								 else
									 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
								 c++;
								 if(S1(cp,c)) //Confirmation of params - (
								 {
									 c=count_int;
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 
											 if(body1.InternBody(cp,c))
											 {
												c=body1.count;
												
												if(cp.get(c).equals("]"))
												{
													c++;
													count=count_int=c;
													return true; //Successful parsed.
													
												}
												if(failedCount<c)
										   			failedCount=c;
											   	
											 }
											 if(failedCount<c && body1.failedCount<c)
										   			failedCount=c;
											   	else if(body1.failedCount>c)
											   			failedCount=body1.failedCount;
										 }
										 if(failedCount<c)
									   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
							   	
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }//if stable not present.
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookUpClass(vp.get(c)))
							 {
								 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
								 {
									 tI.isPrivate.add(true);
									 tI.isStable.add(false);
									 tI.nameInterfaces.add(vp.get(c));
									 
								 }
								 else
									 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
							 }
							 else
								 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }	
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 } // if access modifier not present
				 else if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookUpClass(vp.get(c)))
							 {
								 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
								 {
									 tI.isPrivate.add(false);
									 tI.isStable.add(true);
									 tI.nameInterfaces.add(vp.get(c));
									 
								 }
								 else
									 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
							 }
							 else
								 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
			
				 }//if stable+access modifier not present.
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
						 {
							 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
							 {
								 tI.isPrivate.add(false);
								 tI.isStable.add(false);
								 tI.nameInterfaces.add(vp.get(c));
								 
							 }
							 else
								 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
						 }
						 else
							 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				
				 }

				
				 return false;

			 }
			 private boolean S1_List(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals(","))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
							 System.out.println("No interface found Error "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1_List(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						 
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("inheritance"))
				 {
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
				 return false;
			 }
			 private boolean S1(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("interface"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
							 System.out.println("No interface found "+ vp.get(c)+ " at char number: "+c);
						 
						 c++;
						 if(S1_List(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("inheritance"))
							 {
								 c++;
								 if(cp.get(c).equals("ID"))
								 {
									 if(!lookUpClass(vp.get(c)))
										 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
									 c++;
									 if(cp.get(c).equals("["))
									 {
										 count_int=c;
										 return true;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 else if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("inheritance"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
							 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(cp.get(c).equals("["))
						 {
							 count_int=c;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 return false;
			 }
		}
		
	//Interfaces Semantic End
		
	//Interface Body Semantic Start
		
		
		
		
		class interfaceBodyModel {
			private KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0;
			tableFunction interfaces_tf = new tableFunction();
			 private ArrayList<String> cp = new ArrayList();
			 private ArrayList<String> vp = new ArrayList();
			 private tableFunction tf = new tableFunction();
				 private String follow_of_Parent[];
				 private String follow_for_exp[]={")","["};
				 private interfaceBodyModel(){}
			 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=null;
			 }
			 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;			
			 }
			 public boolean InternBody(ArrayList<String> cp,int c)
			 {
				 if(multiprototypes(cp,c))
				 {
					count=c=count_int;
					interfaces_tf.paramList.add(getParams());
					
					return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 return false;
			 }
			 private boolean multiprototypes(ArrayList<String>cp,int c)
			 {
				 if(prototypeFun(cp,c))
				 {
					c=count_int;
					if(multiprototypes(cp,c))
					 {
						c=count_int;
						return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 return false;
			 }
			 private boolean prototypeFun(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 tf.isPrivate.add(false);
						 tf.isStable.add(false);
						 tf.ReturnType.add(vp.get(c-1));
						 tf.nameFunction.add(vp.get(c));
						 tf.varScope.add(scope);
						 c++;
						 
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 c++;
							 
							 if(params(cp, c))
							 {
								 
								 c=count_int;
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									count_int=c;
									return true;
									 
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 return false;
			 }
			 private  ArrayList<String> params = new ArrayList();	
			 private ArrayList<String> getParams()
			 {
				 return params;
			 }
			 private void SetParams(String param)
			 {
				 params.add(param);
			 }
			 private tableVar tv = new tableVar();
			 private boolean lookUpVar(String name)
		 	 {
				 if(tv.varDT.size()>0)
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
		 		 return false;
		 	 }
			 private boolean params(ArrayList<String> cp,int c)
			 {
				 if(fields(cp,c))
				 {
					 c=count_int;
					 
					 return true;
				 }
				 else if(Object(cp, c))
				 {
					 c=count_int;
						count_int=c;
						return true;
				 }
				 else if(cp.get(c).equals(")"))
				 {
					 count_int=++c;
					 
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
				 return false;
			 }
			 private boolean list(ArrayList<String> classPart,int count)
			 {
				 
				 if(classPart.get(count).equals(","))
				 {
					 count++;
					 if(cp.get(count).equals("datatype"))
					 {
						 count++;
						 if(classPart.get(count).equals("ID"))
							{
							 if(!lookUpVar(vp.get(count)))
								{
									tv.varTable.add(vp.get(count));
									tv.varDT.add(vp.get(count-1));
									tv.varScope.add(scope);
									tv.isVarArrayTable.add(false);
									SetParams(vp.get(count-1));
								}
								else
									System.out.println("Redeclaration of variable "+vp.get(count)+" at char number: "+count);
								count++;
								
								if(list(classPart,count))
								{
									count=count_int;
									count_int=count;
									//System.out.println(count+":rr:"+cp.get(count));
									return true;
								}
								/*else
								{
									
									count--;
									//System.out.println(count+":rr:"+cp.get(count));
									if(Object(classPart, count))
									 {
										 count=count_int;
											count_int=count;
											
											return true;
									 }
								}*/
								if(failedCount<count)
						   			failedCount=count;
							}
						 if(failedCount<count)
					   			failedCount=count;
					 }
					 else if(Object(classPart, count))
					 {
						 count=count_int;
							count_int=count;
							
							return true;
					 }
					 if(failedCount<count)
				   			failedCount=count;
					 
				 }
				 else if(classPart.get(count).equals(")"))
				 {
					 count_int=count;
						return true;
				 }
				 if(failedCount<count)
			   			failedCount=count;
				   
				 return false;
			 }
			 private boolean Object(ArrayList<String> cp,int c)
			 {
				 if(c>=cp.size())
				 {
					 return true;
				 }
				
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
							 {
								 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
							 }
						 c++;
						
							 if(Ob(cp, c))
							 {
								c=count_int;
								 if(c>=cp.size())
								 {
									 if(cp.get(c-1).equals(")"))
									 {
										 count_int=c;
										 return true;
									 }
									 if(failedCount<c)
								   			failedCount=c;
									 
								 }
								 else
								 {
									 
									 if(cp.get(c-1).equals(")"))
									 {
										 count_int=c;
										 return true;
									 }
									 else if(cp.get(c).equals(")"))
									 {
										 
										 count_int=c=c+1;
										 
										 return true;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
				 return false;
			 }
			 private boolean Ob(ArrayList<String> cp,int c)
			 {
				 if(c>=cp.size())
				 {
					 return true;
				 }
				 else
				 {
					 
					 if(cp.get(c).equals(","))
					 {
						 c++;
						
						 if(cp.get(c).equals("ID")) //obj1
						 {
							 c++;
							 
							if(cp.get(c).equals("ID"))
							{
								if(lookUpClass(vp.get(c-1)))
			 				 	{
			 				 		if(!lookUpVar(vp.get(c)))
			 						 {
			 							 
			 							 tv.varTable.add(vp.get(c));
			 							 tv.varScope.add(scope);
			 							 tv.varDT.add("class_"+vp.get(c-1));
			 							 SetParams(vp.get(c-1));
			 						 }
			 						 else
			 						 {
			 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
			 						 }
			 				 	}
			 				 	 else
		 						 {
		 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
		 						 }
								c++;
							
								 if(Ob(cp,c))
								 {
									 c=count_int;
									 if(cp.get(c).equals(")"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
									 else
										 if(cp.get(c-1).equals(")"))
										 {
											 
											 count_int=c;
											 return true;
										 }
											
								
									 
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 
								
							}
							if(failedCount<c)
					   			failedCount=c;
						 }
						 else if(cp.get(c).equals("datatype"))
						 {
						
							 if(fields(cp, c))
							 {
								 c=count_int;
									count_int=c;
									return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else  if(cp.get(c).equals(")"))
					 {
						 c++;
						 count_int=c;
						 
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
					 return false;
			 }

			 private boolean fields(ArrayList<String> cp,int c)
			 {
				 if(cp.get(c).equals("datatype"))
				 {
					 c++;
					
					if(cp.get(c).equals("ID"))
					{
						
						if(!lookUpVar(vp.get(c)))
						{
							tv.varTable.add(vp.get(c));
							tv.varDT.add(vp.get(c-1));
							tv.varScope.add(scope);
							tv.isVarArrayTable.add(false);
							SetParams(vp.get(c-1));
						}
						else
							System.out.println("Redeclaration of variable "+vp.get(c)+" at char number: "+c);
						c++;
						
						 if(list(cp,c))
						{
							c=count_int;
							
							if(c>=cp.size())
							{
								if(cp.get(c-1).equals(")"))
								{
									
									count_int=c;
									
									return true;
								}
								if(failedCount<c)
						   			failedCount=c;
							}
							else if(cp.get(c).equals(")"))
							{
								c++;
								
								count_int=c;

								return true;
							}
							else if(cp.get(c-1).equals(")"))
							{

								count_int=c;

								return true;
							}
							if(failedCount<c)
					   			failedCount=c;
						}
						 if(failedCount<c)
					   			failedCount=c;
					}
					if(failedCount<c)
			   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 
				 return false;
			 }
		}
	//Interface Body Semantic End
		
	//Simple Body Semantic Start
		class BodyModel {
			KeyWords words = new KeyWords();
			int count=0,count_int=0,scope=0;
			public int failedCount=0;
			public boolean isSomethingOpen=false;
			ArrayList<String> cp = new ArrayList();
			ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[]; //Usually ]
			String follow_for_exp[]={"Terminator"};
			ExpModel exp ;
			declarationModelSemanticBody decl;
			tableVar tv = new tableVar();
			functionCallModel functCall;
			BodyModel(){}
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope,tableVar ptv)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				decl = new declarationModelSemanticBody(cp,vp,scope);
				functCall= new functionCallModel(cp,vp,scope);
				this.tv=ptv;
		 }
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,tableVar ptv)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				decl = new declarationModelSemanticBody(cp,vp,scope);
				this.tv=ptv;
				functCall= new functionCallModel(cp,vp,scope);
		 }
		 public boolean getIsSomeThingOpen()
		 {
			 return isSomethingOpen; 
		 }
		 public void setIsSomeThingOpen(boolean tf)
		 {
			 isSomethingOpen=tf; 
		 }
		 public boolean body(ArrayList<String> cp,int c)
		 {

			 if(m_mst(cp,c))
			 {
				 c=count_int;
				 count=c;
				 //System.out.println(c+"body Passed: "+cp.get(c));
				 return true;
			 }
			 return false;
		 }
		 boolean m_mst(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 //Declaration can occur
				int temp=c;
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					// System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				// System.out.println("Declaration Failed at Line number: "+temp);
				 if(failedCount<c && c< decl.failedCount)
			   			failedCount=decl.failedCount;
				 else if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("ID"))
			 {
				 //Declaration can occur
				int temp=c;
				int check=0;
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					// System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				else if(functCall.functionCall(cp,c))
				 {
					 c=functCall.count;
					// System.out.println("Function Calling Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
			//		 System.out.println("Expression Passed at Line number: "+temp);
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					 {
						
						 count_int=c;
						 return true;
					 }
					 else 
					 {
						 
						 if(m_mst(cp,c))
						 {
							 
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 
					check++; 
				 }
				 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					 
					 count_int=c;
					 return true;
				 }
				 /*if(exp.FailedCounter>decl.failedCount && exp.FailedCounter> functCall.failedCount && exp.FailedCounter>c && exp.FailedCounter> failedCount)
				 {
					 failedCount=exp.FailedCounter;
					// System.out.println("Invalid expression: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter<decl.failedCount && decl.failedCount> functCall.failedCount && c<decl.failedCount && failedCount< decl.failedCount)
				 {
					 failedCount=decl.failedCount;
					 //System.out.println("Invalid Declaration at line number: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter< functCall.failedCount && decl.failedCount< functCall.failedCount && c<functCall.failedCount && failedCount< functCall.failedCount)
				 {
					 failedCount= functCall.failedCount;
					// System.out.println("Invalid FunctionCalling at line number: "+(failedCount+1));
				 }
				 else*/ if(failedCount<c)
				 {
					 failedCount=c;
				 }
					 
			 }
			 else if(cp.get(c).equals("si")) //if-else
			 {
				int temp=c;
				 IfElseModel ifelse;
				 ifelse = new IfElseModel(cp,vp,scope);
				 int check=0;
				 if(ifelse.ifElse(cp,c))
				 {
					 c=ifelse.count;
					 check=1;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
					
				 }
				
				 if(failedCount<c && c> ifelse.failedCount)
			   			failedCount=c;
				 else if(failedCount<ifelse.failedCount && c< ifelse.failedCount)
				 {
					 failedCount=ifelse.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("check")) //switch
			 {
				 int temp=c;
				 boolean check=false;
				 SwitchModel switch1 =new SwitchModel(cp, vp,scope);
				 if(switch1.Switch(cp,c))
				 {
					 c=switch1.count;
					// System.out.println("Switch Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
				 }
			
				 if(failedCount<c && c> switch1.failedCount)
			   			failedCount=c;
				 else if(failedCount<switch1.failedCount && c< switch1.failedCount)
				 {
						failedCount=switch1.failedCount;
						 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("for loop")) //for Loop
			 {
				 int temp=c;
				 boolean check=false;
				 
				 ForModel to = new ForModel(cp,vp,scope,tv);
				 if(to.For(cp,c))
				 {
					 c=to.count;
					// System.out.println("For Loop Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> to.failedCount)
			   			failedCount=c;
				 else if(failedCount<to.failedCount && c< to.failedCount)
				 {
					 failedCount=to.failedCount;
					 isSomethingOpen=true;
			   	}
			 }
			 else if(cp.get(c).equals("while loop")) //while Loop
			 {
				 boolean check=false;
				 int temp=c;
				 WhileModel w=new WhileModel(cp,vp,scope);
				 if(w.test(cp,c))
				 {
					 c=w.count;
				//	 System.out.println("While Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> w.failedCount)
			   			failedCount=c;
				 else if(failedCount<w.failedCount && c< w.failedCount)
				 {
					 failedCount=w.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("break")) //break
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
				//	 System.out.println("Break Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("continue")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Continue Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("return")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 //System.out.println("Return Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						// System.out.println("Expression Passed at Line number: "+temp);
						 check=true;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 else if(failedCount<c)
							 failedCount=c;
					 }
				 }
				 if(!check)
				 {
					// System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(exp.exp(cp, c)) //Exp
			 {
				 boolean check=false;
				 c=exp.count;
				 
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Expression Passed after at Line number: "+c);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 else if(cp.get(c-1).equals("Terminator"))
				 {
					// System.out.println("Expression Passed after at Line number: "+(c-1));
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					// System.out.println("Terminator missing at Line number: "+c);
					 if(failedCount<c)
						 failedCount=c;
				 }
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 
				 count_int=c;
				 return true;
			 } 
			
			 if(failedCount<c)
				 failedCount=c;
			 if(exp.FailedCounter>c)
			 {
				// System.out.println("invalid exp at Line number: "+exp.FailedCounter);
				 failedCount=exp.FailedCounter;
			 }
			 else
			 {
				// System.out.println(failedCount+"Missing at Line number: "+c);
				 if(failedCount<c)
					 failedCount=c;
			 }
			 
			 return false;
		 }
		 

			//DECLARATIONSemanticBody START

			class declarationModelSemanticBody 
			{
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
					 ArrayList<String> varTable = new ArrayList();
				String follow_of_Parent;
				String follow_for_exp[]={"Terminator","]"};
				ExpModel exp ;
				int start=0,end,scope;
				String tempDT="";
				declarationModelSemanticBody(){}
			 public declarationModelSemanticBody(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
			 }
			 public declarationModelSemanticBody(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
			 {
					this.cp = cp;;
					this.vp = vp;
					follow_of_Parent=Follow;
					
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
			 }
			 private boolean LookupVar(String name)
			 {
				 for(int i=0; i<tv.isVarArrayTable.size();i++)
				 {
					 if(name.contentEquals(tv.varTable.get(i)))
					 {
						 //System.out.println("Class_"+tv.varTable.get(i));
						 return true;
				 
					 }
				 }
				// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
				 return false;
			 }
			 private boolean lookUpClass(String name)
		 	 {
		 		if(Syntaxtification.ct.Cname.size()>0 )
		 		{
		 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
		 			if(Syntaxtification.ct.Cname.get(i).equals(name))
			 			 return true;
		 			
			 		 
			 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
			 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
			 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
			 		 			 return true;
			 		 
		 		}
		 		//System.out.println(name);
		 		return false;
		 	 }
			 private boolean field(ArrayList<String> classPart,int count)
			 {
				 if(classPart.get(count).equals("datatype"))
				 {
					 
					 count++;
					
					if(classPart.get(count).equals("ID"))
					{
						if(LookupVar(vp.get(count)))
						{
							System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
							return false;
						}
						else
						{
							tv.varTable.add(vp.get(count)); //Adding name
							tv.varScope.add(scope); //Adding Scope
							tv.varDT.add(vp.get(count-1)); //Adding DataType
							tempDT=vp.get(count-1);
							//Syntaxtification.varTable.add(vp.get(count));
						}
						count++;
						
						if(init(classPart,count))
						{
							count=count_int;
							
							if(list(classPart,count))
							{
								
								count=count_int;
								
								//System.out.println(count+"e"+classPart.get(count));
								if(classPart.get(count).equals("Terminator"))
								{
									count++;
									//this.count=count;
									count_int=count;
									exp.count_int=0;
									
									return true;
								}
									  
							}
							else if(classPart.get(count).equals("Terminator"))
							{
								count++;
								this.count=count;
								count_int=count;
								exp.count_int=0;
								
								return true;
							}
						}
						else if(list(classPart,count))
						{
							count=count_int;
							if(count>=classPart.size())
							{
								if(classPart.get(count-1).equals("Terminator"))
								{
									//this.count=count;
									count_int=count;
									exp.count_int=0;
									return true;
								}
							}
							if(classPart.get(count).equals("Terminator"))
							{
								
								count++;
								
								count_int=count;
								exp.count_int=0;
								//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
								return true;
							}
						}
						else if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
				 }
				 return false;
			 }
			 public boolean decl(ArrayList<String> classPart,int count)
			 {
				
			
				 if(field(classPart,count))
				 {
					 	count=count_int;
					 	this.count=count;
						count_int=0;
						exp.count_int=0;	
						return true;
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
					 this.count=count;
						count_int=0;
						exp.count_int=0;
						
						return true;
				 }
				 
				 return false;
			 }
			 boolean init(ArrayList<String> classPart,int count)
			 {
				 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
				    if(classPart.get(count).equals("Assignment"))
					{
				    	
						count++;
						tv.isVarArrayTable.add(false);
						if(exp.exp(classPart, count))
						{
							if(!exp.returnType.equals(tempDT))
							{
								System.out.println("Expecting "+tempDT+" at char number "+count);
								return false;
							}
							count_int=exp.count;
							count=count_int;
							count_int=count;
							return true;
						}
					}
					else if(array(classPart,count))
					{
						tv.isVarArrayTable.add(true);
						count=count_int;
						count_int=count;
						return true;
					}
					else if(classPart.get(count).equals(","))
					{
						tv.isVarArrayTable.add(false);
						count++;
						count_int=count;
						return true;
					}
				    tv.isVarArrayTable.add(false);
						return false;
			 }
			 boolean list(ArrayList<String> classPart,int count)
			 {
				 
				 //System.out.println(count+"e"+classPart.get(count));
				 if(classPart.get(count).equals(","))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							if(LookupVar(vp.get(count)))
							{
								System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
								return false;
							}
							else
							{
								
								tv.varTable.add(vp.get(count)); //Adding name
								tv.varDT.add(tempDT); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
							}
							count++;
							if(init(classPart,count))
							{
								count=count_int;
								if(list(classPart,count))
								{
									count=count_int;
									count_int=count;
									return true;
								}
								
							}
							else if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								return true;
							}
						}
				 }
				 else if(classPart.get(count).equals("Terminator"))
				 {
					// count++;
					 count_int=count;
						return true;
				 }
				 return false;
			 }
			 
			 //Other models
			 boolean array(ArrayList<String> classPart,int count)
			 {
				 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
				 if(classPart.get(count).equals("Assign Op"))
				 {
					count++;
					//Syntaxtification.tv.isVarArrayTable.add(true);
					if(exp.exp(classPart, count))
					{
						if(!exp.returnType.equals("dig"))
						{
							System.out.println("Expecting integer value at char number: "+count);
							return false;
						}
						count_int=exp.count;
						count=count_int;
						count_int=count;
						return true;
					}
				 }
				 else if(classPart.get(count).equals("Assignment"))
				 {
					 count++;
					 if(classPart.get(count).equals("["))
					 {
						 count++;
						 if(words.isConst(classPart.get(count)))
						 {
							 if(!words.getConst(classPart.get(count)).equals(tempDT))
							 {
									System.out.println("Expecting "+tempDT+" value at char number: "+count);
									return false;
								}
							 count++;
							 if(clist1(classPart,count))
							 {
								 count=count_int;
								 return true;
							 }
						 }
						 else if(classPart.get(count).equals("]"))
						 {
							 count=count_int;
								count_int=count;
							 return true;
						 }
					 }
				 }
				 return false;
			 }
			 boolean clist1(ArrayList<String> cp,int c)
			 {
			 	if(c>=cp.size()){ c=cp.size()-1; return true;}
			 	 if(cp.get(c).equals(","))			//Call to clist1
			 	 {
			 		 c++;
			 		 if(words.isConst(cp.get(c)))
			 		 {
			 			if(!words.getConst(cp.get(c)).equals(tempDT))
						 {
								System.out.println("Expecting "+tempDT+" value at char number: "+c);
								return false;
							}
			 			 c++;
			 			 if(clist1(cp,c))
			 			 {
			 				 c=count_int;
							 count_int=c;
							 return true;
						 
			 			 }
			 			 
			 		 }
			 	 }
			 	 else if(cp.get(c).equals("]"))
			 	 {
			 		c++;
					 count_int=c;
					 return true;
			 	 }
			 	return false;
			 } 
			 boolean Object(ArrayList<String> cp,int c)
			 {
				 
				 if(c>=cp.size())
				 {
					 return true;
				 }
				 
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)) && !vp.get(c+1).equals("("))
						{
							System.out.println("No class found Error at char number:"+c+",,"+vp.get(c));
							return false;
						}
						
					 
					 
					  c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 
						  	if(LookupVar(vp.get(c)))
							{
								System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
								return false;
							}
							else
							{
								tv.varTable.add(vp.get(c)); //Adding name
								tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
								tempDT=("class_"+vp.get(c-1));
							}
					  
						  	c++;
						
						 if(obj1(cp,c))
						 {
							 
							 c=count_int;
							 
							 if(Ob(cp, c))
							 {
								 
								 if(c>=cp.size())
								 {
									 if(cp.get(c-1).equals("Terminator"))
									 {
										 count_int=c;
										 return true;
									 }
								 }
								 else
								 {
									 if(cp.get(c-1).equals("Terminator"))
									 {
										 count_int=c;
										 return true;
									 }
									 else if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
								 }
							 }
						 }
					 }
				 }
				 return false;
			 }
			 boolean LookUpClassParent(String child,String Parent)
			 {
				 int indexOfChild=0,i=0;
				 indexOfChild=indexOfChild(child);
				i= indexOfParent(Parent,indexOfChild);
				 if(i!=-1)
				 {
					 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
					 return true;
				 }
					
				
				 
				 return false;
			 }
			 int indexOfParent(String Parent,int indexOfChild)
			 {
				 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
				 {
					
					 return indexOfChild;
				 }
				 else
				 {
					 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
						 return -1;
					 else
					 {
						 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
						
					 }
								 
				 }
				 
			 }
			 int indexOfChild(String Child)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 {
					 if(Syntaxtification.ct.Cname.get(i).equals(Child))
					 {
						 return i;
					 }
				 }
				 return -1;
			 }
			 boolean obj1(ArrayList<String> cp, int c)
			 {
				 
				 if(cp.get(c).equals("Assignment"))
				 {
					 Syntaxtification.tv.isVarArrayTable.add(false);
					 c++;
					 if(cp.get(c).equals("new"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 
							 if(vp.get(c).equals(vp.get(c-4)))
								{
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
								}
							 else if(!vp.get(c).equals(vp.get(c-4)))
								{
								  
								 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
										
										
										
									}
								 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
										
											System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
											return false;
										
										
										
									}
								  else
								  {
									System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
									return false;
								  }
								}
								
						 }
						
					 }
					
				 }
				 else if(cp.get(c).equals("["))
				 {
					 
					 c++;
				
					 Syntaxtification.tv.isVarArrayTable.add(true);
					 if(words.isConst(cp.get(c)))
					 {
						 if(!words.getConst(cp.get(c)).equals("dig"))
						 {
							 System.out.println("Expecting integer value at char number :"+c);
							 return false;
						 }
						 c++;
						 if(cp.get(c).equals("]"))
						 {
							 c++;
							 //Here init_ob
							 
							if(init_ob(cp,c))
							{
								c=count_int;
							
								 return true;
							}
						 }
					 }
					 else if(exp.exp(cp, c))
					 {
						 if(!exp.returnType.equals("dig"))
						 {
							 System.out.println("Expecting integer value at char number :"+c);
							 return false;
						 }
						 c=exp.count;
						
						 count_int=c;
						 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
						 else
						 {
							 
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 //Here init_ob
								if(init_ob(cp,c))
								{
									c=count_int;
									 return true;
								}
							 }
							 else  if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 //Here init_ob
								if(init_ob(cp,c))
								{
									c=count_int;
									 return true;
								}
							 }
						 }
						
					 }
				 }
				 else if(cp.get(c).equals("Terminator"))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 count_int=c;
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					 tv.isVarArrayTable.add(false);
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals(","))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 count_int=c;
					 return true;
				 }
				 
				 return false;
			 }
			 boolean init_ob(ArrayList<String> cp,int c)
			 {
				if(c>=cp.size()) return true; 
				
				 if(cp.get(c).equals("Assignment"))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 if(cp.get(c).equals("new"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("ID"))
						 {
							 if(vp.get(c).equals(vp.get(c-4)))
								{
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
								}
							 else if(!vp.get(c).equals(vp.get(c-4)))
								{
								  
								 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
										
										
										
									}
								 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
										
											System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
											return false;
										
										
										
									}
								  else
								  {
									System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
									return false;
								  }
								}
						 }
				/*		 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals("["))
							 {
								 c++;
								 if(words.isConst(cp.get(c)))
								 {
									 c++;
									 if(cp.get(c).equals("]"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
								 }
							 }
						 }
				*/		 
					 }
					 
				 }
				 else if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 count_int=c;
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals(","))
				 {
					 c++;
					 count_int=c;
					 return true;
				 }
				 return false;
			 }
			 boolean Ob(ArrayList<String> cp,int c)
			 {
				 if(c>=cp.size())
				 {
					 return true;
				 }
				 else
				 {
					 if(cp.get(c).equals(","))
					 {
						 c++;
						 if(cp.get(c).equals("ID")) //obj1
						 {
							
								{
									tv.varTable.add(vp.get(c)); //Adding name
									tv.varDT.add(tempDT); //Adding DataType
									tv.varScope.add(scope); //Adding Scope
								}
						  
							 c++;
							 if(obj1(cp,c))
							 {
								 c=count_int;
								 if(Ob(cp,c))
								 {
									 c=count_int;
									 if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
								 }
							 }
								
						 }
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 count_int=c;
					
						 
						 return true;
					 }
					 else  if(cp.get(c-1).equals("Terminator"))
					 {
						 
						 count_int=c;
					
						 
						 return true;
					 }
				 }
				 
					 return false;
			 }
			}

			//DECLARATIONBody SEMANTIC END


		}
	//Simple Body Semantic End
		
		

		//Constructor Semantic Start
		
		class ConstructorModel {


			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[];
			String follow_for_exp[]={"["};
			private	String follow_for_body[]={"]"};
			ExpModel exp ;
			declarationModel decl;
			private	BodyModel body;
			private  ArrayList<String> params = new ArrayList();	
			 private ArrayList<String> getParams()
			 {
				 return params;
			 }
			 private void SetParams(String param)
			 {
				 params.add(param);
			 }
			tableVar tv = new tableVar();
			ConstructorModel(){}
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			 }
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope,tv);
			 }
			 public boolean construct_def(ArrayList<String> cp,int c)
			 {
				 
				 if(cp.get(c).equals("acces modifier"))
				 {
					
					 c++;
					 
					 if(cp.get(c).equals("type modifier"))
					 {
						 c++;
						
							 if(cp.get(c).equals("ID"))
							 {
								 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
								 {
									 tFunction.isPrivate.add(true);
									 tFunction.isStable.add(true);
									 tFunction.nameFunction.add(vp.get(c));
									 tFunction.varScope.add(scope);
									 tFunction.ReturnType.add("constructor");
								 }
								 else
								 {
									 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
								 }
								 c++;
								 if(cp.get(c).equals("(")) //Confirmation of params - (
								 {
									 
									 if(params(cp, c))
			 						 {
			 							 
			 							 c=count_int;
			 							 if(cp.get(c).equals("["))
			 							 {
			 								 c++;
			 								 //System.out.println(c+"HerEFF:"+cp.get(c));
			 								 if(body.body(cp,c))
			 								 {
			 									
			 									c=body.count;
			 								tFunction.variablelist.add(body.tv);
			 									if(cp.get(c).equals("]"))
			 									{
			 										c++;
			 										count=count_int=c;
			 										return true; //Successful parsed.
			 										
			 									}
			 								 }
			 								 if(failedCount<c && body.failedCount<c)
			 							   			failedCount=c;
			 								   	else if(body.failedCount>c)
			 								   			failedCount=body.failedCount;
			 							 }
			 						 }
								 }
							 }
						 
					 }//if stable not present.
					 else if(cp.get(c).equals("ID"))
						 {
						 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
						 {
							 tFunction.isPrivate.add(true);
							 tFunction.isStable.add(false);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add("constructor");
						 }
						 else
						 {
							 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
						 }
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 									tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
							 }
						 }
					 
				 } // if access modifier not present
				 else if(cp.get(c).equals("type modifier"))
				 {
					 c++;

						 if(cp.get(c).equals("ID"))
						 {
							 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
							 {
								 tFunction.isPrivate.add(false);
								 tFunction.isStable.add(true);
								 tFunction.nameFunction.add(vp.get(c));
								 tFunction.varScope.add(scope);
								 tFunction.ReturnType.add("constructor");
							 }
							 else
							 {
								 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
							 }
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 									tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
							 }
						 }
					 
				 }//if stable+access modifier not present.

				 else if(cp.get(c).equals("ID"))
					 {
					 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
					 {
						 tFunction.isPrivate.add(false);
						 tFunction.isStable.add(false);
						 tFunction.nameFunction.add(vp.get(c));
						 tFunction.varScope.add(scope);
						 tFunction.ReturnType.add("constructor");
					 }
					 else
					 {
						 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
					 }
						 c++;
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 if(params(cp, c))
	 						 {
	 							 
	 							 c=count_int;
	 							 if(cp.get(c).equals("["))
	 							 {
	 								 c++;
	 								 //System.out.println(c+"HerEFF:"+cp.get(c));
	 								 if(body.body(cp,c))
	 								 {
	 									
	 									c=body.count;
	 									tFunction.variablelist.add(body.tv);
	 									if(cp.get(c).equals("]"))
	 									{
	 										c++;
	 										count=count_int=c;
	 										return true; //Successful parsed.
	 										
	 									}
	 								 }
	 								 if(failedCount<c && body.failedCount<c)
	 							   			failedCount=c;
	 								   	else if(body.failedCount>c)
	 								   			failedCount=body.failedCount;
	 							 }
	 						 }
						 }
					 }
				 


				 return false;
			 }
			 private boolean lookUpVar(String name)
		 	 {
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
		 		 return false;
		 	 }
		 	 private boolean lookUpClass(String name)
		 	 {
		 		if(Syntaxtification.ct.Cname.size()>0)
		 		{
		 			
		 			if(Syntaxtification.ct.Cname.get(ClassIndex).equals(name))
			 			 return true;
		 			 else
			 		 {
			 			 if(Syntaxtification.ct.innerClassList.size()>0)
			 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
			 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
			 		 			 return true;
			 		 }
		 		}
		 		
		 		return false;
		 	 }
		 	 boolean fields(ArrayList<String> cp,int c)
		 	 {
		 		 if(cp.get(c).equals("datatype"))
		 		 {
		 			 c++;
		 			
		 			if(cp.get(c).equals("ID"))
		 			{
		 				if(!lookUpVar(vp.get(c)))
						 {
							 
							 tv.varTable.add(vp.get(c));
							 tv.varScope.add(scope);
							 tv.varDT.add(vp.get(c-1));
							 SetParams(vp.get(c-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
						 }
		 				c++;
		 				
		 				 if(list(cp,c))
		 				{
		 					c=count_int;
		 					
		 					if(c>=cp.size())
		 					{
		 						if(cp.get(c-1).equals(")"))
		 						{
		 							
		 							count_int=c;
		 							
		 							return true;
		 						}
		 					}
		 					else if(cp.get(c).equals(")"))
		 					{
		 						c++;
		 						
		 						count_int=c;

		 						return true;
		 					}
		 					else if(cp.get(c-1).equals(")"))
		 					{

		 						count_int=c;

		 						return true;
		 					}
		 				}
		 				
		 			}
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		 
		 		 return false;
		 	 }
		 	 boolean params(ArrayList<String> cp,int c)
		 	 {
		 		 if(fields(cp,c))
		 		 {
		 			 c=count_int;
		 			 tFunction.paramList.add(getParams());
		 			 return true;
		 		 }
		 		 else if(Object(cp, c))
		 		 {
		 			 c=count_int;
		 				count_int=c;
		 				return true;
		 		 }
		 		 else if(cp.get(c).equals(")"))
		 		 {
		 			 count_int=++c;
		 			 tFunction.paramList.add(getParams());
		 			 return true;
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   	
		 		 return false;
		 	 }
		 	 boolean list(ArrayList<String> classPart,int count)
		 	 {
		 		 
		 		 if(classPart.get(count).equals(","))
		 		 {
		 			 count++;
		 			 if(cp.get(count).equals("datatype"))
		 			 {
		 				 count++;
		 				 if(classPart.get(count).equals("ID"))
		 					{
		 					if(!lookUpVar(vp.get(count)))
							 {
								 
								 tv.varTable.add(vp.get(count));
								 tv.varScope.add(scope);
								 tv.varDT.add(vp.get(count-1));
								 SetParams(vp.get(count-1));
							 }
							 else
							 {
								 System.out.println("Redeclaration Error of variable "+ vp.get(count)+ " at char number:"+(count+1));
							 }
		 						count++;
		 						
		 						if(list(classPart,count))
		 						{
		 							count=count_int;
		 							count_int=count;
		 							//System.out.println(count+":rr:"+cp.get(count));
		 							return true;
		 						}
		 						/*else
		 						{
		 							
		 							count--;
		 							//System.out.println(count+":rr:"+cp.get(count));
		 							if(Object(classPart, count))
		 							 {
		 								 count=count_int;
		 									count_int=count;
		 									
		 									return true;
		 							 }
		 						}*/
		 					}
		 			 }
		 			 else if(Object(classPart, count))
		 			 {
		 				 count=count_int;
		 					count_int=count;
		 					
		 					return true;
		 			 }
		 			 
		 		 }
		 		 else if(classPart.get(count).equals(")"))
		 		 {
		 			 
		 			 count_int=count;
		 				return true;
		 		 }
		 		 if(failedCount<count)
		 	   			failedCount=count;
		 		   
		 		 return false;
		 	 }
		 	 boolean Object(ArrayList<String> cp,int c)
		 	 {
		 		 if(c>=cp.size())
		 		 {
		 			 return true;
		 		 }
		 		
		 		 if(cp.get(c).equals("ID"))
		 		 {
		 			 c++;
		 			 if(cp.get(c).equals("ID"))
		 			 {
		 				 	if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
	 						 {
	 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
	 						 }
		 				 c++;
		 				
		 					 if(Ob(cp, c))
		 					 {
		 						c=count_int;
		 						 if(c>=cp.size())
		 						 {
		 							 if(cp.get(c-1).equals(")"))
		 							 {
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 
		 						 }
		 						 else
		 						 {
		 							 
		 							 if(cp.get(c-1).equals(")"))
		 							 {
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 else if(cp.get(c).equals(")"))
		 							 {
		 								 
		 								 count_int=c=c+1;
		 								 
		 								 return true;
		 							 }
		 						 }
		 					 }
		 				 
		 			 }
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   
		 		 return false;
		 	 }
		 	 boolean Ob(ArrayList<String> cp,int c)
		 	 {
		 		 if(c>=cp.size())
		 		 {
		 			 return true;
		 		 }
		 		 else
		 		 {
		 			 
		 			 if(cp.get(c).equals(","))
		 			 {
		 				 c++;
		 				
		 				 if(cp.get(c).equals("ID")) //obj1
		 				 {
		 					 c++;
		 					 
		 					if(cp.get(c).equals("ID"))
		 					{
		 						if(lookUpClass(vp.get(c-1)))
			 				 	{
			 				 		if(!lookUpVar(vp.get(c)))
			 						 {
			 							 
			 							 tv.varTable.add(vp.get(c));
			 							 tv.varScope.add(scope);
			 							 tv.varDT.add("class_"+vp.get(c-1));
			 							 SetParams(vp.get(c-1));
			 						 }
			 						 else
			 						 {
			 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
			 						 }
			 				 	}
			 				 	 else
		 						 {
		 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
		 						 }
		 						c++;
		 					
		 						 if(Ob(cp,c))
		 						 {
		 							 c=count_int;
		 							 if(cp.get(c).equals(")"))
		 							 {
		 								 c++;
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 else
		 								 if(cp.get(c-1).equals(")"))
		 								 {
		 									 
		 									 count_int=c;
		 									 return true;
		 								 }
		 									
		 						
		 							 
		 						 }
		 					 
		 						
		 					}
		 				 }
		 				 else if(cp.get(c).equals("datatype"))
		 				 {
		 				
		 					 if(fields(cp, c))
		 					 {
		 						 c=count_int;
		 							count_int=c;
		 							return true;
		 					 }
		 				 }
		 			 }
		 			 else  if(cp.get(c).equals(")"))
		 			 {
		 				 c++;
		 				 count_int=c;
		 				 
		 				 return true;
		 			 }
		 		
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   	
		 			 return false;
		 	 }



		}
		//Constructor Semantic End

	//Function Semantic Start
		 class FunctionDefModel 
		 {

		 	KeyWords words = new KeyWords();
		 	public int count=0,count_int=0,failedCount=0,scope;
		 	 ArrayList<String> cp = new ArrayList();
		 		 ArrayList<String> vp = new ArrayList();
		 	String follow_of_Parent[];
		 	String follow_for_exp[]={"["};
		 	private	String follow_for_body[]={"]"};
		 	ExpModel exp ;
		 	declarationModel decl;
		 	private	BodyModel body;
		 	tableVar tv = new tableVar();
		 	FunctionDefModel(){}
		 	 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 	 {
		 			this.cp = cp;
		 			this.vp = vp;
		 			this.scope=scope;
		 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
		 			decl = new declarationModel(cp,vp,scope);
		 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		 	 }
		 	 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
		 	 {
		 			this.cp = cp;
		 			this.vp = vp;
		 			follow_of_Parent=Follow;
		 			this.scope=scope;
		 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
		 			decl = new declarationModel(cp,vp,scope);
		 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		 	 }
		 	private  ArrayList<String> params = new ArrayList();	
			 private ArrayList<String> getParams()
			 {
				 return params;
			 }
			 private void SetParams(String param)
			 {
				 params.add(param);
			 }
		 	 public boolean func_def(ArrayList<String> cp,int c)
		 	 {
		 		 
		 		 if(cp.get(c).equals("acces modifier"))
		 		 {
		 			
		 			 c++;
		 			 
		 			 if(cp.get(c).equals("type modifier"))
		 			 {
		 				 c++;
		 				 //System.out.println(""+cp.get(c));
		 				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
		 				 {
		 					 c++;
		 					 if(cp.get(c).equals("ID"))
		 					 {
		 						if(!lookUpFunction(vp.get(c)))
								 {
									 tFunction.isPrivate.add(true);
									 tFunction.isStable.add(true);
									 tFunction.nameFunction.add(vp.get(c));
									 tFunction.varScope.add(scope);
									 tFunction.ReturnType.add(vp.get(c-1));
								 }
								 else
								 {
									 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
								 }
		 						 c++;
		 						 if(cp.get(c).equals("(")) //Confirmation of params - (
		 						 {
		 							 
		 							if(params(cp, c))
			 						 {
			 							 
			 							 c=count_int;
			 							 if(cp.get(c).equals("["))
			 							 {
			 								 c++;
			 								 //System.out.println(c+"HerEFF:"+cp.get(c));
			 								 if(body.body(cp,c))
			 								 {
			 									
			 									c=body.count;
			 									tFunction.variablelist.add(body.tv);
			 									if(cp.get(c).equals("]"))
			 									{
			 										c++;
			 										count=count_int=c;
			 										return true; //Successful parsed.
			 										
			 									}
			 								 }
			 								 if(failedCount<c && body.failedCount<c)
			 							   			failedCount=c;
			 								   	else if(body.failedCount>c)
			 								   			failedCount=body.failedCount;
			 							 }
			 						 }
		 						 }
		 					 }
		 				 }
		 			 }//if stable not present.
		 			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
		 			 {
		 				 c++;
		 				 
		 				 if(cp.get(c).equals("ID"))
		 				 {
		 					if(!lookUpFunction(vp.get(c)))
							 {
								 tFunction.isPrivate.add(true);
								 tFunction.isStable.add(false);
								 tFunction.nameFunction.add(vp.get(c));
								 tFunction.varScope.add(scope);
								 tFunction.ReturnType.add(vp.get(c-1));
							 }
							 else
							 {
								 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
							 }
		 					 c++;
		 					 
		 					 if(cp.get(c).equals("(")) //Confirmation of params - (
		 					 {
		 						 c++;
		 						 
		 						 if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 									tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
		 					 }
		 				 }
		 				 if(failedCount<c)
		 			   			failedCount=c;
		 				   
		 			 }
		 			 if(failedCount<c)
		 		   			failedCount=c;
		 			   	
		 		 } // if access modifier not present
		 		 else if(cp.get(c).equals("type modifier"))
		 		 {
		 			 
		 			 c++;
		 			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
		 			 {
		 				 c++;
		 				 
		 				 if(cp.get(c).equals("ID"))
		 				 {
		 					if(!lookUpFunction(vp.get(c)))
							 {
								 tFunction.isPrivate.add(false);
								 tFunction.isStable.add(true);
								 tFunction.nameFunction.add(vp.get(c));
								 tFunction.varScope.add(scope);
								 tFunction.ReturnType.add(vp.get(c-1));
							 }
							 else
							 {
								 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
							 }
		 					 c++;
		 					 
		 					 if(cp.get(c).equals("(")) //Confirmation of params - (
		 					 {
		 						 c++;
		 						 
		 						 if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 									tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
		 					 }
		 				 }
		 				 if(failedCount<c)
		 			   			failedCount=c;
		 				   
		 			 }
		 			 if(failedCount<c)
		 		   			failedCount=c;
		 			  
		 		 }//if stable+access modifier not present.
		 		 else  if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
		 		 {
		 			 c++;
		 			 
		 			 if(cp.get(c).equals("ID"))
		 			 {
		 				if(!lookUpFunction(vp.get(c)))
						 {
							 tFunction.isPrivate.add(false);
							 tFunction.isStable.add(false);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add(vp.get(c-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
						 }
		 				 c++;
		 				 
		 				 if(cp.get(c).equals("(")) //Confirmation of params - (
		 				 {
		 					 
		 					 c++;
		 					 
		 					 if(params(cp, c))
		 					 {
		 						 
		 						 c=count_int;
		 						 if(cp.get(c).equals("["))
		 						 {
		 							 c++;
		 							 //System.out.println(c+"HerEFF:"+cp.get(c));
		 							 if(body.body(cp,c))
		 							 {
		 								
		 								c=body.count;
		 								tFunction.variablelist.add(body.tv);
		 								if(cp.get(c).equals("]"))
		 								{
		 									c++;
		 									count=count_int=c;
		 									return true; //Successful parsed.
		 									
		 								}
		 							 }
		 							 if(failedCount<c && body.failedCount<c)
		 						   			failedCount=c;
		 							   	else if(body.failedCount>c)
		 							   			failedCount=body.failedCount;
		 						 }
		 					 }
		 				 }
		 			 }
		 			 if(failedCount<c)
		 		   			failedCount=c;
		 			   
		 		 }


		 		 return false;
		 	 }
		 	 private boolean lookUpVar(String name)
		 	 {
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
		 		 return false;
		 	 }
		 	 private boolean lookUpClass(String name)
		 	 {
		 		if(Syntaxtification.ct.Cname.size()>0)
		 		{
		 			
		 			if(Syntaxtification.ct.Cname.get(ClassIndex).equals(name))
			 			 return true;
		 			 else
			 		 {
			 			 if(Syntaxtification.ct.innerClassList.size()>0)
			 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
			 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
			 		 			 return true;
			 		 }
		 		}
		 		
		 		return false;
		 	 }
		 	 boolean fields(ArrayList<String> cp,int c)
		 	 {
		 		 if(cp.get(c).equals("datatype"))
		 		 {
		 			 c++;
		 			
		 			if(cp.get(c).equals("ID"))
		 			{
		 				if(!lookUpVar(vp.get(c)))
						 {
							 
							 tv.varTable.add(vp.get(c));
							 tv.varScope.add(scope);
							 tv.varDT.add(vp.get(c-1));
							 SetParams(vp.get(c-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
						 }
		 				c++;
		 				
		 				 if(list(cp,c))
		 				{
		 					c=count_int;
		 					
		 					if(c>=cp.size())
		 					{
		 						if(cp.get(c-1).equals(")"))
		 						{
		 							
		 							count_int=c;
		 							
		 							return true;
		 						}
		 					}
		 					else if(cp.get(c).equals(")"))
		 					{
		 						c++;
		 						
		 						count_int=c;

		 						return true;
		 					}
		 					else if(cp.get(c-1).equals(")"))
		 					{

		 						count_int=c;

		 						return true;
		 					}
		 				}
		 				
		 			}
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		 
		 		 return false;
		 	 }
		 	 boolean params(ArrayList<String> cp,int c)
		 	 {
		 		 if(fields(cp,c))
		 		 {
		 			 c=count_int;
		 			 tFunction.paramList.add(getParams());
		 			 return true;
		 		 }
		 		 else if(Object(cp, c))
		 		 {
		 			 c=count_int;
		 				count_int=c;
		 				return true;
		 		 }
		 		 else if(cp.get(c).equals(")"))
		 		 {
		 			 count_int=++c;
		 			 tFunction.paramList.add(getParams());
		 			 return true;
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   	
		 		 return false;
		 	 }
		 	 boolean list(ArrayList<String> classPart,int count)
		 	 {
		 		 
		 		 if(classPart.get(count).equals(","))
		 		 {
		 			 count++;
		 			 if(cp.get(count).equals("datatype"))
		 			 {
		 				 count++;
		 				 if(classPart.get(count).equals("ID"))
		 					{
		 					if(!lookUpVar(vp.get(count)))
							 {
								 
								 tv.varTable.add(vp.get(count));
								 tv.varScope.add(scope);
								 tv.varDT.add(vp.get(count-1));
								 SetParams(vp.get(count-1));
							 }
							 else
							 {
								 System.out.println("Redeclaration Error of variable "+ vp.get(count)+ " at char number:"+(count+1));
							 }
		 						count++;
		 						
		 						if(list(classPart,count))
		 						{
		 							count=count_int;
		 							count_int=count;
		 							//System.out.println(count+":rr:"+cp.get(count));
		 							return true;
		 						}
		 						/*else
		 						{
		 							
		 							count--;
		 							//System.out.println(count+":rr:"+cp.get(count));
		 							if(Object(classPart, count))
		 							 {
		 								 count=count_int;
		 									count_int=count;
		 									
		 									return true;
		 							 }
		 						}*/
		 					}
		 			 }
		 			 else if(Object(classPart, count))
		 			 {
		 				 count=count_int;
		 					count_int=count;
		 					
		 					return true;
		 			 }
		 			 
		 		 }
		 		 else if(classPart.get(count).equals(")"))
		 		 {
		 			 
		 			 count_int=count;
		 				return true;
		 		 }
		 		 if(failedCount<count)
		 	   			failedCount=count;
		 		   
		 		 return false;
		 	 }
		 	 boolean Object(ArrayList<String> cp,int c)
		 	 {
		 		 if(c>=cp.size())
		 		 {
		 			 return true;
		 		 }
		 		
		 		 if(cp.get(c).equals("ID"))
		 		 {
		 			 c++;
		 			 if(cp.get(c).equals("ID"))
		 			 {
		 				 	if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
	 						 {
	 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
	 						 }
		 				 c++;
		 				
		 					 if(Ob(cp, c))
		 					 {
		 						c=count_int;
		 						 if(c>=cp.size())
		 						 {
		 							 if(cp.get(c-1).equals(")"))
		 							 {
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 
		 						 }
		 						 else
		 						 {
		 							 
		 							 if(cp.get(c-1).equals(")"))
		 							 {
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 else if(cp.get(c).equals(")"))
		 							 {
		 								 
		 								 count_int=c=c+1;
		 								 
		 								 return true;
		 							 }
		 						 }
		 					 }
		 				 
		 			 }
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   
		 		 return false;
		 	 }
		 	 boolean Ob(ArrayList<String> cp,int c)
		 	 {
		 		 
		 		 if(c>=cp.size())
		 		 {
		 			 return true;
		 		 }
		 		 else
		 		 {
		 			 
		 			 if(cp.get(c).equals(","))
		 			 {
		 				 c++;
		 				
		 				 if(cp.get(c).equals("ID")) //obj1
		 				 {
		 					 c++;
		 					 
		 					if(cp.get(c).equals("ID"))
		 					{
		 						if(lookUpClass(vp.get(c-1)))
			 				 	{
			 				 		if(!lookUpVar(vp.get(c)))
			 						 {
			 							 
			 							 tv.varTable.add(vp.get(c));
			 							 tv.varScope.add(scope);
			 							 tv.varDT.add("class_"+vp.get(c-1));
			 							 SetParams(vp.get(c-1));
			 						 }
			 						 else
			 						 {
			 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
			 						 }
			 				 	}
			 				 	 else
		 						 {
		 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
		 						 }
		 						c++;
		 					
		 						 if(Ob(cp,c))
		 						 {
		 							 c=count_int;
		 							 if(cp.get(c).equals(")"))
		 							 {
		 								 c++;
		 								 count_int=c;
		 								 return true;
		 							 }
		 							 else
		 								 if(cp.get(c-1).equals(")"))
		 								 {
		 									 
		 									 count_int=c;
		 									 return true;
		 								 }
		 									
		 						
		 							 
		 						 }
		 					 
		 						
		 					}
		 				 }
		 				 else if(cp.get(c).equals("datatype"))
		 				 {
		 				
		 					 if(fields(cp, c))
		 					 {
		 						 c=count_int;
		 							count_int=c;
		 							return true;
		 					 }
		 				 }
		 			 }
		 			 else  if(cp.get(c).equals(")"))
		 			 {
		 				 c++;
		 				 count_int=c;
		 				 
		 				 return true;
		 			 }
		 		
		 		 }
		 		 if(failedCount<c)
		 	   			failedCount=c;
		 		   	
		 			 return false;
		 	 }


		 	}
		
		
		
		
	//Function Semantic End
		 
		 
		 
		 
		 
		 
		 
		 
		 
		///DECLARATION START
		class declarationModel 
		{
			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
				 ArrayList<String> varTable = new ArrayList();
			String follow_of_Parent;
			String follow_for_exp[]={"Terminator","]"};
			ExpModel exp ;
			int start=0,end;
			declarationModelSemantic dms;

			declarationModel(){}
		 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				dms= new declarationModelSemantic(cp,vp,scope);
		 }
		 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
				dms= new declarationModelSemantic(cp,vp,Follow,scope);
		 }
		 private boolean field(ArrayList<String> classPart,int count)
		 {
			 if(classPart.get(count).equals("datatype"))
			 {
				 count++;
				
				if(classPart.get(count).equals("ID"))
				{
					
					count++;
					
					if(init(classPart,count))
					{
						count=count_int;
						
						if(list(classPart,count))
						{
							
							count=count_int;
							
							//System.out.println(count+"e"+classPart.get(count));
							if(classPart.get(count).equals("Terminator"))
							{
								count++;
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								return true;
							}
								  
						}
						else if(classPart.get(count).equals("Terminator"))
						{
							count++;
							this.count=count;
							count_int=count;
							exp.count_int=0;
							
							return true;
						}
					}
					else if(list(classPart,count))
					{
						count=count_int;
						if(count>=classPart.size())
						{
							if(classPart.get(count-1).equals("Terminator"))
							{
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								return true;
							}
						}
						if(classPart.get(count).equals("Terminator"))
						{
							
							count++;
							
							count_int=count;
							exp.count_int=0;
							//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
							return true;
						}
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						//this.count=count;
						count_int=count;
						exp.count_int=0;
						return true;
					}
				}
			 }
			 return false;
		 }
		 public boolean decl(ArrayList<String> classPart,int count)
		 {
			 
			 start=count;
			 if(field(classPart,count))
			 {
				 	count=count_int;
				 	this.count=count;
					count_int=0;
					exp.count_int=0;
					end=this.count;
					
					if(dms.decl(classPart, start))
					{
						System.out.println("Declaration ki Semantic Sahi hai");
					}
					else
					{
						System.out.println("Declaration ki Semantic Sahi nahi hai");
						//Syntax should return false or not?
					}
					return true;
			 }
			 else if(Object(classPart, count))
			 {
				 count=count_int;
				 this.count=count;
				 //start=count;
					count_int=0;
					exp.count_int=0;
					
					if(dms.decl(classPart, start))
					{
						System.out.println("Declaration ki Semantic Sahi hai");
					}
					else
					{
						System.out.println("Declaration ki Semantic Sahi nahi hai");
						//Syntax should return false or not?
					}
					//System.out.print("Declaration ki Semantic Sahi nahi haieeee");
					return true;
			 }

			 return false;
		 }
		 boolean init(ArrayList<String> classPart,int count)
		 {
			 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
			    if(classPart.get(count).equals("Assignment"))
				{
			    	
					count++;
					
					if(exp.exp(classPart, count))
					{
						//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
						count_int=exp.count;
						count=count_int;
						count_int=count;
						return true;
					}
				}
				else if(array(classPart,count))
				{
					count=count_int;
					count_int=count;
					return true;
				}
				else if(classPart.get(count).equals(","))
				{
					count++;
					count_int=count;
					return true;
				}
				
					return false;
		 }
		 boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 //System.out.println(count+"e"+classPart.get(count));
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(classPart.get(count).equals("ID"))
					{
						count++;
						if(init(classPart,count))
						{
							count=count_int;
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								return true;
							}
							
						}
						else if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
					}
			 }
			 else if(classPart.get(count).equals("Terminator"))
			 {
				// count++;
				 count_int=count;
					return true;
			 }
			 return false;
		 }
		 
		 //Other models
		 boolean array(ArrayList<String> classPart,int count)
		 {
			 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
			 if(classPart.get(count).equals("Assign Op"))
			 {
				count++;
				if(exp.exp(classPart, count))
				{
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			 }
			 else if(classPart.get(count).equals("Assignment"))
			 {
				 count++;
				 if(classPart.get(count).equals("["))
				 {
					 count++;
					 if(words.isConst(classPart.get(count)))
					 {
						 count++;
						 if(clist1(classPart,count))
						 {
							 count=count_int;
							 return true;
						 }
					 }
					 else if(classPart.get(count).equals("]"))
					 {
						 count=count_int;
							count_int=count;
						 return true;
					 }
				 }
			 }
			 return false;
		 }
		 boolean clist1(ArrayList<String> cp,int c)
		 {
		 	if(c>=cp.size()){ c=cp.size()-1; return true;}
		 	 if(cp.get(c).equals(","))			//Call to clist1
		 	 {
		 		 c++;
		 		 if(words.isConst(cp.get(c)))
		 		 {
		 			 c++;
		 			 if(clist1(cp,c))
		 			 {
		 				 c=count_int;
						 count_int=c;
						 return true;
					 
		 			 }
		 			 
		 		 }
		 	 }
		 	 else if(cp.get(c).equals("]"))
		 	 {
		 		c++;
				 count_int=c;
				 return true;
		 	 }
		 	return false;
		 } 
		 boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 
					 c++;
					 
					 if(obj1(cp,c))
					 {
						 c=count_int;
						 
						 if(Ob(cp, c))
						 {
							 
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
							 }
							 else
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
				 }
			 }
			 return false;
		 }
		 boolean obj1(ArrayList<String> cp, int c)
		 {
			 if(cp.get(c).equals("Assignment"))
			 {
				 
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						 c=exp.count;
						 count_int=c;
						return true;
					 }
				 }
				
			 }
			 else if(cp.get(c).equals("["))
			 {
				 
				 c++;
				 //System.out.println("GG"+cp.get(c));
				 if(words.isConst(cp.get(c)))
				 {
					 c++;
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						 
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					
					 count_int=c;
					 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
					 else
					 {
						 
						 if(cp.get(c).equals("]"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
						 else  if(cp.get(c).equals("Terminator"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
					 }
					
				 }
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 return false;
		 }
		 boolean init_ob(ArrayList<String> cp,int c)
		 {
			if(c>=cp.size()) return true; 
			 if(cp.get(c).equals("Assignment"))
			 {
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 
					 if(exp.exp(cp, c))
					 {
						 
						 c=exp.count;
						 
						 count_int=c;
						return true;
					 }
			/*		 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 count_int=c;
							 return true;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 c++;
							 if(words.isConst(cp.get(c)))
							 {
								 c++;
								 if(cp.get(c).equals("]"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
			*/		 
				 }
				 
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 return false;
		 }
		 boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 if(cp.get(c).equals(","))
				 {
					 c++;
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 if(obj1(cp,c))
						 {
							 c=count_int;
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
							
					 }
				 }
				 else  if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 
					 count_int=c;
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					 
					 count_int=c;
					 return true;
				 }

			 }
				 return false;
		 }
		}
		//DECLARATION COMPLETED
		

		//DECLARATION SEMANTIC START

		class declarationModelSemantic 
		{
			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
				 ArrayList<String> varTable = new ArrayList();
			String follow_of_Parent,tempDT;
			String follow_for_exp[]={"Terminator","]"};
			ExpModel exp ;
			int start=0,end,scope;
			
			declarationModelSemantic(){}
		 public declarationModelSemantic(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
		 }
		 public declarationModelSemantic(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
		 {
				this.cp = cp;;
				this.vp = vp;
				follow_of_Parent=Follow;
				
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
		 }
		 private boolean LookupVar(String name)
		 {
			 for(int i=0; i<tv.isVarArrayTable.size();i++)
			 {
				 if(name.contentEquals(tv.varTable.get(i)))
				 {
					 System.out.println("Class_"+tv.varTable.get(i));
					 return true;
			 
				 }
			 }
			// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
			 return false;
		 }
		 private boolean lookUpClass(String name)
	 	 {
	 		if(Syntaxtification.ct.Cname.size()>0 )
	 		{
	 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
	 			if(Syntaxtification.ct.Cname.get(i).equals(name))
		 			 return true;
	 			
		 		 
		 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
		 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
		 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
		 		 			 return true;
		 		 
	 		}
	 		System.out.println(name);
	 		return false;
	 	 }
		 private boolean field(ArrayList<String> classPart,int count)
		 {
			 if(classPart.get(count).equals("datatype"))
			 {
				 
				 count++;
				
				if(classPart.get(count).equals("ID"))
				{
					if(LookupVar(vp.get(count)))
					{
						System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
						return false;
					}
					else
					{
						tv.varTable.add(vp.get(count)); //Adding name
						tv.varScope.add(scope); //Adding Scope
						tv.varDT.add(vp.get(count-1)); //Adding DataType
						//Syntaxtification.varTable.add(vp.get(count));
						tempDT=vp.get(count-1);
					}
					count++;
					
					if(init(classPart,count))
					{
						count=count_int;
						
						if(list(classPart,count))
						{
							
							count=count_int;
							
							//System.out.println(count+"e"+classPart.get(count));
							if(classPart.get(count).equals("Terminator"))
							{
								count++;
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								
								return true;
							}
								  
						}
						else if(classPart.get(count).equals("Terminator"))
						{
							count++;
							this.count=count;
							count_int=count;
							exp.count_int=0;
							
							return true;
						}
					}
					else if(list(classPart,count))
					{
						count=count_int;
						if(count>=classPart.size())
						{
							if(classPart.get(count-1).equals("Terminator"))
							{
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								return true;
							}
						}
						if(classPart.get(count).equals("Terminator"))
						{
							
							count++;
							
							count_int=count;
							exp.count_int=0;
							//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
							return true;
						}
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						//this.count=count;
						count_int=count;
						exp.count_int=0;
						return true;
					}
				}
			 }
			 return false;
		 }
		 public boolean decl(ArrayList<String> classPart,int count)
		 {
			
		
			 if(field(classPart,count))
			 {
				 	count=count_int;
				 	this.count=count;
					count_int=0;
					exp.count_int=0;	
					return true;
			 }
			 else if(Object(classPart, count))
			 {
				 count=count_int;
				 this.count=count;
					count_int=0;
					exp.count_int=0;
					
					return true;
			 }
			 
			 return false;
		 }
		 boolean init(ArrayList<String> classPart,int count)
		 {
			 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
			    if(classPart.get(count).equals("Assignment"))
				{
			    	
					count++;
					tv.isVarArrayTable.add(false);
					if(exp.exp(classPart, count))
					{
						if(!exp.returnType.equals(tempDT))
						{
							System.out.println("Expecting" +tempDT+" at char number :" +count);
							return false;
						}
						count_int=exp.count;
						count=count_int;
						count_int=count;
						return true;
					}
				}
				else if(array(classPart,count))
				{
					tv.isVarArrayTable.add(true);
					count=count_int;
					count_int=count;
					return true;
				}
				else if(classPart.get(count).equals(","))
				{
					tv.isVarArrayTable.add(false);
					count++;
					count_int=count;
					return true;
				}
			    tv.isVarArrayTable.add(false);
					return false;
		 }
		 boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 //System.out.println(count+"e"+classPart.get(count));
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(classPart.get(count).equals("ID"))
					{
						if(LookupVar(vp.get(count)))
						{
							System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
							return false;
						}
						else
						{
							
							tv.varTable.add(vp.get(count)); //Adding name
							tv.varDT.add(tempDT); //Adding DataType
							tv.varScope.add(scope); //Adding Scope
						}
						count++;
						if(init(classPart,count))
						{
							count=count_int;
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								return true;
							}
							
						}
						else if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
					}
			 }
			 else if(classPart.get(count).equals("Terminator"))
			 {
				// count++;
				 count_int=count;
					return true;
			 }
			 return false;
		 }
		 
		 //Other models
		 boolean array(ArrayList<String> classPart,int count)
		 {
			 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
			 if(classPart.get(count).equals("Assign Op"))
			 {
				count++;
				//Syntaxtification.tv.isVarArrayTable.add(true);
				if(exp.exp(classPart, count))
				{
					if(!exp.returnType.equals("dig"))
					{
						System.out.println("Expecting interger at char number :" +count);
						return false;
					}
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			 }
			 else if(classPart.get(count).equals("Assignment"))
			 {
				 count++;
				 if(classPart.get(count).equals("["))
				 {
					 count++;
					 if(words.isConst(classPart.get(count)))
					 {
						 if(!words.getConst(classPart.get(count)).equals(tempDT))
							{
								System.out.println("Expecting "+tempDT+" at char number :" +count);
								return false;
							}
						 count++;
						 if(clist1(classPart,count))
						 {
							 count=count_int;
							 return true;
						 }
					 }
					 else if(classPart.get(count).equals("]"))
					 {
						 count=count_int;
							count_int=count;
						 return true;
					 }
				 }
			 }
			 return false;
		 }
		 boolean clist1(ArrayList<String> cp,int c)
		 {
		 	if(c>=cp.size()){ c=cp.size()-1; return true;}
		 	 if(cp.get(c).equals(","))			//Call to clist1
		 	 {
		 		 c++;
		 		 if(words.isConst(cp.get(c)))
		 		 {
		 			 if(!words.getConst(cp.get(count)).equals(tempDT))
						{
							System.out.println("Expecting "+tempDT +" at char number :" +c);
							return false;
						}
		 			 c++;
		 			 if(clist1(cp,c))
		 			 {
		 				 c=count_int;
						 count_int=c;
						 return true;
					 
		 			 }
		 			 
		 		 }
		 	 }
		 	 else if(cp.get(c).equals("]"))
		 	 {
		 		c++;
				 count_int=c;
				 return true;
		 	 }
		 	return false;
		 } 
		 boolean Object(ArrayList<String> cp,int c)
		 {
			 
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpClass(vp.get(c)))
					{
						System.out.println("No class found Error at line number:"+c+",,"+vp.get(c));
						return false;
					}
					
				 
				 
				  c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 
					  	if(LookupVar(vp.get(c)))
						{
							System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
							return false;
						}
						else
						{
							tv.varTable.add(vp.get(c)); //Adding name
							tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
							tv.varScope.add(scope); //Adding Scope
						}
				  
					  	c++;
					
					 if(obj1(cp,c))
					 {
						 
						 c=count_int;
						 
						 if(Ob(cp, c))
						 {
							 
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
							 }
							 else
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
				 }
			 }
			 return false;
		 }
		 boolean LookUpClassParent(String child,String Parent)
		 {
			 int indexOfChild=0,i=0;
			 indexOfChild=indexOfChild(child);
			i= indexOfParent(Parent,indexOfChild);
			 if(i!=-1)
			 {
				 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
				 return true;
			 }
				
			
			 
			 return false;
		 }
		 int indexOfParent(String Parent,int indexOfChild)
		 {
			 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
			 {
				
				 return indexOfChild;
			 }
			 else
			 {
				 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
					 return -1;
				 else
				 {
					 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
					
				 }
							 
			 }
			 
		 }
		 int indexOfChild(String Child)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
			 {
				 if(Syntaxtification.ct.Cname.get(i).equals(Child))
				 {
					 return i;
				 }
			 }
			 return -1;
		 }
		 boolean obj1(ArrayList<String> cp, int c)
		 {
			 
			 if(cp.get(c).equals("Assignment"))
			 {
				 Syntaxtification.tv.isVarArrayTable.add(false);
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 
						 if(vp.get(c).equals(vp.get(c-4)))
							{
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
							}
						 else if(!vp.get(c).equals(vp.get(c-4)))
							{
							  
							 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
									
									
									
								}
							 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
									
										System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
										return false;
									
									
									
								}
							  else
							  {
								System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
								return false;
							  }
							}
							
					 }
					
				 }
				
			 }
			 else if(cp.get(c).equals("["))
			 {
				 
				 c++;
			
				 Syntaxtification.tv.isVarArrayTable.add(true);
				 if(words.isConst(cp.get(c)))
				 {
					 if(!words.getConst(cp.get(c)).equals(tempDT))
						{
							System.out.println("Expecting "+tempDT+" at char number :" +c);
							return false;
						}
					 c++;
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						 
						if(init_ob(cp,c))
						{
							c=count_int;
						
							 return true;
						}
					 }
				 }
				 else if(exp.exp(cp, c))
				 {
					 if(!exp.returnType.equals(tempDT))
						{
							System.out.println("Expecting "+tempDT+" at char number :" +count);
							return false;
						}
					 c=exp.count;
					
					 count_int=c;
					 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
					 else
					 {
						 
						 if(cp.get(c).equals("]"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
						 else  if(cp.get(c).equals("Terminator"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
					 }
					
				 }
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				 tv.isVarArrayTable.add(false);
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 count_int=c;
				 return true;
			 }
			 
			 return false;
		 }
		 boolean init_ob(ArrayList<String> cp,int c)
		 {
			if(c>=cp.size()) return true; 
			
			 if(cp.get(c).equals("Assignment"))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 if(vp.get(c).equals(vp.get(c-4)))
							{
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
							}
						 else if(!vp.get(c).equals(vp.get(c-4)))
							{
							  
							 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
									
									
									
								}
							 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
									
										System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
										return false;
									
									
									
								}
							  else
							  {
								System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
								return false;
							  }
							}
					 }
			/*		 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 count_int=c;
							 return true;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 c++;
							 if(words.isConst(cp.get(c)))
							 {
								 c++;
								 if(cp.get(c).equals("]"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
			*/		 
				 }
				 
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 return false;
		 }
		 boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 if(cp.get(c).equals(","))
				 {
					 c++;
					 if(cp.get(c).equals("ID")) //obj1
					 {
						
							{
								tv.varTable.add(vp.get(c)); //Adding name
								tv.varDT.add(tempDT); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
							}
					  
						 c++;
						 if(obj1(cp,c))
						 {
							 c=count_int;
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
							
					 }
				 }
				 else  if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 count_int=c;
				
					 
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					 
					 count_int=c;
				
					 
					 return true;
				 }
			 }
			 
				 return false;
		 }
		}

		//DECLARATION SEMANTIC END



		 class ExpModel
			{
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,FailedCounter=0,scope=0;
				 String follow_of_Parent[];
				 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			
				 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
				 String returnType="",ChangeType="";
				
				 String leftOp="",rightOp="";
				 public boolean AllowAssignation=true;
				ExpModel(){}
				public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;

				}
				public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;
					follow_of_Parent=follow;
					
				}
				public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					AllowAssignation=allow;
					this.scope=scope;
				}
				public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;
					follow_of_Parent=follow;
					AllowAssignation=allow;
					
				}
				
			public 	boolean exp(ArrayList<String> ClassPart,int count)
				{
				
					if(isUniOp(ClassPart.get(count)))
					{
					
						startUniOp=true;
						count++;
						if(exp1(ClassPart,count))
						{
							count=count_int;
							this.count=count;
						
							return true;
						}
					}
					else if(exp1(ClassPart,count))
					{
						
						count=count_int;
						count_int=0;
						this.count=count;
						
						
						return true;
					}
					else if(words.isParentFollow(cp.get(count), follow_of_Parent))
					{
						count=count_int;
						this.count=count;
						
						return true;
					}
					if(FailedCounter<count)
						FailedCounter=count;
				
					return false;
				}

			boolean exp1(ArrayList<String> cp,int c)
			{
					if(cp.size()<=c){count_int=cp.size()-1;return true; }
				
					if(cp.get(c).equals("ID"))
					{
						
						if(vp.get(c+1).equals("("))
						{
							String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
							functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
							fcm.follow_for_exp[0]=")";
							
							if(fcm.functionCall(cp, c))
							{
								
								String dt=getFuncReturnType(vp.get(c));
								String rdt=getFuncReturnType(vp.get(c));
								
								c=fcm.count-1;
								if(!opFlag) //No Left Operator Exists right now
								{
									
									 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
										{
									 		startUniOp=false;
												System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
												return false;
												
			
										}
										else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
										{
											startUniOp=false;
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
									else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=dt; //left operator
														opFlag=true;
														
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
											System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
											return false;
											
		
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									
						
								}
								else if(opFlag) //Left Operator Exists
								{
									
									{
										if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
										{

											rightOp=rdt;
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											leftOp=returnType;
											opFlag=true;
											
										}
										else //No more operators
										{

											
													
													if(!typeChanged)
													{
														
														
															rightOp=rdt;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
															 
														
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=rdt;
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														
														else
														{
															rightOp=ChangeType;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
														}
													}
													
												
											
										}
									}
									
								}
							
							}
							/*
							if(!lookupFunction(vp.get(c)))
							{
								System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							else
							{}
							*/
						}//Not function
						else //Maybe variable?
						{
							if(!lookUpVar(vp.get(c)))
							{
								System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							else
							{
								if(!opFlag) //No Left Operator Exists right now
								{
									String dt=getReturnTypeVar(vp.get(c));
									returnType=dt;
									 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
										{
										 startUniOp=false;
											if(dt.equals("dig") || dt.equals("frac"))
											{
												returnType="dig";
											}
											else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
										{
											startUniOp=false;
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
									else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=dt; //left operator
														opFlag=true;
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
										if(dt.equals("dig") || dt.equals("frac"))
										{
											returnType="dig";
										}
										else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(!vp.get(c+1).equals("."))
									{

											
											if(!typeChanged)
											{
											
													if(cp.get(c+1).equals("["))
													if(!isArrayType(vp.get(c)))
													{
														System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
														return false;
													}
											
											}
											else if(typeChanged)
											{
												typeChanged=false;
												
												if(!compTypeCast(dt,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
											
											}
												
											
										
									}
								}
								else if(opFlag) //Left Operator Exists
								{
									String rdt=getReturnTypeVar(vp.get(c));
									if(cp.get(c+1).equals("["))
									{
										if(!isArrayType(vp.get(c)))
										{
											System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
									}
									else
									{
										if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
										{
										
											
											rightOp=rdt;
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											leftOp=returnType;
											opFlag=true;
											
										}
										else //No more operators
										{
													if(!typeChanged)
													{
														
														
															rightOp=rdt;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
													
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=rdt;
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														
														else
														{
															rightOp=ChangeType;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
														}
													}
													
												
										
										}
									}
									
								}
							}
						
						}
						c++;
						
						if(exp21(cp,c))
						{
							c=count_int;
							return true;
						}
					}
					else if(words.isConst(cp.get(c)))
					{
						
						if(!opFlag) //No Left Operator Exists right now
						{
							
							 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
								{
								 startUniOp=false;
								 
									if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
									{
										returnType="dig";
									}
									else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
									{
										System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
								{
									
									startUniOp=false;
									
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									
										
								}
							else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
							{
												leftOp=words.getConst(cp.get(c)); //left operator
												opFlag=true;
							}
							else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
							{
								if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
								{
									returnType="dig";
								}
								else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
								{
									System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
									return false;
								}
									
							}
							else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
							{
								if(words.getConst(cp.get(c)).equals("boolean"))
								{
									returnType="boolean";
								}
								else 
								{
									System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
									return false;
								}
									
							}
							else
							{

									
										if(!typeChanged)
										{
											returnType=words.getConst(cp.get(c));
										}
										else if(typeChanged)
										{
											typeChanged=false;
											String temp=words.getConst(cp.get(c));
											if(!compTypeCast(temp,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
											else
												{
												returnType=ChangeType;
												}
										}
										
									
								
							}
						}
						else if(opFlag) //Left Operator Exists
						{
							if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
							{

								rightOp=words.getConst(cp.get(c));
								returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
								if(returnType.equals("x"))
								{
									System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								leftOp=returnType;
								opFlag=true;
								
							}
							else //No more operators
							{

										if(!typeChanged)
										{
												rightOp=words.getConst(cp.get(c));
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												opFlag=false;
											
												 
											
										}
										else if(typeChanged)
										{
											typeChanged=false;
											String temp=words.getConst(cp.get(c));
											if(!compTypeCast(temp,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
											else
											{
												rightOp=ChangeType;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												opFlag=false;
												
											}
										}
										
							
							}
						}
						c++;
						
						if(exp2(cp,c))
						{
							
							c=count_int;
							return true;
						}
					}
					else if(cp.get(c).equals("("))
					{
						c++;
						if(news(cp,c))
						{
						
							c=count_int;
							return true;
						}
					}
					if(FailedCounter<c)
					FailedCounter=c;
					return false;
			}
			boolean compTypeCast(String func,String Modified)
			{
				if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
				{
					if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
						return true;
					
				}
				else if(Modified.equals("beta"))
				{
					
					if(func.equals("beta"))
						return true;
				}
				
				return false;
			}
			boolean exp2(ArrayList<String>  cp,int c)
			{
				
					if(cp.size()<=c){count_int=cp.size()-1; return true; } 
					else
					{
						if(isOp(cp.get(c),c))
						{
							//..........
							c++;
							if(exp1(cp,c))
							{
								c=count_int;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c).equals(")"))
						{
							count_int=c;
							return true;			
						}
						else if(words.isParentFollow(cp.get(c),follow_of_Parent))
						{

							count_int=c;
							return true;
									
						}
						else if(cp.get(c).equals(",") && !ifParams)
						{
							count_int=c;
							
							return true;
						}
						else if(cp.get(c).equals("]"))
						{
							count_int=c;
							return true;
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean exp21(ArrayList<String>  cp,int c)
				{
					if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
					
					if(exp2(cp,c))
					{
						c=count_int;
						
						return true;
					}
				
					else if(cp.get(c).equals("["))
					{
						
						c++;
						
						if(words.isConst(cp.get(c)))
						{
							
							c++;
							
							if(cp.get(c).equals("]"))
							{
								c++;
								if(exp2(cp,c))
								{
									
									c=count_int;
									
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;	
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c).equals("ID"))
						{

							if(vp.get(c+1).equals("("))
							{
								if(!lookupFunction(vp.get(c)))
								{
									System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								
							}//Not function
							else //Maybe variable?
							{
								
									if(!lookUpVar(vp.get(c)))
									{
										System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									
								
							}
							c++;
							
							if(exp1(cp,c))
							{
								
								c=count_int;
						
									//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
								if(cp.get(c).equals("]"))
								{
									
									c++;
									if(exp2(cp,c))
									{
										
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c-1).equals("]"))
								{
									
									if(exp2(cp,c))
									{
										
										c=count_int;
										return true;
									}
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					
					else if(O1(cp,c))
					{
						
						c=count_int;
						if(exp2(cp,c))
						{
							
							c=count_int;
							count_int=c;
						
							if(c>=cp.size()){c=count_int=cp.size()-1;}
							return true;
						}
						else if(cp.get(c).equals("Dot operator"))
						{
							if(exp21(cp,c))
							{
								c=count_int;
								count_int=c;

								if(c>=cp.size()){c=count_int=cp.size()-1;}
								return true;
							}
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean params(ArrayList<String>  cp,int c)
				{
				
					if(cp.size()<=c){count_int=cp.size()-1;return true; }
					ifParams=true;

					if(cp.get(c).equals(")"))
					{
						c++;
						count_int=c;
						
						ifParams=false;
						return true;
					}
					else if(exp1(cp,c))
					{
						
						c=count_int;
						//System.out.println(cp.get(c)+":exp1 of params"+c);
						if(c<cp.size())
						{
							
							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								//System.out.println(cp.get(c)+":dF"+c);
								ifParams=false;
								return true;
							}
							else if(cp.get(c).equals("]"))
							{
								c++;
								count_int=c;
								//System.out.println("___"+cp.get(c));
								ifParams=false;
								return true;
							}
							else if(cp.get(c).equals(",")) //what if datatype?
							{
								
								c++;
								 if(cp.get(c).equals("ID"))
								 {
									 c++;
									 if(cp.get(c).equals("ID"))
									 {
										 c++;
										 if(params(cp,c))
										 {
												
												c=count_int;
												ifParams=false;
												return true;
									     }
									 }
								 }
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
						
							c=count_int;
							ifParams=false;
							return true;
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean news(ArrayList<String>  cp,int c)
				{
					
				if(isUniOp(cp.get(c)))
				{
					c++;
					if(exp1(cp,c))
					{
						
						c=count_int; 
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c=count_int;
								
								return true;
							}
						}
						else
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
							//c++;
							if(ifParams){count_int=c;return true;}
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
					}
				
				}
				else if(exp1(cp,c))
					{
						
						c=count_int; 
						//System.out.println(c+":33:"+cp.get(c));
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c=count_int;
								
								return true;
							}
							else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
							{
							   c=count_int;	
								return true;
							}
						}
						else
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
							//c++;
							if(ifParams){count_int=c;return true;}
							if(exp2(cp,c))
							{
								
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
						}
						else if(words.isParentFollow(cp.get(c), follow_of_Parent))
						{
							//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
						   c=count_int;	
							return true;
						}
					
					}
					if(cp.get(c).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c++;
						
						if(exp2(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
					}
					else if(cp.get(c).equals("datatype"))
					{
						ChangeType=vp.get(c);
						typeChanged=true;
						c++;
						if(cp.get(c).equals(")"))
						{
							
							c++;
							if(exp1(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								return true;
							}
						}
					}
					else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					{
						//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
					   c=count_int;	
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean O1(ArrayList<String>  cp,int c) // Incomplete
				{
					if(cp.get(c).equals("Dot operator"))
					{
						
						c++;
						
						if(cp.get(c).equals("ID"))
						{
							
						
							if(exp1(cp,c))
							{
								c=count_int;
								if(O1(cp,c))
								{
									c=count_int;
									count_int=c;
									//System.out.println(c+":fff:"+cp.get(c));
									return true;
								}
							}
						 if(cp.get(c).equals("["))
							{
								
								c++;
								//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
								if(words.isConst(cp.get(c)))
								{
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("333,"+c+","+cp.get(c));
										return true;
									}
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							else if(cp.get(c).equals("Dot operator"))
							{
								if(O1(cp,c))
								{
									c=count_int;
									count_int=c;
									//System.out.println(c+":fff:"+cp.get(c));
									return true;
								}
							}
							else if(cp.get(c).equals(")"))
							{
							
								c=count_int=c;
								if(ifParams)
								ifParams=false;
								return true;
							}
						}
						else if(words.isConst(cp.get(c)))
						{
							c++;
							count_int=c;
							return true;
						}
					}
					else if(cp.get(c).equals("&"))
					{
						c++;
						count_int=c;
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			String OperationOnOperands(String left,String right,String op,int cpInd)
			{
				if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
				{
					if(left.equals("dig"))
					{
						if(right.equals("dig"))
							return "dig";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "dig";
						
					}
					else if(left.equals("frac"))
					{
						if(right.equals("dig"))
							return "frac";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "frac";
						
					}
					else if(left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "alpha";
						else if(right.equals("frac"))
							return "alpha";
						else if(right.equals("alpha"))
							return "alpha";
						
					}
					
				}
				else if(op.equals("suma"))
				{
					if(left.equals("dig"))
					{
						if(right.equals("dig"))
							return "dig";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "dig";
						
					}
					else if(left.equals("frac"))
					{
						if(right.equals("dig"))
							return "frac";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "frac";
						
					}
					else if(left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "alpha";
						else if(right.equals("frac"))
							return "alpha";
						else if(right.equals("alpha"))
							return "alpha";
						
					}
					else if(left.equals("beta"))
					{
						if(right.equals("dig"))
							return "beta";
						else if(right.equals("frac"))
							return "beta";
						else if(right.equals("alpha"))
							return "beta";
						
						
					}
					
				}
				else if(cp.get(cpInd).equals("RO"))
				{
					if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "boolean";
						else if(right.equals("frac"))
							return "boolean";
						else if(right.equals("alpha"))
							return "boolean";
						
					}
					
				}
				else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
				{
					return "boolean" ;
				}
				return "x";
			}
			String valueOp(int index)
				{
					
					return vp.get(index);
				}
		
			boolean isOp(String cp,int c)
				{
					
					if(cp.equals("RO"))//
					{
						 return true;
					}
					else if(cp.equals("Assignment") && AllowAssignation)
					{
						return true;
					}
					else if(cp.equals("BWOP"))
					{
						if(valueOp(c).equals("bitOr"))
						 return true;
					}
					else if(cp.equals("BWOP"))
					{
						if(valueOp(c).equals("bitAnd"))
						 return true;
					}
					else if(cp.equals("OR"))
					{
						 return true;
					}
					else if(cp.equals("AddSum"))
					{
						 return true;
					}
					else if(cp.equals("Mux"))
					{
						
						 return true;
					}
					else if(cp.equals("DIVMOD"))
					{
						 return true;
					}
					else if(cp.equals("Incdec") || cp.equals("Not operator"))
					{
						 return true;
					}
					else if(cp.equals("LO"))
					{
						 return true;
					}

					
					
					return false;
				}
				
			boolean isUniOp(String a)
				{
					if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
					{
						
						return true;

					}
					
					return false;
				}
			
			
			
			 //<-- Finding functionExist or Not?
			 private boolean lookupFunction(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 						return true;
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return true;
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
				 					return true;
				 			} 
			 		}
			 		
			 		return false;
			 }

			private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				int i= indexOfParent(Parent,nameFunction);
				 if(i!=-1) 
					 return true;
				 return false;
			 }
			 private int indexOfParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
							 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
								 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
								 	return i;
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								return k;
						 }
					 }
					
				 return -1;
			 }
			
	//Finding functionExist or Not? -->
			 
	//<-- Getting paramList
			 private ArrayList<String> getFuncParams(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 					{
				 						//for(int p=0;p<tFunction.paramList.size();i++)
				 							
				 						return tFunction.paramList.get(i);
				 					}
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				
				 					return getParamsFunctionin_ClassParent(name,CurrentPname);
				 			} 
			 		}
			 		
			 		return null;
			 }

			private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				return getParamsParent(Parent,nameFunction);
				 
			 }
			 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
								 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
									 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								
						 }
					 }
					
				 return null;
			 }
			
	//Getting paramList  -->	
			 
	//<-- Getting returnType
			 private String getFuncReturnType(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 						return tFunction.ReturnType.get(i);
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				
				 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
				 			} 
			 		}
			 		
			 		return null;
			 }

			private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				return getReturnTypeParent(Parent,nameFunction);
				 
			 }
			 private String getReturnTypeParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
						
								 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
									 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								
						 }
					 }
					
				 return null;
			 }
			
	//Getting returnType  -->	
			private boolean lookUpVar(String name)
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return true;
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				if(LookUpVarin_ClassParent(name,CurrentPname))
		 					return true;
		 			} 
		 		 return false;
		 	 }
			private boolean LookUpVarin_ClassParent(String name,String Parent)
			 {
				int i= VarindexOfParent(Parent,name);
				 if(i!=-1) 
					 return true;
				 return false;
			 }
			private int VarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return i;
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								return k;
						 }
					 }
					
				 return -1;
			 }

			private String getReturnTypeVar(String name) //Method to call
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return tv.varDT.get(i);
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				return returnTypeVarin_ClassParent(name,CurrentPname);
		 					
		 			} 
		 		 return null;
		 	 }
			private String returnTypeVarin_ClassParent(String name,String Parent)
			 {
				return returnTypeVarindexOfParent(Parent,name);
				
			 }
			private String returnTypeVarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								
						 }
					 }
					
				 return null;
			 }

			private boolean isArrayType(String name) //Method to call
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return (tv.isVarArrayTable.get(i));
		 				 
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
		 					
		 			} 
		 		 return false;
		 	 }
			private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
			 {
				return isArrayTypeVarindexOfParent(Parent,name);
				
			 }
			private boolean isArrayTypeVarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								
						 }
					 }
					
				 return false;
			 }

			}

	//isArrayType

	//EXP Semantic if END

	 
	 
}
//MainClass Semantic End
//...........




































//////////////////////////////////////////..................................Simple Class.................................\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//Class Start

class ClassModel
{

	private KeyWords words = new KeyWords();
	public int count=0,count_int=0,failedCount=0;
	int scope=0;
	int start=0;
	ClassModel_Semantic cms ; 
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private	String follow_for_body[]={"]"};
		 private	ExpModel exp ;
		/* private	BodyModel body;*/
		 private classBodyModel body1;
		  tableVar tv = new tableVar();
		  boolean isInner=false;
		 private ClassModel(){}
	 public ClassModel(ArrayList<String> cp, ArrayList<String> vp,int scope,boolean isInner)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			follow_of_Parent=null;	
			cms = new ClassModel_Semantic(cp,vp,scope);
			this.isInner=isInner;
	 }
	 public ClassModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,boolean isInner)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			 cms = new ClassModel_Semantic(cp,vp,scope);
			 this.isInner=isInner;
			
	 }
	 public boolean isSomethingOpen=false;
	 public boolean getIsSomeThingOpen()
	 {
		 return isSomethingOpen; 
	 }
	 public void setIsSomeThingOpen(boolean tf)
	 {
		 isSomethingOpen=tf; 
	 }
	 public boolean classFunction(ArrayList<String> cp,int c)
	 {
		 start=c;
		 if(cp.get(c).equals("acces modifier") && !vp.get(c).equals("local"))
		 {
			
			 c++;
			 
			 if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 if(cp.get(c).equals("class"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.classbody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											if(!isInner)
											{
												if(cms.classFunction(cp,start) )
												{
													System.out.println("Class "+ (vp.get(start+3))+"  Semantic is right");
												}
												else
													System.out.println("Class "+ (vp.get(start+3))+"  Semantic is not right");
											}
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }	
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   	
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }//if stable not present.
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.classbody(cp,c))
								 {
									 c=body1.count;
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										if(!isInner)
										{
											if(cms.classFunction(cp,start))
											{
												System.out.println("Class "+ (vp.get(start+2))+" Semantic is right");
											}
											else
												System.out.println("Class "+ (vp.get(start+2))+" Semantic is not right");
										}
									
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								  
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 } // if access modifier not present
		 else if(cp.get(c).equals("type modifier"))
		 {
			 c++;
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.classbody(cp,c))
								 {
									 c=body1.count;
									 
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										if(!isInner){
										if(cms.classFunction(cp,start))
										{
											System.out.println("Class "+ (vp.get(start+2))+"  Semantic is right");
										}
										else
											System.out.println("Class "+ (vp.get(start+2))+"  Semantic is not right"); 
										}
										return true; //Successful parsed.
										
									}
									 if(failedCount<c)
								   			failedCount=c;
									   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }	
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
	
		 }//if stable+access modifier not present.
		 if(cp.get(c).equals("class"))
		 {
			 c++;
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 
				 if(S1(cp,c)) //Confirmation of params - (
				 {
					 c=count_int;
					 
						 if(cp.get(c).equals("["))
						 {
							 c++;
							 
							 if(body1.classbody(cp,c))
							 {
								 c=body1.count;

								if(cp.get(c).equals("]"))
								{
									c++;
									count=count_int=c;
									if(!isInner){
									if(cms.classFunction(cp,start))
									{
										System.out.println("Class "+ (vp.get(start+1))+" Semantic is right");
									}
									else
										System.out.println("Class Semantic is not right");}
									return true; //Successful parsed.
									
								}
								 if(failedCount<c)
							   			failedCount=c;
								   	
								
							 }
							 if(failedCount<c && body1.failedCount<c)
						   			failedCount=c;
							   	else if(body1.failedCount>c)
							   			failedCount=body1.failedCount;
						 }	
						 if(failedCount<c)
					   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		
		 }


		 return false;

	 }
	 private boolean S1_List(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals(","))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean S1(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("interface"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 if(cp.get(c).equals("inheritance"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 return false;
	 }
	 
	 

	 
	 
	 
//For Loop Start
	 class ForModel 
	 {
	  private KeyWords words = new KeyWords();
	  public int count=0,failedCount=0,scope=0;
	  private int count_int=0;
	  private ArrayList<String> cp = new ArrayList();
	  private ArrayList<String> vp = new ArrayList();
	  private String follow_of_Parent[];
	  private String follow_for_body[]={"]"};
	  private BodyModel body;
	  private tableVar tv = new tableVar();
	  private ForModel(){}
	  public ForModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	  {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	 			follow_of_Parent=null;
	   }
	   public ForModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	   {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			follow_of_Parent=Follow;	
	 			body=new BodyModel(cp,vp,follow_for_body,scope);
	   }
	   	public boolean For(ArrayList<String>cp,int c)
	   	{
	   		if(cp.get(c).equals("for loop"))
	   		{
	   			
	   			c++;
	   			
	   			if(cp.get(c).equals("("))
	   			{
	   				c++;
	   				
	   				if(X(cp,c))
	   				{
	   					c=count_int;
	   					
	   					if(Y(cp,c))
	   					{
	   						c=count_int;
	   						
	   						if(Z(cp,c))
	   						{
	   							c=count_int;
	   							//System.out.println(c+"Here:"+cp.get(c));
	   							if(cp.get(c).equals(")"))
	   							{
	   								c++;
	   								if(cp.get(c).equals("["))
	   								{
	   									c++;
	   									if(body.body(cp, c))
	   									{
	   										c=body.count;
	   										if(cp.get(c).equals("]"))
	   										{
	   											c++;
	   											count=count_int=c;
	   											return true;
	   										}
	   									}
	   								}
	   							}
	   						}
	   					}
	   				}
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean X(ArrayList<String>cp,int c)
	   	{
	   		declarationModel decl=new declarationModel(cp,vp,scope);
	   		
	   		if(decl.decl(cp, c))
	   		{
	   			
	   			c=decl.count;
	   			////System.out.println("Here"+cp.get(c-1));
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   			else if(cp.get(c-1).equals("Terminator"))
	   			{
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		else if(Assign(cp,c,"Terminator".split("\\ ")))
	   		{
	   			c=count_int;
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		else if(cp.get(c).equals("Terminator"))
	 		{
	   			c++;
	 			count_int=c;
	 			return true;
	 		}
	   		return false;
	   	}
	   	private  boolean Assign(ArrayList<String>cp,int c,String follow_for_exp_Assign[])
	   	{
	   		
	   		ExpModel expMain = new ExpModel(cp,vp,follow_for_exp_Assign,false);
	   		if(cp.get(c).equals("ID"))
	   		{
	   			c++;
	   			
	   			if(expMain.isOp(cp.get(c), c) && !cp.get(c).equals("Assignment"))
	   			{
	   				
	   				c++;
	   				if(cp.get(c).equals("Assignment"))
	   				{
	   					c++;
	   					if(expMain.exp(cp, c))
	   					{
	   						c=expMain.count;
	   						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	 	  						{
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	 	  						{
	 	  							c=c-1;
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	   							
	   					}
	   				}
	   			}
	   			else if(cp.get(c).equals("Assignment"))
	 			{
	   				
	 					c++;
	 					
	 					if(expMain.exp(cp, c))
	 					{
	 						c=expMain.count;
	 						//System.out.println(c+" Z:"+cp.get(c));
	 						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	 	  						{
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	 	  						{
	 	  							c=c-1;
	 	  							count_int=c;
	 	  							return true;
	 	  						}
	 							
	 					}
	 			}
	   			else if(cp.get(c).equals("Dot operator"))//if is object attribute;
	   			{
	   				c++;
	   				if(cp.get(c).equals("ID"))
	   		  		{
	   					c++;
	   		  			if(expMain.isOp(cp.get(c), c))
	   		  			{
	   		  				c++;
	   		  				if(cp.get(c).equals("Assignment"))
	   		  				{
	   		  					c++;
	   		  					if(expMain.exp(cp, c))
	   		  					{
	   		  						c=expMain.count;
	   		  						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   		  						{
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   		  						{
	   		  							c=c-1;
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  							
	   		  					}
	   		  				}
	   		  			}
	   		  			else if(cp.get(c).equals("Assignment"))
	   					{
	   							c++;
	   							if(expMain.exp(cp, c))
	   							{
	   								c=expMain.count;
	   								if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   		  						{
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   		  						{
	   		  							c=c-1;
	   		  							count_int=c;
	   		  							return true;
	   		  						}
	   									
	   							}
	   					}
	   		  			else if(cp.get(c).equals("["))//If Array exists
	   		  			{
	   		  				c++;
	   		  				String[] follow_for_exp_Assignment={"]"};
	   		  				ExpModel exp1 = new ExpModel(cp,vp,follow_for_exp_Assignment,false);
	   		  				if(exp1.exp(cp, c))
	   		  				{
	   		  					c=exp1.count;
	   		  					if(cp.get(c).equals("]"))
	   		  					{
	   		  						c++;
	   		  					if(exp1.isOp(cp.get(c), c))
	   		  		  			{
	   		  		  				c++;
	   		  		  				if(cp.get(c).equals("Assignment"))
	   		  		  				{
	   		  		  					c++;
	   		  		  					if(expMain.exp(cp, c))
	   		  		  					{
	   		  		  						c=expMain.count;
	   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  		  							
	   		  		  					}
	   		  		  				}
	   		  		  			}
	   		  		  			else if(cp.get(c).equals("Assignment"))
	   		  					{
	   		  							c++;
	   		  							if(expMain.exp(cp, c))
	   		  							{
	   		  								c=expMain.count;
	   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  									
	   		  							}
	   		  					}
	   		  					}
	   		  					else if(cp.get(c-1).equals("]"))
	   		  					{
	   		  						
	   		  					if(exp1.isOp(cp.get(c), c))
	   		  		  			{
	   		  		  				c++;
	   		  		  				if(cp.get(c).equals("Assignment"))
	   		  		  				{
	   		  		  					c++;
	   		  		  					if(expMain.exp(cp, c))
	   		  		  					{
	   		  		  						c=expMain.count;
	   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  		  							
	   		  		  					}
	   		  		  				}
	   		  		  			}
	   		  		  			else if(cp.get(c).equals("Assignment"))
	   		  					{
	   		  							c++;
	   		  							if(expMain.exp(cp, c))
	   		  							{
	   		  								c=expMain.count;
	   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
	   	  		  						{
	   	  		  							c=c-1;
	   	  		  							count_int=c;
	   	  		  							return true;
	   	  		  						}
	   		  									
	   		  							}
	   		  					}
	   		  					}
	   		  				}
	   		  			}
	   		  		}
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean Y(ArrayList<String>cp,int c)
	   	{
	   		String [] follow_for_exp={"Terminator"};
	   		ExpModel exp = new ExpModel(cp,vp,follow_for_exp,false);
	   		
	   		if(exp.exp(cp, c))
	   		{
	   			c=exp.count;
	   			
	   			if(cp.get(c).equals("Terminator"))
	   			{
	   				c++;
	   				count_int=c;
	   				return true;
	   			}
	   			else if(cp.get(c-1).equals("Terminator"))
	   			{
	   				count_int=c;
	   				return true;
	   			}
	   		}
	   		return false;
	   	}
	   	private  boolean Z(ArrayList<String>cp,int c)
	   	{
	   		
	   		
	   		
	   		if(cp.get(c).equals("Incdec"))
	   		{
	   			c++;
	   			if(cp.get(c).equals("ID"))
	   			{
	   				c++;
	   				if(cp.get(c).equals(")"))
	   				{
	   					count_int=c;
	   					return true;
	   				}
	   			}
	   		}
	   		else if(cp.get(c).equals("ID"))
	 		{
	   			
	 				c++;
	 				
	 				if(cp.get(c).equals("Incdec"))
	 		  		{
	 		  			c++;
	 		  				if(cp.get(c).equals(")"))
	 		  				{
	 		  					count_int=c;
	 		  					return true;
	 		  				}
	 		  			
	 		  		}
	 				else if(Assign(cp,c-1,")".split("\\ "))) //Passed ID's index.
	 		  		{
	 					
	 		  			c=count_int;
	 		  			if(cp.get(c).equals(")"))
	 		  			{
	 		  				
	 		  				count_int=c;
	 		  				return true;
	 		  			}
	 		  		}
	 				
	 		}
	   		return false;
	   	}
	 }
	 
//For Loop End
	 

	//ClassBodyModel Start
	class classBodyModel {
		KeyWords words = new KeyWords();
		int count=0,count_int=0,failedCount=0,scope=0;
		ArrayList<String> cp = new ArrayList();
		ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[]; //Usually ]
		String follow_for_exp[]={"Terminator"};
		ExpModel exp ;
		declarationModel decl;
		//functionCallModel functCall;
		
		classBodyModel(){}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	{
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);
		
	}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	{
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);

	}
	public boolean classbody(ArrayList<String> cp,int c)
	{
		
		 if(b_mst(cp,c))
		 {
			 c=count_int;
			 count=c;
			 
			 return true;
		 }
		 return false;
	}
	public boolean b_mst(ArrayList<String> cp,int c)
	{
		 if(cp.get(c).equals("datatype"))
		 {
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 
			 //Declaration can occur
			 if(decl.decl(cp, c))
			 {
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 	 else if(fdm.func_def(cp, c))
			 {
				 
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && fdm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && decl.failedCount<fdm.failedCount)
					failedCount=fdm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>fdm.failedCount)
					failedCount=decl.failedCount;
		 }
		 else if(cp.get(c).equals("ID"))
		 {
			 ConstructorModel cm= new ConstructorModel(cp,vp,scope);
			 //Declaration can occur
			 
			 if(decl.decl(cp, c))
			 {
				 
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cm.construct_def(cp, c))
			 {
				 c=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && decl.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>cm.failedCount)
					failedCount=decl.failedCount;
		 }
		 else if(cp.get(c).equals("acces modifier") || cp.get(c).equals("type modifier") )
		 {
			 ClassModel cm= new ClassModel(cp,vp,scope+1,true);
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				
				 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
				 {
					 int temp=c+1;
					 
					 if(cp.get(temp).equals("type modifier") && vp.get(temp).equals("stable"))
					 {
						 temp++;
						 
						 if(cp.get(temp).equals("void"))
						 {
							 temp++;
							
							 if(cp.get(temp).equals("main"))
							 { 
								 
								 count_int=c;
								
								 return true;
							 }
						 }
						 
					 }
				 }
				 else if(!cp.get(c).equals("acces modifier"))
				 {
					 count_int=c;
					 return true; 
				 }
				 
			 }
			 else if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(fdm.func_def(cp, c))
			 {
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && im.failedCount<c && fdm.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && im.failedCount<cm.failedCount  && fdm.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<im.failedCount && im.failedCount>c && im.failedCount>cm.failedCount  && im.failedCount<fdm.failedCount)
					failedCount=im.failedCount;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && fdm.failedCount>cm.failedCount  && im.failedCount>fdm.failedCount)
					failedCount=im.failedCount;
		 }
		 else if(cp.get(c).equals("class"))
		 {
			 ClassModel cm= new ClassModel(cp,vp,scope,true);
			 if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<cm.failedCount)
		   			failedCount=cm.failedCount;
			 else
				 cm.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("sculpture"))
		 {
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<im.failedCount)
		   			failedCount=im.failedCount;
			 else
				 im.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("void"))
		 {
			
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 if(fdm.func_def(cp, c))
			 {
				 
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<fdm.failedCount)
		   			failedCount=fdm.failedCount;
			 else
				 fdm.failedCount=failedCount;
		 } 
		 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
		 {
			 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
			 {
				 int temp=c+1;
				 if(cp.get(temp).equals("type modifier") && cp.get(temp).equals("stable"))
				 {
					 temp++;
					 if(cp.get(temp).equals("main"))
					 { 
						 count_int=c;
						 return true;
					 }
					 if(failedCount<temp)
				   			failedCount=temp;
				 }
			 }
			 else if(!cp.get(c).equals("acces modifier"))
			 {
				 count_int=c;
				 return true; 
			 }
			 
		 }/*
		 if(failedCount<c)
				failedCount=c;
		
		 */
		 return false;
	}

	}

	//ClassBodyModel End


	 
	 //Interface
	class InterfaceModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private interfaceBodyModel body1;
			 private InterfaceModel(){}
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp);
				this.scope=scope;
				body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
				follow_of_Parent=null;
		 }
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp);
				//body=new BodyModel(cp,vp,follow_for_body);
				body1=new interfaceBodyModel(cp,vp,follow_for_body,scope);
				
		 }
		
		 public boolean interfaceFunction(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   	
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }//if stable not present.
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }	
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
		
			 }//if stable+access modifier not present.
			 if(cp.get(c).equals("sculpture"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 
								 if(body1.InternBody(cp,c))
								 {
									c=body1.count;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }

			
			 return false;

		 }
		 private boolean S1_List(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean S1(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("interface"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
			
					 
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 if(cp.get(c).equals("inheritance"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 
								 c++;
								 if(cp.get(c).equals("["))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 count_int=c;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 c++;
					 if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
	}
	
//Interfaces  End
	
//Interface Body Semantic Start
	class interfaceBodyModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
		 private tableFunction tf = new tableFunction();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private interfaceBodyModel(){}
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=null;
		 }
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;			
		 }
		 public boolean InternBody(ArrayList<String> cp,int c)
		 {
			 if(multiprototypes(cp,c))
			 {
				count=c=count_int;
				
				
				return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean multiprototypes(ArrayList<String>cp,int c)
		 {
			 if(prototypeFun(cp,c))
			 {
				c=count_int;
				if(multiprototypes(cp,c))
				 {
					c=count_int;
					return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean prototypeFun(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
				
					 c++;
					 
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 c++;
						 
						 if(params(cp, c))
						 {
							 
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								count_int=c;
								return true;
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 return false;
		 }
		 private  ArrayList<String> params = new ArrayList();	
		 private ArrayList<String> getParams()
		 {
			 return params;
		 }
		 private void SetParams(String param)
		 {
			 params.add(param);
		 }
		 private tableVar tv = new tableVar();
		 private boolean lookUpVar(String name)
	 	 {
			 if(tv.varDT.size()>0)
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
	 		 return false;
	 	 }
		 private boolean params(ArrayList<String> cp,int c)
		 {
			 if(fields(cp,c))
			 {
				 c=count_int;
				 
				 return true;
			 }
			 else if(Object(cp, c))
			 {
				 c=count_int;
					count_int=c;
					return true;
			 }
			 else if(cp.get(c).equals(")"))
			 {
				 count_int=++c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(cp.get(count).equals("datatype"))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							count++;
							
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								//System.out.println(count+":rr:"+cp.get(count));
								return true;
							}
							/*else
							{
								
								count--;
								//System.out.println(count+":rr:"+cp.get(count));
								if(Object(classPart, count))
								 {
									 count=count_int;
										count_int=count;
										
										return true;
								 }
							}*/
							if(failedCount<count)
					   			failedCount=count;
						}
					 if(failedCount<count)
				   			failedCount=count;
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
						count_int=count;
						
						return true;
				 }
				 if(failedCount<count)
			   			failedCount=count;
				 
			 }
			 else if(classPart.get(count).equals(")"))
			 {
				 count_int=count;
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			   
			 return false;
		 }
		 private boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					
						 if(Ob(cp, c))
						 {
							c=count_int;
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
								 
							 }
							 else
							 {
								 
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals(")"))
								 {
									 
									 count_int=c=c+1;
									 
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
			 return false;
		 }
		 private boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 
				 if(cp.get(c).equals(","))
				 {
					 c++;
					
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 
						if(cp.get(c).equals("ID"))
						{
							
							c++;
						
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals(")"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
								 else
									 if(cp.get(c-1).equals(")"))
									 {
										 
										 count_int=c;
										 return true;
									 }
										
							
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 
							
						}
						if(failedCount<c)
				   			failedCount=c;
					 }
					 else if(cp.get(c).equals("datatype"))
					 {
					
						 if(fields(cp, c))
						 {
							 c=count_int;
								count_int=c;
								return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else  if(cp.get(c).equals(")"))
				 {
					 c++;
					 count_int=c;
					 
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
				 return false;
		 }

		 private boolean fields(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					
					c++;
					
					 if(list(cp,c))
					{
						c=count_int;
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								
								count_int=c;
								
								return true;
							}
							if(failedCount<c)
					   			failedCount=c;
						}
						else if(cp.get(c).equals(")"))
						{
							c++;
							
							count_int=c;

							return true;
						}
						else if(cp.get(c-1).equals(")"))
						{

							count_int=c;

							return true;
						}
						if(failedCount<c)
				   			failedCount=c;
					}
					 if(failedCount<c)
				   			failedCount=c;
				}
				if(failedCount<c)
		   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 
			 return false;
		 }
	}
//Interface Body  End
	 
	 
	 
	 
	 
	 
	 
	 
	///DECLARATION START
	class declarationModel 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
			 ArrayList<String> varTable = new ArrayList();
		String follow_of_Parent;
		String follow_for_exp[]={"Terminator","]"};
		ExpModel exp ;


		declarationModel(){}
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			exp = new ExpModel(cp,vp,follow_for_exp);
			
	 }
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp);
			
	 }
	 private boolean field(ArrayList<String> classPart,int count)
	 {
		 if(classPart.get(count).equals("datatype"))
		 {
			 count++;
			
			if(classPart.get(count).equals("ID"))
			{
				
				count++;
				
				if(init(classPart,count))
				{
					count=count_int;
					
					if(list(classPart,count))
					{
						
						count=count_int;
						
						//System.out.println(count+"e"+classPart.get(count));
						if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
							  
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						this.count=count;
						count_int=count;
						exp.count_int=0;
						
						return true;
					}
				}
				else if(list(classPart,count))
				{
					count=count_int;
					if(count>=classPart.size())
					{
						if(classPart.get(count-1).equals("Terminator"))
						{
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
					if(classPart.get(count).equals("Terminator"))
					{
						
						count++;
						
						count_int=count;
						exp.count_int=0;
						//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
						return true;
					}
				}
				else if(classPart.get(count).equals("Terminator"))
				{
					count++;
					//this.count=count;
					count_int=count;
					exp.count_int=0;
					return true;
				}
			}
		 }
		 return false;
	 }
	 public boolean decl(ArrayList<String> classPart,int count)
	 {
		
		 
		 if(field(classPart,count))
		 {
			 	count=count_int;
			 	this.count=count;
				count_int=0;
				exp.count_int=0;
			
	
				return true;
		 }
		 else if(Object(classPart, count))
		 {
			 count=count_int;
			 this.count=count;
			
				count_int=0;
				exp.count_int=0;
				
				return true;
		 }

		 return false;
	 }
	 boolean init(ArrayList<String> classPart,int count)
	 {
		 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
		    if(classPart.get(count).equals("Assignment"))
			{
		    	
				count++;
				
				if(exp.exp(classPart, count))
				{
					//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			}
			else if(array(classPart,count))
			{
				count=count_int;
				count_int=count;
				return true;
			}
			else if(classPart.get(count).equals(","))
			{
				count++;
				count_int=count;
				return true;
			}
			
				return false;
	 }
	 boolean list(ArrayList<String> classPart,int count)
	 {
		 
		 //System.out.println(count+"e"+classPart.get(count));
		 if(classPart.get(count).equals(","))
		 {
			 count++;
			 if(classPart.get(count).equals("ID"))
				{
					count++;
					if(init(classPart,count))
					{
						count=count_int;
						if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
						
					}
					else if(list(classPart,count))
					{
						count=count_int;
						count_int=count;
						return true;
					}
				}
		 }
		 else if(classPart.get(count).equals("Terminator"))
		 {
			// count++;
			 count_int=count;
				return true;
		 }
		 return false;
	 }
	 
	 //Other models
	 boolean array(ArrayList<String> classPart,int count)
	 {
		 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
		 if(classPart.get(count).equals("Assign Op"))
		 {
			count++;
			if(exp.exp(classPart, count))
			{
				count_int=exp.count;
				count=count_int;
				count_int=count;
				return true;
			}
		 }
		 else if(classPart.get(count).equals("Assignment"))
		 {
			 count++;
			 if(classPart.get(count).equals("["))
			 {
				 count++;
				 if(words.isConst(classPart.get(count)))
				 {
					 count++;
					 if(clist1(classPart,count))
					 {
						 count=count_int;
						 return true;
					 }
				 }
				 else if(classPart.get(count).equals("]"))
				 {
					 count=count_int;
						count_int=count;
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 boolean clist1(ArrayList<String> cp,int c)
	 {
	 	if(c>=cp.size()){ c=cp.size()-1; return true;}
	 	 if(cp.get(c).equals(","))			//Call to clist1
	 	 {
	 		 c++;
	 		 if(words.isConst(cp.get(c)))
	 		 {
	 			 c++;
	 			 if(clist1(cp,c))
	 			 {
	 				 c=count_int;
					 count_int=c;
					 return true;
				 
	 			 }
	 			 
	 		 }
	 	 }
	 	 else if(cp.get(c).equals("]"))
	 	 {
	 		c++;
			 count_int=c;
			 return true;
	 	 }
	 	return false;
	 } 
	 boolean Object(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		
		 if(cp.get(c).equals("ID"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 
				 c++;
				 
				 if(obj1(cp,c))
				 {
					 c=count_int;
					 
					 if(Ob(cp, c))
					 {

						 if(c>=cp.size())
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
						 }
						 else
						 {
							
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								 c++;
							
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
			 }
		 }
		 return false;
	 }
	 boolean obj1(ArrayList<String> cp, int c)
	 {
		 if(cp.get(c).equals("Assignment"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 if(exp.exp(cp, c))
				 {
					 c=exp.count;
					 count_int=c;
					return true;
				 }
			 }
			
		 }
		 else if(cp.get(c).equals("["))
		 {
			 
			 c++;
			 //System.out.println("GG"+cp.get(c));
			 if(words.isConst(cp.get(c)))
			 {
				 c++;
				 if(cp.get(c).equals("]"))
				 {
					 c++;
					 //Here init_ob
					 
					if(init_ob(cp,c))
					{
						c=count_int;
						 return true;
					}
				 }
			 }
			 else if(exp.exp(cp, c))
			 {
				 
				 c=exp.count;
				
				 count_int=c;
				 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
				 else
				 {
					 
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
				 }
				
			 }
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 
			 c++;
			 count_int=c;
			 
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean init_ob(ArrayList<String> cp,int c)
	 {
		if(c>=cp.size()) return true; 
		 if(cp.get(c).equals("Assignment"))
		 {
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 
				 if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 count_int=c;
					return true;
				 }
		/*		 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						 c=exp.count;
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 c++;
						 if(words.isConst(cp.get(c)))
						 {
							 c++;
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
		*/		 
			 }
			 
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean Ob(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 else
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID")) //obj1
				 {
					 c++;
					 if(obj1(cp,c))
					 {
						 c=count_int;
						 if(Ob(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
							 else  if(cp.get(c-1).equals("Terminator"))
							 {
								
								 
								 count_int=c;
								 return true;
							 }
						 }
					 }
						
				 }
			 }
			 else  if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				
				 
				 count_int=c;
				 return true;
			 }

		 }
			 return false;
	 }
	}
	//DECLARATION COMPLETED

	

	//EXP START

	//EXP START

	class ExpModel 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,FailedCounter=0;
		 String follow_of_Parent[];
		 ArrayList<String> cp = new ArrayList();
		 ArrayList<String> vp = new ArrayList();
		 boolean ifParams=false;
		 public boolean AllowAssignation=true;
		ExpModel(){}
		public ExpModel(ArrayList cp,ArrayList vp)
		{
			this.cp=cp;
			this.vp=vp;
		}
		public ExpModel(ArrayList cp,ArrayList vp,String follow[])
		{
			this.cp=cp;
			this.vp=vp;
			
			follow_of_Parent=follow;
		}
		public ExpModel(ArrayList cp,ArrayList vp,boolean allow)
		{
			this.cp=cp;
			this.vp=vp;
			AllowAssignation=allow;
		}
		public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow)
		{
			this.cp=cp;
			this.vp=vp;
			
			follow_of_Parent=follow;
			AllowAssignation=allow;
		}
		
	public 	boolean exp(ArrayList<String> ClassPart,int count)
		{
			if(isUniOp(ClassPart.get(count)))
			{
				count++;
				if(exp1(ClassPart,count))
				{
					count=count_int;
					this.count=count;
					return true;
				}
			}
			else if(exp1(ClassPart,count))
			{
				
				count=count_int;
				count_int=0;
				this.count=count;
				return true;
			}
			else if(words.isParentFollow(cp.get(count), follow_of_Parent))
			{
				count=count_int;
				this.count=count;
				return true;
			}
			if(FailedCounter<count)
				FailedCounter=count;
			return false;
		}

	boolean exp1(ArrayList<String> cp,int c)
	{
			if(cp.size()<=c){count_int=cp.size()-1;return true; } 
			if(cp.get(c).equals("ID"))
			{
				
				c++;
				
				if(exp21(cp,c))
				{
					c=count_int;
					return true;
				}
			}
			else if(words.isConst(cp.get(c)))
			{
				
				c++;
				
				if(exp2(cp,c))
				{
					int cc=c;
					c=count_int;
					return true;
				}
			}
			else if(cp.get(c).equals("("))
			{
				c++;
				if(news(cp,c))
				{
				
					c=count_int;
					return true;
				}
			}
			if(FailedCounter<c)
			FailedCounter=c;
			return false;
	}
	boolean exp2(ArrayList<String>  cp,int c)
	{
		
			if(cp.size()<=c){count_int=cp.size()-1; return true; } 
			else
			{
				if(isOp(cp.get(c),c))
				{
					c++;
					if(exp1(cp,c))
					{
						c=count_int;
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
				}
				else if(cp.get(c).equals(")"))
				{
					count_int=c;
					return true;			
				}
				else if(words.isParentFollow(cp.get(c),follow_of_Parent))
				{

					count_int=c;
					return true;
							
				}
				else if(cp.get(c).equals(",") && !ifParams)
				{
					count_int=c;
					return true;
				}
				else if(cp.get(c).equals("]"))
				{
					count_int=c;
					return true;
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean exp21(ArrayList<String>  cp,int c)
		{
			if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
			
			if(exp2(cp,c))
			{
				c=count_int;
				return true;
			}
			else if(cp.get(c).equals("("))
			{
				if(params(cp,c))
				{
					c=count_int; 
					
					if(c<cp.size())
					{
						
						if(cp.get(c).equals(")"))
						{
							c++;
						
							if(cp.get(c).equals("Dot operator"))
							{
								c++;
								if(cp.get(c).equals("("))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										return true;
									}
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(exp2(cp,c))
							{
								c=count_int; 	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c-1).equals(")"))
						{

							if(cp.get(c).equals("Dot operator"))
							{
								c++;
								if(cp.get(c).equals("("))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(exp2(cp,c))
							{
								c=count_int; 	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(words.isParentFollow(cp.get(c),follow_of_Parent))
						{
							

							count_int=c;
							
							return true;
									
						}
					}
					else if(cp.get(c-1).equals(")"))
				    {
						c=count_int;
						
						return true;
					}
				
				}
			}
			else if(cp.get(c).equals("["))
			{
				
				c++;
				
				if(words.isConst(cp.get(c)))
				{
					
					c++;
					
					if(cp.get(c).equals("]"))
					{
						c++;
						if(exp2(cp,c))
						{
							
							c=count_int;
							
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;	
					}
					if(FailedCounter<c)
						FailedCounter=c;
				}
				else if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					if(exp1(cp,c))
					{
						
						c=count_int;
				
							//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
						if(cp.get(c).equals("]"))
						{
							
							c++;
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c-1).equals("]"))
						{
							
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					if(FailedCounter<c)
						FailedCounter=c;
				
				}
				if(FailedCounter<c)
					FailedCounter=c;
			}
			
			else if(O1(cp,c))
			{
				
				c=count_int;
				if(exp2(cp,c))
				{
					
					c=count_int;
					count_int=c;
				
					if(c>=cp.size()){c=count_int=cp.size()-1;}
					return true;
				}
				else if(cp.get(c).equals("Dot operator"))
				{
					if(exp21(cp,c))
					{
						c=count_int;
						count_int=c;

						if(c>=cp.size()){c=count_int=cp.size()-1;}
						return true;
					}
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean params(ArrayList<String>  cp,int c)
		{
		
			if(cp.size()<=c){count_int=cp.size()-1;return true; }
			ifParams=true;

			if(cp.get(c).equals(")"))
			{
				c++;
				count_int=c;
				
				ifParams=false;
				return true;
			}
			else if(exp1(cp,c))
			{
				
				c=count_int;
				//System.out.println(cp.get(c)+":exp1 of params"+c);
				if(c<cp.size())
				{
					
					if(cp.get(c).equals(")"))
					{
						c++;
						count_int=c;
						//System.out.println(cp.get(c)+":dF"+c);
						ifParams=false;
						return true;
					}
					else if(cp.get(c).equals("]"))
					{
						c++;
						count_int=c;
						//System.out.println("___"+cp.get(c));
						ifParams=false;
						return true;
					}
					else if(cp.get(c).equals(","))
					{
						
						c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 c++;
								 if(params(cp,c))
								 {
										
										c=count_int;
										ifParams=false;
										return true;
							     }
							 }
						 }
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
				
					c=count_int;
					ifParams=false;
					return true;
				}
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean news(ArrayList<String>  cp,int c)
		{
			
		if(isUniOp(cp.get(c)))
		{
			c++;
			if(exp1(cp,c))
			{
				
				c=count_int; 
				
				if(c>=cp.size())
				{
					if(cp.get(c-1).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c=count_int;
						
						return true;
					}
				}
				else
				if(cp.get(c).equals(")"))
				{
					if(ifParams){count_int=c;return true;}
					c++;
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
					//c++;
					if(ifParams){count_int=c;return true;}
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
			}
		
		}
		else if(exp1(cp,c))
			{
				
				c=count_int; 
				//System.out.println(c+":33:"+cp.get(c));
				if(c>=cp.size())
				{
					if(cp.get(c-1).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c=count_int;
						
						return true;
					}
					else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
					{
					   c=count_int;	
						return true;
					}
				}
				else
				if(cp.get(c).equals(")"))
				{
					if(ifParams){count_int=c;return true;}
					c++;
					
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
				else if(cp.get(c-1).equals(")"))
				{
					//c++;
					if(ifParams){count_int=c;return true;}
					if(exp2(cp,c))
					{
						
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
					else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					{
						//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
					   c=count_int;	
						return true;
					}
				}
				else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				{
					//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
				   c=count_int;	
					return true;
				}
			
			}
			if(cp.get(c).equals(")"))
			{
				if(ifParams){count_int=c;return true;}
				c++;
				
				if(exp2(cp,c))
				{
					c=count_int; //count_int=0; //count_int=0;
					//count_int=0;
					
					return true;
				}
			}
			else if(cp.get(c).equals("datatype"))
			{
				
				c++;
				if(cp.get(c).equals(")"))
				{
					
					c++;
					if(exp1(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						return true;
					}
				}
			}
			else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			{
				//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
			   c=count_int;	
				return true;
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	boolean O1(ArrayList<String>  cp,int c) // Incomplete
		{
			if(cp.get(c).equals("Dot operator"))
			{
				
				c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					if(cp.get(c).equals("("))
					{
						
						if(params(cp,c))
						{
							
							c=count_int; //count_int=0; 
							//System.out.println(c+":ttt:"+cp.get(c));
							if(cp.get(c).equals(")"))
							{
								c++;
								if(cp.get(c).equals("Terminator"))
								{
									c++;
									count_int=c;
									
									return true;
								}
								else
								{
									count_int=c;
									
									return true;
								}
							}
							else if(cp.get(c).equals("Terminator"))
							{
								//System.out.println(count_int+"He"+cp.get(count_int));
								//c++;
							c=count_int;
							////System.out.println("444,"+c+","+cp.get(c));
								return true;
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"d3dHe"+cp.get(count_int));
							   c=count_int;	
							
								return true;
							}
							else if(cp.get(c-1).equals(")"))
							{
								
								if(cp.get(c).equals("Terminator"))
								{
									c++;
									count_int=c;
									
									return true;
								}
								else
								{
									count_int=c;
									
									return true;
								}
							}
						}
						
					}
					else  if(cp.get(c).equals("["))
					{
						
						c++;
						//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
						if(words.isConst(cp.get(c)))
						{
							c++;
							
							if(cp.get(c).equals("]"))
							{
								c++;
								count_int=c;
								//System.out.println("333,"+c+","+cp.get(c));
								return true;
							}
						}
					}
					else if(cp.get(c).equals("&"))
					{
						c++;
						count_int=c;
						return true;
					}
					else if(cp.get(c).equals("Dot operator"))
					{
						if(O1(cp,c))
						{
							c=count_int;
							count_int=c;
							//System.out.println(c+":fff:"+cp.get(c));
							return true;
						}
					}
				}
				else if(words.isConst(cp.get(c)))
				{
					c++;
					count_int=c;
					return true;
				}
			}
			else if(cp.get(c).equals("&"))
			{
				c++;
				count_int=c;
				return true;
			}
			if(FailedCounter<c)
				FailedCounter=c;
			return false;
		}
	String valueOp(int index)
		{
			
			return vp.get(index);
		}
	boolean isOp(String cp,int c)
		{
			
			if(cp.equals("RO"))
			{
				 return true;
			}
			else if(cp.equals("Assignment") && AllowAssignation)
			{
				return true;
			}
			else if(cp.equals("BWOP"))
			{
				if(valueOp(c).equals("bitOr"))
				 return true;
			}
			else if(cp.equals("BWOP"))
			{
				if(valueOp(c).equals("bitAnd"))
				 return true;
			}
			else if(cp.equals("OR"))
			{
				 return true;
			}
			else if(cp.equals("AddSum"))
			{
				 return true;
			}
			else if(cp.equals("Mux"))
			{
				
				 return true;
			}
			else if(cp.equals("DIVMOD"))
			{
				 return true;
			}
			else if(cp.equals("Incdec") || cp.equals("Not operator"))
			{
				 return true;
			}
			else if(cp.equals("LO"))
			{
				 return true;
			}

			
			
			return false;
		}
		
	boolean isUniOp(String a)
		{
			if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
			{
				return true;
			}
			return false;
		}
	}

	
	//While Loop Start
	class WhileModel 
	{
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private	BodyModel body;
			 private WhileModel(){}
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				body=new BodyModel(cp,vp,follow_for_body,scope);
				follow_of_Parent=null;
		 }
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				
		 }
		 public boolean test(ArrayList<String>cp,int c)
		 {
				
				if(cp.get(c).equals("while loop"))
				 {
					 c++;
					 if(cp.get(c).equals("("))
					 {
						 
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 //System.out.println("FF"+c);
							 if(cp.get(c).equals(")"))
							{
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //call for body
									  
									   if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		
									   		c++;
									   		if(c>cp.size()-1){c=count_int=c;
								   			count=count_int;
								   			return true;}
									   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   	}
									   	if(failedCount<c)
								   			failedCount=c;
									   }
									   if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
									  
								 }
								 if(failedCount<c)
							   			failedCount=c;
							}
							 //if(c.prev is equal to )
							 if(cp.get(c-1).equals(")"))
								{
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 //call for body
										  if(body.body(cp,c))
										   {
										   	c=body.count;
										   	if(cp.get(c).equals("]"))
										   	{
										   		c++;
										   		if(c>cp.size()-1){c=count_int=c;
									   			count=count_int;
									   			return true;}
										   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		else
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   	}
										   	if(failedCount<c)
									   			failedCount=c;
										   }
										  if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				if(failedCount<c)
		   			failedCount=c;
				 return false;
				 
		 }
		
	}
	//While Loop End
	
	//IF-ELSE Start
	class IfElseModel {
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={")","["};
		String follow_for_body[]={"]"};
		ExpModel exp ;
		
		IfElseModel(){}
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				this.scope=scope;
				follow_of_Parent=null;
		 }
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				
		 }
		public  boolean ifElse(ArrayList<String> cp,int c)
		 {
			
			
			
			if(cp.get(c).equals("si"))
			 {
				
				 c++;
				 if(cp.get(c).equals("("))
				 {
					 
					 if(exp.exp(cp, c))
					 {
						
						 c=exp.count;
						 
						 if(cp.get(c).equals(")"))
						{
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 //call for body
								  if(!cp.get(c).equals("]"))
								  {
									  BodyModel body;
									  body=new BodyModel(cp,vp,follow_for_body,scope);
									  if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		c++;
									   		if(oelse(cp,c))
									   		{
									   			c=count_int;
									   			count=count_int;
									   		
									   			return true;
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		
									   		
									   	}
									   	if(failedCount<c)
									   	{
									   		failedCount=c;
									   	}
									   }
									  if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c && failedCount<body.failedCount)
									   			failedCount=body.failedCount;
								  }
								  else if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								  if(failedCount<c )
							   			failedCount=c;
							
							 }
							 if(failedCount<c )
						   			failedCount=c;
						}
						 //if(c.prev is equal to )
						 if(cp.get(c-1).equals(")"))
							{
							 if(cp.get(c).equals("["))
							 {
								 c++;
							  if(!cp.get(c).equals("]"))
							  {
								  BodyModel body;
								  body=new BodyModel(cp,vp,follow_for_body,scope);
								  //System.out.println("Here idiot: "+cp.get(c));
								  if(body.body(cp,c))
								   {
								   	c=body.count;
								   	
								   	if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								   	if(failedCount<c)
								   		failedCount=c;
								   }
								  if(failedCount<c && body.failedCount<c)
							   			failedCount=c;
								   	else if(body.failedCount>c && failedCount<body.failedCount)
								   			failedCount=body.failedCount;
							  }
							  else if(cp.get(c).equals("]"))
							   	{
							   		c++;
							   		if(oelse(cp,c))
							   		{
							   			c=count_int;
							   			count=count_int;
							   			
							   			return true;
							   		}
							   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   		else
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   	}
							  if(failedCount<c )
						   			failedCount=c;
							   
							 }
							 	if(failedCount<c)
						   			failedCount=c;
							   
							}
							if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c && exp.FailedCounter<c)
				   			failedCount=c;
					   	else if(exp.FailedCounter>c && failedCount<exp.FailedCounter)
					   			failedCount=exp.FailedCounter;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				  
			 }
			
			 return false;
			 
		 }
		 boolean oelse(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("sino"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("["))
				 {
					 c++;
					 
					if(!cp.get(c).equals("]"))
					{
						 //call for body
						
						 BodyModel body=new BodyModel(cp,vp,follow_for_body,scope);
						 
						  if(body.body(cp,c))
						   {
						   	c=body.count;
						   	
						   	//System.out.println("ddd:"+cp.get(c));
						   	if(cp.get(c).equals("]"))
						   	{
						   		c++;
						   		if(c>=cp.size()){count_int=c;return true;}
						   		
						   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
						   		{
						   			count_int=c;	
						   			return true;
						   		}
						   		else
						   		{
						   			count_int=c;
						   			return true;
						   		}
						   	}
						   	if(failedCount<c && body.failedCount<c)
					   			failedCount=c;
						   	else if(body.failedCount>c && body.failedCount>failedCount)
						   			failedCount=body.failedCount;
						   	}
						  if(failedCount<c)
					   			failedCount=c;
						   	
					}
					else  	if(cp.get(c).equals("]"))
				   	{
				   		c++;
				   		if(c>=cp.size()){count_int=c;return true;}
				   		
				   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
				   		{
				   			count_int=c;	
				   			return true;
				   		}
				   		else
				   		{
				   			count_int=c;
				   			return true;
				   		}
				   			}
					   }
				 if(failedCount<c)
			   			failedCount=c;
				 }
			
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
			 
		 }
	}

	//IF-ELSE END
	
	//Function Def Start
	class FunctionDefModel 
	{

		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={"["};
		private	String follow_for_body[]={"]"};
		ExpModel exp ;
		declarationModel decl;
		private	BodyModel body;
		FunctionDefModel(){}
		 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope);
		 }
		 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope);
		 }
		 public boolean func_def(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 //System.out.println(""+cp.get(c));
					 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										 if(body.body(cp,c))
										 {
											c=count_int;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
										 if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
									 }
								 }
							 }
						 }
					 }
				 }//if stable not present.
				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 c++;
							 
							 if(params(cp, c))
							 {
								 
								 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //System.out.println(c+"HerEFF:"+cp.get(c));
									 if(body.body(cp,c))
									 {
										
										c=body.count;
									
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
									 if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
								 }
							 }
						 }
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   	
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 
				 c++;
				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 c++;
							 
							 if(params(cp, c))
							 {
								 
								 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //System.out.println(c+"HerEFF:"+cp.get(c));
									 if(body.body(cp,c))
									 {
										
										c=body.count;
									
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
									 if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
								 }
							 }
						 }
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
				  
			 }//if stable+access modifier not present.
			 else  if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 c++;
						 
						 if(params(cp, c))
						 {
							 
							 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 //System.out.println(c+"HerEFF:"+cp.get(c));
								 if(body.body(cp,c))
								 {
									
									c=body.count;
								
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
								 }
								 if(failedCount<c && body.failedCount<c)
							   			failedCount=c;
								   	else if(body.failedCount>c)
								   			failedCount=body.failedCount;
							 }
						 }
					 }
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }


			 return false;
		 }
		 boolean fields(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					c++;
					
					 if(list(cp,c))
					{
						c=count_int;
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								
								count_int=c;
								
								return true;
							}
						}
						else if(cp.get(c).equals(")"))
						{
							c++;
							
							count_int=c;

							return true;
						}
						else if(cp.get(c-1).equals(")"))
						{

							count_int=c;

							return true;
						}
					}
					
				}
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 
			 return false;
		 }
		 boolean params(ArrayList<String> cp,int c)
		 {
			 if(fields(cp,c))
			 {
				 c=count_int;
				 
				 return true;
			 }
			 else if(Object(cp, c))
			 {
				 c=count_int;
					count_int=c;
					return true;
			 }
			 else if(cp.get(c).equals(")"))
			 {
				 count_int=++c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(cp.get(count).equals("datatype"))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							count++;
							
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								//System.out.println(count+":rr:"+cp.get(count));
								return true;
							}
							/*else
							{
								
								count--;
								//System.out.println(count+":rr:"+cp.get(count));
								if(Object(classPart, count))
								 {
									 count=count_int;
										count_int=count;
										
										return true;
								 }
							}*/
						}
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
						count_int=count;
						
						return true;
				 }
				 
			 }
			 else if(classPart.get(count).equals(")"))
			 {
				 count_int=count;
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			   
			 return false;
		 }
		 boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 c++;
					
						 if(Ob(cp, c))
						 {
							c=count_int;
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 
							 }
							 else
							 {
								 
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals(")"))
								 {
									 
									 count_int=c=c+1;
									 
									 return true;
								 }
							 }
						 }
					 
				 }
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
			 return false;
		 }
		 boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 
				 if(cp.get(c).equals(","))
				 {
					 c++;
					
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 
						if(cp.get(c).equals("ID"))
						{
							c++;
						
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals(")"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
								 else
									 if(cp.get(c-1).equals(")"))
									 {
										 
										 count_int=c;
										 return true;
									 }
										
							
								 
							 }
						 
							
						}
					 }
					 else if(cp.get(c).equals("datatype"))
					 {
					
						 if(fields(cp, c))
						 {
							 c=count_int;
								count_int=c;
								return true;
						 }
					 }
				 }
				 else  if(cp.get(c).equals(")"))
				 {
					 c++;
					 count_int=c;
					 
					 return true;
				 }
			
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
				 return false;
		 }


		}

	//Function Def End
	//Simple Body  Start
		class BodyModel {
			KeyWords words = new KeyWords();
			int count=0,count_int=0,scope=0;
			public int failedCount=0;
			public boolean isSomethingOpen=false;
			ArrayList<String> cp = new ArrayList();
			ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[]; //Usually ]
			String follow_for_exp[]={"Terminator"};
			ExpModel exp ;
			declarationModel decl;
			functionCallModel functCall;
			BodyModel(){}
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp);
				decl = new declarationModel(cp,vp,scope);
				functCall= new functionCallModel(cp,vp,scope);
		 }
		 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp);
				decl = new declarationModel(cp,vp,scope);
				functCall= new functionCallModel(cp,vp,scope);
		 }
		 public boolean getIsSomeThingOpen()
		 {
			 return isSomethingOpen; 
		 }
		 public void setIsSomeThingOpen(boolean tf)
		 {
			 isSomethingOpen=tf; 
		 }
		 public boolean body(ArrayList<String> cp,int c)
		 {

			 if(m_mst(cp,c))
			 {
				 c=count_int;
				 count=c;
				 //System.out.println(c+"body Passed: "+cp.get(c));
				 return true;
			 }
			 return false;
		 }
		 boolean m_mst(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 //Declaration can occur
				int temp=c;
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					 //System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				// System.out.println("Declaration Failed at Line number: "+temp);
				 if(failedCount<c && c< decl.failedCount)
			   			failedCount=decl.failedCount;
				 else if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("ID"))
			 {
				 //Declaration can occur
				int temp=c;
				int check=0;
				
				 if(decl.decl(cp, c))
				 {
					 c=decl.count;
					// System.out.println("Declaration Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				else if(functCall.functionCall(cp,c))
				 {
					 c=functCall.count;
					// System.out.println("Function Calling Passed at Line number: "+c);
					/// System.out.println(c+""+vp.get(c));
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 check++;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					 {
						
						 count_int=c;
						 return true;
					 }
					 else 
					 {
						 
						 if(m_mst(cp,c))
						 {
							 
							 c=count_int;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 
					check++; 
				 }
				 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					 
					 count_int=c;
					 return true;
				 }
				 if(exp.FailedCounter>decl.failedCount && exp.FailedCounter> functCall.failedCount && exp.FailedCounter>c && exp.FailedCounter> failedCount)
				 {
					 failedCount=exp.FailedCounter;
					// System.out.println("Invalid expression: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter<decl.failedCount && decl.failedCount> functCall.failedCount && c<decl.failedCount && failedCount< decl.failedCount)
				 {
					 failedCount=decl.failedCount;
					 //System.out.println("Invalid Declaration at line number: "+(failedCount+1));
				 }
				 else if(exp.FailedCounter< functCall.failedCount && decl.failedCount< functCall.failedCount && c<functCall.failedCount && failedCount< functCall.failedCount)
				 {
					 failedCount= functCall.failedCount;
					// System.out.println("Invalid FunctionCalling at line number: "+(failedCount+1));
				 }
				 else if(failedCount<c)
				 {
					 failedCount=c;
				 }
					 
			 }
			 else if(cp.get(c).equals("si")) //if-else
			 {
				int temp=c;
				 IfElseModel ifelse;
				 ifelse = new IfElseModel(cp,vp,scope);
				 int check=0;
				 if(ifelse.ifElse(cp,c))
				 {
					 c=ifelse.count;
					 check=1;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
					
				 }
				
				 if(failedCount<c && c> ifelse.failedCount)
			   			failedCount=c;
				 else if(failedCount<ifelse.failedCount && c< ifelse.failedCount)
				 {
					 failedCount=ifelse.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("check")) //switch
			 {
				 int temp=c;
				 boolean check=false;
				 SwitchModel switch1 =new SwitchModel(cp, vp,scope);
				 if(switch1.Switch(cp,c))
				 {
					 c=switch1.count;
					// System.out.println("Switch Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
						 failedCount=c;
				 }
			
				 if(failedCount<c && c> switch1.failedCount)
			   			failedCount=c;
				 else if(failedCount<switch1.failedCount && c< switch1.failedCount)
				 {
						failedCount=switch1.failedCount;
						 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("for loop")) //for Loop
			 {
				 int temp=c;
				 boolean check=false;
				 ForModel to = new ForModel(cp,vp,scope);
				 if(to.For(cp,c))
				 {
					 c=to.count;
					 System.out.println("For Loop Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> to.failedCount)
			   			failedCount=c;
				 else if(failedCount<to.failedCount && c< to.failedCount)
				 {
					 failedCount=to.failedCount;
					 isSomethingOpen=true;
			   	}
			 }
			 else if(cp.get(c).equals("while loop")) //while Loop
			 {
				 boolean check=false;
				 int temp=c;
				 WhileModel w=new WhileModel(cp,vp,scope);
				 if(w.test(cp,c))
				 {
					 c=w.count;
					// System.out.println("While Passed at Line number: "+temp);
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(failedCount<c && c> w.failedCount)
			   			failedCount=c;
				 else if(failedCount<w.failedCount && c< w.failedCount)
				 {
					 failedCount=w.failedCount;
					 isSomethingOpen=true;
				 }
			 }
			 else if(cp.get(c).equals("break")) //break
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 System.out.println("Break Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("continue")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Continue Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 if(!check)
				 {
					 //System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(cp.get(c).equals("return")) //continue
			 {
				 boolean check=false;
				 int temp=c;
				 c++;
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					// System.out.println("Return Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 else if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						// System.out.println("Expression Passed at Line number: "+temp);
						 check=true;
						 if(m_mst(cp,c))
						 {
							 c=count_int;
							 return true;
						 }
						 else if(failedCount<c)
							 failedCount=c;
					 }
				 }
				 if(!check)
				 {
					// System.out.println("Terminator missing at Line number: "+temp);
					 if(failedCount<temp)
						 failedCount=temp;
				 }
			 }
			 else if(exp.exp(cp, c) && !cp.get(c).equals("Terminator") ) //Exp
			 {
				 
				 boolean check=false;
				 c=exp.count;
				 
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 //System.out.println("Expression Passed after at Line number: "+c);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
					 
				 }
				 else if( cp.get(c-1).equals("Terminator") )
				 {
					// System.out.println("Expression Passed after at Line number: "+(c-1));
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
				 if(!check)
				 {
					 System.out.println("Terminator missing at Line number: "+c);
					 if(failedCount<c)
						 failedCount=c;
				 }
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 
				 count_int=c;
				 return true;
			 } 
			
			 if(failedCount<c)
				 failedCount=c;
			 if(exp.FailedCounter>c)
			 {
				// System.out.println("invalid exp at Line number: "+exp.FailedCounter);
				 failedCount=exp.FailedCounter;
			 }
			 else
			 {
				// System.out.println(failedCount+"Missing at Line number: "+c);
				 if(failedCount<c)
					 failedCount=c;
			 }
			 
			 return false;
		 }
		 
		}
	//Simple Body  End


		//SwitchModel  Start
			class SwitchModel {
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={")"};
				String follow_for_body[]={"]"};
				ExpModel exp ;
				BodyModel body;
				
				SwitchModel(){}
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						exp = new ExpModel(cp,vp,follow_for_exp,false);
						body=new BodyModel(cp,vp,follow_for_body,scope);
						follow_of_Parent=null;
				 }
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						body=new BodyModel(cp,vp,follow_for_body,scope);
						exp = new ExpModel(cp,vp,follow_for_exp,false);
						
				 }
				 public boolean Switch(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("check"))
					 {
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								
								if(cp.get(c).equals(")"))
								{
									c++;
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										if(test(cp,c))
										{
											c=count_int;
											count=count_int;
											return true;
										}
										 else if(cp.get(c).equals("]"))
										 {
										   		c++;
										   		count_int=c;
											   count=count_int;
												return true;
						   	
										}
										if(failedCount<c)
								   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								else if(cp.get(c-1).equals(")"))
								{
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(test(cp,c))
											{
												c=count_int;
												count=count_int;
												return true;
											}
											 else if(cp.get(c).equals("]"))
											 {
											   		c++;
											   		count_int=c;
												   count=count_int;
													return true;
							   	
											}
										 if(failedCount<c)
									   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								if(failedCount<c)
						   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 boolean test(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("test"))
					 {
						 c++;
						 String[] temp={":"};
						 ExpModel exp1=new ExpModel(this.cp,vp,temp,false);
					
						 if(exp1.exp(cp, c))
						 {
							 c=exp1.count;
							
							 if(cp.get(c).equals(":"))
							 {
								 
								 c++;
								//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
								   if(failedCount<c)
							   			failedCount=c;
							 }
							 else  if(cp.get(c-1).equals(":"))
							 {
				//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
							 }
							 if(failedCount<c)
						   			failedCount=c;
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 
			}

			//SwitchModel  End
		//Constructor Start
		class ConstructorModel {


			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0,scope=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			String follow_of_Parent[];
			String follow_for_exp[]={"["};
			private	String follow_for_body[]={"]"};
			ExpModel exp ;
			declarationModel decl;
			private	BodyModel body;
			ConstructorModel(){}
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope);
			 }
			 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					follow_of_Parent=Follow;
					exp = new ExpModel(cp,vp,follow_for_exp);
					decl = new declarationModel(cp,vp,scope);
					body=new BodyModel(cp,vp,follow_for_body,scope);
			 }
			 public boolean construct_def(ArrayList<String> cp,int c)
			 {
				 
				 if(cp.get(c).equals("acces modifier"))
				 {
					
					 c++;
					 
					 if(cp.get(c).equals("type modifier"))
					 {
						 c++;
						
							 if(cp.get(c).equals("ID"))
							 {
								 c++;
								 if(cp.get(c).equals("(")) //Confirmation of params - (
								 {
									 
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 
										 if(cp.get(c).equals("["))
										 {
											 c++;
											 
											 if(body.body(cp,c))
											 {
												c=count_int;
												if(cp.get(c).equals("]"))
												{
													c++;
													count=count_int=c;
													return true; //Successful parsed.
													
												}
											 }
										 }
									 }
								 }
							 }
						 
					 }//if stable not present.
					 else if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body.body(cp,c))
										 {
											 c=body.count;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
									 }
								 }
							 }
						 }
					 
				 } // if access modifier not present
				 else if(cp.get(c).equals("type modifier"))
				 {
					 c++;

						 if(cp.get(c).equals("ID"))
						 {
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(body.body(cp,c))
										 {
											 c=body.count;
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
										 }
									 }
								 }
							 }
						 }
					 
				 }//if stable+access modifier not present.

				 else if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body.body(cp,c))
									 {
										
										c=body.count;
										 //System.out.println(c+":tt:"+cp.get(c));
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
									 }
								 }
							 }
						 }
					 }
				 


				 return false;
			 }

		}
		//Constructor  End
	
	//Function Call	
		 class functionCallModel {

				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={"Terminator"};
				ExpModelFunction exp ;
				
				functionCallModel(){}
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						
					
				 }
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						
						
				 }
		
				 public boolean functionCall(ArrayList<String> cp,int c)
				 {
					 
					
					 if(cp.get(c).equals("ID"))
					 {
						
							
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							
							 if(params(cp,c))
							 {
								 
								 c=count_int;
								 
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count=count_int=c;
									 
									 return true; 
								 }
								 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								 {
									// c++;
									 count=count_int=c;
									
									 return true; 
								 }
							 }
						 }
					 }
					 
					 return false;
				 }
				 boolean params(ArrayList<String> cp,int c)
				 {
					 //Pass the params in exp and test to see if it exits or not?
					 
					 exp = new ExpModelFunction(cp,vp,follow_for_exp,false,scope);
					 if(exp.exp(cp, c))
					 {
							
						 c=exp.count;
						
						 if(c<cp.size())
						 {
							 //System.out.println("Exp chal gai function mey paframs:"+cp.get(c));
							if(c<cp.size()-2)
							{
								  if(cp.get(c).equals(","))
									{
										
										c++;
										 if(params(cp, c))
										 {
											 c=count_int;
												count=count_int;
												return true;
											 
											 
										 }
									}
								 else if(cp.get(c).equals(")"))
								 {
									
										count_int=++c;
									
										return true;
									 
								 }
								  
							}
							 else if(cp.get(c).equals(")"))
							 {
								
									c=count_int=c;
								
									return true;
								 
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								   
									count_int=c;
								
									return true;
								 
							 }
							 
						
						 }
					 }
		
					 return false;
				 }


//EXP  Function  START

				 class ExpModelFunction 
					{
						KeyWords words = new KeyWords();
						public int count=0,count_int=0,FailedCounter=0,scope=0;
						 String follow_of_Parent[];
						 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
					
						 boolean ifParams=false;
					
						 public boolean AllowAssignation=true;
						ExpModelFunction(){}
						public ExpModelFunction(ArrayList<String> cp,ArrayList<String> vp,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
						
							follow_of_Parent=follow;
							
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							AllowAssignation=allow;
							this.scope=scope;
						
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							follow_of_Parent=follow;
							AllowAssignation=allow;
						
						}
						
					public 	boolean exp(ArrayList<String> ClassPart,int count)
						{
						
							if(isUniOp(ClassPart.get(count)))
							{
								count++;
								if(exp1(ClassPart,count))
								{
									count=count_int;
									this.count=count;
									return true;
								}
							}
							else if(exp1(ClassPart,count))
							{
								
								count=count_int;
								count_int=0;
								this.count=count;
								return true;
							}
							else if(words.isParentFollow(cp.get(count), follow_of_Parent))
							{
								count=count_int;
								this.count=count;
								return true;
							}
							if(FailedCounter<count)
								FailedCounter=count;
							return false;
						}

					boolean exp1(ArrayList<String> cp,int c)
					{
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
						
							if(cp.get(c).equals("ID"))
							{
								
								if(vp.get(c+1).equals("("))
								{
									String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
									functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
									fcm.follow_for_exp[0]=")";
									
									if(fcm.functionCall(cp, c))
									{	
										c=fcm.count-1;
									}
								}//Not function
							
								c++;
								
								if(exp21(cp,c))
								{
									c=count_int;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								c++;
								
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
							}
							else if(cp.get(c).equals("("))
							{
								c++;
								if(news(cp,c))
								{
								
									c=count_int;
									return true;
								}
							}
							if(FailedCounter<c)
							FailedCounter=c;
							return false;
					}
	
					boolean exp2(ArrayList<String>  cp,int c)
					{
						
							if(cp.size()<=c){count_int=cp.size()-1; return true; } 
							else
							{
								if(isOp(cp.get(c),c))
								{
									//..........
									c++;
									if(exp1(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals(")"))
								{
									count_int=c;
									return true;			
								}
								else if(words.isParentFollow(cp.get(c),follow_of_Parent))
								{

									count_int=c;
									return true;
											
								}
								else if(cp.get(c).equals(",") && !ifParams)
								{
									count_int=c;
									
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									count_int=c;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean exp21(ArrayList<String>  cp,int c)
						{
							if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
							
							if(exp2(cp,c))
							{
								c=count_int;
								
								return true;
							}
							else if(cp.get(c).equals("("))
							{
								
								if(params(cp,c))
								{
									
									c=count_int; 
									
									if(c<cp.size())
									{
										
										if(cp.get(c).equals(")"))
										{
											c++;
										
											if(cp.get(c).equals("Dot operator"))
											{
												c++;
												if(cp.get(c).equals("("))
												{
													if(exp21(cp,c))
													{
														c=count_int;
														return true;
													}
												}
												if(FailedCounter<c)
													FailedCounter=c;
											}
											else if(exp2(cp,c))
											{
												c=count_int; 	
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals(")"))
										{

											if(cp.get(c).equals("Dot operator"))
											{
												c++;
												if(cp.get(c).equals("("))
												{
													if(exp21(cp,c))
													{
														c=count_int;
														return true;
													}
													if(FailedCounter<c)
														FailedCounter=c;
												}
												if(FailedCounter<c)
													FailedCounter=c;
											}
											else if(exp2(cp,c))
											{
												c=count_int; 	
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(words.isParentFollow(cp.get(c),follow_of_Parent))
										{
											

											count_int=c;
											
											return true;
													
										}
									}
									else if(cp.get(c-1).equals(")"))
								    {
										c=count_int;
										
										return true;
									}
								
								}
							}
							else if(cp.get(c).equals("["))
							{
								
								c++;
								
								if(words.isConst(cp.get(c)))
								{
									
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;	
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals("ID"))
								{

								
									c++;
									
									if(exp1(cp,c))
									{
										
										c=count_int;
								
											//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
										if(cp.get(c).equals("]"))
										{
											
											c++;
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals("]"))
										{
											
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							
							else if(O1(cp,c))
							{
								
								
								c=count_int;
								if(exp2(cp,c))
								{
									
									c=count_int;
									count_int=c;
								
									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										count_int=c;

										if(c>=cp.size()){c=count_int=cp.size()-1;}
										return true;
									}
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean params(ArrayList<String>  cp,int c)
						{
						
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
							ifParams=true;

							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								
								ifParams=false;
								return true;
							}
							else if(exp1(cp,c))
							{
								
								c=count_int;
								//System.out.println(cp.get(c)+":exp1 of params"+c);
								if(c<cp.size())
								{
									
									if(cp.get(c).equals(")"))
									{
										c++;
										count_int=c;
										//System.out.println(cp.get(c)+":dF"+c);
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("___"+cp.get(c));
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals(",")) //what if datatype?
									{
										
										c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(cp.get(c).equals("ID"))
											 {
												 c++;
												 if(params(cp,c))
												 {
														
														c=count_int;
														ifParams=false;
														return true;
											     }
											 }
										 }
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
								
									c=count_int;
									ifParams=false;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					String valueOp(int index)
					{
						
						return vp.get(index);
					}
			
				boolean isOp(String cp,int c)
					{
						
						if(cp.equals("RO"))//
						{
							 return true;
						}
						else if(cp.equals("Assignment") && AllowAssignation)
						{
							return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitOr"))
							 return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitAnd"))
							 return true;
						}
						else if(cp.equals("OR"))
						{
							 return true;
						}
						else if(cp.equals("AddSum"))
						{
							 return true;
						}
						else if(cp.equals("Mux"))
						{
							
							 return true;
						}
						else if(cp.equals("DIVMOD"))
						{
							 return true;
						}
						else if(cp.equals("Incdec") || cp.equals("Not operator"))
						{
							 return true;
						}
						else if(cp.equals("LO"))
						{
							 return true;
						}

						
						
						return false;
					}
					
				boolean isUniOp(String a)
					{
						if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
						{
							return true;
						}
						return false;
					}
					boolean news(ArrayList<String>  cp,int c)
						{
							
						if(isUniOp(cp.get(c)))
						{
							c++;
							if(exp1(cp,c))
							{
								
								c=count_int; 
								
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
							}
						
						}
						else if(exp1(cp,c))
							{
								
								c=count_int; 
								//System.out.println(c+":33:"+cp.get(c));
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
									{
									   c=count_int;	
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									{
										//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
									   c=count_int;	
										return true;
									}
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							
							}
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c).equals("datatype"))
							{
								
								c++;
								if(cp.get(c).equals(")"))
								{
									
									c++;
									if(exp1(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										return true;
									}
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean O1(ArrayList<String>  cp,int c) // Incomplete
						{
							if(cp.get(c).equals("Dot operator"))
							{
								
								c++;
								
								if(cp.get(c).equals("ID"))
								{
									
								
									if(exp1(cp,c))
									{
										
										c=count_int;
										
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
								  if(cp.get(c).equals("["))
									{
										
										c++;
										//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
										if(words.isConst(cp.get(c)))
										{
											c++;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count_int=c;
												//System.out.println("333,"+c+","+cp.get(c));
												return true;
											}
										}
									}
									else if(cp.get(c).equals("&"))
									{
										c++;
										count_int=c;
										return true;
									}
									else if(cp.get(c).equals("Dot operator"))
									{
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
								}
								else if(words.isConst(cp.get(c)))
								{
									c++;
									count_int=c;
									return true;
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							else if(cp.get(c).equals(")"))
							{
								//c++;
								count_int=c;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
			
					}

//isArrayType

//EXP  Function END

				 
				 
				 
			}

	//FunctionCallModel  End
		
}
//ClassModel End
















///////////////////////////////// -Semantic Start- /////////////////////////////////////////////////////////////////////////////////////////////////


//Class_Semantic Start

class ClassModel_Semantic
{

	private KeyWords words = new KeyWords();
	public int count=0,count_int=0,failedCount=0;
	int scope=0;
	 private ArrayList<String> cp = new ArrayList();
	 private ArrayList<String> vp = new ArrayList();
		 private String follow_of_Parent[];
		 private String follow_for_exp[]={")","["};
		 private	String follow_for_body[]={"]"};
		 private	ExpModel exp ;
		 private classBodyModel body1;
		  tableVar tv = new tableVar();
		  tableInterface tI = new tableInterface();
		  tableClass innerClassTable = new tableClass();
		  tableFunction tFunction = new tableFunction();
		String CurrentPname;
		  String CurrentName;
		  int CurrentScope;
		  boolean CurrentisPrivate;
		  boolean CurrentisStable;
		  boolean isInner=false;
		 private ClassModel_Semantic(){}
	 public ClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,int scope,boolean isInner)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			follow_of_Parent=null;
			this.isInner=isInner;
	 }
	 public ClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,boolean isInner)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			this.isInner=isInner;
	 }
	 public ClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			follow_of_Parent=null;
	 }
	 public ClassModel_Semantic(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			body1=new classBodyModel(cp,vp,follow_for_body,scope);
			
	 }
	 public boolean isSomethingOpen=false;
	 public boolean getIsSomeThingOpen()
	 {
		 return isSomethingOpen; 
	 }
	 public void setIsSomeThingOpen(boolean tf)
	 {
		 isSomethingOpen=tf; 
	 }
	 public boolean lookUpClass(String name)
	 {
		 for (int i = 0; i < Syntaxtification.ct.Cname.size(); i++) {
		if(Syntaxtification.ct.Cname.get(i).equals(name) && Syntaxtification.ct.classScope.get(i)<=scope )
		{
			
			return true;
		}
		}
		 return false;
	 }
	 public boolean lookUpInterface(String name)
	 {
		 if(Syntaxtification.interfacet.nameInterfaces.size()>0)
		 {
			 for (int i = 0; i < Syntaxtification.interfacet.nameInterfaces.size(); i++) {
					if(Syntaxtification.interfacet.nameInterfaces.get(i).equals(name))
						return true;
					}
		 }
		 else if(Syntaxtification.ct.Interfacesname.size()>0)
		 {
			 for (int i = 0; i < Syntaxtification.ct.Interfacesname.get(ClassIndex).nameInterfaces.size(); i++) {
					if(Syntaxtification.ct.Interfacesname.get(ClassIndex).nameInterfaces.get(i).equals(name))
						return true;
					}
			 
		 }
		 return false;
	 }
	 public boolean lookUpInnerInterface(String name) //Issue
	 {
		 for (int i = 0; i < tI.nameInterfaces.size(); i++) {
		if(tI.nameInterfaces.get(i).equals(name))
			return true;
		}
		 return false;
	 }
	 public boolean lookUpFunction(String name) //Issue
	 {
		 for (int i = 0; i < tFunction.nameFunction.size(); i++) {
		if(tFunction.nameFunction.get(i).equals(name))
			return true;
		}
		 return false;
	 }
	 public int ClassIndex=0;
	 public boolean classFunction(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("acces modifier") && !vp.get(c).equals("local"))
		 {
			
			 c++;
			 
			 if(cp.get(c).equals("type modifier"))
			 {
				 
				 c++;
				 if(cp.get(c).equals("class"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)) )
						 {
							 if( !isInner)
							 {
								 Syntaxtification.ct.isPrivate.add(true);
								 Syntaxtification.ct.isStable.add(true);
								 Syntaxtification.ct.Cname.add(vp.get(c));
								 Syntaxtification.ct.classScope.add(scope); 
							 }
							 else  if( isInner)
							 {
								 CurrentName=vp.get(c);
								 CurrentisPrivate=true;
								 CurrentisStable=true;
								 CurrentScope=scope;
							 }
							 ClassIndex=Syntaxtification.ct.Cname.size()-1;
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at char number:"+(c+1));
						 }
						 
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 
									 if(body1.classbody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }	
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   	
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }//if stable not present.
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 
					 if(!lookUpClass(vp.get(c)))
					 {
						 if( !isInner)
						 {
							 Syntaxtification.ct.isPrivate.add(true);
							 Syntaxtification.ct.isStable.add(true);
							 Syntaxtification.ct.Cname.add(vp.get(c));
							 Syntaxtification.ct.classScope.add(scope); 
						 }
						 else  if( isInner)
						 {
							 CurrentName=vp.get(c);
							 CurrentisPrivate=true;
							 CurrentisStable=true;
							 CurrentScope=scope;
						 }
						 ClassIndex=Syntaxtification.ct.Cname.size()-1;
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at char number:"+(c+1));
					 }
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.classbody(cp,c))
								 {
									 c=body1.count;
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								  
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 } // if access modifier not present
		 else if(cp.get(c).equals("type modifier"))
		 {
			 c++;
			 if(cp.get(c).equals("class"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)))
					 {
						 if( !isInner)
						 {
							 Syntaxtification.ct.isPrivate.add(true);
							 Syntaxtification.ct.isStable.add(true);
							 Syntaxtification.ct.Cname.add(vp.get(c));
							 Syntaxtification.ct.classScope.add(scope); 
						 }
						 else  if( isInner)
						 {
							 CurrentName=vp.get(c);
							 CurrentisPrivate=true;
							 CurrentisStable=true;
							 CurrentScope=scope;
						 }
						 ClassIndex=Syntaxtification.ct.Cname.size()-1;
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at char number:"+(c+1));
					 }
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 if(body1.classbody(cp,c))
								 {
									 c=body1.count;
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
									 if(failedCount<c)
								   			failedCount=c;
									   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }	
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
	
		 }//if stable+access modifier not present.
		 if(cp.get(c).equals("class"))
		 {
			 c++;
			 
			 if(cp.get(c).equals("ID"))
			 {
				 
				 if(!lookUpClass(vp.get(c)))
				 {
					 if( !isInner)
					 {
						 Syntaxtification.ct.isPrivate.add(true);
						 Syntaxtification.ct.isStable.add(true);
						 Syntaxtification.ct.Cname.add(vp.get(c));
						 Syntaxtification.ct.classScope.add(scope); 
					 }
					 else  if( isInner)
					 {
						 CurrentName=vp.get(c);
						 CurrentisPrivate=true;
						 CurrentisStable=true;
						 CurrentScope=scope;
					 }
					 ClassIndex=Syntaxtification.ct.Cname.size()-1;
				 }
				 else
				 {
					 System.out.println("Redeclaration Error of class "+ vp.get(c)+ " at line number:"+(c+1));
				 }
				 c++;
				 
				 if(S1(cp,c)) //Confirmation of params - (
				 {
					 c=count_int;
						 if(cp.get(c).equals("["))
						 {
							 c++;
							 
							 if(body1.classbody(cp,c))
							 {
								 c=body1.count;
								
								if(cp.get(c).equals("]"))
								{
									c++;
									count=count_int=c;
									
									return true; //Successful parsed.
									
								}
								 if(failedCount<c)
							   			failedCount=c;
								   	
								
							 }
							 if(failedCount<c && body1.failedCount<c)
						   			failedCount=c;
							   	else if(body1.failedCount>c)
							   			failedCount=body1.failedCount;
						 }	
						 if(failedCount<c)
					   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		
		 }


		 return false;

	 }
	 private boolean S1_List(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals(","))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
					 System.out.println("No interface found Error");
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		   	
		 return false;
	 }
	 private boolean S1(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("interface"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
					 System.out.println("No interface found Error");
				 
				 c++;
				 if(S1_List(cp,c))
				 {
					 c=count_int;
					 if(cp.get(c).equals("inheritance"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("ID"))
						 {
							
							 if(lookUpClass(vp.get(c)))
							 {
								 Syntaxtification.ct.Pname.add(vp.get(c));
								 CurrentPname=vp.get(c);
								 
							 }
							 else
							 {
								 System.out.println("No Class found Error");
							 }
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 count_int=c;
								 return true;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("inheritance"))
		 {
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 if(lookUpClass(vp.get(c)))
				 {
					 Syntaxtification.ct.Pname.add(vp.get(c)); 
					 CurrentPname=vp.get(c);
				 }
				 else
					 System.out.println("No Class found Error");
				 c++;
				 if(cp.get(c).equals("["))
				 {
					 count_int=c;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("["))
		 {
			 count_int=c;
			 Syntaxtification.ct.Pname.add("-");
			 CurrentPname="-";
			 return true;
		 }
		 if(failedCount<c)
	   			failedCount=c;
		 return false;
	 }
	 
	 

	 
	 
	 
	 
	 

	//ClassBodyModel Start
	class classBodyModel {
		KeyWords words = new KeyWords();
		int count=0,count_int=0,failedCount=0,scope=0;
		ArrayList<String> cp = new ArrayList();
		ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[]; //Usually ]
		String follow_for_exp[]={"Terminator"};
		ExpModel exp ;
		declarationModel decl;
		//functionCallModel functCall;
		
		classBodyModel(){}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	{
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);
		
	}
	public classBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
	{
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			decl = new declarationModel(cp, vp, scope);
			//functCall= new functionCallModel(cp,vp);

	}
	public boolean classbody(ArrayList<String> cp,int c)
	{
		 
		 if(b_mst(cp,c))
		 {
			 c=count_int;
			 count=c;
			 Syntaxtification.ct.innerClassList.add(innerClassTable);
			 Syntaxtification.ct.functionslist.add(tFunction);
			
			 return true;
		 }
		 return false;
	}
	public boolean b_mst(ArrayList<String> cp,int c)
	{
		 if(cp.get(c).equals("datatype"))
		 {
			FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 
			 //Declaration can occur
			 if(decl.decl(cp, c))
			 {
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 	 else if(fdm.func_def(cp, c))
			 {
				 
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && fdm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && decl.failedCount<fdm.failedCount)
					failedCount=fdm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>fdm.failedCount)
					failedCount=decl.failedCount;
		 }
		 else if(cp.get(c).equals("ID"))
		 {
			 ConstructorModel cm= new ConstructorModel(cp,vp,scope);
			 //Declaration can occur
			
			 if(decl.decl(cp, c))
			 {
				 
				 c=decl.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cm.construct_def(cp, c))
			 {
				 c=cm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && decl.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && decl.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<decl.failedCount && decl.failedCount>c && decl.failedCount>cm.failedCount)
					failedCount=decl.failedCount;
		 }
	 else if(cp.get(c).equals("acces modifier") || cp.get(c).equals("type modifier") )
		 {
			 ClassModel_Semantic cm= new ClassModel_Semantic(cp,vp,scope+1,true);
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				
				 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
				 {
					 int temp=c+1;
					 
					 if(cp.get(temp).equals("type modifier") && vp.get(temp).equals("stable"))
					 {
						 temp++;
						 
						 if(cp.get(temp).equals("void"))
						 {
							 temp++;
							
							 if(cp.get(temp).equals("main"))
							 { 
								 
								 count_int=c;
								
								 return true;
							 }
						 }
						 
					 }
				 }
				 else if(!cp.get(c).equals("acces modifier"))
				 {
					 count_int=c;
					 return true; 
				 }
				 
			 } 
			 else if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 
			
				 innerClassTable.Cname.add(cm.CurrentName);
				 innerClassTable.classScope.add(cm.CurrentScope);
				 innerClassTable.isStable.add(cm.CurrentisStable);
				 innerClassTable.isPrivate.add(cm.CurrentisPrivate);
				 innerClassTable.Cvariable.add(cm.tv);
				 innerClassTable.Cvariable.add(cm.tv);
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(fdm.func_def(cp, c))
			 {
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c && cm.failedCount<c && im.failedCount<c && fdm.failedCount<c)
					failedCount=c;
			 else if(failedCount<cm.failedCount && cm.failedCount>c && im.failedCount<cm.failedCount  && fdm.failedCount<cm.failedCount)
					failedCount=cm.failedCount;
			 else if(failedCount<im.failedCount && im.failedCount>c && im.failedCount>cm.failedCount  && im.failedCount<fdm.failedCount)
					failedCount=im.failedCount;
			 else if(failedCount<fdm.failedCount && fdm.failedCount>c && fdm.failedCount>cm.failedCount  && im.failedCount>fdm.failedCount)
					failedCount=im.failedCount;
		 }
		 else if(cp.get(c).equals("class"))
		 {
			 ClassModel_Semantic cm= new ClassModel_Semantic(cp,vp,scope+1,true);
			 int temp=c+1;
			 if(cm.classFunction(cp, c))
			 {
				 c=count_int=cm.count;
				 innerClassTable.Cname.add(cm.CurrentName);
				 innerClassTable.classScope.add(cm.CurrentScope);
				 innerClassTable.isStable.add(cm.CurrentisStable);
				 innerClassTable.isPrivate.add(cm.CurrentisPrivate);
				 innerClassTable.Cvariable.add(cm.tv);
				 
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<cm.failedCount)
		   			failedCount=cm.failedCount;
			 else
				 cm.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("sculpture"))
		 {
			 InterfaceModel im=new InterfaceModel(cp,vp,scope);
			 if(im.interfaceFunction(cp, c))
			 {
				 c=count_int=im.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<im.failedCount)
		   			failedCount=im.failedCount;
			 else
				 im.failedCount=failedCount;
		 }
		 else if(cp.get(c).equals("void"))
		 {
			
			 FunctionDefModel fdm= new FunctionDefModel(cp,vp,scope);
			 if(fdm.func_def(cp, c))
			 {
				 c=count_int=fdm.count;
				 if(b_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<fdm.failedCount)
		   			failedCount=fdm.failedCount;
			 else
				 fdm.failedCount=failedCount;
		 }
		 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
		 {
			 if(cp.get(c).equals("acces modifier") && vp.get(c).equals("local"))
			 {
				 int temp=c+1;
				 if(cp.get(temp).equals("type modifier") && cp.get(temp).equals("stable"))
				 {
					 temp++;
					 if(cp.get(temp).equals("main"))
					 { 
						 count_int=c;
						 return true;
					 }
					 if(failedCount<temp)
				   			failedCount=temp;
				 }
			 }
			 else if(!cp.get(c).equals("acces modifier"))
			 {
				 count_int=c;
				 return true; 
			 }
			 
		 }
		 if(failedCount<c)
				failedCount=c;
		
		 
		 return false;
	}

	}

	//ClassBodyModel End
	

	//IF-ELSE Semantic Start
	class IfElseModel {
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={")","["};
		String follow_for_body[]={"]"};
		ExpModel exp ;
		tableVar tv= new tableVar();
		IfElseModel(){}
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				this.scope=scope;
				follow_of_Parent=null;
		 }
		 public IfElseModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				
		 }
		public  boolean ifElse(ArrayList<String> cp,int c)
		 {
			
			
			
			if(cp.get(c).equals("si"))
			 {
				 c++;
				 if(cp.get(c).equals("("))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						//.............Semantic to be done..................
						if(exp.returnType!="boolean") { System.out.println("Boolean expected at char number: "+c); return false;}
						 //...............................
						 c=exp.count;
						
						 if(cp.get(c).equals(")"))
						{
							 c++;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 //call for body
								  if(!cp.get(c).equals("]"))
								  {
									  BodyModel body;
									  body=new BodyModel(cp,vp,follow_for_body,scope,tv);
									  if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		c++;
									   		if(oelse(cp,c))
									   		{
									   			c=count_int;
									   			count=count_int;
									   		
									   			return true;
									   		}
									   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		
									   		
									   	}
									   	if(failedCount<c)
									   	{
									   		failedCount=c;
									   	}
									   }
									  if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c && failedCount<body.failedCount)
									   			failedCount=body.failedCount;
								  }
								  else if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								  if(failedCount<c )
							   			failedCount=c;
							
							 }
							 if(failedCount<c )
						   			failedCount=c;
						}
						 //if(c.prev is equal to )
						 if(cp.get(c-1).equals(")"))
							{
							 if(cp.get(c).equals("["))
							 {
								 c++;
							  if(!cp.get(c).equals("]"))
							  {
								  BodyModel body;
								  body=new BodyModel(cp,vp,follow_for_body,scope,tv);
								  //System.out.println("Here idiot: "+cp.get(c));
								  if(body.body(cp,c))
								   {
								   	c=body.count;
								   	
								   	if(cp.get(c).equals("]"))
								   	{
								   		c++;
								   		
								   		if(oelse(cp,c))
								   		{
								   			c=count_int;
								   			count=count_int;
								   			
								   			return true;
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   		else
								   		{
								   			c=count_int=c;
								   			count=count_int;
								   			return true;
								   		}
								   	}
								   	if(failedCount<c)
								   		failedCount=c;
								   }
								  if(failedCount<c && body.failedCount<c)
							   			failedCount=c;
								   	else if(body.failedCount>c && failedCount<body.failedCount)
								   			failedCount=body.failedCount;
							  }
							  else if(cp.get(c).equals("]"))
							   	{
							   		c++;
							   		if(oelse(cp,c))
							   		{
							   			c=count_int;
							   			count=count_int;
							   			
							   			return true;
							   		}
							   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   		else
							   		{
							   			c=count_int=c;
							   			count=count_int;
							   			return true;
							   		}
							   	}
							  if(failedCount<c )
						   			failedCount=c;
							   
							 }
							 	if(failedCount<c)
						   			failedCount=c;
							   
							}
							if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c && exp.FailedCounter<c)
				   			failedCount=c;
					   	else if(exp.FailedCounter>c && failedCount<exp.FailedCounter)
					   			failedCount=exp.FailedCounter;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				  
			 }
			
			 return false;
			 
		 }
		 boolean oelse(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("sino"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("["))
				 {
					 c++;
					 
					if(!cp.get(c).equals("]"))
					{
						 //call for body
						
						 BodyModel body=new BodyModel(cp,vp,follow_for_body,scope,tv);
						 
						  if(body.body(cp,c))
						   {
						   	c=body.count;
						   	
						   	//System.out.println("ddd:"+cp.get(c));
						   	if(cp.get(c).equals("]"))
						   	{
						   		c++;
						   		if(c>=cp.size()){count_int=c;return true;}
						   		
						   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
						   		{
						   			count_int=c;	
						   			return true;
						   		}
						   		else
						   		{
						   			count_int=c;
						   			return true;
						   		}
						   	}
						   	if(failedCount<c && body.failedCount<c)
					   			failedCount=c;
						   	else if(body.failedCount>c && body.failedCount>failedCount)
						   			failedCount=body.failedCount;
						   	}
						  if(failedCount<c)
					   			failedCount=c;
						   	
					}
					else  	if(cp.get(c).equals("]"))
				   	{
				   		c++;
				   		if(c>=cp.size()){count_int=c;return true;}
				   		
				   		if(words.isParentFollow(cp.get(c), follow_of_Parent))
				   		{
				   			count_int=c;	
				   			return true;
				   		}
				   		else
				   		{
				   			count_int=c;
				   			return true;
				   		}
				   			}
					   }
				 if(failedCount<c)
			   			failedCount=c;
				 }
			
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
			 
		 }
		 
		//EXP Semantic if  START

		 class ExpModel
			{
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,FailedCounter=0,scope=0;
				 String follow_of_Parent[];
				 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
			
				 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
				 String returnType="",ChangeType="";
				
				 String leftOp="",rightOp="";
				 public boolean AllowAssignation=true;
				ExpModel(){}
				public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;

				}
				public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;
					follow_of_Parent=follow;
					
				}
				public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					AllowAssignation=allow;
					this.scope=scope;
				}
				public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
				{
					this.cp=cp;
					this.vp=vp;
					this.scope=scope;
					follow_of_Parent=follow;
					AllowAssignation=allow;
					
				}
				
			public 	boolean exp(ArrayList<String> ClassPart,int count)
				{
				
					if(isUniOp(ClassPart.get(count)))
					{
					
						startUniOp=true;
						count++;
						if(exp1(ClassPart,count))
						{
							count=count_int;
							this.count=count;
						
							return true;
						}
					}
					else if(exp1(ClassPart,count))
					{
						
						count=count_int;
						count_int=0;
						this.count=count;
						
						
						return true;
					}
					else if(words.isParentFollow(cp.get(count), follow_of_Parent))
					{
						count=count_int;
						this.count=count;
						
						return true;
					}
					if(FailedCounter<count)
						FailedCounter=count;
				
					return false;
				}

			boolean exp1(ArrayList<String> cp,int c)
			{
					if(cp.size()<=c){count_int=cp.size()-1;return true; }
				
					if(cp.get(c).equals("ID"))
					{
						
						if(vp.get(c+1).equals("("))
						{
							String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
							functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
							fcm.follow_for_exp[0]=")";
							
							if(fcm.functionCall(cp, c))
							{
								
								String dt=getFuncReturnType(vp.get(c));
								String rdt=getFuncReturnType(vp.get(c));
								
								c=fcm.count-1;
								if(!opFlag) //No Left Operator Exists right now
								{
									
									 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
										{
									 		startUniOp=false;
												System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
												return false;
												
			
										}
										else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
										{
											startUniOp=false;
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
									else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=dt; //left operator
														opFlag=true;
														
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
											System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
											return false;
											
		
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									
						
								}
								else if(opFlag) //Left Operator Exists
								{
									
									{
										if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
										{

											rightOp=rdt;
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											leftOp=returnType;
											opFlag=true;
											
										}
										else //No more operators
										{

											
													
													if(!typeChanged)
													{
														
														
															rightOp=rdt;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
															 
														
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=rdt;
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														
														else
														{
															rightOp=ChangeType;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
														}
													}
													
												
											
										}
									}
									
								}
							
							}
							/*
							if(!lookupFunction(vp.get(c)))
							{
								System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							else
							{}
							*/
						}//Not function
						else //Maybe variable?
						{
							if(!lookUpVar(vp.get(c)))
							{
								System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							else
							{
								if(!opFlag) //No Left Operator Exists right now
								{
									String dt=getReturnTypeVar(vp.get(c));
									returnType=dt;
									 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
										{
										 startUniOp=false;
											if(dt.equals("dig") || dt.equals("frac"))
											{
												returnType="dig";
											}
											else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
										{
											startUniOp=false;
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
									else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=dt; //left operator
														opFlag=true;
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
										if(dt.equals("dig") || dt.equals("frac"))
										{
											returnType="dig";
										}
										else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(!vp.get(c+1).equals("."))
									{

											
											if(!typeChanged)
											{
											
													if(cp.get(c+1).equals("["))
													if(!isArrayType(vp.get(c)))
													{
														System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
														return false;
													}
											
											}
											else if(typeChanged)
											{
												typeChanged=false;
												
												if(!compTypeCast(dt,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
											
											}
												
											
										
									}
								}
								else if(opFlag) //Left Operator Exists
								{
									String rdt=getReturnTypeVar(vp.get(c));
									if(cp.get(c+1).equals("["))
									{
										if(!isArrayType(vp.get(c)))
										{
											System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
									}
									else
									{
										if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
										{
										
											
											rightOp=rdt;
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											leftOp=returnType;
											opFlag=true;
											
										}
										else //No more operators
										{
													if(!typeChanged)
													{
														
														
															rightOp=rdt;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
													
													}
													else if(typeChanged)
													{
														typeChanged=false;
														String temp=rdt;
														if(!compTypeCast(temp,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														
														else
														{
															rightOp=ChangeType;
															returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
															if(returnType.equals("x"))
															{
																System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															opFlag=false;
															
														}
													}
													
												
										
										}
									}
									
								}
							}
						
						}
						c++;
						
						if(exp21(cp,c))
						{
							c=count_int;
							return true;
						}
					}
					else if(words.isConst(cp.get(c)))
					{
						
						if(!opFlag) //No Left Operator Exists right now
						{
							
							 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
								{
								 startUniOp=false;
								 
									if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
									{
										returnType="dig";
									}
									else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
									{
										System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
								{
									
									startUniOp=false;
									
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									
										
								}
							else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
							{
												leftOp=words.getConst(cp.get(c)); //left operator
												opFlag=true;
							}
							else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
							{
								if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
								{
									returnType="dig";
								}
								else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
								{
									System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
									return false;
								}
									
							}
							else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
							{
								if(words.getConst(cp.get(c)).equals("boolean"))
								{
									returnType="boolean";
								}
								else 
								{
									System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
									return false;
								}
									
							}
							else
							{

									
										if(!typeChanged)
										{
											returnType=words.getConst(cp.get(c));
										}
										else if(typeChanged)
										{
											typeChanged=false;
											String temp=words.getConst(cp.get(c));
											if(!compTypeCast(temp,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
											else
												{
												returnType=ChangeType;
												}
										}
										
									
								
							}
						}
						else if(opFlag) //Left Operator Exists
						{
							if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
							{

								rightOp=words.getConst(cp.get(c));
								returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
								if(returnType.equals("x"))
								{
									System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								leftOp=returnType;
								opFlag=true;
								
							}
							else //No more operators
							{
	
										if(!typeChanged)
										{
												rightOp=words.getConst(cp.get(c));
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												opFlag=false;
											
												 
											
										}
										else if(typeChanged)
										{
											typeChanged=false;
											String temp=words.getConst(cp.get(c));
											if(!compTypeCast(temp,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
											else
											{
												rightOp=ChangeType;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												opFlag=false;
												
											}
										}
										
							
							}
						}
						c++;
						
						if(exp2(cp,c))
						{
							
							c=count_int;
							return true;
						}
					}
					else if(cp.get(c).equals("("))
					{
						c++;
						if(news(cp,c))
						{
						
							c=count_int;
							return true;
						}
					}
					if(FailedCounter<c)
					FailedCounter=c;
					return false;
			}
			boolean compTypeCast(String func,String Modified)
			{
				if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
				{
					if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
						return true;
					
				}
				else if(Modified.equals("beta"))
				{
					
					if(func.equals("beta"))
						return true;
				}
				
				return false;
			}
			boolean exp2(ArrayList<String>  cp,int c)
			{
				
					if(cp.size()<=c){count_int=cp.size()-1; return true; } 
					else
					{
						if(isOp(cp.get(c),c))
						{
							//..........
							c++;
							if(exp1(cp,c))
							{
								c=count_int;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c).equals(")"))
						{
							count_int=c;
							return true;			
						}
						else if(words.isParentFollow(cp.get(c),follow_of_Parent))
						{

							count_int=c;
							return true;
									
						}
						else if(cp.get(c).equals(",") && !ifParams)
						{
							count_int=c;
							
							return true;
						}
						else if(cp.get(c).equals("]"))
						{
							count_int=c;
							return true;
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean exp21(ArrayList<String>  cp,int c)
				{
					if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
					
					if(exp2(cp,c))
					{
						c=count_int;
						
						return true;
					}
				
					else if(cp.get(c).equals("["))
					{
						
						c++;
						
						if(words.isConst(cp.get(c)))
						{
							
							c++;
							
							if(cp.get(c).equals("]"))
							{
								c++;
								if(exp2(cp,c))
								{
									
									c=count_int;
									
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;	
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						else if(cp.get(c).equals("ID"))
						{

							if(vp.get(c+1).equals("("))
							{
								if(!lookupFunction(vp.get(c)))
								{
									System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								
							}//Not function
							else //Maybe variable?
							{
								
									if(!lookUpVar(vp.get(c)))
									{
										System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									
								
							}
							c++;
							
							if(exp1(cp,c))
							{
								
								c=count_int;
						
									//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
								if(cp.get(c).equals("]"))
								{
									
									c++;
									if(exp2(cp,c))
									{
										
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c-1).equals("]"))
								{
									
									if(exp2(cp,c))
									{
										
										c=count_int;
										return true;
									}
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					
					else if(O1(cp,c))
					{
						
						c=count_int;
						if(exp2(cp,c))
						{
							
							c=count_int;
							count_int=c;
						
							if(c>=cp.size()){c=count_int=cp.size()-1;}
							return true;
						}
						else if(cp.get(c).equals("Dot operator"))
						{
							if(exp21(cp,c))
							{
								c=count_int;
								count_int=c;

								if(c>=cp.size()){c=count_int=cp.size()-1;}
								return true;
							}
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean params(ArrayList<String>  cp,int c)
				{
				
					if(cp.size()<=c){count_int=cp.size()-1;return true; }
					ifParams=true;

					if(cp.get(c).equals(")"))
					{
						c++;
						count_int=c;
						
						ifParams=false;
						return true;
					}
					else if(exp1(cp,c))
					{
						
						c=count_int;
						//System.out.println(cp.get(c)+":exp1 of params"+c);
						if(c<cp.size())
						{
							
							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								//System.out.println(cp.get(c)+":dF"+c);
								ifParams=false;
								return true;
							}
							else if(cp.get(c).equals("]"))
							{
								c++;
								count_int=c;
								//System.out.println("___"+cp.get(c));
								ifParams=false;
								return true;
							}
							else if(cp.get(c).equals(",")) //what if datatype?
							{
								
								c++;
								 if(cp.get(c).equals("ID"))
								 {
									 c++;
									 if(cp.get(c).equals("ID"))
									 {
										 c++;
										 if(params(cp,c))
										 {
												
												c=count_int;
												ifParams=false;
												return true;
									     }
									 }
								 }
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
						
							c=count_int;
							ifParams=false;
							return true;
						}
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean news(ArrayList<String>  cp,int c)
				{
					
				if(isUniOp(cp.get(c)))
				{
					c++;
					if(exp1(cp,c))
					{
						
						c=count_int; 
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c=count_int;
								
								return true;
							}
						}
						else
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
							//c++;
							if(ifParams){count_int=c;return true;}
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
					}
				
				}
				else if(exp1(cp,c))
					{
						
						c=count_int; 
						//System.out.println(c+":33:"+cp.get(c));
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c=count_int;
								
								return true;
							}
							else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
							{
							   c=count_int;	
								return true;
							}
						}
						else
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c-1).equals(")"))
						{
							//c++;
							if(ifParams){count_int=c;return true;}
							if(exp2(cp,c))
							{
								
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
						}
						else if(words.isParentFollow(cp.get(c), follow_of_Parent))
						{
							//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
						   c=count_int;	
							return true;
						}
					
					}
					if(cp.get(c).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c++;
						
						if(exp2(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
					}
					else if(cp.get(c).equals("datatype"))
					{
						ChangeType=vp.get(c);
						typeChanged=true;
						c++;
						if(cp.get(c).equals(")"))
						{
							
							c++;
							if(exp1(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								return true;
							}
						}
					}
					else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					{
						//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
					   c=count_int;	
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			boolean O1(ArrayList<String>  cp,int c) // Incomplete
				{
					if(cp.get(c).equals("Dot operator"))
					{
						
						c++;
						
						if(cp.get(c).equals("ID"))
						{
							
						
							if(exp1(cp,c))
							{
								c=count_int;
								if(O1(cp,c))
								{
									c=count_int;
									count_int=c;
									//System.out.println(c+":fff:"+cp.get(c));
									return true;
								}
							}
						 if(cp.get(c).equals("["))
							{
								
								c++;
								//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
								if(words.isConst(cp.get(c)))
								{
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("333,"+c+","+cp.get(c));
										return true;
									}
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							else if(cp.get(c).equals("Dot operator"))
							{
								if(O1(cp,c))
								{
									c=count_int;
									count_int=c;
									//System.out.println(c+":fff:"+cp.get(c));
									return true;
								}
							}
							else if(cp.get(c).equals(")"))
							{
							
								c=count_int=c;
								if(ifParams)
								ifParams=false;
								return true;
							}
						}
						else if(words.isConst(cp.get(c)))
						{
							c++;
							count_int=c;
							return true;
						}
					}
					else if(cp.get(c).equals("&"))
					{
						c++;
						count_int=c;
						return true;
					}
					if(FailedCounter<c)
						FailedCounter=c;
					return false;
				}
			String OperationOnOperands(String left,String right,String op,int cpInd)
			{
				if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
				{
					if(left.equals("dig"))
					{
						if(right.equals("dig"))
							return "dig";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "dig";
						
					}
					else if(left.equals("frac"))
					{
						if(right.equals("dig"))
							return "frac";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "frac";
						
					}
					else if(left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "alpha";
						else if(right.equals("frac"))
							return "alpha";
						else if(right.equals("alpha"))
							return "alpha";
						
					}
					
				}
				else if(op.equals("suma"))
				{
					if(left.equals("dig"))
					{
						if(right.equals("dig"))
							return "dig";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "dig";
						
					}
					else if(left.equals("frac"))
					{
						if(right.equals("dig"))
							return "frac";
						else if(right.equals("frac"))
							return "frac";
						else if(right.equals("alpha"))
							return "frac";
						
					}
					else if(left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "alpha";
						else if(right.equals("frac"))
							return "alpha";
						else if(right.equals("alpha"))
							return "alpha";
						
					}
					else if(left.equals("beta"))
					{
						if(right.equals("dig"))
							return "beta";
						else if(right.equals("frac"))
							return "beta";
						else if(right.equals("alpha"))
							return "beta";
						
						
					}
					
				}
				else if(cp.get(cpInd).equals("RO"))
				{
					if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
					{
						if(right.equals("dig"))
							return "boolean";
						else if(right.equals("frac"))
							return "boolean";
						else if(right.equals("alpha"))
							return "boolean";
						
					}
					
				}
				else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
				{
					return "boolean" ;
				}
				return "x";
			}
			String valueOp(int index)
				{
					
					return vp.get(index);
				}
		
			boolean isOp(String cp,int c)
				{
					
					if(cp.equals("RO"))//
					{
						 return true;
					}
					else if(cp.equals("Assignment") && AllowAssignation)
					{
						return true;
					}
					else if(cp.equals("BWOP"))
					{
						if(valueOp(c).equals("bitOr"))
						 return true;
					}
					else if(cp.equals("BWOP"))
					{
						if(valueOp(c).equals("bitAnd"))
						 return true;
					}
					else if(cp.equals("OR"))
					{
						 return true;
					}
					else if(cp.equals("AddSum"))
					{
						 return true;
					}
					else if(cp.equals("Mux"))
					{
						
						 return true;
					}
					else if(cp.equals("DIVMOD"))
					{
						 return true;
					}
					else if(cp.equals("Incdec") || cp.equals("Not operator"))
					{
						 return true;
					}
					else if(cp.equals("LO"))
					{
						 return true;
					}

					
					
					return false;
				}
				
			boolean isUniOp(String a)
				{
					if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
					{
						
						return true;

					}
					
					return false;
				}
			
			
			
			 //<-- Finding functionExist or Not?
			 private boolean lookupFunction(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 						return true;
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return true;
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
				 					return true;
				 			} 
			 		}
			 		
			 		return false;
			 }

			private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				int i= indexOfParent(Parent,nameFunction);
				 if(i!=-1) 
					 return true;
				 return false;
			 }
			 private int indexOfParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
							 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
								 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
								 	return i;
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								return k;
						 }
					 }
					
				 return -1;
			 }
			
	//Finding functionExist or Not? -->
			 
	//<-- Getting paramList
			 private ArrayList<String> getFuncParams(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 					{
				 						//for(int p=0;p<tFunction.paramList.size();i++)
				 							
				 						return tFunction.paramList.get(i);
				 					}
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				
				 					return getParamsFunctionin_ClassParent(name,CurrentPname);
				 			} 
			 		}
			 		
			 		return null;
			 }

			private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				return getParamsParent(Parent,nameFunction);
				 
			 }
			 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
								 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
									 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								
						 }
					 }
					
				 return null;
			 }
			
	//Getting paramList  -->	
			 
	//<-- Getting returnType
			 private String getFuncReturnType(String name)
			 {
				 //1)CurrentClass--tFunction
				 	if(Syntaxtification.ct.Cname.size()>0 )
				 	{
				 			if(tFunction.nameFunction.size()>0)
				 				for(int i=0;i<tFunction.nameFunction.size();i++)
				 				{
				 					if(tFunction.nameFunction.get(i).equals(name))
				 						return tFunction.ReturnType.get(i);
				 				}
					 //TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
				 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
				 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
				 			
					 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				
				 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
				 			} 
			 		}
			 		
			 		return null;
			 }

			private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
			 {
				return getReturnTypeParent(Parent,nameFunction);
				 
			 }
			 private String getReturnTypeParent(String Parent,String nameFunction)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
						
								 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
									 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
								
						 }
					 }
					
				 return null;
			 }
			
	//Getting returnType  -->	
			private boolean lookUpVar(String name)
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return true;
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				if(LookUpVarin_ClassParent(name,CurrentPname))
		 					return true;
		 			} 
		 		 return false;
		 	 }
			private boolean LookUpVarin_ClassParent(String name,String Parent)
			 {
				int i= VarindexOfParent(Parent,name);
				 if(i!=-1) 
					 return true;
				 return false;
			 }
			private int VarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return i;
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								return k;
						 }
					 }
					
				 return -1;
			 }
	
			private String getReturnTypeVar(String name) //Method to call
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return tv.varDT.get(i);
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				return returnTypeVarin_ClassParent(name,CurrentPname);
		 					
		 			} 
		 		 return null;
		 	 }
			private String returnTypeVarin_ClassParent(String name,String Parent)
			 {
				return returnTypeVarindexOfParent(Parent,name);
				
			 }
			private String returnTypeVarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								
						 }
					 }
					
				 return null;
			 }

			private boolean isArrayType(String name) //Method to call
		 	 {
				 if(tv.varDT.size()>0) //Current class;
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return (tv.isVarArrayTable.get(i));
		 				 
				//TopHierarchical CLass
		 			if(isInner)
		 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
		 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
		 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
		 	 //ParentClass of Current Class
		 			if(!CurrentPname.equals("-"))
		 			{
		 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
		 					
		 			} 
		 		 return false;
		 	 }
			private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
			 {
				return isArrayTypeVarindexOfParent(Parent,name);
				
			 }
			private boolean isArrayTypeVarindexOfParent(String Parent,String name)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
					 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
					 {
						 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
								 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
								 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
						 //Didn't find now look in its parent
						 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
						 {
							 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
								
						 }
					 }
					
				 return false;
			 }
	
			}

//isArrayType

//EXP Semantic if END


		 
	}

	//IF-ELSE Semantic END
	
	
	//FunctionCallModel Semantic Start
		 class functionCallModel {

				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={"Terminator"};
				ExpModelFunction exp ;
				int lc=0;
				functionCallModel(){}
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						
					
				 }
				 public functionCallModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						
						
				 }
		 //<-- Finding functionExist or Not?
				 private boolean lookupFunction(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return true;
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return true;
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
					 					return true;
					 			} 
				 		}
				 		
				 		return false;
				 }

				private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					int i= indexOfParent(Parent,nameFunction);
					 if(i!=-1) 
						 return true;
					 return false;
				 }
				 private int indexOfParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
								 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
									 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
									 	return i;
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									return k;
							 }
						 }
						
					 return -1;
				 }
				
		//Finding functionExist or Not? -->
				 
		//<-- Getting paramList
				 private ArrayList<String> getFuncParams(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 					{
					 						//for(int p=0;p<tFunction.paramList.size();i++)
					 							
					 						return tFunction.paramList.get(i);
					 					}
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getParamsFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getParamsParent(Parent,nameFunction);
					 
				 }
				 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting paramList  -->	
				 
		//<-- Getting returnType
				 private String getFuncReturnType(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return tFunction.ReturnType.get(i);
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getReturnTypeParent(Parent,nameFunction);
					 
				 }
				 private String getReturnTypeParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
							
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting returnType  -->	
				 ArrayList<String> params =new ArrayList<>();
				 public boolean functionCall(ArrayList<String> cp,int c)
				 {
					 
					
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookupFunction(vp.get(c)))
						 {
							 System.out.println("No function Found "+vp.get(c)+" at char number :"+c);
							 return false;
						 }
						 if(vp.get(c).equals("main"))
						 {
							 System.out.println("main function can't be called "+vp.get(c)+" at char number :"+c);
							 return false;
						 }
						
							 params=getFuncParams(vp.get(c));
							
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							
							 if(params(cp,c))
							 {
								 
								 c=count_int;
								 
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count=count_int=c;
									 
									 return true; 
								 }
								 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								 {
									// c++;
									 count=count_int=c;
									
									 return true; 
								 }
							 }
						 }
					 }
					 
					 return false;
				 }
				 boolean params(ArrayList<String> cp,int c)
				 {
					 //Pass the params in exp and test to see if it exits or not?
					 
					 exp = new ExpModelFunction(cp,vp,follow_for_exp,false,scope,params,lc);
					 if(exp.exp(cp, c))
					 {
							
						 c=exp.count;
						
						 if(c<cp.size())
						 {
							 //System.out.println("Exp chal gai function mey paframs:"+cp.get(c));
							if(c<cp.size()-2)
							{
								  if(cp.get(c).equals(","))
									{
										lc++;
										c++;
										 if(params(cp, c))
										 {
											 c=count_int;
												count=count_int;
												return true;
											 
											 
										 }
									}
								 else if(cp.get(c).equals(")"))
								 {
									
										count_int=++c;
									
										return true;
									 
								 }
								  
							}
							 else if(cp.get(c).equals(")"))
							 {
								
									c=count_int=c;
								
									return true;
								 
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								   
									count_int=c;
								
									return true;
								 
							 }
							 
						
						 }
					 }
					 if(lc<params.size() && !exp.extra)
						{
							System.out.println("few params at char number: "+c);
							return false;
						}
					 return false;
				 }


//EXP Semantic Function  START

				 class ExpModelFunction 
					{
						KeyWords words = new KeyWords();
						public int count=0,count_int=0,FailedCounter=0,scope=0;
						 String follow_of_Parent[];
						 ArrayList<String> cp = new ArrayList();
						 ArrayList<String> vp = new ArrayList();
						 ArrayList<String> paramsList = new ArrayList();
						 boolean ifParams=false,typeChanged=false,opFlag=false,extra;
						 String returnType="",ChangeType="";
						int listcounter=0;
						 String leftOp="",rightOp="";
						 public boolean AllowAssignation=true;
						ExpModelFunction(){}
						public ExpModelFunction(ArrayList<String> cp,ArrayList<String> vp,int scope,ArrayList<String> params,int lc)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							paramsList=params;
							listcounter=lc;
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],int scope,ArrayList<String> params,int lc)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							paramsList=params;
							follow_of_Parent=follow;
							listcounter=lc;
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,boolean allow,int scope,ArrayList<String> params,int lc)
						{
							this.cp=cp;
							this.vp=vp;
							AllowAssignation=allow;
							this.scope=scope;
							paramsList=params;
							listcounter=lc;
						}
						public ExpModelFunction(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope,ArrayList<String> params,int lc)
						{
							this.cp=cp;
							this.vp=vp;
							this.scope=scope;
							paramsList=params;
							follow_of_Parent=follow;
							AllowAssignation=allow;
							listcounter=lc;
						}
						
					public 	boolean exp(ArrayList<String> ClassPart,int count)
						{
						
							if(isUniOp(ClassPart.get(count)))
							{
								count++;
								if(exp1(ClassPart,count))
								{
									count=count_int;
									this.count=count;
									returnType="boolean";
									return true;
								}
							}
							else if(exp1(ClassPart,count))
							{
								
								count=count_int;
								count_int=0;
								this.count=count;
								returnType="boolean";
								
								return true;
							}
							else if(words.isParentFollow(cp.get(count), follow_of_Parent))
							{
								count=count_int;
								this.count=count;
								returnType="boolean";
								return true;
							}
							if(FailedCounter<count)
								FailedCounter=count;
							returnType=null;
							return false;
						}

					boolean exp1(ArrayList<String> cp,int c)
					{
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
						
							if(cp.get(c).equals("ID"))
							{
								
								if(vp.get(c+1).equals("("))
								{
									String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
									functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
									fcm.follow_for_exp[0]=")";
									
									if(fcm.functionCall(cp, c))
									{
										
										String dt=getFuncReturnType(vp.get(c));
										String rdt=getFuncReturnType(vp.get(c));
										
										c=fcm.count-1;
										if(!opFlag) //No Left Operator Exists right now
										{
											
											
											if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
																leftOp=dt; //left operator
																opFlag=true;
																
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
											{
													System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
													return false;
				
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else
											{
												
												if(listcounter<paramsList.size())
												{
													
													if(!typeChanged)
													{
														if(!dt.equals(paramsList.get(listcounter)))
														{
															System.out.println("invalid parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
															listcounter++;
													}	
													else if(typeChanged)
													{
														typeChanged=false;
														if(!compTypeCast(dt,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else if(!ChangeType.equals(paramsList.get(listcounter)))
														{
															System.out.println("invalid parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
															listcounter++;
													}		
												}
												else
												{
													System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
													extra=true;
													return false;
												}
											}
								
										}
										else if(opFlag) //Left Operator Exists
										{
											
											{
												if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
												{
		
													rightOp=rdt;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													leftOp=returnType;
													opFlag=true;
													
												}
												else //No more operators
												{

													if(listcounter<paramsList.size())
													{
														
															
															if(!typeChanged)
															{
																if(!rdt.equals(paramsList.get(listcounter)))
																{
																	System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
																	return false;
																}
																else
																{
																	rightOp=rdt;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	listcounter+=1;
																	 
																}
															}
															else if(typeChanged)
															{
																typeChanged=false;
																String temp=rdt;
																if(!compTypeCast(temp,ChangeType))
																{
																	System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																else if(!ChangeType.equals(paramsList.get(listcounter)))
																{
																	System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																else
																{
																	rightOp=ChangeType;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	listcounter++;
																}
															}
															
														
													}
													else
													{
														System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
														extra=true;
														return false;
													}
												}
											}
											
										}
									
									}
									
								}//Not function
								else //Maybe variable?
								{
									if(!lookUpVar(vp.get(c)))
									{
										System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									else
									{
										if(!opFlag) //No Left Operator Exists right now
										{
											String dt=getReturnTypeVar(vp.get(c));
											if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
																leftOp=dt; //left operator
																opFlag=true;
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
											{
												if(dt.equals("dig") || dt.equals("frac"))
												{
													returnType="dig";
												}
												else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
												{
													System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
											{
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(!vp.get(c+1).equals("."))
											{

												if(listcounter<paramsList.size())
												{
													
														
													if(!typeChanged)
													{
														if(!dt.equals(paramsList.get(listcounter)))
														{
															System.out.println("invalidat parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
														{
															if(cp.get(c+1).equals("["))
															if(!isArrayType(vp.get(c)))
															{
																System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															listcounter++;
														}
													}
													else if(typeChanged)
													{
														typeChanged=false;
														
														if(!compTypeCast(dt,ChangeType))
														{
															System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else if(!ChangeType.equals(paramsList.get(listcounter)))
														{
															System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														else
															listcounter++;
													}
														
													
												}
												else
												{
													System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
													extra=true;
													return false;
												}
											}
										}
										else if(opFlag) //Left Operator Exists
										{
											String rdt=getReturnTypeVar(vp.get(c));
											if(cp.get(c+1).equals("["))
											{
												if(!isArrayType(vp.get(c)))
												{
													System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
													return false;
												}
											}
											else
											{
												if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
												{
												
													
													rightOp=rdt;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													leftOp=returnType;
													opFlag=true;
													
												}
												else //No more operators
												{

													if(listcounter<paramsList.size())
													{
														
															
															if(!typeChanged)
															{
																if(!rdt.equals(paramsList.get(listcounter)))
																{
																	System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
																	return false;
																}
																else
																{
																	rightOp=rdt;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	listcounter+=1;
																	 
																}
															}
															else if(typeChanged)
															{
																typeChanged=false;
																String temp=rdt;
																if(!compTypeCast(temp,ChangeType))
																{
																	System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																else if(!ChangeType.equals(paramsList.get(listcounter)))
																{
																	System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																else
																{
																	rightOp=ChangeType;
																	returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																	if(returnType.equals("x"))
																	{
																		System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																		return false;
																	}
																	opFlag=false;
																	listcounter++;
																}
															}
															
														
													}
													else
													{
														System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
														extra=true;
														return false;
													}
												}
											}
											
										}
									}
								
								}
								c++;
								
								if(exp21(cp,c))
								{
									c=count_int;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								if(!opFlag) //No Left Operator Exists right now
								{
									if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
														leftOp=words.getConst(cp.get(c)); //left operator
														opFlag=true;
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
									{
										if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
										{
											returnType="dig";
										}
										else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
									{
										if(words.getConst(cp.get(c)).equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else
									{

										if(listcounter<paramsList.size())
										{
											
												
												if(!typeChanged)
												{
													if(!words.getConst(cp.get(c)).equals(paramsList.get(listcounter)))
													{
														System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
														return false;
													}
													else
													{
														
														listcounter+=1;
														 
													}
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=words.getConst(cp.get(c));
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													else if(!ChangeType.equals(paramsList.get(listcounter)))
													{
														System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													else
														listcounter++;
												}
												
											
										}
										else
										{
											System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
											extra=true;
											return false;
										}
									}
								}
								else if(opFlag) //Left Operator Exists
								{
									if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
									{

										rightOp=words.getConst(cp.get(c));
										returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
										if(returnType.equals("x"))
										{
											System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										leftOp=returnType;
										opFlag=true;
										
									}
									else //No more operators
									{

										if(listcounter<paramsList.size())
										{
											
												
												if(!typeChanged)
												{
													if(!words.getConst(cp.get(c)).equals(paramsList.get(listcounter)))
													{
														System.out.println("invalidate parameter error "+vp.get(c)+" at line number: "+c+" expected: "+paramsList.get(listcounter));;
														return false;
													}
													else
													{
														rightOp=words.getConst(cp.get(c));
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														listcounter+=1;
														 
													}
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=words.getConst(cp.get(c));
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													else if(!ChangeType.equals(paramsList.get(listcounter)))
													{
														System.out.println(ChangeType+"invalid parameter error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													else
													{
														rightOp=ChangeType;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														listcounter++;
													}
												}
												
											
										}
										else
										{
											System.out.println("Extra parameter error "+vp.get(c)+" at line number: "+c);
											extra=true;
											return false;
										}
									}
								}
								c++;
								
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
							}
							else if(cp.get(c).equals("("))
							{
								c++;
								if(news(cp,c))
								{
								
									c=count_int;
									return true;
								}
							}
							if(FailedCounter<c)
							FailedCounter=c;
							return false;
					}
					boolean compTypeCast(String func,String Modified)
					{
						if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
						{
							if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
								return true;
							
						}
						else if(Modified.equals("beta"))
						{
							
							if(func.equals("beta"))
								return true;
						}
						
						return false;
					}
					boolean exp2(ArrayList<String>  cp,int c)
					{
						
							if(cp.size()<=c){count_int=cp.size()-1; return true; } 
							else
							{
								if(isOp(cp.get(c),c))
								{
									//..........
									c++;
									if(exp1(cp,c))
									{
										c=count_int;
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals(")"))
								{
									count_int=c;
									return true;			
								}
								else if(words.isParentFollow(cp.get(c),follow_of_Parent))
								{

									count_int=c;
									return true;
											
								}
								else if(cp.get(c).equals(",") && !ifParams)
								{
									count_int=c;
									
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									count_int=c;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean exp21(ArrayList<String>  cp,int c)
						{
							if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
							
							if(exp2(cp,c))
							{
								c=count_int;
								
								return true;
							}
							
							else if(cp.get(c).equals("["))
							{
								
								c++;
								
								if(words.isConst(cp.get(c)))
								{
									
									c++;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;	
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								else if(cp.get(c).equals("ID"))
								{

									if(vp.get(c+1).equals("("))
									{
										if(!lookupFunction(vp.get(c)))
										{
											System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
									}//Not function
									else //Maybe variable?
									{
										
											if(!lookUpVar(vp.get(c)))
											{
												System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											
										
									}
									c++;
									
									if(exp1(cp,c))
									{
										
										c=count_int;
								
											//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
										if(cp.get(c).equals("]"))
										{
											
											c++;
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											if(FailedCounter<c)
												FailedCounter=c;
										}
										else if(cp.get(c-1).equals("]"))
										{
											
											if(exp2(cp,c))
											{
												
												c=count_int;
												return true;
											}
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							
							else if(O1(cp,c))
							{
								
								c=count_int;
								if(exp2(cp,c))
								{
									
									c=count_int;
									count_int=c;
								
									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(exp21(cp,c))
									{
										c=count_int;
										count_int=c;

										if(c>=cp.size()){c=count_int=cp.size()-1;}
										return true;
									}
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean params(ArrayList<String>  cp,int c)
						{
						
							if(cp.size()<=c){count_int=cp.size()-1;return true; }
							ifParams=true;

							if(cp.get(c).equals(")"))
							{
								c++;
								count_int=c;
								
								ifParams=false;
								return true;
							}
							else if(exp1(cp,c))
							{
								
								c=count_int;
								//System.out.println(cp.get(c)+":exp1 of params"+c);
								if(c<cp.size())
								{
									
									if(cp.get(c).equals(")"))
									{
										c++;
										count_int=c;
										//System.out.println(cp.get(c)+":dF"+c);
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals("]"))
									{
										c++;
										count_int=c;
										//System.out.println("___"+cp.get(c));
										ifParams=false;
										return true;
									}
									else if(cp.get(c).equals(",")) //what if datatype?
									{
										
										c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(cp.get(c).equals("ID"))
											 {
												 c++;
												 if(params(cp,c))
												 {
														
														c=count_int;
														ifParams=false;
														return true;
											     }
											 }
										 }
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
								
									c=count_int;
									ifParams=false;
									return true;
								}
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean news(ArrayList<String>  cp,int c)
						{
							
						if(isUniOp(cp.get(c)))
						{
							c++;
							if(exp1(cp,c))
							{
								
								c=count_int; 
								
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
							}
						
						}
						else if(exp1(cp,c))
							{
								
								c=count_int; 
								//System.out.println(c+":33:"+cp.get(c));
								if(c>=cp.size())
								{
									if(cp.get(c-1).equals(")"))
									{
										if(ifParams){count_int=c;return true;}
										c=count_int;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
									{
									   c=count_int;	
										return true;
									}
								}
								else
								if(cp.get(c).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c++;
									
									if(exp2(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
								}
								else if(cp.get(c-1).equals(")"))
								{
									//c++;
									if(ifParams){count_int=c;return true;}
									if(exp2(cp,c))
									{
										
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										
										return true;
									}
									else if(words.isParentFollow(cp.get(c), follow_of_Parent))
									{
										//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
									   c=count_int;	
										return true;
									}
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							
							}
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c).equals("datatype"))
							{
								ChangeType=vp.get(c);
								typeChanged=true;
								c++;
								if(cp.get(c).equals(")"))
								{
									
									c++;
									if(exp1(cp,c))
									{
										c=count_int; //count_int=0; //count_int=0;
										//count_int=0;
										return true;
									}
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					boolean O1(ArrayList<String>  cp,int c) // Incomplete
						{
							if(cp.get(c).equals("Dot operator"))
							{
								
								c++;
								
								if(cp.get(c).equals("ID"))
								{
									
								
									if(exp1(cp,c))
									{
										c=count_int;
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
									  if(cp.get(c).equals("["))
									{
										
										c++;
										//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
										if(words.isConst(cp.get(c)))
										{
											c++;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count_int=c;
												//System.out.println("333,"+c+","+cp.get(c));
												return true;
											}
										}
									}
									else if(cp.get(c).equals("&"))
									{
										c++;
										count_int=c;
										return true;
									}
									else if(cp.get(c).equals("Dot operator"))
									{
										if(O1(cp,c))
										{
											c=count_int;
											count_int=c;
											//System.out.println(c+":fff:"+cp.get(c));
											return true;
										}
									}
									else if(cp.get(c).equals(")"))
									{
									
										c=count_int=c;
										if(ifParams)
										ifParams=false;
										return true;
									}
								}
								else if(words.isConst(cp.get(c)))
								{
									c++;
									count_int=c;
									return true;
								}
							}
							else if(cp.get(c).equals("&"))
							{
								c++;
								count_int=c;
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
							return false;
						}
					String OperationOnOperands(String left,String right,String op,int cpInd)
					{
						if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
						{
							if(left.equals("dig"))
							{
								if(right.equals("dig"))
									return "dig";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "dig";
								
							}
							else if(left.equals("frac"))
							{
								if(right.equals("dig"))
									return "frac";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "frac";
								
							}
							else if(left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "alpha";
								else if(right.equals("frac"))
									return "alpha";
								else if(right.equals("alpha"))
									return "alpha";
								
							}
							
						}
						else if(op.equals("suma"))
						{
							if(left.equals("dig"))
							{
								if(right.equals("dig"))
									return "dig";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "dig";
								
							}
							else if(left.equals("frac"))
							{
								if(right.equals("dig"))
									return "frac";
								else if(right.equals("frac"))
									return "frac";
								else if(right.equals("alpha"))
									return "frac";
								
							}
							else if(left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "alpha";
								else if(right.equals("frac"))
									return "alpha";
								else if(right.equals("alpha"))
									return "alpha";
								
							}
							else if(left.equals("beta"))
							{
								if(right.equals("dig"))
									return "beta";
								else if(right.equals("frac"))
									return "beta";
								else if(right.equals("alpha"))
									return "beta";
								
								
							}
							
						}
						else if(cp.get(cpInd).equals("RO"))
						{
							if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
							{
								if(right.equals("dig"))
									return "boolean";
								else if(right.equals("frac"))
									return "boolean";
								else if(right.equals("alpha"))
									return "boolean";
								
							}
							
						}
						else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
						{
							return "boolean" ;
						}
						return "x";
					}
					String valueOp(int index)
						{
							
							return vp.get(index);
						}
				
					boolean isOp(String cp,int c)
						{
							
							if(cp.equals("RO"))//
							{
								 return true;
							}
							else if(cp.equals("Assignment") && AllowAssignation)
							{
								return true;
							}
							else if(cp.equals("BWOP"))
							{
								if(valueOp(c).equals("bitOr"))
								 return true;
							}
							else if(cp.equals("BWOP"))
							{
								if(valueOp(c).equals("bitAnd"))
								 return true;
							}
							else if(cp.equals("OR"))
							{
								 return true;
							}
							else if(cp.equals("AddSum"))
							{
								 return true;
							}
							else if(cp.equals("Mux"))
							{
								
								 return true;
							}
							else if(cp.equals("DIVMOD"))
							{
								 return true;
							}
							else if(cp.equals("Incdec") || cp.equals("Not operator"))
							{
								 return true;
							}
							else if(cp.equals("LO"))
							{
								 return true;
							}

							
							
							return false;
						}
						
					boolean isUniOp(String a)
						{
							if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
							{
								return true;
							}
							return false;
						}
					private boolean lookUpVar(String name)
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return true;
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return true;
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				if(LookUpVarin_ClassParent(name,CurrentPname))
				 					return true;
				 			} 
				 		 return false;
				 	 }
					private boolean LookUpVarin_ClassParent(String name,String Parent)
					 {
						int i= VarindexOfParent(Parent,name);
						 if(i!=-1) 
							 return true;
						 return false;
					 }
					private int VarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return i;
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),name);
										return k;
								 }
							 }
							
						 return -1;
					 }
			
					private String getReturnTypeVar(String name) //Method to call
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return tv.varDT.get(i);
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				return returnTypeVarin_ClassParent(name,CurrentPname);
				 					
				 			} 
				 		 return null;
				 	 }
					private String returnTypeVarin_ClassParent(String name,String Parent)
					 {
						return returnTypeVarindexOfParent(Parent,name);
						
					 }
					private String returnTypeVarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
										
								 }
							 }
							
						 return null;
					 }

					private boolean isArrayType(String name) //Method to call
				 	 {
						 if(tv.varDT.size()>0) //Current class;
				 		 for(int i=0;i<tv.varTable.size();i++)
				 			 if(tv.varTable.get(i).equals(name))
				 				 return (tv.isVarArrayTable.get(i));
				 				 
						//TopHierarchical CLass
				 			if(isInner)
				 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
				 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
				 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
				 	 //ParentClass of Current Class
				 			if(!CurrentPname.equals("-"))
				 			{
				 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
				 					
				 			} 
				 		 return false;
				 	 }
					private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
					 {
						return isArrayTypeVarindexOfParent(Parent,name);
						
					 }
					private boolean isArrayTypeVarindexOfParent(String Parent,String name)
					 {
						 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
							 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
							 {
								 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
										 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
										 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
								 //Didn't find now look in its parent
								 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
								 {
									 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
										
								 }
							 }
							
						 return false;
					 }
			
					}

//isArrayType

//EXP Semantic Function END

				 
				 
				 
			}

	//FunctionCallModel Semantic End
	
		//SwitchModel Semantic Start
			class SwitchModel {
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0,scope=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				String follow_of_Parent[];
				String follow_for_exp[]={")"};
				String follow_for_body[]={"]"};
				ExpModel exp ;
				BodyModel body;
				tableVar tv=new tableVar();
				SwitchModel(){}
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
						body=new BodyModel(cp,vp,follow_for_body,scope,tv);
						follow_of_Parent=null;
				 }
				 public SwitchModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
				 {
						this.cp = cp;
						this.vp = vp;
						this.scope=scope;
						follow_of_Parent=Follow;
						body=new BodyModel(cp,vp,follow_for_body,scope,tv);
						exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
						
				 }
				 public boolean Switch(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("check"))
					 {
						 c++;
						 if(cp.get(c).equals("("))
						 {
							 c++;
							 if(exp.exp(cp, c))
							 {
								//...............Semantic To be Done ....................................................
								  if(exp.returnType!="dig" && exp.returnType!="alpha" && exp.returnType!="beta"){
								  System.out.println("Invalid operand "+ vp.get(c)+" at char number:"+c); return false;}
								 //.......................................................................................
								 c=exp.count;
								
								if(cp.get(c).equals(")"))
								{
									c++;
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										if(test(cp,c))
										{
											c=count_int;
											count=count_int;
											return true;
										}
										 else if(cp.get(c).equals("]"))
										 {
										   		c++;
										   		count_int=c;
											   count=count_int;
												return true;
						   	
										}
										if(failedCount<c)
								   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								else if(cp.get(c-1).equals(")"))
								{
									
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 if(test(cp,c))
											{
												c=count_int;
												count=count_int;
												return true;
											}
											 else if(cp.get(c).equals("]"))
											 {
											   		c++;
											   		count_int=c;
												   count=count_int;
													return true;
							   	
											}
										 if(failedCount<c)
									   			failedCount=c;
										  
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
								if(failedCount<c)
						   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 boolean test(ArrayList<String> cp,int c)
				 {
					 if(cp.get(c).equals("test"))
					 {
						 c++;
						 String[] temp={":"};
						 ExpModel exp1=new ExpModel(this.cp,vp,temp,false,scope);
					
						 if(exp1.exp(cp, c))
						 {
							
							//...............Semantic To be Done ....................................................
							  if(exp.returnType!="dig" && exp.returnType!="alpha" && exp.returnType!="beta"){
							  System.out.println("Invalid operand "+ vp.get(c)+" at char number:"+c); return false;}
							 //.......................................................................................
							 c=exp1.count;
							
							 if(cp.get(c).equals(":"))
							 {
								 
								 c++;
								//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope,tv);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
								   if(failedCount<c)
							   			failedCount=c;
							 }
							 else  if(cp.get(c-1).equals(":"))
							 {
				//call for body
								 
								 String follow_for_body1[]={"]","default","test"};
								 BodyModel body1=new BodyModel(cp,cp,follow_for_body1,scope,tv);
								   if(body1.body(cp,c))
								   {
								   	c=body1.count;
								   
								   		
								   		if(cp.get(c).equals("default"))
								   		{
								   			c++;
								   			if(cp.get(c).equals(":"))
								   			{
								   				c++;
								   				if(body.body(cp, c))
								   				{
								   					c=body.count;
								   					if(cp.get(c).equals("]"))
								   					{	
												   			count_int=c;
												   			//count=count_int;
												   			return true;	
								   					}
								   					if(failedCount<c)
											   			failedCount=c;
								   				}
								   				if(failedCount<c && body.failedCount<c)
										   			failedCount=c;
											   	else if(body.failedCount>c)
											   			failedCount=body.failedCount;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   			
								   		}
								   		else if (cp.get(c).equals("test"))
								   		{
								   			
								   			if(test(cp,c))
								   			{
								   				
								   				c=count_int;
									   			return true;
								   			}
								   			if(failedCount<c)
									   			failedCount=c;
								   		}
								   		else if(cp.get(c).equals("]"))
								   		{	
								   			count_int=c;
								   			return true;	
								   		}
								   		else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								   		{
								   			count_int=c;
								   			return true;
								   		}
								   		else
								   		{
								   			count_int=c;
								   			return true;
								   		}
							}
							 }
							 if(failedCount<c)
						   			failedCount=c;
							 
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 return false;
				 }
				 
			}

			//SwitchModel Semantic End
	//While Loop Semantic Start
	class WhileModel 
	{
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private	BodyModel body;
			 tableVar tv=new tableVar();
			 private WhileModel(){}
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope,tv);
				follow_of_Parent=null;
		 }
		 public WhileModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				follow_of_Parent=Follow;
				body=new BodyModel(cp,vp,follow_for_body,scope,tv);
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				
		 }
		 public boolean test(ArrayList<String>cp,int c)
		 {
				
				if(cp.get(c).equals("while loop"))
				 {
					 c++;
					 if(cp.get(c).equals("("))
					 {
						 
						 if(exp.exp(cp, c))
						 {
							 //.............Semantic to be done..................
							  if(exp.returnType!="boolean") { System.out.println("Boolean expected at char number: "+c); return false;}
							 //...............................
							 c=exp.count;
							
							 
							 if(cp.get(c).equals(")"))
							{
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 //call for body
									  
									   if(body.body(cp,c))
									   {
									   	c=body.count;
									   	if(cp.get(c).equals("]"))
									   	{
									   		
									   		c++;
									   		if(c>cp.size()-1){c=count_int=c;
								   			count=count_int;
								   			return true;}
									   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   		else
									   		{
									   			c=count_int=c;
									   			count=count_int;
									   			return true;
									   		}
									   	}
									   	if(failedCount<c)
								   			failedCount=c;
									   }
									   if(failedCount<c && body.failedCount<c)
								   			failedCount=c;
									   	else if(body.failedCount>c)
									   			failedCount=body.failedCount;
									  
								 }
								 if(failedCount<c)
							   			failedCount=c;
							}
							 //if(c.prev is equal to )
							 if(cp.get(c-1).equals(")"))
								{
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 //call for body
										  if(body.body(cp,c))
										   {
										   	c=body.count;
										   	if(cp.get(c).equals("]"))
										   	{
										   		c++;
										   		if(c>cp.size()-1){c=count_int=c;
									   			count=count_int;
									   			return true;}
										   		 if(words.isParentFollow(cp.get(c), follow_of_Parent))
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   		else
										   		{
										   			c=count_int=c;
										   			count=count_int;
										   			return true;
										   		}
										   	}
										   	if(failedCount<c)
									   			failedCount=c;
										   }
										  if(failedCount<c && body.failedCount<c)
									   			failedCount=c;
										   	else if(body.failedCount>c)
										   			failedCount=body.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
								}
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				if(failedCount<c)
		   			failedCount=c;
				 return false;
				 
		 }
	
	}
	//While Loop Semantic End
	//For Loop Semantic Start
		 class ForModel 
		 {
		  private KeyWords words = new KeyWords();
		  public int count=0,failedCount=0,scope=0;
		  private int count_int=0;
		  private ArrayList<String> cp = new ArrayList();
		  private ArrayList<String> vp = new ArrayList();
		  private String follow_of_Parent[];
		  private String follow_for_body[]={"]"};
		  private BodyModel body;
		  private tableVar tv = new tableVar();
		  private tableVar ptv = new tableVar();
		  private ForModel(){}
		  public ForModel(ArrayList<String> cp, ArrayList<String> vp,int scope,tableVar ptv)
		  {
		 			this.cp = cp;
		 			this.vp = vp;
		 			this.scope=scope;
		 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		 			this.ptv=ptv;
		 			follow_of_Parent=null;
		   }
		   public ForModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,tableVar ptv)
		   {
		 			this.cp = cp;
		 			this.vp = vp;
		 			this.scope=scope;
		 			follow_of_Parent=Follow;	
		 			this.ptv=ptv;
		 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		   }
		   	public boolean For(ArrayList<String>cp,int c)
		   	{
		   		if(cp.get(c).equals("for loop"))
		   		{
		   			
		   			c++;
		   			
		   			if(cp.get(c).equals("("))
		   			{
		   				c++;
		   				
		   				if(X(cp,c))
		   				{
		   					c=count_int;
		   					
		   					if(Y(cp,c))
		   					{
		   						c=count_int;
		   						
		   						if(Z(cp,c))
		   						{
		   							c=count_int;
		   							//System.out.println(c+"Here:"+cp.get(c));
		   							if(cp.get(c).equals(")"))
		   							{
		   								c++;
		   								if(cp.get(c).equals("["))
		   								{
		   									c++;
		   									if(body.body(cp, c))
		   									{
		   										c=body.count;
		   										if(cp.get(c).equals("]"))
		   										{
		   											c++;
		   											count=count_int=c;
		   											return true;
		   										}
		   									}
		   								}
		   							}
		   						}
		   					}
		   				}
		   			}
		   		}
		   		
		   		return false;
		   	}
		   	
		   	private  boolean X(ArrayList<String>cp,int c)
		   	{
		   		declarationModelSemanticFor decl=new declarationModelSemanticFor(cp,vp,scope);
		   	
		   		if(decl.decl(cp, c))
		   		{
		   			
		   			c=decl.count;
		   			////System.out.println("Here"+cp.get(c-1));
		   			if(cp.get(c).equals("Terminator"))
		   			{
		   				c++;
		   				count_int=c;
		   				return true;
		   			}
		   			else if(cp.get(c-1).equals("Terminator"))
		   			{
		   				count_int=c;
		   				return true;
		   			}
		   		}
		   		else if(Assign(cp,c,"Terminator".split("\\ ")))
		   		{
		   			
		   			c=count_int;
		   			if(cp.get(c).equals("Terminator"))
		   			{
		   				c++;
		   				count_int=c;
		   				return true;
		   			}
		   		}
		   		else if(cp.get(c).equals("Terminator"))
		 		{
		   			c++;
		 			count_int=c;
		 			return true;
		 		}
		   		return false;
		   	}
private boolean lookUpVar(String name)
		 	 {
				 if(tv.varDT.size()>0)
		 		 for(int i=0;i<tv.varTable.size();i++)
		 			 if(tv.varTable.get(i).equals(name))
		 				 return true;
				 
		 		 return false;
		 	 }
		   	private int findclassIndex(String name)
		 	 {
		   		String[] n=name.split("\\class_");
		   		System.out.println(""+n[0]);
		   	
		   		if(n.length>1)
		 		 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
		 			 if(Syntaxtification.ct.Cname.equals(name))
		 				 return i;
				 
		 		 return -1;
		 	 }
		   	private int findInnerclassIndex(String name)
		 	 {
		   		String[] n=name.split("\\class_");
		   		System.out.println(""+n[0]);
		   	
		   		if(n.length>1)
				 if(Syntaxtification.ct.innerClassList.size()>0)
		 		 for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++)
		 			 if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.equals(n[1]))
		 				 return i;
				 
		 		 return -1;
		 	 }
	
			private  boolean LookupVarP(String name)
			 {
				 for(int i=0; i<ptv.isVarArrayTable.size();i++)
				 {
					 if(name.contentEquals(ptv.varTable.get(i)))
					 {
						 
						 return true;
				 
					 }
				 }
				// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
				 return false;
			 }
		   	private boolean lookUpDeclareVar(String name)
		 	 {
				 if(ClassModel_Semantic.this.tv.varDT.size()>0)
		 		 for(int i=0;i<ClassModel_Semantic.this.tv.varTable.size();i++)
		 			 if(ClassModel_Semantic.this.tv.varTable.get(i).equals(name))
		 			 {
		 				 if(ClassModel_Semantic.this.tv.varDT.get(i).equals("dig") || ClassModel_Semantic.this.tv.varDT.get(i).equals("frac") || ClassModel_Semantic.this.tv.varDT.get(i).contains("class"))
		 				 return true;
		 			}
				 for(int i=0; i<ptv.isVarArrayTable.size();i++)
				 {
					 if(name.contentEquals(ptv.varTable.get(i)))
					 {
						 
						 return true;
				 
					 }
				 }
		 		 return false;
		 	 }
		   	private  boolean Assign(ArrayList<String>cp,int c,String follow_for_exp_Assign[])
		   	{
		   		
		   		ExpModel expMain = new ExpModel(cp,vp,follow_for_exp_Assign,false,scope);
		   		if(cp.get(c).equals("ID"))
		   		{
		   			
		   			if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
		   			{
		   				System.out.println("No Variable found/Incompatible datatypes: "+vp.get(c)+ " at char number: "+c);
		   			}
		   			c++;
		   			
		   			if(expMain.isOp(cp.get(c), c) && !cp.get(c).equals("Assignment"))
		   			{
		   				
		   				c++;
		   				if(cp.get(c).equals("Assignment"))
		   				{
		   					c++;
		   					if(expMain.exp(cp, c))
		   					{
		   						c=expMain.count;
		   						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		 	  						{
		 	  							count_int=c;
		 	  							return true;
		 	  						}
		 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		 	  						{
		 	  							c=c-1;
		 	  							count_int=c;
		 	  							return true;
		 	  						}
		   							
		   					}
		   				}
		   			}
		   			else if(cp.get(c).equals("Assignment"))
		 			{
		   				
		 					c++;
		 					
		 					if(expMain.exp(cp, c))
		 					{
		 						c=expMain.count;
		 						//System.out.println(c+" Z:"+cp.get(c));
		 						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		 	  						{
		 	  							count_int=c;
		 	  							return true;
		 	  						}
		 	  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		 	  						{
		 	  							c=c-1;
		 	  							count_int=c;
		 	  							return true;
		 	  						}
		 							
		 					}
		 			}
		   			else if(cp.get(c).equals("Dot operator"))//if is object attribute;
		   			{
		   				c++;
		   				if(cp.get(c).equals("ID"))
		   		  		{
		   					//Here 2 c-2
		   					if(findclassIndex(vp.get(c-2))!=-1)
		   					{
		   						int ind=findclassIndex(vp.get(c-2));
		   						if(cp.get(c+1).equals("("))
		   						{
		   							for(int i=0;i<Syntaxtification.ct.functionslist.get(ind).nameFunction.size();i++)
		   								if(!Syntaxtification.ct.functionslist.get(ind).nameFunction.equals(vp.get(c)))
		   								{
		   									System.out.println("No Method found "+vp.get(c)+" at char number :" +c);
		   									return false;
		   								}
		   						}
		   						else if(!cp.get(c+1).equals("("))
		   						{
		   							for(int i=0;i<Syntaxtification.ct.Cvariable.get(ind).varTable.size();i++)
		   								if(!Syntaxtification.ct.Cvariable.get(ind).varTable.equals(vp.get(c)))
		   								{
		   									System.out.println("No field found "+vp.get(c)+" at char number :" +c);
		   									return false;
		   								}
		   						}
		   						
		   					}
		   					else if(findInnerclassIndex(vp.get(c-2))!=-1)
		   					{
		   						int ind=findInnerclassIndex(vp.get(c-2));
		   						if(cp.get(c+1).equals("("))
		   						{
		   							for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).functionslist.get(ind).nameFunction.size();i++)
		   								if(!Syntaxtification.ct.innerClassList.get(ClassIndex).functionslist.get(ind).nameFunction.equals(vp.get(c)))
		   								{
		   									System.out.println("No Method found "+vp.get(c)+" at char number :" +c);
		   									return false;
		   								}
		   						}
		   						else if(!cp.get(c+1).equals("("))
		   						{
		   							for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cvariable.get(ind).varTable.size();i++)
		   								if(!Syntaxtification.ct.innerClassList.get(ClassIndex).Cvariable.get(ind).varTable.equals(vp.get(c)))
		   								{
		   									System.out.println("No field found "+vp.get(c)+" at char number :" +c);
		   									return false;
		   								}
		   						}
		   					}
		   					c++;
		   					
		   		  			if(expMain.isOp(cp.get(c), c))
		   		  			{
		   		  				c++;
		   		  				if(cp.get(c).equals("Assignment"))
		   		  				{
		   		  					c++;
		   		  					if(expMain.exp(cp, c))
		   		  					{
		   		  						c=expMain.count;
		   		  						if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   		  						{
		   		  							count_int=c;
		   		  							return true;
		   		  						}
		   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   		  						{
		   		  							c=c-1;
		   		  							count_int=c;
		   		  							return true;
		   		  						}
		   		  							
		   		  					}
		   		  				}
		   		  			}
		   		  			else if(cp.get(c).equals("Assignment"))
		   					{
		   							c++;
		   							if(expMain.exp(cp, c))
		   							{
		   								c=expMain.count;
		   								if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   		  						{
		   		  							count_int=c;
		   		  							return true;
		   		  						}
		   		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   		  						{
		   		  							c=c-1;
		   		  							count_int=c;
		   		  							return true;
		   		  						}
		   									
		   							}
		   					}
		   		  			else if(cp.get(c).equals("["))//If Array exists
		   		  			{
		   		  				c++;
		   		  				String[] follow_for_exp_Assignment={"]"};
		   		  				ExpModel exp1 = new ExpModel(cp,vp,follow_for_exp_Assignment,false,scope);
		   		  				if(exp1.exp(cp, c))
		   		  				{
		   		  					c=exp1.count;
		   		  					if(cp.get(c).equals("]"))
		   		  					{
		   		  						c++;
		   		  					if(exp1.isOp(cp.get(c), c))
		   		  		  			{
		   		  		  				c++;
		   		  		  				if(cp.get(c).equals("Assignment"))
		   		  		  				{
		   		  		  					c++;
		   		  		  					if(expMain.exp(cp, c))
		   		  		  					{
		   		  		  						c=expMain.count;
		   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							c=c-1;
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   		  		  							
		   		  		  					}
		   		  		  				}
		   		  		  			}
		   		  		  			else if(cp.get(c).equals("Assignment"))
		   		  					{
		   		  							c++;
		   		  							if(expMain.exp(cp, c))
		   		  							{
		   		  								c=expMain.count;
		   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							c=c-1;
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   		  									
		   		  							}
		   		  					}
		   		  					}
		   		  					else if(cp.get(c-1).equals("]"))
		   		  					{
		   		  						
		   		  					if(exp1.isOp(cp.get(c), c))
		   		  		  			{
		   		  		  				c++;
		   		  		  				if(cp.get(c).equals("Assignment"))
		   		  		  				{
		   		  		  					c++;
		   		  		  					if(expMain.exp(cp, c))
		   		  		  					{
		   		  		  						c=expMain.count;
		   		  		  					if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							c=c-1;
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   		  		  							
		   		  		  					}
		   		  		  				}
		   		  		  			}
		   		  		  			else if(cp.get(c).equals("Assignment"))
		   		  					{
		   		  							c++;
		   		  							if(expMain.exp(cp, c))
		   		  							{
		   		  								c=expMain.count;
		   		  							if(words.isParentFollow(cp.get(c), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   	  		  						else if(words.isParentFollow(cp.get(c-1), follow_for_exp_Assign))
		   	  		  						{
		   	  		  							c=c-1;
		   	  		  							count_int=c;
		   	  		  							return true;
		   	  		  						}
		   		  									
		   		  							}
		   		  					}
		   		  					}
		   		  				}
		   		  			}
		   		  		}
		   			}
		   		}
		   		return false;
		   	}
		   	private  boolean Y(ArrayList<String>cp,int c)
		   	{
		   		String [] follow_for_exp={"Terminator"};
		   		ExpModel exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
		   		
		   		if(exp.exp(cp, c))
		   		{
		   			c=exp.count;
		   			if(!exp.returnType.equals("boolean"))
		   			{
		   				System.out.println("Expecting Boolean at char no: "+c);
		   				return false;
		   			}
		   			if(cp.get(c).equals("Terminator"))
		   			{
		   				c++;
		   				count_int=c;
		   				return true;
		   			}
		   			else if(cp.get(c-1).equals("Terminator"))
		   			{
		   				count_int=c;
		   				return true;
		   			}
		   		}
		   		return false;
		   	}
		   	private  boolean Z(ArrayList<String>cp,int c)
		   	{
		   		
		   		
		   		
		   		if(cp.get(c).equals("Incdec"))
		   		{
		   			c++;
		   			if(cp.get(c).equals("ID"))
		   			{
		   				if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
						{
							System.out.println("No variable found Error at line number:"+c+",,"+vp.get(c));
							return false;
						}
		   				c++;
		   				if(cp.get(c).equals(")"))
		   				{
		   					count_int=c;
		   					return true;
		   				}
		   			}
		   		}
		   		else if(cp.get(c).equals("ID"))
		 		{
		   			
		   			if(!lookUpDeclareVar(vp.get(c)) && ! lookUpVar(vp.get(c)))
					{
						System.out.println("No variable found Error at line number:"+c+",,"+vp.get(c));
						return false;
					}
		 				c++;
		 				
		 				if(cp.get(c).equals("Incdec"))
		 		  		{
		 		  			c++;
		 		  				if(cp.get(c).equals(")"))
		 		  				{
		 		  					count_int=c;
		 		  					return true;
		 		  				}
		 		  			
		 		  		}
		 				else if(Assign(cp,c-1,")".split("\\ "))) //Passed ID's index.
		 		  		{
		 					
		 		  			c=count_int;
		 		  			if(cp.get(c).equals(")"))
		 		  			{
		 		  				
		 		  				count_int=c;
		 		  				return true;
		 		  			}
		 		  		}
		 				
		 		}
		   		return false;
		   	}
		   	
//DEclaration for loop
			class declarationModelSemanticFor 
			{
				KeyWords words = new KeyWords();
				public int count=0,count_int=0,failedCount=0;
				 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
					 ArrayList<String> varTable = new ArrayList();
				String follow_of_Parent;
				String follow_for_exp[]={"Terminator","]"};
				ExpModel exp ;
				int start=0,end,scope;
				String tempDT="";
				declarationModelSemanticFor(){}
			 public declarationModelSemanticFor(ArrayList<String> cp, ArrayList<String> vp,int scope)
			 {
					this.cp = cp;
					this.vp = vp;
					this.scope=scope;
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			 }
			 public declarationModelSemanticFor(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
			 {
					this.cp = cp;;
					this.vp = vp;
					follow_of_Parent=Follow;
					
					this.scope=scope;
					exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			 }
			 private boolean LookupVar(String name)
			 {
				 for(int i=0; i<tv.isVarArrayTable.size();i++)
				 {
					 if(name.contentEquals(tv.varTable.get(i)))
					 {
						 if(tv.varDT.get(i).equals("dig") || tv.varDT.get(i).equals("frac"))
						 return true;
				 
					 }
				 }
				// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
				 return false;
			 }
			private  boolean LookupVarP(String name)
			 {
				 for(int i=0; i<ptv.isVarArrayTable.size();i++)
				 {
					 if(name.contentEquals(ptv.varTable.get(i)))
					 {
						 if(ptv.varDT.get(i).equals("dig") || ptv.varDT.get(i).equals("frac"))
						 return true;
				 
					 }
				 }
				// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
				 return false;
			 }
			 private boolean lookUpClass(String name)
		 	 {
		 		if(Syntaxtification.ct.Cname.size()>0 )
		 		{
		 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
		 			if(Syntaxtification.ct.Cname.get(i).equals(name))
			 			 return true;
		 			
			 		 
			 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
			 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
			 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
			 		 			 return true;
			 		 
		 		}
		 		System.out.println(name);
		 		return false;
		 	 }
			 private boolean field(ArrayList<String> classPart,int count)
			 {
				 if(classPart.get(count).equals("datatype"))
				 {
					 
					 count++;
					
					if(classPart.get(count).equals("ID"))
					{
						if(LookupVar(vp.get(count)))
						{
							System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
							return false;
						}
						else if(LookupVarP(vp.get(count)))
						{
							System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
							return false;
						}
						else
						{
							if(!vp.get(count-1).equals("dig") && !vp.get(count-1).equals("frac")) 
							{
								System.out.println("Incompatible datatype:"+count+",,"+vp.get(count-1));
								return false;
							}
							tv.varTable.add(vp.get(count)); //Adding name
							tv.varScope.add(scope); //Adding Scope
							tv.varDT.add(vp.get(count-1)); //Adding DataType
							tempDT=vp.get(count-1);
							//Syntaxtification.varTable.add(vp.get(count));
						}
						count++;
						
						if(init(classPart,count))
						{
							count=count_int;
							
							if(list(classPart,count))
							{
								
								count=count_int;
								
								//System.out.println(count+"e"+classPart.get(count));
								if(classPart.get(count).equals("Terminator"))
								{
									count++;
									//this.count=count;
									count_int=count;
									exp.count_int=0;
									
									return true;
								}
									  
							}
							else if(classPart.get(count).equals("Terminator"))
							{
								count++;
								this.count=count;
								count_int=count;
								exp.count_int=0;
								
								return true;
							}
						}
						else if(list(classPart,count))
						{
							count=count_int;
							if(count>=classPart.size())
							{
								if(classPart.get(count-1).equals("Terminator"))
								{
									//this.count=count;
									count_int=count;
									exp.count_int=0;
									return true;
								}
							}
							if(classPart.get(count).equals("Terminator"))
							{
								
								count++;
								
								count_int=count;
								exp.count_int=0;
								//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
								return true;
							}
						}
						else if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
				 }
				 return false;
			 }
			 public boolean decl(ArrayList<String> classPart,int count)
			 {
				
			
				 if(field(classPart,count))
				 {
					 	count=count_int;
					 	this.count=count;
						count_int=0;
						exp.count_int=0;	
						return true;
				 }
				 else if(cp.get(count).equals("ID"))
				 {
					 
							System.out.println("Incompatible datatype:"+count+",,"+vp.get(count));
							return false;
						
				 }
				 
				 return false;
			 }
			 boolean init(ArrayList<String> classPart,int count)
			 {
				 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
				    if(classPart.get(count).equals("Assignment"))
					{
				    	
						count++;
						tv.isVarArrayTable.add(false);
						if(exp.exp(classPart, count))
						{
							//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
							count_int=exp.count;
							if(!tempDT.equals(exp.returnType))
							{
								System.out.println("Invalid assignation "+vp.get(count)+" at char number: "+count);
							}
							count=count_int;
							count_int=count;
							return true;
						}
					}
					else if(array(classPart,count))
					{
						tv.isVarArrayTable.add(true);
						count=count_int;
						count_int=count;
						return true;
					}
					else if(classPart.get(count).equals(","))
					{
						tv.isVarArrayTable.add(false);
						count++;
						count_int=count;
						return true;
					}
				    tv.isVarArrayTable.add(false);
						return false;
			 }
			 boolean list(ArrayList<String> classPart,int count)
			 {
				 
				 //System.out.println(count+"e"+classPart.get(count));
				 if(classPart.get(count).equals(","))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
							if(LookupVar(vp.get(count)))
							{
								System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
								return false;
							}
							else
							{
								
								tv.varTable.add(vp.get(count)); //Adding name
								tv.varDT.add(vp.get(count-1)); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
								tempDT=vp.get(count-1);
							}
							count++;
							if(init(classPart,count))
							{
								count=count_int;
								if(list(classPart,count))
								{
									count=count_int;
									count_int=count;
									return true;
								}
								
							}
							else if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								return true;
							}
						}
				 }
				 else if(classPart.get(count).equals("Terminator"))
				 {
					// count++;
					 count_int=count;
						return true;
				 }
				 return false;
			 }
			 
			 //Other models
			 boolean array(ArrayList<String> classPart,int count)
			 {
				 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
				 if(classPart.get(count).equals("Assign Op"))
				 {
					count++;
					//Syntaxtification.tv.isVarArrayTable.add(true);
					if(exp.exp(classPart, count))
					{
						if(!exp.returnType.equals("dig"))
						{
							System.out.println("Expecting integer value at char count: "+count);
							return false;
						}
						count_int=exp.count;
						count=count_int;
						count_int=count;
						return true;
					}
				 }
				 else if(classPart.get(count).equals("Assignment"))
				 {
					 count++;
					 if(classPart.get(count).equals("["))
					 {
						 count++;
						 if(words.isConst(classPart.get(count)))
						 {
							 if(!words.getConst(classPart.get(count)).equals(tempDT))
							 {
								 System.out.println("Expecting "+tempDT+" value at char count: "+count);
									return false; 
							 }
							 count++;
							 if(clist1(classPart,count))
							 {
								 count=count_int;
								 return true;
							 }
						 }
						 else if(classPart.get(count).equals("]"))
						 {
							 count=count_int;
								count_int=count;
							 return true;
						 }
					 }
				 }
				 return false;
			 }
			 boolean clist1(ArrayList<String> cp,int c)
			 {
			 	if(c>=cp.size()){ c=cp.size()-1; return true;}
			 	 if(cp.get(c).equals(","))			//Call to clist1
			 	 {
			 		 c++;
			 		 if(words.isConst(cp.get(c)))
			 		 {
			 			 if(!words.getConst(cp.get(c)).equals(tempDT))
						 {
							 System.out.println("Expecting "+tempDT+" value at char count: "+c);
								return false; 
						 }
			 			 c++;
			 			 if(clist1(cp,c))
			 			 {
			 				 c=count_int;
							 count_int=c;
							 return true;
						 
			 			 }
			 			 
			 		 }
			 	 }
			 	 else if(cp.get(c).equals("]"))
			 	 {
			 		c++;
					 count_int=c;
					 return true;
			 	 }
			 	return false;
			 } 
			 boolean Object(ArrayList<String> cp,int c)
			 {
				 
				 if(c>=cp.size())
				 {
					 return true;
				 }
				 
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)))
						{
							System.out.println("No class found Error at char number:"+c+",,"+vp.get(c));
							return false;
						}
						
					 
					 
					  c++;
					 if(cp.get(c).equals("ID"))
					 {
						
						 
						  	if(LookupVar(vp.get(c)))
							{
								System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
								return false;
							}
							else
							{
								tv.varTable.add(vp.get(c)); //Adding name
								tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
								tempDT="class_"+vp.get(c-1);
							}
					  
						  	c++;
						
						 if(obj1(cp,c))
						 {
							 
							 c=count_int;
							 
							 if(Ob(cp, c))
							 {
								 
								 if(c>=cp.size())
								 {
									 if(cp.get(c-1).equals("Terminator"))
									 {
										 count_int=c;
										 return true;
									 }
								 }
								 else
								 {
									 if(cp.get(c-1).equals("Terminator"))
									 {
										 count_int=c;
										 return true;
									 }
									 else if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
								 }
							 }
						 }
					 }
				 }
				 return false;
			 }
			 boolean LookUpClassParent(String child,String Parent)
			 {
				 int indexOfChild=0,i=0;
				 indexOfChild=indexOfChild(child);
				i= indexOfParent(Parent,indexOfChild);
				 if(i!=-1)
				 {
					 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
					 return true;
				 }
					
				
				 
				 return false;
			 }
			 int indexOfParent(String Parent,int indexOfChild)
			 {
				 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
				 {
					
					 return indexOfChild;
				 }
				 else
				 {
					 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
						 return -1;
					 else
					 {
						 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
						
					 }
								 
				 }
				 
			 }
			 int indexOfChild(String Child)
			 {
				 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 {
					 if(Syntaxtification.ct.Cname.get(i).equals(Child))
					 {
						 return i;
					 }
				 }
				 return -1;
			 }
			 boolean obj1(ArrayList<String> cp, int c)
			 {
				 
				 if(cp.get(c).equals("Assignment"))
				 {
					 Syntaxtification.tv.isVarArrayTable.add(false);
					 c++;
					 if(cp.get(c).equals("new"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 
							 if(vp.get(c).equals(vp.get(c-4)))
								{
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
								}
							 else if(!vp.get(c).equals(vp.get(c-4)))
								{
								  
								 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
										
										
										
									}
								 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
										
											System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
											return false;
										
										
										
									}
								  else
								  {
									System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
									return false;
								  }
								}
								
						 }
						
					 }
					
				 }
				 else if(cp.get(c).equals("["))
				 {
					 
					 c++;
				
					 Syntaxtification.tv.isVarArrayTable.add(true);
					 if(words.isConst(cp.get(c)))
					 {
						 c++;
						 if(cp.get(c).equals("]"))
						 {
							 c++;
							 //Here init_ob
							 
							if(init_ob(cp,c))
							{
								c=count_int;
							
								 return true;
							}
						 }
					 }
					 else if(exp.exp(cp, c))
					 {
						 
						 c=exp.count;
						
						 count_int=c;
						 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
						 else
						 {
							 
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 //Here init_ob
								if(init_ob(cp,c))
								{
									c=count_int;
									 return true;
								}
							 }
							 else  if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 //Here init_ob
								if(init_ob(cp,c))
								{
									c=count_int;
									 return true;
								}
							 }
						 }
						
					 }
				 }
				 else if(cp.get(c).equals("Terminator"))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 count_int=c;
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					 tv.isVarArrayTable.add(false);
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals(","))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 count_int=c;
					 return true;
				 }
				 
				 return false;
			 }
			 boolean init_ob(ArrayList<String> cp,int c)
			 {
				if(c>=cp.size()) return true; 
				
				 if(cp.get(c).equals("Assignment"))
				 {
					 tv.isVarArrayTable.add(false);
					 c++;
					 if(cp.get(c).equals("new"))
					 {
						 c++;
						 
						 if(cp.get(c).equals("ID"))
						 {
							 if(vp.get(c).equals(vp.get(c-4)))
								{
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
								}
							 else if(!vp.get(c).equals(vp.get(c-4)))
								{
								  
								 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
									 if(exp.exp(cp, c))
									 {
										 c=exp.count;
										 count_int=c;
										return true;
									 }
										
										
										
									}
								 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
									{
										
										
											System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
											return false;
										
										
										
									}
								  else
								  {
									System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
									return false;
								  }
								}
						 }
				
					 }
					 
				 }
				 else if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 count_int=c;
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					
					 count_int=c;
					 return true;
				 }
				 else if(cp.get(c).equals(","))
				 {
					 c++;
					 count_int=c;
					 return true;
				 }
				 return false;
			 }
			 boolean Ob(ArrayList<String> cp,int c)
			 {
				 if(c>=cp.size())
				 {
					 return true;
				 }
				 else
				 {
					 if(cp.get(c).equals(","))
					 {
						 c++;
						 if(cp.get(c).equals("ID")) //obj1
						 {
							
								{
									tv.varTable.add(vp.get(c)); //Adding name
									tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
									tv.varScope.add(scope); //Adding Scope
								}
						  
							 c++;
							 if(obj1(cp,c))
							 {
								 c=count_int;
								 if(Ob(cp,c))
								 {
									 c=count_int;
									 if(cp.get(c).equals("Terminator"))
									 {
										 c++;
										 count_int=c;
										 return true;
									 }
								 }
							 }
								
						 }
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 count_int=c;
					
						 
						 return true;
					 }
					 else  if(cp.get(c-1).equals("Terminator"))
					 {
						 
						 count_int=c;
					
						 
						 return true;
					 }
				 }
				 
					 return false;
			 }
			}

			//DECLARATION For SEMANTIC END


			 class ExpModel
				{
					KeyWords words = new KeyWords();
					public int count=0,count_int=0,FailedCounter=0,scope=0;
					 String follow_of_Parent[];
					 ArrayList<String> cp = new ArrayList();
					 ArrayList<String> vp = new ArrayList();
				
					 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
					 String returnType="",ChangeType="";
					
					 String leftOp="",rightOp="";
					 public boolean AllowAssignation=true;
					ExpModel(){}
					public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;

					}
					public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;
						follow_of_Parent=follow;
						
					}
					public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						AllowAssignation=allow;
						this.scope=scope;
					}
					public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
					{
						this.cp=cp;
						this.vp=vp;
						this.scope=scope;
						follow_of_Parent=follow;
						AllowAssignation=allow;
						
					}
					
				public 	boolean exp(ArrayList<String> ClassPart,int count)
					{
					
						if(isUniOp(ClassPart.get(count)))
						{
						
							startUniOp=true;
							count++;
							if(exp1(ClassPart,count))
							{
								count=count_int;
								this.count=count;
							
								return true;
							}
						}
						else if(exp1(ClassPart,count))
						{
							
							count=count_int;
							count_int=0;
							this.count=count;
							
							
							return true;
						}
						else if(words.isParentFollow(cp.get(count), follow_of_Parent))
						{
							count=count_int;
							this.count=count;
							
							return true;
						}
						if(FailedCounter<count)
							FailedCounter=count;
					
						return false;
					}

				boolean exp1(ArrayList<String> cp,int c)
				{
						if(cp.size()<=c){count_int=cp.size()-1;return true; }
					
						if(cp.get(c).equals("ID"))
						{
							
							if(vp.get(c+1).equals("("))
							{
								String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
								functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
								fcm.follow_for_exp[0]=")";
								
								if(fcm.functionCall(cp, c))
								{
									
									String dt=getFuncReturnType(vp.get(c));
									String rdt=getFuncReturnType(vp.get(c));
									
									c=fcm.count-1;
									if(!opFlag) //No Left Operator Exists right now
									{
										
										 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
											{
										 		startUniOp=false;
													System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
													return false;
													
				
											}
											else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
											{
												startUniOp=false;
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
										else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
															leftOp=dt; //left operator
															opFlag=true;
															
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
										{
												System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
												return false;
												
			
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										
							
									}
									else if(opFlag) //Left Operator Exists
									{
										
										{
											if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
											{

												rightOp=rdt;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												leftOp=returnType;
												opFlag=true;
												
											}
											else //No more operators
											{

												
														
														if(!typeChanged)
														{
															
															
																rightOp=rdt;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
																 
															
														}
														else if(typeChanged)
														{
															typeChanged=false;
															String temp=rdt;
															if(!compTypeCast(temp,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															
															else
															{
																rightOp=ChangeType;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
															}
														}
														
													
												
											}
										}
										
									}
								
								}
								/*
								if(!lookupFunction(vp.get(c)))
								{
									System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								else
								{}
								*/
							}//Not function
							else //Maybe variable?
							{
								if(!lookUpVar(vp.get(c)))
								{
									System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								else
								{
									if(!opFlag) //No Left Operator Exists right now
									{
										String dt=getReturnTypeVar(vp.get(c));
										returnType=dt;
										 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
											{
											 startUniOp=false;
												if(dt.equals("dig") || dt.equals("frac"))
												{
													returnType="dig";
												}
												else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
												{
													System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
											else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
											{
												startUniOp=false;
												if(dt.equals("boolean"))
												{
													returnType="boolean";
												}
												else 
												{
													System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
													return false;
												}
													
											}
										else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
															leftOp=dt; //left operator
															opFlag=true;
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
										{
											if(dt.equals("dig") || dt.equals("frac"))
											{
												returnType="dig";
											}
											else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
											{
												System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
										{
											if(dt.equals("boolean"))
											{
												returnType="boolean";
											}
											else 
											{
												System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
												return false;
											}
												
										}
										else if(!vp.get(c+1).equals("."))
										{

												
												if(!typeChanged)
												{
												
														if(cp.get(c+1).equals("["))
														if(!isArrayType(vp.get(c)))
														{
															System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
															return false;
														}
												
												}
												else if(typeChanged)
												{
													typeChanged=false;
													
													if(!compTypeCast(dt,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
												
												}
													
												
											
										}
									}
									else if(opFlag) //Left Operator Exists
									{
										String rdt=getReturnTypeVar(vp.get(c));
										if(cp.get(c+1).equals("["))
										{
											if(!isArrayType(vp.get(c)))
											{
												System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
												return false;
											}
										}
										else
										{
											if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
											{
											
												
												rightOp=rdt;
												returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
												if(returnType.equals("x"))
												{
													System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												leftOp=returnType;
												opFlag=true;
												
											}
											else //No more operators
											{
														if(!typeChanged)
														{
															
															
																rightOp=rdt;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
														
														}
														else if(typeChanged)
														{
															typeChanged=false;
															String temp=rdt;
															if(!compTypeCast(temp,ChangeType))
															{
																System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
																return false;
															}
															
															else
															{
																rightOp=ChangeType;
																returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
																if(returnType.equals("x"))
																{
																	System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
																	return false;
																}
																opFlag=false;
																
															}
														}
														
													
											
											}
										}
										
									}
								}
								
							}
							c++;
							
							if(exp21(cp,c))
							{
								c=count_int;
								return true;
							}
						}
						else if(words.isConst(cp.get(c)))
						{
							
							if(!opFlag) //No Left Operator Exists right now
							{
								
								 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
									{
									 startUniOp=false;
									 
										if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
										{
											returnType="dig";
										}
										else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
									{
										
										startUniOp=false;
										
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										
											
									}
								else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
													leftOp=words.getConst(cp.get(c)); //left operator
													opFlag=true;
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
								{
									if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
									{
										returnType="dig";
									}
									else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
									{
										System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
									if(words.getConst(cp.get(c)).equals("boolean"))
									{
										returnType="boolean";
									}
									else 
									{
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else
								{

										
											if(!typeChanged)
											{
												returnType=words.getConst(cp.get(c));
											}
											else if(typeChanged)
											{
												typeChanged=false;
												String temp=words.getConst(cp.get(c));
												if(!compTypeCast(temp,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												
												else
													{
													returnType=ChangeType;
													}
											}
											
										
									
								}
							}
							else if(opFlag) //Left Operator Exists
							{
								if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
								{

									rightOp=words.getConst(cp.get(c));
									returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
									if(returnType.equals("x"))
									{
										System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									leftOp=returnType;
									opFlag=true;
									
								}
								else //No more operators
								{

											if(!typeChanged)
											{
													rightOp=words.getConst(cp.get(c));
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													opFlag=false;
												
													 
												
											}
											else if(typeChanged)
											{
												typeChanged=false;
												String temp=words.getConst(cp.get(c));
												if(!compTypeCast(temp,ChangeType))
												{
													System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
													return false;
												}
												
												else
												{
													rightOp=ChangeType;
													returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
													if(returnType.equals("x"))
													{
														System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													opFlag=false;
													
												}
											}
											
								
								}
							}
							c++;
							
							if(exp2(cp,c))
							{
								
								c=count_int;
								return true;
							}
						}
						else if(cp.get(c).equals("("))
						{
							c++;
							if(news(cp,c))
							{
							
								c=count_int;
								return true;
							}
						}
						if(FailedCounter<c)
						FailedCounter=c;
						return false;
				}
				boolean compTypeCast(String func,String Modified)
				{
					if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
					{
						if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
							return true;
						
					}
					else if(Modified.equals("beta"))
					{
						
						if(func.equals("beta"))
							return true;
					}
					
					return false;
				}
				boolean exp2(ArrayList<String>  cp,int c)
				{
					
						if(cp.size()<=c){count_int=cp.size()-1; return true; } 
						else
						{
							if(isOp(cp.get(c),c))
							{
								//..........
								c++;
								if(exp1(cp,c))
								{
									c=count_int;
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(cp.get(c).equals(")"))
							{
								count_int=c;
								return true;			
							}
							else if(words.isParentFollow(cp.get(c),follow_of_Parent))
							{

								count_int=c;
								return true;
										
							}
							else if(cp.get(c).equals(",") && !ifParams)
							{
								count_int=c;
								
								return true;
							}
							else if(cp.get(c).equals("]"))
							{
								count_int=c;
								return true;
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean exp21(ArrayList<String>  cp,int c)
					{
						if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
						
						if(exp2(cp,c))
						{
							c=count_int;
							
							return true;
						}
					
						else if(cp.get(c).equals("["))
						{
							
							c++;
							
							if(words.isConst(cp.get(c)))
							{
								
								c++;
								
								if(cp.get(c).equals("]"))
								{
									c++;
									if(exp2(cp,c))
									{
										
										c=count_int;
										
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;	
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(cp.get(c).equals("ID"))
							{

								if(vp.get(c+1).equals("("))
								{
									if(!lookupFunction(vp.get(c)))
									{
										System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
									
								}//Not function
								else //Maybe variable?
								{
									
										if(!lookUpVar(vp.get(c)))
										{
											System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
									
								}
								c++;
								
								if(exp1(cp,c))
								{
									
									c=count_int;
							
										//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
									if(cp.get(c).equals("]"))
									{
										
										c++;
										if(exp2(cp,c))
										{
											
											c=count_int;
											return true;
										}
										if(FailedCounter<c)
											FailedCounter=c;
									}
									else if(cp.get(c-1).equals("]"))
									{
										
										if(exp2(cp,c))
										{
											
											c=count_int;
											return true;
										}
										return true;
									}
									if(FailedCounter<c)
										FailedCounter=c;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						
						else if(O1(cp,c))
						{
							
							c=count_int;
							if(exp2(cp,c))
							{
								
								c=count_int;
								count_int=c;
							
								if(c>=cp.size()){c=count_int=cp.size()-1;}
								return true;
							}
							else if(cp.get(c).equals("Dot operator"))
							{
								if(exp21(cp,c))
								{
									c=count_int;
									count_int=c;

									if(c>=cp.size()){c=count_int=cp.size()-1;}
									return true;
								}
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean params(ArrayList<String>  cp,int c)
					{
					
						if(cp.size()<=c){count_int=cp.size()-1;return true; }
						ifParams=true;

						if(cp.get(c).equals(")"))
						{
							c++;
							count_int=c;
							
							ifParams=false;
							return true;
						}
						else if(exp1(cp,c))
						{
							
							c=count_int;
							//System.out.println(cp.get(c)+":exp1 of params"+c);
							if(c<cp.size())
							{
								
								if(cp.get(c).equals(")"))
								{
									c++;
									count_int=c;
									//System.out.println(cp.get(c)+":dF"+c);
									ifParams=false;
									return true;
								}
								else if(cp.get(c).equals("]"))
								{
									c++;
									count_int=c;
									//System.out.println("___"+cp.get(c));
									ifParams=false;
									return true;
								}
								else if(cp.get(c).equals(",")) //what if datatype?
								{
									
									c++;
									 if(cp.get(c).equals("ID"))
									 {
										 c++;
										 if(cp.get(c).equals("ID"))
										 {
											 c++;
											 if(params(cp,c))
											 {
													
													c=count_int;
													ifParams=false;
													return true;
										     }
										 }
									 }
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
							
								c=count_int;
								ifParams=false;
								return true;
							}
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean news(ArrayList<String>  cp,int c)
					{
						
					if(isUniOp(cp.get(c)))
					{
						c++;
						if(exp1(cp,c))
						{
							
							c=count_int; 
							
							if(c>=cp.size())
							{
								if(cp.get(c-1).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c=count_int;
									
									return true;
								}
							}
							else
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
								//c++;
								if(ifParams){count_int=c;return true;}
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
						}
					
					}
					else if(exp1(cp,c))
						{
							
							c=count_int; 
							//System.out.println(c+":33:"+cp.get(c));
							if(c>=cp.size())
							{
								if(cp.get(c-1).equals(")"))
								{
									if(ifParams){count_int=c;return true;}
									c=count_int;
									
									return true;
								}
								else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
								{
								   c=count_int;	
									return true;
								}
							}
							else
							if(cp.get(c).equals(")"))
							{
								if(ifParams){count_int=c;return true;}
								c++;
								
								if(exp2(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
							}
							else if(cp.get(c-1).equals(")"))
							{
								//c++;
								if(ifParams){count_int=c;return true;}
								if(exp2(cp,c))
								{
									
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									
									return true;
								}
								else if(words.isParentFollow(cp.get(c), follow_of_Parent))
								{
									//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
								   c=count_int;	
									return true;
								}
							}
							else if(words.isParentFollow(cp.get(c), follow_of_Parent))
							{
								//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
							   c=count_int;	
								return true;
							}
						
						}
						if(cp.get(c).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c++;
							
							if(exp2(cp,c))
							{
								c=count_int; //count_int=0; //count_int=0;
								//count_int=0;
								
								return true;
							}
						}
						else if(cp.get(c).equals("datatype"))
						{
							ChangeType=vp.get(c);
							typeChanged=true;
							c++;
							if(cp.get(c).equals(")"))
							{
								
								c++;
								if(exp1(cp,c))
								{
									c=count_int; //count_int=0; //count_int=0;
									//count_int=0;
									return true;
								}
							}
						}
						else if(words.isParentFollow(cp.get(c), follow_of_Parent))
						{
							//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
						   c=count_int;	
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				boolean O1(ArrayList<String>  cp,int c) // Incomplete
					{
						if(cp.get(c).equals("Dot operator"))
						{
							
							c++;
							
							if(cp.get(c).equals("ID"))
							{
								
							
								if(exp1(cp,c))
								{
									c=count_int;
									if(O1(cp,c))
									{
										c=count_int;
										count_int=c;
										//System.out.println(c+":fff:"+cp.get(c));
										return true;
									}
								}
							 if(cp.get(c).equals("["))
								{
									
									c++;
									//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
									if(words.isConst(cp.get(c)))
									{
										c++;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count_int=c;
											//System.out.println("333,"+c+","+cp.get(c));
											return true;
										}
									}
								}
								else if(cp.get(c).equals("&"))
								{
									c++;
									count_int=c;
									return true;
								}
								else if(cp.get(c).equals("Dot operator"))
								{
									if(O1(cp,c))
									{
										c=count_int;
										count_int=c;
										//System.out.println(c+":fff:"+cp.get(c));
										return true;
									}
								}
								else if(cp.get(c).equals(")"))
								{
								
									c=count_int=c;
									if(ifParams)
									ifParams=false;
									return true;
								}
							}
							else if(words.isConst(cp.get(c)))
							{
								c++;
								count_int=c;
								return true;
							}
						}
						else if(cp.get(c).equals("&"))
						{
							c++;
							count_int=c;
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
						return false;
					}
				String OperationOnOperands(String left,String right,String op,int cpInd)
				{
					if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
					{
						if(left.equals("dig"))
						{
							if(right.equals("dig"))
								return "dig";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "dig";
							
						}
						else if(left.equals("frac"))
						{
							if(right.equals("dig"))
								return "frac";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "frac";
							
						}
						else if(left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "alpha";
							else if(right.equals("frac"))
								return "alpha";
							else if(right.equals("alpha"))
								return "alpha";
							
						}
						
					}
					else if(op.equals("suma"))
					{
						if(left.equals("dig"))
						{
							if(right.equals("dig"))
								return "dig";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "dig";
							
						}
						else if(left.equals("frac"))
						{
							if(right.equals("dig"))
								return "frac";
							else if(right.equals("frac"))
								return "frac";
							else if(right.equals("alpha"))
								return "frac";
							
						}
						else if(left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "alpha";
							else if(right.equals("frac"))
								return "alpha";
							else if(right.equals("alpha"))
								return "alpha";
							
						}
						else if(left.equals("beta"))
						{
							if(right.equals("dig"))
								return "beta";
							else if(right.equals("frac"))
								return "beta";
							else if(right.equals("alpha"))
								return "beta";
							
							
						}
						
					}
					else if(cp.get(cpInd).equals("RO"))
					{
						if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
						{
							if(right.equals("dig"))
								return "boolean";
							else if(right.equals("frac"))
								return "boolean";
							else if(right.equals("alpha"))
								return "boolean";
							
						}
						
					}
					else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
					{
						return "boolean" ;
					}
					return "x";
				}
				String valueOp(int index)
					{
						
						return vp.get(index);
					}
			
				boolean isOp(String cp,int c)
					{
						
						if(cp.equals("RO"))//
						{
							 return true;
						}
						else if(cp.equals("Assignment") && AllowAssignation)
						{
							return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitOr"))
							 return true;
						}
						else if(cp.equals("BWOP"))
						{
							if(valueOp(c).equals("bitAnd"))
							 return true;
						}
						else if(cp.equals("OR"))
						{
							 return true;
						}
						else if(cp.equals("AddSum"))
						{
							 return true;
						}
						else if(cp.equals("Mux"))
						{
							
							 return true;
						}
						else if(cp.equals("DIVMOD"))
						{
							 return true;
						}
						else if(cp.equals("Incdec") || cp.equals("Not operator"))
						{
							 return true;
						}
						else if(cp.equals("LO"))
						{
							 return true;
						}

						
						
						return false;
					}
					
				boolean isUniOp(String a)
					{
						if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
						{
							
							return true;

						}
						
						return false;
					}
				
				
				
				 //<-- Finding functionExist or Not?
				 private boolean lookupFunction(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return true;
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return true;
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
					 					return true;
					 			} 
				 		}
				 		
				 		return false;
				 }

				private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					int i= indexOfParent(Parent,nameFunction);
					 if(i!=-1) 
						 return true;
					 return false;
				 }
				 private int indexOfParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
								 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
									 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
									 	return i;
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									return k;
							 }
						 }
						
					 return -1;
				 }
				
		//Finding functionExist or Not? -->
				 
		//<-- Getting paramList
				 private ArrayList<String> getFuncParams(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 					{
					 						//for(int p=0;p<tFunction.paramList.size();i++)
					 							
					 						return tFunction.paramList.get(i);
					 					}
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getParamsFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getParamsParent(Parent,nameFunction);
					 
				 }
				 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting paramList  -->	
				 
		//<-- Getting returnType
				 private String getFuncReturnType(String name)
				 {
					 //1)CurrentClass--tFunction
					 	if(Syntaxtification.ct.Cname.size()>0 )
					 	{
					 			if(tFunction.nameFunction.size()>0)
					 				for(int i=0;i<tFunction.nameFunction.size();i++)
					 				{
					 					if(tFunction.nameFunction.get(i).equals(name))
					 						return tFunction.ReturnType.get(i);
					 				}
						 //TopHierarchical CLass
					 			if(isInner)
					 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
					 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
					 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
					 			
						 //ParentClass of Current Class
					 			if(!CurrentPname.equals("-"))
					 			{
					 				
					 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
					 			} 
				 		}
				 		
				 		return null;
				 }

				private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
				 {
					return getReturnTypeParent(Parent,nameFunction);
					 
				 }
				 private String getReturnTypeParent(String Parent,String nameFunction)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
							
									 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
										 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
									
							 }
						 }
						
					 return null;
				 }
				
		//Getting returnType  -->	
				private boolean lookUpVar(String name)
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return true;
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return true;
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				if(LookUpVarin_ClassParent(name,CurrentPname))
			 					return true;
			 			} 
			 		 return false;
			 	 }
				private boolean LookUpVarin_ClassParent(String name,String Parent)
				 {
					int i= VarindexOfParent(Parent,name);
					 if(i!=-1) 
						 return true;
					 return false;
				 }
				private int VarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return i;
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									return k;
							 }
						 }
						
					 return -1;
				 }

				private String getReturnTypeVar(String name) //Method to call
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return tv.varDT.get(i);
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				return returnTypeVarin_ClassParent(name,CurrentPname);
			 					
			 			} 
			 		 return null;
			 	 }
				private String returnTypeVarin_ClassParent(String name,String Parent)
				 {
					return returnTypeVarindexOfParent(Parent,name);
					
				 }
				private String returnTypeVarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									
							 }
						 }
						
					 return null;
				 }

				private boolean isArrayType(String name) //Method to call
			 	 {
					 if(tv.varDT.size()>0) //Current class;
			 		 for(int i=0;i<tv.varTable.size();i++)
			 			 if(tv.varTable.get(i).equals(name))
			 				 return (tv.isVarArrayTable.get(i));
			 				 
					//TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
			 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
			 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
			 	 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
			 					
			 			} 
			 		 return false;
			 	 }
				private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
				 {
					return isArrayTypeVarindexOfParent(Parent,name);
					
				 }
				private boolean isArrayTypeVarindexOfParent(String Parent,String name)
				 {
					 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
						 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
						 {
							 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
									 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
									 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
							 //Didn't find now look in its parent
							 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
							 {
								 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
									
							 }
						 }
						
					 return false;
				 }

				}

//Exp for for
		 }
		 
	//For Loop Semantic End
		 

//Interfaces Semantic Start
	
	class InterfaceModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private	String follow_for_body[]={"]"};
			 private	ExpModel exp ;
			 private interfaceBodyModel body1;
			 private InterfaceModel(){}
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope+1);
				this.scope=scope;
				body1=new interfaceBodyModel(cp,vp,follow_for_body);
				follow_of_Parent=null;
		 }
		 public InterfaceModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope+1);
				//body=new BodyModel(cp,vp,follow_for_body);
				body1=new interfaceBodyModel(cp,vp,follow_for_body);
				
		 }
		
		 public boolean interfaceFunction(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("sculpture"))
					 {
						 c++;
						 if(cp.get(c).equals("ID"))
						 {
							 if(!lookUpClass(vp.get(c)))
							 {
								 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))	
								 {
									 tI.isPrivate.add(true);
									 tI.isStable.add(true);
									 tI.nameInterfaces.add(vp.get(c));
									 
								 }
								 else
									 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
							 }
							 else
								 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
							 c++;
							 if(S1(cp,c)) //Confirmation of params - (
							 {
								 c=count_int;
									 if(cp.get(c).equals("["))
									 {
										 c++;
										 
										 if(body1.InternBody(cp,c))
										 {
											c=body1.count;
											
											if(cp.get(c).equals("]"))
											{
												c++;
												count=count_int=c;
												return true; //Successful parsed.
												
											}
											if(failedCount<c)
									   			failedCount=c;
										   	
										 }
										 if(failedCount<c && body1.failedCount<c)
									   			failedCount=c;
										   	else if(body1.failedCount>c)
										   			failedCount=body1.failedCount;
									 }
									 if(failedCount<c)
								   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
						   	
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }//if stable not present.
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
						 {
							 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
							 {
								 tI.isPrivate.add(true);
								 tI.isStable.add(false);
								 tI.nameInterfaces.add(vp.get(c));
								 
							 }
							 else
								 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
						 }
						 else
							 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }	
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					   
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 c++;
				 if(cp.get(c).equals("sculpture"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 if(!lookUpClass(vp.get(c)))
						 {
							 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
							 {
								 tI.isPrivate.add(false);
								 tI.isStable.add(true);
								 tI.nameInterfaces.add(vp.get(c));
								 
							 }
							 else
								 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
						 }
						 else
							 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
						 c++;
						 if(S1(cp,c)) //Confirmation of params - (
						 {
							 c=count_int;
								 if(cp.get(c).equals("["))
								 {
									 c++;
									 if(body1.InternBody(cp,c))
									 {
										c=body1.count;
										
										if(cp.get(c).equals("]"))
										{
											c++;
											count=count_int=c;
											return true; //Successful parsed.
											
										}
										if(failedCount<c)
								   			failedCount=c;
									   	
									 }
									 if(failedCount<c && body1.failedCount<c)
								   			failedCount=c;
									   	else if(body1.failedCount>c)
									   			failedCount=body1.failedCount;
								 }
								 if(failedCount<c)
							   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
		
			 }//if stable+access modifier not present.
			 if(cp.get(c).equals("sculpture"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)))
					 {
						 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
						 {
							 tI.isPrivate.add(false);
							 tI.isStable.add(false);
							 tI.nameInterfaces.add(vp.get(c));
							 
						 }
						 else
							 System.out.println("Redeclaration of Interface: "+ vp.get(c)+ " at char number: "+c);
					 }
					 else
						 System.out.println("Redeclaration of class name: "+ vp.get(c)+ " at char number: "+c);
					 c++;
					 if(S1(cp,c)) //Confirmation of params - (
					 {
						 c=count_int;
							 if(cp.get(c).equals("["))
							 {
								 c++;
								 
								 if(body1.InternBody(cp,c))
								 {
									c=body1.count;
									
									if(cp.get(c).equals("]"))
									{
										c++;
										count=count_int=c;
										return true; //Successful parsed.
										
									}
									if(failedCount<c)
							   			failedCount=c;
								   	
								 }
								 if(failedCount<c && body1.failedCount<c)
							   			failedCount=c;
								   	else if(body1.failedCount>c)
								   			failedCount=body1.failedCount;
							 }
							 if(failedCount<c)
						   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }

			
			 return false;

		 }
		 private boolean S1_List(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
						 System.out.println("No interface found Error "+ vp.get(c)+ " at char number: "+c);
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean S1(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("interface"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpInterface(vp.get(c)) && !lookUpInnerInterface(vp.get(c)))
						 System.out.println("No interface found "+ vp.get(c)+ " at char number: "+c);
					 
					 c++;
					 if(S1_List(cp,c))
					 {
						 c=count_int;
						 if(cp.get(c).equals("inheritance"))
						 {
							 c++;
							 if(cp.get(c).equals("ID"))
							 {
								 if(!lookUpClass(vp.get(c)))
									 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
								 c++;
								 if(cp.get(c).equals("["))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 count_int=c;
							 return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("inheritance"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(!lookUpClass(vp.get(c)))
						 System.out.println("No class found "+ vp.get(c)+ " at char number: "+c);
					 c++;
					 if(cp.get(c).equals("["))
					 {
						 count_int=c;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(cp.get(c).equals("["))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
	}
	
//Interfaces Semantic End
	
//Interface Body Semantic Start
	
	
	
	
	class interfaceBodyModel {
		private KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0;
		tableFunction interfaces_tf = new tableFunction();
		 private ArrayList<String> cp = new ArrayList();
		 private ArrayList<String> vp = new ArrayList();
		 private tableFunction tf = new tableFunction();
			 private String follow_of_Parent[];
			 private String follow_for_exp[]={")","["};
			 private interfaceBodyModel(){}
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=null;
		 }
		 public interfaceBodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;			
		 }
		 public boolean InternBody(ArrayList<String> cp,int c)
		 {
			 if(multiprototypes(cp,c))
			 {
				count=c=count_int;
				interfaces_tf.paramList.add(getParams());
				
				return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean multiprototypes(ArrayList<String>cp,int c)
		 {
			 if(prototypeFun(cp,c))
			 {
				c=count_int;
				if(multiprototypes(cp,c))
				 {
					c=count_int;
					return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 count_int=c;
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 return false;
		 }
		 private boolean prototypeFun(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
					 tf.isPrivate.add(false);
					 tf.isStable.add(false);
					 tf.ReturnType.add(vp.get(c-1));
					 tf.nameFunction.add(vp.get(c));
					 tf.varScope.add(scope);
					 c++;
					 
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 c++;
						 
						 if(params(cp, c))
						 {
							 
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								count_int=c;
								return true;
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				   
			 }
			 return false;
		 }
		 private  ArrayList<String> params = new ArrayList();	
		 private ArrayList<String> getParams()
		 {
			 return params;
		 }
		 private void SetParams(String param)
		 {
			 params.add(param);
		 }
		 private tableVar tv = new tableVar();
		 private boolean lookUpVar(String name)
	 	 {
			 if(tv.varDT.size()>0)
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
	 		 return false;
	 	 }
		 private boolean params(ArrayList<String> cp,int c)
		 {
			 if(fields(cp,c))
			 {
				 c=count_int;
				 
				 return true;
			 }
			 else if(Object(cp, c))
			 {
				 c=count_int;
					count_int=c;
					return true;
			 }
			 else if(cp.get(c).equals(")"))
			 {
				 count_int=++c;
				 
				 return true;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
			 return false;
		 }
		 private boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(cp.get(count).equals("datatype"))
				 {
					 count++;
					 if(classPart.get(count).equals("ID"))
						{
						 if(!lookUpVar(vp.get(count)))
							{
								tv.varTable.add(vp.get(count));
								tv.varDT.add(vp.get(count-1));
								tv.varScope.add(scope);
								tv.isVarArrayTable.add(false);
								SetParams(vp.get(count-1));
							}
							else
								System.out.println("Redeclaration of variable "+vp.get(count)+" at char number: "+count);
							count++;
							
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								//System.out.println(count+":rr:"+cp.get(count));
								return true;
							}
							/*else
							{
								
								count--;
								//System.out.println(count+":rr:"+cp.get(count));
								if(Object(classPart, count))
								 {
									 count=count_int;
										count_int=count;
										
										return true;
								 }
							}*/
							if(failedCount<count)
					   			failedCount=count;
						}
					 if(failedCount<count)
				   			failedCount=count;
				 }
				 else if(Object(classPart, count))
				 {
					 count=count_int;
						count_int=count;
						
						return true;
				 }
				 if(failedCount<count)
			   			failedCount=count;
				 
			 }
			 else if(classPart.get(count).equals(")"))
			 {
				 count_int=count;
					return true;
			 }
			 if(failedCount<count)
		   			failedCount=count;
			   
			 return false;
		 }
		 private boolean Object(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			
			 if(cp.get(c).equals("ID"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 if(lookUpClass(vp.get(c-1)))
	 				 	{
	 				 		if(!lookUpVar(vp.get(c)))
	 						 {
	 							 
	 							 tv.varTable.add(vp.get(c));
	 							 tv.varScope.add(scope);
	 							 tv.varDT.add("class_"+vp.get(c-1));
	 							 SetParams(vp.get(c-1));
	 						 }
	 						 else
	 						 {
	 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
	 						 }
	 				 	}
	 				 	 else
						 {
							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
						 }
					 c++;
					
						 if(Ob(cp, c))
						 {
							c=count_int;
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
								 
							 }
							 else
							 {
								 
								 if(cp.get(c-1).equals(")"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals(")"))
								 {
									 
									 count_int=c=c+1;
									 
									 return true;
								 }
								 if(failedCount<c)
							   			failedCount=c;
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   
			 return false;
		 }
		 private boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 
				 if(cp.get(c).equals(","))
				 {
					 c++;
					
					 if(cp.get(c).equals("ID")) //obj1
					 {
						 c++;
						 
						if(cp.get(c).equals("ID"))
						{
							if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
	 						 {
	 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
	 						 }
							c++;
						
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals(")"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
								 else
									 if(cp.get(c-1).equals(")"))
									 {
										 
										 count_int=c;
										 return true;
									 }
										
							
								 
							 }
							 if(failedCount<c)
						   			failedCount=c;
						 
							
						}
						if(failedCount<c)
				   			failedCount=c;
					 }
					 else if(cp.get(c).equals("datatype"))
					 {
					
						 if(fields(cp, c))
						 {
							 c=count_int;
								count_int=c;
								return true;
						 }
						 if(failedCount<c)
					   			failedCount=c;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else  if(cp.get(c).equals(")"))
				 {
					 c++;
					 count_int=c;
					 
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			
			 }
			 if(failedCount<c)
		   			failedCount=c;
			   	
				 return false;
		 }

		 private boolean fields(ArrayList<String> cp,int c)
		 {
			 if(cp.get(c).equals("datatype"))
			 {
				 c++;
				
				if(cp.get(c).equals("ID"))
				{
					
					if(!lookUpVar(vp.get(c)))
					{
						tv.varTable.add(vp.get(c));
						tv.varDT.add(vp.get(c-1));
						tv.varScope.add(scope);
						tv.isVarArrayTable.add(false);
						SetParams(vp.get(c-1));
					}
					else
						System.out.println("Redeclaration of variable "+vp.get(c)+" at char number: "+c);
					c++;
					
					 if(list(cp,c))
					{
						c=count_int;
						
						if(c>=cp.size())
						{
							if(cp.get(c-1).equals(")"))
							{
								
								count_int=c;
								
								return true;
							}
							if(failedCount<c)
					   			failedCount=c;
						}
						else if(cp.get(c).equals(")"))
						{
							c++;
							
							count_int=c;

							return true;
						}
						else if(cp.get(c-1).equals(")"))
						{

							count_int=c;

							return true;
						}
						if(failedCount<c)
				   			failedCount=c;
					}
					 if(failedCount<c)
				   			failedCount=c;
				}
				if(failedCount<c)
		   			failedCount=c;
			 }
			 if(failedCount<c)
		   			failedCount=c;
			 
			 return false;
		 }
	}
//Interface Body Semantic End
	
//Simple Body Semantic Start
	class BodyModel {
		KeyWords words = new KeyWords();
		int count=0,count_int=0,scope=0;
		public int failedCount=0;
		public boolean isSomethingOpen=false;
		ArrayList<String> cp = new ArrayList();
		ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[]; //Usually ]
		String follow_for_exp[]={"Terminator"};
		ExpModel exp ;
		declarationModelSemanticBody decl;
		tableVar tv = new tableVar();
		functionCallModel functCall;
		BodyModel(){}
	 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,int scope,tableVar ptv)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			decl = new declarationModelSemanticBody(cp,vp,scope);
			functCall= new functionCallModel(cp,vp,scope);
			this.tv=ptv;
	 }
	 public BodyModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope,tableVar ptv)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			decl = new declarationModelSemanticBody(cp,vp,scope);
			this.tv=ptv;
			functCall= new functionCallModel(cp,vp,scope);
	 }
	 public boolean getIsSomeThingOpen()
	 {
		 return isSomethingOpen; 
	 }
	 public void setIsSomeThingOpen(boolean tf)
	 {
		 isSomethingOpen=tf; 
	 }
	 public boolean body(ArrayList<String> cp,int c)
	 {

		 if(m_mst(cp,c))
		 {
			 c=count_int;
			 count=c;
			 //System.out.println(c+"body Passed: "+cp.get(c));
			 return true;
		 }
		 return false;
	 }
	 boolean m_mst(ArrayList<String> cp,int c)
	 {
		 if(cp.get(c).equals("datatype"))
		 {
			 //Declaration can occur
			int temp=c;
			 if(decl.decl(cp, c))
			 {
				 c=decl.count;
				 System.out.println("Declaration Passed at Line number: "+temp);
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
			 }
			// System.out.println("Declaration Failed at Line number: "+temp);
			 if(failedCount<c && c< decl.failedCount)
		   			failedCount=decl.failedCount;
			 else if(failedCount<c)
		   			failedCount=c;
		 }
		 else if(cp.get(c).equals("ID"))
		 {
			 //Declaration can occur
			int temp=c;
			int check=0;
			 if(decl.decl(cp, c))
			 {
				 c=decl.count;
				 System.out.println("Declaration Passed at Line number: "+temp);
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 check++;
			 }
			else if(functCall.functionCall(cp,c))
			 {
				 c=functCall.count;
				 System.out.println("Function Calling Passed at Line number: "+temp);
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
			   			failedCount=c;
				 check++;
			 }
			 else if(exp.exp(cp, c))
			 {
				 
				 c=exp.count;
				 System.out.println("Expression Passed at Line number: "+temp);
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				 {
					
					 count_int=c;
					 return true;
				 }
				 else 
				 {
					 
					 if(m_mst(cp,c))
					 {
						 
						 c=count_int;
						 return true;
					 }
					 if(failedCount<c)
				   			failedCount=c;
				 }
				 
				check++; 
			 }
			 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
			 {
				 
				 count_int=c;
				 return true;
			 }
			 if(exp.FailedCounter>decl.failedCount && exp.FailedCounter> functCall.failedCount && exp.FailedCounter>c && exp.FailedCounter> failedCount)
			 {
				 failedCount=exp.FailedCounter;
				// System.out.println("Invalid expression: "+(failedCount+1));
			 }
			 else if(exp.FailedCounter<decl.failedCount && decl.failedCount> functCall.failedCount && c<decl.failedCount && failedCount< decl.failedCount)
			 {
				 failedCount=decl.failedCount;
				 //System.out.println("Invalid Declaration at line number: "+(failedCount+1));
			 }
			 else if(exp.FailedCounter< functCall.failedCount && decl.failedCount< functCall.failedCount && c<functCall.failedCount && failedCount< functCall.failedCount)
			 {
				 failedCount= functCall.failedCount;
				// System.out.println("Invalid FunctionCalling at line number: "+(failedCount+1));
			 }
			 else if(failedCount<c)
			 {
				 failedCount=c;
			 }
				 
		 }
		 else if(cp.get(c).equals("si")) //if-else
		 {
			int temp=c;
			 IfElseModel ifelse;
			 ifelse = new IfElseModel(cp,vp,scope);
			 int check=0;
			 if(ifelse.ifElse(cp,c))
			 {
				 c=ifelse.count;
				 check=1;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
					 failedCount=c;
				
			 }
			
			 if(failedCount<c && c> ifelse.failedCount)
		   			failedCount=c;
			 else if(failedCount<ifelse.failedCount && c< ifelse.failedCount)
			 {
				 failedCount=ifelse.failedCount;
				 isSomethingOpen=true;
			 }
		 }
		 else if(cp.get(c).equals("check")) //switch
		 {
			 int temp=c;
			 boolean check=false;
			 SwitchModel switch1 =new SwitchModel(cp, vp,scope);
			 if(switch1.Switch(cp,c))
			 {
				 c=switch1.count;
				 System.out.println("Switch Passed at Line number: "+temp);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 if(failedCount<c)
					 failedCount=c;
			 }
		
			 if(failedCount<c && c> switch1.failedCount)
		   			failedCount=c;
			 else if(failedCount<switch1.failedCount && c< switch1.failedCount)
			 {
					failedCount=switch1.failedCount;
					 isSomethingOpen=true;
			 }
		 }
		 else if(cp.get(c).equals("for loop")) //for Loop
		 {
			 int temp=c;
			 boolean check=false;
			 
			 ForModel to = new ForModel(cp,vp,scope,tv);
			 if(to.For(cp,c))
			 {
				 c=to.count;
				 System.out.println("For Loop Passed at Line number: "+temp);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
			 }
			 if(failedCount<c && c> to.failedCount)
		   			failedCount=c;
			 else if(failedCount<to.failedCount && c< to.failedCount)
			 {
				 failedCount=to.failedCount;
				 isSomethingOpen=true;
		   	}
		 }
		 else if(cp.get(c).equals("while loop")) //while Loop
		 {
			 boolean check=false;
			 int temp=c;
			 WhileModel w=new WhileModel(cp,vp,scope);
			 if(w.test(cp,c))
			 {
				 c=w.count;
				 System.out.println("While Passed at Line number: "+temp);
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
			 }
			 if(failedCount<c && c> w.failedCount)
		   			failedCount=c;
			 else if(failedCount<w.failedCount && c< w.failedCount)
			 {
				 failedCount=w.failedCount;
				 isSomethingOpen=true;
			 }
		 }
		 else if(cp.get(c).equals("break")) //break
		 {
			 boolean check=false;
			 int temp=c;
			 c++;
			 if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 System.out.println("Break Passed at Line number: "+temp);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
			 }
			 if(!check)
			 {
				 //System.out.println("Terminator missing at Line number: "+temp);
				 if(failedCount<temp)
					 failedCount=temp;
			 }
		 }
		 else if(cp.get(c).equals("continue")) //continue
		 {
			 boolean check=false;
			 int temp=c;
			 c++;
			 if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 System.out.println("Continue Passed at Line number: "+temp);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
				 
			 }
			 if(!check)
			 {
				 //System.out.println("Terminator missing at Line number: "+temp);
				 if(failedCount<temp)
					 failedCount=temp;
			 }
		 }
		 else if(cp.get(c).equals("return")) //continue
		 {
			 boolean check=false;
			 int temp=c;
			 c++;
			 if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 System.out.println("Return Passed at Line number: "+temp);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
			 }
			 else if(exp.exp(cp, c))
			 {
				 
				 c=exp.count;
				 
				 if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 System.out.println("Expression Passed at Line number: "+temp);
					 check=true;
					 if(m_mst(cp,c))
					 {
						 c=count_int;
						 return true;
					 }
					 else if(failedCount<c)
						 failedCount=c;
				 }
			 }
			 if(!check)
			 {
				// System.out.println("Terminator missing at Line number: "+temp);
				 if(failedCount<temp)
					 failedCount=temp;
			 }
		 }
		 else if(exp.exp(cp, c)) //Exp
		 {
			 boolean check=false;
			 c=exp.count;
			 
			 if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 System.out.println("Expression Passed after at Line number: "+c);
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
				 
			 }
			 else if(cp.get(c-1).equals("Terminator"))
			 {
				 System.out.println("Expression Passed after at Line number: "+(c-1));
				 check=true;
				 if(m_mst(cp,c))
				 {
					 c=count_int;
					 return true;
				 }
				 else if(failedCount<c)
					 failedCount=c;
			 }
			 if(!check)
			 {
				 System.out.println("Terminator missing at Line number: "+c);
				 if(failedCount<c)
					 failedCount=c;
			 }
		 }
		 else if(words.isParentFollow(cp.get(c), follow_of_Parent))
		 {
			 
			 count_int=c;
			 return true;
		 } 
		
		 if(failedCount<c)
			 failedCount=c;
		 if(exp.FailedCounter>c)
		 {
			// System.out.println("invalid exp at Line number: "+exp.FailedCounter);
			 failedCount=exp.FailedCounter;
		 }
		 else
		 {
			// System.out.println(failedCount+"Missing at Line number: "+c);
			 if(failedCount<c)
				 failedCount=c;
		 }
		 
		 return false;
	 }
	 

		//DECLARATIONSemanticBody START

		class declarationModelSemanticBody 
		{
			KeyWords words = new KeyWords();
			public int count=0,count_int=0,failedCount=0;
			 ArrayList<String> cp = new ArrayList();
				 ArrayList<String> vp = new ArrayList();
				 ArrayList<String> varTable = new ArrayList();
			String follow_of_Parent;
			String follow_for_exp[]={"Terminator","]"};
			ExpModel exp ;
			int start=0,end,scope;
			String tempDT="";
			declarationModelSemanticBody(){}
		 public declarationModelSemanticBody(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
		 }
		 public declarationModelSemanticBody(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
		 {
				this.cp = cp;;
				this.vp = vp;
				follow_of_Parent=Follow;
				
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
		 }
		 private boolean LookupVar(String name)
		 {
			 for(int i=0; i<tv.isVarArrayTable.size();i++)
			 {
				 if(name.contentEquals(tv.varTable.get(i)))
				 {
					 System.out.println("Class_"+tv.varTable.get(i));
					 return true;
			 
				 }
			 }
			// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
			 return false;
		 }
		 private boolean lookUpClass(String name)
	 	 {
	 		if(Syntaxtification.ct.Cname.size()>0 )
	 		{
	 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
	 			if(Syntaxtification.ct.Cname.get(i).equals(name))
		 			 return true;
	 			
		 		 
		 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
		 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
		 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
		 		 			 return true;
		 		 
	 		}
	 		System.out.println(name);
	 		return false;
	 	 }
		 private boolean field(ArrayList<String> classPart,int count)
		 {
			 if(classPart.get(count).equals("datatype"))
			 {
				 
				 count++;
				
				if(classPart.get(count).equals("ID"))
				{
					if(LookupVar(vp.get(count)))
					{
						System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
						return false;
					}
					else
					{
						tv.varTable.add(vp.get(count)); //Adding name
						tv.varScope.add(scope); //Adding Scope
						tv.varDT.add(vp.get(count-1)); //Adding DataType
						tempDT=vp.get(count-1);
						//Syntaxtification.varTable.add(vp.get(count));
					}
					count++;
					
					if(init(classPart,count))
					{
						count=count_int;
						
						if(list(classPart,count))
						{
							
							count=count_int;
							
							//System.out.println(count+"e"+classPart.get(count));
							if(classPart.get(count).equals("Terminator"))
							{
								count++;
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								
								return true;
							}
								  
						}
						else if(classPart.get(count).equals("Terminator"))
						{
							count++;
							this.count=count;
							count_int=count;
							exp.count_int=0;
							
							return true;
						}
					}
					else if(list(classPart,count))
					{
						count=count_int;
						if(count>=classPart.size())
						{
							if(classPart.get(count-1).equals("Terminator"))
							{
								//this.count=count;
								count_int=count;
								exp.count_int=0;
								return true;
							}
						}
						if(classPart.get(count).equals("Terminator"))
						{
							
							count++;
							
							count_int=count;
							exp.count_int=0;
							//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
							return true;
						}
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						//this.count=count;
						count_int=count;
						exp.count_int=0;
						return true;
					}
				}
			 }
			 return false;
		 }
		 public boolean decl(ArrayList<String> classPart,int count)
		 {
			
		
			 if(field(classPart,count))
			 {
				 	count=count_int;
				 	this.count=count;
					count_int=0;
					exp.count_int=0;	
					return true;
			 }
			 else if(Object(classPart, count))
			 {
				 count=count_int;
				 this.count=count;
					count_int=0;
					exp.count_int=0;
					
					return true;
			 }
			 
			 return false;
		 }
		 boolean init(ArrayList<String> classPart,int count)
		 {
			 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
			    if(classPart.get(count).equals("Assignment"))
				{
			    	
					count++;
					tv.isVarArrayTable.add(false);
					if(exp.exp(classPart, count))
					{
						if(!exp.returnType.equals(tempDT))
						{
							System.out.println("Expecting "+tempDT+" at char number "+count);
							return false;
						}
						count_int=exp.count;
						count=count_int;
						count_int=count;
						return true;
					}
				}
				else if(array(classPart,count))
				{
					tv.isVarArrayTable.add(true);
					count=count_int;
					count_int=count;
					return true;
				}
				else if(classPart.get(count).equals(","))
				{
					tv.isVarArrayTable.add(false);
					count++;
					count_int=count;
					return true;
				}
			    tv.isVarArrayTable.add(false);
					return false;
		 }
		 boolean list(ArrayList<String> classPart,int count)
		 {
			 
			 //System.out.println(count+"e"+classPart.get(count));
			 if(classPart.get(count).equals(","))
			 {
				 count++;
				 if(classPart.get(count).equals("ID"))
					{
						if(LookupVar(vp.get(count)))
						{
							System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
							return false;
						}
						else
						{
							
							tv.varTable.add(vp.get(count)); //Adding name
							tv.varDT.add(tempDT); //Adding DataType
							tv.varScope.add(scope); //Adding Scope
						}
						count++;
						if(init(classPart,count))
						{
							count=count_int;
							if(list(classPart,count))
							{
								count=count_int;
								count_int=count;
								return true;
							}
							
						}
						else if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
					}
			 }
			 else if(classPart.get(count).equals("Terminator"))
			 {
				// count++;
				 count_int=count;
					return true;
			 }
			 return false;
		 }
		 
		 //Other models
		 boolean array(ArrayList<String> classPart,int count)
		 {
			 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
			 if(classPart.get(count).equals("Assign Op"))
			 {
				count++;
				//Syntaxtification.tv.isVarArrayTable.add(true);
				if(exp.exp(classPart, count))
				{
					if(!exp.returnType.equals("dig"))
					{
						System.out.println("Expecting integer value at char number: "+count);
						return false;
					}
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			 }
			 else if(classPart.get(count).equals("Assignment"))
			 {
				 count++;
				 if(classPart.get(count).equals("["))
				 {
					 count++;
					 if(words.isConst(classPart.get(count)))
					 {
						 if(!words.getConst(classPart.get(count)).equals(tempDT))
						 {
								System.out.println("Expecting "+tempDT+" value at char number: "+count);
								return false;
							}
						 count++;
						 if(clist1(classPart,count))
						 {
							 count=count_int;
							 return true;
						 }
					 }
					 else if(classPart.get(count).equals("]"))
					 {
						 count=count_int;
							count_int=count;
						 return true;
					 }
				 }
			 }
			 return false;
		 }
		 boolean clist1(ArrayList<String> cp,int c)
		 {
		 	if(c>=cp.size()){ c=cp.size()-1; return true;}
		 	 if(cp.get(c).equals(","))			//Call to clist1
		 	 {
		 		 c++;
		 		 if(words.isConst(cp.get(c)))
		 		 {
		 			if(!words.getConst(cp.get(c)).equals(tempDT))
					 {
							System.out.println("Expecting "+tempDT+" value at char number: "+c);
							return false;
						}
		 			 c++;
		 			 if(clist1(cp,c))
		 			 {
		 				 c=count_int;
						 count_int=c;
						 return true;
					 
		 			 }
		 			 
		 		 }
		 	 }
		 	 else if(cp.get(c).equals("]"))
		 	 {
		 		c++;
				 count_int=c;
				 return true;
		 	 }
		 	return false;
		 } 
		 boolean Object(ArrayList<String> cp,int c)
		 {
			 
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 
			 if(cp.get(c).equals("ID"))
			 {
				 if(!lookUpClass(vp.get(c)) && !vp.get(c+1).equals("("))
					{
						System.out.println("No class found Error at char number:"+c+",,"+vp.get(c));
						return false;
					}
					
				 
				 
				  c++;
				 if(cp.get(c).equals("ID"))
				 {
					
					 
					  	if(LookupVar(vp.get(c)))
						{
							System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
							return false;
						}
						else
						{
							tv.varTable.add(vp.get(c)); //Adding name
							tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
							tv.varScope.add(scope); //Adding Scope
							tempDT=("class_"+vp.get(c-1));
						}
				  
					  	c++;
					
					 if(obj1(cp,c))
					 {
						 
						 c=count_int;
						 
						 if(Ob(cp, c))
						 {
							 
							 if(c>=cp.size())
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
							 }
							 else
							 {
								 if(cp.get(c-1).equals("Terminator"))
								 {
									 count_int=c;
									 return true;
								 }
								 else if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
				 }
			 }
			 return false;
		 }
		 boolean LookUpClassParent(String child,String Parent)
		 {
			 int indexOfChild=0,i=0;
			 indexOfChild=indexOfChild(child);
			i= indexOfParent(Parent,indexOfChild);
			 if(i!=-1)
			 {
				 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
				 return true;
			 }
				
			
			 
			 return false;
		 }
		 int indexOfParent(String Parent,int indexOfChild)
		 {
			 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
			 {
				
				 return indexOfChild;
			 }
			 else
			 {
				 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
					 return -1;
				 else
				 {
					 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
					
				 }
							 
			 }
			 
		 }
		 int indexOfChild(String Child)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
			 {
				 if(Syntaxtification.ct.Cname.get(i).equals(Child))
				 {
					 return i;
				 }
			 }
			 return -1;
		 }
		 boolean obj1(ArrayList<String> cp, int c)
		 {
			 
			 if(cp.get(c).equals("Assignment"))
			 {
				 Syntaxtification.tv.isVarArrayTable.add(false);
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 if(cp.get(c).equals("ID"))
					 {
						 
						 if(vp.get(c).equals(vp.get(c-4)))
							{
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
							}
						 else if(!vp.get(c).equals(vp.get(c-4)))
							{
							  
							 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
									
									
									
								}
							 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
									
										System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
										return false;
									
									
									
								}
							  else
							  {
								System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
								return false;
							  }
							}
							
					 }
					
				 }
				
			 }
			 else if(cp.get(c).equals("["))
			 {
				 
				 c++;
			
				 Syntaxtification.tv.isVarArrayTable.add(true);
				 if(words.isConst(cp.get(c)))
				 {
					 if(!words.getConst(cp.get(c)).equals("dig"))
					 {
						 System.out.println("Expecting integer value at char number :"+c);
						 return false;
					 }
					 c++;
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						 
						if(init_ob(cp,c))
						{
							c=count_int;
						
							 return true;
						}
					 }
				 }
				 else if(exp.exp(cp, c))
				 {
					 if(!exp.returnType.equals("dig"))
					 {
						 System.out.println("Expecting integer value at char number :"+c);
						 return false;
					 }
					 c=exp.count;
					
					 count_int=c;
					 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
					 else
					 {
						 
						 if(cp.get(c).equals("]"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
						 else  if(cp.get(c).equals("Terminator"))
						 {
							 c++;
							 //Here init_ob
							if(init_ob(cp,c))
							{
								c=count_int;
								 return true;
							}
						 }
					 }
					
				 }
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				 tv.isVarArrayTable.add(false);
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 count_int=c;
				 return true;
			 }
			 
			 return false;
		 }
		 boolean init_ob(ArrayList<String> cp,int c)
		 {
			if(c>=cp.size()) return true; 
			
			 if(cp.get(c).equals("Assignment"))
			 {
				 tv.isVarArrayTable.add(false);
				 c++;
				 if(cp.get(c).equals("new"))
				 {
					 c++;
					 
					 if(cp.get(c).equals("ID"))
					 {
						 if(vp.get(c).equals(vp.get(c-4)))
							{
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
							}
						 else if(!vp.get(c).equals(vp.get(c-4)))
							{
							  
							 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
								 if(exp.exp(cp, c))
								 {
									 c=exp.count;
									 count_int=c;
									return true;
								 }
									
									
									
								}
							 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
								{
									
									
										System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
										return false;
									
									
									
								}
							  else
							  {
								System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
								return false;
							  }
							}
					 }
			/*		 if(cp.get(c).equals("ID"))
					 {
						 c++;
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 count_int=c;
							 return true;
						 }
						 else if(cp.get(c).equals("["))
						 {
							 c++;
							 if(words.isConst(cp.get(c)))
							 {
								 c++;
								 if(cp.get(c).equals("]"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
					 }
			*/		 
				 }
				 
			 }
			 else if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				
				 count_int=c;
				 return true;
			 }
			 else if(cp.get(c).equals(","))
			 {
				 c++;
				 count_int=c;
				 return true;
			 }
			 return false;
		 }
		 boolean Ob(ArrayList<String> cp,int c)
		 {
			 if(c>=cp.size())
			 {
				 return true;
			 }
			 else
			 {
				 if(cp.get(c).equals(","))
				 {
					 c++;
					 if(cp.get(c).equals("ID")) //obj1
					 {
						
							{
								tv.varTable.add(vp.get(c)); //Adding name
								tv.varDT.add(tempDT); //Adding DataType
								tv.varScope.add(scope); //Adding Scope
							}
					  
						 c++;
						 if(obj1(cp,c))
						 {
							 c=count_int;
							 if(Ob(cp,c))
							 {
								 c=count_int;
								 if(cp.get(c).equals("Terminator"))
								 {
									 c++;
									 count_int=c;
									 return true;
								 }
							 }
						 }
							
					 }
				 }
				 else  if(cp.get(c).equals("Terminator"))
				 {
					 c++;
					 count_int=c;
				
					 
					 return true;
				 }
				 else  if(cp.get(c-1).equals("Terminator"))
				 {
					 
					 count_int=c;
				
					 
					 return true;
				 }
			 }
			 
				 return false;
		 }
		}

		//DECLARATIONBody SEMANTIC END


	}
//Simple Body Semantic End
	
	

	//Constructor Semantic Start
	
	class ConstructorModel {


		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0,scope=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		String follow_of_Parent[];
		String follow_for_exp[]={"["};
		private	String follow_for_body[]={"]"};
		ExpModel exp ;
		declarationModel decl;
		private	BodyModel body;
		private  ArrayList<String> params = new ArrayList();	
		 private ArrayList<String> getParams()
		 {
			 return params;
		 }
		 private void SetParams(String param)
		 {
			 params.add(param);
		 }
		tableVar tv = new tableVar();
		ConstructorModel(){}
		 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				this.scope=scope;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		 }
		 public ConstructorModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[],int scope)
		 {
				this.cp = cp;
				this.vp = vp;
				follow_of_Parent=Follow;
				exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
				decl = new declarationModel(cp,vp,scope);
				body=new BodyModel(cp,vp,follow_for_body,scope,tv);
		 }
		 public boolean construct_def(ArrayList<String> cp,int c)
		 {
			 
			 if(cp.get(c).equals("acces modifier"))
			 {
				
				 c++;
				 
				 if(cp.get(c).equals("type modifier"))
				 {
					 c++;
					
						 if(cp.get(c).equals("ID"))
						 {
							 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
							 {
								 tFunction.isPrivate.add(true);
								 tFunction.isStable.add(true);
								 tFunction.nameFunction.add(vp.get(c));
								 tFunction.varScope.add(scope);
								 tFunction.ReturnType.add("constructor");
							 }
							 else
							 {
								 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
							 }
							 c++;
							 if(cp.get(c).equals("(")) //Confirmation of params - (
							 {
								 
								 if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 								tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
							 }
						 }
					 
				 }//if stable not present.
				 else if(cp.get(c).equals("ID"))
					 {
					 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
					 {
						 tFunction.isPrivate.add(true);
						 tFunction.isStable.add(false);
						 tFunction.nameFunction.add(vp.get(c));
						 tFunction.varScope.add(scope);
						 tFunction.ReturnType.add("constructor");
					 }
					 else
					 {
						 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
					 }
						 c++;
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 if(params(cp, c))
	 						 {
	 							 
	 							 c=count_int;
	 							 if(cp.get(c).equals("["))
	 							 {
	 								 c++;
	 								 //System.out.println(c+"HerEFF:"+cp.get(c));
	 								 if(body.body(cp,c))
	 								 {
	 									
	 									c=body.count;
	 									tFunction.variablelist.add(body.tv);
	 									if(cp.get(c).equals("]"))
	 									{
	 										c++;
	 										count=count_int=c;
	 										return true; //Successful parsed.
	 										
	 									}
	 								 }
	 								 if(failedCount<c && body.failedCount<c)
	 							   			failedCount=c;
	 								   	else if(body.failedCount>c)
	 								   			failedCount=body.failedCount;
	 							 }
	 						 }
						 }
					 }
				 
			 } // if access modifier not present
			 else if(cp.get(c).equals("type modifier"))
			 {
				 c++;

					 if(cp.get(c).equals("ID"))
					 {
						 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
						 {
							 tFunction.isPrivate.add(false);
							 tFunction.isStable.add(true);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add("constructor");
						 }
						 else
						 {
							 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
						 }
						 c++;
						 if(cp.get(c).equals("(")) //Confirmation of params - (
						 {
							 if(params(cp, c))
	 						 {
	 							 
	 							 c=count_int;
	 							 if(cp.get(c).equals("["))
	 							 {
	 								 c++;
	 								 //System.out.println(c+"HerEFF:"+cp.get(c));
	 								 if(body.body(cp,c))
	 								 {
	 									
	 									c=body.count;
	 									tFunction.variablelist.add(body.tv);
	 									if(cp.get(c).equals("]"))
	 									{
	 										c++;
	 										count=count_int=c;
	 										return true; //Successful parsed.
	 										
	 									}
	 								 }
	 								 if(failedCount<c && body.failedCount<c)
	 							   			failedCount=c;
	 								   	else if(body.failedCount>c)
	 								   			failedCount=body.failedCount;
	 							 }
	 						 }
						 }
					 }
				 
			 }//if stable+access modifier not present.

			 else if(cp.get(c).equals("ID"))
				 {
				 if(Syntaxtification.ct.Cname.get(ClassIndex).equals(vp.get(c)))
				 {
					 tFunction.isPrivate.add(false);
					 tFunction.isStable.add(false);
					 tFunction.nameFunction.add(vp.get(c));
					 tFunction.varScope.add(scope);
					 tFunction.ReturnType.add("constructor");
				 }
				 else
				 {
					 System.out.println("Invalidate Class Name Error "+ vp.get(c)+ " at char number:"+(c+1));
				 }
					 c++;
					 if(cp.get(c).equals("(")) //Confirmation of params - (
					 {
						 if(params(cp, c))
 						 {
 							 
 							 c=count_int;
 							 if(cp.get(c).equals("["))
 							 {
 								 c++;
 								 //System.out.println(c+"HerEFF:"+cp.get(c));
 								 if(body.body(cp,c))
 								 {
 									
 									c=body.count;
 									tFunction.variablelist.add(body.tv);
 									if(cp.get(c).equals("]"))
 									{
 										c++;
 										count=count_int=c;
 										return true; //Successful parsed.
 										
 									}
 								 }
 								 if(failedCount<c && body.failedCount<c)
 							   			failedCount=c;
 								   	else if(body.failedCount>c)
 								   			failedCount=body.failedCount;
 							 }
 						 }
					 }
				 }
			 


			 return false;
		 }
		 private boolean lookUpVar(String name)
	 	 {
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
	 		 return false;
	 	 }
	 	 private boolean lookUpClass(String name)
	 	 {
	 		if(Syntaxtification.ct.Cname.size()>0)
	 		{
	 			
	 			if(Syntaxtification.ct.Cname.get(ClassIndex).equals(name))
		 			 return true;
	 			 else
		 		 {
		 			 if(Syntaxtification.ct.innerClassList.size()>0)
		 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
		 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
		 		 			 return true;
		 		 }
	 		}
	 		
	 		return false;
	 	 }
	 	 boolean fields(ArrayList<String> cp,int c)
	 	 {
	 		 if(cp.get(c).equals("datatype"))
	 		 {
	 			 c++;
	 			
	 			if(cp.get(c).equals("ID"))
	 			{
	 				if(!lookUpVar(vp.get(c)))
					 {
						 
						 tv.varTable.add(vp.get(c));
						 tv.varScope.add(scope);
						 tv.varDT.add(vp.get(c-1));
						 SetParams(vp.get(c-1));
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
					 }
	 				c++;
	 				
	 				 if(list(cp,c))
	 				{
	 					c=count_int;
	 					
	 					if(c>=cp.size())
	 					{
	 						if(cp.get(c-1).equals(")"))
	 						{
	 							
	 							count_int=c;
	 							
	 							return true;
	 						}
	 					}
	 					else if(cp.get(c).equals(")"))
	 					{
	 						c++;
	 						
	 						count_int=c;

	 						return true;
	 					}
	 					else if(cp.get(c-1).equals(")"))
	 					{

	 						count_int=c;

	 						return true;
	 					}
	 				}
	 				
	 			}
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		 
	 		 return false;
	 	 }
	 	 boolean params(ArrayList<String> cp,int c)
	 	 {
	 		 if(fields(cp,c))
	 		 {
	 			 c=count_int;
	 			 tFunction.paramList.add(getParams());
	 			 return true;
	 		 }
	 		 else if(Object(cp, c))
	 		 {
	 			 c=count_int;
	 				count_int=c;
	 				return true;
	 		 }
	 		 else if(cp.get(c).equals(")"))
	 		 {
	 			 count_int=++c;
	 			 tFunction.paramList.add(getParams());
	 			 return true;
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   	
	 		 return false;
	 	 }
	 	 boolean list(ArrayList<String> classPart,int count)
	 	 {
	 		 
	 		 if(classPart.get(count).equals(","))
	 		 {
	 			 count++;
	 			 if(cp.get(count).equals("datatype"))
	 			 {
	 				 count++;
	 				 if(classPart.get(count).equals("ID"))
	 					{
	 					if(!lookUpVar(vp.get(count)))
						 {
							 
							 tv.varTable.add(vp.get(count));
							 tv.varScope.add(scope);
							 tv.varDT.add(vp.get(count-1));
							 SetParams(vp.get(count-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of variable "+ vp.get(count)+ " at char number:"+(count+1));
						 }
	 						count++;
	 						
	 						if(list(classPart,count))
	 						{
	 							count=count_int;
	 							count_int=count;
	 							//System.out.println(count+":rr:"+cp.get(count));
	 							return true;
	 						}
	 						/*else
	 						{
	 							
	 							count--;
	 							//System.out.println(count+":rr:"+cp.get(count));
	 							if(Object(classPart, count))
	 							 {
	 								 count=count_int;
	 									count_int=count;
	 									
	 									return true;
	 							 }
	 						}*/
	 					}
	 			 }
	 			 else if(Object(classPart, count))
	 			 {
	 				 count=count_int;
	 					count_int=count;
	 					
	 					return true;
	 			 }
	 			 
	 		 }
	 		 else if(classPart.get(count).equals(")"))
	 		 {
	 			 
	 			 count_int=count;
	 				return true;
	 		 }
	 		 if(failedCount<count)
	 	   			failedCount=count;
	 		   
	 		 return false;
	 	 }
	 	 boolean Object(ArrayList<String> cp,int c)
	 	 {
	 		 if(c>=cp.size())
	 		 {
	 			 return true;
	 		 }
	 		
	 		 if(cp.get(c).equals("ID"))
	 		 {
	 			 c++;
	 			 if(cp.get(c).equals("ID"))
	 			 {
	 				 	if(lookUpClass(vp.get(c-1)))
	 				 	{
	 				 		if(!lookUpVar(vp.get(c)))
	 						 {
	 							 
	 							 tv.varTable.add(vp.get(c));
	 							 tv.varScope.add(scope);
	 							 tv.varDT.add("class_"+vp.get(c-1));
	 							 SetParams(vp.get(c-1));
	 						 }
	 						 else
	 						 {
	 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
	 						 }
	 				 	}
	 				 	 else
 						 {
 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
 						 }
	 				 c++;
	 				
	 					 if(Ob(cp, c))
	 					 {
	 						c=count_int;
	 						 if(c>=cp.size())
	 						 {
	 							 if(cp.get(c-1).equals(")"))
	 							 {
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 
	 						 }
	 						 else
	 						 {
	 							 
	 							 if(cp.get(c-1).equals(")"))
	 							 {
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 else if(cp.get(c).equals(")"))
	 							 {
	 								 
	 								 count_int=c=c+1;
	 								 
	 								 return true;
	 							 }
	 						 }
	 					 }
	 				 
	 			 }
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   
	 		 return false;
	 	 }
	 	 boolean Ob(ArrayList<String> cp,int c)
	 	 {
	 		 if(c>=cp.size())
	 		 {
	 			 return true;
	 		 }
	 		 else
	 		 {
	 			 
	 			 if(cp.get(c).equals(","))
	 			 {
	 				 c++;
	 				
	 				 if(cp.get(c).equals("ID")) //obj1
	 				 {
	 					 c++;
	 					 
	 					if(cp.get(c).equals("ID"))
	 					{
	 						if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
	 						 {
	 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
	 						 }
	 						c++;
	 					
	 						 if(Ob(cp,c))
	 						 {
	 							 c=count_int;
	 							 if(cp.get(c).equals(")"))
	 							 {
	 								 c++;
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 else
	 								 if(cp.get(c-1).equals(")"))
	 								 {
	 									 
	 									 count_int=c;
	 									 return true;
	 								 }
	 									
	 						
	 							 
	 						 }
	 					 
	 						
	 					}
	 				 }
	 				 else if(cp.get(c).equals("datatype"))
	 				 {
	 				
	 					 if(fields(cp, c))
	 					 {
	 						 c=count_int;
	 							count_int=c;
	 							return true;
	 					 }
	 				 }
	 			 }
	 			 else  if(cp.get(c).equals(")"))
	 			 {
	 				 c++;
	 				 count_int=c;
	 				 
	 				 return true;
	 			 }
	 		
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   	
	 			 return false;
	 	 }



	}
	//Constructor Semantic End

//Function Semantic Start
	 class FunctionDefModel 
	 {

	 	KeyWords words = new KeyWords();
	 	public int count=0,count_int=0,failedCount=0,scope;
	 	 ArrayList<String> cp = new ArrayList();
	 		 ArrayList<String> vp = new ArrayList();
	 	String follow_of_Parent[];
	 	String follow_for_exp[]={"["};
	 	private	String follow_for_body[]={"]"};
	 	ExpModel exp ;
	 	declarationModel decl;
	 	private	BodyModel body;
	 	tableVar tv = new tableVar();
	 	FunctionDefModel(){}
	 	 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
	 			decl = new declarationModel(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
	 	 }
	 	 public FunctionDefModel(ArrayList<String> cp, ArrayList<String> vp,String Follow[])
	 	 {
	 			this.cp = cp;
	 			this.vp = vp;
	 			follow_of_Parent=Follow;
	 			this.scope=scope;
	 			exp = new ExpModel(cp,vp,follow_for_exp,false,scope);
	 			decl = new declarationModel(cp,vp,scope);
	 			body=new BodyModel(cp,vp,follow_for_body,scope,tv);
	 	 }
	 	private  ArrayList<String> params = new ArrayList();	
		 private ArrayList<String> getParams()
		 {
			 return params;
		 }
		 private void SetParams(String param)
		 {
			 params.add(param);
		 }
	 	 public boolean func_def(ArrayList<String> cp,int c)
	 	 {
	 		 
	 		 if(cp.get(c).equals("acces modifier"))
	 		 {
	 			
	 			 c++;
	 			 
	 			 if(cp.get(c).equals("type modifier"))
	 			 {
	 				 c++;
	 				 //System.out.println(""+cp.get(c));
	 				 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
	 				 {
	 					 c++;
	 					 if(cp.get(c).equals("ID"))
	 					 {
	 						if(!lookUpFunction(vp.get(c)))
							 {
								 tFunction.isPrivate.add(true);
								 tFunction.isStable.add(true);
								 tFunction.nameFunction.add(vp.get(c));
								 tFunction.varScope.add(scope);
								 tFunction.ReturnType.add(vp.get(c-1));
							 }
							 else
							 {
								 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
							 }
	 						 c++;
	 						 if(cp.get(c).equals("(")) //Confirmation of params - (
	 						 {
	 							 
	 							if(params(cp, c))
		 						 {
		 							 
		 							 c=count_int;
		 							 if(cp.get(c).equals("["))
		 							 {
		 								 c++;
		 								 //System.out.println(c+"HerEFF:"+cp.get(c));
		 								 if(body.body(cp,c))
		 								 {
		 									
		 									c=body.count;
		 									tFunction.variablelist.add(body.tv);
		 									if(cp.get(c).equals("]"))
		 									{
		 										c++;
		 										count=count_int=c;
		 										return true; //Successful parsed.
		 										
		 									}
		 								 }
		 								 if(failedCount<c && body.failedCount<c)
		 							   			failedCount=c;
		 								   	else if(body.failedCount>c)
		 								   			failedCount=body.failedCount;
		 							 }
		 						 }
	 						 }
	 					 }
	 				 }
	 			 }//if stable not present.
	 			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
	 			 {
	 				 c++;
	 				 
	 				 if(cp.get(c).equals("ID"))
	 				 {
	 					if(!lookUpFunction(vp.get(c)))
						 {
							 tFunction.isPrivate.add(true);
							 tFunction.isStable.add(false);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add(vp.get(c-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
						 }
	 					 c++;
	 					 
	 					 if(cp.get(c).equals("(")) //Confirmation of params - (
	 					 {
	 						 c++;
	 						 
	 						 if(params(cp, c))
	 						 {
	 							 
	 							 c=count_int;
	 							 if(cp.get(c).equals("["))
	 							 {
	 								 c++;
	 								 //System.out.println(c+"HerEFF:"+cp.get(c));
	 								 if(body.body(cp,c))
	 								 {
	 									
	 									c=body.count;
	 									tFunction.variablelist.add(body.tv);
	 									if(cp.get(c).equals("]"))
	 									{
	 										c++;
	 										count=count_int=c;
	 										return true; //Successful parsed.
	 										
	 									}
	 								 }
	 								 if(failedCount<c && body.failedCount<c)
	 							   			failedCount=c;
	 								   	else if(body.failedCount>c)
	 								   			failedCount=body.failedCount;
	 							 }
	 						 }
	 					 }
	 				 }
	 				 if(failedCount<c)
	 			   			failedCount=c;
	 				   
	 			 }
	 			 if(failedCount<c)
	 		   			failedCount=c;
	 			   	
	 		 } // if access modifier not present
	 		 else if(cp.get(c).equals("type modifier"))
	 		 {
	 			 
	 			 c++;
	 			 if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
	 			 {
	 				 c++;
	 				 
	 				 if(cp.get(c).equals("ID"))
	 				 {
	 					if(!lookUpFunction(vp.get(c)))
						 {
							 tFunction.isPrivate.add(false);
							 tFunction.isStable.add(true);
							 tFunction.nameFunction.add(vp.get(c));
							 tFunction.varScope.add(scope);
							 tFunction.ReturnType.add(vp.get(c-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
						 }
	 					 c++;
	 					 
	 					 if(cp.get(c).equals("(")) //Confirmation of params - (
	 					 {
	 						 c++;
	 						 
	 						 if(params(cp, c))
	 						 {
	 							 
	 							 c=count_int;
	 							 if(cp.get(c).equals("["))
	 							 {
	 								 c++;
	 								 //System.out.println(c+"HerEFF:"+cp.get(c));
	 								 if(body.body(cp,c))
	 								 {
	 									
	 									c=body.count;
	 									tFunction.variablelist.add(body.tv);
	 									if(cp.get(c).equals("]"))
	 									{
	 										c++;
	 										count=count_int=c;
	 										return true; //Successful parsed.
	 										
	 									}
	 								 }
	 								 if(failedCount<c && body.failedCount<c)
	 							   			failedCount=c;
	 								   	else if(body.failedCount>c)
	 								   			failedCount=body.failedCount;
	 							 }
	 						 }
	 					 }
	 				 }
	 				 if(failedCount<c)
	 			   			failedCount=c;
	 				   
	 			 }
	 			 if(failedCount<c)
	 		   			failedCount=c;
	 			  
	 		 }//if stable+access modifier not present.
	 		 else  if(cp.get(c).equals("void") || cp.get(c).equals("datatype"))
	 		 {
	 			 c++;
	 			 
	 			 if(cp.get(c).equals("ID"))
	 			 {
	 				if(!lookUpFunction(vp.get(c)))
					 {
						 tFunction.isPrivate.add(false);
						 tFunction.isStable.add(false);
						 tFunction.nameFunction.add(vp.get(c));
						 tFunction.varScope.add(scope);
						 tFunction.ReturnType.add(vp.get(c-1));
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of Function "+ vp.get(c)+ " at char number:"+(c+1));
					 }
	 				 c++;
	 				 
	 				 if(cp.get(c).equals("(")) //Confirmation of params - (
	 				 {
	 					 
	 					 c++;
	 					 
	 					 if(params(cp, c))
	 					 {
	 						 
	 						 c=count_int;
	 						 if(cp.get(c).equals("["))
	 						 {
	 							 c++;
	 							 //System.out.println(c+"HerEFF:"+cp.get(c));
	 							 if(body.body(cp,c))
	 							 {
	 								
	 								c=body.count;
	 								tFunction.variablelist.add(body.tv);
	 								if(cp.get(c).equals("]"))
	 								{
	 									c++;
	 									count=count_int=c;
	 									return true; //Successful parsed.
	 									
	 								}
	 							 }
	 							 if(failedCount<c && body.failedCount<c)
	 						   			failedCount=c;
	 							   	else if(body.failedCount>c)
	 							   			failedCount=body.failedCount;
	 						 }
	 					 }
	 				 }
	 			 }
	 			 if(failedCount<c)
	 		   			failedCount=c;
	 			   
	 		 }


	 		 return false;
	 	 }
	 	 private boolean lookUpVar(String name)
	 	 {
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
	 		 return false;
	 	 }
	 	 private boolean lookUpClass(String name)
	 	 {
	 		if(Syntaxtification.ct.Cname.size()>0)
	 		{
	 			
	 			if(Syntaxtification.ct.Cname.get(ClassIndex).equals(name))
		 			 return true;
	 			 else
		 		 {
		 			 if(Syntaxtification.ct.innerClassList.size()>0)
		 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
		 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
		 		 			 return true;
		 		 }
	 		}
	 		
	 		return false;
	 	 }
	 	 boolean fields(ArrayList<String> cp,int c)
	 	 {
	 		 if(cp.get(c).equals("datatype"))
	 		 {
	 			 c++;
	 			
	 			if(cp.get(c).equals("ID"))
	 			{
	 				if(!lookUpVar(vp.get(c)))
					 {
						 
						 tv.varTable.add(vp.get(c));
						 tv.varScope.add(scope);
						 tv.varDT.add(vp.get(c-1));
						 SetParams(vp.get(c-1));
					 }
					 else
					 {
						 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
					 }
	 				c++;
	 				
	 				 if(list(cp,c))
	 				{
	 					c=count_int;
	 					
	 					if(c>=cp.size())
	 					{
	 						if(cp.get(c-1).equals(")"))
	 						{
	 							
	 							count_int=c;
	 							
	 							return true;
	 						}
	 					}
	 					else if(cp.get(c).equals(")"))
	 					{
	 						c++;
	 						
	 						count_int=c;

	 						return true;
	 					}
	 					else if(cp.get(c-1).equals(")"))
	 					{

	 						count_int=c;

	 						return true;
	 					}
	 				}
	 				
	 			}
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		 
	 		 return false;
	 	 }
	 	 boolean params(ArrayList<String> cp,int c)
	 	 {
	 		 if(fields(cp,c))
	 		 {
	 			 c=count_int;
	 			 tFunction.paramList.add(getParams());
	 			 return true;
	 		 }
	 		 else if(Object(cp, c))
	 		 {
	 			 c=count_int;
	 				count_int=c;
	 				return true;
	 		 }
	 		 else if(cp.get(c).equals(")"))
	 		 {
	 			 count_int=++c;
	 			 tFunction.paramList.add(getParams());
	 			 return true;
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   	
	 		 return false;
	 	 }
	 	 boolean list(ArrayList<String> classPart,int count)
	 	 {
	 		 
	 		 if(classPart.get(count).equals(","))
	 		 {
	 			 count++;
	 			 if(cp.get(count).equals("datatype"))
	 			 {
	 				 count++;
	 				 if(classPart.get(count).equals("ID"))
	 					{
	 					if(!lookUpVar(vp.get(count)))
						 {
							 
							 tv.varTable.add(vp.get(count));
							 tv.varScope.add(scope);
							 tv.varDT.add(vp.get(count-1));
							 SetParams(vp.get(count-1));
						 }
						 else
						 {
							 System.out.println("Redeclaration Error of variable "+ vp.get(count)+ " at char number:"+(count+1));
						 }
	 						count++;
	 						
	 						if(list(classPart,count))
	 						{
	 							count=count_int;
	 							count_int=count;
	 							//System.out.println(count+":rr:"+cp.get(count));
	 							return true;
	 						}
	 					
	 					}
	 			 }
	 			 else if(Object(classPart, count))
	 			 {
	 				 count=count_int;
	 					count_int=count;
	 					
	 					return true;
	 			 }
	 			 
	 		 }
	 		 else if(classPart.get(count).equals(")"))
	 		 {
	 			 
	 			 count_int=count;
	 				return true;
	 		 }
	 		 if(failedCount<count)
	 	   			failedCount=count;
	 		   
	 		 return false;
	 	 }
	 	 boolean Object(ArrayList<String> cp,int c)
	 	 {
	 		 if(c>=cp.size())
	 		 {
	 			 return true;
	 		 }
	 		
	 		 if(cp.get(c).equals("ID"))
	 		 {
	 			 c++;
	 			 if(cp.get(c).equals("ID"))
	 			 {
	 				 	if(lookUpClass(vp.get(c-1)))
	 				 	{
	 				 		if(!lookUpVar(vp.get(c)))
	 						 {
	 							 
	 							 tv.varTable.add(vp.get(c));
	 							 tv.varScope.add(scope);
	 							 tv.varDT.add("class_"+vp.get(c-1));
	 							 SetParams(vp.get(c-1));
	 						 }
	 						 else
	 						 {
	 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
	 						 }
	 				 	}
	 				 	 else
 						 {
 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
 						 }
	 				 c++;
	 				
	 					 if(Ob(cp, c))
	 					 {
	 						c=count_int;
	 						 if(c>=cp.size())
	 						 {
	 							 if(cp.get(c-1).equals(")"))
	 							 {
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 
	 						 }
	 						 else
	 						 {
	 							 
	 							 if(cp.get(c-1).equals(")"))
	 							 {
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 else if(cp.get(c).equals(")"))
	 							 {
	 								 
	 								 count_int=c=c+1;
	 								 
	 								 return true;
	 							 }
	 						 }
	 					 }
	 				 
	 			 }
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   
	 		 return false;
	 	 }
	 	 boolean Ob(ArrayList<String> cp,int c)
	 	 {
	 		 
	 		 if(c>=cp.size())
	 		 {
	 			 return true;
	 		 }
	 		 else
	 		 {
	 			 
	 			 if(cp.get(c).equals(","))
	 			 {
	 				 c++;
	 				
	 				 if(cp.get(c).equals("ID")) //obj1
	 				 {
	 					 c++;
	 					 
	 					if(cp.get(c).equals("ID"))
	 					{
	 						if(lookUpClass(vp.get(c-1)))
		 				 	{
		 				 		if(!lookUpVar(vp.get(c)))
		 						 {
		 							 
		 							 tv.varTable.add(vp.get(c));
		 							 tv.varScope.add(scope);
		 							 tv.varDT.add("class_"+vp.get(c-1));
		 							 SetParams(vp.get(c-1));
		 						 }
		 						 else
		 						 {
		 							 System.out.println("Redeclaration Error of variable "+ vp.get(c)+ " at char number:"+(c+1));
		 						 }
		 				 	}
		 				 	 else
	 						 {
	 							 System.out.println("No Class Error "+ vp.get(c-1)+ " at char number:"+(c));
	 						 }
	 						c++;
	 					
	 						 if(Ob(cp,c))
	 						 {
	 							 c=count_int;
	 							 if(cp.get(c).equals(")"))
	 							 {
	 								 c++;
	 								 count_int=c;
	 								 return true;
	 							 }
	 							 else
	 								 if(cp.get(c-1).equals(")"))
	 								 {
	 									 
	 									 count_int=c;
	 									 return true;
	 								 }
	 									
	 						
	 							 
	 						 }
	 					 
	 						
	 					}
	 				 }
	 				 else if(cp.get(c).equals("datatype"))
	 				 {
	 				
	 					 if(fields(cp, c))
	 					 {
	 						 c=count_int;
	 							count_int=c;
	 							return true;
	 					 }
	 				 }
	 			 }
	 			 else  if(cp.get(c).equals(")"))
	 			 {
	 				 c++;
	 				 count_int=c;
	 				 
	 				 return true;
	 			 }
	 		
	 		 }
	 		 if(failedCount<c)
	 	   			failedCount=c;
	 		   	
	 			 return false;
	 	 }


	 	}
	
	
	
	
//Function Semantic End
	 
	 
	 
	 
	 
	 
	 
	 
	 
	///DECLARATION START
	class declarationModel 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
			 ArrayList<String> varTable = new ArrayList();
		String follow_of_Parent;
		String follow_for_exp[]={"Terminator","]"};
		ExpModel exp ;
		int start=0,end;
		declarationModelSemantic dms;

		declarationModel(){}
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			dms= new declarationModelSemantic(cp,vp,scope);
	 }
	 public declarationModel(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			follow_of_Parent=Follow;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
			dms= new declarationModelSemantic(cp,vp,Follow,scope);
	 }
	 private boolean field(ArrayList<String> classPart,int count)
	 {
		 if(classPart.get(count).equals("datatype"))
		 {
			 count++;
			
			if(classPart.get(count).equals("ID"))
			{
				
				count++;
				
				if(init(classPart,count))
				{
					count=count_int;
					
					if(list(classPart,count))
					{
						
						count=count_int;
						
						//System.out.println(count+"e"+classPart.get(count));
						if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
							  
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						this.count=count;
						count_int=count;
						exp.count_int=0;
						
						return true;
					}
				}
				else if(list(classPart,count))
				{
					count=count_int;
					if(count>=classPart.size())
					{
						if(classPart.get(count-1).equals("Terminator"))
						{
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
					if(classPart.get(count).equals("Terminator"))
					{
						
						count++;
						
						count_int=count;
						exp.count_int=0;
						//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
						return true;
					}
				}
				else if(classPart.get(count).equals("Terminator"))
				{
					count++;
					//this.count=count;
					count_int=count;
					exp.count_int=0;
					return true;
				}
			}
		 }
		 return false;
	 }
	 public boolean decl(ArrayList<String> classPart,int count)
	 {
		 
		 start=count;
		 if(field(classPart,count))
		 {
			 	count=count_int;
			 	this.count=count;
				count_int=0;
				exp.count_int=0;
				end=this.count;
				
				if(dms.decl(classPart, start))
				{
					System.out.println("Declaration ki Semantic Sahi hai");
				}
				else
				{
					System.out.println("Declaration ki Semantic Sahi nahi hai");
					//Syntax should return false or not?
				}
				return true;
		 }
		 else if(Object(classPart, count))
		 {
			 count=count_int;
			 this.count=count;
			 //start=count;
				count_int=0;
				exp.count_int=0;
				
				if(dms.decl(classPart, start))
				{
					System.out.println("Declaration ki Semantic Sahi hai");
				}
				else
				{
					System.out.println("Declaration ki Semantic Sahi nahi hai");
					//Syntax should return false or not?
				}
				//System.out.print("Declaration ki Semantic Sahi nahi haieeee");
				return true;
		 }

		 return false;
	 }
	 boolean init(ArrayList<String> classPart,int count)
	 {
		 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
		    if(classPart.get(count).equals("Assignment"))
			{
		    	
				count++;
				
				if(exp.exp(classPart, count))
				{
					//System.out.println(exp.count+","+classPart.get(count)+"_init_"+count);
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			}
			else if(array(classPart,count))
			{
				count=count_int;
				count_int=count;
				return true;
			}
			else if(classPart.get(count).equals(","))
			{
				count++;
				count_int=count;
				return true;
			}
			
				return false;
	 }
	 boolean list(ArrayList<String> classPart,int count)
	 {
		 
		 //System.out.println(count+"e"+classPart.get(count));
		 if(classPart.get(count).equals(","))
		 {
			 count++;
			 if(classPart.get(count).equals("ID"))
				{
					count++;
					if(init(classPart,count))
					{
						count=count_int;
						if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
						
					}
					else if(list(classPart,count))
					{
						count=count_int;
						count_int=count;
						return true;
					}
				}
		 }
		 else if(classPart.get(count).equals("Terminator"))
		 {
			// count++;
			 count_int=count;
				return true;
		 }
		 return false;
	 }
	 
	 //Other models
	 boolean array(ArrayList<String> classPart,int count)
	 {
		 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
		 if(classPart.get(count).equals("Assign Op"))
		 {
			count++;
			if(exp.exp(classPart, count))
			{
				count_int=exp.count;
				count=count_int;
				count_int=count;
				return true;
			}
		 }
		 else if(classPart.get(count).equals("Assignment"))
		 {
			 count++;
			 if(classPart.get(count).equals("["))
			 {
				 count++;
				 if(words.isConst(classPart.get(count)))
				 {
					 count++;
					 if(clist1(classPart,count))
					 {
						 count=count_int;
						 return true;
					 }
				 }
				 else if(classPart.get(count).equals("]"))
				 {
					 count=count_int;
						count_int=count;
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 boolean clist1(ArrayList<String> cp,int c)
	 {
	 	if(c>=cp.size()){ c=cp.size()-1; return true;}
	 	 if(cp.get(c).equals(","))			//Call to clist1
	 	 {
	 		 c++;
	 		 if(words.isConst(cp.get(c)))
	 		 {
	 			 c++;
	 			 if(clist1(cp,c))
	 			 {
	 				 c=count_int;
					 count_int=c;
					 return true;
				 
	 			 }
	 			 
	 		 }
	 	 }
	 	 else if(cp.get(c).equals("]"))
	 	 {
	 		c++;
			 count_int=c;
			 return true;
	 	 }
	 	return false;
	 } 
	 boolean Object(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		
		 if(cp.get(c).equals("ID"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("ID"))
			 {
				 
				 c++;
				 
				 if(obj1(cp,c))
				 {
					 c=count_int;
					 
					 if(Ob(cp, c))
					 {
						 
						 if(c>=cp.size())
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
						 }
						 else
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
			 }
		 }
		 return false;
	 }
	 boolean obj1(ArrayList<String> cp, int c)
	 {
		 if(cp.get(c).equals("Assignment"))
		 {
			 
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 if(exp.exp(cp, c))
				 {
					 c=exp.count;
					 count_int=c;
					return true;
				 }
			 }
			
		 }
		 else if(cp.get(c).equals("["))
		 {
			 
			 c++;
			 //System.out.println("GG"+cp.get(c));
			 if(words.isConst(cp.get(c)))
			 {
				 c++;
				 if(cp.get(c).equals("]"))
				 {
					 c++;
					 //Here init_ob
					 
					if(init_ob(cp,c))
					{
						c=count_int;
						 return true;
					}
				 }
			 }
			 else if(exp.exp(cp, c))
			 {
				 
				 c=exp.count;
				
				 count_int=c;
				 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
				 else
				 {
					 
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
				 }
				
			 }
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean init_ob(ArrayList<String> cp,int c)
	 {
		if(c>=cp.size()) return true; 
		 if(cp.get(c).equals("Assignment"))
		 {
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 
				 if(exp.exp(cp, c))
				 {
					 
					 c=exp.count;
					 
					 count_int=c;
					return true;
				 }
		/*		 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						 c=exp.count;
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 c++;
						 if(words.isConst(cp.get(c)))
						 {
							 c++;
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
		*/		 
			 }
			 
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean Ob(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 else
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID")) //obj1
				 {
					 c++;
					 if(obj1(cp,c))
					 {
						 c=count_int;
						 if(Ob(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
						
				 }
			 }
			 else  if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 
				 count_int=c;
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				 
				 count_int=c;
				 return true;
			 }

		 }
			 return false;
	 }
	}
	//DECLARATION COMPLETED
	

	//DECLARATION SEMANTIC START

	class declarationModelSemantic 
	{
		KeyWords words = new KeyWords();
		public int count=0,count_int=0,failedCount=0;
		 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
			 ArrayList<String> varTable = new ArrayList();
		String follow_of_Parent,tempDT;
		String follow_for_exp[]={"Terminator","]"};
		ExpModel exp ;
		int start=0,end,scope;
		
		declarationModelSemantic(){}
	 public declarationModelSemantic(ArrayList<String> cp, ArrayList<String> vp,int scope)
	 {
			this.cp = cp;
			this.vp = vp;
			this.scope=scope;
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
	 }
	 public declarationModelSemantic(ArrayList<String> cp, ArrayList<String> vp,String Follow,int scope)
	 {
			this.cp = cp;;
			this.vp = vp;
			follow_of_Parent=Follow;
			
			this.scope=scope;
			exp = new ExpModel(cp,vp,follow_for_exp,true,scope);
	 }
	 private boolean LookupVar(String name)
	 {
		 for(int i=0; i<tv.isVarArrayTable.size();i++)
		 {
			 if(name.contentEquals(tv.varTable.get(i)))
			 {
				 System.out.println("Class_"+tv.varTable.get(i));
				 return true;
		 
			 }
		 }
		// System.out.println("jhhjhj"+Syntaxtification.varTable+","+name);
		 return false;
	 }
	 private boolean lookUpClass(String name)
 	 {
 		if(Syntaxtification.ct.Cname.size()>0 )
 		{
 			for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
 			if(Syntaxtification.ct.Cname.get(i).equals(name))
	 			 return true;
 			
	 		 
	 		if(Syntaxtification.ct.innerClassList.size()>0 && Syntaxtification.ct.innerClassList.size()<ClassIndex)
	 			for(int i=0;i<Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.size();i++) 
	 				if(Syntaxtification.ct.innerClassList.get(ClassIndex).Cname.get(ClassIndex).equals(name))
	 		 			 return true;
	 		 
 		}
 		System.out.println(name);
 		return false;
 	 }
	 private boolean field(ArrayList<String> classPart,int count)
	 {
		 if(classPart.get(count).equals("datatype"))
		 {
			 
			 count++;
			
			if(classPart.get(count).equals("ID"))
			{
				if(LookupVar(vp.get(count)))
				{
					System.out.println("Redeclaration Error at line number:"+count+",,"+vp.get(count));
					return false;
				}
				else
				{
					tv.varTable.add(vp.get(count)); //Adding name
					tv.varScope.add(scope); //Adding Scope
					tv.varDT.add(vp.get(count-1)); //Adding DataType
					//Syntaxtification.varTable.add(vp.get(count));
					tempDT=vp.get(count-1);
				}
				count++;
				
				if(init(classPart,count))
				{
					count=count_int;
					
					if(list(classPart,count))
					{
						
						count=count_int;
						
						//System.out.println(count+"e"+classPart.get(count));
						if(classPart.get(count).equals("Terminator"))
						{
							count++;
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							
							return true;
						}
							  
					}
					else if(classPart.get(count).equals("Terminator"))
					{
						count++;
						this.count=count;
						count_int=count;
						exp.count_int=0;
						
						return true;
					}
				}
				else if(list(classPart,count))
				{
					count=count_int;
					if(count>=classPart.size())
					{
						if(classPart.get(count-1).equals("Terminator"))
						{
							//this.count=count;
							count_int=count;
							exp.count_int=0;
							return true;
						}
					}
					if(classPart.get(count).equals("Terminator"))
					{
						
						count++;
						
						count_int=count;
						exp.count_int=0;
						//System.out.println(exp.count+"ff,"+classPart.get(count)+"_init_"+count);
						return true;
					}
				}
				else if(classPart.get(count).equals("Terminator"))
				{
					count++;
					//this.count=count;
					count_int=count;
					exp.count_int=0;
					return true;
				}
			}
		 }
		 return false;
	 }
	 public boolean decl(ArrayList<String> classPart,int count)
	 {
		
	
		 if(field(classPart,count))
		 {
			 	count=count_int;
			 	this.count=count;
				count_int=0;
				exp.count_int=0;	
				return true;
		 }
		 else if(Object(classPart, count))
		 {
			 count=count_int;
			 this.count=count;
				count_int=0;
				exp.count_int=0;
				
				return true;
		 }
		 
		 return false;
	 }
	 boolean init(ArrayList<String> classPart,int count)
	 {
		 if(count>=classPart.size()) {count_int=classPart.size()-1;return true; } 
		    if(classPart.get(count).equals("Assignment"))
			{
		    	
				count++;
				tv.isVarArrayTable.add(false);
				if(exp.exp(classPart, count))
				{
					if(!exp.returnType.equals(tempDT))
					{
						System.out.println("Expecting" +tempDT+" at char number :" +count);
						return false;
					}
					count_int=exp.count;
					count=count_int;
					count_int=count;
					return true;
				}
			}
			else if(array(classPart,count))
			{
				tv.isVarArrayTable.add(true);
				count=count_int;
				count_int=count;
				return true;
			}
			else if(classPart.get(count).equals(","))
			{
				tv.isVarArrayTable.add(false);
				count++;
				count_int=count;
				return true;
			}
		    tv.isVarArrayTable.add(false);
				return false;
	 }
	 boolean list(ArrayList<String> classPart,int count)
	 {
		 
		 //System.out.println(count+"e"+classPart.get(count));
		 if(classPart.get(count).equals(","))
		 {
			 count++;
			 if(classPart.get(count).equals("ID"))
				{
					if(LookupVar(vp.get(count)))
					{
						System.out.print("Redeclaration Error at line number:"+count+",,"+vp.get(count));
						return false;
					}
					else
					{
						
						tv.varTable.add(vp.get(count)); //Adding name
						tv.varDT.add(tempDT); //Adding DataType
						tv.varScope.add(scope); //Adding Scope
					}
					count++;
					if(init(classPart,count))
					{
						count=count_int;
						if(list(classPart,count))
						{
							count=count_int;
							count_int=count;
							return true;
						}
						
					}
					else if(list(classPart,count))
					{
						count=count_int;
						count_int=count;
						return true;
					}
				}
		 }
		 else if(classPart.get(count).equals("Terminator"))
		 {
			// count++;
			 count_int=count;
				return true;
		 }
		 return false;
	 }
	 
	 //Other models
	 boolean array(ArrayList<String> classPart,int count)
	 {
		 if(classPart.size()<=count){count_int=classPart.size()-1;return true; } 
		 if(classPart.get(count).equals("Assign Op"))
		 {
			count++;
			//Syntaxtification.tv.isVarArrayTable.add(true);
			if(exp.exp(classPart, count))
			{
				if(!exp.returnType.equals("dig"))
				{
					System.out.println("Expecting interger at char number :" +count);
					return false;
				}
				count_int=exp.count;
				count=count_int;
				count_int=count;
				return true;
			}
		 }
		 else if(classPart.get(count).equals("Assignment"))
		 {
			 count++;
			 if(classPart.get(count).equals("["))
			 {
				 count++;
				 if(words.isConst(classPart.get(count)))
				 {
					 if(!words.getConst(classPart.get(count)).equals(tempDT))
						{
							System.out.println("Expecting "+tempDT+" at char number :" +count);
							return false;
						}
					 count++;
					 if(clist1(classPart,count))
					 {
						 count=count_int;
						 return true;
					 }
				 }
				 else if(classPart.get(count).equals("]"))
				 {
					 count=count_int;
						count_int=count;
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 boolean clist1(ArrayList<String> cp,int c)
	 {
	 	if(c>=cp.size()){ c=cp.size()-1; return true;}
	 	 if(cp.get(c).equals(","))			//Call to clist1
	 	 {
	 		 c++;
	 		 if(words.isConst(cp.get(c)))
	 		 {
	 			 if(!words.getConst(cp.get(count)).equals(tempDT))
					{
						System.out.println("Expecting "+tempDT +" at char number :" +c);
						return false;
					}
	 			 c++;
	 			 if(clist1(cp,c))
	 			 {
	 				 c=count_int;
					 count_int=c;
					 return true;
				 
	 			 }
	 			 
	 		 }
	 	 }
	 	 else if(cp.get(c).equals("]"))
	 	 {
	 		c++;
			 count_int=c;
			 return true;
	 	 }
	 	return false;
	 } 
	 boolean Object(ArrayList<String> cp,int c)
	 {
		 
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 
		 if(cp.get(c).equals("ID"))
		 {
			 if(!lookUpClass(vp.get(c)))
				{
					System.out.println("No class found Error at line number:"+c+",,"+vp.get(c));
					return false;
				}
				
			 
			 
			  c++;
			 if(cp.get(c).equals("ID"))
			 {
				
				 
				  	if(LookupVar(vp.get(c)))
					{
						System.out.println("Redeclaration Error at line number:"+c+",,"+vp.get(c));
						return false;
					}
					else
					{
						tv.varTable.add(vp.get(c)); //Adding name
						tv.varDT.add("class_"+vp.get(c-1)); //Adding DataType
						tv.varScope.add(scope); //Adding Scope
					}
			  
				  	c++;
				
				 if(obj1(cp,c))
				 {
					 
					 c=count_int;
					 
					 if(Ob(cp, c))
					 {
						 
						 if(c>=cp.size())
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
						 }
						 else
						 {
							 if(cp.get(c-1).equals("Terminator"))
							 {
								 count_int=c;
								 return true;
							 }
							 else if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
			 }
		 }
		 return false;
	 }
	 boolean LookUpClassParent(String child,String Parent)
	 {
		 int indexOfChild=0,i=0;
		 indexOfChild=indexOfChild(child);
		i= indexOfParent(Parent,indexOfChild);
		 if(i!=-1)
		 {
			 System.out.println(Syntaxtification.ct.Pname.get(indexOfChild)+","+i);
			 return true;
		 }
			
		
		 
		 return false;
	 }
	 int indexOfParent(String Parent,int indexOfChild)
	 {
		 if(Syntaxtification.ct.Pname.get(indexOfChild).equals(Parent))
		 {
			
			 return indexOfChild;
		 }
		 else
		 {
			 if(Syntaxtification.ct.Pname.get(indexOfChild).equals("-"))
				 return -1;
			 else
			 {
				 return indexOfParent(Parent,indexOfChild(Syntaxtification.ct.Pname.get(indexOfChild)));
				
			 }
						 
		 }
		 
	 }
	 int indexOfChild(String Child)
	 {
		 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
		 {
			 if(Syntaxtification.ct.Cname.get(i).equals(Child))
			 {
				 return i;
			 }
		 }
		 return -1;
	 }
	 boolean obj1(ArrayList<String> cp, int c)
	 {
		 
		 if(cp.get(c).equals("Assignment"))
		 {
			 Syntaxtification.tv.isVarArrayTable.add(false);
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 if(cp.get(c).equals("ID"))
				 {
					 
					 if(vp.get(c).equals(vp.get(c-4)))
						{
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 count_int=c;
							return true;
						 }
						}
					 else if(!vp.get(c).equals(vp.get(c-4)))
						{
						  
						 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
							{
								
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
								
								
								
							}
						 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
							{
								
								
									System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
									return false;
								
								
								
							}
						  else
						  {
							System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
							return false;
						  }
						}
						
				 }
				
			 }
			
		 }
		 else if(cp.get(c).equals("["))
		 {
			 
			 c++;
		
			 Syntaxtification.tv.isVarArrayTable.add(true);
			 if(words.isConst(cp.get(c)))
			 {
				 if(!words.getConst(cp.get(c)).equals(tempDT))
					{
						System.out.println("Expecting "+tempDT+" at char number :" +c);
						return false;
					}
				 c++;
				 if(cp.get(c).equals("]"))
				 {
					 c++;
					 //Here init_ob
					 
					if(init_ob(cp,c))
					{
						c=count_int;
					
						 return true;
					}
				 }
			 }
			 else if(exp.exp(cp, c))
			 {
				 if(!exp.returnType.equals(tempDT))
					{
						System.out.println("Expecting "+tempDT+" at char number :" +count);
						return false;
					}
				 c=exp.count;
				
				 count_int=c;
				 if(cp.size()<=c){ if(cp.get(c-1).equals("]")){return true;}}
				 else
				 {
					 
					 if(cp.get(c).equals("]"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
					 else  if(cp.get(c).equals("Terminator"))
					 {
						 c++;
						 //Here init_ob
						if(init_ob(cp,c))
						{
							c=count_int;
							 return true;
						}
					 }
				 }
				
			 }
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 tv.isVarArrayTable.add(false);
			 c++;
			 count_int=c;
			 return true;
		 }
		 else  if(cp.get(c-1).equals("Terminator"))
		 {
			 tv.isVarArrayTable.add(false);
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 tv.isVarArrayTable.add(false);
			 c++;
			 count_int=c;
			 return true;
		 }
		 
		 return false;
	 }
	 boolean init_ob(ArrayList<String> cp,int c)
	 {
		if(c>=cp.size()) return true; 
		
		 if(cp.get(c).equals("Assignment"))
		 {
			 tv.isVarArrayTable.add(false);
			 c++;
			 if(cp.get(c).equals("new"))
			 {
				 c++;
				 
				 if(cp.get(c).equals("ID"))
				 {
					 if(vp.get(c).equals(vp.get(c-4)))
						{
						 if(exp.exp(cp, c))
						 {
							 c=exp.count;
							 count_int=c;
							return true;
						 }
						}
					 else if(!vp.get(c).equals(vp.get(c-4)))
						{
						  
						 if(LookUpClassParent(vp.get(c),vp.get(c-4)))
							{
								
							 if(exp.exp(cp, c))
							 {
								 c=exp.count;
								 count_int=c;
								return true;
							 }
								
								
								
							}
						 	else if(!LookUpClassParent(vp.get(c),vp.get(c-4)))
							{
								
								
									System.out.println("No parent class at char number:"+c+",,"+vp.get(c));
									return false;
								
								
								
							}
						  else
						  {
							System.out.println("no classFound Error Error at line number : "+c+",,"+vp.get(c));
							return false;
						  }
						}
				 }
		/*		 if(cp.get(c).equals("ID"))
				 {
					 c++;
					 if(exp.exp(cp, c))
					 {
						 c=exp.count;
						 count_int=c;
						 return true;
					 }
					 else if(cp.get(c).equals("["))
					 {
						 c++;
						 if(words.isConst(cp.get(c)))
						 {
							 c++;
							 if(cp.get(c).equals("]"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
				 }
		*/		 
			 }
			 
		 }
		 else if(cp.get(c).equals("Terminator"))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 else  if(cp.get(c-1).equals("Terminator"))
		 {
			
			 count_int=c;
			 return true;
		 }
		 else if(cp.get(c).equals(","))
		 {
			 c++;
			 count_int=c;
			 return true;
		 }
		 return false;
	 }
	 boolean Ob(ArrayList<String> cp,int c)
	 {
		 if(c>=cp.size())
		 {
			 return true;
		 }
		 else
		 {
			 if(cp.get(c).equals(","))
			 {
				 c++;
				 if(cp.get(c).equals("ID")) //obj1
				 {
					
						{
							tv.varTable.add(vp.get(c)); //Adding name
							tv.varDT.add(tempDT); //Adding DataType
							tv.varScope.add(scope); //Adding Scope
						}
				  
					 c++;
					 if(obj1(cp,c))
					 {
						 c=count_int;
						 if(Ob(cp,c))
						 {
							 c=count_int;
							 if(cp.get(c).equals("Terminator"))
							 {
								 c++;
								 count_int=c;
								 return true;
							 }
						 }
					 }
						
				 }
			 }
			 else  if(cp.get(c).equals("Terminator"))
			 {
				 c++;
				 count_int=c;
			
				 
				 return true;
			 }
			 else  if(cp.get(c-1).equals("Terminator"))
			 {
				 
				 count_int=c;
			
				 
				 return true;
			 }
		 }
		 
			 return false;
	 }
	}

	//DECLARATION SEMANTIC END



	 class ExpModel
		{
			KeyWords words = new KeyWords();
			public int count=0,count_int=0,FailedCounter=0,scope=0;
			 String follow_of_Parent[];
			 ArrayList<String> cp = new ArrayList();
			 ArrayList<String> vp = new ArrayList();
		
			 boolean ifParams=false,typeChanged=false,opFlag=false,startUniOp=false;
			 String returnType="",ChangeType="";
			
			 String leftOp="",rightOp="";
			 public boolean AllowAssignation=true;
			ExpModel(){}
			public ExpModel(ArrayList<String> cp,ArrayList<String> vp,int scope)
			{
				this.cp=cp;
				this.vp=vp;
				this.scope=scope;

			}
			public ExpModel(ArrayList cp,ArrayList vp,String follow[],int scope)
			{
				this.cp=cp;
				this.vp=vp;
				this.scope=scope;
				follow_of_Parent=follow;
				
			}
			public ExpModel(ArrayList cp,ArrayList vp,boolean allow,int scope)
			{
				this.cp=cp;
				this.vp=vp;
				AllowAssignation=allow;
				this.scope=scope;
			}
			public ExpModel(ArrayList cp,ArrayList vp,String follow[],boolean allow,int scope)
			{
				this.cp=cp;
				this.vp=vp;
				this.scope=scope;
				follow_of_Parent=follow;
				AllowAssignation=allow;
				
			}
			
		public 	boolean exp(ArrayList<String> ClassPart,int count)
			{
			
				if(isUniOp(ClassPart.get(count)))
				{
				
					startUniOp=true;
					count++;
					if(exp1(ClassPart,count))
					{
						count=count_int;
						this.count=count;
					
						return true;
					}
				}
				else if(exp1(ClassPart,count))
				{
					
					count=count_int;
					count_int=0;
					this.count=count;
					
					
					return true;
				}
				else if(words.isParentFollow(cp.get(count), follow_of_Parent))
				{
					count=count_int;
					this.count=count;
					
					return true;
				}
				if(FailedCounter<count)
					FailedCounter=count;
			
				return false;
			}

		boolean exp1(ArrayList<String> cp,int c)
		{
				if(cp.size()<=c){count_int=cp.size()-1;return true; }
			
				if(cp.get(c).equals("ID"))
				{
					
					if(vp.get(c+1).equals("("))
					{
						String[] follow ={")","RO","BWOP","OR","AddSum","Mux","DIVMOD","Incdec","LO"};
						functionCallModel fcm= new functionCallModel(cp,vp,follow,scope+1);
						fcm.follow_for_exp[0]=")";
						
						if(fcm.functionCall(cp, c))
						{
							
							String dt=getFuncReturnType(vp.get(c));
							String rdt=getFuncReturnType(vp.get(c));
							
							c=fcm.count-1;
							if(!opFlag) //No Left Operator Exists right now
							{
								
								 	if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
									{
								 		startUniOp=false;
											System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
											return false;
											
		
									}
									else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
									{
										startUniOp=false;
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
								else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
													leftOp=dt; //left operator
													opFlag=true;
													
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
								{
										System.out.println("Cannot increment function "+vp.get(c)+" at char number: "+c);
										return false;
										
	
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
									if(dt.equals("boolean"))
									{
										returnType="boolean";
									}
									else 
									{
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								
					
							}
							else if(opFlag) //Left Operator Exists
							{
								
								{
									if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
									{

										rightOp=rdt;
										returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
										if(returnType.equals("x"))
										{
											System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										leftOp=returnType;
										opFlag=true;
										
									}
									else //No more operators
									{

										
												
												if(!typeChanged)
												{
													
													
														rightOp=rdt;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														
														 
													
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=rdt;
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													
													else
													{
														rightOp=ChangeType;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														
													}
												}
												
											
										
									}
								}
								
							}
						
						}
						/*
						if(!lookupFunction(vp.get(c)))
						{
							System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
							return false;
						}
						else
						{}
						*/
					}//Not function
					else //Maybe variable?
					{
						if(!lookUpVar(vp.get(c)))
						{
							System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
							return false;
						}
						else
						{
							if(!opFlag) //No Left Operator Exists right now
							{
								String dt=getReturnTypeVar(vp.get(c));
								returnType=dt;
								 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
									{
									 startUniOp=false;
										if(dt.equals("dig") || dt.equals("frac"))
										{
											returnType="dig";
										}
										else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
										{
											System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
									else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
									{
										startUniOp=false;
										if(dt.equals("boolean"))
										{
											returnType="boolean";
										}
										else 
										{
											System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
											return false;
										}
											
									}
								else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
													leftOp=dt; //left operator
													opFlag=true;
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
								{
									if(dt.equals("dig") || dt.equals("frac"))
									{
										returnType="dig";
									}
									else if(dt.equals("beta") || dt.equals("alpha") || dt.equals("boolean"))
									{
										System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
								{
									if(dt.equals("boolean"))
									{
										returnType="boolean";
									}
									else 
									{
										System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
										return false;
									}
										
								}
								else if(!vp.get(c+1).equals("."))
								{

										
										if(!typeChanged)
										{
										
												if(cp.get(c+1).equals("["))
												if(!isArrayType(vp.get(c)))
												{
													System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
													return false;
												}
										
										}
										else if(typeChanged)
										{
											typeChanged=false;
											
											if(!compTypeCast(dt,ChangeType))
											{
												System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
												return false;
											}
										
										}
											
										
									
								}
							}
							else if(opFlag) //Left Operator Exists
							{
								String rdt=getReturnTypeVar(vp.get(c));
								if(cp.get(c+1).equals("["))
								{
									if(!isArrayType(vp.get(c)))
									{
										System.out.println("No array found error "+vp.get(c)+" at line number: "+c);
										return false;
									}
								}
								else
								{
									if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
									{
									
										
										rightOp=rdt;
										returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
										if(returnType.equals("x"))
										{
											System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										leftOp=returnType;
										opFlag=true;
										
									}
									else //No more operators
									{
												if(!typeChanged)
												{
													
													
														rightOp=rdt;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
												
												}
												else if(typeChanged)
												{
													typeChanged=false;
													String temp=rdt;
													if(!compTypeCast(temp,ChangeType))
													{
														System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
														return false;
													}
													
													else
													{
														rightOp=ChangeType;
														returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
														if(returnType.equals("x"))
														{
															System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
															return false;
														}
														opFlag=false;
														
													}
												}
												
											
									
									}
								}
								
							}
						}
					
					}
					c++;
					
					if(exp21(cp,c))
					{
						c=count_int;
						return true;
					}
				}
				else if(words.isConst(cp.get(c)))
				{
					
					if(!opFlag) //No Left Operator Exists right now
					{
						
						 if(startUniOp && cp.get(c-1).equals("Incdec") )	//Check if operator is there?
							{
							 startUniOp=false;
							 
								if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
								{
									returnType="dig";
								}
								else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
								{
									System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
									return false;
								}
									
							}
							else if(startUniOp && cp.get(c-1).equals("Not operator") )	//Check if operator is there?
							{
								
								startUniOp=false;
								
									System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
									return false;
								
									
							}
						else if(isOp(cp.get(c+1), c+1) && !valueOp(c+1).equals("Incdec") && !valueOp(c+1).equals("Not operator") )	//Check if operator is there?
						{
											leftOp=words.getConst(cp.get(c)); //left operator
											opFlag=true;
						}
						else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Incdec") )	//Check if operator is there?
						{
							if(words.getConst(cp.get(c)).equals("dig") || words.getConst(cp.get(c)).equals("frac"))
							{
								returnType="dig";
							}
							else if(words.getConst(cp.get(c)).equals("beta") || words.getConst(cp.get(c)).equals("alpha") || words.getConst(cp.get(c)).equals("boolean"))
							{
								System.out.println("Cannot increment String/char/boolean datatypes "+vp.get(c)+" at char number: "+c);
								return false;
							}
								
						}
						else if(isOp(cp.get(c+1), c+1) && valueOp(c+1).equals("Not operator") )	//Check if operator is there?
						{
							if(words.getConst(cp.get(c)).equals("boolean"))
							{
								returnType="boolean";
							}
							else 
							{
								System.out.println("Cannot not operand on this datatype: "+vp.get(c)+" at char number: "+c);
								return false;
							}
								
						}
						else
						{

								
									if(!typeChanged)
									{
										returnType=words.getConst(cp.get(c));
									}
									else if(typeChanged)
									{
										typeChanged=false;
										String temp=words.getConst(cp.get(c));
										if(!compTypeCast(temp,ChangeType))
										{
											System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
										else
											{
											returnType=ChangeType;
											}
									}
									
								
							
						}
					}
					else if(opFlag) //Left Operator Exists
					{
						if(isOp(cp.get(c+1), c+1))	//Check if operator is there? if yes=>
						{

							rightOp=words.getConst(cp.get(c));
							returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
							if(returnType.equals("x"))
							{
								System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							leftOp=returnType;
							opFlag=true;
							
						}
						else //No more operators
						{

									if(!typeChanged)
									{
											rightOp=words.getConst(cp.get(c));
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											opFlag=false;
										
											 
										
									}
									else if(typeChanged)
									{
										typeChanged=false;
										String temp=words.getConst(cp.get(c));
										if(!compTypeCast(temp,ChangeType))
										{
											System.out.println("invalid typeCast error "+vp.get(c)+" at line number: "+c);
											return false;
										}
										
										else
										{
											rightOp=ChangeType;
											returnType=OperationOnOperands(leftOp,rightOp,valueOp(c-1),(c-1));
											if(returnType.equals("x"))
											{
												System.out.println("invalid operation error "+vp.get(c)+" at line number: "+c);
												return false;
											}
											opFlag=false;
											
										}
									}
									
						
						}
					}
					c++;
					
					if(exp2(cp,c))
					{
						
						c=count_int;
						return true;
					}
				}
				else if(cp.get(c).equals("("))
				{
					c++;
					if(news(cp,c))
					{
					
						c=count_int;
						return true;
					}
				}
				if(FailedCounter<c)
				FailedCounter=c;
				return false;
		}
		boolean compTypeCast(String func,String Modified)
		{
			if(Modified.equals("alpha")||Modified.equals("frac")||Modified.equals("dig"))
			{
				if(func.equals("dig") || func.equals("frac") || func.equals("alpha"))
					return true;
				
			}
			else if(Modified.equals("beta"))
			{
				
				if(func.equals("beta"))
					return true;
			}
			
			return false;
		}
		boolean exp2(ArrayList<String>  cp,int c)
		{
			
				if(cp.size()<=c){count_int=cp.size()-1; return true; } 
				else
				{
					if(isOp(cp.get(c),c))
					{
						//..........
						c++;
						if(exp1(cp,c))
						{
							c=count_int;
							return true;
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					else if(cp.get(c).equals(")"))
					{
						count_int=c;
						return true;			
					}
					else if(words.isParentFollow(cp.get(c),follow_of_Parent))
					{

						count_int=c;
						return true;
								
					}
					else if(cp.get(c).equals(",") && !ifParams)
					{
						count_int=c;
						
						return true;
					}
					else if(cp.get(c).equals("]"))
					{
						count_int=c;
						return true;
					}
				}
				if(FailedCounter<c)
					FailedCounter=c;
				return false;
			}
		boolean exp21(ArrayList<String>  cp,int c)
			{
				if(c>=cp.size()) {count_int=cp.size()-1;return true; } 
				
				if(exp2(cp,c))
				{
					c=count_int;
					
					return true;
				}
			
				else if(cp.get(c).equals("["))
				{
					
					c++;
					
					if(words.isConst(cp.get(c)))
					{
						
						c++;
						
						if(cp.get(c).equals("]"))
						{
							c++;
							if(exp2(cp,c))
							{
								
								c=count_int;
								
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;	
						}
						if(FailedCounter<c)
							FailedCounter=c;
					}
					else if(cp.get(c).equals("ID"))
					{

						if(vp.get(c+1).equals("("))
						{
							if(!lookupFunction(vp.get(c)))
							{
								System.out.println("No function found error "+vp.get(c)+" at line number: "+c);
								return false;
							}
							
						}//Not function
						else //Maybe variable?
						{
							
								if(!lookUpVar(vp.get(c)))
								{
									System.out.println("No variable found error "+vp.get(c)+" at line number: "+c);
									return false;
								}
								
							
						}
						c++;
						
						if(exp1(cp,c))
						{
							
							c=count_int;
					
								//System.out.println(cp.get(c)+" "+c+"Exp21="+"g");
							if(cp.get(c).equals("]"))
							{
								
								c++;
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
								if(FailedCounter<c)
									FailedCounter=c;
							}
							else if(cp.get(c-1).equals("]"))
							{
								
								if(exp2(cp,c))
								{
									
									c=count_int;
									return true;
								}
								return true;
							}
							if(FailedCounter<c)
								FailedCounter=c;
						}
						if(FailedCounter<c)
							FailedCounter=c;
					
					}
					if(FailedCounter<c)
						FailedCounter=c;
				}
				
				else if(O1(cp,c))
				{
					
					c=count_int;
					if(exp2(cp,c))
					{
						
						c=count_int;
						count_int=c;
					
						if(c>=cp.size()){c=count_int=cp.size()-1;}
						return true;
					}
					else if(cp.get(c).equals("Dot operator"))
					{
						if(exp21(cp,c))
						{
							c=count_int;
							count_int=c;

							if(c>=cp.size()){c=count_int=cp.size()-1;}
							return true;
						}
					}
				}
				if(FailedCounter<c)
					FailedCounter=c;
				return false;
			}
		boolean params(ArrayList<String>  cp,int c)
			{
			
				if(cp.size()<=c){count_int=cp.size()-1;return true; }
				ifParams=true;

				if(cp.get(c).equals(")"))
				{
					c++;
					count_int=c;
					
					ifParams=false;
					return true;
				}
				else if(exp1(cp,c))
				{
					
					c=count_int;
					//System.out.println(cp.get(c)+":exp1 of params"+c);
					if(c<cp.size())
					{
						
						if(cp.get(c).equals(")"))
						{
							c++;
							count_int=c;
							//System.out.println(cp.get(c)+":dF"+c);
							ifParams=false;
							return true;
						}
						else if(cp.get(c).equals("]"))
						{
							c++;
							count_int=c;
							//System.out.println("___"+cp.get(c));
							ifParams=false;
							return true;
						}
						else if(cp.get(c).equals(",")) //what if datatype?
						{
							
							c++;
							 if(cp.get(c).equals("ID"))
							 {
								 c++;
								 if(cp.get(c).equals("ID"))
								 {
									 c++;
									 if(params(cp,c))
									 {
											
											c=count_int;
											ifParams=false;
											return true;
								     }
								 }
							 }
						}
					}
					else if(cp.get(c-1).equals(")"))
					{
					
						c=count_int;
						ifParams=false;
						return true;
					}
				}
				if(FailedCounter<c)
					FailedCounter=c;
				return false;
			}
		boolean news(ArrayList<String>  cp,int c)
			{
				
			if(isUniOp(cp.get(c)))
			{
				c++;
				if(exp1(cp,c))
				{
					
					c=count_int; 
					
					if(c>=cp.size())
					{
						if(cp.get(c-1).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c=count_int;
							
							return true;
						}
					}
					else
					if(cp.get(c).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c++;
						if(exp2(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
					}
					else if(cp.get(c-1).equals(")"))
					{
						//c++;
						if(ifParams){count_int=c;return true;}
						if(exp2(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
					}
				}
			
			}
			else if(exp1(cp,c))
				{
					
					c=count_int; 
					//System.out.println(c+":33:"+cp.get(c));
					if(c>=cp.size())
					{
						if(cp.get(c-1).equals(")"))
						{
							if(ifParams){count_int=c;return true;}
							c=count_int;
							
							return true;
						}
						else if(words.isParentFollow(cp.get(c-1), follow_of_Parent))
						{
						   c=count_int;	
							return true;
						}
					}
					else
					if(cp.get(c).equals(")"))
					{
						if(ifParams){count_int=c;return true;}
						c++;
						
						if(exp2(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
					}
					else if(cp.get(c-1).equals(")"))
					{
						//c++;
						if(ifParams){count_int=c;return true;}
						if(exp2(cp,c))
						{
							
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							
							return true;
						}
						else if(words.isParentFollow(cp.get(c), follow_of_Parent))
						{
							//System.out.println(count_int+"_2He"+cp.get(c)+","+c);
						   c=count_int;	
							return true;
						}
					}
					else if(words.isParentFollow(cp.get(c), follow_of_Parent))
					{
						//System.out.println(count_int+"_eHe"+cp.get(c)+","+c);
					   c=count_int;	
						return true;
					}
				
				}
				if(cp.get(c).equals(")"))
				{
					if(ifParams){count_int=c;return true;}
					c++;
					
					if(exp2(cp,c))
					{
						c=count_int; //count_int=0; //count_int=0;
						//count_int=0;
						
						return true;
					}
				}
				else if(cp.get(c).equals("datatype"))
				{
					ChangeType=vp.get(c);
					typeChanged=true;
					c++;
					if(cp.get(c).equals(")"))
					{
						
						c++;
						if(exp1(cp,c))
						{
							c=count_int; //count_int=0; //count_int=0;
							//count_int=0;
							return true;
						}
					}
				}
				else if(words.isParentFollow(cp.get(c), follow_of_Parent))
				{
					//System.out.println(count_int+"_1He"+cp.get(c)+","+c);
				   c=count_int;	
					return true;
				}
				if(FailedCounter<c)
					FailedCounter=c;
				return false;
			}
		boolean O1(ArrayList<String>  cp,int c) // Incomplete
			{
				if(cp.get(c).equals("Dot operator"))
				{
					
					c++;
					
					if(cp.get(c).equals("ID"))
					{
						
					
						if(exp1(cp,c))
						{
							c=count_int;
							if(O1(cp,c))
							{
								c=count_int;
								count_int=c;
								//System.out.println(c+":fff:"+cp.get(c));
								return true;
							}
						}
					 if(cp.get(c).equals("["))
						{
							
							c++;
							//System.out.println(cp.get(c)+"_"+c+ " ex###gg3-conccst-pass");
							if(words.isConst(cp.get(c)))
							{
								c++;
								
								if(cp.get(c).equals("]"))
								{
									c++;
									count_int=c;
									//System.out.println("333,"+c+","+cp.get(c));
									return true;
								}
							}
						}
						else if(cp.get(c).equals("&"))
						{
							c++;
							count_int=c;
							return true;
						}
						else if(cp.get(c).equals("Dot operator"))
						{
							if(O1(cp,c))
							{
								c=count_int;
								count_int=c;
								//System.out.println(c+":fff:"+cp.get(c));
								return true;
							}
						}
						else if(cp.get(c).equals(")"))
						{
						
							c=count_int=c;
							if(ifParams)
							ifParams=false;
							return true;
						}
					}
					else if(words.isConst(cp.get(c)))
					{
						c++;
						count_int=c;
						return true;
					}
				}
				else if(cp.get(c).equals("&"))
				{
					c++;
					count_int=c;
					return true;
				}
				if(FailedCounter<c)
					FailedCounter=c;
				return false;
			}
		String OperationOnOperands(String left,String right,String op,int cpInd)
		{
			if(op.equals("mux")||op.equals("demux")||op.equals("ken")|| cp.get(cpInd).equals("DIVMOD"))
			{
				if(left.equals("dig"))
				{
					if(right.equals("dig"))
						return "dig";
					else if(right.equals("frac"))
						return "frac";
					else if(right.equals("alpha"))
						return "dig";
					
				}
				else if(left.equals("frac"))
				{
					if(right.equals("dig"))
						return "frac";
					else if(right.equals("frac"))
						return "frac";
					else if(right.equals("alpha"))
						return "frac";
					
				}
				else if(left.equals("alpha"))
				{
					if(right.equals("dig"))
						return "alpha";
					else if(right.equals("frac"))
						return "alpha";
					else if(right.equals("alpha"))
						return "alpha";
					
				}
				
			}
			else if(op.equals("suma"))
			{
				if(left.equals("dig"))
				{
					if(right.equals("dig"))
						return "dig";
					else if(right.equals("frac"))
						return "frac";
					else if(right.equals("alpha"))
						return "dig";
					
				}
				else if(left.equals("frac"))
				{
					if(right.equals("dig"))
						return "frac";
					else if(right.equals("frac"))
						return "frac";
					else if(right.equals("alpha"))
						return "frac";
					
				}
				else if(left.equals("alpha"))
				{
					if(right.equals("dig"))
						return "alpha";
					else if(right.equals("frac"))
						return "alpha";
					else if(right.equals("alpha"))
						return "alpha";
					
				}
				else if(left.equals("beta"))
				{
					if(right.equals("dig"))
						return "beta";
					else if(right.equals("frac"))
						return "beta";
					else if(right.equals("alpha"))
						return "beta";
					
					
				}
				
			}
			else if(cp.get(cpInd).equals("RO"))
			{
				if(left.equals("dig")||left.equals("frac")||left.equals("alpha"))
				{
					if(right.equals("dig"))
						return "boolean";
					else if(right.equals("frac"))
						return "boolean";
					else if(right.equals("alpha"))
						return "boolean";
					
				}
				
			}
			else if(op.equals("===") || cp.get(cpInd).equals("BWOP"))
			{
				return "boolean" ;
			}
			return "x";
		}
		String valueOp(int index)
			{
				
				return vp.get(index);
			}
	
		boolean isOp(String cp,int c)
			{
				
				if(cp.equals("RO"))//
				{
					 return true;
				}
				else if(cp.equals("Assignment") && AllowAssignation)
				{
					return true;
				}
				else if(cp.equals("BWOP"))
				{
					if(valueOp(c).equals("bitOr"))
					 return true;
				}
				else if(cp.equals("BWOP"))
				{
					if(valueOp(c).equals("bitAnd"))
					 return true;
				}
				else if(cp.equals("OR"))
				{
					 return true;
				}
				else if(cp.equals("AddSum"))
				{
					 return true;
				}
				else if(cp.equals("Mux"))
				{
					
					 return true;
				}
				else if(cp.equals("DIVMOD"))
				{
					 return true;
				}
				else if(cp.equals("Incdec") || cp.equals("Not operator"))
				{
					 return true;
				}
				else if(cp.equals("LO"))
				{
					 return true;
				}

				
				
				return false;
			}
			
		boolean isUniOp(String a)
			{
				if(a.equals("Not operator") || a.equals("Incdec") || a.equals("AddSum") )
				{
					
					return true;

				}
				
				return false;
			}
		
		
		
		 //<-- Finding functionExist or Not?
		 private boolean lookupFunction(String name)
		 {
			 //1)CurrentClass--tFunction
			 	if(Syntaxtification.ct.Cname.size()>0 )
			 	{
			 			if(tFunction.nameFunction.size()>0)
			 				for(int i=0;i<tFunction.nameFunction.size();i++)
			 				{
			 					if(tFunction.nameFunction.get(i).equals(name))
			 						return true;
			 				}
				 //TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
			 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
			 						return true;
			 			
				 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				if(LookUpFunctionin_ClassParent(name,CurrentPname))
			 					return true;
			 			} 
		 		}
		 		
		 		return false;
		 }

		private boolean LookUpFunctionin_ClassParent(String nameFunction,String Parent)
		 {
			int i= indexOfParent(Parent,nameFunction);
			 if(i!=-1) 
				 return true;
			 return false;
		 }
		 private int indexOfParent(String Parent,String nameFunction)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.functionslist.size();j++)
						 for(int k=0;k<Syntaxtification.ct.functionslist.get(j).paramList.size();k++)
							 if(Syntaxtification.ct.functionslist.get(j).nameFunction.get(k).equals(nameFunction))
							 	return i;
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						 int k = indexOfParent(Syntaxtification.ct.Pname.get(i),nameFunction);
							return k;
					 }
				 }
				
			 return -1;
		 }
		
//Finding functionExist or Not? -->
		 
//<-- Getting paramList
		 private ArrayList<String> getFuncParams(String name)
		 {
			 //1)CurrentClass--tFunction
			 	if(Syntaxtification.ct.Cname.size()>0 )
			 	{
			 			if(tFunction.nameFunction.size()>0)
			 				for(int i=0;i<tFunction.nameFunction.size();i++)
			 				{
			 					if(tFunction.nameFunction.get(i).equals(name))
			 					{
			 						//for(int p=0;p<tFunction.paramList.size();i++)
			 							
			 						return tFunction.paramList.get(i);
			 					}
			 				}
				 //TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
			 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
			 						return Syntaxtification.ct.functionslist.get(ClassIndex).paramList.get(i);
			 			
				 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				
			 					return getParamsFunctionin_ClassParent(name,CurrentPname);
			 			} 
		 		}
		 		
		 		return null;
		 }

		private ArrayList<String> getParamsFunctionin_ClassParent(String nameFunction,String Parent)
		 {
			return getParamsParent(Parent,nameFunction);
			 
		 }
		 private ArrayList<String> getParamsParent(String Parent,String nameFunction)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
							 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
								 	return Syntaxtification.ct.functionslist.get(i).paramList.get(j);
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						return getParamsParent(Syntaxtification.ct.Pname.get(i),nameFunction);
							
					 }
				 }
				
			 return null;
		 }
		
//Getting paramList  -->	
		 
//<-- Getting returnType
		 private String getFuncReturnType(String name)
		 {
			 //1)CurrentClass--tFunction
			 	if(Syntaxtification.ct.Cname.size()>0 )
			 	{
			 			if(tFunction.nameFunction.size()>0)
			 				for(int i=0;i<tFunction.nameFunction.size();i++)
			 				{
			 					if(tFunction.nameFunction.get(i).equals(name))
			 						return tFunction.ReturnType.get(i);
			 				}
				 //TopHierarchical CLass
			 			if(isInner)
			 				for(int i=0;i<Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.size();i++) 
			 					if(Syntaxtification.ct.functionslist.get(ClassIndex).nameFunction.get(i).equals(name))
			 						return Syntaxtification.ct.functionslist.get(ClassIndex).ReturnType.get(i);
			 			
				 //ParentClass of Current Class
			 			if(!CurrentPname.equals("-"))
			 			{
			 				
			 					return getReturnTypeFunctionin_ClassParent(name,CurrentPname);
			 			} 
		 		}
		 		
		 		return null;
		 }

		private String getReturnTypeFunctionin_ClassParent(String nameFunction,String Parent)
		 {
			return getReturnTypeParent(Parent,nameFunction);
			 
		 }
		 private String getReturnTypeParent(String Parent,String nameFunction)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.functionslist.get(i).nameFunction.size();j++)
					
							 if(Syntaxtification.ct.functionslist.get(i).nameFunction.get(j).equals(nameFunction))
								 	return Syntaxtification.ct.functionslist.get(i).ReturnType.get(j);
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						return getReturnTypeParent(Syntaxtification.ct.Pname.get(i),nameFunction);
							
					 }
				 }
				
			 return null;
		 }
		
//Getting returnType  -->	
		private boolean lookUpVar(String name)
	 	 {
			 if(tv.varDT.size()>0) //Current class;
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return true;
			//TopHierarchical CLass
	 			if(isInner)
	 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
	 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
	 						return true;
	 	 //ParentClass of Current Class
	 			if(!CurrentPname.equals("-"))
	 			{
	 				if(LookUpVarin_ClassParent(name,CurrentPname))
	 					return true;
	 			} 
	 		 return false;
	 	 }
		private boolean LookUpVarin_ClassParent(String name,String Parent)
		 {
			int i= VarindexOfParent(Parent,name);
			 if(i!=-1) 
				 return true;
			 return false;
		 }
		private int VarindexOfParent(String Parent,String name)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
							 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
							 	return i;
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						 int k = VarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
							return k;
					 }
				 }
				
			 return -1;
		 }

		private String getReturnTypeVar(String name) //Method to call
	 	 {
			 if(tv.varDT.size()>0) //Current class;
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return tv.varDT.get(i);
			//TopHierarchical CLass
	 			if(isInner)
	 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
	 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
	 						return Syntaxtification.ct.Cvariable.get(ClassIndex).varDT.get(i);
	 	 //ParentClass of Current Class
	 			if(!CurrentPname.equals("-"))
	 			{
	 				return returnTypeVarin_ClassParent(name,CurrentPname);
	 					
	 			} 
	 		 return null;
	 	 }
		private String returnTypeVarin_ClassParent(String name,String Parent)
		 {
			return returnTypeVarindexOfParent(Parent,name);
			
		 }
		private String returnTypeVarindexOfParent(String Parent,String name)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
							 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
							 	return Syntaxtification.ct.Cvariable.get(i).varDT.get(j);
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						 return returnTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
							
					 }
				 }
				
			 return null;
		 }

		private boolean isArrayType(String name) //Method to call
	 	 {
			 if(tv.varDT.size()>0) //Current class;
	 		 for(int i=0;i<tv.varTable.size();i++)
	 			 if(tv.varTable.get(i).equals(name))
	 				 return (tv.isVarArrayTable.get(i));
	 				 
			//TopHierarchical CLass
	 			if(isInner)
	 				for(int i=0;i<Syntaxtification.ct.Cvariable.get(ClassIndex).varScope.size();i++) 
	 					if(Syntaxtification.ct.Cvariable.get(ClassIndex).varTable.get(i).equals(name))
	 						return Syntaxtification.ct.Cvariable.get(ClassIndex).isVarArrayTable.get(i);
	 	 //ParentClass of Current Class
	 			if(!CurrentPname.equals("-"))
	 			{
	 				return isArrayTypeVarin_ClassParent(name,CurrentPname);
	 					
	 			} 
	 		 return false;
	 	 }
		private boolean isArrayTypeVarin_ClassParent(String name,String Parent)
		 {
			return isArrayTypeVarindexOfParent(Parent,name);
			
		 }
		private boolean isArrayTypeVarindexOfParent(String Parent,String name)
		 {
			 for(int i=0;i<Syntaxtification.ct.Cname.size();i++)
				 if(Syntaxtification.ct.Cname.get(i).equals(Parent))	
				 {
					 for(int j=0;j<Syntaxtification.ct.Cvariable.get(i).varScope.size();j++)
							 if(Syntaxtification.ct.Cvariable.get(i).varTable.get(j).equals(name))
							 	return Syntaxtification.ct.Cvariable.get(i).isVarArrayTable.get(j);
					 //Didn't find now look in its parent
					 if(!Syntaxtification.ct.Pname.get(i).equals("-"))
					 {
						 return isArrayTypeVarindexOfParent(Syntaxtification.ct.Pname.get(i),name);
							
					 }
				 }
				
			 return false;
		 }

		}

//isArrayType

//EXP Semantic if END

}
//Class End

//---------------------------------------------------------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------------------------------------------------------//
