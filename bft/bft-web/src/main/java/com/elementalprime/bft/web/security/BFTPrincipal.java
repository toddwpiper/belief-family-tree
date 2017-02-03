package com.elementalprime.bft.web.security;

import java.security.Principal;

public class BFTPrincipal {

	private Principal principal;
	private String displayName;

	public BFTPrincipal(Principal principal, String displayName) {
		this.principal = principal;
		this.displayName = displayName;
	}

	public Principal getPrinciple() {
		return principal;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return "SdsmPrincipal [principal.name=" + principal.getName() + ", displayName=" + displayName + "]";
	}
	
}
