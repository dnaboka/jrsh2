package com.jaspersoft.jrsh.service;

import com.jaspersoft.jrsh.command.Command;
import com.jaspersoft.jrsh.exception.ValidationException;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public interface ValidationService {

    void validate(final Command command) throws ValidationException;
}