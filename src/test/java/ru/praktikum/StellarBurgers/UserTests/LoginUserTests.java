package ru.praktikum.StellarBurgers.UserTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.StellarBurgers.ConstantsData;
import ru.praktikum.StellarBurgers.UserLogin;
import ru.praktikum.StellarBurgers.UserMethods;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("POST /api/auth/login : Авторизация пользователя")
public class LoginUserTests extends ConstantsData {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void shouldSuccessLoginUser(){
        Response response = userMethods.login(UserLogin.fromUser(user));
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue())
                .and()
                .body("refreshToken", notNullValue())
                .and()
                .body("user.email", equalTo(user.getEmail()))
                .and()
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Логин с пустым email")
    public void shouldntSuccessLoginUserWithoutEmail(){
        user.setEmail(null);
        Response response = userMethods.login(UserLogin.fromUser(user));
        String expectedMessage = "email or password are incorrect";
        response.then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Логин с пустым password")
    public void shouldntSuccessLoginUserWithoutPassword(){
            user.setPassword(null);
            Response response = userMethods.login(UserLogin.fromUser(user));
            String expectedMessage = "email or password are incorrect";
            response.then()
                    .statusCode(401)
                    .and()
                    .body("success", equalTo(false))
                    .and()
                    .assertThat()
                    .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void shouldntSuccessLoginUserWithIncorrectEmail(){
        user.setEmail(faker.internet().emailAddress());
        Response response = userMethods.login(UserLogin.fromUser(user));
        String expectedMessage = "email or password are incorrect";
        response.then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Логин с неверным password")
    public void shouldntSuccessLoginUserWithIncorrectPassword(){
        user.setPassword(faker.internet().password());
        Response response = userMethods.login(UserLogin.fromUser(user));
        String expectedMessage = "email or password are incorrect";
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
