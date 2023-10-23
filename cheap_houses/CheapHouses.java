/**
 * Author:  Sherali Ozodov
 * File name: CheapHouses.java
 * Course:  CSC 210
 *
 * The program CheapHouses reads a csv file(which is houses.csv by default), 
 * which includes address, price, latitude/longitude coordinates in a country.
 * The user entered the price of a house and houses that cost less than this 
 * price are identified. Their location should be plotted as small filled circles
 * in a window to visualize their density and relative locations. The program
 * has a simple GUI that allows users to specify the CSV file to be read and
 * the cutoff price for the visualization. After all, if the file is found, every 
 * time when the user change the cutoff price and press the button "plot", the 
 * number of small plotted circles should change according to availability of 
 * houses below than cutoff price in csv file.
 * 
 */


package package1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CheapHouses {
	// The public class CheapHouses implements the program and it contains, 
	// main, createAndShowGUI, and public static class Content.
	
	// First, static integer, HashMap and GPanel are declared.
    static GPanel gpanel;
    static int price_int_private;
    static HashMap < Double, Double > hmap_complete = new HashMap < > ();

    public static void main(String[] args) {
    	createAndShowGUI();
    }
    
    public static void createAndShowGUI() {
    	// In this method, one JFrame; two JPanels which are mainPanel and widgetsPanel;
    	// JLabel for file and price; one JTextField for price and JButton
    	 JFrame mainFrame = new JFrame("Home Price Distribution");
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         mainFrame.setSize(850, 650);

         JPanel mainPanel = new JPanel(null);
         JPanel widgetsPanel = new JPanel();
         widgetsPanel.setLocation(0, 580 - 22);
         widgetsPanel.setSize(850, 82);
         widgetsPanel.setBackground(new Color(255, 229, 204));

         JLabel labelFile = new JLabel("File:");
         labelFile.setBounds(160, 575, 70, 30);
         JTextField tFieldHouses = new JTextField("houses.csv");
         tFieldHouses.setBounds(205, 575, 140, 30);
         JLabel labelPrice = new JLabel("Price:");
         labelPrice.setBounds(390, 575, 70, 30);
         JTextField tPrice = new JTextField("");
         tPrice.setBounds(435, 575, 140, 30);

         JButton plotButton = new JButton("Plot");
         plotButton.setBounds(620, 575, 100, 30);

         plotButton.addActionListener(new ActionListener() {
        	 // It adds addActionListener to the plot button to make actions after
        	 // the button is clicked.
             public void actionPerformed(ActionEvent e) {
            	 // First, the price entered by the user is assigned to price_str_private variable,
            	 // and string is converted to an integer and assigned to price_int_private variable.
            	 // If anything except number is entered to price field,it return "Enter an integer only"
                 String price_str_private;
                 try {
                     price_str_private = tPrice.getText();
                     price_int_private = Integer.parseInt(price_str_private);
                 } catch (Exception exe) {
                     System.out.println("Enter an integer only:");
                 }
                 // Then,it reads the file and add information to an array. 
                 // If the file does not exist, it returns "Error: File not found".
                 List < List < String >> line_array = new ArrayList < > ();
                 try {
                     Scanner tfile = new Scanner(new File("/Users/sheraliozodov/Downloads/" + tFieldHouses.getText()));
                     String s = tfile.nextLine();
                     while (tfile.hasNext()) {
                         s = tfile.nextLine();
                         String[] values = s.split(",");
                         line_array.add(Arrays.asList(values));
                     }
                 } catch (java.io.FileNotFoundException ex) {
                     System.out.println("Error: File not found");
                 }
                 // After that, it gets the array, make filters according cutoff price entered by the user.
                 // It then adds latitude and longitude to a map.
                 double latitude_double = 0;
                 double longitude_double = 0;
                 int price = 0;
                 hmap_complete = new HashMap < > ();
                 for (List < String > s: line_array)
                     if (!s.get(9).contains("price")) {
                         price = Integer.parseInt(s.get(9));
                         // at that points, it compares the cutoff price by the user with house
                         // prices in a csv file.
                         if (price < price_int_private) {
                             if (!s.get(10).contains("latitude") & !s.get(11).contains("longitude")) {
                                 latitude_double = Double.parseDouble(s.get(10));
                                 longitude_double = Double.parseDouble(s.get(11));
                                 hmap_complete.put(latitude_double, (longitude_double));
                             }
                         }
                     }
                 // it then prints out the graphics.
                 GPanel.isDraw = true;
                 gpanel.paintImmediately(gpanel.getBounds());
             }
         });
         // It creates an instance of GPanel, set bounds, border and background.
         // Then, gpanel is added to mainPanel.
         gpanel = new GPanel();
         gpanel.setBounds(0, 0, 850, 580 - 22);
         gpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
         gpanel.setBackground(Color.white);
         mainPanel.add(gpanel);

         // after that, JLabelJ, TextField and JButton are added to mainPanel.
         // mainPanel is also added mainFrame.
         mainPanel.add(labelFile);
         mainPanel.add(tFieldHouses);
         mainPanel.add(labelPrice);
         mainPanel.add(tPrice);
         mainPanel.add(plotButton);
         mainPanel.add(widgetsPanel);
         mainFrame.add(mainPanel);
         mainFrame.setVisible(true);
    }

    public static class GPanel extends JPanel {
    	// In GPanel, GUI section is implemented.
        static Boolean isDraw = false;
        
        public GPanel() {
            Graphics g = getGraphics();
        }
        public void paintComponent(Graphics g) {
        	// When plot button is pressed, boolean variable isDraw becomes true,
        	// and allow to visualize plots.
            if (isDraw) {
                if (!hmap_complete.isEmpty()) {
                	// After the cutoff price is entered, it adds latitude and longitude points 
                	// to a map. At that point, it checks if that map is not empty.
                	// To scale latitude and longitude points, first minimum and maximum 
                	// of latitude and longitude points as well as their differences are
                	// found are assigned to variables.
                    Double max_lat = Collections.max(hmap_complete.keySet());
                    Double min_lat = Collections.min(hmap_complete.keySet());
                    Double diff_lat = max_lat - min_lat;

                    Double max_long = Collections.max(hmap_complete.values());
                    Double min_long = Collections.min(hmap_complete.values());
                    Double diff_long = max_long - min_long;
                    // Lastly it loops through the the map hmap_complete, and 
                    // does scaling to coordinates and prints them are plotted
                    // as small filled circles.
                    for (Double key: hmap_complete.keySet()) {
                        Double cur_lat = (key - min_lat) / diff_lat * 650-100;
                        Double cur_long = (hmap_complete.get(key) - min_long) / diff_long * 850;
                        g.fillOval((int) Math.round(cur_lat), (int) Math.round(cur_long), 6, 6);
                    }
                } else {
                    super.paintComponent(g);
                }
            }
        }
    }
}