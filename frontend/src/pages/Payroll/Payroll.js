import React, { useState, useEffect } from 'react';
import Table from '../../components/Table/Table';
import styled from 'styled-components';
import { HEADER_ARR } from './Constants';
import Pagination from '@material-ui/lab/Pagination';
import axios from 'axios';
import EditModal from './EditModal';

const Container = styled.div`
  .numBoxContainer {
    display: flex;
    justify-content: space-between;
    margin-bottom: 3em;
  }
`;

const StyledForm = styled.form`
  display: flex;
  margin-bottom: 3em;
  input {
    margin-right: 2em;
  }
  .textInput {
    flex: 5;
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

function Payroll() {
  const [userUpdate, setUserUpdate] = useState(false);
  const [word, setWord] = useState('');
  const [salaryData, setSalaryData] = useState([]);
  const [pageInfo, setPageInfo] = useState({
    currentPage: 1,
    totalPage: 1,
  });
  const [showPagination, setShowPagination] = useState(true);
  const [showEditModal, setShowEditModal] = useState(false);
  const [employeeData, setEmployeeData] = useState({
    empId: 0,
    empName: '',
    empPosition: '',
    incentive: 0,
    salary: 0,
  });

  const fetchAllSalary = () => {
    axios.get('/salary').then((res) => {
      const { list, pageResultDTO } = res.data;
      setShowPagination(true);
      setSalaryData(list);
      setPageInfo({
        ...pageInfo,
        currentPage: 1,
        totalPage: pageResultDTO.totalPage,
      });
    });
  };

  const fetchSalary = (route) => {
    axios
      .get(route)
      .then((res) => {
        setSalaryData(res.data.list);
      })
      .catch(() => {
        setSalaryData([]);
      });
  };

  const editModal = (data) => {
    setShowEditModal(true);
    setEmployeeData({ ...data });
  };

  const handleEditModalClose = () => {
    setShowEditModal(false);
  };

  useEffect(() => {
    fetchAllSalary();
  }, []);

  useEffect(() => {
    if (userUpdate) {
      fetchAllSalary();
    }
    setUserUpdate(false);
  }, [userUpdate]);

  const handleWordChange = (e) => {
    setWord(e.target.value);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (word === '') {
      fetchAllSalary();
    } else {
      setShowPagination(false);
      fetchSalary(`/salary?name=${word}`);
    }
  };

  const handlePageChange = (e) => {
    const pageNum = parseInt(e.target.firstChild.nodeValue);
    fetchSalary(`/salary?page=${pageNum}`);
    setPageInfo({ ...pageInfo, currentPage: pageNum });
  };

  return (
    <Container>
      <StyledForm>
        <input
          type="text"
          name="word"
          className="textInput"
          placeholder="직원 이름을 입력하세요."
          value={word}
          onChange={handleWordChange}
        />
        <button type="submit" onClick={handleSearch}>
          검색
        </button>
      </StyledForm>
      <Table
        page="payroll"
        headerArr={HEADER_ARR}
        dataArr={salaryData}
        editModal={editModal}
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
      <EditModal
        showEditModal={showEditModal}
        employeeData={employeeData}
        handleEditModalClose={handleEditModalClose}
        setUserUpdate={setUserUpdate}
      />
    </Container>
  );
}

export default Payroll;
