import React from 'react';
import WorkBlockElement from './WorkBlockElement';
import EvalBlockElement from './EvalBlockElement';

const Block = function ({ page, searchResult, modalOpen }) {
  return (
    <>
      {searchResult &&
        searchResult.map((data, index) => {
          return page === 'work' ? (
            <WorkBlockElement data={data} modalOpen={modalOpen} key={index} />
          ) : page === 'evaluation' ? (
            <EvalBlockElement data={data} modalOpen={modalOpen} key={index} />
          ) : null;
        })}
    </>
  );
};

export default React.memo(Block);
