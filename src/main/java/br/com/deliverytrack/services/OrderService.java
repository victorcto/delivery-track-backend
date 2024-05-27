package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Dimension;
import br.com.deliverytrack.domains.Order;
import br.com.deliverytrack.dtos.OrderDTO;
import br.com.deliverytrack.enums.Status;
import br.com.deliverytrack.repositories.OrderRepository;
import br.com.deliverytrack.utils.MessageList;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    private final TrackingService trackingService;

    public OrderDTO save(OrderDTO orderDTO) {
        orderDTO.setTrackingNumber(trackingService.generateUniqueTrackingNumber());
        orderDTO.setOrderDate(Instant.now());
        orderDTO.setStatus(Status.PENDING);

        Order order = modelMapper.map(orderDTO, Order.class);

        validateOrder(order);

        order = orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    private void validateOrder(Order order) {
        if (ValidatorUtil.isEmpty(order.getPrice())) {
            throw new IllegalArgumentException("O preço não foi informaddo.");
        }

        if (ValidatorUtil.isEmpty(order.getAddress())) {
            throw new IllegalArgumentException("O endereço nâo foi informado.");
        }

        if (ValidatorUtil.isEmpty(order.getCustomer())) {
            throw new IllegalArgumentException("O cliente nâo foi informado.");
        }

        if (ValidatorUtil.isEmpty(order.getDriver())) {
            throw new IllegalArgumentException("O motorista nâo foi informado.");
        }

        if (ValidatorUtil.isEmpty(order.getParcel().getDescription())) {
            throw new IllegalArgumentException("A descrição da encomenda não foi informada.");
        }

        MessageList dimensionErrors = validateDimensions(order.getParcel().getDimension());
        if (ValidatorUtil.isNotEmpty(dimensionErrors)) {
            throw new IllegalArgumentException(dimensionErrors.toString());
        }
    }

    private MessageList validateDimensions(Dimension dimension) {
        MessageList errors = new MessageList();
        if (ValidatorUtil.isEmpty(dimension.getHeight())) {
            errors.addMessage("A altura da encomenda não foi informada.");
        }

        if (ValidatorUtil.isEmpty(dimension.getWidth())) {
            errors.addMessage("A largura da encomenda não foi informada.");
        }

        if (ValidatorUtil.isEmpty(dimension.getLength())) {
            errors.addMessage("O comprimento da encomenda não foi informado.");
        }

        if (ValidatorUtil.isEmpty(dimension.getWeight())) {
            errors.addMessage("O peso da encomenda não foi informado.");
        }

        return errors;
    }

}
