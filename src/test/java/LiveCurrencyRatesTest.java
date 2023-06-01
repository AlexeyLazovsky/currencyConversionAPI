
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class LiveCurrencyRatesTest {
    private static Response response;

    @BeforeAll
    public static void setup() {
        response = given()
                .queryParam("apikey", Consts.ACCESS_KEY)
                .get(Consts.BASE_URL + Consts.LIVE_ENDPOINT);
    }

    @Test
    public void getLiveCurrencyRatesTest() {
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));

        response.then().body("source", equalTo("USD"));
        response.then().body("quotes", notNullValue());


        response.then().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes.USDEUR", notNullValue());
       response.then().body("quotes.USDILS", notNullValue());
        response.then().body("quotes.USDRUB", notNullValue());
    }




}
