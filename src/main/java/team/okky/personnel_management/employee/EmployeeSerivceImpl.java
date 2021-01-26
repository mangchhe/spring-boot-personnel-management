package team.okky.personnel_management.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.department.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeSerivceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 전체 직원 목록 보여주기
     * @detail 직원 입사일 기준으로 내림차순
     * @return 직원 정보가 담긴 목록
     */
    @Override
    public List<EmployeeDTO.ListIndex> viewAll(PageRequestDTO pageRequestDTO){
        return employeeRepository.findAllOrderByJoinDate(pageRequestDTO).stream()
                .map(Employee::entityToListIndex)
                .collect(Collectors.toList());
    }

    /**
     * 전체 직원 목록 페이징 데이터 생성
     * @param pageNo
     * @return 전체 직원 페이징 정보
     */
    @Override
    public PageResultDTO viewAllForPage(int pageNo){
        return new PageResultDTO(
                employeeRepository.findAllOrderByJoinDateTotal(),
                pageNo);
    }

    /**
     * 이름 검색
     * @param name
     * @return 해당하는 이름만 담은 사원 목록, 동명이인 포함
     */
    @Override
    public List<EmployeeDTO.ListIndex> viewAllByName(String name, PageRequestDTO pageRequestDTO){
        return employeeRepository.findAllByName(name, pageRequestDTO).stream()
                .map(Employee::entityToListIndex)
                .collect(Collectors.toList());
    }

    /**
     * 이름으로 검색한 직원 목록 페이징 데이터 생성
     * @param pageNo
     * @param name
     * @return 이름으로 검색한 직원 페이징 정보
     */
    @Override
    public PageResultDTO viewAllByNameForPage(String name, int pageNo){
        return new PageResultDTO(
                employeeRepository.findAllByNameTotal(name),
                pageNo);
    }

    /**
     * 직원 부서로 검색
     * @param deptName
     * @return 해당 부서를 검색한 직원 목록
     */
    @Override
    public List<EmployeeDTO.ListIndex> viewAllByDept(String deptName, PageRequestDTO pageRequestDTO){
        return employeeRepository.findAllByDept(deptName, pageRequestDTO).stream()
                .map(Employee::entityToListIndex)
                .collect(Collectors.toList());
    }

    /**
     * 부서이름으로 검색한 직원 목록 페이지 데이터 생성
     * @param pageNo
     * @param deptName
     * @return 부서이름으로 검색한 직원 페이징 정보
     */
    @Override
    public PageResultDTO viewAllByDeptNameForPage(String deptName, int pageNo){
        return new PageResultDTO(
                employeeRepository.findAllByDeptTotal(deptName),
                pageNo);
    }

    /**
     * 사원 등록
     * @param addEmployee
     * @return 등록된 사원
     */
    @Override
    @Transactional(readOnly = false)
    public Employee createEmployee(EmployeeDTO.AddEmployee addEmployee){
        Employee employee = Employee.builder()
                .empName(addEmployee.getEmpName())
                .department(departmentRepository.findOne(addEmployee.getDeptId()))
                .empPosition(addEmployee.getEmpPosition())
                .empJoinDate(addEmployee.getEmpJoinDate())
                .empPhoneNum(addEmployee.getEmpPhoneNum())
                .build();
        employeeRepository.save(employee);
        return employee;
    }

    /**
     * 사원 정보 변경
     * @param updateEmployee
     * @return 변경된 사원
     */
    @Override
    @Transactional(readOnly = false)
    public Employee updateEmployee(EmployeeDTO.UpdateEmployee updateEmployee){
        Employee employee = employeeRepository.findOne(updateEmployee.getEmpId());
        employee.changePhoneNum(updateEmployee.getEmpPhoneNum());
        employee.changeJoinDate(updateEmployee.getEmpJoinDate());
        return employee;
    }

}
