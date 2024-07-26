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

public class ChangeUserData extends ConstantsData {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @DisplayName("Изменение email пользователя (с авторизацией)")
    @Test
    public void shouldUpdateUserEmail(){
        userMethods.login(UserLogin.fromUser(user));
        user.setEmail(faker.internet().emailAddress());
        Response response = userMethods.changeInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(user.getEmail()))
                .and()
                .body("user.name", equalTo(user.getName()));
    }

    @DisplayName("Изменение password пользователя (с авторизацией)")
    @Test
    public void shouldUpdateUserPassword(){
        userMethods.login(UserLogin.fromUser(user));
        user.setEmail(faker.internet().password());
        Response response = userMethods.changeInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(user.getEmail()))
                .and()
                .body("user.name", equalTo(user.getName()));
    }

    @DisplayName("Изменение name пользователя (с авторизацией)")
    @Test
    public void shouldUpdateUserName(){
        userMethods.login(UserLogin.fromUser(user));
        user.setEmail(faker.name().username());
        Response response = userMethods.changeInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(user.getEmail()))
                .and()
                .body("user.name", equalTo(user.getName()));
    }

    @DisplayName("Изменение email пользователя (без авторизации)")
    @Test
    public void shouldntUpdateUserEmailWithoutToken(){
        user.setEmail(faker.internet().emailAddress());
        String expectedMessage = "You should be authorised";
        Response response = userMethods.changeInfoWithoutToken(user);
        response.then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @DisplayName("Изменение password пользователя (без авторизации)")
    @Test
    public void shouldntUpdateUserPasswordWithoutToken(){
        user.setPassword(faker.internet().password());
        String expectedMessage = "You should be authorised";
        Response response = userMethods.changeInfoWithoutToken(user);
        response.then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @DisplayName("Изменение name пользователя (без авторизации)")
    @Test
    public void shouldntUpdateUserNameWithoutToken(){
        user.setName(faker.name().username());
        String expectedMessage = "You should be authorised";
        Response response = userMethods.changeInfoWithoutToken(user);
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
