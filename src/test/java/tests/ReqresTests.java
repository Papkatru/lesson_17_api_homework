package tests;

import base.TestBase;
import models.LombokReqresRequest;
import models.LombokReqresResponse;
import models.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.Is.is;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class ReqresTests extends TestBase {

    @Test
    @DisplayName("Получение списка пользователей с использованием Groovy")
    public void getListUsersWithGroovy() {
        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("george.edwards@reqres.in"));
    }

    @Test
    @DisplayName("Получение списка пользователей")
    public void getListUsers() {
        given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data[0]", hasKey("id"));
    }

    @Test
    @DisplayName("Получение одного пользователя")
    public void getOneUsers() {
        UserData responseBody = given()
                .filter(withCustomTemplates())
                .log().all()
                .when()
                .get("/users/12")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.email", is("rachel.howell@reqres.in"))
                .extract().as(UserData.class);
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void successAuth() {
        LombokReqresRequest requestBody = new LombokReqresRequest();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");
        LombokReqresResponse response = given()
                .with().spec(loginRequestSpec)
                .body(requestBody)
                .when()
                .post()
                .then()
                .spec(loginResponseSpec)
                .extract()
                .as(LombokReqresResponse.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @Test
    @DisplayName("Неуспешная авторизация")
    public void failureAuth() {
        LombokReqresRequest requestBody = new LombokReqresRequest();
        requestBody.setEmail("eve.holt@reqres.in");
        given()
                .with().spec(loginRequestSpec)
                .body(requestBody)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUser() {
        LombokReqresRequest requestBody = new LombokReqresRequest();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");
        LombokReqresRequest responseBoby = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("first_name", is("morpheus"))
                .extract().as(LombokReqresRequest.class);
    }
}
