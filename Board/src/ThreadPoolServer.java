

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThreadPoolServer implements Runnable 
{
	
	static ServerSocket serverSocket = null;
	static Socket socket = null;
	private boolean isStopped = false;
	private Thread runningThread = null;
	public static Board board;
	
	ExecutorService threadPool = Executors.newFixedThreadPool(20);
	
	 private void openServerSocket() 
	 {
	        try 
	        {
	        	JTextField name = new JTextField(30);
	            JTextField port = new JTextField(30);

	            JPanel myPanel = new JPanel();
	            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
	            myPanel.add(new JLabel("Enter your name : "));
	            myPanel.add(name);
	            myPanel.add(new JLabel("Enter port number : "));
	            myPanel.add(port);
	            
	            int result = JOptionPane.showConfirmDialog(null, myPanel, 
	                    "Inputs", JOptionPane.OK_CANCEL_OPTION);

	            if (result == JOptionPane.OK_OPTION) 
	            {
	            	serverSocket = new ServerSocket(Integer.parseInt(port.getText()));
	            	
	            	board = new Board("Host", name.getText());
	             }
	            else
	            	Server.server.stop();
	            
	        } catch (IOException e) 
	        {
	        	JOptionPane.showMessageDialog(null,
		 			    "Port already busy or server already running!",
		 			    "Error",
		 			    JOptionPane.ERROR_MESSAGE);
	        	
	            throw new RuntimeException("Cannot open port", e);
	        }
	    }
	 
	 public void stop()
	 {
	        this.isStopped = true;
	        try 
	        {
	            serverSocket.close();
	            System.out.println("Server socket closed.") ;
	        } 
	        catch (IOException e) 
	        {
	        	
	            throw new RuntimeException("Error closing server", e);
	        }
	  }
	 
	 private synchronized boolean isStopped() 
	 {
	        return this.isStopped;
	 }
	 
	@Override
	public void run() {
		// TODO Auto-generated method stub
			  
			   synchronized(this)
			   {
		            runningThread = Thread.currentThread();
		       }
			   
		        openServerSocket();
		        
		        while(! isStopped())
		        {
		            Socket clientSocket = null;
		            try 
		            {
		                clientSocket = serverSocket.accept();
		                
		                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		                
		                String line = in.readLine();
		                String name = in.readLine();
		                
		                System.out.println(line + "  " + name);
		                if(line.equalsIgnoreCase("connect"))
		                {
		                	Object[] options = {"Yes, please",
		                    "No way!"};
								int n = JOptionPane.showOptionDialog(null,
								    "Would you like to allow " + name + " to use the whiteboard?" ,
								    "Confirmation!",
								    JOptionPane.YES_NO_OPTION,
								    JOptionPane.QUESTION_MESSAGE,
								    null,     //do not use a custom Icon
								    options,  //the titles of buttons
								    options[0]); //default button title
		
		                	if(n == JOptionPane.YES_OPTION)
		                	{
		                		out.println("yes");
		                	}
		                	else
		                	{
		                		out.println("no");
		                	}
		                }
		            } 
		            catch (IOException e) 
		            {
		                if(isStopped()) 
		                {
		                    return;
		                }
		                //throw new RuntimeException("Error accepting client connection", e);
		            }
		            
		            this.threadPool.execute(new WorkerRunnable());
		        }
		        this.threadPool.shutdown();
		        System.out.println("Server Stopped.") ;
		 }

}
	

