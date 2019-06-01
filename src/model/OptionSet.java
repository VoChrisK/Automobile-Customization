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
public class OptionSet implements Serializable {
	
	//class variable declarations
	private String optionsetname;
	private Option choice;
	private ArrayList<Option> options;
	
	//Constructor #1 declaration
	public OptionSet() {
		optionsetname = new String("");
		choice = new Option();
		options = new ArrayList<Option>();
	}
	
	public OptionSet(String optionsetname) {
		this.optionsetname = optionsetname;
		choice = new Option();
		options = new ArrayList<Option>();
	}
	
	//*****************************************
	//*getters and setters for class OptionSet*
	//*****************************************
	
	public synchronized void SetOptionSetName(String optionname) {
		this.optionsetname = optionname;
	}
	
	public synchronized String GetOptionSetName() {
		return optionsetname;
	}
	
	public synchronized void SetOptions(ArrayList<Option> options) {
		this.options = options;
	}
	
	public synchronized ArrayList<Option> GetOptions() {
		return options;
	}
	
	public synchronized void SetChoice(Option choice) {
		this.choice = choice;
	}
	
	public synchronized Option GetChoice() {
		return choice;
	}
	
	//*****************************************
	//*other class methods for class OptionSet*
	//*****************************************
	
	public synchronized void AddOption(String name) {
		options.add(new Option(name));
	}
	
	public synchronized void AddOption(String name, double price) {
		options.add(new Option(name, price));
	}
	
	public synchronized void AddOption(Option option) {
		options.add(option);
	}
	
	private synchronized int SearchOption(String name) {
		int location = -1;
		
		for(int i = 0; i < options.size(); i++) {
			if(name.equalsIgnoreCase(options.get(i).getName())) {
				location = i;
				break;
			}
		}
		
		return location;
	}
	
	public synchronized boolean DeleteOption(String name) {
		boolean status = false;
		int location = SearchOption(name);
		
		if(location != -1) {
			options.remove(location);
			status = true;
		}
		else
			;
		
		return status;
	}
	
	//inner class
	public class Option implements Serializable {
		
		//class variable declarations
		private String name;
		private double price;
		
		//constructor #1
		protected Option() {
			this.name = new String("Empty");
			this.price = 0.00;
		}
		
		//constructor #2
		protected Option(String name) {
			this.name = name;
			this.price = 0.00;
		}
		
		//constructor #3
		protected Option(String name, double price) {
			this.name = name;
			this.price = price;
		}
		
		//**************************************
		//*getters and setters for class Option*
		//**************************************
		
		public synchronized String getName() {
			return name;
		}

		public synchronized void setName(String name) {
			this.name = name;
		}

		public synchronized double getPrice() {
			return price;
		}

		public synchronized void setPrice(double price) {
			this.price = price;
		}
	}

}
