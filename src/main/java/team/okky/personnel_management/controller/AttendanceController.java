package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.domain.AttendanceStatus;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.dto.PageResultDTO;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.service.AttendanceService;
import team.okky.personnel_management.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;


@Controller
@ResponseBody
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/attendance")
    public AttendanceDTO.StatusAndList viewIndex(@RequestParam(value = "page", defaultValue = "1") Integer pageNo){
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);
        AttendanceDTO.StatusAndList statusAndList = new AttendanceDTO.StatusAndList();
        statusAndList.setStatus(attendanceService.viewStatus());
        statusAndList.setAttendanceList(attendanceService.viewAll(pageRequestDTO));
        statusAndList.setPageResultDTO(attendanceService.viewAllForPage(pageNo));
        return statusAndList;
    }

    @GetMapping("/attendance/status/{status}")
    public List<AttendanceDTO.ListAll> viewStatusDetail(@PathVariable String status){

        List<AttendanceDTO.ListAll> listByStatuses = attendanceService.viewStatusDetail(AttendanceStatus.valueOf(status.toUpperCase()));

        return listByStatuses;
    }

    @GetMapping("/attendance/search")
    public AttendanceDTO.StatusAndList viewByDateOrDate(@RequestParam(required = false) Long name,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) LocalDate date){

        List<AttendanceDTO.ListAll> attendanceList = null;

        if(name == null && date != null) {
            attendanceList = attendanceService.viewByDate(date);
        }
        else if(name != null && date == null){
            attendanceList = attendanceService.viewByName(name);
        }
        else if(name != null && date != null){
            attendanceList = attendanceService.viewByDateAndName(date, name);
        }

        AttendanceDTO.StatusAndList statusAndList = new AttendanceDTO.StatusAndList();
        statusAndList.setStatus(attendanceService.viewStatus());
        statusAndList.setAttendanceList(attendanceList);
        return statusAndList;
    }

}
