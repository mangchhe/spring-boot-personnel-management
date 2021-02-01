import React from 'react';

function PersonnelStatusRow({ data }) {
  return (
    <tr>
      <td>{data.employeeName}</td>
      <td>{data.curDepartmentName}</td>
      <td>{data.transferDepartmentName}</td>
      <td>{data.curPosition}</td>
      <td>{data.transferPosition}</td>
      <td>{data.approveDate}</td>
      <td>{data.appointDate}</td>
      <td>{data.isEnd ? '발령완료' : '발령예정'}</td>
    </tr>
  );
}

export default PersonnelStatusRow;
