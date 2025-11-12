package com.tasktracker.backend.security.jwt;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtService {

    @Value("spring.jwt.secret")
    private String jwtSecret;

    public JwtAuthenticationDto generatedAuthToken(String email){
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generatedJwtToken(email));
        jwtDto.setRefreshToken(generatedRefreshToken(email));
        return jwtDto;

    }

    public JwtAuthenticationDto refreshBaseToken(String email, String refreshToken) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generatedJwtToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;

    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseEncryptedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JwtException", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JwtException", ex);
        } catch (MalformedJwtException ex) {
            log.error("Malformed JwtException", ex);
        } catch (SecurityException ex) {
            log.error("Security Exception", ex);
        } catch (Exception ex) {
            log.error("invalid token", ex);
        }
        return false;
    }


    private String generatedJwtToken(String email) { // todo сроки жзини тянуть из application файла
        Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private String generatedRefreshToken(String email) { // todo сроки жзини тянуть из application файла
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
