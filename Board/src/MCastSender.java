import java.awt.Point;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class MCastSender
{
 int port = 5000;
 String group = "225.4.5.6"; //class D
 MulticastSocket mskt;
 
 public static int xX1, yY1, xX2, yY2, choice = 5;
 public static int stroke = 0, eraser = 0;
 public static int eraserWidth, eraserHeight;	
 public static String color = "";
 public static List<Point> points = new ArrayList<Point>();
 public static ArrayList<String> text;
 public InetAddress address;
 

public static int getxX1() {
	return xX1;
}

public static void setxX1(int xX1) {
	MCastSender.xX1 = xX1;
}

public static int getyY1() {
	return yY1;
}

public static void setyY1(int yY1) {
	MCastSender.yY1 = yY1;
}

public static int getxX2() {
	return xX2;
}

public static void setxX2(int xX2) {
	MCastSender.xX2 = xX2;
}

public static int getyY2() {
	return yY2;
}

public static void setyY2(int yY2) {
	MCastSender.yY2 = yY2;
}

public static int getChoice() {
	return choice;
}

public static void setChoice(int choice) {
	MCastSender.choice = choice;
}

public static int getStroke() {
	return stroke;
}

public static void setStroke(int stroke) {
	MCastSender.stroke = stroke;
}

public static int getEraser() {
	return eraser;
}

public static void setEraser(int eraser) {
	MCastSender.eraser = eraser;
}

public static List<Point> getPoints() {
	return points;
}

public static void setPoints(List<Point> points) {
	MCastSender.points = points;
}

public static ArrayList<String> getText() {
	return text;
}

public static void setText(ArrayList<String> text) {
	MCastSender.text = text;
}

public static int getEraserWidth() {
	return eraserWidth;
}

public static void setEraserWidth(int eraserWidth) {
	MCastSender.eraserWidth = eraserWidth;
}

public static int getEraserHeight() {
	return eraserHeight;
}

public static void setEraserHeight(int eraserHeight) {
	MCastSender.eraserHeight = eraserHeight;
}

public static String getColor() {
	return color;
}

public static void setColor(String color) {
	MCastSender.color = color;
}

public MCastSender() 
 {
  try
  {
   mskt = new MulticastSocket();
   text = new ArrayList<String>();
   text.add(new String());
   address = InetAddress.getByName(group);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }

public static byte[] intsToBytes(int[] ints) 
{
    ByteBuffer bb = ByteBuffer.allocate(ints.length * 4);
    IntBuffer ib = bb.asIntBuffer();
    for (int i : ints) ib.put(i);
    return bb.array();
}

 void sendValues()
 {
  try
  {
    byte x1[] = ByteBuffer.allocate(4).putInt(getxX1()).array();
    DatagramPacket x1pack = new DatagramPacket(x1, x1.length, address , port);
    mskt.send(x1pack);
    
    byte y1[] = ByteBuffer.allocate(4).putInt(getyY1()).array();
    DatagramPacket y1pack = new DatagramPacket(y1, y1.length, address , port);
    mskt.send(y1pack);
    
    byte x2[] = ByteBuffer.allocate(4).putInt(getxX2()).array();
    DatagramPacket x2pack = new DatagramPacket(x2, x2.length, address , port);
    mskt.send(x2pack);
    
    byte y2[] = ByteBuffer.allocate(4).putInt(getyY2()).array();
    DatagramPacket y2pack = new DatagramPacket(y2, y2.length, address , port);
    mskt.send(y2pack);
    
    byte choice[] = ByteBuffer.allocate(4).putInt(getChoice()).array();
    DatagramPacket choicepack = new DatagramPacket(choice, choice.length, address , port);
    mskt.send(choicepack);
    
    byte stroke[] = ByteBuffer.allocate(4).putInt(getStroke()).array();
    DatagramPacket strokepack = new DatagramPacket(stroke, stroke.length, address , port);
    mskt.send(strokepack);
    
    byte eraser[] = ByteBuffer.allocate(4).putInt(getEraser()).array();
    DatagramPacket eraserpack = new DatagramPacket(eraser, eraser.length, address , port);
    mskt.send(eraserpack);
    
    int[] intx = new int[getPoints().size()];
    for(int i = 0; i < getPoints().size(); i++)
    {
    	intx[i] = getPoints().get(i).x;
    }
    byte[] pointx = intsToBytes(intx);
    DatagramPacket pointxpack = new DatagramPacket(pointx, pointx.length, address , port);
    mskt.send(pointxpack);
    
    int[] inty = new int[getPoints().size()];
    for(int i = 0; i < getPoints().size(); i++)
    {
    	inty[i] = getPoints().get(i).y;
    	//System.out.println(intx[i] + " " + inty[i]);
    }
    byte[] pointy = intsToBytes(inty);
    DatagramPacket pointypack = new DatagramPacket(pointy, pointy.length, address , port);
    mskt.send(pointypack);
    
    if(!(text == null))
    {
	    byte[] text = new byte[MCastSender.text.size()];
	    text = MCastSender.text.get(0).getBytes();
	    DatagramPacket textpack = new DatagramPacket(text, text.length, address , port);
	    mskt.send(textpack);
    }    
    
    byte eraserWidth[] = ByteBuffer.allocate(4).putInt(getEraserWidth()).array();
    DatagramPacket eraserwidthpack = new DatagramPacket(eraserWidth, eraserWidth.length, address , port);
    mskt.send(eraserwidthpack);
    
    byte eraserHeight[] = ByteBuffer.allocate(4).putInt(getEraserHeight()).array();
    DatagramPacket eraserheightpack = new DatagramPacket(eraserHeight, eraserHeight.length, address , port);
    mskt.send(eraserheightpack);
    
    byte color[] = MCastSender.color.getBytes();
    DatagramPacket colorpack = new DatagramPacket(color, color.length, address , port);
    mskt.send(colorpack);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }
    
}