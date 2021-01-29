package team.okky.personnel_management.salary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.employee.Employee;
import team.okky.personnel_management.employee.EmployeePosition;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;
import team.okky.personnel_management.employee.EmployeeRepository;
import team.okky.personnel_management.evaluation.EvaluationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalaryServiceImpl implements SalaryService {
    private final EvaluationRepository evaluationRepository;
    private final EmployeeRepository employeeRepository;
    private static List<SalaryDTO.updateForm> checkChange = new ArrayList<>();
    private Map<EmployeePosition, Integer> salaryMap = new HashMap<>();

    @Override
    public List<SalaryDTO.indexSalary> viewAll(PageRequestDTO pageRequestDTO) {

        List<SalaryDTO.indexSalary> list = new ArrayList<>();

        for (Employee e : employeeRepository.findAll(pageRequestDTO)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public PageResultDTO viewAllForPage(int pageNo){
        return new PageResultDTO(
                employeeRepository.findTotal(),
                pageNo);
    }

    @Override
    public List<SalaryDTO.indexSalary> viewAllByName(String empName,PageRequestDTO pageRequestDTO){
        List<SalaryDTO.indexSalary> list = new ArrayList<>();
        for (Employee e : employeeRepository.findAllByEmpName(empName,pageRequestDTO)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public PageResultDTO viewAllByNameForPage(String empName, int pageNo){
        return new PageResultDTO(
                employeeRepository.findTotalByName(empName),
                pageNo);
    }

    @Override
    public List<SalaryDTO.indexSalary> findByName(String empName){
        List<SalaryDTO.indexSalary> list = new ArrayList<>();
        for (Employee e : employeeRepository.findAllByEmpName(empName)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public SalaryDTO.indexSalary salaryListPerEmployee(Employee e){
        salaryMap.put(EmployeePosition.valueOf("DEFAULT"), 0);
        salaryMap.put(EmployeePosition.valueOf("INTERN"), 2800);
        salaryMap.put(EmployeePosition.valueOf("STAFF"), 3000);
        salaryMap.put(EmployeePosition.valueOf("SENIOR_STAFF"), 3500);
        salaryMap.put(EmployeePosition.valueOf("ASSISTANT_MANAGER"), 4000);
        salaryMap.put(EmployeePosition.valueOf("MANAGER"), 4500);
        salaryMap.put(EmployeePosition.valueOf("DEPUTY_GENERAL_MANAGER"), 5000);
        salaryMap.put(EmployeePosition.valueOf("GENERAL_MANAGER"), 5500);
        salaryMap.put(EmployeePosition.valueOf("EXECUTIVES"), 7000);

        Long evalScore = evaluationRepository.currentIncentive(e.getEmpName()).longValue();

        SalaryDTO.indexSalary salaryDTO = SalaryDTO.indexSalary.builder()
                .empId(e.getEmpId())
                .empName(e.getEmpName())
                .deptName(e.getDepartment().getDeptName())
                .empPosition(e.getEmpPosition().getPosition())
                .salary(salaryMap.get(e.getEmpPosition()))
                .incentive((int) (evalScore * 2.4))
                .build();

        List<SalaryDTO.updateForm> update = checkChange.stream().filter(t->t.getEmpId()==salaryDTO.getEmpId()).collect(Collectors.toList());
        if(!update.isEmpty()){
            salaryDTO.setSalary(update.get(0).getSalary());
            salaryDTO.setIncentive(update.get(0).getIncentive());
        }
        return salaryDTO;
    }

    @Override
    public void update(SalaryDTO.updateForm salaryPerEmp){
        if(checkChange.isEmpty()){
            checkChange.add(salaryPerEmp);
        }
        List<SalaryDTO.updateForm> update = checkChange.stream().filter(t->t.getEmpId()==salaryPerEmp.getEmpId()).collect(Collectors.toList());
        if(!update.isEmpty()) {
            update.get(0).setSalary(salaryPerEmp.getSalary());
            update.get(0).setIncentive(salaryPerEmp.getIncentive());
        }
        else {
            checkChange.add(salaryPerEmp);
        }
    }
}


