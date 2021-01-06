package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.dto.AttendanceDTO;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class AttendanceServiceTest {


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

        for (int i = 0; i < 10; i++) {

            Employee employee = Employee.builder()
                    .emp_name("테스터" + i)
                    .emp_jumin("123456-1234567")
                    .emp_position(position[i % 5])
                    .emp_phone_num("010-2472-2117")
                    .emp_join_date(LocalDate.now())
                    .build();
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                    Vacation.builder()
                            .vac_type("연가")
                            .vac_days(3)
                            .vac_start_date(LocalDate.now().minusDays(1))
                            .vac_end_date(LocalDate.now().plusDays(1))
                            .employee(employee)
                            .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                  Sick.builder()
                          .sick_title("감기몸살")
                          .sick_content("두통, 속쓰림, 오한")
                          .sick_start_date(LocalDate.now().minusDays(1))
                          .sick_end_date(LocalDate.now().plusDays(1))
                          .employee(employee)
                          .build()
                );
            }
        }

        // when, then

        for(Attendance a : attendanceService.autoCreateAttendance()){
            System.out.println(a.getEmployee().getEmp_name() + " " + a.getAtt_status() + " " + a.getEmployee().getEmp_position());
        }

    }
    
    @Test
    public void 출근() throws Exception {
        //given
        Employee employee = Employee.builder()
                .emp_name("테스터1")
                .emp_jumin("123456-1234567")
                .emp_position("과장")
                .emp_phone_num("010-2472-2117")
                .emp_join_date(LocalDate.now())
                .build();
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

        for(Attendance a : attendanceRepository.findAll()){
            System.out.println(a.getEmployee().getEmp_name());
            System.out.println(a.getAtt_date());
            System.out.println(a.getAtt_status());
        }

        attendanceService.onWork(employee);
        for(Attendance a : attendanceRepository.findAll()){
            System.out.println(a.getEmployee().getEmp_name());
            System.out.println(a.getAtt_date());
            System.out.println(a.getAtt_status());
        }

    }

    @Test
    public void 퇴근() throws Exception {
        //given
        Employee employee = Employee.builder()
                .emp_name("테스터1")
                .emp_jumin("123456-1234567")
                .emp_position("과장")
                .emp_phone_num("010-2472-2117")
                .emp_join_date(LocalDate.now())
                .build();
        employeeRepository.save(employee);

        attendanceService.autoCreateAttendance();

        //when, then
        for(Attendance a : attendanceRepository.findAll()){
            System.out.println(a.getEmployee().getEmp_name());
            System.out.println(a.getAtt_date());
            System.out.println(a.getAtt_status());
        }

        attendanceService.offWork(employee);

        for(Attendance a : attendanceRepository.findAll()){
            System.out.println(a.getEmployee().getEmp_name());
            System.out.println(a.getAtt_date());
            System.out.println(a.getAtt_status());
        }

    }

    @Test
    public void 전체직원_근태목록() throws Exception {
        //given
        String[] position = new String[]{"사원", "대리", "과장", "차장", "부장"};
        LocalDate date = LocalDate.now();
        LocalDate beforeDate = null;
        LocalTime beforeTime = null;
        Boolean first = true;
        int idx = 0;

        for (int i = 0; i < 100; i++) {
            Employee employee = Employee.builder()
                    .emp_name("테스터" + i)
                    .emp_jumin("123456-1234567")
                    .emp_position(position[i % 5])
                    .emp_phone_num("010-2472-2117")
                    .emp_join_date(LocalDate.now())
                    .build();
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
        String[] position = new String[]{"사원", "대리", "과장", "차장", "부장"};
        List<Employee> employeelist = new ArrayList<>();
        int on = 0, off = 0, absence = 10, late = 0, vacation = 0, sick = 0;

        for (int i = 0; i < 10; i++) {

            Employee employee = Employee.builder()
                    .emp_name("테스터" + i)
                    .emp_jumin("123456-1234567")
                    .emp_position(position[i % 5])
                    .emp_phone_num("010-2472-2117")
                    .emp_join_date(LocalDate.now())
                    .build();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vac_type("연가")
                                .vac_days(3)
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
                                .sick_title("감기몸살")
                                .sick_content("두통, 속쓰림, 오한")
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
        String[] position = new String[]{"사원", "대리", "과장", "차장", "부장"};
        List<Employee> employeelist = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Employee employee = Employee.builder()
                    .emp_name("테스터" + i)
                    .emp_jumin("123456-1234567")
                    .emp_position(position[i % 5])
                    .emp_phone_num("010-2472-2117")
                    .emp_join_date(LocalDate.now())
                    .build();
            employeelist.add(employee);
            employeeRepository.save(employee);

            if(i % 4 == 0 && i != 0){
                vacationRepository.save(
                        Vacation.builder()
                                .vac_type("연가")
                                .vac_days(3)
                                .vac_start_date(LocalDate.now().minusDays(1))
                                .vac_end_date(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }

            if(i % 5 == 0 && i != 0){
                sickRepository.save(
                        Sick.builder()
                                .sick_title("감기몸살")
                                .sick_content("두통, 속쓰림, 오한")
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
        String[] position = new String[]{"사원", "대리", "과장", "차장", "부장"};
        LocalDate date = LocalDate.now();

        for (int i = 0; i < 100; i++) {
            Employee employee = Employee.builder()
                    .emp_name("테스터" + i)
                    .emp_jumin("123456-1234567")
                    .emp_position(position[i % 5])
                    .emp_phone_num("010-2472-2117")
                    .emp_join_date(LocalDate.now())
                    .build();
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
    }
}