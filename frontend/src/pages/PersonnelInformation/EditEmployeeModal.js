import React, { useState, useEffect } from 'react';
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
  margin: 2em 1em 0 1em;
`;

const FormRow = styled.div`
  display: flex;
  margin-bottom: 2em;
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

function EditEmployeeModal({
  showModal,
  employeeData,
  handleEdit,
  setShowEditModal,
}) {
  const {
    empName,
    departmentDeptName,
    empPosition,
    empJoinDate,
    empPhoneNum,
  } = employeeData;

  const [inputValues, setInputValues] = useState({
    dateInput: '',
    phoneInput: '',
  });

  useEffect(() => {
    if (empJoinDate) {
      setInputValues({
        dateInput: empJoinDate,
        phoneInput: empPhoneNum,
      });
    }
  }, [employeeData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const { dateInput, phoneInput } = inputValues;

  const handleSubmit = (e) => {
    e.preventDefault();
    handleEdit(dateInput, phoneInput);
    handleModalClose();
  };

  const handleModalClose = () => {
    setShowEditModal(false);
    setInputValues({
      dateInput: empJoinDate || '',
      phoneInput: empPhoneNum || '',
    });
  };

  return (
    <Rodal visible={showModal} onClose={handleModalClose}>
      <ModalTitle>
        {empName} : {departmentDeptName} - {empPosition}
      </ModalTitle>
      <Form onSubmit={handleSubmit}>
        <FormRow>
          <label>입사년도: </label>
          <input
            type="date"
            name="dateInput"
            value={dateInput}
            onChange={handleInputChange}
          />
        </FormRow>
        <FormRow>
          <label>전화번호: </label>
          <input
            type="tel"
            name="phoneInput"
            value={phoneInput}
            onChange={handleInputChange}
          />
        </FormRow>
        <SubmitButton>
          <button type="submit">수정</button>
        </SubmitButton>
      </Form>
    </Rodal>
  );
}

export default EditEmployeeModal;
