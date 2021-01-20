import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const WorkBlockElement = React.memo(function ({ data, modalOpen }) {
  return (
    <div
      id={data.workId}
      className={data.workStatus ? styles.blockElements : styles.blockCompleted}
    >
      <p className={styles.title}>[{data.workName}]</p>
      <p className={styles.p}>
        <span>- 업무기간: {data.workStartDate} ~ </span>
        <span>{data.workEndDate}</span>
      </p>
      <p className={styles.p}>- 담당부서: {data.deptName}</p>
      <p className={styles.p}>- 담당자: {data.workChargeName}</p>
      <p className={styles.p}>- 담당 직원:{data.employees}</p>
      {data.workStatus ? (
        <button onClick={modalOpen} className={styles.button}>
          <i>
            <FaPen />
          </i>
        </button>
      ) : null}
    </div>
  );
});

export default WorkBlockElement;
