import React from 'react';
import { useParams } from 'react-router-dom';

export default function ApplicationDetail() {
  const { id } = useParams();

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Application Detail: {id}</h1>
        <p className="page-subtitle">Deep dive into jobs, stages, and tasks</p>
      </div>

      <div className="grid grid-cols-2">
        <div className="card">
          <h2 style={{ marginBottom: '1rem' }}>Jobs</h2>
          <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
            <li style={{ padding: '0.75rem', backgroundColor: 'var(--bg-dark)', borderRadius: '0.5rem' }}>Job 0: 4 Stages (Completed)</li>
            <li style={{ padding: '0.75rem', backgroundColor: 'var(--bg-dark)', borderRadius: '0.5rem' }}>Job 1: 2 Stages (Completed)</li>
          </ul>
        </div>
        <div className="card">
          <h2 style={{ marginBottom: '1rem' }}>Stages Overview</h2>
          <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
            <li style={{ padding: '0.75rem', backgroundColor: 'var(--bg-dark)', borderRadius: '0.5rem', borderLeft: '4px solid var(--danger)' }}>
              Stage 2: 200 Tasks (Data Skew Detected)
            </li>
            <li style={{ padding: '0.75rem', backgroundColor: 'var(--bg-dark)', borderRadius: '0.5rem', borderLeft: '4px solid var(--success)' }}>
              Stage 3: 50 Tasks (Normal)
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
