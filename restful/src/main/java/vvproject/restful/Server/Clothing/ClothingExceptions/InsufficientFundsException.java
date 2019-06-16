package vvproject.restful.Server.Clothing.ClothingExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InsufficientFundsException is thrown when a user wants
 * to purchase a item but has not enough money on his account.
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String insufficient_funds) {
        super(s);
    }
}
