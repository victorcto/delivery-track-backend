package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Customer;
import br.com.deliverytrack.domains.Driver;
import br.com.deliverytrack.domains.Order;
import br.com.deliverytrack.domains.Parcel;
import br.com.deliverytrack.dtos.DeadlineControl;
import br.com.deliverytrack.dtos.request.OrderRequest;
import br.com.deliverytrack.dtos.response.OrderResponse;
import br.com.deliverytrack.enums.Status;
import br.com.deliverytrack.exceptions.BusinessRuleException;
import br.com.deliverytrack.exceptions.DataValidationException;
import br.com.deliverytrack.exceptions.InfraException;
import br.com.deliverytrack.exceptions.NotFoundException;
import br.com.deliverytrack.repositories.CustomerRepository;
import br.com.deliverytrack.repositories.DriverRepository;
import br.com.deliverytrack.repositories.OrderRepository;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new DataValidationException("Customer not found"));
        Driver driver = driverRepository.findById(orderRequest.getDriverId()).orElseThrow(() -> new DataValidationException("Driver not found"));
        Parcel parcel = modelMapper.map(parcelService.save(orderRequest.getParcel()), Parcel.class);

        orderRequest.setStatus(Status.PENDING);
        orderRequest.setAddress(orderRequest.getAddress());
        orderRequest.setPrice(orderRequest.getPrice());
        orderRequest.setDelivered(false);

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
            throw new InfraException("Não foi possível gerar o número do pedido. " + "Por favor, entre em contato com o suporte");
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

    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().sorted(Comparator.comparing(Order::getOrderDate)).map((o) -> modelMapper.map(o, OrderResponse.class)).toList();
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("O pedido de encomenda não foi encontrado."));

        return modelMapper.map(order, OrderResponse.class);
    }

    public DeadlineControl getDeadlineControl() {
        List<OrderResponse> orderResponses = getAll();
        long totalOrders = orderResponses.size();

        List<OrderResponse> ordersDelivered = orderResponses.stream().filter(OrderResponse::getDelivered).toList();

        List<OrderResponse> ordersLate = orderResponses.stream().filter(order -> !order.getDelivered()).toList();

        float deliveredPercentage = (float) (ordersDelivered.size() * 100.0 / totalOrders);
        float latePercentage = (float) (ordersLate.size() * 100.0 / totalOrders);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        deliveredPercentage = Float.parseFloat(df.format(deliveredPercentage));
        latePercentage = Float.parseFloat(df.format(latePercentage));

        return new DeadlineControl(deliveredPercentage, latePercentage);
    }

    public List<OrderResponse> getLatestRegisteredOrders() {
        List<Order> orders = orderRepository.findFirst4ByOrderByOrderDateDesc();
        return orders.stream().map((o) -> modelMapper.map(o, OrderResponse.class)).toList();
    }

}
