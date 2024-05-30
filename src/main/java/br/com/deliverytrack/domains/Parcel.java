package br.com.deliverytrack.domains;

import br.com.deliverytrack.utils.PersistDB;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "parcels")
public class Parcel implements PersistDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    private Float width;

    private Float height;

    private Float length;

    private Float weight;
}
