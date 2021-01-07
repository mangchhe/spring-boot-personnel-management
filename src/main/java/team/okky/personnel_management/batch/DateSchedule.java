package team.okky.personnel_management.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.service.AttendanceService;

@Component
public class DateSchedule {

    @Autowired
    private AttendanceService service;

    /**
     * 12시 정각 근태 시스템 동기화
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void task1(){
        service.autoCreateAttendance();
    }
}
