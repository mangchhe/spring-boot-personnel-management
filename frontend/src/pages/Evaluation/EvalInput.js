import React from 'react';
import styles from './eval.module.css';

const WorkInput = function ({
  handleSubmit,
  optionValue,
  handleSelectChange,
  input,
  handleInputChange,
}) {
  return (
    <>
      <form onSubmit={handleSubmit}>
        <select
          value={optionValue}
          onChange={handleSelectChange}
          className={styles.selectBox}
        >
          <option value="workName">업무명</option>
          <option value="empName">직원이름</option>
          <option value="deptName">부서</option>
        </select>
        <input
          value={input}
          placeholder="검색어를 입력하세요"
          onChange={handleInputChange}
          className={styles.input}
        />
        <button type="submit" className={styles.searchButton}>
          검색하기
        </button>
      </form>
    </>
  );
};

export default WorkInput;
