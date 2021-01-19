package team.okky.personnel_management.config.awt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import team.okky.personnel_management.config.auth.PrincipalDetails;
import team.okky.personnel_management.domain.Manager;
import team.okky.personnel_management.repository.ManagerRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private ManagerRepository managerRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ManagerRepository managerRepository) {
        super(authenticationManager);
        this.managerRepository = managerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("인증이나 권한이 필요한 페이지 요청");

        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        try {
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(jwtToken)
                    .getClaim("email")
                    .asString();

            if (username != null) {
                Manager manager = managerRepository.findByEmail(username).get(0);

                PrincipalDetails principalDetails = new PrincipalDetails(manager);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);
            }
        } catch (TokenExpiredException e) {
            log.info("토큰 기간이 만료되었습니다.");
        } catch (SignatureVerificationException e) {
            log.info("토큰이 누군가에 의해 변경되었습니다.");
        } catch (NullPointerException | IndexOutOfBoundsException e){
            log.info("해당 토큰의 사용자가 존재하지 않습니다.");
        }
    }
}
