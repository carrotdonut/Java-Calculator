/*
File Name:     CalculatorOpen.java
Name:          Kelly Jia
Class:         TEJ3M1-01
Date:          March 24, 2016
Description:   This program displays an opening screen for the calculator, displays some important
               notes about the calculator to the user, and opens the Calculator class
*/

//Import the packages to implement graphics
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorOpen implements MouseListener
{
   Drawing draw = new Drawing();    //Declre and create a draw object to use the repaint method for
                                    //the ImageIcons
   ImageIcon beginPic;              //Declare and create the ImageIcons to hold the title image, and notes image
   ImageIcon beginNotes;
   boolean pastBeginning = false;   //create a boolean to check if the user clicked past the title screen
   
   public CalculatorOpen()  //constructor
   {
      //Create and declare the JFrame, ImageIcons, and setup the JFrame
      JFrame frame = new JFrame("CalculatorOpen");
      beginPic = new ImageIcon("title.jpg");
      beginNotes = new ImageIcon("notes.jpg");
      frame.add(draw);
      draw.addMouseListener(this);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400,430);
      frame.setVisible(true);
   }
   
   //Output the images to the screen
   class Drawing extends JComponent
   {
      public void paint(Graphics g)
      {
         Graphics2D g2 = (Graphics2D) g;
         
         //if the user still hasn't clicked past the title screen, output the title screen
         if (!pastBeginning)
            g.drawImage(beginPic.getImage(),0,0,this);
         //If the user did click past the title screen, output the notes image
         else
         {
            g.drawImage(beginNotes.getImage(),0,0,this);
         
         }
      }//paint method
   }

   // starting implementing MouseListener 
   public void mousePressed(MouseEvent e)
   {
   }
   
   public void mouseReleased(MouseEvent e)
   {      
      // find coords of mouse click
      int row = e.getX();
      int col = e.getY();
      
      //If the user is still on the title screen and they click, take
      //them to the notes screen
      if (!pastBeginning)
      {
         pastBeginning = true;  
         draw.repaint();
      }
      //If the user is on the notes screen and they click, open the Calculator class
      else
      {      
         draw.repaint();
         new Calculator();
      }    
   }

   public void mouseClicked(MouseEvent e)
   {
   }

   public void mouseEntered(MouseEvent e)
   {
   }

   public void mouseExited(MouseEvent e)
   {
   }
   //finishing implementing MouseListener  
   
   //Main method: Call the CalculatorOpen class to display it to the user
   public static void main(String[] args)
   {
      new CalculatorOpen();
   }//main method

}//CalculatorOpen class