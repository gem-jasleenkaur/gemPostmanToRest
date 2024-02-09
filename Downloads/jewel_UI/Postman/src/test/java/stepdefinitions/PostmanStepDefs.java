package stepdefinitions;

import io.restassured.response.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static net.serenitybdd.rest.SerenityRest.given;

public class PostmanStepDefs
{

    private Response response;
    public static String filePath = "C:\\Users\\jasleen.multani\\Downloads\\jewel_UI\\Postman\\src\\test\\resources\\Demo.postman_collection.json";
    @Given("^user creates a new request named Simple get request request and sets endpoint$")
    public void setRequestForSimpleGetRequest()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read the JSON data from the file
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }

                // Parse the JSON data
                String jsonString = jsonStringBuilder.toString();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

                String url = String.valueOf(jsonObject.getAsJsonArray("item").get(0).getAsJsonObject().get("request").getAsJsonObject().get("url").getAsJsonObject().get("raw"));
                URL newUrl = new URL(url);
                response = given().when().get(newUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

