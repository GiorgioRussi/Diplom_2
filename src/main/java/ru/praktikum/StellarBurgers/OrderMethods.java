package ru.praktikum.StellarBurgers;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class OrderMethods extends Constants {

    @Step("Создание заказа с токеном")
    public Response createWithToken(Order order, String token) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(order)
                .post("/api/orders");
    }

    @Step("Создание заказа без токена")
    public Response createWithoutToken(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/orders");

    }

    @Step("Получение списка ингредиентов")
    public Response getIngredientList() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/ingredients");
    }
}