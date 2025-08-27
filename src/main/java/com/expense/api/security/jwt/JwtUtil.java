package com.expense.api.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.expense.api.security.redis.JwtBlacklistService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Autowired
	private JwtBlacklistService jwtBlacklistService;
	private final String SECRET_KEY = "thisissecretkeyofmyappexpensetracker2025august";
	private final long EXPIRATION_TIME = 1000 * 60 * 60;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
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
		return extractedUserName.equals(username) && !isTokenExpired(token)
				&& !jwtBlacklistService.isTokenBlacklisted(token);
	}

	public long getRemainingExpiration(String token) {
		try {
			Date expiration = Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getExpiration();
			long remainingMillis = expiration.getTime() - System.currentTimeMillis();
			return Math.max(remainingMillis, 0);
		} catch (Exception e) {
			return 0;
		}
	}
}
