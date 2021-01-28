package team.okky.personnel_management.transfer;

import java.util.List;

public interface TransferService {

    public List<TransferDTO.Info> findAll();
    public List<TransferDTO.Info> findAllByEmpName(String empName);
    public List<TransferDTO.Info> findAllByDeptName(String deptName);
    public List<TransferDTO.Info> findAllByEmpPosition(String empPosition);
    public Transfer addTransfer(TransferDTO.AddForm addForm);

}
