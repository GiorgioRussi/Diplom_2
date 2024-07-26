package ru.praktikum.StellarBurgers.OrdersTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.StellarBurgers.ConstantsData;
import ru.praktikum.StellarBurgers.Order;
import ru.praktikum.StellarBurgers.OrderMethods;
import ru.praktikum.StellarBurgers.UserMethods;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("POST /api/orders : Создание заказа")
public class CreateOrderTests extends ConstantsData {

    OrderMethods orderMethods;
    UserMethods userMethods;
    List<String> ingredientsHash;
    List<String> ingredients = new ArrayList<>();
    Order order;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        orderMethods = new OrderMethods();
        order = new Order(ingredients);
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @Test
    @DisplayName("Создание заказа: с авторизацией, с верным хешем ингредиентов")
    public void shouldCreateOrderValidIngredientsWithToken(){
        Response response = orderMethods.getIngredientList();
        ingredientsHash = response.then().extract().jsonPath().getList("data._id");
        ingredients.add(ingredientsHash.get(0));
        ingredients.add(ingredientsHash.get(1));
        order.setIngredients(ingredients);
        response = orderMethods.createWithToken(order, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("name", notNullValue())
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order.owner.name", equalTo(user.getName()))
                .and()
                .body("order.owner.email", equalTo(user.getEmail()))
                .and()
                .body("order.status", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа: без авторизации, с верным хешем ингредиентов")
    public void shouldCreateOrderValidIngredientsWithoutToken(){
        Response response = orderMethods.getIngredientList();
        ingredientsHash = response.then().extract().jsonPath().getList("data._id");
        ingredients.add(ingredientsHash.get(0));
        ingredients.add(ingredientsHash.get(1));
        order.setIngredients(ingredients);
        response = orderMethods.createWithoutToken(order);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("name", notNullValue())
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа: c авторизацией, без ингредиентов")
    public void shouldntCreateOrderNoIngredientsWithToken(){
        order.setIngredients(ingredients);
        Response response = orderMethods.createWithToken(order, accessToken);
        String expectedMessage = "Ingredient ids must be provided";
        response.then()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание заказа: без авторизации, без ингредиентов")
    public void shouldntCreateOrderNoIngredientsWithoutToken(){
        order.setIngredients(ingredients);
        Response response = orderMethods.createWithoutToken(order);
        String expectedMessage = "Ingredient ids must be provided";
        response.then()
                .statusCode(400).and().body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание заказа: c авторизацией, неверный хеш ингредиентов")
    public void shouldntCreateOrderIncorrectIngredientsWithToken(){
        ingredients.add(INCORRECT_HASHCODE);
        order.setIngredients(ingredients);
        orderMethods.createWithToken(order, accessToken).then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Создание заказа: без авторизации, неверный хеш ингредиентов")
    public void shouldntCreateOrderIncorrectIngredientsWithoutToken(){
        ingredients.add(INCORRECT_HASHCODE);
        order.setIngredients(ingredients);
        orderMethods.createWithoutToken(order).then()
                .statusCode(500);
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

}
