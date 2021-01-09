import React, {useEffect, useState } from 'react';
import Block from '../../components/Block/Block.js';
import styles from './work.module.css';
import axios from 'axios';

const WorkInput = function ({handleSubmit, optionValue, handleSelectChange, input, handleInputChange}) {
  return (
    <div className={styles.searchBox}>
      <form onSubmit = {handleSubmit}>
        <select value = {optionValue} onChange = {handleSelectChange} className={styles.selectBox}>
          <option value="name">업무명</option>
          <option value="staff">직원이름</option>
          <option value="department">부서</option>
        </select>
        <input
          value={input}
          placeholder="검색어를 입력하세요"
          onChange={handleInputChange}
          className={styles.input}
        />
        <button type ='submit' className={styles.searchButton}>
          검색하기
        </button>
      </form>
    </div>
  );
};

// [
//   {
//     id: 1,
//     name: '업무1',
//     start_date: '2020.08.01',
//     end_date: '2020.09.01',
//     department: '부서1',
//     manager: '담당자1',
//     staff: '직원들1',
//     completed: false,
//   },
//   {
//     id: 2,
//     name: '업무2',
//     start_date: '2020.08.01',
//     end_date: '2020.09.01',
//     department: '부서2',
//     manager: '담당자2',
//     staff: '직원들2',
//     completed: false,
//   },
//   {
//     id: 3,
//     name: '업무3',
//     start_date: '2020.08.01',
//     end_date: '2020.09.01',
//     department: '부서3',
//     manager: '담당자3',
//     staff: '직원들3',
//     completed: true,
//   },
//   {
//     id: 4,
//     name: '업무3',
//     start_date: '2020.08.01',
//     end_date: '2020.09.01',
//     department: '부서4',
//     manager: '담당자4',
//     staff: '직원들4',
//     completed: true,
//   },
// ]


const Work = function () {
  const [input, setInput] = useState('');
  const [datas, setData] = useState([{data: ''}]);
  const [option, setOption] = useState('name');
  
  //로딩 및 에러처리
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  
  useEffect(() => {
    const fetchusers = async() => {
      try {
        setLoading(true)
        setError(null)
        const response = await axios.get(
          `https://jsonplaceholder.typicode.com/users`,
        );
        setData(response.data);
      } catch (e) {
        setError(e);
      }
      setLoading(false)
    }

    fetchusers()
  }, []);


  if (loading) return <div>Loading..</div>;
  if (error) return <div>Error Occurred</div>;

  const handleSelectChange = (e) => {
    setOption(e.target.value);
  };

  const handleInputChange = (e) => {
    setInput(e.target.value);
  };

  const handleSubmit = async(e) => {
    e.preventDefault()
    if(!input){
      return
    }
  const fetchusers = async() => {
    try {
        setLoading(true)
        setError(null)
        const response = await axios.get(
          `https://jsonplaceholder.typicode.com/users?${option}=${input}`,
        );
        setData(response.data);
      } catch (e) {
        setError(e);
      }
      setLoading(false)
    }

    fetchusers()
  }
  
  return (
    <div className={styles.container}>
      <WorkInput
        handleSubmit = {handleSubmit}
        optionValue = {option}
        handleSelectChange ={handleSelectChange}
        input={input}
        handleInputChange={handleInputChange}
      />
      {option}{input}
      <Block searchResult={datas} className={styles.block} />
    </div>
  );
};

export default React.memo(Work);
