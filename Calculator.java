/*
File Name:     Calculator.java
Name:          Kelly Jia
Class:         TEJ3M1-01
Date:          March 24, 2016
Description:   This program displays a basic calculator with GUI implemented. 
               The calculator contains the following functions:
               - Clearing the display
               - The ability to switch sign (+/-) of the current number
               - Adding a decimal point into the number
               - Compute the % equivalent of the current number
               - Compute the square root of the current number
               - Compute the square of the current number
               - Compute the sin, cosine, and tangent values in degrees of the given number
               - Perform operations (add, subtract, multiply, divide)
               
               The majority of the graphics (JButtons, JPanel, FLowLayout, TextArea, GridLayout) is referenced
               from http://www.dreamincode.net/forums/topic/321933-creating-a-calculator-using-jframe/. 
               It has been modified and altered to fit my personal specifications.
               
               NOTE!!! If the program is not indented properly on another IDE (I found that this happens occasionally)
               please indent using the IDE's indent function.
*/

//Import the needed packages to implement the graphics component of the calculator
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener
{ 
   //Declaring all the global variables
   JPanel[] row = new JPanel[6];                      //Declare and create 6 JPanels, 1 for the display and 1 for each row of buttons
   final JButton[] btn = new JButton[24];             //Create all the buttons on the calculator
   String[] btnString = {"C","+/-","%","*","sqrt",    //Create a string array to hold all the symbols on the buttons
      "7","8","9","/","x^2",
      "4","5","6","+","sin",
      "1","2","3","-","cos",
      "0",".","=","tan"};
      
   Dimension answerDim = new Dimension(320,40);       //Declare and create dimensions for the answer display, the normal buttons, and the 0 button
   Dimension normalBtn = new Dimension(60,60);
   Dimension zeroBtn = new Dimension(121,60);
   
   boolean[] operation = new boolean[4];     //Create a boolean array for each operation: 0-multiply, 1-divide ,2-add,3-substract
   double[] nums = new double[2];            //Create a double array to hold the 2 temporary operands
   JTextArea answer = new JTextArea(1,12);   //Create a JTextArea to display the numbers that the user presses, and the answer
   boolean funcDone = false;
   boolean ifDec = false;
   
   Font fontAns = new Font("calibri", Font.BOLD, 28);  //Declare new fonts and colors for personlization purposes
   Font fontBtn = new Font("calibri", Font.BOLD, 17);
   Color color = new Color(51,204,255);

   public Calculator() //Constructor
   {
      //Set up the java window
      super("Calculator");
      setDesign();
      setSize(400,430);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      //Setup the grid layout, which is used to lay out the buttons
      GridLayout grid = new GridLayout(6,5);
      setLayout(grid);
      
      //Initialize all the operations to false
      for (int i=0; i < 4; i++)
         operation[i] = false;
        
      FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);       //Create a flow layout for the answer display area
      FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);   //Create another flow layout for all the buttons
      
      //Create a JPanel for the answer display, and each row of buttons
      for (int i=0; i <6; i++)
         row[i] = new JPanel();
      
      row[0].setLayout(f1); //Make the 1st row the flowlayout of the answer display area
      
      for (int i=1; i <6; i++)  //Use the 2nd flowlayout for the rest of the rows of buttons
         row[i].setLayout(f2);
      
      //Create all the buttons on the calculator
      //Add text to the buttons from the btnString array, and set their font
      //Add the action listener to the buttons on the calculator, so they can perform operations
      for (int i=0; i < 24; i++)
      {
         btn[i] = new JButton();
         btn[i].setText(btnString[i]);
         btn[i].addActionListener(this);
         btn[i].setName(i +"");
         btn[i].setBackground(color);
         btn[i].setFont(fontBtn);
      }
      
      //Making the answer display not inputtable by the keyboard, and making it display from right to left
      //Set up the bigger font for the display
      answer.setEditable(false);
      answer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
      answer.setFont(fontAns);
      
      //Setting the sizes of the display area and the buttons
      answer.setPreferredSize(answerDim);
      for (int i=0; i < 24; i++)
      {
         btn[i].setPreferredSize(normalBtn);
      }     
      btn[20].setPreferredSize(zeroBtn);
      
      //Add all the buttons to their corresponding panel, and add the panels to the frame
      row[0].add(answer);
      add(row[0]);
      
      for (int i=0; i < 5; i++)
         row[1].add(btn[i]);
      add(row[1]);
   
      for (int i=5; i < 10; i++)
         row[2].add(btn[i]);
      add(row[2]);
      
      for (int i=10; i <15; i++)
         row[3].add(btn[i]);
      add(row[3]);
      
      for (int i=15; i < 20; i++)
         row[4].add(btn[i]);
      add(row[4]);
      
      for (int i=20; i < 24; i++)
         row[5].add(btn[i]);
      add(row[5]);
      
      setVisible(true);
   }
   
   //Setting up the Calculator's design with Java 7's NimbusLookAndFeel
   public final void setDesign()
   {
      try
      {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
      }
      catch(Exception e)
      {
      
      }
   }
   
   //clear method:
   //Sets the display back to empty, and set all operations back to false
   public void clear()
   {
      for (int i=0; i < 4; i++)
         operation[i] = false;
     
      nums[0] = 0;
      nums[1] = 0;
     
      answer.setText("");
   }//clear method
   
   //percent method:
   //Displays the current value as its corresponding percentage (100 -> 1)
   public void percent()
   {
      //Gets the string value of the current display, parse it into
      //a double, divide by 100 to obtain the percentage value, and display it back
      //to the display
      String n = answer.getText();
     
      try
      {
         answer.setText(String.valueOf(Double.parseDouble(n)/100));
         
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
     
   }//percent method
   
   //posNeg method:
   //Switches the positive/negative sign of the current value
   public void posNeg()
   {
      //Gets the string value of the current display, parse it into
      //a double, multiply it by -1 to switch the sign, and display it back
      //to the display
      String n = answer.getText();
      
      try
      {
         answer.setText(String.valueOf(Double.parseDouble(n)*-1));           
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
   }//posNeg method
   
   //sqrt method:
   //Displays the square root of the current number
   public void sqrt()
   {
      //Gets the string value of the current display, parse it into
      //a double, compute the square root, and display it back
      //to the display
      String n = answer.getText();
      
      try
      {
         //If the user is attemtping to find the square root of a negative number,
         //display ERROR to the display area
         if (Double.parseDouble(n) < 0)
            answer.setText("ERROR");
         else
            answer.setText(String.valueOf(Math.sqrt(Double.parseDouble(n))));
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }//sqrt method
   
   //square method:
   //Displays the square of the current number
   public void square()
   {
      //Gets the string value of the current display, parse it into
      //a double, compute the square, and display it back
      //to the display
      String n = answer.getText();
     
      try
      {
         answer.setText(String.valueOf(Math.pow(Double.parseDouble(n),2)));
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }//square method
   
   //sine method:
   //Displays the sine value of the current number (degrees)
   public void sine()
   {
      //Gets the string value of the current display, parse it into
      //a double, convert the value to radians (as Java's math class works with radians), compute the sine 
      //and display it back to the display
      String n = answer.getText();
      
      try
      {
         answer.setText(String.valueOf(Math.sin(Math.toRadians((Double.parseDouble(n))))));
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }//sine method
   
   //cosine method:
   //Displays the cosine value of the current number (degrees)
   public void cosine()
   {
      //Gets the string value of the current display, parse it into
      //a double, convert the value to radians (as Java's math class works with radians), compute the cosine
      //and display it back to the display
      String n = answer.getText();
      try
      {
         answer.setText(String.valueOf(Math.cos(Math.toRadians((Double.parseDouble(n))))));
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }//cosine method
   
   //tangent method:
   //Displays the tangent value of the current number (degrees)
   public void tangent()
   {
      //Gets the string value of the current display, parse it into
      //a double, convert the value to radians (as Java's math class works with radians), compute the tangent
      //and display it back to the display
      String n = answer.getText();
      try
      {
         //If the user enters 45, then output 1 to the display
         //An extra if statement is needed as due to double's floating point, Java would normally
         //output 0.999999..
         if (Integer.parseInt(n) == 45)
            answer.setText("1");
         else
            answer.setText(String.valueOf(Math.tan(Math.toRadians((Double.parseDouble(n))))));
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }//tangent method
   
   //finalAns method:
   //Displays the final answer after computing operations
   public void finalAns()
   {
      //Get the current number on the display, parse it to a double, and set it as
      //the 2nd operand
      
      //Checks which operation the user wants to do, and perform that operation
      //Display the answer to the display area, set the operation back to false, and
      //make the answer the 1st operand so the user can continue using the number
      try
      {
         nums[1] = Double.parseDouble(answer.getText());
         double ans=0;
      
         if (operation[0]) //multiply
         {
            ans = nums[0]*nums[1];
         
            if ((int)ans == ans)
               answer.setText(String.valueOf((int)ans));
            else
               answer.setText(String.valueOf(ans));
         
            operation[0] = false;
            nums[0] = ans;
            nums[1] = 0;       
         }
         else if (operation[1]) //divide
         {
         //If the user tries to divide a number by 0, display ERROR
            if (nums[1] == 0)
               answer.setText("ERROR");
            else
            {
               ans = nums[0]/nums[1];
            
               if ((int)ans == ans)
                  answer.setText(String.valueOf((int)ans));
               else
                  answer.setText(String.valueOf(ans));
            }
            operation[1] = false;
            nums[0] = ans;
            nums[1] = 0;  
         }
         else if (operation[2]) //add
         {
            ans = nums[0] + nums[1];
         
            if ((int)ans == ans)
               answer.setText(String.valueOf((int)ans));
            else
               answer.setText(String.valueOf(ans));
         
            operation[2] = false;
            nums[0] = ans;
            nums[1] = 0;  
         }
         else if(operation[3]) //subtract
         {
            ans = nums[0] - nums[1];
         
            if ((int)ans == ans)
               answer.setText(String.valueOf((int)ans));
            else
               answer.setText(String.valueOf(ans));
         
            operation[3] = false;
            nums[0] = ans;
            nums[1] = 0;  
         }
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
      funcDone = true;
   }
   
   //ifClr method:
   //After the user performs a function on a number, the next time they press more numbers
   //Reinitialize the display so they don't add on digits to the curent answer
   public void ifClr()
   {
      if (funcDone)
      {
         answer.setText("");
         funcDone = false;
      }
   }//ifClr method
   
   //actionPerformed method:
   //This is where the program finds which button the user presses, and calls appropriate methods
   //to perform the button's function and operations
   public void actionPerformed(ActionEvent ae)
   {
      //If the user pressed any of the function buttons (clear, posNeg, percent, sqrt, square, sine
      //cosine, tangent, or =), then call the corresponding method to perform the function
      if (ae.getSource() == btn[0])
         clear();     
      else if (ae.getSource() == btn[1])
         posNeg();     
      else if (ae.getSource() == btn[2])
         percent();     
      else if (ae.getSource() == btn[4])
         sqrt();
      else if(ae.getSource() == btn[9])
         square();
      else if(ae.getSource() == btn[14])
         sine();
      else if(ae.getSource() == btn[19])
         cosine();
      else if(ae.getSource() == btn[23])
         tangent();
      else if(ae.getSource() == btn[22])
         finalAns();
      
      //If the user pressed any of the numbers, first call the ifClr method to see if the user
      //just finished an operation, and then add their number to the end of the display
      if (ae.getSource() == btn[5])
      {
         ifClr();
         answer.append("7");
      }
      if(ae.getSource() == btn[6])
      {
         ifClr();
         answer.append("8");
      }
      if(ae.getSource() == btn[7])
      {
         ifClr();
         answer.append("9");
      }
      if(ae.getSource() == btn[10])
      {
         ifClr();
         answer.append("4");
      }
      if(ae.getSource() == btn[11])
      {
         ifClr();
         answer.append("5");
      }
      if(ae.getSource() == btn[12])
      {
         ifClr();
         answer.append("6");
      }
      if(ae.getSource() == btn[15])
      {
         ifClr();
         answer.append("1");
      }
      if(ae.getSource() == btn[16])
      {
         ifClr();
         answer.append("2");
      }
      if(ae.getSource() == btn[17])
      {
         ifClr();
         answer.append("3");
      }
      if(ae.getSource() == btn[20])
      {
         ifClr();
         answer.append("0");
      }
      if(ae.getSource() == btn[21])
      {
         ifClr();                                       //Restrict the user from adding more than 1 decimal point to 
         if ((answer.getText()).indexOf(".") == -1)     //the number on the display
            answer.append(".");
      }
      
      //If the user pressed any of the operation buttons (multiply, divide, add, subtract)
      //Make their current number the 1st operand, and set the corresponding operation boolean to true
      try
      {
         if (ae.getSource() == btn[3]) //multiply
         {
            nums[0] = Double.parseDouble(answer.getText());
            operation[0] = true;
            operation[1] = false;
            operation[2] = false;
            operation[3] = false;
            funcDone = true;       
         }
         else if (ae.getSource() == btn[8]) //divide
         {
            nums[0] = Double.parseDouble(answer.getText());
            operation[0] = false;
            operation[1] = true;
            operation[2] = false;
            operation[3] = false;
            funcDone = true;
         }
         else if(ae.getSource() == btn[13]) //add
         {
            nums[0] = Double.parseDouble(answer.getText());
            operation[0] = false;
            operation[1] = false;
            operation[2] = true;
            operation[3] = false;
            funcDone = true;  
         }
         else if(ae.getSource() == btn[18]) //subtract
         {
            nums[0] = Double.parseDouble(answer.getText());
            operation[0] = false;
            operation[1] = false;
            operation[2] = false;
            operation[3] = true;
            funcDone = true;  
         }
      }
      //Incase the user presses the button when the display is empty, catch the
      //NumberFormatException as the value would be null
      catch (NumberFormatException e)
      {
      }
   }
   
   //Call the Calculator class in the main method to display the calculator
   //to the user
   public static void main(String[] args)
   {
      new Calculator();
   }//main method
   
}