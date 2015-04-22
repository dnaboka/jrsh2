package com.jaspersoft.jrsh.mode;

import java.util.Arrays;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public class ToolMode extends AbstractRunMode {

    public ToolMode() {
        super(Mode.TOOL);
    }

    @Override
    public void run(final String... args) {


        try {
            super.run(args[0]);
            super.run(Arrays.copyOfRange(args, 1, args.length));
        } catch (final Exception e) {
            // TODO process error
        }
    }
}