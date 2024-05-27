package br.com.deliverytrack.dtos;

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
public class OrderDTO {
    private Long id;

    private String trackingNumber;

    private ParcelDTO parcel;

    private Status status;

    private Instant orderDate;

    private Instant deliveryDate;

    private String address;

    private CustomerDTO customer;

    private DriverDTO driver;

    private Float price;
}
