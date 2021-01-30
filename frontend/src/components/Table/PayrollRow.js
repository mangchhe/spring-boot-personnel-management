import React from 'react';
import styled, { css } from 'styled-components';

const Button = styled.button`
  cursor: pointer;
  background-color: white;
  font-size: 14px;
  ${({ theme }) => {
    return css`
      color: ${theme.palette['blue']};
    `;
  }}
`;

function PayrollRow({ data, rest }) {
  const { editModal } = rest;
  const handleEditButton = () => {
    editModal(data);
  };

  return (
    <tr>
      <td>{data.empName}</td>
      <td>{data.deptName}</td>
      <td>{data.empPosition}</td>
      <td>{data.salary}</td>
      <td>{data.incentive}</td>
      <td>
        <Button onClick={handleEditButton}>수정</Button>
      </td>
    </tr>
  );
}

export default PayrollRow;
