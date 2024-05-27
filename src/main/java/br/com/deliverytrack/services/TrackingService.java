package br.com.deliverytrack.services;

import br.com.deliverytrack.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {
    private final OrderRepository orderRepository;

    public String generateUniqueTrackingNumber() {
        String trackingNumber = "";
        do {
            trackingNumber = generateTrackingNumber();
        } while (isUniqueTrackingNumber(trackingNumber));

        return trackingNumber;
    }

    private String generateTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return "RR" + uuid.toString().substring(0, 8) + "-" + uuid.toString().substring(9, 13) + "-"
                + uuid.toString().substring(14, 16);
    }

    private boolean isUniqueTrackingNumber(String trackingNumber) {
        return orderRepository.existsByTrackingNumber(trackingNumber);
    }
}
