package br.com.deliverytrack.dtos.request;

import br.com.deliverytrack.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private ParcelRequest parcel;

    private Status status;

    private Instant deliveryDate;

    private String address;

    private Long customerId;

    private Long driverId;

    private Float price;
}
