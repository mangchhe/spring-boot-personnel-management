import React, { useState } from 'react';
import styled from 'styled-components';
import { withRouter } from 'react-router-dom';
import LoginInput from './LoginInput';
import LoginButton from './LoginButton';
import axios from 'axios';

const LoginContainer = styled.div`
  width: 500px;
  margin: 200px auto;
  display: flex;
  flex-direction: column;
  text-align: center;
`;

const StyledSpan = styled.span`
  color: #a6a6a6;
  font-weight: 300;
  font-size: 30px;
  margin-bottom: 30px;
`;

function Login({ history }) {
  const [inputs, setInput] = useState({
    userId: '',
    userPw: '',
  });

  const { userId, userPw } = inputs;

  const onChange = (e) => {
    const { value, name } = e.target;
    setInput({
      ...inputs,
      [name]: value,
    });
  };

  const loginUser = async () => {
    const loginData = {
      mnEmail: 'test10@okky.kr',
      mnPw: '1234',
    };
    alert(`"test10@okky.kr",'1234'`);
    axios
      .post('/login', loginData)
      .then((response) => {
        console.log(response);
        if (response.headers.authorization) {
          localStorage.setItem('token', response.headers.authorization);
          history.push('./attendance');
        } else {
          alert(`로그인이 완료되지 않았습니다.`);
        }
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const onSubmit = (e) => {
    e.preventDefault();
    // if (!userId || !userPw) {
    //   alert('필수 항목을 작성하세요!');
    // } else {
    loginUser();
    // }
  };

  return (
    <LoginContainer>
      <StyledSpan>인사 관리 시스템</StyledSpan>
      <form onSubmit={onSubmit}>
        <LoginInput
          name="userId"
          placeholder="아이디"
          onChange={onChange}
          value={userId}
        />
        <LoginInput
          name="userPw"
          placeholder="비밀번호"
          onChange={onChange}
          value={userPw}
        />
        <LoginButton type="submit">로그인</LoginButton>
      </form>
    </LoginContainer>
  );
}

export default withRouter(Login);
