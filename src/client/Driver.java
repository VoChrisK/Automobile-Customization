/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package client;

import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		CarModelOptionsIO client = new CarModelOptionsIO();
		client.ClientServerInteraction();
		client.close();
	}

}
