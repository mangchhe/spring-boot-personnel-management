import React from 'react';
import {
  FaCheckCircle,
  FaExclamationTriangle,
  FaMinusCircle,
} from 'react-icons/fa';
import { NORMAL, LATE } from './Constants';
import styled, { css } from 'styled-components';

const colorStyles = css`
  ${({ theme, type }) => {
    const color = type === NORMAL ? 'blue' : type === LATE ? 'red' : 'grey';
    return css`
      color: ${theme.palette[color]};
    `;
  }}
`;

const StyledBox = styled.div`
  display: flex;
  svg {
    font-size: 3em;
    ${colorStyles}
  }
`;

const Text = styled.div`
  margin-left: 2em;
  p {
    margin: 0;
    font-size: 14px;
  }
  .title {
    font-size: 16px;
  }
  .num {
    font-size: 24px;
    ${colorStyles}
  }
`;

function NumBox({ num, type }) {
  return (
    <StyledBox type={type}>
      <div>
        {type === NORMAL ? (
          <FaCheckCircle />
        ) : type === LATE ? (
          <FaExclamationTriangle />
        ) : (
          <FaMinusCircle />
        )}
      </div>
      <Text type={type}>
        <p className="title">{type}</p>
        <p>
          <span className="num">{num}</span> 건
        </p>
      </Text>
    </StyledBox>
  );
}

export default NumBox;
