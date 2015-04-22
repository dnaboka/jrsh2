package com.jaspersoft.jrsh.command.dto;

import com.jaspersoft.jrsh.util.Mandatory;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/21/15
 */
public class LoginDTO {

    @Mandatory
    private String url;

    @Mandatory
    private String login;

    @Mandatory
    private String password;

    private String organization;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }
}