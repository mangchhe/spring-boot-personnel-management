import React, { useState, useEffect } from 'react';
import axios from 'axios';
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

function EditModal({
  showEditModal,
  handleEditModalClose,
  employeeData,
  setUserUpdate,
}) {
  const {
    empId,
    empName,
    deptName,
    empPosition,
    salary,
    incentive,
  } = employeeData;

  const [inputValues, setInputValues] = useState({
    salaryInput: salary,
    incentiveInput: incentive,
  });

  const { salaryInput, incentiveInput } = inputValues;

  useEffect(() => {
    setInputValues({
      salaryInput: salary,
      incentiveInput: incentive,
    });
  }, [employeeData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInputValues({ ...inputValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      empId,
      salary: parseInt(salaryInput),
      incentive: parseInt(incentiveInput),
    };
    axios
      .put(`/salary/${empId}/edit`, data)
      .then(() => {
        setUserUpdate(true);
      })
      .catch((err) => console.log(err));
    handleClose();
  };

  const handleClose = () => {
    handleEditModalClose(false);
    setInputValues({
      salaryInput: salary || '',
      incentiveInput: incentive || '',
    });
  };

  return (
    <Rodal visible={showEditModal} onClose={handleClose}>
      <ModalTitle>
        {empName} : {deptName} - {empPosition}
      </ModalTitle>
      <Form onSubmit={handleSubmit}>
        <FormRow>
          <label>급여: </label>
          <input
            type="number"
            name="salaryInput"
            value={salaryInput}
            onChange={handleInputChange}
          />
        </FormRow>
        <FormRow>
          <label>성과급: </label>
          <input
            type="number"
            name="incentiveInput"
            value={incentiveInput}
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

export default EditModal;
