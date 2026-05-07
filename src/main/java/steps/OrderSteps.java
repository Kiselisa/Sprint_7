package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа с цветом самоката: {order.color}")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов без параметров")
    public static Response getOrderList() {
        return given()
                .log().all()
                .baseUri(BASE_URI)
                .when()
                .get(GET_ORDERS)
                .then()
                .extract().response();
    }
}
