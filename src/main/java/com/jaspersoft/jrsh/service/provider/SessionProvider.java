package com.jaspersoft.jrsh.service.provider;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jrsh.exception.ConnectionException;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/20/15
 */
public class SessionProvider {

    private static final int TIMEOUT = 5000;

    private static volatile SessionProvider instance;

    private Session session;
    
    private static String currentlyLoggedUser;
    
    private SessionProvider() {
    }

    public static SessionProvider getInstance() {
        
        if (instance == null) {
            synchronized (SessionProvider.class) {
                if (instance == null) {
                    instance = new SessionProvider();
                }
            }
        }
        return instance;
    }

    public Session getSession() {
        if (session == null) {
            System.out.println(">>> User not authorized");
            throw new AuthenticationFailedException();
        }
        return session;
    }

    public Session getSession(final String url, final String user, final String pwd, final String org)
            throws ConnectionException {

        if (session == null) {
            session = createSession(url, user, pwd, org);
        }

        return session;
    }

    public void invalidateSession() throws ConnectionException {

        try {
            if (session != null) {
                session.logout();
                session = null;
                System.out.println(">>> User [" + currentlyLoggedUser + "] successfully logged out");
                currentlyLoggedUser = null;
            }
        } catch (final Exception ex) {
            throw new ConnectionException(ex);
        }
    }

    private static Session createSession(final String url, final String user, final String pwd, final String org)
            throws ConnectionException {

        String errMsg = null;
        if (url == null || url.isEmpty()) {
            errMsg = "URL is null or empty";
        } else if (user == null || user.isEmpty()) {
            errMsg = "User name is null or empty";
        } else if (pwd == null || pwd.isEmpty()) {
            errMsg = "Password is null or empty";
        }

        if (errMsg != null) {
            throw new ConnectionException(errMsg);
        }

        final RestClientConfiguration config = new RestClientConfiguration(url);
        config.setConnectionTimeout(TIMEOUT);
        config.setReadTimeout(TIMEOUT);
        final JasperserverRestClient client = new JasperserverRestClient(config);

        String userName = user;
        if (org != null && !org.isEmpty()) {
            userName += "|" + org;
        }

        final Session session;
        try {
            session = client.authenticate(userName, pwd);
            currentlyLoggedUser = userName;
        } catch (final AuthenticationFailedException afe) {
            throw afe;
        } catch (final Exception ex) {
            throw new ConnectionException(ex);
        }

        System.out.println(">>> User [" + userName + "] successfully loged in");

        return session;
    }
}