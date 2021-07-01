package com.leiran.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtils implements Serializable {


    private static final String secret = "secret";

    private static final String token = "token";

    private static final Long expiration = 604800000L;
    private static final Clock clock = DefaultClock.INSTANCE;

    public static String generateToken(Map<String, Object> claims, String subject) {
        final Date createDate = clock.now();
        final Date expirationDate = new Date(createDate.getTime() + expiration);

        return "Bearer " + Jwts.builder().
                setClaims(claims).setSubject(subject)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static void validateToken(String token, String subject, Integer tokenVersion) throws JwtException {
        Claims claims = getClaimsFromToken(token);
        Integer tTokenVersion = claims.get("tokenVersion", Integer.class);
        boolean flag;
        flag = subject.equals(getClaimFromToken(token, Claims::getSubject));
        if (!flag) {
            throw new JwtException("please login.");
        }
        flag = isTokenExpired(token);
        if (flag) {
            throw new JwtException("please login.");
        }
        flag = tokenVersion.equals(tTokenVersion);
        if (!flag) {
            throw new JwtException("please login.");
        }
    }

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //
    private static <T> T getClaimFromToken(String token, Function<Claims, T> function) {
        final Claims claims = getClaimsFromToken(token);
        return function.apply(claims);
    }

    //
    private static Claims getClaimsFromToken(String token) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("please login.");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return body;
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    public static Boolean isTokenAlmostExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.getTime() < (clock.now().getTime() - 1000 * 60 * 60);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tokenVersion", 28);
        String s = generateToken(map, "1376087783730413570");
        System.out.println(s);
    }
}
