package team.okky.personnel_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import team.okky.personnel_management.access.AccessService;
import team.okky.personnel_management.config.exception.AuthenticationException;
import team.okky.personnel_management.config.exception.AuthorizationException;
import team.okky.personnel_management.config.jwt.JwtAuthenticationFilter;
import team.okky.personnel_management.config.jwt.JwtAuthorizationFilter;
import team.okky.personnel_management.manager.ManagerRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final ManagerRepository managerRepository;
    private final AccessService accessService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), managerRepository, accessService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), managerRepository))
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationException())
                .accessDeniedHandler(new AuthorizationException())
                .and()
                .authorizeRequests()
                .antMatchers("/init")
                .permitAll()
                .anyRequest()
                .access("hasRole('ROLE_MANAGER')");
    }
}
