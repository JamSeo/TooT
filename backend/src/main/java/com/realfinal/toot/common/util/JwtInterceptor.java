package com.realfinal.toot.common.util;


import com.realfinal.toot.api.user.service.UserService;
import com.realfinal.toot.common.exception.user.NoRefreshTokenInCookieException;
import com.realfinal.toot.common.exception.user.NotLoginedException;
import com.realfinal.toot.common.exception.user.UnexpectedTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final JwtProviderUtil jwtProviderUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String requestPath = request.getRequestURI();
        log.info(
            "=====================================     JWT Interceptor Start : " + requestPath
                + "    =====================================");

        // CORS - Method OPTIONS 는 허용처리해야함
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // error 페이지 요청 시 jwt 토큰 인터셉터 제외
        if ("/error".equals(requestPath)) {
            log.info(
                "=====================================     JWT Interceptor End : Error Page    =====================================");
            return true;
        }

        // RefreshToken Check
        // Cookie에서 refreshToken 추출
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) { //로그인이 필요한 상황
            log.warn(
                "=====================================     JWT Interceptor End : no refreshToken in Cookie    =====================================");
            throw new NoRefreshTokenInCookieException();
        } else {
            if (userService.isLogout(refreshToken)) { // redis에 refreshToken 정보가 없음
                log.warn(
                    "=====================================     JWT Interceptor End : Not Logined User    =====================================");
                throw new NotLoginedException();
            }
        }

        // AccessToken Check
        // accessToken payload에서 userId 추출
        Long userId = jwtProviderUtil.getUserIdFromToken(request.getHeader("accesstoken"));
        // 추출된 userId가 DB에 존재하는지 확인
        if (!userService.isUser(userId)) { // 회원가입하지 않은(부적절한) accessToken일때
            log.warn(
                "=====================================     JWT Interceptor End : Unexpected AccessToken    =====================================");
            throw new UnexpectedTokenException();
        }

        log.info(
            "=====================================     JWT Interceptor End : TRUE SUCCESS    =====================================");
        return true;
    }
}