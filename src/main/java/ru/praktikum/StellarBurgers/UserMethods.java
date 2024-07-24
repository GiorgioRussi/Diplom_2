package ru.praktikum.StellarBurgers;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class UserMethods extends Constants {

    @Step("Создать пользователя")
    public Response create(User user){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register");
    }

    @Step("Логин пользователя")
    public Response login(User user){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/login");
    }


}
