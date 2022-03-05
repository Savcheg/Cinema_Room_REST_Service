package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongNumberException extends RuntimeException{

    public WrongNumberException(String message) {
        super( message);
    }
}
