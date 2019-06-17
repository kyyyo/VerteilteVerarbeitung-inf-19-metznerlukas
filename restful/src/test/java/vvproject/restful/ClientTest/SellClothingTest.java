package vvproject.restful.ClientTest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import vvproject.restful.Server.Enums.Gender;
import vvproject.restful.Server.Enums.Size;
import vvproject.restful.Server.Enums.Type;

import static org.junit.Assert.assertEquals;

public class SellClothingTest {

    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");


    @Test
    public void sellClothing() {
        HttpStatus status = client.sellClothing(Gender.MALE, Type.JACKET, Size.M, 23.0f, 10.0f, "test_user_2", "password");
        assertEquals(HttpStatus.OK, status);
    }
}
