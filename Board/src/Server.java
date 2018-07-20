
public class Server
{
	    
	public static ThreadPoolServer server;
	
	public static void main(String args[]) throws Exception
	    {
		    server = new ThreadPoolServer();
			new Thread(server).start();
	    }
}