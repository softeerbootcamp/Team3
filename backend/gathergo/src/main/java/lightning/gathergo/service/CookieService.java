package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieService {
    private static final int DEFAULT_COOKIE_EXPIRATION = 3600;
    public static final String SESSION_ID = "sessionId";

    private static Cookie nullCookie = new Cookie(CookieService.SESSION_ID, "");

    private final SessionService sessionService;

    @Autowired
    public CookieService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void createSessionCookie(String name, String value, HttpServletResponse response) {
        createSessionCookie(name, value, DEFAULT_COOKIE_EXPIRATION, response);
    }

    /**
     * 쿠키 생성 및 반환
     *
     * @param name             쿠키 이름
     * @param value            쿠키 값
     * @param cookieExpiration 쿠키 만료 시간 (초 단위)
     * @param response         HttpServletResponse 객체
     */
    public void createSessionCookie(String name, String value, int cookieExpiration, HttpServletResponse response) {
        Cookie sessionCookie = new Cookie(name, value);
        sessionCookie.setMaxAge(cookieExpiration);
        sessionCookie.setHttpOnly(false);
        sessionCookie.setPath("/; SameSite=None");

        response.addCookie(sessionCookie);
    }

    /**
     * 지정된 이름의 쿠키를 찾기 위해 쿠키를 필터링
     * 값이 빈 것이 아니면 첫 번째 일치하는 쿠키를 반환하고, 그렇지 않으면 null을 반환
     *
     * @param cookies an array of cookies
     * @param name    the name of the cookie to find
     * @return the first matching cookie if it exists and its value is not blank, otherwise returns null
     */
    public Cookie ifValidCookie(Cookie[] cookies, String name) {
        Cookie cookie = Arrays.stream(cookies)
                .filter((c) -> c.getName().equals(name))
                .findFirst().orElse(nullCookie);

        String cookieValue = cookie.getValue().trim();

        if(!cookieValue.isBlank())
            return cookie;
        else
            return null;
    }

    /**
     * 지정된 이름의 쿠키를 검색한 다음 쿠키 값과 일치하는 세션을 세션 저장소에서 찾아서 세션을 검색
     * 세션이 존재하면 주어진 "sessionId" 에 연관된 SessionRepository에세션을 반환하고, 그렇지 않으면 null을 반환
     *
     * @param cookies an array of cookies
     * @return the session related to "sessionId" in SessionRepository if it exists, otherwise returns null
     */
    public Session retrieveSession(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        Cookie foundCookie = ifValidCookie(cookies, SESSION_ID);
        if(foundCookie == null)
            return null;

        // SessionRepository에 접근해 expiration 및 세션 정보 일치하는지 확인
        return sessionService.findSessionBySID(foundCookie.getValue().trim()).orElse(null);
    }
    /**
     * Cookie의 세션 정보(sessionId)를 통해, SessionRepository의 세션 정보 삭제
     *
     * @param cookies an array of cookies
     * @return 세션 정보가 존재하지 않으면 Null, 이외에는 이전 Session 반환
     */
    public Session invalidateSession(Cookie[] cookies, HttpServletResponse response) {
        if (cookies == null) {
            return null;
        }

        Cookie foundCookie = ifValidCookie(cookies, SESSION_ID);
        if (foundCookie.equals(nullCookie)) {
            return null;
        }
        String sessionId = foundCookie.getValue().trim();
        foundCookie.setMaxAge(0);
        foundCookie.setValue("");
        response.addCookie(foundCookie);
        return sessionService.deleteSession(sessionId);
    }
}
