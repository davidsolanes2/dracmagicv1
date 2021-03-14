package com.app.dracmagicv1.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
			auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select email, password, estatus from User where email=?")
			.authoritiesByUsernameQuery("select u.email, r.name from users_roles ur " +
					"inner join User u on u.id = ur.user_id " + 
					"inner join Role r on r.id = ur.role_id " + 
					"where u.email = ?");
		
	}
}
