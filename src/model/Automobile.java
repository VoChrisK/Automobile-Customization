/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Automobile implements Serializable {
	
	//class variable declarations
	private String autoname;
	private double baseprice;
	private double totalprice;
	private ArrayList<OptionSet> optionsets;
	
	//Constructor #1 declaration
	public Automobile() {
		autoname = new String("");
		baseprice = 0.00;
		totalprice = 0.00;
		optionsets = new ArrayList<OptionSet>();
	}
	
	//Constructor #2 declaration
	public Automobile(String autoname) {
		this.autoname = autoname;
		baseprice = 0.00;
		totalprice = 0.00;
		optionsets = new ArrayList<OptionSet>();
	}
	
	//******************************************
	//*getters and setters for class Automobile*
	//******************************************
	
	public synchronized void SetAutoName(String autoname) {
		this.autoname = autoname;
	}
	
	public synchronized String GetAutoName() {
		return autoname;
	}
	
	public synchronized void SetBasePrice(double baseprice) {
		this.baseprice = baseprice;
	}
	
	public synchronized double GetBasePrice() {
		return baseprice;
	}
	
	public synchronized void SetTotalPrice(double totalprice) {
		this.totalprice = totalprice;
	}
	
	public synchronized double GetTotalPrice() {
		return totalprice;
	}
	
	public synchronized void SetOptionSets(ArrayList<OptionSet> optionsets) {
		this.optionsets = optionsets;
	}
	
	public synchronized ArrayList<OptionSet> GetOptionSets() {
		return optionsets;
	}
	
	//******************************************
	//*other class methods for class Automobile*
	//******************************************
	
	//class method to create a new OptionSet from the String parameter and add that in the ArrayList at the bottom of the list
	public synchronized void AddOptionSet(String name) {
		optionsets.add(new OptionSet(name));
	}
	
	//class method to search a particular element in the ArrayList using the name of the target
	//this method returns an int instead of a boolean to ensure code reusability
	//private is used because there is no reason to use this class method outside of the class at the moment
	private synchronized int SearchOptionSet(String name) {
		int location = -1;
		
		for(int i = 0; i < optionsets.size(); i++) {
			if(name.equalsIgnoreCase(optionsets.get(i).GetOptionSetName())) { //if target is found, set location to that current index and exit out of the loop
				location = i;
				break;
			}
		}
		
		return location;
	}
	
	//class method to delete a particular element in the ArrayList using the name of the target
	public synchronized boolean DeleteOptionSet(String name) {
		boolean status = false;
		int location = SearchOptionSet(name); //call the search method and initialize the variable location with the returning value
		
		if(location != -1) { //if location does not equal to -1, it will pinpoint to the target optionset, delete it, and automatically update the ArrayList
			optionsets.remove(location);
			status = true;
		}
		else
			; //filler code -- do nothing if location equals to -1
		
		return status;
	}
	
	//print methods for each class excluding the Custom Automobile class are all handled in Automobile object
	//Since each OptionSet object in the array have different lengths, print methods are utilized to access each individual option and print each one
	
	public synchronized void PrintAutoData() {
		System.out.printf("Name: %s \n", autoname);
		//System.out.printf("Base Price: $%s \n", baseprice); //omitted
		//System.out.printf("Total Price: $%s \n", totalprice); //omitted
	}
	
	public synchronized void PrintOptionSetName(int index) {
			System.out.printf(optionsets.get(index).GetOptionSetName() + "\n");
	}
	
	public synchronized void PrintOptions(int index) {
		for(int i = 0; i < optionsets.get(index).GetOptions().size(); i++)
			System.out.printf("%s:" +/* $" + optionsets.get(index).GetOptions().get(i).getPrice() + " - " + */optionsets.get(index).GetOptions().get(i).getName() + "\n", i + 1);
			//purposely omit out the price for this assignment
	}

}
