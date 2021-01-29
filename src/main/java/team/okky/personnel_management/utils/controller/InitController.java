package team.okky.personnel_management.utils.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeePosition;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.evaluation.EvaluationRepository;
import team.okky.personnel_management.attendance.AttendanceService;
import team.okky.personnel_management.evaluation.Evaluation;
import team.okky.personnel_management.manager.Manager;
import team.okky.personnel_management.manager.ManagerService;
import team.okky.personnel_management.sick.Sick;
import team.okky.personnel_management.sick.SickRepository;
import team.okky.personnel_management.transfer.Transfer;
import team.okky.personnel_management.transfer.TransferDTO;
import team.okky.personnel_management.transfer.TransferService;
import team.okky.personnel_management.vacation.Vacation;
import team.okky.personnel_management.vacation.VacationRepository;
import team.okky.personnel_management.work.Work;
import team.okky.personnel_management.work.WorkRepository;

import java.time.LocalDate;
import java.util.*;

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
    private final ManagerService managerService;
    private final TransferService transferService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/init")
    @Transactional
    @ResponseBody
    public void init() {
        String[] position = new String[]{"임원", "부장", "차장", "과장", "대리", "주임", "사원"};
        String[] comment = new String[]{"BEST","SOSO","BAD"};
        int score = 0;
        List<Manager> managerList = new ArrayList<>();
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
                            .workStartDate(LocalDate.of(2020,  12, i + 1))
                            .workEndDate(LocalDate.of(2021, 1, 10+i))
                            .department(departmentList.get(i/2))
                            .build();
            workRepository.save(work);
            workList.add(work);
        }
        int j=-1; int k=0;
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 0) {
                Manager manager = Manager.builder()
                        .mnEmail("test"+i+"@okky.kr")
                        .mnPw(bCryptPasswordEncoder.encode("1234"))
                        .mnAuthority("ROLE_MANAGER")
                        .mnCreateDate(LocalDate.now())
                        .build();
                managerService.save(manager);
                managerList.add(manager);
            }
            Employee employee = Employee.builder()
                    .empPosition(EmployeePosition.findByEmployeePosition(position[i % 7]))
                    .empName("테스터" + i)
                    .department(
                            departmentList.get(i / 10)
                    )
                    .work(workList.get(i/ 5))
                    .empPhoneNum("010-2472-2117")
                    .empJoinDate(LocalDate.of(LocalDate.now().getYear(), (int) (Math.random() * 11 + 1), (int) (Math.random() * 25 + 1)))
                    .manager(i%10==0?managerList.get(k++):null)
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

            if(comment[i%3].equals("BEST")){
                score = 90;
            }
            else if(comment[i%3].equals("SOSO")){
                score = 60;
            }
            else{ score = 30;}

            evaluationRepository.save(Evaluation.builder()
                    .evalResultScore(score)
                    .evalComment(comment[i%3])
                    .employee(employeeList.get(i))
                    .work(workList.get(i%5==0?++j:j))
                    .build());

            TransferDTO.AddForm addForm = TransferDTO.AddForm.builder()
                    .employeeId(employee.getEmpId())
                    .transferPosition(position[(i+1) % 7])
                    .transferDate(LocalDate.now().plusDays(i))
                    .departmentName(departmentList.get(i % 5).getDeptName())
                    .build();
            //when
            Transfer result = transferService.addTransfer(addForm);

        }

        attendanceService.autoCreateAttendance();

        attendanceService.onWork(employeeList.get(0));
        attendanceService.onWork(employeeList.get(1));
        attendanceService.onWork(employeeList.get(2));
        attendanceService.offWork(employeeList.get(2));


    }
}
