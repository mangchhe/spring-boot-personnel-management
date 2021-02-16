package team.okky.personnel_management.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    /**
     * 인사 이동 목록 뷰, 사원이름&사원직급&부서이름 검색
     * @param empName
     * @param deptName
     * @param empPosition
     * @param pageNo
     * @return 검색된 인사 이동 목록
     */
    @GetMapping("/transfer")
    public TransferDTO.IndexWithPage transfer(@RequestParam(value = "employee", required = false) String empName,
                                              @RequestParam(value = "department", required = false) String deptName,
                                              @RequestParam(value = "position", required = false) String empPosition,
                                              @RequestParam(value = "page", defaultValue = "1") Integer pageNo){

        List<TransferDTO.Index> transferList = null;
        PageResultDTO pageResultDTO = null;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        if(!StringUtils.isEmpty(empName)){
            transferList = transferService.findAllByEmpName(empName, pageRequestDTO);
            pageResultDTO = transferService.findPageByEmpName(empName, pageNo);
        }
        else if(!StringUtils.isEmpty(deptName)){
            transferList = transferService.findAllByDeptName(deptName, pageRequestDTO);
            pageResultDTO = transferService.findPageByDeptName(deptName, pageNo);
        }
        else if(!StringUtils.isEmpty(empPosition)){
            transferList = transferService.findAllByEmpPosition(empPosition, pageRequestDTO);
            pageResultDTO = transferService.findPageByEmpPosition(empPosition, pageNo);
        }
        else{
            transferList = transferService.findAll(pageRequestDTO);
            pageResultDTO = transferService.findPage(pageNo);
        }

        return TransferDTO.IndexWithPage.builder()
                .transList(transferList)
                .pageResultDTO(pageResultDTO)
                .build();
    }

    /**
     * 인사 이동 추가
     * @param addForm
     */
    @PostMapping("/transfer")
    public void tranferForm(@RequestBody TransferDTO.AddForm addForm){
        transferService.createTransfer(addForm);
    }

}
