package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class HerOkuAppBaseUrl {
    protected RequestSpecification herOku;

    @BeforeMethod

        public void setUp() {
        herOku = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();

    }

        }

