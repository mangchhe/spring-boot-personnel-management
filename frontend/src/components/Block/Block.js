import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const BlockElements = React.memo(function ({ data, modalOpen }) {
  return (
    <div
      id={data.workId}
      className={data.workStatus ? styles.blockElements : styles.blockCompleted}
    >
      <p className={styles.p}>업무명: {data.workName}</p>
      <p className={styles.p}>
        <span>업무기간: {data.workStartDate} ~ </span>
        <span>{data.workEndDate}</span>
      </p>
      <p className={styles.p}>담당부서: {data.deptName}</p>
      <p className={styles.p}>담당자: {data.workChargeName}</p>
      <p className={styles.p}>업무 담당 직원:{data.employees}</p>
      {data.workStatus ? (
        <button onClick={modalOpen}>
          <i className={styles.icon}>
            <FaPen />
          </i>
        </button>
      ) : null}
    </div>
  );
});

const Block = function ({ searchResult, modalOpen }) {
  if (searchResult.length === 0) {
    return <div>Loading...</div>;
  }
  return (
    <>
      {searchResult.map((data, index) => {
        return <BlockElements data={data} modalOpen={modalOpen} key={index} />;
      })}
    </>
  );
};

export default React.memo(Block);
