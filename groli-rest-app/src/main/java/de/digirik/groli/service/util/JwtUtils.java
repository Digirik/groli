package de.digirik.groli.service.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import de.digirik.groli.configuration.GroliUserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtils {

	private final String jwtSecret;
	private final int jwtExpirationInMs;

	public JwtUtils(@Value("${groli.security.jwt-secret}") String jwtSecret,
	        @Value("${groli.security.jwt-expiration-in-ms}") int jwtExpirationInMs) {
		this.jwtSecret = jwtSecret;
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	public String generateJwtToken(Authentication authentication) {
		GroliUserPrincipal userPrincipal =
		        (GroliUserPrincipal) authentication.getPrincipal();

		return Jwts.builder()
		    .setSubject((userPrincipal.getUsername()))
		    .setIssuedAt(new Date())
		    .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
		    .signWith(SignatureAlgorithm.HS512, jwtSecret)
		    .compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
		    .setSigningKey(jwtSecret)
		    .parseClaimsJws(token)
		    .getBody()
		    .getSubject();
	}

	// TODO: add logging
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
		} catch (MalformedJwtException e) {
		} catch (ExpiredJwtException e) {
		} catch (UnsupportedJwtException e) {
		} catch (IllegalArgumentException e) {
		}

		return false;
	}
}
