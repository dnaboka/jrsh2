package com.jaspersoft.jrsh.command;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;
import com.jaspersoft.jrsh.service.provider.SessionProvider;
import com.jaspersoft.jrsh.util.ResultCode;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/23/15
 */
public class ServerInfoCommand implements Command {

    public static final String NAME = "serverinfo";

    private static final String USAGE_DESCRIPTION = "serverinfo";

    private Object paramDTO;

    static {
        CommandFactory.registerCommand(NAME, ServerInfoCommand.class);
    }

    @Override
    public ResultCode call() throws Exception {

        final Session session = SessionProvider.getInstance().getSession();
        final ServerInfo info = session.serverInfoService().details().getEntity();
        System.out.println("\tServer info:");
        System.out.println("\t\tVersion: " + info.getVersion());
        System.out.println("\t\tFeatures: " + info.getFeatures());
        System.out.println("\t\tEdition: " + info.getEdition());
        System.out.println("\t\tLicenseType: " + info.getLicenseType());
        System.out.println("\t\tDateFormatPattern: " + info.getDateFormatPattern());
        System.out.println("\t\tDatetimeFormatPattern: " + info.getDatetimeFormatPattern());
        System.out.println("\t\tBuild: " + info.getBuild());
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