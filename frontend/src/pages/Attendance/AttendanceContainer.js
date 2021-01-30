import React, { useCallback, useState, useEffect } from 'react';
import Attendance from './Attendance';
import axios from 'axios';

function AttendanceContainer() {
  const [inputValues, setInputValues] = useState({
    date: '',
    word: '',
  });
  const [attendanceArr, setAttendanceArr] = useState(null);
  const [statusArr, setStatusArr] = useState([]);
  const [duplicateArr, setDuplicateArr] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedId, setSelectedId] = useState('');
  const [pageInfo, setPageInfo] = useState({
    currentPage: 1,
    totalPage: 1,
  });
  const [showPagination, setShowPagination] = useState(true);

  const { date, word } = inputValues;

  const fetchAttendanceWithPage = (route) => {
    axios
      .get(route)
      .then((res) => {
        const { attendanceList, status } = res.data;
        setAttendanceArr(attendanceList);
        setStatusArr(status);
        setPageInfo({
          ...pageInfo,
          currentPage: 1,
          totalPage: res.data.pageResultDTO.totalPage,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const fetchAttendance = (route) => {
    axios
      .get(route)
      .then((res) => {
        const { attendanceList, status } = res.data;
        setAttendanceArr(attendanceList);
        setStatusArr(status);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const fetchDuplication = (name) => {
    axios
      .get(`/employee?name=${name}`)
      .then((res) => {
        setDuplicateArr(res.data.list);
        if (res.data.list.length === 0) {
          setAttendanceArr([]);
          return;
        }
        setShowModal(true);
        setSelectedId(res.data.list[0].empId);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    fetchAttendanceWithPage(`/attendance?page=${pageInfo.currentPage}`);
  }, []);

  const handleAllDates = () => {
    setInputValues({ ...inputValues, date: '' });
  };

  const handleInputChange = useCallback(
    (e) => {
      const { name, value } = e.target;
      setInputValues({ ...inputValues, [name]: value });
    },
    [inputValues],
  );

  const handleSearch = (e) => {
    e.preventDefault();
    if (!date && !word) {
      fetchAttendanceWithPage('/attendance');
      setPageInfo({ ...pageInfo, currentPage: 1 });
      setShowPagination(true);
    }
    if (date && !word) {
      fetchAttendance(`/attendance?date=${date}`);
      setShowPagination(false);
    }
    if (word) {
      fetchDuplication(word);
      setShowPagination(false);
    }
  };

  const handleStatus = (status) => {
    axios
      .get(`/attendance/status/${status}`)
      .then((res) => {
        setAttendanceArr(res.data);
        setShowPagination(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  const handleSelectedIdChange = (e) => {
    setSelectedId(parseInt(e.target.value));
  };

  const handleChoose = (e) => {
    e.preventDefault();
    const { date } = inputValues;
    handleModalClose();
    if (!date) {
      fetchAttendance(`/attendance?name=${selectedId}`);
    } else {
      fetchAttendance(`/attendance?date=${date}&name=${selectedId}`);
    }
  };

  const handlePageChange = (e) => {
    const pageNum = parseInt(e.target.firstChild.nodeValue);
    fetchAttendance(`/attendance?page=${pageNum}`);
    setPageInfo({
      ...pageInfo,
      currentPage: pageNum,
    });
  };

  return (
    <Attendance
      date={date}
      word={word}
      handleAllDates={handleAllDates}
      handleSearch={handleSearch}
      handleInputChange={handleInputChange}
      statusArr={statusArr}
      attendanceArr={attendanceArr}
      handleStatus={handleStatus}
      showModal={showModal}
      handleModalClose={handleModalClose}
      duplicateArr={duplicateArr}
      selectedId={selectedId}
      handleSelectedIdChange={handleSelectedIdChange}
      handleChoose={handleChoose}
      pageInfo={pageInfo}
      handlePageChange={handlePageChange}
      showPagination={showPagination}
    />
  );
}

export default AttendanceContainer;
