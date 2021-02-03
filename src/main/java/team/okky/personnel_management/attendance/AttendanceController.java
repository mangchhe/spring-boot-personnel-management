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

    @GetMapping("/attendance/status/{status}")
    public List<AttendanceDTO.Index> viewStatusDetail(@PathVariable String status){

        List<AttendanceDTO.Index> listByStatuses = attendanceService.findAllByStatus(AttendanceStatus.valueOf(status.toUpperCase()));

        return listByStatuses;
    }

}
