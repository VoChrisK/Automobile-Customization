/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/
package server;
import java.io.*;

import adapter.BuildAutomobile;
import adapter.GetAuto;
import java.net.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import model.CustomAutomobile;

//This class is an implementation of the server side of client-server API. PrintWriter, BufferedReader, 
//ObjectInput and ObjectOut Streams are used to communicate with the client
@SuppressWarnings("serial")
public class BuildCarModelOptions implements Serializable {
	
	//class variable declarations
	static final int port = 4444;
	GetAuto customautos;
	int keycounter;
	ServerSocket server = null;
	Socket client = null;
	PrintWriter out = null;
	BufferedReader reader = null;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;
    Properties props = null;
    String choice;
    CustomAutomobile customauto;
    AutoServer interact;

    //constructor to create a new ServerSocket and Socket to host a server and get a response from the client
	public BuildCarModelOptions() throws IOException {		
		
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.err.printf("SERVER: ERROR: CANNOT LISTEN!\n");
			System.exit(1);
		}
		
		try {
			client = server.accept(); /*DefaultSocketClient(server.accept(), server.accept().getInetAddress().getHostName(), port); */
		} catch (IOException e) {
			System.err.printf("SERVER: ERROR: CANNOT ACCEPT!\n");
			System.exit(1);
		}
		
		//instantiate and initalize proper class variables and objects
		customautos = new BuildAutomobile();
		keycounter = 1;
		out = new PrintWriter(client.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new ObjectOutputStream(client.getOutputStream());
		input = new ObjectInputStream(client.getInputStream());
		customauto = new CustomAutomobile();
		interact = new BuildAutomobile();
	}
	
	//class method to close the specified objects properly
	public void close() throws IOException {
        input.close();
        output.close();
        client.close();
        server.close();
	}
	
	//class method to handle the whole client server interaction. The class method mimics the method in client:
	//it involves a switch-case like statements enclosed in a while loop - to stimulate a menu interface
	public void ClientServerInteraction() throws IOException {
        while((choice = reader.readLine()).equals("c") == false) { //while the user's input in client does not equal to "c"
        	if(choice.equals("a")) { //if user's input is "a" option back in client
        		out.println("SERVER: Option #1 selected.");
        		if(reader.readLine().equals("SUCCESS")) {
        			try {
        				props = (Properties) input.readObject(); //read a properties object from the client
        				out.println("SERVER: Properties Object succeed in transferring to the server!");
        			} catch (ClassNotFoundException e) {
        				out.println("SERVER: ERROR: Cannot read a Properties Object from client!\n");
        			}
                
        			if(props != null) { 
        				//parse and transfer the data from the Properties Object into the Custom Automobile object
        				//additionally, the new Custom Automobile object is added into the LinkedHashMap CustomAutomobiles Object
        				customautos.GetCustomAuto().put("Model #" + keycounter, interact.ConvertPropertiesToCustomAuto(props));
        				keycounter++;
        				out.println("SERVER: Properties Object has converted to a Custom Automobile Object and has been added to the LinkedHashMap CustomAutomobiles!");
        			}
        			else
        				out.println("SERVER: Conversion of Properties Object and/or addition to the LinkedHashMap CustomAutomobiles failed!");
    
        			props = null;
        		}
        	}  
        	
        	if(choice.equals("b")) { //if user's input is "b" option back in client
        		if(customautos.GetCustomAuto().isEmpty()) //if the LinkedHashMap does not contain any Custom Automobile Objects, write error to
        			out.println("ERROR"); //client, terminate the rest of this option choice, and go back to the menu
        		else {
        			out.println("SUCCESS");
        			
        			//method object/variable declarations
        			Set<String> set = customautos.GetCustomAuto().keySet(); //Set object is equal to the set of keys in the Custom Automobiles object
        			Iterator<String> iterate = set.iterator(); //generic is set to String because the keys are defined as Strings
        			String traverse;
        			String append = new String("Available Models: ");
        			
        			//while there are still elements left in the LinkedHashMap CustomAutomobiles
        			//sends the available Custom Automobiles in LinkedHashMap CustomAutomobiles to client
        			while(iterate.hasNext()) {
        				traverse = iterate.next(); //holds the current key and use that key to access the current element's data
        				append += traverse + ": " + customautos.GetCustomAuto().get(traverse).GetAutoName() + ", ";
        			}
        			
        			//removes the unnecessary comma in the end of the string 
        			append = append.substring(0, append.length() - 2);
        			
        			//prints out the current available Custom Automobiles in the LinkedHashMap
        			out.println(append);
        			
        			String key = reader.readLine();
        			
        			try {
        				output.writeObject(customautos.GetCustomAuto().get(key)); //proceed to write the desired Custom Automobile to client
        			} catch(IOException e) {
        				output.writeBytes("SERVER: ERROR");
        			}
        		}
        	}
        }     	
        
        if(choice.equals("c")) //if user's input is "c" option back in client
        	out.println("SERVER: Goodbye, from server!\n"); //exit the menu interface and terminate the client-server interaction
		
	}
}
