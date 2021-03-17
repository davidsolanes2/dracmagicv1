package com.app.dracmagicv1.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	/**
	 * Personalizamos el Acceso a las URLs de la aplicación
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(
        			"/css/**",
        			"/images/**",
        			"/js/**").permitAll()
        .antMatchers(
        			"/",
        			"/login",
        			"/signup").permitAll()
        .and().formLogin().loginPage("/login").permitAll()
        .and().logout().permitAll();
        
	}
	
	/**
	 *  Implementación de Spring Security que encripta passwords con el algoritmo Bcrypt
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
