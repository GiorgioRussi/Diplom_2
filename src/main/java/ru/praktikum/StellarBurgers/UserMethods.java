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

    @Step("Получение информации о пользователе с токеном")
    public Response getInfoWithToken(String token){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .get("/api/auth/user");
    }

    @Step("Получение информации о пользователе без токена")
    public Response getInfoWithoutToken(){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/auth/user");
    }

    @Step("Изменение данных пользователя с токеном")
    public Response changeInfoWithToken(User user, String token){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(user)
                .patch("/api/auth/user");
    }
    @Step("Изменение данных пользователя без токена")
    public Response changeInfoWithoutToken(User user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Получение заказов пользователя с токеном")
    public Response getOrderListWithToken(String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .get("/api/orders");
    }

    @Step("Получение заказов пользователя без токена")
    public Response getOrderListWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/orders");
    }

}
