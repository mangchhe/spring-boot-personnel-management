import React from 'react';
import {
  FaCheckCircle,
  FaExclamationTriangle,
  FaMinusCircle,
} from 'react-icons/fa';
import { ON, OFF, ABSENCE, LATE } from './Constants';
import styled, { css } from 'styled-components';

const colorStyles = css`
  ${({ theme, type }) => {
    const color = [ON, OFF].includes(type)
      ? 'blue'
      : [ABSENCE, LATE].includes(type)
      ? 'red'
      : 'grey';
    return css`
      color: ${theme.palette[color]};
    `;
  }}
`;

const StyledBox = styled.div`
  display: flex;
  svg {
    font-size: 2.8em;
    ${colorStyles}
  }
`;

const Text = styled.div`
  margin-left: 1.2em;
  p {
    margin: 0;
    font-size: 13px;
  }
  .title {
    font-size: 15px;
  }
  .num {
    font-size: 23px;
    ${colorStyles}
  }
`;

function NumBox({ num, type }) {
  return (
    <StyledBox type={type}>
      <div>
        {[ON, OFF].includes(type) ? (
          <FaCheckCircle />
        ) : [ABSENCE, LATE].includes(type) ? (
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
