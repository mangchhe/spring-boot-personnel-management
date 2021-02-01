package team.okky.personnel_management.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.okky.personnel_management.access.Access;
import team.okky.personnel_management.access.AccessRepository;
import team.okky.personnel_management.access.GeoLocationDTO;
import team.okky.personnel_management.access.GeoService;
import team.okky.personnel_management.config.auth.PrincipalDetails;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final AccessRepository accessRepository;
    private final ManagerService managerService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeoService geoService;

    @GetMapping("/profile")
    public ManagerDTO.Profile profile(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String mnEmail = principalDetails.getUsername();
        List<Access> accessList = accessRepository.findCurrentAccessByEmail(mnEmail);
        ManagerDTO.Profile Profile = ManagerDTO.Profile.builder()
                .currentAccessDate(accessList.get(0).getAccessDate())
                .accessArea(accessList.get(0).getAccessArea())
                .mnEmail(mnEmail)
                .build();
        return Profile;
    }

    @GetMapping("/profile/accessRecord")
    public List<ManagerDTO.AccessRecord> accessRecord(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String mnEmail = principalDetails.getUsername();
        List<Access> accessList = accessRepository.findCurrentAccessByEmail(mnEmail);
        return accessList.stream().map(Access::allAccessRecord).collect(Collectors.toList());

//        List<ManagerDTO.AccessRecordWithGeo> accessRecordWithGeoList;
//        ManagerDTO.AccessRecordWithGeo.builder()
//                .accessRecord()
//                .geoLocation(geoService.findCity())
//                .build();
//
//        return
    }

    @PutMapping("/profile")
    public String updatePw(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestBody ManagerDTO.UpdatePw updatePw) {
        Manager manager = managerService.findByEmail(principalDetails.getUsername()).get(0);
        if (!bCryptPasswordEncoder.matches(updatePw.getCurPw(), manager.getMnPw())) {
            log.info("현재 비밀번호가 일치하지 않습니다.");
            return "현재 비밀번호가 일치하지 않습니다.";
        }
        if (!updatePw.getNewPw().equals(updatePw.getNewPwCheck())) {
            log.info("새로운 비밀번호가 일치하지 않습니다.");
            return "새로운 비밀번호가 일치하지 않습니다.";
        }
        if (bCryptPasswordEncoder.matches(updatePw.getCurPw(), manager.getMnPw()) && updatePw.getNewPw().equals(updatePw.getNewPwCheck())) {
            managerService.updatePw(manager, bCryptPasswordEncoder.encode(updatePw.getNewPw()));
            return "비밀번호가 변경되었습니다.";
        }
        return "비밀번호가 변경되지 않았습니다. 다시 시도하세요.";
    }
}
