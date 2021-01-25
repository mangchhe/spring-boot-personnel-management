import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`
  /* 공통 스타일 */
  width: 90%;
  color: white;
  cursor: pointer;
  text-align: center;

  /* 크기 */
  height: 40px;
  font-size: 18px;
  font-weight: 200;

  /* 색상 */
  background: #2d93f0;

  margin-top: 6px;
`;

const LoginButton = function ({ type, children }) {
  return <StyledButton type={type}>{children}</StyledButton>;
};

export default LoginButton;
