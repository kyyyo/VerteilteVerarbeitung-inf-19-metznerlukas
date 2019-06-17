package vvproject.restful.ClientTest;

import org.junit.BeforeClass;
import org.junit.Test;

public class Client {

    private vvproject.restful.Client.Client client;

    @BeforeClass
    public void setupClient() {
        this.client = new vvproject.restful.Client.Client("http://localhost:8080");
        this.client.register(
                "test_user_1",
                "password",
                "test",
                "test",
                "test",
                "test",
                "test",
                0
        );
        this.client.register(
                "test_user_2",
                "password",
                "test",
                "test",
                "test",
                "test",
                "test",
                0
        );
    }

    @Test
    public void findAllClothings() {

    }
}
