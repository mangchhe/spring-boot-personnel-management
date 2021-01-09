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
  const { date, word } = inputValues;

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
      .get(`/employee/duplication/${name}`)
      .then((res) => {
        setDuplicateArr(res.data);
        if (res.data.length === 0) {
          setAttendanceArr([]);
          return;
        }
        setShowModal(true);
        setSelectedId(res.data[0].empId);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    fetchAttendance('/attendance');
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
    const { date, word } = inputValues;
    if (!date && !word) {
      fetchAttendance('/attendance');
    }
    if (date && !word) {
      fetchAttendance(`/attendance/search?date=${date}`);
    }
    if (word) {
      fetchDuplication(word);
    }
  };

  const handleStatus = (status) => {
    axios
      .get(`/attendance/status/${status}`)
      .then((res) => {
        setAttendanceArr(res.data);
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
      fetchAttendance(`/attendance/search?name=${selectedId}`);
    } else {
      fetchAttendance(`/attendance/search?date=${date}&name=${selectedId}`);
    }
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
    />
  );
}

export default AttendanceContainer;
