package vvproject.restful.Server.Member.MemberExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * MemberNotFoundException is thrown when a user
 * is not found in the database.
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends Throwable {
    public MemberNotFoundException(String s) {
        super(s);
    }
}
