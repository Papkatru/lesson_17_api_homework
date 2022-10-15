package base;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    @Step("Установка настроек браузера")
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}
