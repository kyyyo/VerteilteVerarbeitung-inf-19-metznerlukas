package vvproject.restful.Client.Management;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vvproject.restful.Server.Transaction.Transaction;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;

import java.util.Arrays;
import java.util.List;

public class TransactionManagement {

    private final String serviceURL;

    public TransactionManagement(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public List<Transaction> findAllTransactions() {
        String url = this.serviceURL + "/transactions";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction[]> transactions = restTemplate.getForEntity(url, Transaction[].class);
        if (transactions.getStatusCode().equals(HttpStatus.OK))
            return Arrays.asList(transactions.getBody());
        else
            return null;
    }

    public Transaction findTransaction(Long id) throws TransactionNotFoundException {
        String url = this.serviceURL + "/transactions/" + id.toString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction> transaction = restTemplate.getForEntity(url, Transaction.class);
        if (transaction.getStatusCode().equals(HttpStatus.OK))
            return transaction.getBody();
        else
            throw new TransactionNotFoundException(id + " Not found!");
    }
}
