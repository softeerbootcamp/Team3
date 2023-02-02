package lightning.gathergo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class AuthenticationFilter implements Filter {
    // 로그인이 필요한 모든 게시글 url에 대해 동작, 세션이 없으면 로그인 창으로 리다이렉트
    // Session 존재 유무 체크
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. session 정보 확인하는지 확인
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            /*Cookie cookie = Arrays.stream(httpRequest.getCookies())
                    .filter((c) -> c.getName().equals("sessionId"))
                    .findFirst().orElseThrow(() -> new RuntimeException("로그인 정보가 존재하지 않습니다"));*/
            String sessionId = httpRequest.getHeader("sessionId");
            logger.debug(sessionId);
            if(sessionId == null)
                throw new RuntimeException("로그인 정보가 존재하지 않습니다");
            chain.doFilter(request, response);

        } catch(RuntimeException e) {
            httpResponse.setStatus(HttpServletResponse.SC_FOUND);
            httpResponse.setHeader("Location", "/login");
        }
    }
}
