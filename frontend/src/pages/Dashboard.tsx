import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const data = [
  { time: '10:00', duration: 120 },
  { time: '10:05', duration: 180 },
  { time: '10:10', duration: 150 },
  { time: '10:15', duration: 300 },
  { time: '10:20', duration: 110 },
  { time: '10:25', duration: 90 },
];

export default function Dashboard() {
  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Platform Overview</h1>
        <p className="page-subtitle">Real-time Spark observability and performance metrics</p>
      </div>

      <div className="grid grid-cols-4" style={{ marginBottom: '2rem' }}>
        <div className="card">
          <div className="kpi-title">Applications Analyzed</div>
          <div className="kpi-value text-primary">1,248</div>
        </div>
        <div className="card">
          <div className="kpi-title">Critical Findings</div>
          <div className="kpi-value text-danger">42</div>
        </div>
        <div className="card">
          <div className="kpi-title">Potential Savings</div>
          <div className="kpi-value text-success">$14,500</div>
        </div>
        <div className="card">
          <div className="kpi-title">Avg Runtime Reduction</div>
          <div className="kpi-value text-warning">28%</div>
        </div>
      </div>

      <div className="grid grid-cols-1">
        <div className="card">
          <h2 style={{ marginBottom: '1rem' }}>Average Job Runtime (Last 6 hours)</h2>
          <div className="chart-container">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={data}>
                <CartesianGrid strokeDasharray="3 3" stroke="#334155" />
                <XAxis dataKey="time" stroke="#94a3b8" />
                <YAxis stroke="#94a3b8" />
                <Tooltip 
                  contentStyle={{ backgroundColor: '#1e293b', borderColor: '#334155', color: '#f8fafc' }}
                  itemStyle={{ color: '#3b82f6' }}
                />
                <Line type="monotone" dataKey="duration" stroke="#3b82f6" strokeWidth={3} dot={{ r: 4, fill: '#3b82f6' }} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </div>
  );
}
