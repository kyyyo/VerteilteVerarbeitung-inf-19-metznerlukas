package vvproject.restful.Server.Member.MemberExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongLoginException extends Throwable {
    public WrongLoginException(String wrong_password) {
    }
}
