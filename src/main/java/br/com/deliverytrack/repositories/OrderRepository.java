package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByTrackingNumber(String trackingNumber);
}
