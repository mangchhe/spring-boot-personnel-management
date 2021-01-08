package team.okky.personnel_management.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Sick;
import team.okky.personnel_management.domain.Vacation;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;
import team.okky.personnel_management.service.AttendanceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class StartConfig implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final AttendanceService attendanceService;
    private final VacationRepository vacationRepository;
    private final SickRepository sickRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] position = new String[]{"대리", "사원", "부장", "본부장", "사장", "차장", "과장"};
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            Employee employee = Employee.builder()
                    .emp_position(position[i % 7])
                    .emp_name("테스터" + i)
                    .department(
                            Department.builder()
                                    .dept_name("부서" + i)
                                    .build()
                    )
                    .emp_phone_num("010-2472-2117")
                    .emp_jumin("123456-1234567")
                    .emp_internal_num("010-1234-5678")
                    .emp_join_date(LocalDate.of(LocalDate.now().getYear(), (int)(Math.random() * 11 + 1), (int)(Math.random() * 25 + 1)))
                    .build();
            employeeList.add(employee);
            employeeRepository.save(employee);
            employeeRepository.save(
                    Employee.builder()
                            .emp_name("테스터" + i)
                            .department(new Department())
                            .build()
            );
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

        attendanceService.onWork(employeeList.get(0));
        attendanceService.onWork(employeeList.get(1));
        attendanceService.onWork(employeeList.get(2));
        attendanceService.offWork(employeeList.get(2));

    }
}
