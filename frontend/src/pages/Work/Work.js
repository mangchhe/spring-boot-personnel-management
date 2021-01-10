import React, { useEffect, useState } from 'react';
import Block from '../../components/Block/Block.js';
import styles from './work.module.css';
import axios from 'axios';
import WorkInput from './WorkInput';
import WorkModal from './WorkModal';

const Work = function () {
  const [input, setInput] = useState('');
  const [datas, setData] = useState([{ data: '' }]);
  const [option, setOption] = useState('workName');

  //로딩 및 에러처리
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [modal, setModal] = useState(false);

  useEffect(() => {
    const fetchusers = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await axios.get(
          `https://jsonplaceholder.typicode.com/users`, //'/work'
        );
        setData(response.data);
      } catch (e) {
        setError(e);
      }
      setLoading(false);
    };

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
    const fetchusers = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await axios.get(
          `/work?nameType=${option}&name=${input}`,
        );
        setData(response.data);
      } catch (e) {
        setError(e);
      }
      setLoading(false);
    };

    fetchusers();
  };

  //모달
  const modalOpen = () => {
    setModal(true);
  };

  const modalClose = () => {
    setModal(false);
  };

  const addWork = () => {
    setModal(true);
  };

  const selectDept = () => {
    setModal(false);
  };


  return (
    <div className={styles.container}>
      <WorkInput
        handleSubmit={handleSubmit}
        optionValue={option}
        handleSelectChange={handleSelectChange}
        input={input}
        handleInputChange={handleInputChange}
      />
      <button onClick={modalOpen} className={styles.addButton}>업무추가하기</button>
      <WorkModal modal={modal} addWork={addWork} selectDept={selectDept} modalClose={modalClose} />
      {option}
      {input}
      <Block searchResult={datas} className={styles.block} />
    </div>
  );
};

export default React.memo(Work);
