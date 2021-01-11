import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const BlockElements = React.memo(function ({ data }) {
  return (
    <div
      className={data.workStatus ? styles.blockElements : styles.blockCompleted}
    >
      <p>{data.workName}</p>
      <p>
        <span>{data.workStartDate} ~ </span>
        <span>{data.workEndDate}</span>
      </p>
      <p>{data.deptName}</p>
      <p>{data.workChargeName}</p>
      <p>{data.employees}</p>
      {data.workStatus ? (
        <i className={styles.icon}>
          <FaPen />
        </i>
      ) : null}
    </div>
  );
});

const Block = function ({ searchResult }) {
  return (
    <>
      {searchResult.map((data) => {
        return <BlockElements data={data} key={data.workId} />;
      })}
    </>
  );
};

export default React.memo(Block);
