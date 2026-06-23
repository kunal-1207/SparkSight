import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import { Activity, AlertTriangle, Lightbulb, PieChart } from 'lucide-react';
import Dashboard from './pages/Dashboard';
import ApplicationDetail from './pages/ApplicationDetail';
import Findings from './pages/Findings';
import Recommendations from './pages/Recommendations';
import './index.css';

function App() {
  return (
    <Router>
      <div className="app-container">
        <aside className="sidebar">
          <NavLink to="/" className="sidebar-logo">
            <Activity size={28} />
            SparkSight
          </NavLink>
          <nav>
            <NavLink to="/" className={({isActive}) => isActive ? "nav-link active" : "nav-link"}>
              <PieChart size={20} /> Dashboard
            </NavLink>
            <NavLink to="/applications/app-2026" className={({isActive}) => isActive ? "nav-link active" : "nav-link"}>
              <Activity size={20} /> App Details
            </NavLink>
            <NavLink to="/findings" className={({isActive}) => isActive ? "nav-link active" : "nav-link"}>
              <AlertTriangle size={20} /> Findings
            </NavLink>
            <NavLink to="/recommendations" className={({isActive}) => isActive ? "nav-link active" : "nav-link"}>
              <Lightbulb size={20} /> Recommendations
            </NavLink>
          </nav>
        </aside>
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/applications/:id" element={<ApplicationDetail />} />
            <Route path="/findings" element={<Findings />} />
            <Route path="/recommendations" element={<Recommendations />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
