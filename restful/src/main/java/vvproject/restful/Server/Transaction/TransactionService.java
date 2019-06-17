package vvproject.restful.Server.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service that controlls the transaction repository
 * and resolves the Optionals received from the repository
 *
 * @author Lukas Metzner, sINFlumetz
 */
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
        this.transactionRepository.findAll().forEach((transaction) -> result.add(transaction));
        return result;
    }

    public Transaction findById(Long id) throws TransactionNotFoundException {
        Optional<Transaction> optionalTransaction = this.transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            return optionalTransaction.get();
        } else {
            throw new TransactionNotFoundException("Transaction with id: " + id + " not found!");
        }
    }
}
