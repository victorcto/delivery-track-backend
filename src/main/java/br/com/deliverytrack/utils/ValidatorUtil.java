package br.com.deliverytrack.utils;

import java.util.Collection;
import java.util.Map;

public class ValidatorUtil {

    public static boolean isEmpty(Object object) {
        return switch (object) {
            case null -> true;
            case String s -> s.isBlank();
            case Collection<?> collection -> collection.isEmpty();
            case Map<?, ?> map -> map.isEmpty();
            case Object[] objects -> objects.length == 0;
            case PersistDB persistDB -> persistDB.getId() == 0;
            case Number number -> number.doubleValue() == 0;
            default -> false;
        };
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
