package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLogin;
import model.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание нового курьера: login={courier.login}, password={courier.password}")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин курьера в системе: login={credentials.login}, password={credentials.password}")
    public static Response loginCourier(CourierLogin credentials) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(LOGIN_COURIER)
                .then()
                .extract().response();
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int id) {
        return given()
                .log().all()
                .baseUri(data.CourierData.BASE_URI)
                .when()
                .delete(DELETE_COURIER + id)
                .then()
                .extract().response();
    }

    @Step("Получение ID курьера")
    public static int getCourierId(CourierLogin credentials) {
        return loginCourier(credentials)
                .then()
                .extract()
                .path("id");
    }

}
