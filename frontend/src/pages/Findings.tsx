import React from 'react';

const findings = [
  { id: 'f1', app: 'DailyETL_v3', type: 'DATA_SKEW', severity: 'CRITICAL', score: '14.8x', date: '2026-06-23 10:45 AM' },
  { id: 'f2', app: 'RecommendationTrainer', type: 'MEMORY_SPILL', severity: 'CRITICAL', score: '8.2 GB', date: '2026-06-23 09:12 AM' },
  { id: 'f3', app: 'HourlyAgg', type: 'HEAVY_SHUFFLE', severity: 'HIGH', score: '42% Runtime', date: '2026-06-23 08:30 AM' },
  { id: 'f4', app: 'UserAnalytics', type: 'MISSING_BROADCAST', severity: 'HIGH', score: '34MB Table', date: '2026-06-22 11:20 PM' },
];

export default function Findings() {
  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Performance Findings</h1>
        <p className="page-subtitle">Automatically detected bottlenecks across all applications</p>
      </div>

      <div className="grid grid-cols-1">
        {findings.map(f => (
          <div key={f.id} className="card" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
              <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', marginBottom: '0.5rem' }}>
                <h3 style={{ fontSize: '1.25rem' }}>{f.type.replace('_', ' ')}</h3>
                <span className={`badge badge-${f.severity.toLowerCase()}`}>{f.severity}</span>
              </div>
              <p style={{ color: 'var(--text-muted)' }}>Application: <strong style={{ color: 'var(--text-main)' }}>{f.app}</strong></p>
            </div>
            <div style={{ textAlign: 'right' }}>
              <div style={{ fontSize: '1.5rem', fontWeight: 700, color: 'var(--primary)', marginBottom: '0.25rem' }}>{f.score}</div>
              <div style={{ color: 'var(--text-muted)', fontSize: '0.875rem' }}>{f.date}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
