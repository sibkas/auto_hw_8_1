package data;

import com.github.javafaker.Faker;

public class DataHelper {
    private static final Faker FAKER = new Faker();

    private static final String VALID_LOGIN = "vasya";
    private static final String UNENCRYPTED_PASSWORD = "qwerty123";
    private static final String HASHED_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aab9099955383569421f1d1";

    public static String getValidLogin() {
        return "vasya";
    }

    public static String getUnencryptedPassword() {
        return "qwerty123";
    }

    public static String getHashedPassword() {
        return "5e884898da28047151d0e56f8dc6292773603d0d6aab9099955383569421f1d1";
    }


    private DataHelper() { }

    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }


}
