package team.okky.personnel_management.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.okky.personnel_management.access.Access;
import team.okky.personnel_management.access.AccessService;
import team.okky.personnel_management.config.auth.PrincipalDetails;
import team.okky.personnel_management.manager.Manager;
import team.okky.personnel_management.manager.ManagerRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ManagerRepository managerRepository;
    private final AccessService accessService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ManagerRepository managerRepository, AccessService accessService) {
        this.authenticationManager = authenticationManager;
        this.managerRepository = managerRepository;
        this.accessService = accessService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            log.info("로그인 요청");
            
            ObjectMapper objectMapper = new ObjectMapper();
            Manager manager = objectMapper.readValue(request.getInputStream(), Manager.class);

            String ip = request.getHeader("X-FORWARDED-FOR");
            if(ip==null || ip.isEmpty()){ip = request.getRemoteAddr();}
            Access access = Access.builder()
                    .accessDate(LocalDateTime.now())
                    .accessArea(ip)
                    .manager(managerRepository.findByEmail(manager.getMnEmail()).get(0))
                    .build();

            accessService.save(access);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            manager.getMnEmail(),
                            manager.getMnPw()
                    );

            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

            return authentication;

        }catch (BadCredentialsException e){
            log.info("비밀번호가 일치하지 않습니다.");
        }catch (LockedException e){
            log.info("계정이 잠겨 있습니다.");
        }catch (DisabledException e){
            log.info("계정이 비활성화 되어있습니다.");
        }catch (AccountExpiredException e){
            log.info("계정 유효기간이 만료되었습니다.");
        }catch (CredentialsExpiredException e){
            log.info("계정 비밀번호 유효기간이 만료되었습니다.");
        }catch (Exception e){
            log.info("로그인 인증 과정 예외 발생");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 완료");

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("jwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("email", principal.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        
        log.info("JWT 토큰 생성&전달 완료");

    }
}
