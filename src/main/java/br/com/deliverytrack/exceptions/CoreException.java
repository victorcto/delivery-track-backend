package br.com.deliverytrack.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoreException extends RuntimeException {
    private List<String> messages;

    public CoreException(String message) {
        super(message);
        this.messages = List.of(message);
    }

    public CoreException(List<String> messages) {
        super("");
        this.messages = messages;
    }
}
