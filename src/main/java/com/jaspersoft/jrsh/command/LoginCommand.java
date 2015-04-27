package com.jaspersoft.jrsh.command;

import com.jaspersoft.jrsh.command.dto.LoginDTO;
import com.jaspersoft.jrsh.exception.ExecutionException;
import com.jaspersoft.jrsh.service.provider.SessionProvider;
import com.jaspersoft.jrsh.util.ResultCode;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/21/15
 */
public class LoginCommand implements Command<LoginDTO> {

    public static final String NAME = "login";

    public static final String USAGE_DESCRIPTION = "login url <url> login <login> password <password> [organization <org>]";

    private LoginDTO loginDTO;

    static {
        CommandFactory.registerCommand(NAME, LoginCommand.class);
    }

    @Override
    public ResultCode call() throws Exception {

        SessionProvider.getInstance().invalidateSession();
        SessionProvider.getInstance().getSession(loginDTO.getUrl(), loginDTO.getLogin(), loginDTO.getPassword(),
                loginDTO.getOrganization());
        return ResultCode.SUCCESS;
    }

    @Override
    public LoginDTO getParamDTO() {
        return loginDTO;
    }

    @Override
    public void setParamDTO(final LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsageDescription() {
        return USAGE_DESCRIPTION;
    }

    @Override
    public boolean hasArguments() {
        return true;
    }
}