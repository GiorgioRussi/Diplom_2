package ru.praktikum.StellarBurgers;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class OrderMethods extends Constants {

    @Step("Создание заказа")
    public Response create (Order order){
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/orders");
    }


}
