import React, { useEffect, useState } from 'react';
import { Route, Redirect } from 'react-router-dom';
import axios from 'axios';

const AuthRoute = ({ component: Component, ...rest }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(null);
  const [token, setToken] = useState(null);

  useEffect(() => {
    let token = localStorage.getItem('token');
    setToken(token);
    if (token) {
      axios.defaults.headers.common['Authorization'] = `${token}`;
      setIsAuthenticated(true);
      setTimeout(() => {
        localStorage.removeItem('token');
        setToken(null);
      }, 30 * 60 * 1000);
    } else {
      setIsAuthenticated(false);
    }
  }, [token]);
  if (isAuthenticated === null) {
    return <></>;
  }

  return (
    <Route
      {...rest}
      render={(props) =>
        !isAuthenticated ? <Redirect to="/" /> : <Component {...props} />
      }
    />
  );
};

export default AuthRoute;
