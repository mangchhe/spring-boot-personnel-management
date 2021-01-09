package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.domain.Sick;
import team.okky.personnel_management.domain.Vacation;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.SickRepository;
import team.okky.personnel_management.repository.VacationRepository;
import team.okky.personnel_management.service.AttendanceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InitController {
    private final EmployeeRepository employeeRepository;
    private final AttendanceService attendanceService;
    private final VacationRepository vacationRepository;
    private final SickRepository sickRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/init")
    @Transactional
    @ResponseBody
    public void init(){
        String[] position = new String[]{"대리", "사원", "부장", "본부장", "사장", "차장", "과장"};
        List<Employee> employeeList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();

        for(int i=1;i<6;i++){
            Department department = Department.builder()
                    .deptName("부서"+i)
                    .build();
            departmentList.add(department);
            departmentRepository.save(department);
        }
        for (int i = 0; i < 49; i++) {
            Employee employee = Employee.builder()
                    .empPosition(position[i % 7])
                    .empName("테스터" + i)
                    .department(
                            departmentList.get(i/10)
                    )
                    .empPhoneNum("010-2472-2117")
                    .empJumin("123456-1234567")
                    .empInternalNum("010-1234-5678")
                    .empJoinDate(LocalDate.of(LocalDate.now().getYear(), (int) (Math.random() * 11 + 1), (int) (Math.random() * 25 + 1)))
                    .build();
            employeeList.add(employee);
            employeeRepository.save(employee);
            if (i % 4 == 0 && i != 0) {
                vacationRepository.save(
                        Vacation.builder()
                                .vacStartDate(LocalDate.now().minusDays(1))
                                .vacEndDate(LocalDate.now().plusDays(1))
                                .employee(employee)
                                .build()
                );
            }

            if (i % 5 == 0 && i != 0) {
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

        attendanceService.onWork(employeeList.get(0));
        attendanceService.onWork(employeeList.get(1));
        attendanceService.onWork(employeeList.get(2));
        attendanceService.offWork(employeeList.get(2));

    }
}
