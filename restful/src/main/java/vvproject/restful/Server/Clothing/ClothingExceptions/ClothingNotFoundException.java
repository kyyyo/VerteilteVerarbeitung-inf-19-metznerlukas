package vvproject.restful.Server.Clothing.ClothingExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClothingNotFoundException extends Throwable {
    public ClothingNotFoundException(String s) {
    }
}
