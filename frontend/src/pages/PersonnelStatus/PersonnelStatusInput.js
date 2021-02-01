import React from 'react';
import styles from './personnelStatus.module.css';

const PersonnelStatusInput = function ({
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
          <option value="employee">이름</option>
          <option value="position">직급</option>
          <option value="department">부서</option>
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

export default PersonnelStatusInput;
