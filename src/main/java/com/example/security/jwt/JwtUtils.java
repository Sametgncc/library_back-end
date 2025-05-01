package com.example.security.jwt;




import com.example.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger LOGGER =  LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtExpirationMs}") // aplication propertiese gidip o degerleri burada belirlenir
    private long jwtExpirationInMs ;

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;

    // Generate JWT
    public String generateJwtToken(Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateTokenFromUsername(userDetails.getUsername());


    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact(); // create etmek icin

    }


    //Validate JWT

    public boolean validateJwtToken(String jwtToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken); // parser parcalama islemi icin
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT Token is expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT Token is unsupported : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("JWT Token is invalid : {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("JWT Token is invalid : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT is empty : {}", e.getMessage());
        }
        return false;

    }


    //getUsernameFromJWT

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
