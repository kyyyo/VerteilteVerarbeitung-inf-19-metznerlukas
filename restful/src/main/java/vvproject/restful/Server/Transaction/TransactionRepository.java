package vvproject.restful.Server.Transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("TransactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
