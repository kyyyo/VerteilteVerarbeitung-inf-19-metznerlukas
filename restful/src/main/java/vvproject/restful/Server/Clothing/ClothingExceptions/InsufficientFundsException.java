package vvproject.restful.Server.Clothing.ClothingExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String insufficient_funds) {
    }
}
