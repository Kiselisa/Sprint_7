import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLogin;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;

public class LoginCourierTest extends BaseApiTest {

    private int courierId;
    private CourierModel courier;

    @Before
    public void prepareData() {
        courier = CourierData.getRandomCourier();
        createCourier(courier);
        courierId = CourierSteps.getCourierId(new CourierLogin(courier.getLogin(), courier.getPassword()));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            CourierSteps.deleteCourier(courierId);
        }
    }
    @Test
    @DisplayName("Успешное создание нового курьера и вход в систему с его логином и паролем")
    public void testCourierLoginSuccess() {
        CourierLogin credentials = new CourierLogin(courier.getLogin(), courier.getPassword());
        loginCourier(credentials)
                .then()
                .statusCode(HTTP_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Получение ошибки при входе курьера в систему без логина(null)")
    public void testCourierLoginWithNullLoginError() {
        CourierLogin courier = new CourierLogin(null, PASSWORD);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Получение ошибки при входе курьера в систему без логина(незаполненное поле)")
    public void testCourierLoginWithNoLoginError() {
        CourierLogin courier = new CourierLogin("", PASSWORD);
        loginCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Получение ошибки при входе курьера в систему без пароля(null)")
    @Description("При тесте часто происходит ошибка сервера: Expected status code <400> but was <504>.")
    public void testCourierLoginWithNullPasswordError() {
        CourierLogin credentials = new CourierLogin(courier.getLogin(), null);
        loginCourier(credentials)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Получение ошибки при входе курьера в систему без пароля(незаполненное поле)")
    public void testCourierLoginWithNoPasswordError() {
        CourierLogin credentials = new CourierLogin(courier.getLogin(), "");
        loginCourier(credentials)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Получение ошибки при входе курьера в систему с неправильным паролем")
    public void testCourierLoginWithWrongPasswordError() {
        CourierLogin credentials = new CourierLogin(courier.getLogin(), courier.getPassword() + 33);
        loginCourier(credentials)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Получение ошибки при входе курьера в систему с несуществующим логином")
    public void testCourierLoginWithWrongLoginError() {
        CourierLogin credentials = new CourierLogin("badLogin" + System.currentTimeMillis(), "8888");
        loginCourier(credentials)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

}