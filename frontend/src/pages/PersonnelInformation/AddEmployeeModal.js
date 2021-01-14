import React from 'react';
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
import styled, { css } from 'styled-components';

const ModalTitle = styled.p`
  font-size: 18px;
  ${({ theme }) => {
    return css`
      color: ${theme.palette.blue};
    `;
  }}
  margin-bottom: 1em;
`;

const Form = styled.form`
  margin: 1em;
`;

const FormRow = styled.div`
  display: flex;
  margin-bottom: 1.2em;
  font-size: 14px;
  label {
    flex: 1;
    text-align: right;
  }
  input,
  select {
    margin-left: 2em;
    flex: 4;
  }
`;

const SubmitButton = styled.div`
  text-align: right;
  position: absolute;
  right: 1em;
  bottom: 1em;

  button {
    cursor: pointer;
    background-color: #a9a9a9;
    color: white;
    border-radius: 3px;
    padding: 0.3em 1em;
  }
`;

const empPositionList = ['사원', '대리', '과장', '차장', '부장'];

function AddEmployeeModal({
  showModal,
  handleModalClose,
  handleChoose,
  addInput,
  handleAddInputChange,
  departmentList,
}) {
  const { empName, deptId, empPosition, empJoinDate, empPhoneNum } = addInput;

  return (
    <Rodal
      visible={showModal}
      onClose={handleModalClose}
      width={480}
      height={288}
    >
      <ModalTitle>직원 추가</ModalTitle>
      <Form onSubmit={handleChoose}>
        <FormRow>
          <label>이름: </label>
          <input
            type="text"
            name="empName"
            value={empName}
            onChange={handleAddInputChange}
          />
        </FormRow>
        <FormRow>
          <label>부서: </label>
          <select name="deptId" value={deptId} onChange={handleAddInputChange}>
            {departmentList.map((department) => (
              <option value={department.deptId} key={department.deptId}>
                {department.deptName}
              </option>
            ))}
          </select>
        </FormRow>
        <FormRow>
          <label>직급: </label>
          <select
            name="empPosition"
            value={empPosition}
            onChange={handleAddInputChange}
          >
            {empPositionList.map((s) => (
              <option value={s} key={s}>
                {s}
              </option>
            ))}
          </select>
        </FormRow>
        <FormRow>
          <label>입사년도: </label>
          <input
            type="date"
            name="empJoinDate"
            value={empJoinDate}
            onChange={handleAddInputChange}
          />
        </FormRow>
        <FormRow>
          <label>전화번호: </label>
          <input
            type="tel"
            name="empPhoneNum"
            value={empPhoneNum}
            onChange={handleAddInputChange}
          />
        </FormRow>
        <SubmitButton>
          <button type="submit">추가</button>
        </SubmitButton>
      </Form>
    </Rodal>
  );
}

export default AddEmployeeModal;
