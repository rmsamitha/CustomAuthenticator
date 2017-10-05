package org.samitha.custom.testing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;

import java.util.Map;
import java.util.logging.Logger;

public class CustomAPIAuthenticationHandler extends AbstractHandler {
    private static final Log log = LogFactory.getLog(CustomAPIAuthenticationHandler.class);

    public boolean handleRequest(MessageContext messageContext) {
        log.info("Going to handle request...");
        try {
            if (authenticate(messageContext)) {
                return true;
            }
        } catch (APISecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean handleResponse(MessageContext messageContext) {
        return true;
    }

    public boolean authenticate(MessageContext synCtx) throws APISecurityException {
        log.info("Going to authenticate...");
        Map headers = getTransportHeaders(synCtx);
        String authHeader = getAuthorizationHeader(headers);
        if (authHeader.startsWith("Bearer: SDSD4343DSSZZXZXZ43343")) {
            log.info("Authenticated..!!");
            return true;
        }
        log.info("Authentication failed");
        return false;
    }

    private String getAuthorizationHeader(Map headers) {
        return (String) headers.get("Authorization");
    }

    private Map getTransportHeaders(MessageContext messageContext) {
        return (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
    }
}