import io.restassured.response.Response;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ConversionTest {

    @Test
    public void convertCurrencyTest() {
        String fromCurrency = "USD";
        String toCurrency = "CAD";
        int amount = 11;

        Response response = given()
                .header("apikey", Consts.ACCESS_KEY)
                .queryParam("from", fromCurrency)
                .queryParam("to", toCurrency)
                .queryParam("amount", amount)
                .get(Consts.BASE_URL + "/convert");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("query.from", equalTo(fromCurrency));
        response.then().body("query.to", equalTo(toCurrency));
        response.then().body("query.amount", equalTo(amount));
        response.then().body("result", notNullValue());
        response.then().body("info.quote", notNullValue());
    }

}
