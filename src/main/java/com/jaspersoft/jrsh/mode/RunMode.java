package com.jaspersoft.jrsh.mode;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public interface RunMode {

    enum Mode {
        TOOL, SHELL, SCRIPT
    }

    void run(final String... args);
}