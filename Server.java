// Write a server program and a client program. 
// The client sends the weight and height for a person to the server.
// The server computesBMI (Body Mass Index) and sends back to the client 
// a string that reports theBMI. You can use the following formulafor 
// computing BMI: bmi = weightInKilograms / (heightInMeters * heightInMeters)
// 
import java.io.*;
import java.net.*;
//--------------------------------------------------------
//QUESTION #1b
//NAME: Mariangelis Marrero
//PROF: Akbas
//--------------------------------------------------------

import java.util.*;
import java.awt.*;
import javax.swing.*;
 
public class Server extends JFrame {
	// Text area for displaying contents
	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args) {
		new Server();	
	}
	
	public Server() {
		// Place text area on the frame
		getContentPane().setLayout(new BorderLayout());
	    getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
	    setTitle("Server");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!
	    
	    try {
	    	// Create a server socket
	        ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("Server started at " + new Date() + '\n');

	        // Listen for a connection request
	        Socket socket = serverSocket.accept();
 
	        // Create data input and output streams
	        DataInputStream inputFromClient = new DataInputStream(
	           socket.getInputStream());
	        DataOutputStream outputToClient = new DataOutputStream(
	           socket.getOutputStream());

	        while (true) {
               // Receive weight and height from the client
	           double weightInKilograms = inputFromClient.readDouble();
	           double heightInMeters = inputFromClient.readDouble();
 
	           // Compute bmi
	           double bmi = (weightInKilograms / (heightInMeters * heightInMeters))* Math.PI;

	           // Send bmi back to the client
	           outputToClient.writeDouble(heightInMeters);
	 
	           jta.append("Weight and height received from client: " + weightInKilograms +  heightInMeters + '\n');
	           jta.append("Computed BMI: " + bmi + '\n');
	         }
	       }
           catch(IOException ex) {
	         System.err.println(ex);
	       }
	   }
}