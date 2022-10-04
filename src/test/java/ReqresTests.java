import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.Is.is;

public class ReqresTests {

    @Test
    @DisplayName("Получение списка пользователей")
    public void getListUsers() {
        String uri = "https://reqres.in/api/users?page=2";
        given()
                .log().all()
                .when()
                .get(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("data[0]", hasKey("id"));
    }

    @Test
    @DisplayName("Получение одного пользователя")
    public void getOneUsers() {
        String uri = "https://reqres.in/api/users/12";
        given()
                .log().all()
                .when()
                .get(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("data.email", is("rachel.howell@reqres.in"));
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void successAuth() {
        BodyReqresRequests requestsBody = new BodyReqresRequests("eve.holt@reqres.in", "cityslicka");
        String uri = "https://reqres.in/api/login";
        given()
                .log().all()
                .contentType(JSON)
                .body(requestsBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    public void failureAuth() {
        BodyReqresRequests requestsBody = new BodyReqresRequests("eve.holt@reqres.in");
        String uri = "https://reqres.in/api/login";
        given()
                .log().all()
                .contentType(JSON)
                .body(requestsBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUser() {
        BodyReqresRequests requestsBody = new BodyReqresRequests();
        requestsBody.setName("morpheus").setJob("leader");
        String uri = "https://reqres.in/api/users";
        given()
                .log().all()
                .contentType(JSON)
                .body(requestsBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"));
    }
}
