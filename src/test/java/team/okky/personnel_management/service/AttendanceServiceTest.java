package team.okky.personnel_management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.repository.AttendanceRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;

import java.time.LocalDate;
import java.util.List;

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

        // when

        for(Attendance a : attendanceService.autoCreateAttendance()){
            System.out.println(a.getEmployee().getEmp_name() + " " + a.getAtt_status() + " " + a.getEmployee().getEmp_position());
        }
        
        //then
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

        //when
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
        
        //then
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

        //when
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
        //then
    }
    
}