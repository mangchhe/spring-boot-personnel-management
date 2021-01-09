import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Sidebar from './components/Sidebar/Sidebar';
import Header from './components/Header/Header';
import Login from './pages/Login/Login';
import AttendanceContainer from './pages/Attendance/AttendanceContainer';
import Payroll from './pages/Payroll/Payroll';
import Work from './pages/Work/Work';
import Performance from './pages/Performance/Performance';
import PersonnelInformation from './pages/PersonnelInformation/PersonnelInformation';
import PersonnelStatus from './pages/PersonnelStatus/PersonnelStatus';
import Profile from './pages/Profile/Profile';
import styles from './app.module.css';
import { ThemeProvider } from 'styled-components';

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
                    <Route path="/attendance" component={AttendanceContainer} />
                    <Route path="/payroll" component={Payroll} />
                    <Route path="/work" component={Work} />
                    <Route path="/performance" component={Performance} />
                    <Route
                      path="/personnelInformation"
                      component={PersonnelInformation}
                    />
                    <Route
                      path="/personnelStatus"
                      component={PersonnelStatus}
                    />
                    <Route path="/profile" component={Profile} />
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
