package com.elementalprime.bft.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	AuthenticationManager manager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		LOG.debug("Adding UserProfileLoggingFilter");

		UserProfileLoggingFilter filter = new UserProfileLoggingFilter();

		filter.setAuthenticationManager(manager);
		filter.setAuthenticationDetailsSource(preAuthADS());

		// Expect pre-auth before spring security
		http.addFilterBefore(filter, SecurityContextHolderAwareRequestFilter.class);

		http.headers().cacheControl().disable(); // we want caching

		http.authorizeRequests().anyRequest().permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		/*web.ignoring().antMatchers("/**");*/
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		LOG.debug("Adding PreAuthenticatedAuthenticationProvider");

		PreAuthenticatedAuthenticationProvider x = new PreAuthenticatedAuthenticationProvider();

		/*
		 * We take a pre-authenticated token and either load or create it from
		 * our local DB
		 */
		x.setPreAuthenticatedUserDetailsService((PreAuthenticatedAuthenticationToken t) -> {

			Collection<? extends GrantedAuthority> authorities = null;

			if (t.getDetails() != null) {
				GrantedAuthoritiesContainer details = (GrantedAuthoritiesContainer) t.getDetails();
				authorities = details.getGrantedAuthorities();
			}

			LOG.info("Loading user [{}] with granted authorities: [{}]", t.getName(), authorities);

			User user = new User(t.getName(), UUID.randomUUID().toString(), authorities);

			return user;
		});

		auth.authenticationProvider(x);
	}

	/**
     * Takes specific roles from the request's UserPrincipal
     */
    private J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource preAuthADS() {
    	
    	Set<String> roles = new HashSet<String>();
    	roles.add("ADMIN");
    	roles.add("USER");
    	roles.add("TMEXAM-USER");
    	    	
        J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource x = new J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource();
        x.setUserRoles2GrantedAuthoritiesMapper(attributes -> attributes == null ? new ArrayList<>() : attributes.stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));
        x.setMappableRolesRetriever(() -> roles);
        return x;
    }

	/**
	 * Defined so we can wire {@link SecurityConfig#manager} in and use it in
	 * {@link SecurityConfig#configure(HttpSecurity)}
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
