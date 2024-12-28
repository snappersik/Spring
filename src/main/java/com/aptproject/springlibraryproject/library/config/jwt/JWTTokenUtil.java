package com.aptproject.springlibraryproject.library.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JWTTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 684800000;
    public final String secret="l123lsd7TI716t2_oe";
    private static final ObjectMapper objectMapper=new ObjectMapper();
    public String generateToken(final UserDetails payload) {    return Jwts.builder()
            .setSubject(payload.toString())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public Boolean validateToken(final String token, UserDetails userDetails){
        final String userName=getUsernameFromToken(token);
        return(userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Boolean isTokenExpired(final String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());}

    private Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }
    public String getUsernameFromToken(final String token) {    return getStringValueFromTokenByKey(token, "username");
    }
    public String getRoleFromToken(final String token) {    return getStringValueFromTokenByKey(token, "user_role");
    }
    private String getStringValueFromTokenByKey(final String token,final String key){
        String claim = getClaimsFromToken(token, Claims::getSubject);
        JsonNode claimJSON=null;
        try{
            claimJSON = objectMapper.readTree(claim);
        } catch (JsonProcessingException e){
            log.error("JWTTokenUtil#getUsernameFromToken():{}", e.getMessage());
        }
        if(claimJSON !=null) {
            return claimJSON.get(key).asText();
        } else{
            return  null;
        }
    }
    private <T> T getClaimsFromToken(final String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }
}