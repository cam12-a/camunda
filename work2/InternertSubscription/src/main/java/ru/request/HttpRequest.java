package ru.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import ru.models.ClientData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends ClientData{

    public String senHTTPRequest(String urlToSend)  {
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(urlToSend);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } catch (Exception ex) {
            ex.getMessage();
        }


        return response.toString();

    }

    public  void setDataFromJsonToUserForm(String response) throws JsonProcessingException, JSONException
    {

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(response);

        jsonNode=jsonNode.get("results");

        for (JsonNode jsonNode1 : jsonNode) {
            super.setCountry(jsonNode1.get("location").get("country").toString());
            this.setFirst_name(jsonNode1.get("name").get("first").toString());
            this.setLast_name(jsonNode1.get("name").get("last").toString());
        }


    }



}
