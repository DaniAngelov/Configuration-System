package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SERVER_URI = "http://localhost:8080/api/configs";
    private static final String FILE_NAME = "name";

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        //For simplicity there are 3 configurations, however a more generic approach can be added for multiple configs
        File devConfigs = new File("src/main/java/org/example/testData/configurationDev.properties");
        File stagingConfigs = new File("src/main/java/org/example/testData/configurationStaging.properties");
        File prodConfigs = new File("src/main/java/org/example/testData/configurationProd.properties");
        List<File> files = List.of(devConfigs
                , stagingConfigs
                , prodConfigs
        );
        Scanner scanner = null;

        for (File file : files) {
            scanner = new Scanner(file);
            JSONObject jsonObject = createJSONObject(scanner, file.getName());
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .headers("accept", "application/json", "Content-Type","application/json")
                    .uri(URI.create(SERVER_URI))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(response.body().equalsIgnoreCase("There is already such configuration!")){
                HttpRequest httpEditRequest = HttpRequest.newBuilder()
                        .PUT(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                        .headers("accept", "application/json", "Content-Type","application/json")
                        .uri(URI.create(SERVER_URI))
                        .build();

                HttpResponse<String> responseFromModification = httpClient.send(httpEditRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(responseFromModification);
                System.out.println(responseFromModification.body());
            }
        }
        scanner.close();
    }

    private static JSONObject createJSONObject(Scanner myReader, String fileName) {
        JSONObject newConfiguration = new JSONObject();
        newConfiguration.put(FILE_NAME, fileName);
        JSONArray configurationContentDTOS = new JSONArray();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String propertyName = data.split("=")[0];
            String propertyValue = data.split("=")[1];
            if (propertyName.equalsIgnoreCase("environmentType")) {
                newConfiguration.put(propertyName, propertyValue);
                continue;
            }
            JSONObject newProperty = new JSONObject();
            newProperty.put("propertyName", propertyName);
            newProperty.put("propertyValue", propertyValue);
            configurationContentDTOS.put(newProperty);
        }
        newConfiguration.put("configurationContents", configurationContentDTOS);
        return newConfiguration;
    }
}