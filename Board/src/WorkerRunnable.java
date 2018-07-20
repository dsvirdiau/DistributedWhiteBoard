public class WorkerRunnable implements Runnable 
{
	MCastRcvr receiver;
	
    public WorkerRunnable() 
    {
        receiver =  new MCastRcvr();
    }
    
	@Override
	public void run() 
	{
			  try 
			  {
				  receiver.getValues();
			  } 
			   catch (Exception e){
				   System.out.println("Receiver error");
			   }
	}

}
