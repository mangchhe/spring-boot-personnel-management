package team.okky.personnel_management.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.okky.personnel_management.attendance.Attendance;
import team.okky.personnel_management.attendance.AttendanceRepository;
import team.okky.personnel_management.attendance.AttendanceService;
import team.okky.personnel_management.attendance.AttendanceStatus;
import team.okky.personnel_management.transfer.TransferService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DateSchedule {

    private final AttendanceService attendanceService;
    private final TransferService transferService;

    /* 더미 데이터 추가 */
    private final AttendanceRepository attendanceRepository;
    private List<Attendance> attendances = new ArrayList<>();
    private List<Integer> onEmployees = new ArrayList<>();

    /**
     * 12시 정각 근태 시스템 동기화
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void task1(){
        attendanceService.autoCreateAttendance();
        transferService.autoAppointTransfer();
        attendances = attendanceRepository.findAllByDate(LocalDate.now());
    }

    /**
     * 9시 50분 출근
     */
    @Scheduled(cron = "0 50 09 * * *")
    public void task2(){
        attendances = attendanceRepository.findAllByDate(LocalDate.now());
        onWork();
    }

    /**
     * 9시 55분 출근
     */
    @Scheduled(cron = "0 55 09 * * *")
    public void task22(){
        onWork();
    }

    /**
     * 9시 59분 출근
     */
    @Scheduled(cron = "0 59 09 * * *")
    public void task222(){
        onWork();
    }

    /**
     * 10시 30분 출근
     */
    @Scheduled(cron = "0 30 10 * * *")
    public void task2222(){
        onWork();
    }

    /**
     * 5시 퇴근
     */
    @Scheduled(cron = "0 0 17 * * *")
    public void task3(){
        for(Integer i : onEmployees){
            attendanceService.offWork(attendances.get(i).getEmployee());
        }
    }

    public void onWork() {
        int size = (int) (Math.random() * 7 + 5);
        for (int i = 0; i < size; i++) {
            int j = (int) (Math.random() * 50);
            if(attendances.get(j).getAttStatus() == AttendanceStatus.SICK && attendances.get(j).getAttStatus() == AttendanceStatus.VACATION){
                onEmployees.add(j);
                continue;
            }
            if(onEmployees.size() == attendances.size()){
                break;
            }
            while(onEmployees.contains(j)){
                j = (int) (Math.random() * 50);
            }
            onEmployees.add(j);
            attendanceService.onWork(attendances.get(j).getEmployee());
        }
    }
}
