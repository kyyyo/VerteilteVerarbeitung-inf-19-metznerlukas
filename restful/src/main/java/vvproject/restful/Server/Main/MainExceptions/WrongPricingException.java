package vvproject.restful.Server.Main.MainExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPricingException extends Throwable {
    public WrongPricingException(String s) {
    }
}
