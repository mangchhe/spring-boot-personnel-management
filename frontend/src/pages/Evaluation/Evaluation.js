import React, { useEffect, useState, useRef } from 'react';
import Block from '../../components/Block/Block.js';
import styles from './eval.module.css';
import axios from 'axios';
import EvalInput from './EvalInput';
import EvalModal from './EvalModal';
import Pagination from '@material-ui/lab/Pagination';

const Evaluation = function () {
  const [page, setPage] = useState({
    currentPage: 1,
    totalPage: 1,
  });
  const [showPage, setShowPage] = useState(true);
  const [input, setInput] = useState('');
  const [datas, setData] = useState([]);
  const [option, setOption] = useState('workName');

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [evalBlockId, setEvalBlockId] = useState('');
  const [correctModal, setCorrectModal] = useState(false);
  const [modalInput, setModalInput] = useState({
    score: '',
    comment: '',
  });

  const [empLists, setEmpLists] = useState([]);
  const [selectedEmp, setSelectedEmp] = useState('');

  const { score, comment } = modalInput;

  const fetchPageData = () => {
    axios.get(`/evaluation?nameType=workName&name=&page=1`).then((response) => {
      const { pageResultDTO } = response.data;
      setPage({ ...page, totalPage: pageResultDTO.totalPage });
    });
  };

  const fetchDatas = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.get(
        `/evaluation?nameType=workName&name=&page=${page.currentPage}`,
      );
      setData(response.data.list);
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  const handlePageChange = (e) => {
    const currentPage = parseInt(e.target.textContent);
    setPage({ ...page, currentPage: currentPage });
  };

  const fetchSearchResult = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.get(
        `/evaluation?nameType=${option}&name=${input}&page=1`,
      );
      setData(response.data.list);
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  const fetchEmp = async () => {
    try {
      const response = await axios.get(`/evaluation/${evalBlockId}/edit`);
      const responsedEmp = response.data.evalListPerWork;
      setEmpLists(responsedEmp);
      setSelectedEmp(responsedEmp[0].evalId);
    } catch (e) {
      console.log('직원데이터를 가져오는데 문제가 있습니다.');
    }
  };

  const mounted = useRef();

  useEffect(() => {
    if (!mounted.current) {
      mounted.current = true;
    } else {
      fetchEmp();
    }
  }, [evalBlockId]);

  useEffect(() => {
    fetchPageData();
  }, []);

  useEffect(() => {
    fetchDatas();
  }, [page]);

  if (loading) return <div>Loading..</div>;
  if (error) return <div>Error Occurred</div>;

  const handleSelectChange = (e) => {
    setOption(e.target.value);
  };

  const handleInputChange = (e) => {
    setInput(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!input) {
      alert(`검색어를 입력해주세요`);
      return;
    }

    fetchSearchResult();
    setShowPage(false);
  };

  const correctModalOpen = async (e) => {
    let getId = e.target.closest('div').id;
    setEvalBlockId(getId);
    setCorrectModal(true);
  };

  const correctModalClose = () => {
    setCorrectModal(false);
  };

  const handleModalInput = (e) => {
    const { value, name } = e.target;
    setModalInput({
      ...modalInput,
      [name]: value,
    });
  };

  const handleSelectEmp = (e) => {
    setSelectedEmp(e.target.value);
  };

  const correctEval = (e) => {
    e.preventDefault();
    try {
      axios
        .put(`evaluation/${evalBlockId}/edit`, {
          evalId: selectedEmp,
          comment: comment,
          score: score,
        })
        .then(() => {
          setShowPage(true);
          fetchDatas();
          setCorrectModal(false);
        });
    } catch (e) {
      console.log('업무를 수정하는데 문제가 있습니다.');
    }
  };

  return (
    <div className={styles.container}>
      <EvalInput
        handleSubmit={handleSubmit}
        optionValue={option}
        handleSelectChange={handleSelectChange}
        input={input}
        handleInputChange={handleInputChange}
      />
      <Block
        page="evaluation"
        searchResult={datas}
        modalOpen={correctModalOpen}
        className={styles.block}
      />
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
      <EvalModal
        showModal={correctModal}
        handleModalInput={handleModalInput}
        handleSelectEmp={handleSelectEmp}
        handleWork={correctEval}
        selectedEmp={selectedEmp}
        score={score}
        comment={comment}
        handleModalClose={correctModalClose}
        empLists={empLists}
        buttonText="수정"
      />
    </div>
  );
};

export default React.memo(Evaluation);
