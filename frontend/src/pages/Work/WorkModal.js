import React from 'react';
import Modal from 'react-modal';
import styles from './work.module.css';

const DeptOption = ({ data }) => {
  return <option value={data.deptId}>{data.deptName}</option>;
};

const Button = ({ buttonText }) => {
  return (
    <button type="submit" className={styles.modalSubmitButton}>
      {buttonText}
    </button>
  );
};

Modal.setAppElement('#modal_root');

function WorkModal({
  modal,
  handleModalInput,
  handleSelectDept,
  handleWork,
  selectedDept,
  workName,
  workCharger,
  workStartDate,
  workEndDate,
  modalClose,
  deptLists,
  buttonText,
}) {
  return (
    <>
      <Modal
        isOpen={modal}
        className={styles.modal}
        overlayClassName={styles.overlay}
      >
        <form onSubmit={handleWork}>
          <label className={styles.label}>
            업무명
            <input
              className={styles.addInput}
              onChange={handleModalInput}
              value={workName}
              name="workName"
            />
          </label>
          <label className={styles.label}>
            부서
            <select
              value={selectedDept}
              onChange={handleSelectDept}
              className={styles.deptSelect}
            >
              {deptLists.map((deptList, index) => {
                return (
                  <DeptOption
                    key={index}
                    data={deptList}
                    selectedDept={selectedDept}
                    handleSelectDept={handleSelectDept}
                  />
                );
              })}
            </select>
          </label>
          <label className={styles.label}>
            담당자
            <input
              value={workCharger}
              name="workCharger"
              onChange={handleModalInput}
              className={styles.addInput}
            />
          </label>
          <label className={styles.label}>
            시작일자
            <input
              value={workStartDate}
              name="workStartDate"
              onChange={handleModalInput}
              className={styles.addInput}
              type="date"
            />
          </label>
          <label className={styles.label}>
            종료일자
            <input
              value={workEndDate}
              name="workEndDate"
              onChange={handleModalInput}
              className={styles.addInput}
              type="date"
            />
          </label>
          <Button buttonText={buttonText}></Button>
        </form>
        <button onClick={modalClose} className={styles.modalCloseButton}>
          닫기
        </button>
      </Modal>
    </>
  );
}
export default WorkModal;
