import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const EvalBlockElement = React.memo(function ({ data, modalOpen }) {
  return (
    <div id={data.evalInfo.evalBlockId} className={styles.blockElements}>
      <p className={styles.p}>
        {data.evalInfo.deptName}:{data.evalInfo.workName}
      </p>
      {data.evalListPerWork.map((evalPerWork, index) => {
        return (
          <p className={styles.span} key={index}>
            {evalPerWork.empName}: {evalPerWork.score}, {evalPerWork.comment}
          </p>
        );
      })}
      <button onClick={modalOpen} className={styles.button}>
        <i>
          <FaPen />
        </i>
      </button>
    </div>
  );
});

export default EvalBlockElement;
