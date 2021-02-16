package team.okky.personnel_management.employee;

import team.okky.personnel_management.utils.dto.PageRequestDTO;
import team.okky.personnel_management.utils.dto.PageResultDTO;

import java.util.List;


public interface EmployeeService {

    /**
     * 사원 등록
     * @param addForm
     * @return 등록된 사원
     */
    public Employee createEmployee(EmployeeDTO.AddForm addForm);

    /**
     * 사원 정보 변경
     * @param updateForm
     * @return 변경된 사원
     */
    public Employee updateEmployee(EmployeeDTO.UpdateForm updateForm);

    /**
     * 전체 직원 목록 보여주기
     * @detail 직원 입사일 기준으로 내림차순
     * @return 직원 정보가 담긴 목록
     */
    public List<EmployeeDTO.Index> viewAll(PageRequestDTO pageRequestDTO);

    /**
     * 전체 직원 목록 페이징 데이터 생성
     * @param pageNo
     * @return 전체 직원 페이징 정보
     */
    public PageResultDTO viewPage(int pageNo);

    /**
     * 이름 검색
     * @param name
     * @return 해당하는 이름만 담은 사원 목록, 동명이인 포함
     */
    public List<EmployeeDTO.Index> viewAllByName(String name, PageRequestDTO pageRequestDTO);

    /**
     * 이름으로 검색한 직원 목록 페이징 데이터 생성
     * @param pageNo
     * @param name
     * @return 이름으로 검색한 직원 페이징 정보
     */
    public PageResultDTO viewPageByName(String name, int pageNo);

    /**
     * 직원 부서로 검색
     * @param deptName
     * @return 해당 부서를 검색한 직원 목록
     */
    public List<EmployeeDTO.Index> viewAllByDept(String deptName, PageRequestDTO pageRequestDTO);

    /**
     * 부서이름으로 검색한 직원 목록 페이지 데이터 생성
     * @param pageNo
     * @param deptName
     * @return 부서이름으로 검색한 직원 페이징 정보
     */
    public PageResultDTO viewPageByDeptName(String deptName, int pageNo);

}
