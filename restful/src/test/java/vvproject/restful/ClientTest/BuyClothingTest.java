package vvproject.restful.ClientTest;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import vvproject.restful.Client.ClientExceptions.TransactionFailedException;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Enums.ClothingStatus;

import static junit.framework.TestCase.assertEquals;

public class BuyClothingTest {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void buyClothingTest() throws TransactionFailedException, ClothingNotFoundException {
        HttpStatus status = client.buyClothing(1L, "test_user_1", "password");
        assertEquals(HttpStatus.OK, status);
        Clothing clothing = client.findClothingById(1L);
        System.out.println(clothing.getClothingStatus());
        assertEquals(ClothingStatus.SOLD, clothing.getClothingStatus());
    }
}
