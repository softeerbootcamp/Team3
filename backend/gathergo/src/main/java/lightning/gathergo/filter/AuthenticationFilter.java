package lightning.gathergo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Order(1)
@Component
public class AuthenticationFilter implements Filter {
    // 로그인이 필요한 모든 게시글 url에 대해 동작, 세션이 없으면 로그인 창으로 리다이렉트
    // Session 존재 유무 체크
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static Cookie nullCookie = new Cookie("sessionId", "");
    private final static String SESSIONID = "sessionId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // session 정보 존재하는지 확인
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie sessionCookie = Arrays.stream(httpRequest.getCookies())
                .filter((c) -> c.getName().equals(SESSIONID))
                .findFirst().orElse(nullCookie);

        String sessionId = sessionCookie.getValue().trim();

        logger.debug(sessionId);
        if(sessionId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_FOUND);
            httpResponse.setHeader("Location", "/login");
        }

        chain.doFilter(request, response);
    }
}
