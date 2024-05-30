package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Customer;
import br.com.deliverytrack.domains.Driver;
import br.com.deliverytrack.domains.Order;
import br.com.deliverytrack.domains.Parcel;
import br.com.deliverytrack.dtos.request.OrderRequest;
import br.com.deliverytrack.dtos.response.OrderResponse;
import br.com.deliverytrack.enums.Status;
import br.com.deliverytrack.exceptions.BusinessRuleException;
import br.com.deliverytrack.exceptions.DataValidationException;
import br.com.deliverytrack.exceptions.InfraException;
import br.com.deliverytrack.repositories.CustomerRepository;
import br.com.deliverytrack.repositories.DriverRepository;
import br.com.deliverytrack.repositories.OrderRepository;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;

    private final ModelMapper modelMapper;

    private final TrackingService trackingService;
    private final ParcelService parcelService;

    @Transactional
    public OrderResponse save(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(
                () -> new DataValidationException("Customer not found"));
        Driver driver = driverRepository.findById(orderRequest.getDriverId()).orElseThrow(
                () -> new DataValidationException("Driver not found"));
        Parcel parcel = modelMapper.map(parcelService.save(orderRequest.getParcel()), Parcel.class);

        orderRequest.setStatus(Status.PENDING);
        orderRequest.setAddress(orderRequest.getAddress());
        orderRequest.setPrice(orderRequest.getPrice());

        Order order = modelMapper.map(orderRequest, Order.class);
        order.setTrackingNumber(trackingService.generateUniqueTrackingNumber());
        order.setParcel(parcel);
        order.setOrderDate(Instant.now());
        order.setCustomer(customer);
        order.setDriver(driver);

        validate(order);
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    private void validate(Order order) {
        if (ValidatorUtil.isEmpty(order.getTrackingNumber())) {
            throw new InfraException("Não foi possível gerar o número do pedido. " +
                    "Por favor, entre em contato com o suporte");
        }

        if (ValidatorUtil.isEmpty(order.getParcel())) {
            throw new BusinessRuleException("Os dados da encomenda não foram informados.");
        }

        if (ValidatorUtil.isEmpty(order.getAddress())) {
            throw new DataValidationException("O endereço nâo foi informado.");
        }

        if (ValidatorUtil.isEmpty(order.getCustomer())) {
            throw new DataValidationException("O cliente nâo foi informado ou não existe.");
        }

        if (ValidatorUtil.isEmpty(order.getDriver())) {
            throw new DataValidationException("O entregador nâo foi informado ou não existe.");
        }

        if (order.getPrice() == null) {
            throw new DataValidationException("O preço não foi informaddo.");
        }

        if (order.getPrice() <= 0) {
            throw new BusinessRuleException("O preço não pode ser igual ou menor do que zero.");
        }

    }

}
