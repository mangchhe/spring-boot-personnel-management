package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.okky.personnel_management.domain.Manager;
import team.okky.personnel_management.service.ManagerService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

}
