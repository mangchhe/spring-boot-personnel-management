import React, { useCallback, useState, useEffect } from 'react';
import Attendance from './Attendance';
import axios from 'axios';

////////////////////////////////////////////////
////////////////   Dummy Data      /////////////
const dataArr = [
  {
    date: '02/28',
    day: '금',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '정상',
  },
  {
    date: '03/29',
    day: '토',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '지각',
  },
  {
    date: '04/30',
    day: '일',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '휴가',
  },
  {
    date: '02/28',
    day: '금',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '정상',
  },
  {
    date: '03/29',
    day: '토',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '지각',
  },
  {
    date: '04/30',
    day: '일',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '휴가',
  },
  {
    date: '02/28',
    day: '금',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '정상',
  },
  {
    date: '03/29',
    day: '토',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '지각',
  },
  {
    date: '04/30',
    day: '일',
    name: '조혜련',
    team: '팀원',
    rank: '사원',
    startTime: '9:00',
    endTime: '18:00',
    status: '휴가',
  },
];
const numDummyArr = { normal: 11, late: 3, vacation: 2, absent: 1, sick: 2 };
////////////////////////////////////////////////
////////////////////////////////////////////////

function AttendanceContainer() {
  const currentTime = new Date();
  const [inputValues, setInputValues] = useState({
    date: currentTime.toISOString().split('T')[0],
    word: '',
  });
  ////////////////////////////////////////////////
  /////////   useState 안에 빈 배열로 바꾸기   ////////
  const [attendanceArr, setAttendanceArr] = useState(dataArr);
  const [numArr, setNumArr] = useState(numDummyArr);
  const { date, word } = inputValues;

  useEffect(() => {
    // attendance get 요청
    axios
      .get('/attendance')
      .then((res) => {
        console.log(res.data);
        // attendanceArr로 정보 저장
        // setAttendanceArr(res.data)
      })
      .catch((error) => {
        console.log(error);
      });

    // attendance/info로 get 요청
    axios
      .get('/attendance/info')
      .then((res) => {
        console.log(res.data);
        // numArr로 정보 저장
        // setNumArr(res.data)
      })
      .catch((error) => console.log(error));
  }, []);

  const handleInputChange = useCallback(
    (e) => {
      const { name, value } = e.target;
      setInputValues({ ...inputValues, [name]: value });
    },
    [inputValues],
  );

  const handleSearch = (e) => {
    e.preventDefault();
    alert(`${date}, ${word}`);
    // 검색하면 /attendance?date=2020-03-23&word=조 로 get 요청
    axios
      .get(`/attendance?date=${date}&word=${word}`)
      .then((res) => {
        console.log(res);
        // attendanceArr로 정보 저장
        // setAttendanceArr(res.data);
      })
      .catch((error) => console.log(error));
  };

  return (
    <Attendance
      numArr={numArr}
      date={date}
      word={word}
      handleSearch={handleSearch}
      handleInputChange={handleInputChange}
      attendanceArr={attendanceArr}
    />
  );
}

export default AttendanceContainer;
