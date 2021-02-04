import React, { useEffect, useState } from 'react';
import { Route, Redirect } from 'react-router-dom';
import axios from 'axios';

const AuthRoute = ({ component: Component, ...rest }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(null);

  useEffect(() => {
    let token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = `${token}`;
      setIsAuthenticated(true);
    } else {
      setIsAuthenticated(false);
    }
  }, []);
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
