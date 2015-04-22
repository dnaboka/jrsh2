package com.jaspersoft.jrsh.mode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                // TODO check session is alive or relogin
                super.run(cmdArray);

                // TODO think about exit
            }
        } catch (final IOException ioe) {
            // TODO process error
        } catch (final Exception e) {
            // TODO process error
        }
    }
}