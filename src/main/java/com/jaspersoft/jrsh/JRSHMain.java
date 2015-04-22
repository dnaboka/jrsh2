package com.jaspersoft.jrsh;

import com.jaspersoft.jrsh.mode.ModeFactory;
import com.jaspersoft.jrsh.mode.RunMode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public class JRSHMain {

    public static void main(final String[] args) {

        loadCommandOnStartup();

        final RunMode.Mode mode = defineProcessMode(args);
        final RunMode runMode = ModeFactory.getMode(mode);
        runMode.run(args);
    }

    private static RunMode.Mode defineProcessMode(final String[] args) {

        // TODO check argument list and choose mode

        return RunMode.Mode.SHELL;
    }

    private static void loadCommandOnStartup() {

        String className = null;
        try {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("src/main/resources/commands.properties"));
                className = br.readLine();
                while(className != null) {
                    Class.forName(className);
                    className = br.readLine();
                }
            } finally {
                if (br != null) {
                    br.close();
                    br = null;
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(">>> Couldn't find property file [commands.properties]");
            System.exit(-1);
        } catch (IOException ioe) {
            System.out.println(">>> Couldn't read from property file [commands.properties]");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println(">>> Couldn't load class for name [" + className + "]");
            System.exit(-1);
        }
    }
}