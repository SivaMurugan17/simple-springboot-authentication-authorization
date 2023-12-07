package com.siva.Spring.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;

@Component
public class JwtUtils {

    int jwtExpirationMs = 24*60*60;

    String jwtSecret = "======================BezKoder=Spring===========================";

    String jwtCookieName = "AuthCookie";
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName,jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
        return cookie;
    }

    private String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))).compact();
    }

    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie =ResponseCookie.from(jwtCookieName,null).path("/api").build();
        return cookie;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request,jwtCookieName);
        if(cookie != null){
            return cookie.getValue();
        }
        else{
            return null;
        }
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))).build().parse(jwt);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))).build().parseClaimsJwt(jwt).getBody().getSubject();
    }
}
