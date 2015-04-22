package com.jaspersoft.jrsh.mode;

import java.util.List;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public class ScriptMode extends AbstractRunMode {

    public ScriptMode() {
        super(Mode.SCRIPT);
    }

    @Override
    public void run(final String... args) {

        final List<String[]> cmdList = null; // TODO parse file from args

        try {
            for (final String[] cmd : cmdList) {
                super.run(cmd);
            }
        } catch (final Exception e) {
            // TODO process error
        }
    }
}