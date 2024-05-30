package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Customer;
import br.com.deliverytrack.dtos.request.CustomerRequest;
import br.com.deliverytrack.dtos.response.CustomerResponse;
import br.com.deliverytrack.exceptions.DataValidationException;
import br.com.deliverytrack.exceptions.NotFoundException;
import br.com.deliverytrack.repositories.CustomerRepository;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public CustomerResponse save(CustomerRequest customerRequest) {
        validate(customerRequest);
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        return modelMapper.map(customerRepository.save(customer), CustomerResponse.class);
    }

    private static void validate(CustomerRequest customerRequest) {
        if (ValidatorUtil.isEmpty(customerRequest.getName())) {
            throw new DataValidationException("O nome do cliente não foi informado.");
        }

        if (ValidatorUtil.isEmpty(customerRequest.getEmail())) {
            throw new DataValidationException("O e-mail do cliente não foi informado.");
        }

        if (ValidatorUtil.isNotValidEmail(customerRequest.getEmail())) {
            throw new DataValidationException("O e-mail do cliente não é válido.");
        }

        if (ValidatorUtil.isEmpty(customerRequest.getPhone())) {
            throw new DataValidationException("O telefone do cliente não foi informado.");
        }

        if (ValidatorUtil.isNotValidPhone(customerRequest.getPhone())) {
            throw new DataValidationException("O telefone do cliente não é válido.");
        }
    }

    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().sorted(Comparator.comparing(Customer::getName))
                .map((c) -> modelMapper.map(c, CustomerResponse.class)).toList();
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O cliente não foi encontrado."));

        return modelMapper.map(customer, CustomerResponse.class);
    }
}
