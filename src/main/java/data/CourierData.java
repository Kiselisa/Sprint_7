package data;
import com.github.javafaker.Faker;
import model.CourierModel;

public class CourierData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    static Faker user = new Faker();
    public static final String LOGIN = user.name().fullName();
    public static final String PASSWORD = user.internet().password(4, 6);
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static final String DELETE_COURIER = "/api/v1/courier/";

    public static CourierModel getRandomCourier() {
        Faker faker = new Faker();
        return new CourierModel(
                faker.name().firstName().toLowerCase() + faker.number().digits(3),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
}