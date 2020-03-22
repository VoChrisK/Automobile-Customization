## Automobile Customization

A solo final project for `CIS 35B - Advanced Java Programming` at `De Anza College`. This project allows users to create a car 
based on the make, model, color, and additional features. The newly created car will be saved as an object and sent to the server where
it will be stored in a LinkedHashMap data structure.

![console](https://github.com/VoChrisK/Automobile-Customization/blob/master/assets/console-image.png)

## Languages/Technologies
* Java
* java.net
* Sockets

## Background and Overview

### Client-server interaction

The project features client-server interactivity with sockets. 

The server hosts all car objects in a LinkedHashMap. It waits for the client to send a serialized car object, then it will
save it to the data structure. It also receives a properties object, or an object containing all options (make, model, color),
from the client, parses it, and builds an Automobile Object out of it.

The client is greeted with a menu containing three options: Upload a properties file, configure a car, and quit menu. When the user selects
the upload option, the user is prompted to enter the path of the properties file. Once the path is provided, the client will load the
properties object, serializes it, and sends it to the server.

Here are two sample code snippets showcasing how a menu option is handled in the client-side and server-side:

**Client Side:**
```Java
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
```

**Server Side:**
```Java
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
```

### General Structure

![file structure](https://github.com/VoChrisK/Automobile-Customization/blob/master/assets/file-structure.png)

The **model** contains all information regarding **Automobile**, **Option**, and **OptionSet**. Each class keeps track of the respective entity's data and
make changes to them accordingly. The classes 

**createAuto** and **updateAuto** are external APIs that handle outside requests to create and update Automobiles respectively. **editThread** (or **scaleThread**)
is an internal API that executes operations concerning options and optionsets within the application. It is interface in which the **EditOptions** class implements it
and defines all of its associated methods.

The **FileIO** class under util package handles all logic concerning file input and output. It serializes/deserializes an automobile object to/from a
text file, reads in a file and populate the Automobile object and collection of Automobile objects (LinkedHashMap) with the data, and creates and reads
a properties file.

**AutoException** under exception package defines custom exception handlers for the app. A custom exception handler involves prepopulating the Option and OptionSet
objects should the user provides an incorrect file path. Another handler involves correcting the file extension if an incorrect one was provided.

**proxyAutomobile** is an abstract class that implements the collection of Automobile objects, Option, and OptionSet, and all CRUD operations
associated with them. **BuildAutomobile** extends this class and any interfaces it may use in order to utilizes the abstract class's functionalities.
