package team.okky.personnel_management.transfer;

import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;

public interface TransferService {

    public List<Transfer> autoAppointTransfer();
    public Transfer craeteTransfer(TransferDTO.AddForm addForm);
    public List<TransferDTO.Index> findAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO findPage(int pageNo);
    public List<TransferDTO.Index> findAllByEmpName(String empName, PageRequestDTO pageRequestDTO);
    public PageResultDTO findPageByEmpName(String empName, int pageNo);
    public List<TransferDTO.Index> findAllByDeptName(String deptName, PageRequestDTO pageRequestDTO);
    public PageResultDTO findPageByDeptName(String deptName, int pageNo);
    public List<TransferDTO.Index> findAllByEmpPosition(String empPosition, PageRequestDTO pageRequestDTO);
    public PageResultDTO findPageByEmpPosition(String empPosition, int pageNo);

}
