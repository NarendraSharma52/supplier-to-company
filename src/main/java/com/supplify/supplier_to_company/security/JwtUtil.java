package com.supplify.supplier_to_company.security;

//import com.supplify.supplier_to_company.dtos.JwtPayload;
import com.supplify.supplier_to_company.models.Role;
import com.supplify.supplier_to_company.models.User;
import com.supplify.supplier_to_company.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JwtUtil {

    @Value("${jwt.expirationtime}")
    Long expirationTime;
    @Value("${jwt.secret.password}")
    String secretPassword;

    @Autowired
    UserService userService;

    public String generateJwtToken(String email,
                                   List<String> roles){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", roles);
        String jwtToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretPassword)
                .setClaims(claims)
                .compact();
        return jwtToken;
    }

    public Claims decryptToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretPassword)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public boolean isTokenValid(String token){
        Claims  claims = this.decryptToken(token);
        String email = claims.get("email", String.class);
        List<String> roles = claims.get("roles", List.class);
        log.info("Hello Somendra : " + roles);

        User user = userService.findByEmail(email);
        if(user == null){
            return false;
        }
        List<Role> userRoles = user.getRoles();
        for(int i  = 0; i < roles.size(); i++){
            String roleName = roles.get(i);
            boolean flag = false;
            for(int j = 0; j < userRoles.size(); j++){
                if(roleName.equals(userRoles.get(j).getName())){
                    flag = true;
                    break;
                }
            }
            if(flag == false){
                return false;
            }
        }
        return true;
    }

    public Long getExpirationTime(){
        return expirationTime;
    }


}
