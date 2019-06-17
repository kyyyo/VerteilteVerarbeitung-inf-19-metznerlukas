package vvproject.restful.ClientTest;

import org.junit.Test;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Enums.Type;

import static junit.framework.TestCase.assertEquals;

public class UpdateClothingTest {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void updateClothingTest() throws ClothingNotFoundException {
        Clothing c = client.findClothingById(1L);
        c.setType(Type.PANTS);
        client.updateClothing(c, "test_user_2", "password");
        c = client.findClothingById(1L);
        assertEquals(Type.PANTS, c.getType());
    }
}
