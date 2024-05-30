package br.com.deliverytrack.domains;

import br.com.deliverytrack.enums.Status;
import br.com.deliverytrack.utils.PersistDB;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements PersistDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @ManyToOne
    private Parcel parcel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant deliveryDate;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Driver driver;

    @Column(nullable = false)
    private Float price;

}
