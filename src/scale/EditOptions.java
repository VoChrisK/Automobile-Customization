/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package scale;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

import util.File;
import model.Automobile;
import model.CustomAutomobile;
import adapter.GetAuto;
import adapter.ProxyAutomobile;

public class EditOptions extends ProxyAutomobile implements ScaleThread, GetAuto /*implements Runnable*/ {
	
	//variable declarations
	Scanner in = new Scanner(System.in);
	int input;
	CustomAutomobile temp;

	//constructor #1
	public EditOptions() {
		super();
		input = 0;
		temp = new CustomAutomobile();
	}
	
	//constructor #2
	public EditOptions(File file, LinkedHashMap<String, CustomAutomobile> customautos) {
		super(file, customautos);
		input = 0;
		temp = new CustomAutomobile();
	}
	
	//class method to prompt the user to create a new OptionSet in a Custom Automobile
	public CustomAutomobile CreateOptionSet(CustomAutomobile customauto) {
		
		System.out.printf("Please enter the name of the new OptionSet: ");
		in.nextLine(); //clears keyboard buffer
		customauto.AddOptionSet(in.nextLine());
		System.out.printf("New OptionSet saved.\n");
				
		return customauto;
	}
	
	//class method to prompt the user to create a new Option in desired OptionSet in a Custom Automobile
	public CustomAutomobile CreateOption(CustomAutomobile customauto) {
				
		//variable declaration
		int index = 0;
				
		System.out.printf("\nList of current OptionSets:\n");
				
		//displays existing OptionSet
		for(int i = 0; i < customauto.GetOptionSets().size(); i++) {
			System.out.printf("%s: ", i + 1);
			customauto.PrintOptionSetName(i);
		}
				
		//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
		do {
			System.out.printf("Where do you want to store the new Option (give the number)?: ");
			try {
				index = in.nextInt() - 1;
			} catch(InputMismatchException e) {
				index = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
				in.nextLine(); //clears keyboard buffer
			}

			if(index < 0 || index >= customauto.GetOptionSets().size())
				System.out.printf("Invalid input. Please input an appropriate number.\n");
		} while(index  < 0 || index >= customauto.GetOptionSets().size());
				
		in.nextLine(); //clears keyboard buffer
				
		System.out.printf("Enter the name of the new Option: ");
		String name = " " + in.nextLine();
			
		System.out.printf("Enter the price of the new Option: ");
		customauto.GetOptionSets().get(index).AddOption(name, in.nextDouble()); //passing the user-defined name and price will instantiate a new Option within the method
		//index is used to locate the correct OptionSet, hence the reason for the user's input
				
		System.out.printf("New Option saved.\n\n");
				
		return customauto;
	}
	
	//class method update the name of the OptionSet in a Custom Automobile using the user's input.
	public CustomAutomobile UpdateOptionSet(CustomAutomobile customauto) {
				
		//variable declaration
		int input = 0;
				
		if(customauto.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			//prints out the Optionsets' names only
			System.out.printf("Current OptionSet in the list:\n");
			for(int i = 0; i < customauto.GetOptionSets().size(); i++) {
				System.out.printf("%s - ", i + 1);
				customauto.PrintOptionSetName(i);
			}
				
			//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
			do {
				System.out.printf("Which OptionSet do you want to update? (enter the number only): ");
				try {
					input = in.nextInt() - 1;
				} catch(InputMismatchException e) {
					input = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
					in.nextLine(); //clears keyboard buffer	
					if(input < 0 || input >= customauto.GetOptionSets().size())
						System.out.printf("Invalid input. Please input an appropriate number.\n");
				}
			} while(input < 0 || input >= customauto.GetOptionSets().size());
				
			in.nextLine(); //clears keyboard buffer
			
			//input is used to traverse to a specific OptionSet. That OptionSet will be changed to the user's input.
			System.out.printf("Please enter a new name of OptionSet: ");
			String name = " " + in.nextLine();
			customauto.GetOptionSets().get(input).SetOptionSetName(name);
			System.out.printf("OptionSet has been updated!\n\n");
		}
		
		return customauto;
	}
	
	//class method to update the option name and price using the user's input. 
	public CustomAutomobile UpdateOption(CustomAutomobile customauto) {
				
		//variable declaration
		int input1 = 0; //to traverse through the ArrayList of OptionSets
		int input2 = 0; //to traverse through the ArrayList of Options
				
		if(customauto.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
			System.out.printf("Current Options in each OptionSet in the list:\n");
			for(int i = 0; i < customauto.GetOptionSets().size(); i++) {
				customauto.PrintOptionSetName(i);
				customauto.PrintOptions(i);
			}
			
			do {
				System.out.printf("Which OptionSet is your desired option located? (enter the number only): ");
				try {
					input1 = in.nextInt() - 1;
				} catch(InputMismatchException e) {
					input1 = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
					in.nextLine(); //clears keyboard buffer
						
					if(input1 < 0 || input1 >= customauto.GetOptionSets().size())
						System.out.printf("Invalid input. Please input an appropriate number.\n");
				}
			} while(input1 < 0 || input1 >= customauto.GetOptionSets().size());
				
					
			if(customauto.GetOptionSets().get(input1).GetOptions().isEmpty()) //if Option in a specific OptionSet is empty, skip everything and end this menu option
				System.out.printf("There are currently no options in this OptionSet! Please try again later!\n\n");
			else {
				do {
					//do while loop specifically for user's input. Two techniques to solve errors are used within this loop (InputMismatchException & ArrayOutOfBoundsException)
					System.out.printf("Which Option do you want to update? (enter the number only): ");
					try {
						input2 = in.nextInt() - 1;
					} catch(InputMismatchException e) {
						input2 = -1; //initialize input to -1 if user inputted an incorrect value, continuing the loop
						in.nextLine(); //clears keyboard buffer
					}
						
					if(input2 < 0 || input2 >= customauto.GetOptionSets().get(input1).GetOptions().size())
								System.out.printf("Invalid input. Please input an appropriate number.\n");
				} while(input2 < 0 || input2 >= customauto.GetOptionSets().get(input1).GetOptions().size());
					
				in.nextLine(); //clears keyboard buffer
				System.out.printf("Enter the name of the updated option: ");
				String name = " " + in.nextLine();
				customauto.GetOptionSets().get(input1).GetOptions().get(input2).setName(name);
				System.out.printf("Enter the price of the updated option: ");
				customauto.GetOptionSets().get(input1).GetOptions().get(input2).setPrice(in.nextDouble());
				System.out.printf("Option has been updated!\n\n");
			}	
		}
		return customauto;
	}
	
	//class method to delete an existing OptionSet according to the user's choice. There is a class method in Automobile class that
	//deletes the target but the user has to input the name of the target instead of the number choice
	public CustomAutomobile DeleteOptionSet(CustomAutomobile customauto) {
				
		if(customauto.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			System.out.printf("Current OptionSet in the list:\n");
			for(int i = 0; i < customauto.GetOptionSets().size(); i++) {
				System.out.printf("%s - ", i + 1);
				customauto.PrintOptionSetName(i);
			}
				
			System.out.printf("Please enter the name of the OptionSet you want to delete: ");
				
			in.nextLine(); //clears keyboard buffer
			
			if(customauto.DeleteOptionSet(in.nextLine())) //if the class method returns true then display following message
				System.out.printf("Target has been deleted!\n\n");
			else
				System.out.printf("Target has not been been deleted! Please try again later!\n\n");
		}	
		return customauto;
	}
	
	//class method to delete an existing Option according to the user's choice There is a class method in Automobile class that
	//deletes the target but the user has to input the name of the target instead of the number choice
	public CustomAutomobile DeleteOption(CustomAutomobile customauto) {
				
		//variable declaration
		int index = 0;
		String input = new String("");
				
		if(customauto.GetOptionSets().isEmpty()) //if OptionSet is empty, skip everything and end this menu option
			System.out.printf("There are currently no OptionSets! Please try again later!\n\n");
		else {
			System.out.printf("Current Options in each OptionSet in the list:\n");
			PrintOptionSetAndOption(customauto);
				
			do {
				System.out.printf("Which OptionSet is your desired option located? (enter the number only): ");
				try {
					index = in.nextInt() - 1;
				} catch(InputMismatchException e) {
					index = -1;
					in.nextLine(); //clears keyboard buffer
				}
						
				if(index < 0 || index >= customauto.GetOptionSets().size())
					System.out.printf("Invalid input. Please input an appropriate number.\n");
			} while(index < 0 || index >= customauto.GetOptionSets().size());
				
			if(customauto.GetOptionSets().get(index).GetOptions().isEmpty()) //if Option in a specific OptionSet is empty, skip everything and end this menu option
				System.out.printf("There are currently no options in this OptionSet! Please try again later!\n\n");
			else {
				System.out.printf("Please enter the name of the Option you want to delete: ");
				
				in.nextLine(); //clears keyboard buffer
				
				input = " " + in.nextLine();
				
				if(customauto.GetOptionSets().get(index).DeleteOption(input)) //if the class method returns true then display following message
					System.out.printf("Target has been deleted!\n\n");
				else
					System.out.printf("Target has not been been deleted! Please try again later!\n\n");
			}
		}
		return customauto;
	}
}


