import io.restassured.RestAssured;
import org.junit.BeforeClass;
import static data.CourierData.BASE_URI;

public class BaseApiTest {

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = BASE_URI;
    }
}
