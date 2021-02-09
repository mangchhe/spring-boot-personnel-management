package team.okky.personnel_management.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.Department;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeePosition;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.transfer.Transfer;
import team.okky.personnel_management.transfer.TransferDTO;
import team.okky.personnel_management.transfer.TransferRepository;
import team.okky.personnel_management.transfer.TransferService;
import team.okky.personnel_management.utils.dto.PageRequestDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class TransferServiceTest {

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Test
    public void 자동인사이동() throws Exception {
        //given
        Department department = Department.builder().build();
        Department department2 = Department.builder().build();
        departmentRepository.save(department);
        departmentRepository.save(department2);
        Employee employee = Employee.builder()
                .department(department)
                .empPosition(EmployeePosition.INTERN)
                .build();
        Employee employee2 = Employee.builder()
                .department(department)
                .empPosition(EmployeePosition.INTERN)
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        transferRepository.save(Transfer.builder()
                .employee(employee)
                .transPosition("임원")
                .transDepartment(department2)
                .appointDate(LocalDate.now())
                .build());
        transferRepository.save(Transfer.builder()
                .employee(employee)
                .transPosition("임원")
                .transDepartment(department2)
                .appointDate(LocalDate.now().plusDays(1))
                .build());
        //when
        List<Transfer> result = transferService.autoAppointTransfer();
        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(employee.getDepartment())
                .isEqualTo(department2);
        Assertions.assertThat(employee.getEmpPosition())
                .isEqualTo(EmployeePosition.EXECUTIVES);
        Assertions.assertThat(employee2.getDepartment())
                .isEqualTo(department);
        Assertions.assertThat(employee2.getEmpPosition())
                .isEqualTo(EmployeePosition.INTERN);
    }
    
    @Test
    public void 인사발령() throws Exception {
        //given
        Department curDepartment = Department.builder()
                .deptName("현재 부서")
                .build();
        Department transDepartment = Department.builder()
                .deptName("이동될 부서")
                .build();
        departmentRepository.save(curDepartment);
        departmentRepository.save(transDepartment);
        Employee employee = Employee.builder()
                .empName("테스터")
                .empPosition(EmployeePosition.GENERAL_MANAGER)
                .department(curDepartment)
                .build();
        employeeRepository.save(employee);
        TransferDTO.AddForm addForm = TransferDTO.AddForm.builder()
                .employeeId(employee.getEmpId())
                .transferPosition("임원")
                .transferDate(LocalDate.now().plusDays(5))
                .departmentName(transDepartment.getDeptName())
                .build();
        //when
        Transfer result = transferService.createTransfer(addForm);
        //then
        Assertions.assertThat(result).isEqualTo(transferRepository.findAll(new PageRequestDTO(1)).get(0));
    }
    
    @Test
    public void 전체_인사기록_보기() throws Exception {
        //given
        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .build();
            employeeRepository.save(employee);
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            Transfer transfer = Transfer.builder()
                    .employee(employee)
                    .curDepartment(department)
                    .build();
            transferRepository.save(transfer);
            transfers.add(transfer);
        }
        //when
        List<Transfer> result = transferRepository.findAll(new PageRequestDTO(1));
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(transfers);
    }

    @Test
    public void 직원이름_부서이름_직급_검색() throws Exception {
        //given
        List<Transfer> transfers = new ArrayList<>();
        String[] position = new String[]{"임원", "부장", "차장", "과장", "대리", "주임", "사원"};
        for (int i = 0; i < 7; i++) {
            Employee employee = Employee.builder()
                    .empName("테스터" + i)
                    .build();
            employeeRepository.save(employee);
            Department department = Department.builder()
                    .deptName("부서" + i)
                    .build();
            departmentRepository.save(department);
            Transfer transfer = Transfer.builder()
                    .employee(employee)
                    .curDepartment(department)
                    .curPosition(position[i])
                    .build();
            transferRepository.save(transfer);
            transfers.add(transfer);
        }
        //when
        Transfer resultByEmpName = transferRepository.findAllByEmpName("테스터0", new PageRequestDTO(1)).get(0);
        Transfer resultByDeptName = transferRepository.findAllByDeptName("부서1", new PageRequestDTO(1)).get(0);
        Transfer resultByEmpPosition = transferRepository.findAllByEmpPosition("차장", new PageRequestDTO(1)).get(0);
        //then
        Assertions.assertThat(resultByEmpName).isEqualTo(transfers.get(0));
        Assertions.assertThat(resultByDeptName).isEqualTo(transfers.get(1));
        Assertions.assertThat(resultByEmpPosition).isEqualTo(transfers.get(2));
    }

}