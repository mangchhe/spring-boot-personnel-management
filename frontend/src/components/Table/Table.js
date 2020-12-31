import React from 'react';
import styles from './table.module.css';

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
    <table className={styles.table}>
      <thead>
        <tr>
          {headerArr.map((header, i) => (
            <th key={i}>{header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {page === 'attendance' ? (
          dataArr.map((data, i) => {
            return <AttendanceRow data={data} key={i} />;
          })
        ) : (
          <></>
        )}
      </tbody>
    </table>
  );
}

export default Table;
