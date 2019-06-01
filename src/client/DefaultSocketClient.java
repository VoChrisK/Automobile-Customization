/*
Name: Chris Vo
Class/Section: CIS 35B 
Assignment Number: 6
Due Date: December 7, 2015
Date Submitted: December 7, 2015
*/

//borrowed from Networking powerpoint by Bob Singh.
package client;
import java.net.*;
import java.io.*;
public class 

DefaultSocketClient extends Thread {

    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket sock;
    private String strHost;
    private int iPort;
    int iECHO_PORT = 7;
    int iSMTP_PORT = 25;
    boolean DEBUG = true;

    public DefaultSocketClient(String strHost, int iPort) {       
            setPort (iPort);
            setHost (strHost);
    }//constructor
    
    
	//***************************************************
	//*getters and setters for class DefaultSocketClient*
	//***************************************************
    public void setHost(String strHost){
        this.strHost = strHost;
    }

    public void setPort(int iPort){
        this.iPort = iPort;
    }
    
	//***************************************************
	//*other class methods for class DefaultSocketClient*
	//***************************************************

    public void run(){
    	if (openConnection()) {
    		handleSession();
    		closeSession();
    	}
    }//run
    
    public boolean openConnection() {
    	try {
    		sock = new Socket(strHost, iPort);                    
    	} catch(IOException socketError) {
    		if (DEBUG) 
    			System.err.println("Unable to connect to " + strHost);
    		return false;
    	}
    	try {
    		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    	    writer = new BufferedWriter(new OutputStreamWriter (sock.getOutputStream()));
    	}
    	catch (Exception e) {
    		if (DEBUG) 
    			System.err.println("Unable to obtain stream to/from " + strHost);
    		return false;
    	}
    	return true;
    }

    public void handleSession() {
    	String strInput = "";
    	if (DEBUG) 
    		System.out.println ("Handling session with "+ strHost + ":" + iPort);
    	try {
    		while ((strInput = reader.readLine()) != null)
    			handleInput (strInput);
    	} catch (IOException e) {
    		if (DEBUG) 
    			System.out.println ("This is an IOException Error: Handling session with "+ strHost + ":" + iPort);
    	}
    }       
    
    public void sendOutput(String strOutput) {
    	try {
    		writer.write(strOutput, 0, strOutput.length());
    	} catch (IOException e) {
    		if (DEBUG) 
    			System.out.println("Error writing to " + strHost);
    	}
    }

    public void handleInput(String strInput) {
    	System.out.println(strInput);
    }       

    public void closeSession(){
       try {
          writer = null;
          reader = null;
          sock.close();
       }
       catch (IOException e){
         if (DEBUG) System.err.println
          ("Error closing socket to " + strHost);
       }       
    }

}// class DefaultSocketClient


