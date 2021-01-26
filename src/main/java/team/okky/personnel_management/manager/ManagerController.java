package team.okky.personnel_management.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.okky.personnel_management.access.Access;
import team.okky.personnel_management.access.AccessRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccessRepository accessRepository;

    @PostMapping("/join")
    public Object join(@RequestBody Map<String,String> user, HttpServletRequest request) {
        Manager manager = managerRepository.findByEmail(user.get("email")).get(0);
        if (manager == null) {throw new IllegalArgumentException("등록되지 않은 이메일입니다.");}
        if (!bCryptPasswordEncoder.matches(user.get("password"), manager.getMnPw())) { throw new IllegalArgumentException("잘못된 비밀번호입니다."); }
        Access access = Access.builder()
                .accessArea(request.getRemoteAddr())
                .accessDate(LocalDateTime.now())
                .manager(manager)
                .build();
        accessRepository.save(access);
        log.info(">getRemoteAddr : " + request.getRemoteAddr());
        return "로그인";
    }

    @GetMapping("/profile")
    public ManagerDTO.profile profile(){
        Manager manager = (Manager) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Access access = accessRepository.findCurrentAccessByEmail(manager.getMnEmail());
        ManagerDTO.profile Profile = ManagerDTO.profile.builder()
                .mnEmail(manager.getMnEmail())
                .currentAccessDate(access.getAccessDate())
                .accessArea(access.getAccessArea())
                .build();
        return Profile;
    }
}
