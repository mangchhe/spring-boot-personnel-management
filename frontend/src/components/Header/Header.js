import React from 'react';
import { useLocation } from 'react-router-dom';
import styles from './header.module.css';
import { withRouter } from 'react-router-dom';

const convertLocation = {
  '/attendance': '근태 관리',
  '/payroll': '급여 관리',
  '/work': '업무 관리',
  '/evaluation': '성과 관리',
  '/personnelInformation': '인적사항 관리',
  '/personnelStatus': '인사 현황',
  '/profile': '프로필',
};

function Header({ history }) {
  const location = useLocation();
  const pathname = location.pathname;

  const handleLogout = () => {
    localStorage.removeItem('token');
  };

  return (
    <header className={styles.header}>
      <p className={styles.title}>{convertLocation[pathname]}</p>
      <button className={styles.button} onClick={handleLogout}>
        로그아웃
      </button>
    </header>
  );
}

export default withRouter(Header);
