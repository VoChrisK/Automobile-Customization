/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package exception;

import java.util.LinkedHashMap;
import java.util.Scanner;

import util.File;
import model.Automobile;
import model.CustomAutomobile;

@SuppressWarnings("serial")
public class AutoException extends Exception {
	private String errormsg;
	private String filename;
	CustomAutomobile customauto;
	Scanner in = new Scanner(System.in);
	
	public AutoException() {
		errormsg = new String("");
		filename = new String("");
		customauto = new CustomAutomobile();
	}
	
	public AutoException(String filename) {
		errormsg = new String("");
		this.filename = filename;
		customauto = new CustomAutomobile();
	}
	public AutoException(CustomAutomobile customauto) {
		errormsg = new String("");
		filename = new String("");
		this.customauto = customauto;
	}
	
	private void Display() {
		System.out.printf(errormsg);
	}
	
	/*This custom exception handler class method prevents users from saving the Automobile object with no data.
	 * It will prompt the user to create at least one new OptionSet and Option before saving again */
	public void CannotSaveEmptyAutomobileException(Automobile automobile) {
		if(automobile.GetOptionSets().isEmpty()) {
			errormsg = "ERROR: You cannot have empty option(s) before saving to disk.\n";
			Display();
			
			System.out.printf("You must have at least one OptionSet!\n");
			System.out.printf("Please enter a name for a new OptionSet: ");
			
			in.nextLine(); //clears keyboard buffer
			
			automobile.AddOptionSet(in.nextLine());
			
			in.nextLine(); //clears keyboard buffer
			
			System.out.printf("Now you must have at least one Option!\n");
			System.out.printf("Please enter a name for a new Option: ");
			
			String name = in.nextLine();
			
			System.out.printf("Please enter a price for a new Option: ");
			automobile.GetOptionSets().get(0).AddOption(name, in.nextDouble());
		}
		
		System.out.printf("You have filled at least one OptionSet and one Option. Please select this option again to save.\nRemember, if you intentionally delete "
				+ "them before you save again, you'll be prompted on this procedure again!\n");
	}
	
	/*This custom exception handler fixes the negative base price from the user's input. Once it sets the price to
	 * its positive equivalent, it will return the price and the method(s) that uses it will be run again*/
	public CustomAutomobile NegativeBasePriceException() {
		errormsg = "ERROR: You cannot have a negative base price value.\n";
		Display();
		
		System.out.printf(" Your base price will automatically be changed to positive.\n\n");
		
		//subtract the negative value with itself three times to get the positive version of the exact value
		customauto.SetBasePrice(customauto.GetBasePrice() - customauto.GetBasePrice() - customauto.GetBasePrice());
		
		return customauto;
	}
	
	/*This custom exception handler will manually set every options in accordance to the correct data in Options.txt
	 * The file format suppose to have a number for the optionset size first, a number for the option size second, the optionset name third, and options' name and price last
	 * Only an incorrect optionset size and/or option size will enact this custom exception handler. The other data are fixed from the class method itself.*/
	public Automobile IncorrectTextFileFormatException() {
		
		errormsg = "ERROR: Text file cannot be read into the Automobile Object due to incompatibility. Manually setting all options to the correct data...\n";
		Display();
		
		Automobile newAuto = new Automobile();
		
		newAuto.AddOptionSet("Colors");
		newAuto.GetOptionSets().get(0).AddOption(" Fort Knox Gold Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Liquid Grey Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Infra-Red Clearcoat", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Grabber Green 0 Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Sangria Red Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" French Blue Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Twilight Blue 0 Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" CD Silver Clearcoat Metallic", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Pitch Black Clearcoat", 0.00);
		newAuto.GetOptionSets().get(0).AddOption(" Cloud 9 White Clearcoat", 0.00);
		newAuto.AddOptionSet("Tranmission");
		newAuto.GetOptionSets().get(1).AddOption(" Automatic", 0.00);
		newAuto.GetOptionSets().get(1).AddOption(" Standard", -815.00);
		newAuto.AddOptionSet("Brakes/Traction Control");
		newAuto.GetOptionSets().get(2).AddOption(" Standard", 0.00);
		newAuto.GetOptionSets().get(2).AddOption(" ABS", 400.00);
		newAuto.GetOptionSets().get(2).AddOption(" ABS with Advance Trac", 1625.00);
		newAuto.AddOptionSet("Side Impact Air Bags");
		newAuto.GetOptionSets().get(3).AddOption(" None", 0.00);
		newAuto.GetOptionSets().get(3).AddOption(" Selected", 350.00);
		newAuto.AddOptionSet("Power Moonroof");
		newAuto.GetOptionSets().get(4).AddOption(" None", 0.00);
		newAuto.GetOptionSets().get(4).AddOption(" Selected", 595.00);
		
		return newAuto;
	}
	
	/*This custom exception handler will correct the file extension that the user had input.
	 * It will automatically delete the extension the user input and appends .txt to the end of the String
	 * This exception handler only works with saving Custom Automobiles to disk at the moment*/
	public void InvalidFileExtensionException(File file, int key, LinkedHashMap<String, CustomAutomobile> customautos) {
		errormsg = "ERROR: The file extension that came with your input is invalid and therefore cannot save Custom Automobiles to it.\n";
		Display();
		
		//this statement removes and replaces the invalid file extension to .txt
		filename = filename.substring(0, filename.indexOf(".")).concat(".txt");
		
		//if/else statement is used here to prevent infinite looping if the statement above stopped working properly
		if(file.SaveCustomAutomobile(customautos, filename)) {
			System.out.printf("File name has been modified to have '.txt' in the end. Save has been completed.\n");
		}
		else
			System.out.printf("ERROR: File name is still invalid. Please try again later.\n");
	}
}
