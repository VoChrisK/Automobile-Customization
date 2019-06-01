/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package adapter;

import java.util.LinkedHashMap;

import model.CustomAutomobile;

//this interface calls in an instance of the LinkedHashMap CustomAutomobiles through the getter/setter methods for other classes in
//other packages to use whenever this interface is instantiated
public interface GetAuto {
	void SetCustomAuto(LinkedHashMap<String, CustomAutomobile> customautos);
	LinkedHashMap<String, CustomAutomobile> GetCustomAuto();
}