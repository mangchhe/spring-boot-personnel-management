import React from 'react';
import Modal from 'react-modal';
import styles from './work.module.css';

function WorkModal({
  modal,
  handleAddInput,
  handleSelectDept,
  addWork,
  selectedDept,
  workName,
  workCharger,
  workStartDate,
  workEndDate,
  modalClose,
}) {
  return (
    <>
      <Modal
        isOpen={modal}
        className={styles.modal}
        overlayClassName={styles.overlay}
      >
        <form onSubmit={addWork}>
          <label className={styles.label}>
            업무명:
            <input
              className={styles.addInput}
              onChange={handleAddInput}
              value={workName}
              name="workName"
            />
          </label>
          <label className={styles.label}>
            부서:
            <select value={selectedDept} onChange={handleSelectDept}>
              <option value="dept1">부서1</option>
              <option value="dept2">부서2</option>
              <option value="dept3">부서3</option>
            </select>
          </label>
          <label className={styles.label}>
            담당자:
            <input
              value={workCharger}
              name="workCharger"
              onChange={handleAddInput}
              className={styles.addInput}
            />
          </label>
          <label className={styles.label}>
            시작:
            <input
              value={workStartDate}
              name="workStartDate"
              onChange={handleAddInput}
              className={styles.addInput}
              type="date"
            />
          </label>
          <label className={styles.label}>
            종료:
            <input
              value={workEndDate}
              name="workEndDate"
              onChange={handleAddInput}
              className={styles.addInput}
              type="date"
            />
          </label>
          <button type="submit">추가하기</button>
        </form>
        <button onClick={modalClose}>x</button>
      </Modal>
    </>
  );
}
export default WorkModal;
