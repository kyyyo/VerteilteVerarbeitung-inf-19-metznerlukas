package vvproject.restful.Server.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("TransactionService")
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction t) {
        this.transactionRepository.save(t);
    }

    public List<Transaction> findAllTransactions() {
        List<Transaction> result = new ArrayList<>();
        this.transactionRepository.findAll().forEach((e) -> result.add(e));
        return result;
    }
}
