import React from 'react';
import Table from '../../components/Table/Table';
import NumBox from './NumBox';
import {
  ON,
  OFF,
  ABSENCE,
  LATE,
  VACATION,
  SICK,
  HEADER_ARR,
} from './Constants';
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
    margin-left: 2em;
  }
  .textInput {
    flex: 3;
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
  date,
  word,
  handleAllDates,
  handleSearch,
  handleInputChange,
  statusArr,
  attendanceArr,
}) {
  return (
    <Container>
      <div className="numBoxContainer">
        <NumBox num={statusArr.onCnt} type={ON} />
        <NumBox num={statusArr.offCnt} type={OFF} />
        <NumBox num={statusArr.absenceCnt} type={ABSENCE} />
        <NumBox num={statusArr.lateCnt} type={LATE} />
        <NumBox num={statusArr.vacationCnt} type={VACATION} />
        <NumBox num={statusArr.sickCnt} type={SICK} />
      </div>
      <StyledForm onSubmit={handleSearch}>
        <button onClick={handleAllDates}>모든 날짜</button>
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
