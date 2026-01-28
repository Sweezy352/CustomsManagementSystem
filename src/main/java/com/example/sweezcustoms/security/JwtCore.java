package com.example.sweezcustoms.security;

import com.example.sweezcustoms.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class JwtCore {
    @Value("${jwt.key}")
    private String jwtSecret;
    @Value("${jwt.exparation}")
    private Integer jwtExparation;
    @Value("${refresh_token.exaration}")
    private Integer refreshTokenExparation;
    private SecretKey secretKey;

    public SecretKey getSecretKey(){
        if(Objects.isNull(secretKey)){
            byte[] jwtSecretBytes = Decoders.BASE64.decode(jwtSecret);
            secretKey = Keys.hmacShaKeyFor(jwtSecretBytes);
        }

        return secretKey;
    }

    public String generateAccessToken(UserDetails userDetails){
        UserEntity userEntity = (UserEntity) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userEntity.getUsername());
        claims.put("mail", userEntity.getMail());
        claims.put("phone", userEntity.getPhone());
        return buildToken(claims, userDetails, jwtExparation);
    }

    public String generateRefreshToken(UserDetails userDetails){
        UserEntity userEntity = (UserEntity) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userEntity.getId());
        claims.put("mail", userEntity.getMail());

        return buildToken(claims, userDetails, refreshTokenExparation);
    }


    public String buildToken(Map<String, Object> claims, UserDetails userDetails, int expiration){
        UserEntity userEntity = (UserEntity) userDetails;
        Date dateCreated = new Date();
        Date dateExpiration = new Date(dateCreated.getTime() + expiration);
        return Jwts.builder().setClaims(claims)
                .subject(userEntity.getMail())
                .issuedAt(dateCreated)
                .expiration(dateExpiration)
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validationToken(String jwtToken){

        try{
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        }catch (ExpiredJwtException e){
            log.error("Token expired -------------->>>>>>>>>>>>> " + e.getMessage());
        }catch (SignatureException e){
            log.error("Invalid signature -------------->>>>>>>>>>>>> " + e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid token -------------->>>>>>>>>>>>> " + e.getMessage());
        }
        catch (UnsupportedJwtException e){
            log.error("Invalid token -------------->>>>>>>>>>>>> " + e.getMessage());
        }
        catch (IllegalArgumentException e){
            log.error("Invalid token -------------->>>>>>>>>>>>> " + e.getMessage());
        }
        catch (Exception e){
            log.error("Invalid token -------------->>>>>>>>>>>>> " + e.getMessage());
        }
        return false;
    }

    public String extractUsernameFromToken(String jwtToken){
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwtToken).getPayload().getSubject();
    }
}
