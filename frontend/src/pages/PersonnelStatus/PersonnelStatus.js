import React from 'react';
import PersonnelStatusInput from './PersonnelStatusInput';
import Table from '../../components/Table/Table';
import styles from './personnelStatus.module.css';

const headerArr = [
  '이름',
  '현재부서',
  '이동할부서',
  '현재직급',
  '이동할직급',
  '발령일자',
];
function PersonnelStatus() {
  const addModalOpen = {};
  return (
    <div className={styles.container}>
      <PersonnelStatusInput />
      <div className={styles.addButtonWrap}>
        <button onClick={addModalOpen} className={styles.addButton}>
          발령
        </button>
      </div>
      <Table headerArr={headerArr} />
    </div>
  );
}

export default PersonnelStatus;
