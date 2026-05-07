package data;

import com.github.javafaker.Faker;
import model.OrderModel;

import java.util.List;

public class OrderData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final String CREATE_ORDER_PATH = "/api/v1/orders";
    public static final String GET_ORDERS = "/api/v1/orders";

    static Faker order = new Faker();

    public static OrderModel getDefaultOrder(List<String> color) {
        return new OrderModel(
                order.name().firstName(),
                order.name().lastName(),
                order.address().streetAddress(),
                "6",
                order.phoneNumber().subscriberNumber(10),
                3,
                "2024-06-06",
                order.lorem().sentence(3),
                color
        );
    }
}
