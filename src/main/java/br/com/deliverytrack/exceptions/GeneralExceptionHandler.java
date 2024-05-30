package br.com.deliverytrack.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        Class<?> clazz = ex.getClass();
        ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);

        HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;

        List<String> errors = new ArrayList<>();
        if (ex instanceof CoreException) {
            errors.addAll(((CoreException) ex).getMessages());
        } else {
            errors.add(ex.getMessage());
        }
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(status.value(), errors, path);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }
}
