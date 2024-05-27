package br.com.deliverytrack.repositories;

import br.com.deliverytrack.domains.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
