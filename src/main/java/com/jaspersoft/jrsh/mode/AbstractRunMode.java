package com.jaspersoft.jrsh.mode;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jrsh.command.Command;
import com.jaspersoft.jrsh.command.CommandParser;
import com.jaspersoft.jrsh.command.LoginCommand;
import com.jaspersoft.jrsh.exception.ParsingException;
import com.jaspersoft.jrsh.exception.ValidationException;
import com.jaspersoft.jrsh.service.*;
import com.jaspersoft.jrsh.util.ResultCode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public abstract class AbstractRunMode implements RunMode {
    
    private Mode mode;

    private ValidationService validationService;

    private ExecutorService executorService;

    protected AbstractRunMode(final Mode mode) {
        this.mode = mode;
        validationService = new ValidationServiceImpl();
        executorService = Executors.newSingleThreadExecutor();
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public void run(final String... args) {

        String commandName = null;
        try {
            final Command<ResultCode> command = CommandParser.parse(args);
            commandName = command.getName();
            validationService.validate(command);
            final Future<ResultCode> future = executorService.submit(command);
//            System.out.println(">>> " + future.get());
            future.get(); // TODO process response
        } catch (final ParsingException pe) {
            if (pe.getMessage() != null) {
                System.out.println(">>> " + pe.getMessage());
            }
        } catch (final ValidationException ve) {
            if (ve.getMessage() != null){
                System.out.println(">>> " + ve.getMessage());
            }
        } catch (InterruptedException ie) {
            if (ie.getMessage() != null){
                System.out.println(">>> " + ie.getMessage());
            }
        } catch (ExecutionException ee) {
            if (ee.getCause() instanceof AuthenticationFailedException
                    && !LoginCommand.NAME.equals(commandName)) {
                throw (AuthenticationFailedException) ee.getCause();
            }
            if (ee.getMessage() != null){
                System.out.println(">>> " + ee.getMessage());
            }
        } catch (RuntimeException re) {
            if (re.getMessage() != null){
                System.out.println(">>> " + re.getMessage());
            }
        }
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public void setValidationService(final ValidationService validationService) {
        this.validationService = validationService;
    }
}