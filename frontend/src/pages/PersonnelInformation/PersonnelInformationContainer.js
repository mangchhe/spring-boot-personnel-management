import React, { useState, useEffect, useCallback } from 'react';
import PersonnelInformation from './PersonnelInformation';
import axios from 'axios';

function PersonnelInformationContainer() {
  const [inputValues, setInputValues] = useState({
    type: 'name',
    word: '',
  });
  const [employeeArr, setEmployeeArr] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [employeeData, setEmployeeData] = useState({});
  const [addInput, setAddInput] = useState({
    empName: '',
    deptId: 1,
    empPosition: '사원',
    empJoinDate: '',
    empPhoneNum: '',
  });
  const [departmentList, setDepartmentList] = useState([]);
  const [pageInfo, setPageInfo] = useState({
    currentPage: 1,
    totalPage: 1,
  });
  const [showPagination, setShowPagination] = useState(true);

  const { type, word } = inputValues;

  const fetchEmployeeWithPage = (route) => {
    axios
      .get(route)
      .then((res) => {
        setEmployeeArr(res.data.list);
        setPageInfo({
          ...pageInfo,
          totalPage: res.data.pageResultDTO.totalPage,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const fetchEmployee = (route) => {
    axios
      .get(route)
      .then((res) => {
        setEmployeeArr(res.data.list);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const fetchDepartment = () => {
    axios
      .get('/department/name')
      .then((res) => {
        setDepartmentList(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    fetchEmployeeWithPage(`/employee?page=${pageInfo.currentPage}`);
    fetchDepartment();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    if (word === '') {
      fetchEmployee(`/employee?page=${pageInfo.currentPage}`);
      setShowPagination(true);
    } else {
      fetchEmployee(`/employee?${type}=${word}`);
      setShowPagination(false);
    }
  };

  const handleAdd = () => {
    setShowModal(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const handleModalClose = () => {
    setShowModal(false);
    setAddInput({
      empName: '',
      deptId: 1,
      empPosition: '사원',
      empJoinDate: '',
      empPhoneNum: '',
    });
  };

  const handleChoose = (e) => {
    e.preventDefault();
    const { empName, deptId, empPosition, empJoinDate, empPhoneNum } = addInput;

    axios
      .post('/employee', {
        empName,
        deptId: parseInt(deptId),
        empPosition,
        empJoinDate,
        empPhoneNum,
      })
      .then(() => {
        handleModalClose();
        fetchEmployee(`/employee?page=${pageInfo.currentPage}`);
        setInputValues({ ...inputValues, type: 'name', word: '' });
        setShowPagination(true);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleAddInputChange = (e) => {
    const { name, value } = e.target;
    setAddInput({ ...addInput, [name]: value });
  };

  const editEmployeeModal = (data) => {
    setShowEditModal(true);
    setEmployeeData(data);
  };

  const handleEdit = (date, phone) => {
    axios
      .put('/employee', {
        empId: employeeData.empId,
        empJoinDate: date,
        empPhoneNum: phone,
      })
      .then(() => {
        fetchEmployee(`/employee?page=${pageInfo.currentPage}`);
        setInputValues({ ...inputValues, type: 'name', word: '' });
        setShowPagination(true);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handlePageChange = (e) => {
    const pageNum = parseInt(e.target.firstChild.nodeValue);
    setPageInfo({
      ...pageInfo,
      currentPage: pageNum,
    });
    fetchEmployee(`/employee?page=${pageNum}`);
  };

  return (
    <PersonnelInformation
      type={type}
      word={word}
      handleSearch={handleSearch}
      handleAdd={handleAdd}
      handleInputChange={handleInputChange}
      employeeArr={employeeArr}
      showModal={showModal}
      handleModalClose={handleModalClose}
      handleChoose={handleChoose}
      addInput={addInput}
      handleAddInputChange={handleAddInputChange}
      departmentList={departmentList}
      editEmployeeModal={editEmployeeModal}
      showEditModal={showEditModal}
      employeeData={employeeData}
      handleEdit={handleEdit}
      setShowEditModal={setShowEditModal}
      pageInfo={pageInfo}
      handlePageChange={handlePageChange}
      showPagination={showPagination}
    />
  );
}

export default PersonnelInformationContainer;
