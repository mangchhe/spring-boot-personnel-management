import React from 'react';
import {NavLink} from 'react-router-dom'
import styles from './sidebar.module.css'

function SideList({listitems, category}){
  return(
    <ul className={styles.ul}>
      <p className={styles.p}>{category}</p>
      {listitems.map((listitem, index) => <li className = {styles.list} key={index}><NavLink to={listitem.url} activeClassName = {styles.activeStyle}>{listitem.name}</NavLink></li>)}
    </ul>
  )
}
function Sidebar() {
  const home = [
    {
      name: '근태관리',
      url: "/attendance"
    },
    {
      name: '급여관리',
      url: "/payroll"
    },
    {
      name: '업무관리',
      url: "/task"
    },
    {
      name: '성과관리',
      url: "/performance"
    },
  ]
  const employee = [
    {
      name: '직원',
      url: "/personnelInformation"
    },
    {
      name: '인사현황',
      url: "/personnelStatus"
    }
  ]
  const settings = [
    {
      name: '프로필',
      url: "/profile"
    }
  ]
  return(
    <div className={styles.sidebar}>
      <SideList category = '홈' listitems={home}></SideList>
      <SideList category = '인사관리' listitems={employee}></SideList>
      <SideList category = '설정' listitems={settings}></SideList>
    </div>
  );
}

export default Sidebar;
