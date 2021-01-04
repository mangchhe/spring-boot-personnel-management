import React from 'react';
import Table from '../../components/Table/Table';
import { NORMAL, LATE, VACATION, ABSENT, SICK, HEADER_ARR } from './Constants';
import {
  FaCheckCircle,
  FaExclamationTriangle,
  FaMinusCircle,
} from 'react-icons/fa';
import styles from './attendance.module.css';

function Attendance({
  numArr,
  date,
  word,
  handleSearch,
  handleInputChange,
  attendanceArr,
}) {
  return (
    <div className={styles.container}>
      <div className={styles.numBoxContainer}>
        <NumBox num={numArr.normal} type={NORMAL} />
        <NumBox num={numArr.late} type={LATE} />
        <NumBox num={numArr.vacation} type={VACATION} />
        <NumBox num={numArr.absent} type={ABSENT} />
        <NumBox num={numArr.sick} type={SICK} />
      </div>
      <form onSubmit={handleSearch} className={styles.searchForm}>
        <input
          type="date"
          name="date"
          className={styles.dateInput}
          value={date}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="word"
          className={styles.textInput}
          value={word}
          onChange={handleInputChange}
        />
        <button type="submit" className={styles.searchButton}>
          검색
        </button>
      </form>
      <Table page="attendance" headerArr={HEADER_ARR} dataArr={attendanceArr} />
    </div>
  );
}

const NumBox = ({ num, type }) => {
  return (
    <div className={styles.numBox}>
      <div
        className={
          type === NORMAL
            ? styles.normal
            : type === LATE
            ? styles.late
            : styles.off
        }
      >
        {type === NORMAL ? (
          <FaCheckCircle />
        ) : type === LATE ? (
          <FaExclamationTriangle />
        ) : (
          <FaMinusCircle />
        )}
      </div>
      <div className={styles.text}>
        <p className={styles.title}>{type}</p>
        <p>
          <span
            className={`${styles.num} ${
              type === NORMAL
                ? styles.normal
                : type === LATE
                ? styles.late
                : styles.off
            }`}
          >
            {num}
          </span>{' '}
          건
        </p>
      </div>
    </div>
  );
};

export default Attendance;
