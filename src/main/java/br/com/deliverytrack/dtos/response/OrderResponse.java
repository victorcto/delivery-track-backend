package br.com.deliverytrack.dtos.response;

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
public class OrderResponse {
    private Long id;

    private String trackingNumber;

    private ParcelResponse parcel;

    private Status status;

    private Instant orderDate;

    private Instant deliveryDate;

    private String address;

    private CustomerResponse customer;

    private DriverResponse driver;

    private Float price;

    private Boolean delivered;
}
