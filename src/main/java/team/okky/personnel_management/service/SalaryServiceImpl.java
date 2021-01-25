package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.PageRequestDTO;
import team.okky.personnel_management.dto.PageResultDTO;
import team.okky.personnel_management.dto.SalaryDTO;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.EvaluationRepository;

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
    private Map<String, Integer> salaryMap = new HashMap<>();

    @Override
    public List<SalaryDTO.indexSalary> viewAll(PageRequestDTO pageRequestDTO) {

        List<SalaryDTO.indexSalary> list = new ArrayList<>();

        for (Employee e : employeeRepository.findAllOrderByJoinDate(pageRequestDTO)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public PageResultDTO viewAllForPage(int pageNo){
        return new PageResultDTO(
                employeeRepository.findAllOrderByJoinDateTotal(),
                pageNo);
    }

    @Override
    public List<SalaryDTO.indexSalary> viewAllByName(String empName,PageRequestDTO pageRequestDTO){
        List<SalaryDTO.indexSalary> list = new ArrayList<>();
        for (Employee e : employeeRepository.findAllByName(empName,pageRequestDTO)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public PageResultDTO viewAllByNameForPage(String empName, int pageNo){
        return new PageResultDTO(
                employeeRepository.findAllByNameTotal(empName),
                pageNo);
    }

    @Override
    public List<SalaryDTO.indexSalary> findByName(String empName){
        List<SalaryDTO.indexSalary> list = new ArrayList<>();
        for (Employee e : employeeRepository.findByEmpName(empName)) {
            list.add(salaryListPerEmployee(e));
        }
        return list;
    }

    @Override
    public SalaryDTO.indexSalary salaryListPerEmployee(Employee e){
        salaryMap.put("사원", 3000);
        salaryMap.put("대리", 3500);
        salaryMap.put("차장", 4000);
        salaryMap.put("과장", 4500);
        salaryMap.put("본부장", 5000);
        salaryMap.put("부장", 5500);
        salaryMap.put("사장", 6000);

        Long evalScore = evaluationRepository.currentIncentive(e.getEmpName()).longValue();

        SalaryDTO.indexSalary salaryDTO = SalaryDTO.indexSalary.builder()
                .empId(e.getEmpId())
                .empName(e.getEmpName())
                .deptName(e.getDepartment().getDeptName())
                .empPosition(e.getEmpPosition())
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


