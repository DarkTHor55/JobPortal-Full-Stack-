package com.JobPortal.Configuration;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    private static final  String SECRECT = createSecretKey();



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
//        System.out.println(token);
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*24*7))
                .claim("email", auth.getName())
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public static String createSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[36]; //36*8=288 (>256 bits required for HS256)
        secureRandom.nextBytes(secretBytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(secretBytes);
    }



    public SecretKey getKey() {

        byte[] keyByte = Decoders.BASE64URL.decode(SECRECT);
        return Keys.hmacShaKeyFor(keyByte);
    }

}
