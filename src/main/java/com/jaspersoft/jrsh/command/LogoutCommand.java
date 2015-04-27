package com.jaspersoft.jrsh.command;

import com.jaspersoft.jrsh.service.provider.SessionProvider;
import com.jaspersoft.jrsh.util.ResultCode;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/23/15
 */
public class LogoutCommand implements Command<Object> {

    public static final String NAME = "logout";

    private static final String USAGE_DESCRIPTION = "logout";

    private Object paramDTO;

    static {
        CommandFactory.registerCommand(NAME, LogoutCommand.class);
    }

    @Override
    public ResultCode call() throws Exception {

        SessionProvider.getInstance().invalidateSession();
        return ResultCode.SUCCESS;
    }

    @Override
    public Object getParamDTO() {
        return paramDTO;
    }

    @Override
    public void setParamDTO(final Object paramDTO) {
        this.paramDTO = paramDTO;
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
        return false;
    }
}
