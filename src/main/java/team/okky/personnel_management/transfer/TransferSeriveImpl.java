package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferSeriveImpl implements TransferService{

    private final TransferRepository transferRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 모든 인사기록 검색
     * @return 모든 인사기록
     */
    @Override
    public List<TransferDTO.Info> findAll() {
        return transferRepository.findAll().stream()
                .map(Transfer::entityToInfo)
                .collect(Collectors.toList());
    }

    /**
     * 사원 이름으로 검색
     * @param empName
     * @return 사원 이름으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Info> findAllByEmpName(String empName) {
        return transferRepository.findAllByEmpName(empName).stream()
                .map(Transfer::entityToInfo)
                .collect(Collectors.toList());
    }

    /**
     * 부서 이름으로 검색
     * @param deptName
     * @return 부서 이름으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Info> findAllByDeptName(String deptName) {
        return transferRepository.findAllByDeptName(deptName).stream()
                .map(Transfer::entityToInfo)
                .collect(Collectors.toList());
    }

    /**
     * 사원 직급으로 검색
     * @param empPosition
     * @return 사원 직급으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Info> findAllByEmpPosition(String empPosition) {
        return transferRepository.findAllByEmpPosition(empPosition).stream()
                .map(Transfer::entityToInfo)
                .collect(Collectors.toList());
    }

    /**
     * 인사기록 추가
     * @param addForm
     * @return 추가 된 Transfer
     */
    @Override
    @Transactional(readOnly = false)
    public Transfer addTransfer(TransferDTO.AddForm addForm) {
        Employee employee = employeeRepository.findOne(addForm.employeeId);
        Transfer transfer = Transfer.builder()
                .employee(employee)
                .curPosition(employee.getEmpPosition().getPosition())
                .transPosition(addForm.getTransferPosition())
                .curDepartment(employee.getDepartment())
                .transDepartment(departmentRepository.findByName(addForm.getDepartmentName()))
                .approveDate(LocalDate.now())
                .appointDate(addForm.getTransferDate())
                .build();
        transferRepository.save(transfer);
        return transfer;
    }
}
