package team.okky.personnel_management.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

import java.time.LocalDate;
import java.util.List;


@Controller
@ResponseBody
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * 근태 목록 메인 뷰, 이름&날짜 검색
     * @param pageNo
     * @param id
     * @param date
     * @return 검색된 근태 목록, 오늘 날짜 모든 직원에 대한 근태 상태
     */
    @GetMapping("/attendance")
    public AttendanceDTO.StatusAndIndexWithPage viewIndex(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(value = "name", required = false) Long id,
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) LocalDate date){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);
        AttendanceDTO.StatusAndIndexWithPage statusAndList = new AttendanceDTO.StatusAndIndexWithPage();

        List<AttendanceDTO.Index> attendanceList = null;

        if(id == null && date != null) {
            attendanceList = attendanceService.findAllByDate(date);
        }
        else if(id != null && date == null){
            attendanceList = attendanceService.findAllById(id);
        }
        else if(id != null && date != null){
            attendanceList = attendanceService.findAllByEmpIdAndDate(id, date);
        }else{
            attendanceList = attendanceService.findAll(pageRequestDTO);
            statusAndList.setPageResultDTO(attendanceService.findPage(pageNo));
        }

        statusAndList.setStatus(attendanceService.findAllOnlyStatus());
        statusAndList.setAttendanceList(attendanceList);

        return statusAndList;
    }

    /**
     * 출석, 결석, 지각, 휴가 등 상태에 따른 상세 뷰
     * @param status
     * @return 오늘 날짜로 해당하는 상태의 근태 목록
     */
    @GetMapping("/attendance/status/{status}")
    public List<AttendanceDTO.Index> viewStatusDetail(@PathVariable String status){

        List<AttendanceDTO.Index> listByStatuses = attendanceService.findAllByStatus(AttendanceStatus.valueOf(status.toUpperCase()));

        return listByStatuses;
    }

}
