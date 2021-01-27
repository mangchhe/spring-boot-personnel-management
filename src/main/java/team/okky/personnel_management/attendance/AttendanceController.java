package team.okky.personnel_management.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import team.okky.personnel_management.access.Access;
import team.okky.personnel_management.access.AccessRepository;
import team.okky.personnel_management.access.AccessService;
import team.okky.personnel_management.config.auth.PrincipalDetails;
import team.okky.personnel_management.manager.ManagerRepository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@ResponseBody
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final ManagerRepository managerRepository;
    private final AccessService accessService;

    @GetMapping("/attendance")
    public AttendanceDTO.StatusAndList viewIndex(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(required = false) Long name,
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) LocalDate date,
                                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if(ip==null){ip =req.getRemoteAddr();}
        Access access = Access.builder()
                .accessDate(LocalDateTime.now())
                .accessArea(ip)
                .manager(managerRepository.findByEmail(principalDetails.getUsername()).get(0))
                .build();
        accessService.save(access);

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);
        AttendanceDTO.StatusAndList statusAndList = new AttendanceDTO.StatusAndList();

        List<AttendanceDTO.ListAll> attendanceList = null;

        if(name == null && date != null) {
            attendanceList = attendanceService.viewByDate(date);
        }
        else if(name != null && date == null){
            attendanceList = attendanceService.viewByName(name);
        }
        else if(name != null && date != null){
            attendanceList = attendanceService.viewByDateAndName(date, name);
        }else{
            attendanceList = attendanceService.viewAll(pageRequestDTO);
            statusAndList.setPageResultDTO(attendanceService.viewAllForPage(pageNo));
        }

        statusAndList.setStatus(attendanceService.viewStatus());
        statusAndList.setAttendanceList(attendanceList);

        return statusAndList;
    }

    @GetMapping("/attendance/status/{status}")
    public List<AttendanceDTO.ListAll> viewStatusDetail(@PathVariable String status){

        List<AttendanceDTO.ListAll> listByStatuses = attendanceService.viewStatusDetail(AttendanceStatus.valueOf(status.toUpperCase()));

        return listByStatuses;
    }

}
