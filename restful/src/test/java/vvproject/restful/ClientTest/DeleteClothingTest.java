package vvproject.restful.ClientTest;

import org.junit.Test;

public class DeleteClothingTest {

    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void deleteClothingTest() {
        client.deleteClothing(1L, "test_user_2", "password");
    }
}
