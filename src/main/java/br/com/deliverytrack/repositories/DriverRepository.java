package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
