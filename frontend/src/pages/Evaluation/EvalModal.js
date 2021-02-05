import React from 'react';
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
import styles from './eval.module.css';

const EmpOption = ({ data }) => {
  return <option value={data.evalId}>{data.empName}</option>;
};

const Button = ({ buttonText }) => {
  return (
    <div className={styles.submitButtonContainer}>
      <button type="submit" className={styles.modalSubmitButton}>
        {buttonText}
      </button>
    </div>
  );
};

function EvalModal({
  showModal,
  handleModalInput,
  handleSelectEmp,
  handleWork,
  selectedEmp,
  score,
  comment,
  handleModalClose,
  empLists,
  buttonText,
}) {
  const customStyles = {
    height: 'auto',
    bottom: 'auto',
    top: '30%',
  };
  return (
    <>
      <Rodal
        visible={showModal}
        onClose={handleModalClose}
        customStyles={customStyles}
      >
        <form onSubmit={handleWork}>
          <label className={styles.label}>직원</label>
          <div>
            <select
              value={selectedEmp}
              onChange={handleSelectEmp}
              className={styles.modalSelect}
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
          </div>
          <label className={styles.label}>점수</label>
          <div>
            <input
              value={score}
              name="score"
              onChange={handleModalInput}
              className={styles.addInput}
            />
          </div>
          <label className={styles.label}>코멘트</label>
          <div>
            <input
              value={comment}
              name="comment"
              onChange={handleModalInput}
              className={styles.addInput}
            />
          </div>
          <Button buttonText={buttonText}></Button>
        </form>
      </Rodal>
    </>
  );
}
export default EvalModal;
