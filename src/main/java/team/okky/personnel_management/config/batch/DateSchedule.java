package team.okky.personnel_management.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.okky.personnel_management.attendance.AttendanceService;
import team.okky.personnel_management.transfer.TransferService;

@Component
@RequiredArgsConstructor
public class DateSchedule {

    private final AttendanceService attendanceService;
    private final TransferService transferService;

    /**
     * 12시 정각 근태 시스템 동기화
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void task1(){
        attendanceService.autoCreateAttendance();
        transferService.autoAppointTransfer();
    }
}
