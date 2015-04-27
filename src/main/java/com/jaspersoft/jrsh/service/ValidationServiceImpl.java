package com.jaspersoft.jrsh.service;

import com.jaspersoft.jrsh.command.Command;
import com.jaspersoft.jrsh.exception.ValidationException;
import com.jaspersoft.jrsh.util.Mandatory;

import java.lang.reflect.Field;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validate(final Command command) throws ValidationException {

        try {
            final Object object = command.getParamDTO();
            if (object == null) {
                return;
            }
            final Field[] fieldsToValidate = object.getClass().getDeclaredFields();
            for (Field field : fieldsToValidate) {
                if (field.isAnnotationPresent(Mandatory.class)) {
                    field.setAccessible(true);
                    final Object fieldValue = field.get(object);
                    if (fieldValue == null || fieldValue.toString().isEmpty()) {
                        throw new ValidationException("Value for mandatory parameter [" + field.getName() + "] is null or empty");
                    }
                }
            }
        } catch (IllegalAccessException iae) {
            throw new ValidationException(iae);
        }
    }
}