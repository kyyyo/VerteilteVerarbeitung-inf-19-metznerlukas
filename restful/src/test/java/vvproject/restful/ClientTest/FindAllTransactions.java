package vvproject.restful.ClientTest;

import org.junit.Test;
import vvproject.restful.Server.Transaction.Transaction;

import java.util.List;

public class FindAllTransactions {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void findAllTransactions() {
        List<Transaction> transactions = client.findAllTransactions();
        System.out.println(transactions.toString());
    }
}
