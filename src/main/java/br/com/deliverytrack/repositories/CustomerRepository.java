package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFavorite(Boolean favorite);
}
