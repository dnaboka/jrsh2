package com.jaspersoft.jrsh.command;

import com.jaspersoft.jrsh.util.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public class CommandFactory {

    private static Map<String, Class> commandMap;

    static {
        commandMap = new HashMap<String, Class>();
    }

    private CommandFactory() {}

    public static void registerCommand(final String cmdName, final Class cmdClass) {

        if (cmdName == null || cmdName.isEmpty()) {
            throw new IllegalArgumentException("Command name is null or empty");
        } else if (cmdClass == null) {
            throw new IllegalArgumentException("Command class is null");
        }

        commandMap.put(cmdName.toLowerCase(), cmdClass);
    }

    public static Command<ResultCode> getCommand(final String commandName) {

        if (commandName == null || commandName.isEmpty()) {
            final String errMsg = "Command name is null or empty, please choose correct command name and try again";
            throw new IllegalArgumentException(errMsg);
        }

        try {

            // TODO use one instance for each command

            for (final String cmdName : commandMap.keySet()) {

                if (cmdName.equals(commandName.toLowerCase())) {
                    final Class cmdClass = commandMap.get(cmdName);
                    return (Command<ResultCode>) cmdClass.newInstance();
                }
            }
        } catch (final InstantiationException ie) {
            // TODO throw runtime exception
        } catch (final IllegalAccessException iae) {
            // TODO throw runtime exception
        }

        final String errMsg =  "Wrong command [" + commandName.toLowerCase() + "], please choose correct command and try again";
        throw new IllegalArgumentException(errMsg);
    }
}