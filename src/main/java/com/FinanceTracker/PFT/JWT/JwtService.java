package com.FinanceTracker.PFT.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {


    public static final String SECRET = "MySuperSecretKey12345#129vs1299x9191gu29snsnhxh18w20bxh";

    public String generateToken(String email , String role){
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("Role",role);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .addClaims(claims)
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());

    }

    public Claims verifySignatureAndExtractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    public String extractUsername(String token){

        return verifySignatureAndExtractAllClaims(token).getSubject();
    }

    public Date getExpiration(String token){

        return verifySignatureAndExtractAllClaims(token).getExpiration();
    }


    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}

