import React from 'react';
import styled from 'styled-components';

const Button = styled.button`
  cursor: pointer;
  background-color: white;
`;

function EmployeeRow({ data, rest }) {
  const { editEmployeeModal } = rest;

  const handleEditButton = () => {
    editEmployeeModal(data);
  };

  return (
    <tr>
      <td>{data.empName}</td>
      <td>{data.departmentDeptName}</td>
      <td>{data.empPosition}</td>
      <td>{data.empJoinDate}</td>
      <td>{data.empPhoneNum}</td>
      <td>
        <Button onClick={handleEditButton}>수정</Button>
      </td>
    </tr>
  );
}

export default EmployeeRow;
