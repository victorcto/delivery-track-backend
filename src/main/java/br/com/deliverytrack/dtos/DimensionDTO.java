package br.com.deliverytrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDTO {
    private Float width;

    private Float height;

    private Float length;

    private Float weight;
}
