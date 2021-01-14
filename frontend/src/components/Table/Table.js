import React from 'react';
import AttendanceRow from './AttendanceRow';
import EmployeeRow from './EmployeeRow';
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
  personnelInformation: EmployeeRow,
};

function Table({ page, headerArr, dataArr, ...rest }) {
  const RowComponent = rowComponents[page];

  if (!dataArr) {
    return <p>로딩 중...</p>;
  }

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
          return <RowComponent data={data} key={i} rest={rest} />;
        })}
      </tbody>
    </StyledTable>
  );
}

export default Table;
