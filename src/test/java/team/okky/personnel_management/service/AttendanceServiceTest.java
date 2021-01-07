package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest
@Transactional
class AttendanceServiceTest {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private SickRepository sickRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Test
    public void 근태관리이력생성() throws Exception {
        // given
        String[] position = new String[]{"사원", "대리", "과장", "차장", "부장"};
        Map<AttendanceStatus, Integer> statusMap = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                    Vacation.builder()
                            .vac_start_date(LocalDate.now().minusDays(1))
                            .vac_end_date(LocalDate.now().plusDays(1))
                            .employee(employee)
                            .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                  Sick.builder()
                          .sick_start_date(LocalDate.now().minusDays(1))
                          .sick_end_date(LocalDate.now().plusDays(1))
                          .employee(employee)
                          .build()
                );
            }
        }

        // when, then
        for(Attendance a : attendanceService.autoCreateAttendance()){
            statusMap.put(a.getAtt_status(), statusMap.getOrDefault(a.getAtt_status(), 0) + 1);
        }

        AttendanceDTO.Status status = AttendanceDTO.Status.builder()
                .onCnt(statusMap.get(AttendanceStatus.ON) != null ? statusMap.get(AttendanceStatus.ON) : 0)
                .offCnt(statusMap.get(AttendanceStatus.OFF) != null ? statusMap.get(AttendanceStatus.OFF) : 0)
                .absenceCnt(statusMap.get(AttendanceStatus.ABSENCE) != null ? statusMap.get(AttendanceStatus.ABSENCE) : 0)
                .lateCnt(statusMap.get(AttendanceStatus.LATE) != null ? statusMap.get(AttendanceStatus.LATE) : 0)
                .vacationCnt(statusMap.get(AttendanceStatus.VACATION) != null ? statusMap.get(AttendanceStatus.VACATION) : 0)
                .sickCnt(statusMap.get(AttendanceStatus.SICK) != null ? statusMap.get(AttendanceStatus.SICK) : 0)
                .build();

        Assertions.assertEquals(status, attendanceService.viewStatus());

    }
    
    @Test
    public void 출근() throws Exception {
        //given
        Employee employee = new Employee();
        employeeRepository.save(employee);

        attendanceRepository.save(
          Attendance.builder()
                  .att_date(LocalDate.now().minusDays(1))
                  .att_status(AttendanceStatus.ABSENCE)
                  .employee(employee)
                  .build()
        );

        attendanceService.autoCreateAttendance();

        //when, then
        attendanceService.onWork(employee);;

        if(LocalTime.now().isAfter(AttendanceTime.ON_TIME.getLocalTime())) {
            Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAtt_status()
                    , AttendanceStatus.LATE);
        }else{
            Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAtt_status()
                    , AttendanceStatus.ON);
        }

    }

    @Test
    public void 퇴근() throws Exception {
        //given
        Employee employee = new Employee();
        employeeRepository.save(employee);

        attendanceService.autoCreateAttendance();

        //when, then

        attendanceService.offWork(employee);

        Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAtt_status()
                , AttendanceStatus.OFF);

    }

    @Test
    public void 전체직원_근태목록() throws Exception {
        //given
        LocalDate date = LocalDate.now();
        LocalDate beforeDate = null;
        LocalTime beforeTime = null;
        boolean first = true;
        int idx = 0;

        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee();
            employeeRepository.save(employee);

            if(i % 10 == 0 && i != 0){
                date = date.minusDays(1);
            }
            attendanceRepository.save(
                    Attendance.builder()
                            .att_date(date)
                            .att_on_time(LocalTime.of((int)(Math.random() * 23), (int)(Math.random() * 59), (int)(Math.random() * 59)))
                            .att_status(AttendanceStatus.ABSENCE)
                            .employee(employee)
                            .build()
            );

        }
        //when, then

        for(Attendance a : attendanceService.viewAll()){
            if(first | idx % 10 == 0){
                beforeDate = a.getAtt_date();
                beforeTime = a.getAtt_on_time();
                first = false;
            }else{
                if(beforeDate.isEqual(a.getAtt_date()) || beforeDate.isAfter(a.getAtt_date())){
                    beforeDate = a.getAtt_date();
                }else{
                    Assertions.fail("내림차순 보장 X");
                }
                if(beforeTime.equals(a.getAtt_on_time()) || beforeTime.isAfter(a.getAtt_on_time())){
                    beforeTime = a.getAtt_on_time();
                }else{
                    Assertions.fail("내림차순 보장 X");
                }
            }
            idx++;
        }
    }

    @Test
    public void 전체직원_출근현황() throws Exception {
        //given
        List<Employee> employeelist = new ArrayList<>();
        int on = 0, off = 0, absence = 10, late = 0, vacation = 0, sick = 0;

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vac_start_date(LocalDate.now().minusDays(1))
                                .vac_end_date(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
                vacation ++;
                absence --;
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                        Sick.builder()
                                .sick_start_date(LocalDate.now().minusDays(1))
                                .sick_end_date(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
                sick ++;
                absence --;
            }
        }

        attendanceService.autoCreateAttendance();

        for (int i = 0; i < 2; i++) {
            attendanceService.onWork(employeelist.get(i));
            absence --;
            if(LocalTime.now().isAfter(AttendanceTime.ON_TIME.getLocalTime())){
                late ++;
            }
            else{
                on ++;
            }
        }

        attendanceService.offWork(employeelist.get(2));
        absence --;
        off ++;

        //when, then
        AttendanceDTO.Status status = attendanceService.viewStatus();

        if(status.getAbsenceCnt() != absence){
            Assertions.fail("결근 상태가 맞지 않다");
        }
        if(status.getOnCnt() != on){
            Assertions.fail("출근 상태가 맞지 않다");
        }
        if(status.getOffCnt() != off){
            Assertions.fail("퇴근 상태가 맞지 않다");
        }
        if(status.getLateCnt() != late){
            Assertions.fail("지각 상태가 맞지 않다");
        }
        if(status.getSickCnt() != sick){
            Assertions.fail("병가 상태가 맞지 않다");
        }
        if(status.getVacationCnt() != vacation){
            Assertions.fail("휴가 상태가 맞지 않다");
        }
    }
    
    @Test
    public void 근태상태_상세표시() throws Exception {
        //given
        List<Employee> employeelist = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Employee employee = new Employee();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vac_start_date(LocalDate.now().minusDays(1))
                                .vac_end_date(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                        Sick.builder()
                                .sick_start_date(LocalDate.now().minusDays(1))
                                .sick_end_date(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }
        }

        attendanceService.autoCreateAttendance();

        for (int i = 0; i < 2; i++) {
            attendanceService.onWork(employeelist.get(i));
        }

        attendanceService.offWork(employeelist.get(2));
        
        //when, then
        if (!attendanceService.viewStatusDetail(AttendanceStatus.VACATION)
                .equals(new ArrayList<>(Arrays.asList(employeelist.get(4), employeelist.get(8))))) {
                    Assertions.fail("방학 상세 정보가 아닙니다.");
                }

        if (!attendanceService.viewStatusDetail(AttendanceStatus.SICK)
                .equals(new ArrayList<>(Arrays.asList(employeelist.get(5))))) {
                    Assertions.fail("병가 상세 정보가 아닙니다.");
                }

        if(LocalTime.now().isAfter(AttendanceTime.ON_TIME.getLocalTime())) {
            if (!attendanceService.viewStatusDetail(AttendanceStatus.LATE)
                    .equals(new ArrayList<>(Arrays.asList(employeelist.get(0), employeelist.get(1))))) {
                        Assertions.fail("지각 상세 정보가 아닙니다.");
                    }
        }else{
            if (!attendanceService.viewStatusDetail(AttendanceStatus.ON)
                    .equals(new ArrayList<>(Arrays.asList(employeelist.get(0), employeelist.get(1))))) {
                        Assertions.fail("출근 상세 정보가 아닙니다.");
                    }
        }

        if (!attendanceService.viewStatusDetail(AttendanceStatus.OFF)
                .equals(new ArrayList<>(Arrays.asList(employeelist.get(2))))) {
                    Assertions.fail("퇴근 상세 정보가 아닙니다.");
                }

        if (!attendanceService.viewStatusDetail(AttendanceStatus.ABSENCE)
                .equals(new ArrayList<>(Arrays.asList(employeelist.get(3), employeelist.get(6), employeelist.get(7), employeelist.get(9))))) {
                    Assertions.fail("결근 상세 정보가 아닙니다.");
                }
    }

    @Test
    public void 해당날짜_검색() throws Exception {
        //given
        LocalDate date = LocalDate.now();

        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee();
            employeeRepository.save(employee);

            if(i % 10 == 0 && i != 0){
                date = date.minusDays(1);
            }
            attendanceRepository.save(
                    Attendance.builder()
                            .att_date(date)
                            .att_on_time(LocalTime.of((int)(Math.random() * 23), (int)(Math.random() * 59), (int)(Math.random() * 59)))
                            .att_status(AttendanceStatus.ABSENCE)
                            .employee(employee)
                            .build()
            );

        }
        //when, then
        for(Attendance a : attendanceService.viewByDate(LocalDate.now())){
            Assertions.assertEquals(a.getAtt_date(), LocalDate.now());
        }

        Assertions.assertEquals(attendanceService.viewByDate(LocalDate.now()).size()
                , attendanceRepository.findAll().stream()
                        .filter(x -> x.getAtt_date().isEqual(LocalDate.now()))
                        .count());
    }

    @Test
    public void 해당이름_검색() throws Exception {
        //given
        List<Attendance> attendanceList = new ArrayList<>();
        Employee employee = Employee.builder()
                .emp_name("테스터")
                .build();
        Employee employee2 = Employee.builder()
                .emp_name("테스터")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        for (int i = 0; i < 3; i++) {
            Attendance attendance = Attendance.builder()
                    .employee(employee)
                    .att_date(LocalDate.now().minusDays(i))
                    .build();
            attendanceRepository.save(attendance);
            attendanceList.add(attendance);
        }

        //when, then
        if(!attendanceService.viewByName(employee.getEmp_id()).equals(attendanceList)){
            Assertions.fail("해당이름 검색에 문제가 있습니다.(1)");
        }
        if(attendanceService.viewByName(employee2.getEmp_id()).equals(attendanceList)){
            Assertions.fail("해당이름 검색에 문제가 있습니다.(2)");
        }
    }

    @Test
    public void 해당날짜_이름_검색() throws Exception {
        //given
        List<Attendance> attendanceList = new ArrayList<>();
        Employee employee = Employee.builder()
                .emp_name("테스터")
                .build();
        employeeRepository.save(employee);

        for (int i = 0; i < 3; i++) {
            Attendance attendance = Attendance.builder()
                    .employee(employee)
                    .att_date(LocalDate.now().minusDays(i))
                    .build();
            attendanceRepository.save(attendance);
            attendanceList.add(attendance);
        }
        //when, then
        if(!attendanceService.viewByDateOrName(LocalDate.now(), employee.getEmp_id()).get(0).equals(attendanceList.get(0))){
            Assertions.fail("날짜와 이름으로 검색에 문제가 있습니다.");
        }
    }

}