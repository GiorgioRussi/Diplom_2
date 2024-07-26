package ru.praktikum.StellarBurgers;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class ConstantsData {

    public Faker faker = new Faker();

    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().username();
    public  User user = new User(email, password, name);
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static String INCORRECT_HASHCODE = RandomStringUtils.random(24, true, true);

}
