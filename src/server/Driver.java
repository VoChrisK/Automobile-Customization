/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package server;

import java.io.IOException;

import server.BuildCarModelOptions;

public class Driver {

	public static void main(String[] args) throws IOException {
		BuildCarModelOptions server = new BuildCarModelOptions();
		server.ClientServerInteraction();
		server.close();
	}

}
