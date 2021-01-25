package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.okky.personnel_management.domain.Access;
import team.okky.personnel_management.domain.Manager;
import team.okky.personnel_management.dto.ManagerDTO;
import team.okky.personnel_management.repository.AccessRepository;
import team.okky.personnel_management.repository.ManagerRepository;
import team.okky.personnel_management.service.ManagerService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccessRepository accessRepository;

    @PostMapping("/join")
    public void join(){
        Manager manager = Manager.builder()
                .mnEmail("test@okky.kr")
                .mnPw(bCryptPasswordEncoder.encode("1234"))
                .mnAuthority("ROLE_MANAGER")
                .mnCreateDate(LocalDate.now())
                .build();
        managerService.save(manager);
    }

    @GetMapping("/profile")
    public ManagerDTO.profile profile(){
        Manager manager = (Manager) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Access access = accessRepository.findCurrentAccessByEmail(manager.getMnEmail());
        ManagerDTO.profile profile = ManagerDTO.profile.builder()
                .mnEmail(manager.getMnEmail())
                .currentAccessDate(access.getAccessDate())
                .accessArea(access.getAccessArea())
                .build();
        return profile;
    }
}
