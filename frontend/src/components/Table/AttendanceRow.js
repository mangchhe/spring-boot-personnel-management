import React from 'react';

function AttendanceRow({ data }) {
  return (
    <tr>
      <td>{data.date}</td>
      <td>{data.day}</td>
      <td>{data.name}</td>
      <td>{data.team}</td>
      <td>{data.rank}</td>
      <td>{data.startTime}</td>
      <td>{data.endTime}</td>
      <td>{data.status}</td>
    </tr>
  );
}

export default AttendanceRow;
