package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Attendance;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Sick;
import team.okky.personnel_management.domain.Vacation;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.HashMap;

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
                            .att_status("결근")
                            .employee(e)
                            .build()
            );
        }
        // 오늘 휴가인 사람 체크
        for(Vacation v : vacationRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(v.getEmployee(), LocalDate.now()).get(0).setAtt_status("휴가");
        }
        // 오늘 병가인 사람 체크
        for(Sick s : sickRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(s.getEmployee(), LocalDate.now()).get(0).setAtt_status("병가");
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
        // 10시에 데이터 처리가 된 후라면
        if(!attendanceList.isEmpty()){
            // 출근 시간 전이라면
            if(LocalTime.now().isBefore(LocalTime.of(10, 0, 0))) {
                attendanceList.get(0).setAtt_status("출근");
            // 출근 시간 이후라면
            }else{
                attendanceList.get(0).setAtt_status("지각");
            }
            return attendanceList.get(0);
        }
        // 10시에 데이터 처리가 되기 전이라면
        else{
            Attendance attendance = Attendance.builder()
                    .att_date(LocalDate.now())
                    .att_on_time(LocalTime.now())
                    .att_status("출근")
                    .build();
            attendanceRepository.save(attendance);
            return attendance;
        }
    }

    /**
     * 퇴근을 할 경우
     * @param employee
     * @return 퇴근 처리된 사원
     */
    @Transactional(readOnly = false)
    public Attendance offWork(Employee employee){
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployeeAndDate(employee, LocalDate.now());
        attendanceList.get(0).setAtt_status("퇴근");
        return attendanceList.get(0);
    }

    /**
     * 전체 직원 근태 목록 보여주기
     * detail, 최근 날짜, 출근 시각 내림차순
     * @return 최근 날짜, 최근 시각 정렬 된 사원 근태 이력
     */
    public List<Attendance> viewAll(){
        return attendanceRepository.findAllOrderByDateAndTime();
    }

    /**
     * 전체 직원 출근 현황 보여주기
     * detail, 출근, 결근, 지각, 휴가, 병가, 퇴근
     * @return 모든 사원들의 출근 상태
     */
    public AttendanceDTO.Status viewStatus(){

        HashMap<String, Integer> statusMap = new HashMap<>();
        for(Attendance a : attendanceRepository.findAllByDate(LocalDate.now())) {
            statusMap.put(a.getAtt_status(), statusMap.getOrDefault(a.getAtt_status(), 0) + 1);
        }

        return AttendanceDTO.Status.builder()
                .onCnt(statusMap.get("출근"))
                .offCnt(statusMap.get("퇴근"))
                .absenceCnt(statusMap.get("결근"))
                .lateCnt(statusMap.get("지각"))
                .vacationCnt(statusMap.get("방학"))
                .sickCnt(statusMap.get("병가"))
                .build();
    }

    /**
     * 출근, 결근, 지각, 휴가, 병가, 퇴근 해당 하는 사원 보여주기
     * @param status
     * @return 각 상태에 해당하는 사원 목록
     */
    public List<Employee> viewStatusDetail(String status){
        return attendanceRepository.findAllByStatus(status);
    }

    /**
     * 해당 날짜 검색
     * detail, 해당 날짜에 출근하지 않은 사람 상태(휴가, 결석 등) 표시해야함
     * @param date
     * @return 해당하는 날짜만 담은 근태 이력
     */
    public List<Attendance> viewByDate(LocalDate date){
        return attendanceRepository.findAllByDate(date);
    }

    /**
     * 이름 검색(동명이인)
     * @param name
     * @return 해당하는 이름만 담은 사원 목록
     */
    public List<Attendance> viewByName(String name){
        return attendanceRepository.findAllByName(name);
    }

    /**
     * 해당 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param id
     * @return 해당하는 이름만 담은 근태 이력
     */
    public List<Attendance> viewByName(Long id){
        return attendanceRepository.findAllById(id);
    }

    /**
     * 해당 날짜 + 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param date
     * @param id
     * @return 해당 날짜와 이름만 담은 근태 이력
     */
    public List<Attendance> viewByDateOrName(LocalDate date, Long id){
        return attendanceRepository.findAllByDateAndId(date, id);
    }

}
