package team.okky.personnel_management.attendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.sick.Sick;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.sick.SickRepository;
import team.okky.personnel_management.vacation.Vacation;
import team.okky.personnel_management.vacation.VacationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;
    private final SickRepository sickRepository;

    /**
     * 모든 직원의 근태이력을 자동 생성
     * detail, 시스템 00:00 기준 생성
     * @return 오늘 사원들 근태 이력
     */
    @Override
    @Transactional(readOnly = false)
    public List<Attendance> autoCreateAttendance(){
        // 모든 사람 이력 생성, 현재 결근 체크(null)
        for(Employee e : employeeRepository.findAll()){
            attendanceRepository.save(
                    Attendance.builder()
                            .attDate(LocalDate.now())
                            .attStatus(AttendanceStatus.ABSENCE)
                            .employee(e)
                            .build()
            );
        }
        // 오늘 휴가인 사람 체크
        for(Vacation v : vacationRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(v.getEmployee(), LocalDate.now()).get(0).changeAttStatus(AttendanceStatus.VACATION);
        }
        // 오늘 병가인 사람 체크
        for(Sick s : sickRepository.findAllByDate(LocalDate.now())){
            attendanceRepository.findAllByEmployeeAndDate(s.getEmployee(), LocalDate.now()).get(0).changeAttStatus(AttendanceStatus.SICK);
        }
        return attendanceRepository.findAllByDate(LocalDate.now());
    }

    /**
     * 출근을 할 경우
     * @param employee
     * @return 출근 처리된 사원
     */
    @Override
    @Transactional(readOnly = false)
    public Attendance onWork(Employee employee){
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployeeAndDate(employee, LocalDate.now());

        // 출근 시간 전이라면
        if(LocalTime.now().isBefore(AttendanceTime.ON_TIME.getLocalTime())) {
            attendanceList.get(0).changeAttStatus(AttendanceStatus.ON);
            attendanceList.get(0).changeAttOnTime(LocalTime.now());
        // 출근 시간 이후라면
        }else{
            attendanceList.get(0).changeAttStatus(AttendanceStatus.LATE);
            attendanceList.get(0).changeAttOnTime(LocalTime.now());
        }
        return attendanceList.get(0);
    }

    /**
     * 퇴근을 할 경우
     * @param employee
     * @return 퇴근 처리된 사원
     */
    @Override
    @Transactional(readOnly = false)
    public Attendance offWork(Employee employee){
        List<Attendance> attendanceList = attendanceRepository.findAllByEmployeeAndDate(employee, LocalDate.now());
        attendanceList.get(0).changeAttStatus(AttendanceStatus.OFF);
        attendanceList.get(0).changeAttOffTime(LocalTime.now());
        return attendanceList.get(0);
    }

    /**
     * 전체 직원 근태 목록 보여주기
     * detail, 최근 날짜, 출근 시각 내림차순
     * @param pageRequestDTO
     * @return 최근 날짜, 최근 시각 정렬 된 사원 근태 이력
     */
    @Override
    public List<AttendanceDTO.Index> findAll(PageRequestDTO pageRequestDTO){
        return attendanceRepository.findAll(pageRequestDTO).stream()
                .map(Attendance::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 전체 직원 근태 목록 페이징 데이터 생성
     * @param pageNo
     * @return 전체 직원 근태 목록 페이징 정보
     */
    @Override
    public PageResultDTO findPage(int pageNo){
        return new PageResultDTO(
                attendanceRepository.findTotal(),
                pageNo);
    }

    /**
     * 전체 직원 출근 현황 보여주기
     * detail, 출근, 결근, 지각, 휴가, 병가, 퇴근
     * @return 오늘 날짜 모든 사원들의 출근 상태
     */
    @Override
    public AttendanceDTO.Status findAllOnlyStatus(){
        HashMap<AttendanceStatus, Integer> statusMap = new HashMap<>();
        for(Attendance a : attendanceRepository.findAllByNowDate()) {
            statusMap.put(a.getAttStatus(), statusMap.getOrDefault(a.getAttStatus(), 0) + 1);
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
    @Override
    public List<AttendanceDTO.Index> findAllByStatus(AttendanceStatus status){
        return attendanceRepository.findAllByStatus(status).stream()
                .map(Attendance::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 해당 날짜 검색
     * detail, 해당 날짜에 출근하지 않은 사람 상태(휴가, 결석 등) 표시해야함
     * @param date
     * @return 해당하는 날짜만 담은 근태 이력
     */
    @Override
    public List<AttendanceDTO.Index> findAllByDate(LocalDate date){
        return attendanceRepository.findAllByDate(date).stream()
                .map(Attendance::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 해당 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param id
     * @return 해당하는 이름만 담은 근태 이력
     */
    @Override
    public List<AttendanceDTO.Index> findAllById(Long id){
        return attendanceRepository.findAllById(id).stream()
                .map(Attendance::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 해당 날짜 + 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param date
     * @param id
     * @return 해당 날짜와 이름만 담은 근태 이력
     */
    @Override
    public List<AttendanceDTO.Index> findAllByEmpIdAndDate(Long id, LocalDate date){
        return attendanceRepository.findAllByEmpIdAndDate(id, date).stream()
                .map(Attendance::entityToIndex)
                .collect(Collectors.toList());
    }
}
