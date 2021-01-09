import React from 'react';

const status = {
  ON: '출근',
  OFF: '퇴근',
  ABSENCE: '결근',
  LATE: '지각',
  VACATION: '휴가',
  SICK: '병가',
};

function AttendanceRow({ data }) {
  return (
    <tr>
      <td>{data.attDate}</td>
      <td>{data.dayOfWeek}</td>
      <td>{data.empName}</td>
      <td>{data.deptName || ''}</td>
      <td>{data.empPosition}</td>
      <td>{data.attOnTime || ''}</td>
      <td>{data.attOffTime || ''}</td>
      <td>{status[data.attStatus]}</td>
    </tr>
  );
}

export default AttendanceRow;
