import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.github.javafaker.Faker;
import com.google.gson.JsonObject;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";
    private static final String ENDPOINT_REGISTER_USER = "/auth/register";
    private static final String ENDPOINT_DELETE_USER = "/auth/user";

    public static void setup() {
        io.restassured.RestAssured.baseURI = BASE_URL;
    }

    public static String generateRandomEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String generateRandomName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    private static String userPassword = "123QWE";
    public static String getUserPassword() {
        return userPassword;
    }

    public static Response registerUser(String email, String userPassword, String name) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", userPassword);
        requestBody.addProperty("name", name);

        Response response = post(ENDPOINT_REGISTER_USER, requestBody.toString());

        return response;
    }

    public static Response deleteUser(String accessToken) {
        return deleteWithToken(ENDPOINT_DELETE_USER, accessToken);
    }

    private static Response post(String endpoint, String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    private static Response deleteWithToken(String endpoint, String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(endpoint);
    }
}