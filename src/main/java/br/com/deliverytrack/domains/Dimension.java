package br.com.deliverytrack.domains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dimensions")
public class Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float width;

    private Float height;

    private Float length;

    private Float weight;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parcel parcel;
}
