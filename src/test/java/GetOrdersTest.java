import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.OrderSteps;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {
    @Test
    @DisplayName("Список заказов не пустой")
    public void testOrderListIsNotEmpty() {
        OrderSteps.getOrderList()
                .then()
                .statusCode(HTTP_OK)
                .assertThat()
                .body("orders", notNullValue());
    }

}
