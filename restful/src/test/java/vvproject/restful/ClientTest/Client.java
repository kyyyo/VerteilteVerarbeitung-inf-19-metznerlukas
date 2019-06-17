package vvproject.restful.ClientTest;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

public class Client {

    private vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void setupClient() {
        HttpStatus res1 = client.register(
                "test_user_1",
                "password",
                "test",
                "test",
                "test",
                "test",
                "test",
                0,
                50.0f
        );
        HttpStatus res2 = client.register(
                "test_user_2",
                "password",
                "test",
                "test",
                "test",
                "test",
                "test",
                0,
                50.0f
        );
        assertEquals(HttpStatus.OK, res1);
        assertEquals(HttpStatus.OK, res2);
    }
}
