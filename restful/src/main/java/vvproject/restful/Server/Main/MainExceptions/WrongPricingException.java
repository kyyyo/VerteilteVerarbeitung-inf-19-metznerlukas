package vvproject.restful.Server.Main.MainExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * WrongPricingException is called when the exchangePrice
 * is not between 10 and 50 percent of the originalPrice
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPricingException extends Throwable {
    public WrongPricingException(String s) {
    }
}
