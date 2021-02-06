import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';

const Container = styled.div`
  margin: 0 20%;
`;

const Circle = styled.div`
  border-radius: 50%;
  width: 150px;
  height: 150px;
  background-color: ${({ theme }) => theme.palette['blue']};
  color: white;
  text-align: center;
  margin: 0 auto;
  margin-bottom: 5em;
  p {
    padding-top: 20px;
    font-size: 70px;
  }
`;

const Row = styled.div`
  display: flex;
  margin-bottom: 1em;
`;

const Label = styled.p`
  flex: 1;
  color: ${({ theme }) => theme.palette['blue']};
`;

const Info = styled.div`
  flex: 2;
`;

const FormRow = styled.div`
  display: flex;
  margin-bottom: 1em;

  label {
    flex: 2;
  }

  input {
    flex: 3;
  }
`;

const Button = styled.button`
  margin-top: 1em;
  float: right;
  width: 100px;
  padding: 0.4em;
  border: none;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.palette['blue']};
  color: white;
  outline: none;
  cursor: pointer;
  box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
`;

function Profile() {
  const [userInfo, setUserInfo] = useState({
    accessArea: '',
    currentAccessDate: '',
    mnEmail: '',
  });
  const [passwordInfo, setPasswordInfo] = useState({
    curPw: '',
    newPw: '',
    newPwCheck: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPasswordInfo({ ...passwordInfo, [name]: value });
  };

  const fetchUserInfo = () => {
    axios.get('/profile').then((res) => {
      setUserInfo({ ...res.data });
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .put(`/profile`, passwordInfo)
      .then((res) => {
        alert(res.data);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    fetchUserInfo();
  }, []);

  return (
    <Container>
      <Circle>
        <p>{userInfo.mnEmail.toUpperCase()[0]}</p>
      </Circle>
      <Row>
        <Label>이메일</Label>
        <Info>{userInfo.mnEmail}</Info>
      </Row>
      <Row>
        <Label>접속날짜</Label>
        <Info>{userInfo.currentAccessDate}</Info>
      </Row>
      <Row>
        <Label>접속지역</Label>
        <Info>{userInfo.accessArea}</Info>
      </Row>
      {/* <Row>
        <Label>비밀번호 변경</Label>
        <Info>
          <form onSubmit={handleSubmit}>
            <FormRow>
              <label>현재 비밀번호:</label>
              <input
                type="password"
                name="curPw"
                value={passwordInfo.curPw}
                onChange={handleInputChange}
              ></input>
            </FormRow>
            <FormRow>
              <label>새로운 비밀번호:</label>
              <input
                type="password"
                name="newPw"
                value={passwordInfo.newPw}
                onChange={handleInputChange}
              ></input>
            </FormRow>
            <FormRow>
              <label>새로운 비밀번호 확인:</label>
              <input
                type="password"
                name="newPwCheck"
                value={passwordInfo.newPwCheck}
                onChange={handleInputChange}
              ></input>
            </FormRow>
            <Button type="submit">수정</Button>
          </form>
        </Info>
      </Row> */}
    </Container>
  );
}

export default Profile;
