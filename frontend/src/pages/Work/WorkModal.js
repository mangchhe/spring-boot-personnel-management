import React from 'react';
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
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

function WorkModal({
  showModal,
  handleModalInput,
  handleSelectDept,
  handleWork,
  selectedDept,
  workName,
  workCharger,
  workStartDate,
  workEndDate,
  handleModalClose,
  deptLists,
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
              {deptLists &&
                deptLists.map((deptList, index) => {
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
      </Rodal>
    </>
  );
}
export default WorkModal;
