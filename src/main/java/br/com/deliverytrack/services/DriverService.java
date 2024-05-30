package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Driver;
import br.com.deliverytrack.dtos.request.DriverRequest;
import br.com.deliverytrack.dtos.response.DriverResponse;
import br.com.deliverytrack.exceptions.DataValidationException;
import br.com.deliverytrack.exceptions.NotFoundException;
import br.com.deliverytrack.repositories.DriverRepository;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DriverResponse save(DriverRequest driverRequest) {
        validate(driverRequest);
        Driver driver = modelMapper.map(driverRequest, Driver.class);
        return modelMapper.map(driverRepository.save(driver), DriverResponse.class);
    }

    private static void validate(DriverRequest driverRequest) {
        if (ValidatorUtil.isEmpty(driverRequest.getName())) {
            throw new DataValidationException("O nome do entregador não foi informado.");
        }

        if (ValidatorUtil.isEmpty(driverRequest.getEmail())) {
            throw new DataValidationException("O e-mail do entregador não foi informado.");
        }

        if (ValidatorUtil.isNotValidEmail(driverRequest.getEmail())) {
            throw new DataValidationException("O e-mail do entregador não é válido.");
        }

        if (ValidatorUtil.isEmpty(driverRequest.getPhone())) {
            throw new DataValidationException("O telefone do entregador não foi informado.");
        }
        if (ValidatorUtil.isNotValidPhone(driverRequest.getPhone())) {
            throw new DataValidationException("O telefone do entregador não é válido.");
        }
    }

    public List<DriverResponse> getAll() {
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().sorted(Comparator.comparing(Driver::getName))
                .map((d) -> modelMapper.map(d, DriverResponse.class)).toList();
    }

    public DriverResponse getById(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O motorista não foi encontrado."));

        return modelMapper.map(driver, DriverResponse.class);
    }

}
