package br.com.deliverytrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {
    private Long id;

    private String description;

    private DimensionDTO dimension;
}
