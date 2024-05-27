package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
