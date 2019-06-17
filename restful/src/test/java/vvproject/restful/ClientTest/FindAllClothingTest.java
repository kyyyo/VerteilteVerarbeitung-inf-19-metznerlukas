package vvproject.restful.ClientTest;

import org.junit.Test;
import vvproject.restful.Server.Clothing.Clothing;

import java.util.List;

public class FindAllClothingTest {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void findAllClothing() {
        List<Clothing> clothing = client.findAllClothing();
        System.out.println(clothing.get(0).getId());
    }

}
