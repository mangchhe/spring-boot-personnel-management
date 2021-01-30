import React from 'react';
import Table from '../../components/Table/Table';
import { HEADER_ARR } from './Constants';
import styled from 'styled-components';
import AddEmployeeModal from './AddEmployeeModal';
import EditEmployeeModal from './EditEmployeeModal';
import Pagination from '@material-ui/lab/Pagination';

const StyledForm = styled.form`
  display: flex;
  margin-bottom: 3em;
  input {
    margin-left: 2em;
    margin-right: 2em;
  }
  .typeInput {
    flex: 1;
    margin-left: 2em;
  }
  .textInput {
    flex: 4;
  }
  button {
    flex: 1;
    cursor: pointer;
    background-color: #a9a9a9;
    color: white;
    border-radius: 3px;
  }
`;

const PaginationContainer = styled.div`
  text-align: center;
  margin-top: 5em;
  margin-bottom: 3em;
  display: flex;
  justify-content: center;
`;

function PersonnelInformation({
  type,
  word,
  handleSearch,
  handleAdd,
  handleInputChange,
  employeeArr,
  showModal,
  handleModalClose,
  handleChoose,
  addInput,
  handleAddInputChange,
  departmentList,
  editEmployeeModal,
  showEditModal,
  employeeData,
  handleEdit,
  setShowEditModal,
  pageInfo,
  handlePageChange,
  showPagination,
}) {
  return (
    <div>
      <StyledForm onSubmit={handleSearch}>
        <button type="button" onClick={handleAdd}>
          직원 추가
        </button>
        <select
          name="type"
          value={type}
          onChange={handleInputChange}
          className="typeInput"
        >
          <option value="name">이름</option>
          <option value="deptName">부서</option>
        </select>
        <input
          type="text"
          name="word"
          value={word}
          onChange={handleInputChange}
          className="textInput"
        />
        <button type="submit">검색</button>
      </StyledForm>
      <Table
        page="personnelInformation"
        headerArr={HEADER_ARR}
        dataArr={employeeArr}
        editEmployeeModal={editEmployeeModal}
      />
      {showPagination && (
        <PaginationContainer>
          <Pagination
            count={pageInfo.totalPage}
            page={pageInfo.currentPage}
            onChange={handlePageChange}
            hidePrevButton
            hideNextButton
          />
        </PaginationContainer>
      )}
      <AddEmployeeModal
        showModal={showModal}
        handleModalClose={handleModalClose}
        handleChoose={handleChoose}
        addInput={addInput}
        handleAddInputChange={handleAddInputChange}
        departmentList={departmentList}
      />
      <EditEmployeeModal
        showModal={showEditModal}
        employeeData={employeeData}
        handleEdit={handleEdit}
        setShowEditModal={setShowEditModal}
      />
    </div>
  );
}

export default PersonnelInformation;
