package br.com.deliverytrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InfraException extends CoreException {
    public InfraException(String message) {
        super(message);
    }

    public InfraException(List<String> messages) {
        super(messages);
    }
}
