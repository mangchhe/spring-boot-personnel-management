import React, { useEffect, useState } from 'react';
import Block from '../../components/Block/Block.js';
import styles from './eval.module.css';
import axios from 'axios';
import EvalInput from './EvalInput';
import EvalModal from './EvalModal';

const Evaluation = function () {
  const [input, setInput] = useState('');
  const [datas, setData] = useState([{ data: '' }]);
  const [option, setOption] = useState('');

  //로딩 및 에러처리
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [evalBlockId, setEvalBlockId] = useState('');
  const [correctModal, setCorrectModal] = useState(false);
  const [modalInput, setModalInput] = useState({
    score: '',
    comment: '',
  });

  const [empLists, setEmpLists] = useState([{ emp: '' }]);
  const [selectedEmp, setSelectedEmp] = useState('1');

  const { score, comment } = modalInput;

  const fetchusers = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.get(`/evaluation?nameType=workName&name=`);
      setData(response.data);
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  //수정
  const fetchEmp = async () => {
    try {
      const response = await axios.get(`/evaluation/${evalBlockId}/edit`);
      setEmpLists(response.data.evalPerWorkList);
    } catch (e) {
      console.log('직원데이터를 가져오는데 문제가 있습니다.');
    }
  };

  useEffect(() => {
    fetchusers();
  }, []);

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
      return;
    }
    const fetchSearchResult = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await axios.get(
          `/evaluation?nameType=${option}&name=${input}`,
        );
        setData(response.data);
      } catch (e) {
        setError(e);
      }
      setLoading(false);
    };

    fetchSearchResult();
  };

  const correctModalOpen = (e) => {
    let getId = e.target.closest('div').id;
    setEvalBlockId(getId);
    fetchEmp();
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
        .post(`evaluation/${evalBlockId}/edit`, {
          evalId: selectedEmp,
          comment: comment,
          score: score,
        })
        .then(() => {
          fetchusers();
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
      <EvalModal
        modal={correctModal}
        handleModalInput={handleModalInput}
        handleSelectEmp={handleSelectEmp}
        handleWork={correctEval}
        selectedEmp={selectedEmp}
        score={score}
        comment={comment}
        modalClose={correctModalClose}
        empLists={empLists}
        buttonText="업무수정"
      />
    </div>
  );
};

export default React.memo(Evaluation);
