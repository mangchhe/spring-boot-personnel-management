import React from 'react';
import styles from './block.module.css';
import { FaPen } from 'react-icons/fa';

function BlockElements({ data, is_completed }) {
  return (
    <div
      className={!is_completed ? styles.blockElements : styles.blockCompleted}
    >
      <p>
        {data.name}
      </p>
      <p>
        <span>{data.start_date} ~ </span>
        <span>{data.end_date}</span>
      </p>
      <p>{data.department}</p>
      <p>{data.manager}</p>
      <p>{data.staff}</p>
      {!is_completed ? <i className={styles.icon}><FaPen /></i> : null}
    </div>
  );
}

function Block({datas}) {
  return (
    <>
      {datas.map((data) => {
        return (
          <BlockElements
            data={data}
            is_completed={data.completed}
            key={data.id}
          />
        );
      })}
    </>
  );
}

export default Block;
