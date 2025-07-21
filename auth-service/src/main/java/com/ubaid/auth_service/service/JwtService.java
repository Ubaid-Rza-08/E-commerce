package com.ubaid.auth_service.service;

import com.ubaid.auth_service.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {
    String base64Secret = "VYm92hHgq/FS0I4awR87SFnD7t68dZ0je8PI4Q45ZwhJex9VILCWdJhbfipCROpHZ8J7Z5CfoqWqPpgQ0Yo4CA==";
    byte[] decodedSecret = Base64.getDecoder().decode(base64Secret);
    SecretKey key = Keys.hmacShaKeyFor(decodedSecret);

    public String generateToken( UserEntity userEntity){
        Map<String,Object> claims=new HashMap<>();
        claims.put("email",userEntity.getEmail());
        claims.put("roles",userEntity.getRoles());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userEntity.getUser_id().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .and()
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public String generateRefreshToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuer("ubaid-rza")
                .audience().add("E-Com-frontend")
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*24))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public Map<String ,Object>validateAndExtractClaims(String token){
        Map<String ,Object>response=new HashMap<>();
        try{
            Claims claims=extractAllClaims(token);
            response.put("user_id",claims.getSubject());
            response.put("roles",claims.get("roles"));
            response.put("id",claims.getId());
            response.put("issuer",claims.getIssuer());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token validation failed");

        }
        return response;
    }
    public boolean validateToken(String token){
        return isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }
    public String extractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        try{
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}