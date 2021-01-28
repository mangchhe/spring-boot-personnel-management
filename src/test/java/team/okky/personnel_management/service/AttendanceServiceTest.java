package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.attendance.*;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.sick.Sick;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.sick.SickRepository;
import team.okky.personnel_management.vacation.Vacation;
import team.okky.personnel_management.vacation.VacationRepository;

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
            Employee employee = Employee.builder().build();
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                    Vacation.builder()
                            .vacStartDate(LocalDate.now().minusDays(1))
                            .vacEndDate(LocalDate.now().plusDays(1))
                            .employee(employee)
                            .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                  Sick.builder()
                          .sickStartDate(LocalDate.now().minusDays(1))
                          .sickEndDate(LocalDate.now().plusDays(1))
                          .employee(employee)
                          .build()
                );
            }
        }

        // when, then
        for(Attendance a : attendanceService.autoCreateAttendance()){
            statusMap.put(a.getAttStatus(), statusMap.getOrDefault(a.getAttStatus(), 0) + 1);
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
        Employee employee = Employee.builder().build();
        employeeRepository.save(employee);

        attendanceRepository.save(
          Attendance.builder()
                  .attDate(LocalDate.now().minusDays(1))
                  .attStatus(AttendanceStatus.ABSENCE)
                  .employee(employee)
                  .build()
        );

        attendanceService.autoCreateAttendance();

        //when, then
        attendanceService.onWork(employee);;

        if(LocalTime.now().isAfter(AttendanceTime.ON_TIME.getLocalTime())) {
            Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAttStatus()
                    , AttendanceStatus.LATE);
        }else{
            Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAttStatus()
                    , AttendanceStatus.ON);
        }

    }

    @Test
    public void 퇴근() throws Exception {
        //given
        Employee employee = Employee.builder().build();
        employeeRepository.save(employee);

        attendanceService.autoCreateAttendance();

        //when, then

        attendanceService.offWork(employee);

        Assertions.assertEquals(attendanceRepository.findAllByDate(LocalDate.now()).get(0).getAttStatus()
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
            Employee employee = Employee.builder()
                    .department(new Department())
                    .build();
            employeeRepository.save(employee);

            if(i % 10 == 0 && i != 0){
                date = date.minusDays(1);
            }
            attendanceRepository.save(
                    Attendance.builder()
                            .attDate(date)
                            .attOnTime(LocalTime.of((int)(Math.random() * 23), (int)(Math.random() * 59), (int)(Math.random() * 59)))
                            .attStatus(AttendanceStatus.ABSENCE)
                            .employee(employee)
                            .build()
            );

        }
        //when, then

        for(AttendanceDTO.ListAll a : attendanceService.viewAll(new PageRequestDTO(1))){
            if(first | idx % 10 == 0){
                beforeDate = a.getAttDate();
                beforeTime = a.getAttOnTime();
                first = false;
            }else{
                if(beforeDate.isEqual(a.getAttDate()) || beforeDate.isAfter(a.getAttDate())){
                    beforeDate = a.getAttDate();
                }else{
                    Assertions.fail("내림차순 보장 X");
                }
                if(beforeTime.equals(a.getAttOnTime()) || beforeTime.isAfter(a.getAttOnTime())){
                    beforeTime = a.getAttOnTime();
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
            Employee employee = Employee.builder().build();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vacStartDate(LocalDate.now().minusDays(1))
                                .vacEndDate(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
                vacation ++;
                absence --;
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                        Sick.builder()
                                .sickStartDate(LocalDate.now().minusDays(1))
                                .sickEndDate(LocalDate.now().plusDays(1))
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

            Employee employee = Employee.builder()
                    .empName("테스터")
                    .department(Department.builder()
                            .deptName("인사과")
                            .build())
                    .build();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vacStartDate(LocalDate.now().minusDays(1))
                                .vacEndDate(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                        Sick.builder()
                                .sickStartDate(LocalDate.now().minusDays(1))
                                .sickEndDate(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }
        }

        attendanceService.autoCreateAttendance();

        for (int i = 0; i < 3; i++) {
            attendanceService.onWork(employeelist.get(i));
        }

        attendanceService.offWork(employeelist.get(3));

        //when, then

        if(LocalTime.now().isAfter(AttendanceTime.ON_TIME.getLocalTime())){
            Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.LATE).size(), 3);
        }else{
            Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.ON).size(), 3);

        }
        Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.OFF).size(), 1);
        Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.VACATION).size(), 2);
        Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.SICK).size(), 1);
        Assertions.assertEquals(attendanceService.viewStatusDetail(AttendanceStatus.ABSENCE).size(), 3);

    }

    @Test
    public void 해당날짜_검색() throws Exception {
        //given
        LocalDate date = LocalDate.now();

        for (int i = 0; i < 100; i++) {
            Employee employee = Employee.builder()
                    .department(Department.builder()
                            .build())
                    .build();
            employeeRepository.save(employee);

            if(i % 10 == 0 && i != 0){
                date = date.minusDays(1);
            }
            attendanceRepository.save(
                    Attendance.builder()
                            .attDate(date)
                            .attOnTime(LocalTime.of((int)(Math.random() * 23), (int)(Math.random() * 59), (int)(Math.random() * 59)))
                            .attStatus(AttendanceStatus.ABSENCE)
                            .employee(employee)
                            .build()
            );

        }
        //when, then
        for(AttendanceDTO.ListAll a : attendanceService.viewByDate(LocalDate.now())){
            Assertions.assertEquals(a.getAttDate(), LocalDate.now());
        }

        Assertions.assertEquals(attendanceService.viewByDate(LocalDate.now()).size()
                , attendanceRepository.findAll().stream()
                        .filter(x -> x.getAttDate().isEqual(LocalDate.now()))
                        .count());
    }

    @Test
    public void 해당이름_검색() throws Exception {
        //given
        Employee employee = Employee.builder()
                .empName("테스터1")
                .department(new Department())
                .build();
        Employee employee2 = Employee.builder()
                .empName("테스터2")
                .department(new Department())
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        for (int i = 0; i < 3; i++) {
            Attendance attendance = Attendance.builder()
                    .employee(employee)
                    .attDate(LocalDate.now().minusDays(i))
                    .build();
            Attendance attendance2 = Attendance.builder()
                    .employee(employee2)
                    .attDate(LocalDate.now().minusDays(i))
                    .build();
            attendanceRepository.save(attendance);
            attendanceRepository.save(attendance2);
        }

        //when, then
        for (int i = 0; i < 3; i++) {
            if(!attendanceService.viewByName(employee.getEmpId()).get(i).getEmpName().equals("테스터1")){
                Assertions.fail("해당이름 검색에 문제가 있습니다.(1)");
            }
            if(!attendanceService.viewByName(employee2.getEmpId()).get(i).getEmpName().equals("테스터2")){
                Assertions.fail("해당이름 검색에 문제가 있습니다.(2)");
            }
        }
    }

    @Test
    public void 해당날짜_이름_검색() throws Exception {
        //given
        Employee employee = Employee.builder()
                .empName("테스터")
                .department(new Department())
                .build();
        Employee employee2 = Employee.builder()
                .empName("테스터2")
                .department(new Department())
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        for (int i = 0; i < 3; i++) {
            Attendance attendance = Attendance.builder()
                    .employee(employee)
                    .attDate(LocalDate.now().minusDays(i))
                    .build();
            Attendance attendance2 = Attendance.builder()
                    .employee(employee2)
                    .attDate(LocalDate.now().minusDays(i))
                    .build();
            attendanceRepository.save(attendance);
            attendanceRepository.save(attendance2);
        }
        //when, then
        for(AttendanceDTO.ListAll list : attendanceService.viewByDateAndName(LocalDate.now(), employee.getEmpId())){
            Assertions.assertEquals(list.getEmpName(), "테스터");
            Assertions.assertEquals(list.getAttDate(), LocalDate.now());
        }
    }

}