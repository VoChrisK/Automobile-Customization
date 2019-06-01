/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import model.Automobile;
import model.CustomAutomobile;

public class File {
	
	//class variable declarations
	Scanner in = new Scanner(System.in);
	private Automobile automobile;
	
	//Constructor declaration
	public File() {
		automobile = new Automobile("Sample Model");
	}
	
	//************************************
	//*Getters and setters for class File*
	//************************************
	
	public void SetAutomobile(Automobile automobile) {
		this.automobile = automobile;
	}
	
	public Automobile GetAutomobile() {
		return automobile;
	}
	
	//************************************
	//*Other class methods for class File*
	//************************************
		
	//method to read file and store its content in the Automobile object
	/*Update Version 3: Same functionality as Version 2 except code is altered in order to be compatible for ArrayList Object.
	  Additonally, the Options.txt file is updated to have the OptionSet size at the first line*/
	public Automobile buildAutoObjectV3() {
		try {
			//variable declarations
			FileReader file = new FileReader("Options.txt");
			BufferedReader reader = new BufferedReader(file);
			boolean eof = false;
			int optionsetsize;
			int index = 0;
			double price = 0.00;
				
			System.out.printf("NOTICE: Now reading from Options.txt into the Automobile object...\n");
			
			String line = reader.readLine();
			optionsetsize = Integer.parseInt(line);

			while(!eof) {
			
			for(int i = 0; i < optionsetsize; i++) {
				line = reader.readLine();

				if(line == null)
					eof = true;
				else 
					index = Integer.parseInt(line);
						
				line = reader.readLine();
						
				if(line == null)
					eof = true;
				else
					automobile.AddOptionSet(line);
			
				for(int j = 0; j < index; j++) {
					
					line = reader.readLine();

					if(line == null)
						break;
					else {
						StringTokenizer seperate = new StringTokenizer(line);
								
						/*try/catch exception to handle missing option price value. The structure of each option data in Options.txt is price
						 * first then name. If number format error is caught, the line will append 0.00 to the beginning of the
						 * string and it will be read into the options define again.*/
						try {
							price = Double.parseDouble(seperate.nextToken());
						} catch(NumberFormatException e) {
							line = "0.00 " + line;
							seperate = new StringTokenizer(line);
							price = Double.parseDouble(seperate.nextToken());
						}
						finally {
									
						}
								
						/*if/else statement to handle missing option name. If the number of tokens in the StringTokenizer does
						equal to zero, it will concatenate all the tokens, remove the first word of the String (since the first
						word is the option price) and store it into the Automobile object*/
						if(seperate.countTokens() != 0) {
						
							while(seperate.hasMoreTokens()) 
								line.concat(seperate.nextToken());
									
							line = line.substring(line.indexOf(" "));
							line = line.substring(1);
							automobile.GetOptionSets().get(i).AddOption(line, price);
						}
						else { 
							System.out.printf("A specific line in the file Options.txt has a missing option name! Please input a new option name: ");
							automobile.GetOptionSets().get(i).AddOption(in.nextLine(), price);
							in.nextLine();
						}			
					}
				}
			}
		}
			
			System.out.printf("\nNOTICE: Reading from file Options.txt is a success!\n");
			reader.close();
		} catch (IOException e) { 
			System.out.printf("ERROR: Cannot read data from Options.txt into the Automobile object. There are currently ZERO OptionSets and Options!\n");
		}
			
	return automobile;
	}
	
	//class method to write the current Automobile object into a file, saving it for later use on startup
	public void Serialize(Automobile automobile) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Automobile.txt"));
			output.writeObject(automobile);
			output.close();
			System.out.printf("NOTICE: Now saving the Automobile Object to a text file through serialization.\n");
		} catch(IOException e) {
			System.out.printf("ERROR: Cannot save the Automobile Object to a text file.\n");
		}
	}
	
	//class method to read a file into the Automobile object and updating it. Thus, the object can be used again on startup
	public Automobile DeSerialize(Automobile automobile) {
		try { //exception handling statements to read the values from the file into the Automobile object, provided if the file is found and the values can be read into the object
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("Automobile.txt"));
			automobile = (Automobile) input.readObject();
			System.out.printf("\nNOTICE: Successfully read Automobile.txt into the current Automobile Object through serialization.\n\n");
			input.close();
		} catch(IOException e) {
			System.out.printf("ERROR: Unable to read Automobile.txt into the current Automobile Object.\n\n");
		} catch(ClassNotFoundException e) {
			System.out.printf("ERROR: Unable to read Automobile.txt into the current Automobile Object.\n\n");
		}
		
		return automobile;
	}
	
	//class method to write the current Custom Automobiles into a file, saving it for later use on startup should the user prefers to
	public boolean SaveCustomAutomobile(LinkedHashMap<String, CustomAutomobile> customautos, String filename) {
		boolean status = false;
		
		try {  //try/catch exceptions to handle invalid filename input
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
			output.writeObject(customautos);
			output.close();
			status = true;
			System.out.printf("NOTICE: Now saving the Custom Automobiles to a text file through serialization.\n");
		} catch(IOException e) {
			System.out.printf("ERROR: Cannot save the Custom Automobiles to a text file.\n");
		}
		
		return status;
	}
	
	//class method to read a file of user's choice into the Custom Automobiles and updating it. Thus, the object can be reused on startup.
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, CustomAutomobile> LoadCustomAutomobile(LinkedHashMap<String, CustomAutomobile> customautos, String filename) {
		try { //try/catch exceptions to handle invalid filename input
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
			customautos = (LinkedHashMap<String, CustomAutomobile>) input.readObject();
			System.out.printf("\nNOTICE: Successfully read " + filename + " into the current Custom Automobiles through serialization.\n\n");
			input.close();
		} catch(IOException e) { //if errors have been caught, the Custom Automobiles will not be read from a file and returns an unchanged object
			System.out.printf("ERROR: Unable to read " + filename + " into the current Custom Automobiles.\n\n");
		} catch(ClassNotFoundException e) {
			System.out.printf("ERROR: Unable to read " + filename + " into the current Custom Automobiles.\n\n");
		}
		
		return customautos;
	}
	
	//class method to essentially parse and serialize the current contents in the Automobile object (which was read from Options.txt)
	//to a new properties file with user-defined filename, content by content.
	public void CreateAPropertiesFile(String filename) {
		
		System.out.printf("\nNOTICE: Commencing conversion of the automobile data from Options.txt to a new properties file.\n");
		
		try {
			PrintWriter output = new PrintWriter(filename);
			output.println("CarModel:" + automobile.GetAutoName());
			
			for(int i = 0; i < automobile.GetOptionSets().size(); i++) {
				output.println("Option" + (i + 1) + "=" + automobile.GetOptionSets().get(i).GetOptionSetName());
				for(int j = 0; j < automobile.GetOptionSets().get(i).GetOptions().size(); j++) {
					output.println("OptionValue" + (i + 1) + (char) (97 + j) + "=" + automobile.GetOptionSets().get(i).GetOptions().get(j).getName());
				}
			}
			System.out.printf("\nNOTICE: Successfully created a new properties file with " + filename + ".\n\n");
			output.close();
		} catch (IOException e) {
			System.out.printf("ERROR: Cannot create a properties file using data from Options.txt!\n");
		}
	}
	
	//class method to parse and read a properties file into a properties object, then return the object
	public Properties ReadPropertiesFile(String filename) {
		Properties props = new Properties();
		try {
			FileInputStream in = new FileInputStream(filename);
			props.load(in); //This loads the entire file in memory.
		} catch (IOException e) {
			System.out.printf("ERROR: Unable to read the properties file into the properties object!\n\n");
			props = null;
		}
		
		return props;
	}
	
	//class method to serialize the key counter to disk
	public void SaveKey(int key) {
		try {  //try/catch exceptions to handle invalid filename input
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("key.txt"));
			output.writeInt(key);
			output.close();
			System.out.printf("NOTICE: Now saving the Key Counter to a text file through serialization.\n");
		} catch(IOException e) {
			System.out.printf("ERROR: Cannot save the Key Counter to a text file.\n");
		}
	}
	
	//class method to deserialize the key counter from disk
	public int LoadKey(int key) {
		try { //try/catch exceptions to handle invalid filename input
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("key.txt"));
			key = (int) input.readInt();
			System.out.printf("\nNOTICE: Successfully read key.txt into the current Key Counter through serialization.\n\n");
			input.close();
		} catch(IOException e) { //if errors have been caught, the Custom Automobiles will not be read from a file and returns an unchanged object
			System.out.printf("ERROR: Unable to read Key.txt into the current Key Counter.\n\n");
		}
		
		return key;
	}
}
