package com.sonam.ecommerce.ecommercebackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Contains methods for generating, parsing, and validating JWT tokens.
@Component
public class JwtHelper  {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60; // token is valid for 1 day

    private String secret = generateSafeToken(); // contains Base64-encoded secret key

    // Generate a secure random byte array with 64 bytes (512 bits)
    private String generateSafeToken() {
        byte[] secretBytes = new byte[64];
        new SecureRandom().nextBytes(secretBytes);
        // Convert the byte array to a Base64-encoded string
        return Base64.getEncoder().encodeToString(secretBytes);
    }

    //retrieve username(email) from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token's claims
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // extract specific claim from token's claims
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the appropriate signing key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    //check if the token has expired by comparing token's expiration date to the current date.
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //generate refresh token for user if token has expired
    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 604800000)) // 7 days
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token); //extract username
        // check if username matches the username of 'userDetails' obj
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // obtain a signing key for JWT token generation using a secret.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        //System.out.println("Secret: "+secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
