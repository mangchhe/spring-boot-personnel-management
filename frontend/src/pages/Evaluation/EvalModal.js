import React from 'react';
import Modal from 'react-modal';
import styles from './eval.module.css';

const EmpOption = ({ data }) => {
  return <option value={data.evalId}>{data.empName}</option>;
};

const Button = ({ buttonText }) => {
  return (
    <button type="submit" className={styles.modalSubmitButton}>
      {buttonText}
    </button>
  );
};

Modal.setAppElement('#modal_root');

function EvalModal({
  modal,
  handleModalInput,
  handleSelectEmp,
  handleWork,
  selectedEmp,
  score,
  comment,
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
            직원
            <select
              value={selectedEmp}
              onChange={handleSelectEmp}
              className={styles.empSelect}
            >
              {empLists &&
                empLists.map((empList, index) => {
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
            점수
            <input
              value={score}
              name="score"
              onChange={handleModalInput}
              className={styles.addInput}
            />
          </label>
          <label className={styles.label}>
            코멘트
            <input
              value={comment}
              name="comment"
              onChange={handleModalInput}
              className={styles.addInput}
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
export default EvalModal;
