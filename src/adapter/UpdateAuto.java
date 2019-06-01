/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package adapter;

import java.util.LinkedHashMap;

import util.File;
import model.*;

public interface UpdateAuto {
	LinkedHashMap<String, CustomAutomobile> UpdateCustomAuto(LinkedHashMap<String, CustomAutomobile> customauto, CustomAutomobile custom, File file);
	LinkedHashMap<String, CustomAutomobile> DeleteCustomAuto(LinkedHashMap<String, CustomAutomobile> customautos);
}
