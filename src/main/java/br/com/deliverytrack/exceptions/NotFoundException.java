package br.com.deliverytrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends CoreException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(List<String> messages) {
        super(messages);
    }
}
