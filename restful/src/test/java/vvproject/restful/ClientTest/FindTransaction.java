package vvproject.restful.ClientTest;

import org.junit.Test;
import vvproject.restful.Server.Transaction.Transaction;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;

public class FindTransaction {
    private static vvproject.restful.Client.Client client =
            new vvproject.restful.Client.Client("http://localhost:8080/api/v1");

    @Test
    public void findTransaction() throws TransactionNotFoundException {
        Transaction transaction = client.findTransaction(2L);
        System.out.println(transaction.getBoughtClothing());
    }
}
