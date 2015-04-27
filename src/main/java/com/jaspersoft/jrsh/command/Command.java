package com.jaspersoft.jrsh.command;

import com.jaspersoft.jrsh.util.ResultCode;

import java.util.concurrent.Callable;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/21/15
 */
public interface Command<T> extends Callable<ResultCode> {
    
    T getParamDTO();

    void setParamDTO(final T paramDTO);

    String getName();
    
    String getUsageDescription();

    boolean hasArguments();
}