package br.com.deliverytrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessRuleException extends CoreException {

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(List<String> messages) {
        super(messages);
    }
}
