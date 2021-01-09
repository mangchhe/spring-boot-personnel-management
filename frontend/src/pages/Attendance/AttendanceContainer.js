import React, { useCallback, useState, useEffect } from 'react';
import Attendance from './Attendance';
import axios from 'axios';

function AttendanceContainer() {
  const [inputValues, setInputValues] = useState({
    date: '',
    word: '',
  });
  const [attendanceArr, setAttendanceArr] = useState([]);
  const [statusArr, setStatusArr] = useState([]);
  const { date, word } = inputValues;

  const fetchAttendance = (route) => {
    axios
      .get(route)
      .then((res) => {
        console.log(res.data);
        const { attendanceList, status } = res.data;
        setAttendanceArr(attendanceList);
        setStatusArr(status);
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
    if (date && !word) {
      fetchAttendance(`/attendance/search?date=${date}`);
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
    />
  );
}

export default AttendanceContainer;
