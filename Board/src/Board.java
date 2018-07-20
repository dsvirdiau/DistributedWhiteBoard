import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Board extends JPanel implements MouseListener, ActionListener, MouseMotionListener, KeyListener, WindowListener
{
	public static JFrame frame;
	
    private static final long serialVersionUID = 1L;
    public static int stroke = 0, eraser = 0;
    public static int xX1, yY1, xX2, yY2, choice = 5;
    public static String color, savePath, saveAsPath;
    
    MCastSender sender;
    
    JRadioButtonMenuItem rbMenuItem;
    JRadioButtonMenuItem rbMenuItemMode;
    JRadioButtonMenuItem rbMenuItemEraser;
    JRadioButtonMenuItem rbMenuItemStroke;
    
    static ArrayList<String> textInput;
    static List<Point> points;
    
   
    static BufferedImage grid;
    static Graphics2D gc;
    
    ButtonGroup group, groupMode, groupEraser, groupStroke;
    
    int fillFlag = 0;
    static String shape = "Line", text = "";
    char c;

    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private static int eraserWidth = 20;
    private static int eraserHeight = 20;
    public static String type, name;
    
    Board(String type, String name)
    {
    	Board.type = type;
    	Board.name = name;
    	
        frame = new JFrame("White Board" + " : " + type + "         Name : " + name);
        frame.setSize(600, 400);
        
        frame.setResizable(false);
        frame.setBackground(BACKGROUND_COLOR);
        frame.getContentPane().add(this);
        
        sender = new MCastSender();
        points = new ArrayList<Point>();
        points.clear();

        
        JMenuBar menuBar = new JMenuBar();
        
      //for Save
        if(type.equalsIgnoreCase("host"))
 		{
        	
	 		JMenu menuFile = new JMenu("File");
	 		menuFile.setMnemonic(KeyEvent.VK_F);
 		
 		
 			JMenuItem menuItemNew = new JMenuItem("New");
 			menuItemNew.setActionCommand("New");
 			menuItemNew.addActionListener(this);
 			menuItemNew.setMnemonic(KeyEvent.VK_N);
 	 		menuFile.add(menuItemNew);
 		
	 		JMenuItem menuItemOpen = new JMenuItem("Open");
	 		menuItemOpen.setActionCommand("Open");
	 		menuItemOpen.addActionListener(this);
	 		menuItemOpen.setMnemonic(KeyEvent.VK_O);
	 		menuFile.add(menuItemOpen);
	 		
	 		JMenuItem menuItemSave = new JMenuItem("Save");
	 		menuItemSave.setActionCommand("Save");
	 		menuItemSave.addActionListener(this);
	 		menuItemSave.setMnemonic(KeyEvent.VK_S);
	 		menuFile.add(menuItemSave);
	 		
	 		JMenuItem menuItemSaveAs = new JMenuItem("Save As");
	 		menuItemSaveAs.setActionCommand("SaveAs");
	 		menuItemSaveAs.addActionListener(this);
	 		menuItemSaveAs.setMnemonic(KeyEvent.VK_S);
	 		menuFile.add(menuItemSaveAs);
	 		
	 		JMenuItem menuItemClose = new JMenuItem("Close");
	 		menuItemClose.setActionCommand("Close");
	 		menuItemClose.addActionListener(this);
	 		menuItemClose.setMnemonic(KeyEvent.VK_C);
	 		menuFile.add(menuItemClose);
	 		
	 		menuBar.add(menuFile);
 		}
        // for shape
     		
     		JMenu menu = new JMenu("Shapes");
     		menu.setMnemonic(KeyEvent.VK_S);
     		
     		group = new ButtonGroup();
     		
     		rbMenuItem = new JRadioButtonMenuItem("Line");
     		rbMenuItem.setActionCommand("Line");
     		rbMenuItem.addActionListener(this);
     		rbMenuItem.setSelected(true);
     		rbMenuItem.setMnemonic(KeyEvent.VK_L);
     		group.add(rbMenuItem);
     		menu.add(rbMenuItem);

     		rbMenuItem = new JRadioButtonMenuItem("Rectangle");
     		rbMenuItem.setActionCommand("Rectangle");
     		rbMenuItem.addActionListener(this);
     		rbMenuItem.setMnemonic(KeyEvent.VK_R);
     		group.add(rbMenuItem);
     		menu.add(rbMenuItem);
     		
     		rbMenuItem = new JRadioButtonMenuItem("Oval");
     		rbMenuItem.setActionCommand("Oval");
     		rbMenuItem.addActionListener(this);
     		rbMenuItem.setMnemonic(KeyEvent.VK_O);
     		group.add(rbMenuItem);
     		menu.add(rbMenuItem);
     		
     		rbMenuItem = new JRadioButtonMenuItem("Free Style");
     		rbMenuItem.setActionCommand("Free");
     		rbMenuItem.addActionListener(this);
     		rbMenuItem.setMnemonic(KeyEvent.VK_F);
     		group.add(rbMenuItem);
     		menu.add(rbMenuItem);
     		
     		rbMenuItem = new JRadioButtonMenuItem("Text");
     		rbMenuItem.setActionCommand("Text");
     		rbMenuItem.addActionListener(this);
     		rbMenuItem.setMnemonic(KeyEvent.VK_T);
     		group.add(rbMenuItem);
     		menu.add(rbMenuItem);
     		
     		menuBar.add(menu);
     		
     		//for mode
     		JMenu menuMode = new JMenu("Mode");
     		menuMode.setMnemonic(KeyEvent.VK_M);
     		
     		
     		groupMode = new ButtonGroup();
     		
     		rbMenuItemMode = new JRadioButtonMenuItem("Unfilled");
     		rbMenuItemMode.setActionCommand("Unfilled");
     		rbMenuItemMode.addActionListener(this);
     		rbMenuItemMode.setSelected(true);
     		rbMenuItemMode.setMnemonic(KeyEvent.VK_U);
     		groupMode.add(rbMenuItemMode);
     		menuMode.add(rbMenuItemMode);
     		
     		rbMenuItemMode = new JRadioButtonMenuItem("Filled");
     		rbMenuItemMode.setActionCommand("Filled");
     		rbMenuItemMode.addActionListener(this);
     		rbMenuItemMode.setMnemonic(KeyEvent.VK_F);
     		groupMode.add(rbMenuItemMode);
     		menuMode.add(rbMenuItemMode);
     		
     		menuMode.addSeparator();
     		
     		JMenuItem menuItemcolors = new JMenuItem("Colors");
     		menuItemcolors.setActionCommand("Colors");
     		menuItemcolors.addActionListener(this);
     		menuItemcolors.setMnemonic(KeyEvent.VK_C);
     		menuMode.add(menuItemcolors);

     		menuBar.add(menuMode);
     				
     		//for eraser
     		JMenu menuEraser = new JMenu("Eraser");
     		menuEraser.setMnemonic(KeyEvent.VK_E);
     		
     		groupEraser = new ButtonGroup();
     		
     		rbMenuItemEraser = new JRadioButtonMenuItem("Small Eraser");
     		rbMenuItemEraser.setActionCommand("Small Eraser");
     		rbMenuItemEraser.addActionListener(this);
     		rbMenuItemEraser.setMnemonic(KeyEvent.VK_S);
     		groupEraser.add(rbMenuItemEraser);
     		menuEraser.add(rbMenuItemEraser);

     		rbMenuItemEraser = new JRadioButtonMenuItem("Medium Eraser");
     		rbMenuItemEraser.setActionCommand("Medium Eraser");
     		rbMenuItemEraser.addActionListener(this);
     		rbMenuItemEraser.setMnemonic(KeyEvent.VK_M);
     		groupEraser.add(rbMenuItemEraser);
     		menuEraser.add(rbMenuItemEraser);
     		
     		rbMenuItemEraser = new JRadioButtonMenuItem("Large Eraser");
     		rbMenuItemEraser.setActionCommand("Large Eraser");
     		rbMenuItemEraser.addActionListener(this);
     		rbMenuItemEraser.setMnemonic(KeyEvent.VK_L);
     		groupEraser.add(rbMenuItemEraser);
     		menuEraser.add(rbMenuItemEraser);
     		
     		menuEraser.addSeparator();
     		
     		JMenuItem menuItemErase = new JMenuItem("Erase All");
     		menuItemErase.setActionCommand("Erase All");
     		menuItemErase.addActionListener(this);
     		menuItemErase.setMnemonic(KeyEvent.VK_E);
     		menuEraser.add(menuItemErase);
     		
     		menuBar.add(menuEraser);
       		
     		//for stroke
     		JMenu menuStroke = new JMenu("Stroke");
     		menuStroke.setMnemonic(KeyEvent.VK_E);
     		
     		groupStroke = new ButtonGroup();
     		
     		rbMenuItemStroke = new JRadioButtonMenuItem("Thin Stroke");
     		rbMenuItemStroke.setActionCommand("Thin Stroke");
     		rbMenuItemStroke.addActionListener(this);
     		rbMenuItemStroke.setSelected(true);
     		rbMenuItemStroke.setMnemonic(KeyEvent.VK_T);
     		groupStroke.add(rbMenuItemStroke);
     		menuStroke.add(rbMenuItemStroke);

     		rbMenuItemStroke = new JRadioButtonMenuItem("Medium Stroke");
     		rbMenuItemStroke.setActionCommand("Medium Stroke");
     		rbMenuItemStroke.addActionListener(this);
     		rbMenuItemStroke.setMnemonic(KeyEvent.VK_M);
     		groupStroke.add(rbMenuItemStroke);
     		menuStroke.add(rbMenuItemStroke);
     		
     		rbMenuItemStroke = new JRadioButtonMenuItem("Thick Stroke");
     		rbMenuItemStroke.setActionCommand("Thick Stroke");
     		rbMenuItemStroke.addActionListener(this);
     		rbMenuItemStroke.setMnemonic(KeyEvent.VK_T);
     		groupStroke.add(rbMenuItemStroke);
     		menuStroke.add(rbMenuItemStroke);
     		
     		menuBar.add(menuStroke);
     		
     		
     		
     		//////////////////////////
     		frame.setJMenuBar(menuBar);
       
     		
        addMouseListener(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
        frame.addWindowListener(this);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    
    private void savePanel(int type)
	{
    	if(type == 0)
    	{
    		if(savePath == null)
    		{
				JFileChooser jfc = new JFileChooser();
				int result = jfc.showSaveDialog(this);
				
				if (result == JFileChooser.CANCEL_OPTION)
				    return;
				else
					savePath = jfc.getSelectedFile().getPath();
				
				if(!savePath.contains("."))
					savePath = savePath.trim() + ".png";
    		}
			
			Container c = frame.getContentPane();
			BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
			c.paint(im.getGraphics());
			try {
				ImageIO.write(im, "PNG", new File(savePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
			JFileChooser jfc = new JFileChooser();
			int result = jfc.showSaveDialog(this);
			
			if (result == JFileChooser.CANCEL_OPTION)
			    return;
			else
				saveAsPath = jfc.getSelectedFile().getPath();
			
			if(!saveAsPath.contains("."))
				saveAsPath = saveAsPath.trim() + ".png";
			
			Container c = frame.getContentPane();
			BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
			c.paint(im.getGraphics());
			try 
			{
				ImageIO.write(im, "PNG", new File(saveAsPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
    

    public void newPanel()
    {
    	String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Do you want to save this board?","Whiteboard",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
         if(PromptResult==JOptionPane.YES_OPTION)
         {
        	 savePanel(0);
        	 
        	 frame.repaint();
             Color temp = gc.getColor();
             gc.setColor(BACKGROUND_COLOR);
             gc.fillRect(0, 0, frame.getWidth(), frame.getHeight());
             gc.setColor(temp);
             frame.repaint();
         }
         
         
    }
    
    public void closePanel()
    {
    	if(type.equalsIgnoreCase("host"))
    	{
    		String ObjButtons[] = {"Yes","No"};
            int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit without saving?","Whiteboard",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
             if(PromptResult==JOptionPane.YES_OPTION)
             {
                 Server.server.stop();
            	 System.exit(0);
             }
             else
             {
            	 savePanel(0);
                 Server.server.stop();
            	 System.exit(0);
             }
    	}
    	else
    	{
	    	String ObjButtons[] = {"Yes","No"};
	        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Whiteboard",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
	         if(PromptResult==JOptionPane.YES_OPTION)
	         {
	        	 System.exit(0);
	         }
    	}
    }
    
    public void open()
    {
    	       try 
    	       {               
    	    	   JFileChooser jfc = new JFileChooser();
    				int result = jfc.showOpenDialog(this);
    				
    				if (result == JFileChooser.CANCEL_OPTION)
    				    return;
    				else
    					savePath = jfc.getSelectedFile().getPath();
    				
    	    	   BufferedImage image = ImageIO.read(new File(savePath));
    	    	   gc.drawImage(image, 0, 0, null); 
    	    	   repaint();
    	       } catch (IOException ex) 
    	       {
    	            // handle exception...
    	       }
    }
    
    public synchronized void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (grid == null)
        {
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
            gc.setColor(Color.BLUE);
            
            String rbg = Integer.toHexString(Color.BLUE.getRGB());
            rbg = rbg.substring(2, rbg.length());
            color = rbg;
        }

        g2.drawImage(grid, null, 0, 0);
        check();
        
    }

    public static synchronized void draw(int x1, int y1, int x2, int y2, int ch, int strk, int eras, ArrayList<Point> pnts, ArrayList<String> txt,int eraserW, int eraserH, String clr)
    {
    	xX1 = x1;
    	yY1 = y1;
    	xX2 = x2;
    	yY2 = y2;
    	choice = ch;
    	stroke = strk;
    	eraser = eras;
    	points = pnts;
    	textInput = txt;
    	eraserWidth = eraserW;
    	eraserHeight = eraserH;
    	color = clr;
    	
		
    	gc.setColor(new Color(Integer.parseInt(color,16)));
    	
        int w = xX2 - xX1;
        if (w < 0)
            w = w * (-1);

        int h = yY2 - yY1;
        if (h < 0)
            h = h * (-1);

        switch (choice)
        {
        case 1:
        	
        	if (stroke == 0)
                gc.setStroke(new BasicStroke(1));
            if (stroke == 1)
                gc.setStroke(new BasicStroke(3));
            if (stroke == 2)
                gc.setStroke(new BasicStroke(6));
            
            check();
            gc.drawRect(xX1, yY1, w, h);
            frame.repaint();
            break;

        case 2:
        	
        	if (stroke == 0)
                gc.setStroke(new BasicStroke(1));
            if (stroke == 1)
                gc.setStroke(new BasicStroke(3));
            if (stroke == 2)
                gc.setStroke(new BasicStroke(6));
            
            check();
            gc.drawOval(xX1, yY1, w, h);
            frame.repaint();
            break;

        case 3:
            check();
            gc.drawRect(xX1, yY1, w, h);
            gc.fillRect(xX1, yY1, w, h);
            frame.repaint();
            break;

        case 4:
            check();
            gc.drawOval(xX1, yY1, w, h);
            gc.fillOval(xX1, yY1, w, h);
            frame.repaint();
            break;

        case 5:

            if (stroke == 0)
                gc.setStroke(new BasicStroke(1));
            if (stroke == 1)
                gc.setStroke(new BasicStroke(3));
            if (stroke == 2)
                gc.setStroke(new BasicStroke(6));
            gc.drawLine(xX1, yY1, xX2, yY2);
            frame.repaint();
            break;

        case 6:
        	frame.repaint();
            Color temp = gc.getColor();
            gc.setColor(BACKGROUND_COLOR);
            gc.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            gc.setColor(temp);
            frame.repaint();
            break;

        case 7:
            if (eraser == 1)
            {
                gc.clearRect(xX1, yY1, w, h);
                frame.repaint();
            }
            else
            {
            }
            break;
            
        case 8:
        	
        	int spacing = gc.getFontMetrics().getHeight();
        	int n = 0;
        	if(textInput == null)
        	{}
        	else
        	{
	        	for (String line : textInput) 
	        	{
	    			gc.drawString(line, xX1, yY1 + (n*spacing));
	    			n++;
	    		}
	        	frame.repaint();
        	}
            break;
            
        case 9:
        	if (stroke == 0)
                gc.setStroke(new BasicStroke(1));
            if (stroke == 1)
                gc.setStroke(new BasicStroke(3));
            if (stroke == 2)
                gc.setStroke(new BasicStroke(6));
            
        	for (int i = 1; i < Board.points.size(); i++) 
            {
        			if(points.get(0).x != points.get(i).x)
        				gc.drawLine(points.get(i-1).x, points.get(i-1).y, points.get(i).x, points.get(i).y);
            }
        	
            frame.repaint();
            break;
            
        }
    }

    public static void check()
    {
        if (xX1 > xX2)
        {
            int z = 0;
            z = xX1;
            xX1 = xX2;
            xX2 = z;
        }
        if (yY1 > yY2)
        {
            int z = 0;
            z = yY1;
            yY1 = yY2;
            yY2 = z;
        }
        
    }

    public void actionPerformed(ActionEvent e)
    {
        super.removeMouseMotionListener(this);

        if (e.getActionCommand().equals("Colors"))
        {
            Color bgColor = JColorChooser.showDialog(this, "Choose Background Color", getBackground());
            if (bgColor != null)
            {
               gc.setColor(bgColor);
            }
             String rbg = Integer.toHexString(bgColor.getRGB());
             rbg = rbg.substring(2, rbg.length());
             color = rbg;
        }

        if(e.getActionCommand().equals("Unfilled"))
        {
        	
        	fillFlag = 0;
        	
        	if(shape.equalsIgnoreCase("Rectangle"))
        		choice = 1;
        	
        	if(shape.equalsIgnoreCase("Oval"))
        		choice = 2;
        }

        if (e.getActionCommand().equals("Filled"))
        {
        	fillFlag = 1;
        	
        	if(shape.equalsIgnoreCase("Rectangle"))
        		choice = 3;
        	
        	if(shape.equalsIgnoreCase("Oval"))
        		choice = 4;
        }
        
        if (e.getActionCommand().equals("Rectangle"))
        {
        	shape = "Rectangle";
        	
        	if(fillFlag == 0)
        		choice = 1;
        	
        	if(fillFlag == 1)
        		choice = 3;
        }

        if (e.getActionCommand().equals("Oval"))
        {
        	shape = "Oval";
        	
        	if(fillFlag == 0)
        		choice = 2;
        	
        	if(fillFlag == 1)
        		choice = 4;
        }

        if (e.getActionCommand().equals("Line"))
        {
        	shape = "Line";
            choice = 5;
        }
 
        if (e.getActionCommand().equals("Thin Stroke"))
        {
            stroke = 0;
            
            if(choice == 9)
            	super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Medium Stroke"))
        {
            stroke = 1;
            
            if(choice == 9)
            	super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Thick Stroke"))
        {
            stroke = 2;
            
            if(choice == 9)
            	super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Small Eraser"))
        {
        	eraserWidth = 20;
        	eraserHeight = 20;
        	
            eraser = 1;
            choice = 7;
            super.addMouseMotionListener(this);
        }
        
        if (e.getActionCommand().equals("Medium Eraser"))
        {
        	eraserWidth = 40;
        	eraserHeight = 40;
        	
            eraser = 1;
            choice = 7;
            super.addMouseMotionListener(this);
        }
        
        if (e.getActionCommand().equals("Large Eraser"))
        {
        	eraserWidth = 60;
        	eraserHeight = 60;
        	
            eraser = 1;
            choice = 7;
            super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Erase All"))
        {
            choice = 6;
            draw(xX1, yY1, xX2, yY2, choice, stroke, eraser, (ArrayList<Point>) points, textInput, eraserWidth, eraserHeight, color);
        }
        
        if (e.getActionCommand().equals("Text"))
        {
        	shape = "Text";
            choice = 8;
        }
        
        if (e.getActionCommand().equals("Free"))
        {
        	shape = "Free";
            choice = 9;
            super.addMouseMotionListener(this);
        }
        
        if (e.getActionCommand().equals("Save"))
        {
            savePanel(0);
        }
        
        if (e.getActionCommand().equals("SaveAs"))
        {
            savePanel(1);
        }
        
        if (e.getActionCommand().equals("New"))
        {
            newPanel();
        }
        
        if (e.getActionCommand().equals("Open"))
        {
            open();
        }
        
        if (e.getActionCommand().equals("Close"))
        {
            closePanel();
        }

    }

    public void mouseExited(MouseEvent evt)
    {
    }

    public void mouseEntered(MouseEvent evt)
    {
    }

    public void mouseClicked(MouseEvent evt)
    {
//    	textInput = null;
    }

    public void mousePressed(MouseEvent evt)
    {
        xX1 = evt.getX();
        yY1 = evt.getY();
        
        points.clear();
    }

    public void mouseReleased(MouseEvent evt)
    {
        xX2 = evt.getX();
        yY2 = evt.getY();
        
        if(shape.equalsIgnoreCase("Text"))
        {
        	textInput = null;
        	System.out.println("text");
        }
        else if(shape.equalsIgnoreCase("Free"))
        {
        	System.out.println("free");
        }
        else
        {
        	draw(xX1, yY1, xX2, yY2, choice, stroke, eraser, (ArrayList<Point>) points, textInput, eraserWidth, eraserHeight, color);
        }
        
        MCastSender.setxX1(xX1);
        MCastSender.setxX2(xX2);
        MCastSender.setyY1(yY1);
        MCastSender.setyY2(yY2);
        MCastSender.setChoice(choice);
        MCastSender.setStroke(stroke);
        MCastSender.setEraser(eraser);
        MCastSender.setEraserHeight(eraserHeight);
        MCastSender.setEraserWidth(eraserWidth);
        MCastSender.setPoints(points);
        MCastSender.setColor(color);
        
        if(textInput == null)
        {
        	if(!shape.equalsIgnoreCase("Text"))
        	{
	        	textInput = new ArrayList<>();
	        	textInput.add(new String("null"));
	        	MCastSender.setText(textInput);
        	}
        }
        else
        	MCastSender.setText(textInput);
        
        
        if(!shape.equalsIgnoreCase("Text"))
        	sender.sendValues();
        
        
    }

    
    public void mouseMoved(MouseEvent arg0)
    {
    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(shape.equalsIgnoreCase("Text")) 
		{
			if(textInput == null) 
			{
				textInput = new ArrayList<String>();
				textInput.add(new String());
			}
			
			c = e.getKeyChar();
			
			if(c == '\n') 
			{
//				textInput.add(new String());
				sender.sendValues();
				textInput = null;
			} 
			else 
			{
				int lastLine = textInput.size()-1;
				String lastStr = textInput.get(lastLine);
				textInput.set(lastLine, lastStr + c);
				MCastSender.setText(textInput);
			}
			
//			sender.sendValues();
			draw(xX1, yY1, xX2, yY2, choice, stroke, eraser, (ArrayList<Point>) points, textInput, eraserWidth, eraserHeight, color);
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) 
	{
		// TODO Auto-generated method stub
		    if(shape.equalsIgnoreCase("Free"))
	    	{
		    	points.add(new Point(me.getX(), me.getY()));
		    	draw(xX1, yY1, xX2, yY2, choice, stroke, eraser, (ArrayList<Point>) points, textInput, eraserWidth, eraserHeight, color);
	    	}
		    else
		    {
		        Color c = gc.getColor();
		        gc.setColor(BACKGROUND_COLOR);
		        gc.drawRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
		        gc.fillRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
		        gc.setColor(c);
		        repaint();
		    }
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		closePanel();
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}