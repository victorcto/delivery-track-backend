package br.com.deliverytrack.utils;

import java.io.Serializable;

public interface PersistDB extends Serializable {
    Long getId();

    void setId(Long id);
}
