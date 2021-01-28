package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import team.okky.personnel_management.employee.EmployeeDTO;
import team.okky.personnel_management.employee.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/transfer")
    public List<TransferDTO.Info> transfer(@RequestParam(value = "employee", required = false) String empName,
                           @RequestParam(value = "department", required = false) String deptName,
                           @RequestParam(value = "position", required = false) String empPosition){

        List<TransferDTO.Info> transferList = null;

        if(!StringUtils.isEmpty(empName)){
            transferList = transferService.findAllByEmpName(empName);
        }
        else if(!StringUtils.isEmpty(deptName)){
            transferList = transferService.findAllByDeptName(deptName);
        }
        else if(!StringUtils.isEmpty(empPosition)){
            transferList = transferService.findAllByEmpPosition(empPosition);
        }
        else{
            transferList = transferService.findAll();
        }
        return transferList;
    }

    @PostMapping("/transfer")
    public void tranferForm(@ModelAttribute TransferDTO.AddForm addForm){
        transferService.addTransfer(addForm);
    }

}
