package team.okky.personnel_management.attendance;

import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.time.LocalDate;
import java.util.List;


public interface AttendanceService {

    /**
     * 모든 직원의 근태이력을 자동 생성
     * detail, 시스템 00:00 기준 생성
     * @return 오늘 사원들 근태 이력
     */
    public List<Attendance> autoCreateAttendance();
    /**
     * 출근을 할 경우
     * @param employee
     * @return 출근 처리된 사원
     */
    public Attendance onWork(Employee employee);

    /**
     * 퇴근을 할 경우
     * @param employee
     * @return 퇴근 처리된 사원
     */
    public Attendance offWork(Employee employee);

    /**
     * 전체 직원 근태 목록 보여주기
     * detail, 최근 날짜, 출근 시각 내림차순
     * @param pageRequestDTO
     * @return 최근 날짜, 최근 시각 정렬 된 사원 근태 이력
     */
    public List<AttendanceDTO.Index> findAll(PageRequestDTO pageRequestDTO);

    /**
     * 전체 직원 근태 목록 페이징 데이터 생성
     * @param pageNo
     * @return 전체 직원 근태 목록 페이징 정보
     */
    public PageResultDTO findPage(int pageNo);

    /**
     * 전체 직원 출근 현황 보여주기
     * detail, 출근, 결근, 지각, 휴가, 병가, 퇴근
     * @return 오늘 날짜 모든 사원들의 출근 상태
     */
    public AttendanceDTO.Status findAllOnlyStatus();

    /**
     * 출근, 결근, 지각, 휴가, 병가, 퇴근 해당 하는 사원 보여주기
     * @param status
     * @return 각 상태에 해당하는 사원 목록
     */
    public List<AttendanceDTO.Index> findAllByStatus(AttendanceStatus status);

    /**
     * 해당 날짜 검색
     * detail, 해당 날짜에 출근하지 않은 사람 상태(휴가, 결석 등) 표시해야함
     * @param date
     * @return 해당하는 날짜만 담은 근태 이력
     */
    public List<AttendanceDTO.Index> findAllByDate(LocalDate date);

    /**
     * 해당 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param id
     * @return 해당하는 이름만 담은 근태 이력
     */
    public List<AttendanceDTO.Index> findAllById(Long id);

    /**
     * 해당 날짜 + 이름 검색
     * detail, 이름으로 검색 시 동명이인이 있을 수 있으므로 주키로 검색
     * @param date
     * @param id
     * @return 해당 날짜와 이름만 담은 근태 이력
     */
    public List<AttendanceDTO.Index> findAllByEmpIdAndDate(Long id, LocalDate date);

}
