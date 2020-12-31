import React from 'react';
import { useLocation } from 'react-router-dom';
import styles from './header.module.css';

const convertLocation = {
  '/attendance': '근태 관리',
  '/payroll': '급여 관리',
  '/task': '업무 관리',
  '/performance': '성과 관리',
  '/personnelInformation': '인적사항 관리',
  '/personnelStatus': '인사 현황',
  '/profile': '프로필',
};

function Header() {
  const location = useLocation();
  const pathname = location.pathname;

  const handleLogout = () => {
    alert('Handle Logout');
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

export default Header;
