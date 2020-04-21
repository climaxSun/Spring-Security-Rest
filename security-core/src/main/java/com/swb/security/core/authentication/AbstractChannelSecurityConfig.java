/**
 * 
 */
package com.swb.security.core.authentication;

import com.swb.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zhailiang
 *
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("swbSecurityAuthenticationSuccessHandler")
	private AuthenticationSuccessHandler successHandler;

	@Autowired
	@Qualifier("swbSecurityAuthenticationFailureHandler")
	private AuthenticationFailureHandler failureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(successHandler)
			.failureHandler(failureHandler);
	}
	
}
