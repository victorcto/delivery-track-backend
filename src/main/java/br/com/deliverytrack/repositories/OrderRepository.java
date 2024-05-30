package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByTrackingNumber(String trackingNumber);

    List<Order> findFirst4ByOrderByOrderDateDesc();
}
