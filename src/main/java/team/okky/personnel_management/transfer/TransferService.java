package team.okky.personnel_management.transfer;

import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;

public interface TransferService {

    /**
     * 인사이동이 필요한 사원들을 인사이동
     * @return 인사이동이 완료된 인사목록
     */
    public List<Transfer> autoAppointTransfer();

    /**
     * 인사기록 추가
     * @param addForm
     * @return 추가 된 Transfer
     */
    public Transfer createTransfer(TransferDTO.AddForm addForm);

    /**
     * 모든 인사기록 검색
     * @return 모든 인사기록
     */
    public List<TransferDTO.Index> findAll(PageRequestDTO pageRequestDTO);

    /**
     * 모든 인사기록 페이징 데이터 생성
     * @param pageNo
     * @return 모든 인사기록 페이징 정보
     */
    public PageResultDTO findPage(int pageNo);

    /**
     * 사원 이름으로 검색
     * @param empName
     * @return 사원 이름으로 검색한 인사기록 목록
     */
    public List<TransferDTO.Index> findAllByEmpName(String empName, PageRequestDTO pageRequestDTO);

    /**
     * 사원 이름으로 검색한 인사기록 페이징 데이터 생성
     * @param empName
     * @param pageNo
     * @return 사원 이름으로 검색한 인사기록 페이징 정보
     */
    public PageResultDTO findPageByEmpName(String empName, int pageNo);

    /**
     * 부서 이름으로 검색
     * @param deptName
     * @return 부서 이름으로 검색한 인사기록 목록
     */
    public List<TransferDTO.Index> findAllByDeptName(String deptName, PageRequestDTO pageRequestDTO);

    /**
     * 부서 이름으로 검색한 인사기록 페이징 데이터 생성
     * @param deptName
     * @param pageNo
     * @return 부서 이름으로 검색한 인사기록 페이징 정보
     */
    public PageResultDTO findPageByDeptName(String deptName, int pageNo);

    /**
     * 사원 직급으로 검색
     * @param empPosition
     * @return 사원 직급으로 검색한 인사기록 목록
     */
    public List<TransferDTO.Index> findAllByEmpPosition(String empPosition, PageRequestDTO pageRequestDTO);

    /**
     * 사원 직책으로 검색한 페이징 인사기록 데이터 생성
     * @param empPosition
     * @param pageNo
     * @return 사원 직책으로 검색한 인사기록 페이징 정보
     */
    public PageResultDTO findPageByEmpPosition(String empPosition, int pageNo);

}
