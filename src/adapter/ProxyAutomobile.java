/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package adapter;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import model.Automobile;
import model.CustomAutomobile;
import exception.AutoException;
import util.File;

/*
 * This abstract class handles the implementation of the LinkedHashMap CustomAutomobiles and provides class methods to create, update (edit),
 * delete, print, serialize to file, and deserialize from file the object. There are other class methods to handle other objects like
 * the standard Automobile object as well as its sub-objects: ArrayList OptionSet and ArrayList Option (inner class)
 * Since this class cannot be instantiated, BuildAutomobile class extends this and that would be instantiated instead. 
 * Additionally, BuildAutomobile class is the one to implement any interfaces and ProxyAutomobile is the one to either
 * define them as class methods or instantiate them (hence why BuildAutomobile is an empty class). 
 */
public abstract class ProxyAutomobile implements GetAuto {
	//variable declarations
	File file;
	LinkedHashMap<String, CustomAutomobile> customautos;
	Scanner in = new Scanner(System.in);
		
	//constructor #1
	protected ProxyAutomobile() {
		file = new File(); //create a static automobile object within the File class. 
		//This automobile object will contain all the options & optionset from file for user selection.
		customautos = new LinkedHashMap<String, CustomAutomobile>(); //create a LinkedHashMap Object of Custom Automobiles objects.
		//Allows customization for each Automobile object should the user desires to.
	}
	
	//constructor #2
	protected ProxyAutomobile(File file, LinkedHashMap<String, CustomAutomobile> customautos) {
		this.file = file;
		this.customautos = customautos;
	}	
		
	//***********************************************
	//*getters and setters for class ProxyAutomobile*
	//***********************************************

	public LinkedHashMap<String, CustomAutomobile> GetCustomAuto() {
		return customautos;
	}
		
	public void SetCustomAuto(LinkedHashMap<String, CustomAutomobile> customautos) {
		this.customautos = customautos;
	}
		
	public File GetFile() {
		return file;
	}
		
	public void SetFile(File file) {
		this.file = file;
	}
	
	//***********************************************
	//*other class methods for class ProxyAutomobile*
	//***********************************************
	
	/*
	 * this class method accepts a Properties object and a CustomAutomobile object to be converted to. This class method essentially
	 * parse the contents of the Properties file and transfer each content to each respective CustomAutomobile field.
	 * This class method currently does not support Properties object with more than five Option values so
	 * standard Automobile OptionSet and Option format is used here to parse, transfer, and convert.
	 */
	public CustomAutomobile ConvertPropertiesToCustomAuto(Properties props) {
		CustomAutomobile customauto = new CustomAutomobile();
    	customauto.SetAutoName(props.getProperty("CarModel"));
    	
    	//for loop to convert each value of a Properties Object into a Custom Automobile
    	for(int i = 0; i < 5; i++) {
    		customauto.AddOptionSet((props.getProperty("Option" + (i + 1))));
    		if(i == 0) {
    			for(int j = 0; j < 10; j++)
    				customauto.GetOptionSets().get(i).AddOption(props.getProperty("OptionValue" + (i + 1) + (char) (97 + j)));
    		}
    		
    		if(i == 1) {
    			for(int j = 0; j < 2; j++)
    				customauto.GetOptionSets().get(i).AddOption(props.getProperty("OptionValue" + (i + 1) + (char) (97 + j)));
    		}
    		
    		if(i == 2) {
    			for(int j = 0; j < 3; j++)
    				customauto.GetOptionSets().get(i).AddOption(props.getProperty("OptionValue" + (i + 1) + (char) (97 + j)));
    		}
    		
    		if(i == 3) {
    			for(int j = 0; j < 2; j++)
    				customauto.GetOptionSets().get(i).AddOption(props.getProperty("OptionValue" + (i + 1) + (char) (97 + j)));
    		}
    		
    		if(i == 4) {
    			for(int j = 0; j < 2; j++)
    				customauto.GetOptionSets().get(i).AddOption(props.getProperty("OptionValue" + (i + 1) + (char) (97 + j)));
    		}
    	}
    
    	return customauto;
	}
			
	//class method to create a Custom Automobile from the pool of existing OptionSets and Options
	//Version 2: Modified some code to synchronize with Assignment 5's criteria. The user will create a Custom Automobile Object based on the
	//OptionSets and Options it carries. Some of the code are omitted out for this assignment only.
	public CustomAutomobile CreateCustomAutoV2(CustomAutomobile create) {
			
		//variable declarations
		int input = 0; 
		int optionsize;
		
		/*
		System.out.printf("Please enter the name of your Custom Automobile: ");
		create.SetAutoName(in.nextLine());
		System.out.printf("Please enter the base price of your Custom Automobile: ");
		create.SetBasePrice(in.nextDouble());
		create.SetTotalPrice(create.GetBasePrice());
		*/
				
		System.out.printf("Please select one Option from each OptionSet when customizing your own automobile.\n\n");
				
		//for loop along with a nested do while loop to input a choice among the Options in each Option 
		//AND traverse and print through the Options in each OptionSet individually
		for(int i = 0; i < create.GetOptionSets().size(); i++) {
			//initializing optionsize to the size of options per optionset to shorten the length of code
			optionsize = create.GetOptionSets().get(i).GetOptions().size();
			create.PrintOptionSetName(i);
			if(optionsize == 0) //if there's an optionset (whether user-defined or initial) that contains no existing options
				System.out.printf("Unfortunately this OptionSet is empty at the moment.\n\n"); //skips it (do not add it into Custom Automobile) and display this message instead
			else {
				//file.GetAutomobile().AddOptionSet(file.GetAutomobile().GetOptionSets().get(i).GetOptionSetName()); //add the OptionSet name from file class to a Custom Automobile object
				create.PrintOptions(i); //print the options in the current OptionSet
				//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
				do {
					System.out.printf("\nEnter the numbered choice of your desired option: ");
					try {
						input = in.nextInt() - 1;
					} catch(InputMismatchException e) {
						input = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
						in.nextLine(); //clears keyboard buffer
					}
						
					if(input < 0 || input >= optionsize)
						System.out.printf("Invalid input. Please input an appropriate number.\n");
						
				} while(input < 0 || input >= optionsize);
				//The input will act like an index to get to the correct option in the ArrayList(s) and will be used to set the Option Choice to that value
				create.SetOptionChoice(i, (create.GetOptionSets().get(i).GetOptions().get(input)));
				//Somewhat similar to the method call above except that the current total price will be added with the Option Choice's price
				create.SetTotalPrice(create.GetTotalPrice() + create.GetOptionSets().get(i).GetOptions().get(input).getPrice());
					
				System.out.printf("Option saved.\n\n");
			}
		}
				
		System.out.printf("Custom Automobile has been successfully created!\n");
				
		return create;
	}
		
	//class method to prompt the user to reinput the data for the Custom Automobile of their choice. The user will make any notable changes 
	//to the data and will overwrite the previous Custom Automobile with the same key. Hence the Custom Automobile will be updated.
	public LinkedHashMap<String, CustomAutomobile> UpdateCustomAuto(LinkedHashMap<String, CustomAutomobile> customauto, CustomAutomobile custom, File file) {
			
		//variable declarations
		Set<String> set = customautos.keySet(); //Set object is equal to the set of keys in the Custom Automobiles object
		Iterator<String> iterate = set.iterator(); //generic is set to String because the keys are defined as Strings
		int input = 0;
			
		if(customautos.isEmpty()) //if Custom Automobile is empty, skip everything and end this menu option
			System.out.printf("There are currently no Custom Automobiles! Please try again later!\n\n");
		else {
			System.out.printf("Current Custom Automobiles in the list:\n");
				
			while(iterate.hasNext()) //while there are still elements left in the LinkedHashMap
				System.out.printf(iterate.next() + "\n"); //print out the current amount of existing Custom Automobiles in the LinkedHashMap
			
			//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
			do {
				System.out.printf("Which Custom Automobile do you want to update? (enter the model number only): ");
				try {
					input = in.nextInt();
				} catch(InputMismatchException e) {
					input = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
					in.nextLine(); //clears keyboard buffer
				}
				
				if(input < 0 || input > customauto.size())
					System.out.printf("Invalid input. Please input an appropriate number.\n");
			} while(input < 0 || input > customauto.size());
			
			//prints out the Custom Automobile that the user selected for updating
			System.out.printf("\nHere is the data of your selected Custom Automobile:\n");
			System.out.printf("---------------------------------------\n");
			customautos.get("Model #" + input).PrintAutoData();
			System.out.printf("---------------------------------------\n");
			for(int i = 0; i < customautos.get("Model #" + input).GetOptionSets().size(); i++) {
				customautos.get("Model #" + input).PrintOptionSetName(i);
				customautos.get("Model #" + input).PrintChoices(i);
				System.out.printf("---------------------------------------\n");
			}
			
			System.out.printf("\nWe will now commence the update to the chosen Custom Automobile. You will be directed to the create a new Custom Automobile\n");
			System.out.printf("menu option but the options you choose will be saved onto the Custom Automobile you selected, thus updating it.\n\n");
			
			//call the method to create a Custom Automobile and save that custom Automobile in the same key that the previous Custom Automobile had
			customautos.put("Model #" + input, CreateCustomAutoV2(custom)); //this will overwrite the previous Custom Automobile
			
			System.out.printf("Ignore the message above. Custom Automobile has been updated!\n\n");
		}
		return customautos;
	}
		
	//class method to delete a Custom Automobile of the user's choice in the HashLinkedMap. The Model # will be intact
	//should the user delete a Custom Automobile that is not the first element or the last element.
	public LinkedHashMap<String, CustomAutomobile> DeleteCustomAuto(LinkedHashMap<String, CustomAutomobile> customautos) {
			
		//variable declarations
		Set<String> set = customautos.keySet(); //Set object is equal to the set of keys in the Custom Automobiles object
		Iterator<String> iterate = set.iterator(); //generic is set to String because the keys are defined as Strings
		int input = 0;
			
		if(customautos.isEmpty()) //if Custom Automobile is empty, skip everything and end this menu option
			System.out.printf("There are currently no Custom Automobiles! Please try again later!\n\n");
		else {
			System.out.printf("Current Custom Automobiles in the list:\n");
			while(iterate.hasNext())
				System.out.printf(iterate.next() + "\n");
			
			//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
			do {
				System.out.printf("Which Custom Automobile do you want to delete? (enter the model number only): ");
				try {
					input = in.nextInt();
				} catch(InputMismatchException e) {
					input = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
					in.nextLine(); //clears keyboard buffer
				}
				
				if(input < 0 || input > customautos.size())
					System.out.printf("Invalid input. Please input an appropriate number.\n");
			} while(input < 0 || input > customautos.size());
				
			customautos.remove("Model #" + input);
			
			System.out.printf("Custom Automobile has been removed!\n\n");
		}	
		return customautos;
	}
		
	//class method to print the contents of LinkedHashMap object individually by utilizing the Set and Iterator objects
	public void PrintCustomAuto(LinkedHashMap<String, CustomAutomobile> customautos) {
			
		//variable declaration
		Set<String> set = customautos.keySet(); //Set object is equal to the set of keys in the Custom Automobiles object
		Iterator<String> iterate = set.iterator(); //generic is set to String because the keys are defined as Strings
		String traverse;
			
		if(customautos.isEmpty()) //if Custom Automobile is empty, skip everything and end this menu option
			System.out.printf("There are currently no Custom Automobiles! Please try again later!\n\n");
		else {
			System.out.printf("---------------------------------------\n");
		
			//while there are still elements left in the LinkedHashMap
			while(iterate.hasNext()) {
				traverse = iterate.next(); //holds the current key and use that key to access the current element's data
				System.out.printf(traverse + ":\n");
				customautos.get(traverse).PrintAutoData();
				System.out.printf("---------------------------------------\n");
				for(int i = 0; i < customautos.get(traverse).GetOptionSets().size(); i++) { //nested for loop to print out the options in each OptionSet object
					customautos.get(traverse).PrintOptionSetName(i);
					customautos.get(traverse).PrintChoices(i);
					System.out.printf("---------------------------------------\n");
				}
				System.out.printf("\n");
			}
		}
	}
	
	//overloaded class method to print the contents of an individual Custom Automobile object instead of the entire LHM
	public void PrintCustomAuto(CustomAutomobile customauto) {
		
		if(customauto.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			System.out.printf("---------------------------------------\n");
			customauto.PrintAutoData();
			System.out.printf("---------------------------------------\n");
			
			for(int i = 0; i < customauto.GetOptionSets().size(); i++) { // for loop to print out the options in each OptionSet object
				System.out.printf("%s - ", i + 1);
				customauto.PrintOptionSetName(i);
				customauto.PrintOptions(i);
				System.out.printf("---------------------------------------\n");
			}
		}
	}
		
	//class method to print the contents of OptionSets and Options objects in the Automobile object (located in the File class) to screen
	public void PrintOptionSetAndOption(Automobile automobile) {
			
		if(automobile.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			System.out.printf("---------------------------------------\n");
			automobile.PrintAutoData();
			System.out.printf("---------------------------------------\n");
			
			for(int i = 0; i < automobile.GetOptionSets().size(); i++) { // for loop to print out the options in each OptionSet object
				System.out.printf("%s - ", i + 1);
				automobile.PrintOptionSetName(i);
				automobile.PrintOptions(i);
				System.out.printf("---------------------------------------\n");
			}
		}
	}
	
	//class method to serialize the LinkedHashMap CustomAutomobiles to disk
	public void SaveCustomAuto(int key, LinkedHashMap<String, CustomAutomobile> customautos) throws AutoException {
		String filename = new String("");
			
		in.nextLine(); //clears keyboard buffer
		
		//first prompts user for filename before serialization. There are several tools to fix errors otherwise.
		System.out.printf("Please enter the file name of your choice (INCLUDE FILE EXTENSION .txt): ");
		filename = in.nextLine();
			
		//if the user does not include a file extension, this if statement will automatically append it to the user's input
		if(filename.endsWith(".txt") == false)
			filename = filename.concat(".txt");
			
		if(filename.substring(filename.indexOf(".")).equalsIgnoreCase(".txt")) {
			file.SaveCustomAutomobile(customautos, filename);
			file.SaveKey(key);
		}
		else
			throw new AutoException(filename);
	}
	
	//class method to serialize the Automobile's OptionSets and Options to disk
	public void SaveOptionSetAndOption(File file) {
		file.Serialize(file.GetAutomobile());
	}
	
	//class method to deserialize the contents from disk and load them into the current LinkedHashMap CustomAutombiles object
	public LinkedHashMap<String, CustomAutomobile> LoadCustomAuto(File file, LinkedHashMap<String, CustomAutomobile> customautos) {
		String filename = new String("");
			
		in.nextLine();
			
		//first prompts user for filename before serialization. There are several tools to fix errors otherwise.
		System.out.printf("Please enter the file name of your choice (INCLUDE FILE EXTENSION .txt): ");
		filename = in.nextLine();
			
		//if the user does not include a file extension, this if statement will automatically append it to the user's input
		if(filename.endsWith(".txt") == false)
			filename = filename.concat(".txt");
			
		if(filename.substring(filename.indexOf(".")).equalsIgnoreCase(".txt"))
			customautos = file.LoadCustomAutomobile(customautos, filename);
		else
			System.out.printf("Your filename input is invalid. Please try again later!\n\n");
			
		return customautos;
	}
		
	//class method to deserialize the Automobile's OptionSets and Options from disk and load them in the current objects
	public Automobile LoadOptionSetAndOption(File file) {
		file.SetAutomobile(file.DeSerialize(file.GetAutomobile()));
			
		return file.GetAutomobile();
	}
		
	//loads current value of key from file so key counter doesn't start counting from zero every time this program starts up
	@SuppressWarnings("unused")
	private int LoadKey(File file, int key) {
		return file.LoadKey(key);
	}
}