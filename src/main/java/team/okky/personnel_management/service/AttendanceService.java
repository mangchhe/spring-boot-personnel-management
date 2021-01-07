package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.dto.EmployeeDTO;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;
    private final SickRepository sickRepository;

    /**
     * 모든 직원의 근태이력을 자동 생성
     * detail, 시스템 00:00 기준 생성
     * @return 오늘 사원들 근태 이력
     */
    @Transactional(readOnly = false)
    public List<Attendance> autoCreateAttendance(){
        // 모든 사람 이력 생성, 현재 결근 체크(null)
        for(Employee e : employeeRepository.findAll()){
            attendanceRepository.save(
                    Attendance.builder()
                            .att_date(LocalDate.now())
                            .att_status(AttendanceStatus.ABSENCE)
                            .employee(e)
                            .build()
            );
        }
        // 오늘 휴가인 사람 체크
        for(Vacation v : vacationRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(v.getEmployee(), LocalDate.now()).get(0).setAtt_status(AttendanceStatus.VACATION);
        }
        // 오늘 병가인 사람 체크
        for(Sick s : sickRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(s.getEmployee(), LocalDate.now()).get(0).setAtt_status(AttendanceStatus.SICK);
        }
        return attendanceRepository.findAllByDate(LocalDate.now());
    }

    /**
     * 출근을 할 경우
     * @param employee
     * @return 출근 처리된 사원
     */
    @Transactional(readOnly = false)
    public Attendance onWork(Employee employee){
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployeeAndDate(employee, LocalDate.now());

        // 출근 시간 전이라면
        if(LocalTime.now().isBefore(AttendanceTime.ON_TIME.getLocalTime())) {
            attendanceList.get(0).setAtt_status(AttendanceStatus.ON);
        // 출근 시간 이후라면
        }else{
            attendanceList.get(0).setAtt_status(AttendanceStatus.LATE);
        }
        return attendanceList.get(0);
    }

    /**
     * 퇴근을 할 경우
     * @param employee
     * @return 퇴근 처리된 사원
     */
    @Transactional(readOnly = false)
    public Attendance offWork(Employee employee){
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployeeAndDate(employee, LocalDate.now());
        attendanceList.get(0).setAtt_status(AttendanceStatus.OFF);
        return attendanceList.get(0);
    }

    /**
     * 전체 직원 근태 목록 보여주기
     * detail, 최근 날짜, 출근 시각 내림차순
     * @return 최근 날짜, 최근 시각 정렬 된 사원 근태 이력
     */
    public List<AttendanceDTO.ListAll> viewAll(){
        List<AttendanceDTO.ListAll> list = new ArrayList<>();
        for (Attendance a : attendanceRepository.findAllOrderByDateAndTime()){
            list.add(
                    AttendanceDTO.ListAll.builder()
                            .attDate(a.getAtt_date())
                            .dayOfWeek(a.getAtt_date().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
                            .empName(a.getEmployee().getEmp_name())
                            .deptName(a.getEmployee().getDepartment().getDept_name())
                            .empPosition(a.getEmployee().getEmp_position())
                            .attOnTime(a.getAtt_on_time())
                            .attOffTime(a.getAtt_off_time())
                            .attStatus(a.getAtt_status())
                            .build()
            );
        }
        return list;
    }

    /**
     * 전체 직원 출근 현황 보여주기
     * detail, 출근, 결근, 지각, 휴가, 병가, 퇴근
     * @return 모든 사원들의 출근 상태
     */
    public AttendanceDTO.Status viewStatus(){

        HashMap<AttendanceStatus, Integer> statusMap = new HashMap<>();
        for(Attendance a : attendanceRepository.findAllByDate(LocalDate.now())) {
            statusMap.put(a.getAtt_status(), statusMap.getOrDefault(a.getAtt_status(), 0) + 1);
        }

        return AttendanceDTO.Status.builder()
                .onCnt(statusMap.get(AttendanceStatus.ON) != null ? statusMap.get(AttendanceStatus.ON) : 0)
                .offCnt(statusMap.get(AttendanceStatus.OFF) != null ? statusMap.get(AttendanceStatus.OFF) : 0)
                .absenceCnt(statusMap.get(AttendanceStatus.ABSENCE) != null ? statusMap.get(AttendanceStatus.ABSENCE) : 0)
                .lateCnt(statusMap.get(AttendanceStatus.LATE) != null ? statusMap.get(AttendanceStatus.LATE) : 0)
                .vacationCnt(statusMap.get(AttendanceStatus.VACATION) != null ? statusMap.get(AttendanceStatus.VACATION) : 0)
                .sickCnt(statusMap.get(AttendanceStatus.SICK) != null ? statusMap.get(AttendanceStatus.SICK) : 0)
                .build();
    }

    /**
     * 출근, 결근, 지각, 휴가, 병가, 퇴근 해당 하는 사원 보여주기
     * @param status
     * @return 각 상태에 해당하는 사원 목록
     */
    public List<EmployeeDTO.ListByStatus> viewStatusDetail(AttendanceStatus status){
        List<EmployeeDTO.ListByStatus> list = new ArrayList<>();
        for(Employee e : attendanceRepository.findAllByStatus(status)){
            list.add(
                    EmployeeDTO.ListByStatus.builder()
                            .emp_position(e.getEmp_position())
                            .emp_name(e.getEmp_name())
                            .emp_internal_num(e.getEmp_internal_num())
                            .emp_join_date(e.getEmp_join_date())
                            .dept_name(e.getDepartment().getDept_name())
                            .build()
            );
        }
        return list;
    }

    /**
     * 해당 날짜 검색
     * detail, 해당 날짜에 출근하지 않은 사람 상태(휴가, 결석 등) 표시해야함
     * @param date
     * @return 해당하는 날짜만 담은 근태 이력
     */
    public List<AttendanceDTO.ListAll> viewByDate(LocalDate date){
        List<AttendanceDTO.ListAll> list = new ArrayList<>();

        for (Attendance a : attendanceRepository.findAllByDate(date)){
            list.add(
                    AttendanceDTO.ListAll.builder()
                            .attDate(a.getAtt_date())
                            .dayOfWeek(a.getAtt_date().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
                            .empName(a.getEmployee().getEmp_name())
                            .deptName(a.getEmployee().getDepartment().getDept_name())
                            .empPosition(a.getEmployee().getEmp_position())
                            .attOnTime(a.getAtt_on_time())
                            .attOffTime(a.getAtt_off_time())
                            .attStatus(a.getAtt_status())
                            .build()
            );
        }
        return list;
    }

    /**
     * 해당 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param id
     * @return 해당하는 이름만 담은 근태 이력
     */
    public List<AttendanceDTO.ListAll> viewByName(Long id){
        List<AttendanceDTO.ListAll> list = new ArrayList<>();

        for (Attendance a : attendanceRepository.findAllById(id)){
            list.add(
                    AttendanceDTO.ListAll.builder()
                            .attDate(a.getAtt_date())
                            .dayOfWeek(a.getAtt_date().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
                            .empName(a.getEmployee().getEmp_name())
                            .deptName(a.getEmployee().getDepartment().getDept_name())
                            .empPosition(a.getEmployee().getEmp_position())
                            .attOnTime(a.getAtt_on_time())
                            .attOffTime(a.getAtt_off_time())
                            .attStatus(a.getAtt_status())
                            .build()
            );
        }
        return list;
    }

    /**
     * 해당 날짜 + 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param date
     * @param id
     * @return 해당 날짜와 이름만 담은 근태 이력
     */
    public List<AttendanceDTO.ListAll> viewByDateAndName(LocalDate date, Long id){
        List<AttendanceDTO.ListAll> list = new ArrayList<>();

        for (Attendance a : attendanceRepository.findAllByDateAndId(date, id)){
            list.add(
                    AttendanceDTO.ListAll.builder()
                            .attDate(a.getAtt_date())
                            .dayOfWeek(a.getAtt_date().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN))
                            .empName(a.getEmployee().getEmp_name())
                            .deptName(a.getEmployee().getDepartment().getDept_name())
                            .empPosition(a.getEmployee().getEmp_position())
                            .attOnTime(a.getAtt_on_time())
                            .attOffTime(a.getAtt_off_time())
                            .attStatus(a.getAtt_status())
                            .build()
            );
        }
        return list;
    }

}
