import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

const EvalBlockElement = React.memo(function ({ data, modalOpen }) {
  return (
    <div id={data.evalInfo.evalBlockId} className={styles.blockCompleted}>
      <p className={styles.p}>
        {data.evalInfo.deptName}:{data.evalInfo.workName}
      </p>
      {data.evalPerWorkList.map((evalPerWork) => {
        return (
          <p className={styles.p}>
            {evalPerWork.empName}:{evalPerWork.score}, {evalPerWork.comment}
          </p>
        );
      })}
      <button onClick={modalOpen}>
        <i className={styles.icon}>
          <FaPen />
        </i>
      </button>
    </div>
  );
});

export default EvalBlockElement;
