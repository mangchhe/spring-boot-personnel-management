package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.domain.Work;
import team.okky.personnel_management.dto.SearchDTO;
import team.okky.personnel_management.repository.DepartmentRepository;
import team.okky.personnel_management.dto.WorkFindDto;
import team.okky.personnel_management.repository.WorkRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkService {
    private final WorkRepository workRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Work save(Work work){ return workRepository.save(work); }

    public Work findOne(Long id){
        return workRepository.findOne(id);
    }

    public List<WorkFindDto> filteringList(SearchDTO workSearch) {
        return workRepository.filteringList(workSearch);
    }

    public List<WorkFindDto> findAll(){
        return workRepository.findAll();
    }

    @Transactional
    public Work update(Long workId, String workName, Long deptId, String chargeName, LocalDate startDate, LocalDate endDate){
        Work findWork = workRepository.findOne(workId);
        Department selectDept = departmentRepository.findOne(deptId);

        findWork.change(workName, chargeName, startDate, endDate, selectDept);
        return findWork;
    }
}
