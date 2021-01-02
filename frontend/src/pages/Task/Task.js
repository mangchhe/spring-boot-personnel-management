import React, { useState } from 'react';
import Block from '../../components/Block/Block.js'
import styles from './task.module.css'

function TaskInput(){
  const [inputs, setInput] = useState('')

  const onChange = (e) => {
    setInput(e.target.value)
  }
  return(
    <input value = {inputs} placeholder = '검색어를 입력하세요' onChange={onChange}/>
  )
}

function Task() {
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
        name: '업무4',
        start_date: '2020.08.01',
        end_date: '2020.09.01',
        department: '부서4',
        manager: '담당자4',
        staff: '직원들4',
        completed: true,
      },
  ]);
  return (
    <div className = {styles.container}>
      <TaskInput/>
      <Block datas = {datas} className = {styles.block}/>    
    </div>
  )
}

export default Task;
