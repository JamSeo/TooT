package com.realfinal.toot.common.util;

import com.realfinal.toot.common.exception.user.CreateTokenException;
import com.realfinal.toot.common.exception.user.EmptyTokenException;
import com.realfinal.toot.common.exception.user.ExpiredTokenException;
import com.realfinal.toot.common.exception.user.InvalidTokenException;
import com.realfinal.toot.common.exception.user.RefreshTokenExpiredException;
import com.realfinal.toot.common.exception.user.UnexpectedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProviderUtil {

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    private final RedisUtil redisUtil;

    /**
     * access token 생성
     *
     * @param payload 토큰에 저장할 정보. 우리는 userId (providerId 아님)
     * @return 생성된 token
     */
    public String createAccessToken(String payload) {
        log.info("JwtProviderUtil_createAccessToken_start: " + payload);
        String token = createToken(payload, accessTokenValidityInMilliseconds);
        log.info("JwtProviderUtil_createAccessToken_end: " + token);
        return token;
    }

    /**
     * refresh token 생성
     *
     * @return 생성된 refresh token
     */
    public String createRefreshToken() {
        log.info("JwtProviderUtil_createRefreshToken_start");
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        String token = createToken(generatedString, refreshTokenValidityInMilliseconds);
        log.info("JwtProviderUtil_createRefreshToken_end: " + token);
        return token;
    }

    /**
     * 토큰 생성. (access, refresh 토큰 생성시 사용하기 위한 메서드)
     *
     * @param payload      토큰에 저장할 정보. 우리는 userId (providerId 아님)
     * @param expireLength 만료 기간
     * @return 만들어진 토큰
     */
    public String createToken(String payload, long expireLength) {
        log.info("JwtProviderUtil_createToken_start: " + payload + " " + expireLength);
        try {
            Claims claims = Jwts.claims().setSubject(payload);
            Date now = new Date();
            Date validity = new Date(now.getTime() + expireLength);
            String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
            log.info("JwtProviderUtil_createToken_end: " + token);
            return token;
        } catch (Exception e) {
            throw new CreateTokenException();
        }
    }

    /**
     * 토큰에 담긴 정보 추출 메서드 payload의 sub에 userId가 담겨있으므로 추출
     *
     * @param accessToken 정보 추출할 토큰. (우리는 refresh token에 정보를 담지 않아 access만 가능하다)
     * @return userId (providerId 아님)
     */
    public Long getUserIdFromToken(String accessToken) {
//        log.info("JwtProviderUtil_getUserIdFromToken_start: " + accessToken);
        try {
            Long userId = Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject());
//            log.info("JwtProviderUtil_getUserIdFromToken_end: " + userId);
            return userId;
        } catch (ExpiredJwtException e) {
            Long userId = Long.parseLong(e.getClaims().getSubject());
            log.info("JwtProviderUtil_getUserIdFromToken_exception: " + userId);
            return userId;
        } catch (JwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    /**
     * 토큰 유효성 체크
     *
     * @param token 체크하고 싶은 토큰
     * @return 유효하면 true, 아니면 false 리턴
     */
    public Boolean validateToken(String token) {
//        log.info("JwtProviderUtil_validateToken_start: " + token);
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            Boolean result = !claimsJws.getBody().getExpiration().before(new Date());
//            log.info("JwtProviderUtil_validateToken_end: " + result);
            return result;
        } catch (ExpiredJwtException exception) {
            log.info("JwtProviderUtil_validateToken_exception: " + false);
            return false;
        } catch (Exception e) {
            log.warn("JwtProviderUtil_validateToken_end: unexpected Exception occured");
            throw new UnexpectedTokenException();
        }
    }

    /**
     * Refresh Token의 유효성 + 데이터 담겨있는지 검사. 문제 있으면 에러 던짐.
     *
     * @param refreshToken Refresh Token 문자열
     */
    public void refreshTokenExtractor(String refreshToken) {
        log.info("JwtProviderUtil_refreshTokenExtractor_start: " + refreshToken);
        // Token 유효성 검사
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new EmptyTokenException();
        }
        if (!validateToken(refreshToken)) {
            throw new ExpiredTokenException();
        }
        log.info("JwtProviderUtil_refreshTokenExtractor_end: token validate success");
    }

    /**
     * access token 재발급
     *
     * @param refreshToken 레디스에 저장된 토큰. 확인 후 access token 재발급 시 사용
     * @return 새로 발급된 access token
     */
    public String recreateAccessToken(String refreshToken) {
        log.info("JwtProviderUtil_recreateAccessToken_start: " + refreshToken);
        refreshTokenExtractor(refreshToken);
        String data = redisUtil.getData(refreshToken); // userId
        if (data == null) { //리프레시도 만료된 경우.
            log.info("JwtProviderUtil_recreateAccessToken_mid: refresh token expired: "
                + refreshToken);
            throw new RefreshTokenExpiredException();
        }
        String newAccessToken = createAccessToken(data);
        log.info("JwtProviderUtil_recreateAccessToken_end: " + newAccessToken);
        return newAccessToken;
    }
}
