package config;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

	private static final String SECRET_KEY="keaWy48Z9cS3TcG85/1S+IMYYARae2vyOkd+I23G0+R+A0RQhHkBmcrZ5heDZXRoVk/rIAdx7tZSIC5AWVUkqDfSybvPu5jUN0OPL0D5lkVY9BqnTFL2quh8EH1SqM+TM0b5p1sah1L3LeqJQZBr0bWYsIpHQId2ZmLlxd0qkqMU+f4FtosF1lgus3ePzEvVg6QcKwoh9OJk97zo0ihRZavQd2ORhLoHljliwZwGkpWH44dVxWIw1GpcNJsXBL5WgZru5vubzRT9nSsUwLEHxUXvfAvs2hPLB6hOuhTC+LFQnnqAneuNCLozsltKysNGs4ZqEfNSYdWoU2oM55zvBih8rtXqUfdTSCGHXpjqjcg=\r\n"
			+ "";
	public String extratUsername (String token ) {
		return extractClaim(token,Claims::getSubject);
	}

	public <T> T extractClaim(String token , Function <Claims,T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	
	
	
	
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	//pour la generation du token
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24))
				.signWith(getSignInkey(), SignatureAlgorithm.HS256).compact();
	}

	//pour la validation du token
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extratUsername(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));

	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}


	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}











	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInkey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY)	;
        return  Keys.hmacShaKeyFor(keyBytes);
	}

}
