package com.elementalprime.bft.web.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class UserProfileLoggingFilter extends AbstractPreAuthenticatedProcessingFilter {

    /**
     * Return the J2EE user name.
     */
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
    	
        Principal p = httpRequest.getUserPrincipal();
        
        BFTPrincipal sdsmPrinciple = new BFTPrincipal(p, httpRequest.getHeader("displayName"));
        
        return sdsmPrinciple;
    }

    /**
     * For J2EE container-based authentication there is no generic way to retrieve the
     * credentials, as such this method returns a fixed dummy value.
     */
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpRequest) {
        return "N/A";
    }

}
