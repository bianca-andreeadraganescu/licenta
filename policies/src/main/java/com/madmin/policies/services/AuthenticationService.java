package com.madmin.policies.services;

import com.madmin.policies.object.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.madmin.policies.exceptions.AuthenticationException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class AuthenticationService {

//    @Value("${jwt.secret}")
    private static final String SECRET_KEY = "TbbxPCD+cas1tMNaEi8x7N/q7RbhFC3ckcS+ZLxKJtYpVJFwn5/dOtjTJ1s+NDL0";
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String login(String username, String password) {
//        // Implement user authentication logic and check credentials
//        // If successful, generate a JWT token and return it
//        User user = userService.authenticateUser(username, password);
//        if (user != null) {
//            // Generate the JWT token
//            String token = generateToken(user.getUsername(), user.getRoles());
//            return token;
//        } else {
//            throw new AuthenticationException("Invalid credentials");
//        }
//    }

//    private String generateToken(String username, List<String> roles) {
//        // Generate the JWT token using jjwt library
//        // Add user roles as claims to the token
//        // Set expiration time, secret key, etc.
//        // Return the token
//        // Example code:
//        String token = Jwts.builder()
//                .setSubject(username)
//                .claim("roles", roles)
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//        return token;
//    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

//    public Claims getClaimsFromToken(String token) {
//        // Parse the token and get the claims
//        try {
//            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        } catch (Exception e) {
//            return null; // Invalid token or expired
//        }
//    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Check if the token is valid and not expired
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
}
