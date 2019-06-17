package vvproject.restful.Server.Member.MemberExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * WrongLoginException is used when the login
 * credentials of a user are wrong.
 *
 * @author Lukas Metzner, sINFlumetz
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongLoginException extends Throwable {
    public WrongLoginException(String s) {
        super(s);
    }
}
