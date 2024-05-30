package br.com.deliverytrack.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelRequest {
    private String description;

    private Float width;

    private Float height;

    private Float length;

    private Float weight;
}
