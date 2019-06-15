package vvproject.restful.Server.Main.MainExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * MaxSellingSizeException is called if a user has more than
 * 10 offers on the platform.
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxSellingSizeException extends Throwable {
    public MaxSellingSizeException(String s) {
    }
}
