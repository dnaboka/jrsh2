package com.jaspersoft.jrsh.mode;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jrsh.command.LoginCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public class ShellMode extends AbstractRunMode {

    protected ShellMode() {
        super(Mode.SHELL);
    }

    @Override
    public void run(final String... args) {

        try {
            final BufferedReader console = new BufferedReader(new InputStreamReader(System.in)); // TODO use custom console
            // TODO add autocompletion to console

            super.run(args);

            while(true) {

                System.out.print(">>> ");

                final String cmdLine = console.readLine();
                final String[] cmdArray = cmdLine.split(" "); // TODO change
                try {
                    super.run(cmdArray);
                } catch (AuthenticationFailedException afe) {
                    if (args == null || args.length == 0) {
                        continue;
                    }
                    System.out.println(">>> Trying authorize user using " + Arrays.toString(args));
                    super.run(args);
                    System.out.println(">>> Execute last command: " + cmdLine);
                    try {
                        super.run(cmdArray);
                    } catch (AuthenticationFailedException afe1) {
                        System.out.println(">>> Couldn't authorize user");
                        System.out.println(">>> Please, call login command: " + LoginCommand.USAGE_DESCRIPTION);
                    }
                }

                // TODO think about exit
            }
        } catch (final IOException ioe) {
            // TODO process error
        } catch (final Exception e) {
            // TODO process error
        }
    }
}