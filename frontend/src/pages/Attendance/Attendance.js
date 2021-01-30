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
import DuplicateModal from './DuplicateModal';
import Pagination from '@material-ui/lab/Pagination';

const Container = styled.div`
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

const PaginationContainer = styled.div`
  text-align: center;
  margin-top: 5em;
  margin-bottom: 3em;
  display: flex;
  justify-content: center;
`;

const numBoxNames = [
  { name: 'onCnt', type: ON },
  { name: 'offCnt', type: OFF },
  { name: 'absenceCnt', type: ABSENCE },
  { name: 'lateCnt', type: LATE },
  { name: 'vacationCnt', type: VACATION },
  { name: 'sickCnt', type: SICK },
];

function Attendance({
  date,
  word,
  handleAllDates,
  handleSearch,
  handleInputChange,
  statusArr,
  attendanceArr,
  handleStatus,
  showModal,
  handleModalClose,
  duplicateArr,
  selectedId,
  handleSelectedIdChange,
  handleChoose,
  pageInfo,

  handlePageChange,
  showPagination,
}) {
  return (
    <Container>
      <div className="numBoxContainer">
        {numBoxNames.map((s) => (
          <NumBox
            num={statusArr[s.name]}
            type={s.type}
            handleStatus={handleStatus}
            key={s.type}
          />
        ))}
      </div>
      <StyledForm onSubmit={handleSearch}>
        <button type="button" onClick={handleAllDates}>
          모든 날짜
        </button>
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
      {showPagination && (
        <PaginationContainer>
          <Pagination
            count={pageInfo.totalPage}
            page={pageInfo.currentPage}
            onChange={handlePageChange}
            hidePrevButton
            hideNextButton
          />
        </PaginationContainer>
      )}
      <DuplicateModal
        showModal={showModal}
        handleModalClose={handleModalClose}
        duplicateArr={duplicateArr}
        selectedId={selectedId}
        handleSelectedIdChange={handleSelectedIdChange}
        handleChoose={handleChoose}
      />
    </Container>
  );
}

export default Attendance;
