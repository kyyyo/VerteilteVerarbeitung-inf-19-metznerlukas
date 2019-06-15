package vvproject.restful.Server.Member.MemberExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends Throwable {
    public MemberNotFoundException(String s) {
    }
}
