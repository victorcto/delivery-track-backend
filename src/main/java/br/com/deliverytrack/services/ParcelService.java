package br.com.deliverytrack.services;

import br.com.deliverytrack.domains.Parcel;
import br.com.deliverytrack.dtos.request.ParcelRequest;
import br.com.deliverytrack.dtos.response.ParcelResponse;
import br.com.deliverytrack.exceptions.DataValidationException;
import br.com.deliverytrack.repositories.ParcelRepository;
import br.com.deliverytrack.utils.MessageList;
import br.com.deliverytrack.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ParcelResponse save(ParcelRequest parcelRequest) {
        MessageList errors = validate(parcelRequest);
        if (ValidatorUtil.isNotEmpty(errors.getMessages())) {
            throw new DataValidationException(errors.getMessages());
        }

        Parcel parcel = modelMapper.map(parcelRequest, Parcel.class);
        return modelMapper.map(parcelRepository.save(parcel), ParcelResponse.class);
    }

    private MessageList validate(ParcelRequest parcel) {
        if (ValidatorUtil.isEmpty(parcel.getDescription())) {
            throw new DataValidationException("A descrição da encomenda não foi informada.");
        }

        MessageList errors = new MessageList();
        if (ValidatorUtil.isEmpty(parcel.getHeight())) {
            errors.addMessage("A altura da encomenda não foi informada.");
        }

        if (ValidatorUtil.isEmpty(parcel.getWidth())) {
            errors.addMessage("A largura da encomenda não foi informada.");
        }

        if (ValidatorUtil.isEmpty(parcel.getLength())) {
            errors.addMessage("O comprimento da encomenda não foi informado.");
        }

        if (ValidatorUtil.isEmpty(parcel.getWeight())) {
            errors.addMessage("O peso da encomenda não foi informado.");
        }

        return errors;
    }
}
