package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class Main {

    public static void saveToFile(String s){
        try {
            File file = new File("data.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(s + '\n');
            buffer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException{

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("apiKey", ""); // please enter your own api key in quotation marks
        parameters.put("country", "pl");
        parameters.put("category", "business");
        parameters.put("from", "2022-02-10");

        String stringUrl = "?country=" + parameters.get("country") +
                "&category=" + parameters.get("category") +
                "&from=" + parameters.get("from") +
                "&apiKey=" + parameters.get("apiKey");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("https://newsapi.org/v2/top-headlines" + stringUrl))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObj = new JSONObject(response.body());
        JSONArray arrObj = jsonObj.getJSONArray("articles");

        for (int i = 0; i < arrObj.length(); i++) {
            String author = arrObj.getJSONObject(i).get("author").toString();
            String description = arrObj.getJSONObject(i).get("description").toString();
            String title = arrObj.getJSONObject(i).get("title").toString();
            String tmp = title + ":" + description + ":" + author;
            saveToFile(tmp);
        }
    }

}
