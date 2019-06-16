package vvproject.restful.Server.Transaction.TransactionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends Throwable {
    public TransactionNotFoundException(String s) {
        super(s);
    }
}
