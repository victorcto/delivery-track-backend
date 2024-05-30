package br.com.deliverytrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataValidationException extends CoreException {

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(List<String> messages) {
        super(messages);
    }
}
