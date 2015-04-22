package com.jaspersoft.jrsh.command;

import com.jaspersoft.jrsh.command.dto.LoginDTO;
import com.jaspersoft.jrsh.exception.ParsingException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public class CommandParser {

    private CommandParser() {}

    public static Command parse(final String... args) throws ParsingException {    // TODO think about catching exception inside and throw PE

        if (args == null || args.length == 0) {
            throw new ParsingException();
        } else {
            boolean isNeedException = true;
            for (String arg : args) {
                if (arg != null && !arg.trim().isEmpty()) {
                    isNeedException = false;
                    break;
                }
            }
            if (isNeedException) {
                throw new ParsingException();
            }
        }

        if (checkLoginToken(args[0])) {
            if (args.length == 1) {
                final Command command = CommandFactory.getCommand(LoginCommand.NAME);
                fillCommandParams(command, args[0]);
                return command;
            } else {
                // TODO throw error (impossible case)
            }
        } else {
            final String cmdName = args[0];
            final Command command = CommandFactory.getCommand(cmdName);
            fillCommandParams(command, Arrays.copyOfRange(args, 1, args.length));
            return command;
        }

        throw new ParsingException(); // TODO think about message
    }

    private static boolean checkLoginToken(final String tokenToCheck) {

        final Pattern p = Pattern.compile("\\w*%\\w*@.*$");
        final Matcher m = p.matcher(tokenToCheck);
        return m.matches();
    }

    private static void fillCommandParams(final Command command, final String... args)
            throws ParsingException {

        if (args == null || args.length == 0) {
            throw new ParsingException("Empty argument line. Please use following format: " + command.getUsageDescription());
        }

        try {
            if (args.length == 1) { // only for login token
                final Scanner scanner = new Scanner(args[0]).useDelimiter(Pattern.compile("[%@]"));
                final LoginDTO loginDTO = new LoginDTO();
                loginDTO.setLogin(scanner.next());
                loginDTO.setPassword(scanner.next());
                loginDTO.setUrl(scanner.next());
                command.setParamDTO(loginDTO);
            } else {
                final String dtoClassName = defineGenericTypeClassName(command);
                final Class dtoClass = Class.forName(dtoClassName);
                final Object dto = dtoClass.newInstance();
                final Field[] fields = dtoClass.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        final String fieldName = field.getName();
                        boolean isNeedSetValue = false;
                        for (String arg : args) {               // TODO think on case of multiple values
                            if (!isNeedSetValue && fieldName.equalsIgnoreCase(arg)) {
                                isNeedSetValue = true;
                            } else if (isNeedSetValue) {
                                field.setAccessible(true);
                                field.set(dto, arg);
                                break;
                            }
                        }
                    }
                }
                command.setParamDTO(dto);
            }
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static String defineGenericTypeClassName(final Command command) {

        final Class commandClass = command.getClass();
        final ParameterizedType genericType = (ParameterizedType) command.getClass().getGenericInterfaces()[0];
        final Class parameter = (Class) genericType.getActualTypeArguments()[0];
        return parameter.getName();
    }
}