import React, { useEffect, useState } from 'react';
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
import styles from './personnelStatus.module.css';
import axios from 'axios';

const POSITION_LISTS = ['사원', '주임', '대리', '과장', '차장', '부장', '임원'];

function PersonnelStatusModal({ showModal, handleModalClose, fetchData }) {
  const [input, setInput] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState('');
  const [deptLists, setDeptLists] = useState([]);
  const [selectedDept, setSelectedDept] = useState('부서1');
  const [selectedPos, setSelectedPos] = useState('사원');
  const [selectedDate, setSelectedDate] = useState('');

  useEffect(() => {
    getDeptLists();
  }, []);

  const getDeptLists = () => {
    axios.get(`./department/name`).then((response) => {
      setDeptLists(response.data);
    });
  };

  const handleModalInput = (e) => {
    setInput(e.target.value);
  };
  const searchEmployee = () => {
    axios.get(`/employee?name=${input}`).then((response) => {
      setSearchResults(response.data.list);
    });
  };

  const saveEmployee = (e) => {
    setSelectedEmployee(e.target.id);
  };

  const addPersonnelStatus = (e) => {
    e.preventDefault();
    axios
      .post('/transfer', {
        employeeId: parseInt(selectedEmployee),
        departmentName: selectedDept,
        transferPosition: selectedPos,
        transferDate: selectedDate,
      })
      .then(() => {
        fetchData();
        handleModalClose();
      });
  };
  const handleDeptChange = (e) => {
    setSelectedDept(e.target.value);
  };

  const handlePosChange = (e) => {
    setSelectedPos(e.target.value);
  };

  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };

  const customStyles = {
    height: 'auto',
    bottom: 'auto',
    top: '30%',
  };
  return (
    <Rodal
      visible={showModal}
      onClose={handleModalClose}
      customStyles={customStyles}
    >
      <div className={styles.modal}>
        <div>
          <label className={styles.label}>직원이름</label>
          <div>
            <input
              className={styles.addInput}
              onChange={handleModalInput}
              placeholder="발령할 직원 이름을 검색해주세요"
              value={input}
            />
            <button
              className={styles.searchEmpButton}
              type="button"
              onClick={searchEmployee}
            >
              검색
            </button>
          </div>
        </div>
        {searchResults.length > 0 && (
          <>
            <label className={styles.label}>발령할 직원을 선택해주세요</label>
            <div className={styles.empLists}>
              <ul onClick={saveEmployee}>
                {searchResults &&
                  searchResults.map((result, index) => {
                    return (
                      <li
                        className={styles.empLi}
                        id={result.empId}
                        key={index}
                        value={result.empName}
                      >
                        {result.empName} <span />
                        {result.deptName} <span />
                        {result.empPosition}
                        <span />
                      </li>
                    );
                  })}
              </ul>
            </div>
          </>
        )}
        <form onSubmit={addPersonnelStatus}>
          <label className={styles.label}>발령부서</label>
          <div>
            <select
              className={styles.modalSelect}
              value={selectedDept}
              onChange={handleDeptChange}
            >
              {deptLists &&
                deptLists.map((dept, index) => {
                  return (
                    <option key={index} value={dept.deptName}>
                      {dept.deptName}
                    </option>
                  );
                })}
            </select>
          </div>
          <label className={styles.label}>발령직급</label>
          <div>
            <select
              className={styles.modalSelect}
              value={selectedPos}
              onChange={handlePosChange}
            >
              {POSITION_LISTS.map((position, index) => {
                return (
                  <option key={index} value={position}>
                    {position}
                  </option>
                );
              })}
            </select>
          </div>
          <label className={styles.label}>발령예정일자</label>
          <div>
            <input
              className={styles.dateInput}
              type="date"
              onChange={handleDateChange}
            />
          </div>
          <div className={styles.submitButtonContainer}>
            <button type="submit" className={styles.submitButton}>
              발령
            </button>
          </div>
        </form>
      </div>
    </Rodal>
  );
}

export default PersonnelStatusModal;
