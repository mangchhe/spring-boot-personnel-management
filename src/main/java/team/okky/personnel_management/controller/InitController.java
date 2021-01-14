package team.okky.personnel_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.okky.personnel_management.domain.*;
import team.okky.personnel_management.repository.*;
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
    private final WorkRepository workRepository;
    private final EvaluationRepository evaluationRepository;

    @GetMapping("/init")
    @Transactional
    @ResponseBody
    public void init() {
        String[] position = new String[]{"대리", "사원", "부장", "본부장", "사장", "차장", "과장"};
        String[] comment = new String[]{"BEST","SOSO","BAD"};
        List<Employee> employeeList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();
        List<Work> workList = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentList.add(department);
            departmentRepository.save(department);
        }

        for(int i=0;i<10;i++) {
            Work work =
                    Work.builder()
                            .workName("업무" + i)
                            .workChargeName("담당자" + i)
                            .workStartDate(LocalDate.of(2020, i + 1, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 4 + i))
                            .department(departmentList.get(i/2))
                            .build();
            workRepository.save(work);
            workList.add(work);
        }
        int j=-1;
        for (int i = 0; i < 50; i++) {
            Employee employee = Employee.builder()
                    .empPosition(position[i % 7])
                    .empName("테스터" + i)
                    .department(
                            departmentList.get(i / 10)
                    )
                    .work(workList.get(i/ 5))
                    .empPhoneNum("010-2472-2117")
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
                evaluationRepository.save(Evaluation.builder()
                        .evalResultScore((int) (Math.random() * 100) + 1)
                        .evalComment(comment[i % 3])
                        .employee(employeeList.get(i))
                        .work(workList.get(i%5==0?++j:j))
                        .build());

        }

        attendanceService.autoCreateAttendance();

        attendanceService.onWork(employeeList.get(0));
        attendanceService.onWork(employeeList.get(1));
        attendanceService.onWork(employeeList.get(2));
        attendanceService.offWork(employeeList.get(2));


    }
}
