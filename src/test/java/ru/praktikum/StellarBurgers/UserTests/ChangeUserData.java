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

@DisplayName("PATCH /api/auth/user : Изменение данных пользователя")
public class ChangeUserData extends ConstantsData {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @Test
    @DisplayName("Изменение email пользователя (с авторизацией)")
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

    @Test
    @DisplayName("Изменение password пользователя (с авторизацией)")
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

    @Test
    @DisplayName("Изменение name пользователя (с авторизацией)")
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

    @Test
    @DisplayName("Изменение email пользователя (без авторизации)")
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

    @Test
    @DisplayName("Изменение password пользователя (без авторизации)")
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

    @Test
    @DisplayName("Изменение name пользователя (без авторизации)")
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
