package ru.praktikum.StellarBurgers.OrdersTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.StellarBurgers.ConstantsData;
import ru.praktikum.StellarBurgers.OrderMethods;
import ru.praktikum.StellarBurgers.UserLogin;
import ru.praktikum.StellarBurgers.UserMethods;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


@DisplayName("GET /api/orders : Получение списка заказов пользователя")
public class GetUserOrderListTests extends ConstantsData {

    UserMethods userMethods;
    OrderMethods orderMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        orderMethods = new OrderMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void shouldGetUserOrderListWithToken(){
        userMethods.login(UserLogin.fromUser(user));
        Response response = userMethods.getOrderListWithToken(accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("orders", notNullValue())
                .and()
                .body("total", notNullValue())
                .and()
                .body("totalToday", notNullValue());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void shouldGetUserOrderListWithoutToken(){
        String expectedMessage = "You should be authorised";
        Response response = userMethods.getOrderListWithoutToken();
        response.then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

}
