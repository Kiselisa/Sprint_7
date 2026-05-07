import data.OrderData;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest extends BaseApiTest {
    private final List<String> color;

    public CreateOrderParamTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Варианты цвета самоката: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{List.of("BLACK")}, {List.of("GREY")}, {List.of("BLACK", "GREY")}, {List.of()}};
    }

    @Test
    @DisplayName("Создание заказа с разными цветами самоката")
    public void testCreateOrderWithDifferentColors() {
        OrderModel order = OrderData.getDefaultOrder(color);
        OrderSteps.createOrder(order).then().statusCode(201).body("track", notNullValue());
    }
}

