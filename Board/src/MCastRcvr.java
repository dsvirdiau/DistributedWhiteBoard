import java.awt.Point;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MCastRcvr
{
 int port = 5000;
 String group = "225.4.5.6";
 MulticastSocket mskt;
 boolean flag;
 
 public static int xX1, yY1, xX2, yY2, choice = 5;
 public static int stroke = 0, eraser = 0;
 public static int eraserWidth, eraserHeight;	
 public static String color = "";
 public static List<Point> points = new ArrayList<Point>();
 public static ArrayList<String> text = new ArrayList<String>();


public static int getxX1() {
	return xX1;
}

public static void setxX1(int xX1) {
	MCastRcvr.xX1 = xX1;
}

public static int getyY1() {
	return yY1;
}

public static void setyY1(int yY1) {
	MCastRcvr.yY1 = yY1;
}

public static int getxX2() {
	return xX2;
}

public static void setxX2(int xX2) {
	MCastRcvr.xX2 = xX2;
}

public static int getyY2() {
	return yY2;
}

public static void setyY2(int yY2) {
	MCastRcvr.yY2 = yY2;
}

public static int getChoice() {
	return choice;
}

public static void setChoice(int choice) {
	MCastRcvr.choice = choice;
}

public static int getStroke() {
	return stroke;
}

public static void setStroke(int stroke) {
	MCastRcvr.stroke = stroke;
}

public static int getEraser() {
	return eraser;
}

public static void setEraser(int eraser) {
	MCastRcvr.eraser = eraser;
}

public static List<Point> getPoints() {
	return points;
}

public static void setPoints(List<Point> points) {
	MCastRcvr.points = points;
}


public static ArrayList<String> getText() {
	return text;
}

public static void setText(ArrayList<String> text) {
	MCastRcvr.text = text;
}


public static int getEraserWidth() {
	return eraserWidth;
}

public static void setEraserWidth(int eraserWidth) {
	MCastRcvr.eraserWidth = eraserWidth;
}

public static int getEraserHeight() {
	return eraserHeight;
}

public static void setEraserHeight(int eraserHeight) {
	MCastRcvr.eraserHeight = eraserHeight;
}

public static String getColor() {
	return color;
}

public static void setColor(String color) {
	MCastRcvr.color = color;
}

public MCastRcvr() 
 {
  try
  {
	   InetAddress address = InetAddress.getByName(group);
	   flag = true;
	   mskt = new MulticastSocket(port);
	   mskt.joinGroup(address);
	   text.add(new String());
  }
  catch(Exception e)
  {
	  e.printStackTrace();
  }
 }

public static int[] bytesToInts(byte[] bytes) 
{
    int[] ints = new int[bytes.length / 4];
    ByteBuffer.wrap(bytes).asIntBuffer().get(ints);
    return ints;
}

 public void getValues()
 {
	 while(true)
	 {
		   try
		   {
		     byte x1[] = new byte[1024];
		     DatagramPacket x1pack = new DatagramPacket(x1, x1.length);
		     mskt.receive(x1pack);
		     setxX1(ByteBuffer.wrap(x1pack.getData()).getInt()); 
		     
		     byte y1[] = new byte[1024];
		     DatagramPacket y1pack = new DatagramPacket(y1, y1.length);
		     mskt.receive(y1pack);
		     setyY1(ByteBuffer.wrap(y1pack.getData()).getInt());
		     
		     byte x2[] = new byte[1024];
		     DatagramPacket x2pack = new DatagramPacket(x2, x2.length);
		     mskt.receive(x2pack);
		     setxX2(ByteBuffer.wrap(x2pack.getData()).getInt());
		     
		     byte y2[] = new byte[1024];
		     DatagramPacket y2pack = new DatagramPacket(y2, y2.length);
		     mskt.receive(y2pack);
		     setyY2(ByteBuffer.wrap(y2pack.getData()).getInt());
		     
		     byte choice[] = new byte[1024];
		     DatagramPacket choicepack = new DatagramPacket(choice, choice.length);
		     mskt.receive(choicepack);
		     setChoice(ByteBuffer.wrap(choicepack.getData()).getInt());
		     
		     byte stroke[] = new byte[1024];
		     DatagramPacket strokepack = new DatagramPacket(stroke, stroke.length);
		     mskt.receive(strokepack);
		     setStroke(ByteBuffer.wrap(strokepack.getData()).getInt());
		     
		     byte eraser[] = new byte[1024];
		     DatagramPacket eraserpack = new DatagramPacket(eraser, eraser.length);
		     mskt.receive(eraserpack);
		     setEraser(ByteBuffer.wrap(eraserpack.getData()).getInt());
		     
		     byte pointx[] = new byte[1024];
		     DatagramPacket pointxpack = new DatagramPacket(pointx, pointx.length);
		     mskt.receive(pointxpack);
		     pointx = pointxpack.getData();
		     int[] intx = new int[pointx.length];
		     intx = bytesToInts(pointx);
		     
		     byte pointy[] = new byte[1024];
			 DatagramPacket pointypack = new DatagramPacket(pointy, pointy.length);
			 mskt.receive(pointypack);
			 pointy = pointypack.getData();
			 int[] inty = new int[pointy.length];
			 inty = bytesToInts(pointy);
			 
			 byte text[] = new byte[1024];
			 DatagramPacket textpack = new DatagramPacket(text, text.length);
			 mskt.receive(textpack);
			 String s = new String(textpack.getData());
			 MCastRcvr.text.set(0, s.trim());
//			 System.out.println("got   --->   " + MCastRcvr.text.get(0));
			 
			 
			 byte eraserWidth[] = new byte[1024];
		     DatagramPacket eraserwidthpack = new DatagramPacket(eraserWidth, eraserWidth.length);
		     mskt.receive(eraserwidthpack);
		     setEraserWidth(ByteBuffer.wrap(eraserwidthpack.getData()).getInt()); 
		     
		     byte eraserHeight[] = new byte[1024];
		     DatagramPacket eraserheightpack = new DatagramPacket(eraserHeight, eraserHeight.length);
		     mskt.receive(eraserheightpack);
		     setEraserHeight(ByteBuffer.wrap(eraserheightpack.getData()).getInt()); 
		     
		     byte color[] = new byte[1024];
		     DatagramPacket colorpack = new DatagramPacket(color, color.length);
		     mskt.receive(colorpack);
		     setColor(new String(colorpack.getData()).trim());
//		     System.out.println("Received  --->  " + getColor());
			 
		     for(int i = 0; i < intx.length; i++)
		     {
		    	 if(intx[i] != 0 && inty[i] != 0)
		    		 MCastRcvr.points.add(new Point(intx[i], inty[i]));
		    		 //System.out.println(intx[i] + " " + inty[i]);
		     }
		     
		     
//		     System.out.println("MCASTRcvr" + "   " + MCastRcvr.eraserHeight + "  " + MCastRcvr.eraserWidth);
		   }
		   catch(Exception e)
		   {
		    e.printStackTrace();
		   }
		   finally
		   {
			   Board.draw(xX1, yY1, xX2, yY2, choice, stroke, eraser, (ArrayList<Point>) points, text, eraserWidth, eraserHeight, color);
			   MCastRcvr.points.clear();
		   }
	 }
 }
}
