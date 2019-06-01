/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package server;

import java.util.Properties;

import model.CustomAutomobile;

public interface AutoServer {
	CustomAutomobile ConvertPropertiesToCustomAuto(Properties props);
}
