import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Sidebar from './components/Sidebar/Sidebar';
import Header from './components/Header/Header';
import Login from './pages/Login/Login';
import Attendance from './pages/Attendance/Attendance';
import Payroll from './pages/Payroll/Payroll';
import Task from './pages/Task/Task';
import Performance from './pages/Performance/Performance';
import PersonnelInformation from './pages/PersonnelInformation/PersonnelInformation';
import PersonnelStatus from './pages/PersonnelStatus/PersonnelStatus';
import Profile from './pages/Profile/Profile';

function App() {
  return (
    <div className="App">
      <Sidebar />
      <div>
        <Header />
        <Router>
          <Switch>
            <Route exact path="/" component={Login} />
            <Route path="/attendance" component={Attendance} />
            <Route path="/payroll" component={Payroll} />
            <Route path="/task" component={Task} />
            <Route path="/performance" component={Performance} />
            <Route
              path="/personnelInformation"
              component={PersonnelInformation}
            />
            <Route path="/personnelStatus" component={PersonnelStatus} />
            <Route path="/profile" component={Profile} />
          </Switch>
        </Router>
      </div>
    </div>
  );
}

export default App;
