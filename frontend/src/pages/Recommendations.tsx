import React from 'react';

const recommendations = [
  { id: 'r1', app: 'DailyETL_v3', rule: 'DataSkewRule', text: 'Apply salting strategy to distribute keys evenly across partitions.', savings: '$230.00/mo', confidence: '92%' },
  { id: 'r2', app: 'UserAnalytics', rule: 'MissingBroadcastJoinRule', text: 'Use Broadcast Join. Dimension Table = 34MB (Threshold = 100MB).', savings: '$145.50/mo', confidence: '98%' },
  { id: 'r3', app: 'RecommendationTrainer', rule: 'MemoryConfigRule', text: 'Increase spark.executor.memory to 16G or reduce spark.sql.shuffle.partitions to prevent 8.2GB spill.', savings: '$85.00/mo', confidence: '87%' },
];

export default function Recommendations() {
  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Optimization Recommendations</h1>
        <p className="page-subtitle">Actionable steps to resolve findings and reduce infrastructure waste</p>
      </div>

      <div className="grid grid-cols-1">
        {recommendations.map(r => (
          <div key={r.id} className="card">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
              <div>
                <h3 style={{ fontSize: '1.25rem', color: 'var(--accent)', marginBottom: '0.25rem' }}>{r.rule}</h3>
                <p style={{ color: 'var(--text-muted)' }}>Application: <strong style={{ color: 'var(--text-main)' }}>{r.app}</strong></p>
              </div>
              <div style={{ textAlign: 'right' }}>
                <div style={{ fontSize: '1.5rem', fontWeight: 700, color: 'var(--success)' }}>{r.savings}</div>
                <div style={{ color: 'var(--text-muted)', fontSize: '0.875rem' }}>Est. Savings</div>
              </div>
            </div>
            <div style={{ padding: '1rem', backgroundColor: 'rgba(0,0,0,0.2)', borderRadius: '0.5rem', borderLeft: '4px solid var(--primary)' }}>
              <p>{r.text}</p>
            </div>
            <div style={{ marginTop: '1rem', fontSize: '0.875rem', color: 'var(--text-muted)' }}>
              Confidence: <strong style={{ color: 'var(--text-main)' }}>{r.confidence}</strong>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
