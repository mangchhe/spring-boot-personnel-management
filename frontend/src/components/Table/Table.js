import React from 'react';
import AttendanceRow from './AttendanceRow';
import styled from 'styled-components';

const StyledTable = styled.table`
  width: 100%;
  text-align: center;
  border-collapse: collapse;
  border: 1px solid #a9a9a9;
  th,
  td {
    border: 1px solid #a9a9a9;
    padding: 0.5em;
  }
`;

const rowComponents = {
  attendance: AttendanceRow,
};

function Table({ page, headerArr, dataArr }) {
  const RowComponent = rowComponents[page];

  if (dataArr.length === 0) {
    return <p>해당하는 정보가 없습니다.</p>;
  }

  return (
    <StyledTable>
      <thead>
        <tr>
          {headerArr.map((header, i) => (
            <th key={i}>{header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {dataArr.map((data, i) => {
          return <RowComponent data={data} key={i} />;
        })}
      </tbody>
    </StyledTable>
  );
}

export default Table;
