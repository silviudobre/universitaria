package be.kdg.programming5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .httpBasic()
                    .authenticationEntryPoint(httpStatusEntryPoint())
                    .and()
                .csrf()
                    .ignoringAntMatchers("/api/universities") // for the separate universitaria client - add page
                    .and()
                .cors()
                    .and()
                .authorizeHttpRequests(
                        auths -> auths
                                .antMatchers(HttpMethod.GET,
                                        "/js/**", "/css/**", "/webjars/**")
                                    .permitAll()
                                .antMatchers(HttpMethod.GET,
                                        "/api/campuses") // for the separate universitaria client - search page
                                    .permitAll()
                                .antMatchers(HttpMethod.POST,
                                        "/api/universities") // for the separate universitaria client - add page
                                    .permitAll()
                                .antMatchers("/", "/index", "/login")
                                    .permitAll()
                                .anyRequest()
                                    .authenticated())
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
                    .and()
                .logout()
                    .permitAll();
        return http.build();
    }

    private HttpStatusEntryPoint httpStatusEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
