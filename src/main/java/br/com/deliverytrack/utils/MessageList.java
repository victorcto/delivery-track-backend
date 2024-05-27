package br.com.deliverytrack.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageList {
    private List<String> messages;

    public MessageList() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
