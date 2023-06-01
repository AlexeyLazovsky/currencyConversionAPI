import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class CurrencyLayerAPITest {


    @Test
    public void unauthorizedAccessTest() {
        Response response = given().get(Consts.BASE_URL + Consts.LIVE_ENDPOINT + "?apikey=" + Consts.INVALID_ACCESS_KEY);
        System.out.println(response.getBody().prettyPrint());
        response.then().statusCode(401);
        response.then().body("message", equalTo("Invalid authentication credentials"));
    }


    @Test
    public void getHistoricalCurrencyRatesTest() {
        String date = "2023-05-01";
        Map<String, Object> params = new HashMap<>();
        params.put("date", date);
        params.put("base", "USD");
        params.put("currencies", "CAD,EUR,ILS,RUB");

        Response response = given()
                .header("apikey", Consts.ACCESS_KEY)
                .queryParams(params)
                .get(Consts.BASE_URL + Consts.HISTORICAL_ENDPOINT);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes", notNullValue());

        //check if the response contains rates for the specified currencies
        response.then().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes.USDILS", notNullValue());
        response.then().body("quotes.USDRUB", notNullValue());
    }


}

