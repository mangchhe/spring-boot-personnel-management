import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const BlockElements = React.memo(function ({ data, modalOpen }) {
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
