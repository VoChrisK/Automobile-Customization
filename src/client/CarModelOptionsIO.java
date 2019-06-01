/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import adapter.BuildAutomobile;
import adapter.CreateAuto;
import model.CustomAutomobile;
import util.File;

//This class is an implementation of the client side of client-server API. PrintWriter, BufferedReader, 
//ObjectInput and ObjectOut Streams are used to communicate with the server
@SuppressWarnings("serial")
public class CarModelOptionsIO implements Serializable {
	
	//class variable declarations
	static final int port = 4444;
	Scanner in = new Scanner(System.in);
	Socket client = null;
	PrintWriter write = null;
	ObjectInputStream input = null;
	ObjectOutputStream output = null;
	BufferedReader receive = null;
	String choice;
	Properties props;
	File file;
	CustomAutomobile customauto;
	
	//constructor to create a socket and try to connect to the server
	public CarModelOptionsIO() {
    	try {
    		client = new /*DefaultSocketClient*/ Socket(InetAddress.getLocalHost(), port);
    		//client.run();
            write = new PrintWriter(client.getOutputStream(), true);
            input = new ObjectInputStream(client.getInputStream());
            output = new ObjectOutputStream(client.getOutputStream());
            receive = new BufferedReader(new InputStreamReader(client.getInputStream()));
    		System.err.printf("Success! Connected to server!\n");
    	} catch(IOException e) {
    		System.err.printf("ERROR: Unable to connect to server.\n");
    		System.exit(1);
    	}
    	
    	choice = new String();
    	file = new File();
	}
	
	//class method to close the specified objects properly
	public void close() throws IOException {
    	write.close();
    	input.close();
    	output.close();
    	client.close();
	}
	
	//class method to handle the whole client server interaction. This class method stimulates a menu interface,
	//and the server follow suit in its own code
	public void ClientServerInteraction() throws IOException {
		//at the beginning of the menu interface, create the Automobile object from Options.txt, and serialize it to a new
		//properties file. Prompts user to enter a filename of the new properties file.  
		file.buildAutoObjectV3();
    	System.out.printf("\nNOTICE: Before you start the menu for Automobile Customization, please enter the filename of the properties file (include .txt): ");
    	file.CreateAPropertiesFile(in.nextLine());
    	System.out.printf("\nNOTICE: Properties file based on Options.txt's data have been successfully created and uploaded!\n");
    	
    	//header
    	System.out.printf("\n********************************************************\n");
    	System.out.printf("*  Welcome to Assignment 6 - Automobile Customization  *\n");
    	System.out.printf("********************************************************\n\n");
    	System.out.printf("    ***************************************\n");
    	System.out.printf("    *          Assignment 6 Menu          *\n");
    	System.out.printf("    ***************************************\n");
    	System.out.printf("    * a - Upload a Properties File        *\n");
    	System.out.printf("    * b - Configure a Car                 *\n");
    	System.out.printf("    * c - Quit                            *\n");
    	System.out.printf("    ***************************************\n\n");
    	
    	do {   		
			//exception handling case here in case the user inputs an invalid choice
			try {
				System.out.printf("Enter a choice: ");
				choice = in.nextLine();
			} catch(InputMismatchException e) {
				choice = "d"; //initialize choice to a value that is not in a case clause to notify the user that he/she input an invalid choice
				in.nextLine(); //clears keyboard buffer
			}
			
			write.println(choice); //send the user-inputted menu choice to the server, and the server will act accordingly to the menu selection
    		
    		switch(choice) {
    			case "z":
    				System.out.printf("    ***************************************\n");
    				System.out.printf("    *          Assignment 6 Menu          *\n");
    				System.out.printf("    ***************************************\n");
    				System.out.printf("    * 1 - Upload a Properties File        *\n");
    				System.out.printf("    * 2 - Configure a Car                 *\n");
    				System.out.printf("    * 3 - Quit                            *\n");
    				System.out.printf("    ***************************************\n\n");
    				break;
    			case "a":
    				props = null;
    				System.err.println(receive.readLine());
    				System.out.printf("Enter the name of the desired properties file (include .txt extension): ");
    				props = file.ReadPropertiesFile(in.nextLine()); //reads a specific properties file and load it into the properties object
    				if(props == null) {
    					System.out.printf("CLIENT: ERROR: Since the properties filename is invalid, properties object cannot be loaded! Please try again later!\n");
    					write.println("ERROR"); //sends a string to the server to handle if/else and client-server interactions
    				}
    				else {
    					write.println("SUCCESS"); //sends a string to the server to handle if/else and client-server interactions
    					//name creation must be prompted to distinguish each automobile model name -- don't want all existing automobile to be called "Sample Model"
						System.out.printf("Please enter the name of the model: ");
						props.setProperty("CarModel", in.nextLine());
    					try {
							output.writeObject(props); //transfers the properties object to the server
							System.out.printf("CLIENT: Properties Object have been successfully created and transferred to the server! Waiting for a server response..\n");
							System.err.println(receive.readLine());
							System.err.println(receive.readLine());
						} catch (IOException e) {
							System.out.printf("CLIENT: ERROR: Properties Object have not been created! Please try again later!\n");
						}
    				}
    				break;
    			case "b":
    				if(receive.readLine().equals("SUCCESS")) { //if server sent a success string, do the following code
    					
    					System.err.println(receive.readLine()); //reads the list of available models in LinkedHashMap CustomAutomobiles
    					
        				System.out.printf("CLIENT: Please enter the Model you wish to edit on (Format: 'Model #Followed by the number of the model'): ");
        				write.println(in.nextLine()); //user-inputted key to be sent to server so server can send back
        				//an instance of Custom Automobile Object
        				
               			try {
            				customauto = (CustomAutomobile) input.readObject(); //reads in the Custom Automobile Object from the server
            			} catch (ClassNotFoundException e) {
            				System.out.println(receive.readLine());
            				System.out.printf("CLIENT: ERROR: Cannot read a Custom Automobile Object from server!\n");
            			}
               			
        				if(customauto != null) {
        					System.out.printf("CLIENT: Custom Automobile Object succeed in transferring to the client!\n");
        					
        					CreateAuto create = new BuildAutomobile();
        					
        					System.out.printf("CLIENT: The option will redirect you to the CreateCustomAutoV2 method to configure your Automobile model.\n");
        					
        					//Next segment of code that does the following: Creates a custom automobile object based on its own list of optionsets and options,
        					//print it out on screen, and then null the object. DOES NOT SAVE TO NEITHER CLIENT NOR SERVER AFTER NULL IS APPLIED TO CUSTOM AUTOMOBILE OBJECT
        					customauto = create.CreateCustomAutoV2(customauto);
        					
        					System.out.printf("\nCLIENT: Custom Automobile Object has been configured! Thank you for using this service!");
        					System.out.printf("\nCLIENT: Printing the configured car. Remember, your configured car will not be saved in neither the server nor the client!\n\n");
        					
        					customauto.PrintAutoData();
        					
        					for(int i = 0; i < customauto.GetOptionSets().size(); i++) {
        						customauto.PrintOptionSetName(i);
        						customauto.PrintChoices(i);
        					}
        					
        					customauto = null;
        				}
        				else
        					System.out.printf("CLIENT: ERROR: Cannot read a Custom Automobile Object from server!\n");
    				}
    				else
    					System.out.printf("CLIENT: ERROR: LinkedHashMap CustomAutomobiles is empty! Please try again later!\n");
    				
    				break;
    			case "c":
    				System.err.println(receive.readLine());
    				System.out.printf("CLIENT: Goodbye user!\n");
    				break;
    			default:
    				System.out.printf("Invalid Input.\n");
    		}
    		
    		if(choice.equals("z") == false && choice.equals("c") == false)
    			System.out.printf("\nInput z to display the menu.\n");
    		
    	} while(choice.equals("c") == false);
	}
}
