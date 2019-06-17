package vvproject.restful.ClientTest;

import org.junit.Test;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;

public class FindClothingTest {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void findAllClothing() throws ClothingNotFoundException, InterruptedException {
        Clothing clothing = client.findClothingById(1L);
        System.out.println(clothing.getId());
        System.out.println(clothing.getType());
        System.out.println(clothing.getGender());
        System.out.println(clothing.getSize());
    }
}
