import React, { useEffect, useState } from 'react';
import PersonnelStatusInput from './PersonnelStatusInput';
import Table from '../../components/Table/Table';
import styles from './personnelStatus.module.css';
import axios from 'axios';
import Pagination from '@material-ui/lab/Pagination';

const HEADER_ARR = [
  '이름',
  '현재부서',
  '이동할부서',
  '현재직급',
  '이동할직급',
  '승인일자',
  '발령일자',
  '발령현황',
];
function PersonnelStatus() {
  const [page, setPage] = useState({
    currentPage: 1,
    totalPage: 5,
  });
  const [datas, setDatas] = useState([]);

  const fetchPersonnelStatus = () => {
    axios.get(`transfer?page=${page.currentPage}`).then((response) => {
      const { transList } = response.data;
      setDatas(transList);
    });
  };
  const handlePageChange = (e) => {
    const currentPage = parseInt(e.target.textContent);
    setPage({ ...page, currentPage: currentPage });
  };
  useEffect(() => {
    fetchPersonnelStatus();
  }, [page]);

  return (
    <div className={styles.container}>
      <PersonnelStatusInput />
      <div className={styles.addButtonWrap}>
        <button className={styles.addButton}>발령</button>
      </div>
      <Table page="personnelStatus" headerArr={HEADER_ARR} dataArr={datas} />
      <div className={styles.pagination}>
        <Pagination
          count={page.totalPage}
          page={page.currentPage}
          onChange={handlePageChange}
          hidePrevButton
          hideNextButton
        />
      </div>
    </div>
  );
}

export default PersonnelStatus;
