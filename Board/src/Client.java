import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client
{
	static Socket socket;
	static MCastRcvr receiver;

	static PrintWriter out = null;
    static BufferedReader in = null;
    static String ans, name;
	static Board boardClient;
	
	
	public static void main(String args[]) throws Exception
	    {
		 try
		 {
			 
			    JTextField namefld = new JTextField(30);
			    JTextField ip = new JTextField(30);
	            JTextField port = new JTextField(30);

	            JPanel myPanel = new JPanel();
	            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
	            myPanel.add(new JLabel("Enter your name : "));
	            myPanel.add(namefld);
	            myPanel.add(new JLabel("Enter IP address : "));
	            myPanel.add(ip);
	            myPanel.add(new JLabel("Enter port : "));
	            myPanel.add(port);
	            
	            int result = JOptionPane.showConfirmDialog(null, myPanel, 
	                    "Inputs", JOptionPane.OK_CANCEL_OPTION);
	            
	            if (result == JOptionPane.OK_OPTION) 
	            {
	            	socket = new Socket(ip.getText(), Integer.parseInt(port.getText()));
	            	name = namefld.getText();
	            }
	            else
	            	System.exit(0);
	            
	            out = new PrintWriter(socket.getOutputStream(), true);
			 	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 	
		 	if(name.equalsIgnoreCase(""))
		 	{
		 		out.println("connect");
			 	out.println("Anonymous");
		 	}
		 	else
		 	{
		 		out.println("connect");
			 	out.println(name);
		 	}
		 	
		 	ans = in.readLine();
		 }
		 catch(Exception ex)
		 {
			 JOptionPane.showMessageDialog(null,
		 			    "Server not running!",
		 			    "Error",
		 			    JOptionPane.ERROR_MESSAGE);
			out.close();
		 	in.close();
		 }
		 	
		 	if(ans.equalsIgnoreCase("yes"))
		 	{
				boardClient = new Board("Client", name);
				receiver = new MCastRcvr();
				receiver.getValues();
		 	}
		 	else
		 	{
		 		JOptionPane.showMessageDialog(null,
		 			    "Administrator did not accept your request, please try again later.",
		 			    "Rejection!",
		 			    JOptionPane.ERROR_MESSAGE);
		 	}
	    }
}
