import React from 'react';
import Modal from 'react-modal';
import styles from './eval.module.css';

const EmpOption = ({ data }) => {
  return <option value={data.evalId}>{data.empName}</option>;
};

const Button = ({ buttonText }) => {
  return (
    <button type="submit" className={styles.modalAddButton}>
      {buttonText}
    </button>
  );
};

Modal.setAppElement('#modal_root');

function WorkModal({
  modal,
  handleModalInput,
  handleSelectEmp,
  handleWork,
  selectedEmp,
  workName,
  workCharger,
  workStartDate,
  workEndDate,
  modalClose,
  empLists,
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
            직원
            <select
              value={selectedEmp}
              onChange={handleSelectEmp}
              className={styles.empSelect}
            >
              {empLists.map((empList, index) => {
                return (
                  <EmpOption
                    key={index}
                    data={empList}
                    selectedEmp={selectedEmp}
                    handleSelectDept={handleSelectEmp}
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
