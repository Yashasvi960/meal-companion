import { useEffect, useState } from 'react';

function App() {
    const [status, setStatus] = useState('loading');
    useEffect(() => {
        fetch('http://localhost:8080/health')
            .then(r => r.json())
            .then(d => setStatus(d.status))
            .catch(()=> setStatus('backend unreachable'));
    }, []);
    return (
        <div className="p-8">
            <h1 className="text-2xl font-bold">Meal Companion — Dev</h1>
            <p>Backend status: {status}</p>
        </div>
    );
}

export default App;