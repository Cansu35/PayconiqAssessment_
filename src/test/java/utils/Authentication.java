package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {

        public static String generateToken() {
            RequestSpecification herOku = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
            Map<String,Object > data = new HashMap<String,Object>();
            data.put("username", "admin");
            data.put("password", "password123");
            //Set the url
            herOku.pathParams("first", "auth");
            //Send the request and get the response / Request gonder  response elde et
            Response response    = given().spec(herOku).contentType(ContentType.JSON).body(data).when().post("/{first}");
            //response.prettyPrint();
            JsonPath json = response.jsonPath();

            return json.getString("token");
        }

        public static void main(String[] args) {
        System.out.println("b  : "+generateToken());
    }


    }


