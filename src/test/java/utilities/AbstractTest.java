/*
 * AbstractTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import security.LoginService;

public abstract class AbstractTest {

	private static Properties	prop;
	// Supporting services --------------------------------

	@Autowired
	private LoginService		loginService;


	// Set up and tear down -------------------------------

	@Before
	public void setUp() {
		AbstractTest.prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("src/main/resources/populate.properties");

			//load a properties file
			AbstractTest.prop.load(input);

		} catch (final IOException e) {

			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (final Exception e2) {
					e2.printStackTrace();
				}
		}
	}

	@After
	public void tearDown() {
	}

	// Supporting methods ---------------------------------

	public void authenticate(final String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = this.loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);
		}

		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	public void unauthenticate() {
		this.authenticate(null);
	}

	public void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}

	public int extract(final String beanName) {
		int result;
		result = Integer.valueOf(AbstractTest.prop.getProperty(beanName));
		return result;
	}
}
