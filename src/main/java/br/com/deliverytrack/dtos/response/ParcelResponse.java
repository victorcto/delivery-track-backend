package br.com.deliverytrack.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelResponse {
    private Long id;

    private String description;

    private Float width;

    private Float height;

    private Float length;

    private Float weight;
}
