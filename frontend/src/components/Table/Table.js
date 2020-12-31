import React from 'react';

const AttendanceRow = ({ data }) => {
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
};

function Table({ page, headerArr, dataArr }) {
  return (
    <table>
      <tr>
        {headerArr.map((header) => (
          <th>{header}</th>
        ))}
      </tr>
      {dataArr.map((data) => {
        if (page === 'attendance') {
          return <AttendanceRow data={data} />;
        }
      })}
    </table>
  );
}

export default Table;
