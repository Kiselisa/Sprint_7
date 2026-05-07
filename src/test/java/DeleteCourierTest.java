import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLogin;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static data.CourierData.DELETE_COURIER;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class DeleteCourierTest extends BaseApiTest {

    private int courierId;

    @Before
    public void prepare() {
        CourierModel courier = CourierData.getRandomCourier();
        createCourier(courier);
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешное удаление курьера")
    public void testCreateAndDeleteCourierSuccess() {
        deleteCourier(courierId)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
        courierId = 0;
    }

    @Test
    @DisplayName("Получение ошибки при удалении курьера без указания ID")
    @Description("Тест падает, так как обнаружено расхождение между ОР (документация: 400 Bad Request) и ФР (сервер: 404 Not Found). Это является багом документации или сервера")
    public void testDeleteCourierWithoutIdError() {
        given()
                .baseUri(data.CourierData.BASE_URI)
                .when()
                .delete(DELETE_COURIER)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Получение ошибки при удалении курьера с несуществующим ID")
    public void testDeleteCourierWithWrongIdError() {
        int badId = 3000003;
        deleteCourier(badId)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет."));
    }
}
