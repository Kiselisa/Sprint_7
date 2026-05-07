import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLogin;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;
import steps.CourierSteps;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CreateCourierTest extends BaseApiTest {
    private int courierId;

    @Test
    @DisplayName("Успешное создание нового курьера")
    public void testCourierCreateSuccess() {
        CourierModel courier = CourierData.getRandomCourier();
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
    }

    @Test
    @DisplayName("Получение ошибки при создании существующего логина")
    @Description("Текст ошибки в тесте изменен в соответствии с фактическим ответом сервера, так как документация не актуальна")
    public void testCreateSameCourierNameError() {
        CourierModel courier = CourierData.getRandomCourier();
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Получение ошибки при создании курьера без логина(null)")
    public void testCreateCourierWithNullLoginError() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setLogin(null);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Получение ошибки при создании курьера без логина(незаполненное поле)")
    public void testCreateCourierWithNoLoginError() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setLogin("");
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Получение ошибки при создании курьера без пароля(null)")
    public void testCreateCourierWithNullPasswordError() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setPassword(null);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Получение ошибки при создании курьера без пароля(незаполненное поле)")
    public void testCreateCourierWithNoPasswordError() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setPassword("");
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Успешное создание нового курьера без необязательного поля 'Имя'(null)")
    public void testCreateCourierWithNullFirstNameSuccess() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setFirstName(null);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
    }
    @Test
    @DisplayName("Успешное создание нового курьера без необязательного поля 'Имя'(незаполненное поле)")
    public void testCreateCourierWithNoFirstNameSuccess() {
        CourierModel courier = CourierData.getRandomCourier();
        courier.setFirstName("");
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            CourierSteps.deleteCourier(courierId);
        }
    }

}
