package com.jaspersoft.jrsh.mode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/15/15
 */
public class ModeFactory {

    private static Map<String, RunMode> runModeMap;

    static {
        runModeMap = new HashMap<String, RunMode>() {{
            put(RunMode.Mode.TOOL.name().toLowerCase(), new ToolMode());
            put(RunMode.Mode.SHELL.name().toLowerCase(), new ShellMode());
            put(RunMode.Mode.SCRIPT.name().toLowerCase(), new ScriptMode());
        }};
    }

    private ModeFactory() {}

    public static RunMode getMode(final RunMode.Mode mode) {

        if (mode == null) {
            final String errMsg =  "Null mode, please choose correct mode " + Arrays.toString(RunMode.Mode.values()) +
                    " and try again";
            throw new IllegalArgumentException(errMsg);
        }

        final RunMode runMode = runModeMap.get(mode.name().toLowerCase());
        if (runMode == null) {
            final String errMsg =  "Wrong mode name [" + mode.name() + "], please choose correct mode " +
                    Arrays.toString(RunMode.Mode.values()) + " and try again";
            throw new IllegalArgumentException(errMsg);
        }

        return runMode;
    }
}