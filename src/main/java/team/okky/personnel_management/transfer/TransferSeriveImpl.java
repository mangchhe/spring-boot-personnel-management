package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.department.DepartmentRepository;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

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
     * 인사이동이 필요한 사원들을 인사이동
     * @return 인사이동이 완료된 인사목록
     */
    @Override
    @Transactional(readOnly = false)
    public List<Transfer> autoAppointTransfer(){
        List<Transfer> nowAppointList = transferRepository.findAllByNowAppointDate();
        for (Transfer t: nowAppointList) {
            Employee employee = t.getEmployee();
            employee.changeDepartment(t.getTransDepartment());
            employee.changePosition(t.getTransPosition());
        }
        return nowAppointList;
    }

    /**
     * 인사기록 추가
     * @param addForm
     * @return 추가 된 Transfer
     */
    @Override
    @Transactional(readOnly = false)
    public Transfer createTransfer(TransferDTO.AddForm addForm) {
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

    /**
     * 모든 인사기록 검색
     * @return 모든 인사기록
     */
    @Override
    public List<TransferDTO.Index> findAll(PageRequestDTO pageRequestDTO) {
        return transferRepository.findAll(pageRequestDTO).stream()
                .map(Transfer::entityToIndex)
                .collect(Collectors.toList());
    }
    
    /**
     * 모든 인사기록 페이징 데이터 생성
     * @param pageNo
     * @return 모든 인사기록 페이징 정보
     */
    @Override
    public PageResultDTO findPage(int pageNo) {
        return new PageResultDTO(
                transferRepository.findTotal(),
                pageNo);
    }

    /**
     * 사원 이름으로 검색
     * @param empName
     * @return 사원 이름으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Index> findAllByEmpName(String empName, PageRequestDTO pageRequestDTO) {
        return transferRepository.findAllByEmpName(empName, pageRequestDTO).stream()
                .map(Transfer::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 사원 이름으로 검색한 인사기록 페이징 데이터 생성
     * @param empName
     * @param pageNo
     * @return 사원 이름으로 검색한 인사기록 페이징 정보
     */
    @Override
    public PageResultDTO findPageByEmpName(String empName, int pageNo) {
        return new PageResultDTO(
                transferRepository.findTotalByEmpName(empName),
                pageNo);
    }

    /**
     * 부서 이름으로 검색
     * @param deptName
     * @return 부서 이름으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Index> findAllByDeptName(String deptName, PageRequestDTO pageRequestDTO) {
        return transferRepository.findAllByDeptName(deptName, pageRequestDTO).stream()
                .map(Transfer::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 부서 이름으로 검색한 인사기록 페이징 데이터 생성
     * @param deptName
     * @param pageNo
     * @return 부서 이름으로 검색한 인사기록 페이징 정보
     */
    @Override
    public PageResultDTO findPageByDeptName(String deptName, int pageNo) {
        return new PageResultDTO(
                transferRepository.findTotalByDeptName(deptName),
                pageNo);
    }

    /**
     * 사원 직급으로 검색
     * @param empPosition
     * @return 사원 직급으로 검색한 인사기록 목록
     */
    @Override
    public List<TransferDTO.Index> findAllByEmpPosition(String empPosition, PageRequestDTO pageRequestDTO) {
        return transferRepository.findAllByEmpPosition(empPosition, pageRequestDTO).stream()
                .map(Transfer::entityToIndex)
                .collect(Collectors.toList());
    }

    /**
     * 사원 직책으로 검색한 페이징 인사기록 데이터 생성
     * @param empPosition
     * @param pageNo
     * @return 사원 직책으로 검색한 인사기록 페이징 정보
     */
    @Override
    public PageResultDTO findPageByEmpPosition(String empPosition, int pageNo) {
        return new PageResultDTO(
                transferRepository.findTotalByEmpPosition(empPosition),
                pageNo);
    }

}
