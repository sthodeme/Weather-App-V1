/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sreeram.weatherapp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class WeatherAppUI extends JFrame{

    //public static Object tempTodayTextField;
    APICaller apiCaller = new APICaller();

    public WeatherAppUI() {
        //System.out.println("Weather App UI");
        JFrame frame = new JFrame("Weather App V1.0");
        frame.setSize(265, 270);
        frame.setBackground(Color.WHITE);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setVisible(true);

        JButton tempRequestButton = new JButton("Current Temperature");
        tempRequestButton.setBounds(20, 20, 200, 30);
        tempRequestButton.setBackground(Color.LIGHT_GRAY);
        frame.add(tempRequestButton);

        JTextField locationTextField = new JTextField();
        locationTextField.setBounds(20, 80, 225, 30);
        frame.add(locationTextField);

        
        JTextField dateTextField = new JTextField();
        dateTextField.setBounds(20, 111, 225, 30);
        frame.add(dateTextField);

        JTextField timeTextField = new JTextField();
        timeTextField.setBounds(20, 142, 225, 30);
        frame.add(timeTextField);

        JTextField tempTodayTextField = new JTextField();
        tempTodayTextField.setBounds(20, 190, 225, 30);
        frame.add(tempTodayTextField);

        ActionListener askTemp = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tempRequestButton) {

                    //display the location
                    locationTextField.setText("Preset Location: Cologne");

                    //collet and display current day
                    LocalDateTime date = LocalDateTime.now();
                    DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                    String dateText = "Today's Date: " + date.format(dayFormatter);
                    dateTextField.setText(dateText);

                    //collect and display the current time
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    String timeText = "Current Time: " + date.format(timeFormatter);
                    timeTextField.setText(timeText);

                    double tempToday = 0;
                    
                    try {
                        tempToday = apiCaller.apiCall();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //System.out.println("Today's Temperature Button Clicked");
                    String temperature = "Current Temperature: " + tempToday + "°C";
                    tempTodayTextField.setText(temperature);
                }
                
            }
        };  // Declare an ActionListener

        tempRequestButton.addActionListener(askTemp);


        frame.setVisible(true);

    }

}
