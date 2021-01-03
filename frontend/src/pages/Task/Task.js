import React, { useCallback, useState } from 'react';
import Block from '../../components/Block/Block.js'
import styles from './task.module.css'

const TaskInput = function({input, handleChange, onSearch}){
  return(
    <div className={styles.searchBox}>
      <input value = {input} placeholder = '검색어를 입력하세요' onChange={handleChange} className = {styles.input}/>
      <button onClick = {onSearch} className={styles.searchButton}>검색하기</button>
    </div>
  )
}

const Task = function() {
  const [input, setInput] = useState('')
  const [datas, setData] = useState([
    {
      id: 1,
      name: '업무1',
      start_date: '2020.08.01',
      end_date: '2020.09.01',
      department: '부서1',
      manager: '담당자1',
      staff: '직원들1',
      completed: false,
    },
    {
      id: 2,
      name: '업무2',
      start_date: '2020.08.01',
      end_date: '2020.09.01',
      department: '부서2',
      manager: '담당자2',
      staff: '직원들2',
      completed: false,
    },
    {
      id: 3,
      name: '업무3',
      start_date: '2020.08.01',
      end_date: '2020.09.01',
      department: '부서3',
      manager: '담당자3',
      staff: '직원들3',
      completed: true,
    },
    {
      id: 4,
      name: '업무3',
      start_date: '2020.08.01',
      end_date: '2020.09.01',
      department: '부서4',
      manager: '담당자4',
      staff: '직원들4',
      completed: true,
    },
  ]);
  const [searchResult, setSearchResult] = useState(datas)
  
  const handleChange = (e) => {
    setInput(e.target.value)
  }

  const onSearch = useCallback(() => {
    setSearchResult(datas.filter(data => data.name.toLowerCase().includes(input.toLowerCase())))
    setInput('')
  }, [datas, input])
  
  return (
    <div className = {styles.container}>
      <TaskInput input={input} handleChange = {handleChange} onSearch = {onSearch}/>
      <Block searchResult = {searchResult} className = {styles.block} />    
    </div>
  )
}

export default React.memo(Task);
