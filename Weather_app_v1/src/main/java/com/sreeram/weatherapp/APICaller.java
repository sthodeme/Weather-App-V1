package com.sreeram.weatherapp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APICaller{

    // boolean flag to call API again & update JSON file 'weather_data.json', if the file is older than 60 minutes
    boolean updateFile = true;

    //minimum time difference to call the API again
    final long MIN_TIME_DIFFERENCE = 60;

    // File object to store the JSON data
    File storedFile = new File("weather_data.json");

    Gson gson = new Gson();
    Gson formattedGson = new GsonBuilder().setPrettyPrinting().create();

    DailyWeatherReport dwr;       //to store the data from the API call
    DailyWeatherReport dwrActual; //to store the actual data from the JSON file



    //chekc if the file exists and is not older than 60 minutes
    public APICaller() {
        if (storedFile.exists()) {
            System.out.println("---------------------------------------------");
            System.out.println("");
            System.out.print("JSON File exists, ");
            long lastModified = storedFile.lastModified();
            long currentTime = System.currentTimeMillis();
            long timeDifference = currentTime - lastModified;
            long timeDifferenceInMinutes = timeDifference / 60000;
            System.out.println(" and was saved " + timeDifferenceInMinutes + " minutes ago.");
            System.out.println("");
            if (timeDifferenceInMinutes < MIN_TIME_DIFFERENCE) {
                System.out.println("File is not older than " + MIN_TIME_DIFFERENCE + " minutes. So, no need to call the API.");
                updateFile = false;
            } 
            System.out.println("");
            System.out.println("---------------------------------------------");
        } 
    }
    
    
        public double apiCall() throws IOException, InterruptedException {
            if (updateFile) {       //if the file is older than 60 minutes, call the API
                HttpClient client = HttpClient.newHttpClient();
                System.out.println(client);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude=50.9333&longitude=6.95&hourly=temperature_2m,rain&forecast_days=1"))
                        //.uri(URI.create("https://catfact.ninja/fact"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                System.out.println("");
                System.out.println("---------------------------------------------");
                System.out.println("");
                //System.out.println("Status Code: " + response.statusCode());
                System.out.println("Response Body: " );
                System.out.println(response.body());
                System.out.println("");
                System.out.println("---------------------------------------------");
                
                dwr = gson.fromJson(response.body(), DailyWeatherReport.class);
                //System.out.println("---------------------------------------------");
                System.out.println("");
                String dwrString = formattedGson.toJson(dwr);
                System.out.println("dwr (Daily Weather Report): ");
                System.out.println(dwr);
                System.out.println("");
                
                System.out.println("");
                //Gson formattedGson = new GsonBuilder().setPrettyPrinting().create();

                System.out.println("--------------- save the daily report to a file --------------------");
                System.out.println("");
                //call function to save the data to a file
                saveToFile(dwr);
                System.out.println("");
                System.out.println("Date/time: " + dwr.getHourly().getTime());
                System.out.println("");
                System.out.println("Temperature: " + dwr.getHourly().getTemperature2m());
                System.out.println("");
                System.out.println("--------------------------------------------------------------------");
                System.out.println("");
                
            } 
            //calculating index to get the current temperature
            LocalTime now = LocalTime.now();
            int nowHour = now.getHour();
            int nowMinute = now.getMinute();
            if (nowMinute >= 30) {
                nowHour++;
                if (nowHour == 24) {
                    nowHour = 0;
                }
            }

            //create 'DailyWeatherReport' Object from the JSON file
            try (FileReader reader = new FileReader(storedFile)) {
                // Convert the JSON to a DailyWeatherReport object
                dwrActual = gson.fromJson(reader, DailyWeatherReport.class);
                if (dwrActual == null ) {
                    System.out.println("dwrActual is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Double.NaN;
                }

            double currentTemperature = dwrActual.getHourly().getTemperature2m().get(nowHour);

            return currentTemperature;
        }
    

    // Method to save the data to a file
    private void saveToFile(DailyWeatherReport dwr) throws IOException {
        try (Writer writer = new FileWriter(storedFile)) {
            formattedGson.toJson(dwr, writer);
            System.out.println("Data has been saved to 'weather_data.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
