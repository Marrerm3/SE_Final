//--------------------------------------------------------
// QUESTION #1a
// NAME: Mariangelis Marrero
// PROF: Akbas
//--------------------------------------------------------

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Client extends JFrame implements ActionListener {
    // Text field for receiving radius
	private JTextField jtfw = new JTextField();
	private JTextField jtfh = new JTextField();
	
	
	// Text area to display contents
	private JTextArea jta = new JTextArea();
	
	// IO streams
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;

	public static void main(String[] args) {
      new Client();
    }
	@SuppressWarnings("resource")
	public Client() {
	   // Panel p to hold the label and text field
	   JPanel p = new JPanel();
	   JPanel pw = new JPanel();
	   JPanel ph = new JPanel();

       pw.setLayout(new BorderLayout());
       pw.add(new JLabel("Enter weigh (kg): "), BorderLayout.WEST);
       pw.add(jtfw, BorderLayout.CENTER);
       

       ph.setLayout(new BorderLayout());
       ph.add(new JLabel("Enter height (m): "), BorderLayout.WEST);
       ph.add(jtfh, BorderLayout.CENTER);
       
       jtfw.setHorizontalAlignment(JTextField.RIGHT);
       jtfh.setHorizontalAlignment(JTextField.RIGHT);
       
       getContentPane().setLayout(new BorderLayout());
       getContentPane().add(pw, BorderLayout.NORTH);
       getContentPane().add(ph, BorderLayout.SOUTH);
       getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
 
       jtfw.addActionListener(this); // Register listener
       jtfh.addActionListener(this);
       
       setTitle("Client");
       setSize(500, 300);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true); // It is necessary to show the frame here!
       setSize(500, 300);
       
       try {
    	 // Create a socket to connect to the server
         Socket socket = new Socket("localhost", 8000);
         // Socket socket = new Socket("130.254.204.36", 8000);
         // Socket socket = new Socket("drake.Armstrong.edu", 8000);

         // Create an input stream to receive data from the server
         inputFromServer = new DataInputStream(
           socket.getInputStream());
 
         // Create an output stream to send data to the server
         outputToServer =
           new DataOutputStream(socket.getOutputStream());
         }
       catch (IOException ex) {
          jta.append(ex.toString() + '\n');
       }
    }

  public void actionPerformed(ActionEvent e) {
     String actionCommand = e.getActionCommand();
     if (e.getSource() instanceof JTextField) {
       try {
         // Get the weight and height from the text field
         double weightInKilograms = Double.parseDouble(jtfw.getText().trim());
         double heightInMeters = Double.parseDouble(jtfh.getText().trim());
         
         Person person = new Person(weightInKilograms, heightInMeters);
         
         // Send the weight and height
         outputToServer.writeObject(person);
         outputToServer.flush();
 
         // Get area from the server
         double bmi = inputFromServer.readDouble();
 
         // Display to the text area
         jta.append("The weight in kg is " + weightInKilograms + "\n");
         jta.append("The height in m is " + heightInMeters + "\n");
         jta.append("BMI received from the server is + bmi + '\n' ");
      }
       catch (IOException ex) {
         System.err.println(ex);
       }
     }
   }
 }