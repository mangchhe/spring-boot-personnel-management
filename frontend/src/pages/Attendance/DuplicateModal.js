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

const ModalRadio = styled.div`
  margin-bottom: 0.5em;
  input {
    margin-right: 0.5em;
  }
`;

const SubmitButton = styled.div`
  text-align: right;
  position: absolute;
  right: 20px;
  bottom: 20px;

  button {
    cursor: pointer;
    background-color: #a9a9a9;
    color: white;
    border-radius: 3px;
    padding: 0.3em 1em;
  }
`;

function DuplicateModal({
  showModal,
  handleModalClose,
  duplicateArr,
  selectedId,
  handleSelectedIdChange,
  handleChoose,
}) {
  return (
    <Rodal visible={showModal} onClose={handleModalClose}>
      <ModalTitle>
        직원 선택: {duplicateArr[0] && duplicateArr[0].empName}
      </ModalTitle>
      <form onSubmit={handleChoose}>
        {duplicateArr.map((person) => (
          <ModalRadio key={person.empId}>
            <input
              type="radio"
              value={person.empId}
              checked={selectedId === person.empId}
              onChange={handleSelectedIdChange}
            />{' '}
            {person.deptName} - {person.empPosition} ({person.empJoinDate} 입사)
          </ModalRadio>
        ))}
        <SubmitButton>
          <button type="submit">선택</button>
        </SubmitButton>
      </form>
    </Rodal>
  );
}

export default DuplicateModal;
