package com.expense.api.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.expense.api.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {
	private final String SECRET_KEY = "thisissecretkeyofmyappexpensetracker2025august";
	private final long EXPIRATION_TIME = 1000 * 60 * 60;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(User u) {
		return Jwts.builder().setSubject(u.getEmail()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUserName(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isTokenExpired(String token) {
		try {
			Date exDate = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
					.getExpiration();
			return exDate.before(new Date());
		} catch (Exception e) {
			return true;
		}
	}

	public boolean isTokenValid(String token, String username) {
		String extractedUserName = extractUserName(token);
		if (extractedUserName == null)
			return false;
		return extractedUserName.equals(username) && !isTokenExpired(token);
	}
}
