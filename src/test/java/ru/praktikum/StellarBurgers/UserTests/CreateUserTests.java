package ru.praktikum.StellarBurgers.UserTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.StellarBurgers.ConstantsData;
import ru.praktikum.StellarBurgers.UserMethods;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("POST /api/auth/register : Регистрация пользователя")
public class CreateUserTests extends ConstantsData {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
    }

    @Test
    @DisplayName("Создать уникального пользователя")
    public void shouldCreateUser(){
        Response response = userMethods.create(user);
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
        accessToken = response.path("accessToken").toString();
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void shouldntCreateAlreadyExistUser(){
        Response response = userMethods.create(user);
        accessToken = response.path("accessToken").toString();
        String expectedMessage = "User already exists";
        userMethods.create(user).then()
                .statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создать пользователя без поля 'email'")
    public void shouldntCreateUserWithoutEmail(){
        user.setEmail(null);
        Response response = userMethods.create(user);
        String expectedMessage = "Email, password and name are required fields";
        response.then()
                .statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создать пользователя без поля 'password'")
    public void shouldntCreateUserWithoutPassword() {
        user.setPassword(null);
        Response response = userMethods.create(user);
        String expectedMessage = "Email, password and name are required fields";
        response.then()
                .statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создать пользователя без поля 'name'")
    public void shouldntCreateUserWithoutName() {
        user.setName(null);
        Response response = userMethods.create(user);
        String expectedMessage = "Email, password and name are required fields";
        response.then()
                .statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .assertThat()
                .body("message", equalTo(expectedMessage));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }
}
