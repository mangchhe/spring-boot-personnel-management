package team.okky.personnel_management.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.domain.Department;
import team.okky.personnel_management.dto.DepartmentDTO;
import team.okky.personnel_management.repository.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public List<DepartmentDTO.Name> viewIdAndName(){
        return departmentRepository.findAll().stream()
                .map(m -> modelMapper.map(m, DepartmentDTO.Name.class))
                .collect(Collectors.toList());
    }
}
