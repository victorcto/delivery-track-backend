package br.com.deliverytrack.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseDTO {
    private Long timestamp;
    private Integer status;
    private List<String> messages;
    private String path;

    public ErrorResponseDTO(Integer status, List<String> messages, String path) {
        this.timestamp = System.currentTimeMillis();
        this.status = status;
        this.messages = messages;
        this.path = path;
    }
}
