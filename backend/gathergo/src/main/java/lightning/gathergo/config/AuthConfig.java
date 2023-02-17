package lightning.gathergo.config;

import lightning.gathergo.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> loginFilter(final AuthenticationFilter filter) {
        // login 필요한 페이지 접속 할 때마다 Session 체크 실행
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/article/test");  // DummyController.class의 테스트 메서드.
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
