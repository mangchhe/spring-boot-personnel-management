import React, { useEffect, useState, useRef } from 'react';
import Block from '../../components/Block/Block.js';
import styles from './eval.module.css';
import axios from 'axios';
import EvalInput from './EvalInput';
import EvalModal from './EvalModal';

const Evaluation = function () {
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

  const fetchDatas = async () => {
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

  const fetchEmp = async () => {
    try {
      const response = await axios.get(`/evaluation/${evalBlockId}/edit`);
      const responsedEmp = response.data.evalPerWorkList;
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
    fetchDatas();
  }, []);

  useEffect(() => {}, [empLists]);

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

    fetchSearchResult();
    console.log(datas);
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
