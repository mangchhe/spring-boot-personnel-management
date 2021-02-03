package team.okky.personnel_management.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.access.Access;
import team.okky.personnel_management.access.AccessService;
import team.okky.personnel_management.manager.Manager;
import team.okky.personnel_management.manager.ManagerDTO;
import team.okky.personnel_management.manager.ManagerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class ManagerServiceTest {
    @Autowired ManagerService managerService;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired AccessService accessService;

    @BeforeEach
    public void 접속한관리자() throws Exception{
        Manager manager = Manager.builder()
                .mnEmail("test@okky.kr")
                .mnPw(bCryptPasswordEncoder.encode("1234"))
                .build();
        managerService.save(manager);
        Access access = Access.builder()
                .accessDate(LocalDateTime.now())
                .manager(manager)
                .build();
        accessService.save(access);
    }
    @Test
    public void 비밀번호변경() throws Exception{
        //given
        Manager manager = managerService.findByEmail("test@okky.kr").get(0);
        ManagerDTO.UpdatePw updatePw = ManagerDTO.UpdatePw.builder()
                .curPw("1234")
                .newPw("0000")
                .newPwCheck("0000")
                .build();
        //when, then
        if(!bCryptPasswordEncoder.matches(updatePw.getCurPw(), manager.getMnPw())){
            Assertions.fail("현재 비밀번호가 일치하지 않습니다.");
        }
        if (!updatePw.getNewPw().equals(updatePw.getNewPwCheck())) {
            Assertions.fail("새로운 비밀번호가 일치하지 않습니다.");
        }
        if (bCryptPasswordEncoder.matches(updatePw.getCurPw(), manager.getMnPw()) && updatePw.getNewPw().equals(updatePw.getNewPwCheck())) {
            managerService.updatePw(manager, bCryptPasswordEncoder.encode(updatePw.getNewPw()));
            Assertions.assertThat(bCryptPasswordEncoder.matches("0000", manager.getMnPw())).isEqualTo(true);
        }
    }

    @Test
    public void 전체_접속시간_내역() throws Exception{
        //given
        List<Access> accessList = accessService.findAll();
        List<LocalDateTime> accessTimeList = accessList.stream().map(Access::getAccessDate).collect(Collectors.toList());
        //when,then
        if(accessTimeList.isEmpty()){
            Assertions.fail("접속시간이 조회되지 않았습니다.");
        }
    }
}
