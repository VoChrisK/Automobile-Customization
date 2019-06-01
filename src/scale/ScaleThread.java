/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package scale;

import model.CustomAutomobile;

public interface ScaleThread {
	CustomAutomobile CreateOptionSet(CustomAutomobile customauto);
	CustomAutomobile CreateOption(CustomAutomobile customauto);
	CustomAutomobile UpdateOptionSet(CustomAutomobile customauto);
	CustomAutomobile UpdateOption(CustomAutomobile customauto);
	CustomAutomobile DeleteOptionSet(CustomAutomobile customauto);
	CustomAutomobile DeleteOption(CustomAutomobile customauto);
}
