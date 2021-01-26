import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Sidebar from './components/Sidebar/Sidebar';
import Header from './components/Header/Header';
import Login from './pages/Login/Login';
import AttendanceContainer from './pages/Attendance/AttendanceContainer';
import Payroll from './pages/Payroll/Payroll';
import Work from './pages/Work/Work';
import Evaluation from './pages/Evaluation/Evaluation';
import PersonnelInformationContainer from './pages/PersonnelInformation/PersonnelInformationContainer';
import PersonnelStatus from './pages/PersonnelStatus/PersonnelStatus';
import Profile from './pages/Profile/Profile';
import styles from './app.module.css';
import { ThemeProvider } from 'styled-components';
import AuthRoute from './AuthRoute';

function App() {
  return (
    <ThemeProvider
      theme={{
        palette: { blue: '#2d93f0', red: '#ff3b30', grey: '#a9a9a9' },
      }}
    >
      <div>
        <Router>
          <Switch>
            <Route exact path="/" component={Login} />
            <Route
              exact
              path="*"
              component={() => (
                <div className={styles.app}>
                  <div className={styles.sidebar}>
                    <Sidebar />
                  </div>
                  <div className={styles.body}>
                    <Header />
                    <AuthRoute
                      path="/attendance"
                      component={AttendanceContainer}
                    />
                    <AuthRoute path="/payroll" component={Payroll} />
                    <AuthRoute path="/work" component={Work} />
                    <AuthRoute path="/evaluation" component={Evaluation} />
                    <AuthRoute
                      path="/personnelInformation"
                      component={PersonnelInformationContainer}
                    />
                    <AuthRoute
                      path="/personnelStatus"
                      component={PersonnelStatus}
                    />
                    <AuthRoute path="/profile" component={Profile} />
                  </div>
                </div>
              )}
            />
          </Switch>
        </Router>
      </div>
    </ThemeProvider>
  );
}

export default App;
