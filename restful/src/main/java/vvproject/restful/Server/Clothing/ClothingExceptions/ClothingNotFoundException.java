package vvproject.restful.Server.Clothing.ClothingExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ClothingNotFoundException is thrown when a
 * clothing is not found in the database.
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClothingNotFoundException extends Throwable {
    public ClothingNotFoundException(String s) {
    }
}
