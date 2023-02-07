package lightning.gathergo.service;

import lightning.gathergo.model.Session;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import java.util.UUID;

import static lightning.gathergo.service.CookieService.SESSION_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@Transactional
@SpringBootTest
class CookieServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CookieServiceTest.class);

    private final SessionService sessionService;

    private final CookieService cookieService;

    private final String expectedSessionId = String.valueOf(UUID.randomUUID());

    @Autowired
    CookieServiceTest(SessionService sessionService, CookieService cookieService) {
        this.sessionService = sessionService;
        this.cookieService = cookieService;
    }

    @Test
    void testCreateSessionCookie() {
        SoftAssertions softly = new SoftAssertions();

        // given
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        cookieService.createSessionCookie(SESSION_ID, expectedSessionId, response);

        Cookie cookie = response.getCookie(SESSION_ID);

        // then
        assert cookie != null;
        softly.assertThat(cookie.getName()).isEqualTo(SESSION_ID);
        softly.assertThat(cookie.getPath()).isEqualTo("/");
        softly.assertThat(cookie.isHttpOnly()).as("HttpOnly 쿠키?").isTrue();
        softly.assertThat(cookie.getValue()).isEqualTo(expectedSessionId);

        softly.assertAll();
    }

    @Test
    void ifNotValidCookie() {
        SoftAssertions softly = new SoftAssertions();

        // given
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        cookieService.createSessionCookie(SESSION_ID, expectedSessionId, 0, response);

        Cookie cookie = response.getCookie(SESSION_ID);

        // then
        assert cookie != null;
        softly.assertThat(cookie.getName()).isEqualTo(SESSION_ID);
        softly.assertThat(cookie.getPath()).isEqualTo("/");
        Cookie[] cookies = {cookie};

        softly.assertThat(cookieService.ifValidCookie(cookies, "expectedSessionId")).isNull();
        softly.assertAll();
    }

    @Test
    void retrieveSession() {
        SoftAssertions softly = new SoftAssertions();

        // given
        MockHttpServletResponse response = new MockHttpServletResponse();

        /*when(sessionService.createSession("asdf", "gildong"))
                .thenReturn(new Session(expectedSessionId, "Asdf", "gildong", LocalDateTime.now()));*/

        Session createdSession = sessionService.createSession("asdf", "gildong");  // 세션 생성

        cookieService.createSessionCookie(SESSION_ID, createdSession.getSessionId(), 0, response); // 쿠키 생성

        // when
        Session foundSession = cookieService.retrieveSession(response.getCookies());

        // then
        assertThat(foundSession).isNotNull();
        assertThat(createdSession.getSessionId()).isEqualTo(foundSession.getSessionId());

        softly.assertAll();
    }
}