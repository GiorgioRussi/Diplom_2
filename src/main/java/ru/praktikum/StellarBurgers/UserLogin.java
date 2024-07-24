package ru.praktikum.StellarBurgers;

public class UserLogin {
    private String email;
    private String password;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserLogin fromUser(User user) {
        return new UserLogin(user.getEmail(), user.getPassword());
    }

    @Override
    public String toString() {
        return "UserLoginDetails{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
