/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package model;

import model.OptionSet.Option;

//This class extends the Automobile object and provides more data for the object, such as the usage of option choice 
//to keep track of user's inputted choice. This class will be used for a user-defined "Custom" Automobile.
@SuppressWarnings("serial")
public class CustomAutomobile extends Automobile {
	
	public CustomAutomobile() {
		super();
	}
	
	public CustomAutomobile(String autoname) {
		super(autoname);
	}
	
	public synchronized void SetOptionChoice(int index, Option option) {
		GetOptionSets().get(index).SetChoice(option);
	}
	
	public synchronized String GetOptionChoiceName(int index) {
		return GetOptionSets().get(index).GetChoice().getName();
	}
	
	public synchronized double GetOptionChoicePrice(int index) {
		return GetOptionSets().get(index).GetChoice().getPrice();
	}
	
	public synchronized void PrintChoices(int index) {
			System.out.printf(/*"$" + GetOptionChoicePrice(index) + " - " +*/ GetOptionChoiceName(index) + "\n"); //purposely omit out the price for this assignment
	}
}
