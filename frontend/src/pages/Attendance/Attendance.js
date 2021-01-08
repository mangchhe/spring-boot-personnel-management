import React from 'react';
import Table from '../../components/Table/Table';
import NumBox from './NumBox';
import { NORMAL, LATE, VACATION, ABSENT, SICK, HEADER_ARR } from './Constants';
import styled from 'styled-components';

const Container = styled.div`
  width: 95%;
  .numBoxContainer {
    display: flex;
    justify-content: space-between;
    margin-bottom: 3em;
  }
`;

const StyledForm = styled.form`
  display: flex;
  margin-bottom: 3em;
  input {
    margin-right: 2em;
  }
  .dateInput {
    flex: 1;
  }
  .textInput {
    flex: 4;
  }
  button {
    flex: 1;
    cursor: pointer;
    background-color: #a9a9a9;
    color: white;
    border-radius: 3px;
  }
`;

function Attendance({
  numArr,
  date,
  word,
  handleSearch,
  handleInputChange,
  attendanceArr,
}) {
  return (
    <Container>
      <div className="numBoxContainer">
        <NumBox num={numArr.normal} type={NORMAL} />
        <NumBox num={numArr.late} type={LATE} />
        <NumBox num={numArr.vacation} type={VACATION} />
        <NumBox num={numArr.absent} type={ABSENT} />
        <NumBox num={numArr.sick} type={SICK} />
      </div>
      <StyledForm onSubmit={handleSearch}>
        <input
          type="date"
          name="date"
          className="dateInput"
          value={date}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="word"
          className="textInput"
          value={word}
          onChange={handleInputChange}
        />
        <button type="submit">검색</button>
      </StyledForm>
      <Table page="attendance" headerArr={HEADER_ARR} dataArr={attendanceArr} />
    </Container>
  );
}

export default Attendance;
