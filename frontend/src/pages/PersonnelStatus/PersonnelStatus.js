import React, { useEffect, useState } from 'react';
import PersonnelStatusInput from './PersonnelStatusInput';
import Table from '../../components/Table/Table';
import styles from './personnelStatus.module.css';
import axios from 'axios';
import Pagination from '@material-ui/lab/Pagination';
import PersonnelStatusModal from './PersonnelStatusModal';

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
  const [input, setInput] = useState('');
  const [option, setOption] = useState('employee');
  const [datas, setDatas] = useState([]);
  const [showPage, setShowPage] = useState(true);
  const [showModal, setShowModal] = useState(false);

  const fetchPersonnelStatus = () => {
    axios.get(`transfer?page=${page.currentPage}`).then((response) => {
      const { transList } = response.data;
      setDatas(transList);
    });
  };

  const fetchSearchResult = () => {
    axios.get(`transfer?${option}=${input}`).then((response) => {
      const { transList } = response.data;
      setDatas(transList);
    });
  };

  useEffect(() => {
    fetchPersonnelStatus();
  }, [page]);

  const handleSelectChange = (e) => {
    setOption(e.target.value);
  };
  const handleInputChange = (e) => {
    setInput(e.target.value);
  };
  const handleSubmit = (e) => {
    e.preventDefault();

    if (!input) {
      alert('검색어를 입력해주세요');
      return;
    }

    fetchSearchResult();
    setShowPage(false);
  };

  const handlePageChange = (e) => {
    const currentPage = parseInt(e.target.textContent);
    setPage({ ...page, currentPage: currentPage });
  };

  const handleModalOpen = () => {
    setShowModal(true);
  };
  const handleModalClose = () => {
    setShowModal(false);
  };

  return (
    <div className={styles.container}>
      <PersonnelStatusInput
        handleSubmit={handleSubmit}
        optionValue={option}
        handleSelectChange={handleSelectChange}
        input={input}
        handleInputChange={handleInputChange}
      />
      <div className={styles.addButtonWrap}>
        <button onClick={handleModalOpen} className={styles.addButton}>
          발령
        </button>
      </div>
      <Table page="personnelStatus" headerArr={HEADER_ARR} dataArr={datas} />
      <div className={styles.pagination}>
        {showPage && (
          <Pagination
            count={page.totalPage}
            page={page.currentPage}
            onChange={handlePageChange}
            hidePrevButton
            hideNextButton
          />
        )}
      </div>
      <PersonnelStatusModal
        showModal={showModal}
        handleModalClose={handleModalClose}
        fetchData={fetchPersonnelStatus}
      />
    </div>
  );
}

export default PersonnelStatus;
