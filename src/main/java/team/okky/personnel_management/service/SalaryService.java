package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Employee;
import team.okky.personnel_management.dto.SalaryDTO;
import team.okky.personnel_management.repository.EmployeeRepository;
import team.okky.personnel_management.repository.EvaluationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryService {
    private final EvaluationRepository evaluationRepository;
    private final EmployeeRepository employeeRepository;
    List<SalaryDTO> salaryDTOList = new ArrayList<>();

    @Transactional(readOnly = true)
    public List<SalaryDTO> filteringList(String empName){
        Map<String,Integer> salaryMap = new HashMap<>();
        salaryMap.put("사원",3000);
        salaryMap.put("대리",3500);
        salaryMap.put("차장",4000);
        salaryMap.put("과장",4500);
        salaryMap.put("본부장",5000);
        salaryMap.put("부장",5500);
        salaryMap.put("사장",6000);

        List<SalaryDTO> list = new ArrayList<>();

        if(empName.isEmpty()) {
            for (Employee e : employeeRepository.findAll()){
                Long evalScore = evaluationRepository.currentIncentive(e.getEmpName()).longValue();
                SalaryDTO salaryDTO = SalaryDTO.builder()
                            .empName(e.getEmpName())
                            .deptName(e.getDepartment().getDeptName())
                            .empPosition(e.getEmpPosition())
                            .salary(salaryMap.get(e.getEmpPosition()))
                            .incentive((int)(evalScore*2.4))
                            .build();
                    list.add(salaryDTO);
            }
        }
        else{
            for (Employee e : employeeRepository.findByEmpName(empName)){
                Long evalScore = evaluationRepository.currentIncentive(e.getEmpName()).longValue();
                SalaryDTO salaryDTO = SalaryDTO.builder()
                        .empName(e.getEmpName())
                        .deptName(e.getDepartment().getDeptName())
                        .empPosition(e.getEmpPosition())
                        .salary(salaryMap.get(e.getEmpPosition()))
                        .incentive((int)(evalScore*2.4))
                        .build();
                list.add(salaryDTO);
                salaryDTOList.add(salaryDTO);
            }
        }
        return list;
        }

        public void update(SalaryDTO salaryDTO){
            for(SalaryDTO s :salaryDTOList){
                if(s.equals(salaryDTO)){
                    s.setEmpName(salaryDTO.getEmpName());
                    s.setDeptName(salaryDTO.getDeptName());
                    s.setEmpPosition(salaryDTO.getEmpPosition());
                    s.setSalary(salaryDTO.getSalary());
                    s.setIncentive(salaryDTO.getIncentive());
                }
            }
        }

}
