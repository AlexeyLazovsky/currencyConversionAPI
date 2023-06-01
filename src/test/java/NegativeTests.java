import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NegativeTests {

    @Test
    public void testInvalidAPIKey() {
        String invalidKey = "invalidKey";
        Response response = given()
                .queryParam("access_key", invalidKey)
                .get(Consts.BASE_URL + Consts.LIVE_ENDPOINT);

        response.then().statusCode(401);
        response.then().body("message", equalTo("No API key found in request"));
    }



    @Test
    public void testNonExistentCurrencyCode() {
        Response response = given()
                .queryParam("access_key", Consts.ACCESS_KEY)
                .queryParam("currencies", "XYZ")
                .get(Consts.BASE_URL + Consts.LIVE_ENDPOINT);

        response.then().statusCode(401);

    }

    @Test
    public void testWithoutMandatoryField() {
        Response response = given()
                .header("apikey", Consts.ACCESS_KEY)
                .get(Consts.BASE_URL + Consts.HISTORICAL_ENDPOINT);

        response.then().statusCode(200);
        response.then().body("error.info", equalTo("You have not specified a date. [Required format: date=YYYY-MM-DD]"));
    }

    @Test
    public void testNonExistentEndpoint() {
        String invalidEndpoint = "/non_existent_endpoint";

        Response response = given()
                .header("apikey", Consts.ACCESS_KEY)
                .get(Consts.BASE_URL + invalidEndpoint);
        System.out.println(response.asString());

        response.then().statusCode(404);
        response.then().body("message", equalTo("no Route matched with those values"));
    }
    @Test
    public void testEmptyResults() {
        String futureDate = "2030-01-01";

        Response response = given()
                .header("apikey", Consts.ACCESS_KEY)
                .param("date", futureDate)
                .get(Consts.BASE_URL + Consts.LATEST_RATES_ENDPOINT);
        System.out.println(response.asString());

        response.then().statusCode(not(200));
    }






}

