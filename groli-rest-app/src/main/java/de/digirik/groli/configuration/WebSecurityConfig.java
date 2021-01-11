package de.digirik.groli.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import de.digirik.groli.configuration.filter.AuthTokenFilter;
import de.digirik.groli.service.GroliUserDetailsService;
import de.digirik.groli.service.util.JwtUtils;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final GroliUserDetailsService groliUserDetailsService;
	private final AuthEntryPointJwt authEntryPointJwt;
	private final JwtUtils jwtUtils;

	public WebSecurityConfig(GroliUserDetailsService groliUserDetailsService,
	        AuthEntryPointJwt authEntryPointJwt, JwtUtils jwtUtils) {
		this.groliUserDetailsService = groliUserDetailsService;
		this.authEntryPointJwt = authEntryPointJwt;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter(jwtUtils, groliUserDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	// Not needed for now because we do that in the AuthTokenFilter?
	// @Bean
	// public DaoAuthenticationProvider authenticationProvider(
	// GroliUserDetailsService groliUserDetailsService) {
	// final DaoAuthenticationProvider authProvider =
	// new DaoAuthenticationProvider();
	// authProvider.setUserDetailsService(groliUserDetailsService);
	// authProvider.setPasswordEncoder(this.passwordEncoder());
	// return authProvider;
	// }

	@Override
	public void configure(
	        AuthenticationManagerBuilder authenticationManagerBuilder)
	        throws Exception {
		authenticationManagerBuilder.userDetailsService(groliUserDetailsService)
		    .passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();

		http.exceptionHandling().authenticationEntryPoint(authEntryPointJwt);

		http.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers("/api/auth/**").permitAll();

		http.addFilterBefore(authenticationJwtTokenFilter(),
		    UsernamePasswordAuthenticationFilter.class);
	}

}
