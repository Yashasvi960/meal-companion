import { useEffect, useState } from 'react';
import {fetchMealPlan, generateMealPlan} from "./generateMealPlan.tsx";
import PlannedMealCard from "./PlannedMealCard.tsx";

function ensureClientId() {
    let cid = localStorage.getItem('clientId');
    if (!cid) {
        cid = 'client-' + Math.random().toString(36).slice(2, 10);
        localStorage.setItem('clientId', cid);
    }
    return cid;
}

export default function MealPlanPage() {
    const [clientId] = useState(ensureClientId());
    const [startDate, setStartDate] = useState(new Date().toISOString().slice(0, 10)); // yyyy-mm-dd
    const [days, setDays] = useState(7);
    const [servings, setServings] = useState(1);
    const [loading, setLoading] = useState(false);
    const [plan, setPlan] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        // optionally load last plan id from localStorage
        const last = localStorage.getItem('lastMealPlanId');
        if (last) {
            setLoading(true);
            fetchMealPlan(last)
                .then(p => setPlan(p))
                .catch(() => localStorage.removeItem('lastMealPlanId'))
                .finally(() => setLoading(false));
        }
    }, []);

    async function onGenerate(e) {
        e.preventDefault();
        setError(null);
        setLoading(true);
        try {
            const resp = await generateMealPlan({
                clientId,
                startDate,
                days: Number(days),
                servings: Number(servings),
                constraints: {} // you can extend to pass avoid list from UI
            });
            setPlan(resp);
            localStorage.setItem('lastMealPlanId', resp.mealPlanId);
        } catch (err) {
            console.error(err);
            setError(err.message || 'Failed to generate');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div style={pageStyle}>
            <h2 style={{ marginBottom: 6 }}>Meal Companion — Generate Meal Plan</h2>
            <p style={{ color: '#6b7280', marginTop: 0 }}>Client ID: <code>{clientId}</code></p>

            <form onSubmit={onGenerate} style={formStyle}>
                <label style={labelStyle}>
                    Start date
                    <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} style={inputStyle} />
                </label>

                <label style={labelStyle}>
                    Days
                    <input type="number" min="1" max="14" value={days} onChange={e => setDays(e.target.value)} style={inputStyle} />
                </label>

                <label style={labelStyle}>
                    Servings
                    <input type="number" min="1" max="10" value={servings} onChange={e => setServings(e.target.value)} style={inputStyle} />
                </label>

                <div style={{ marginTop: 8 }}>
                    <button type="submit" disabled={loading} style={buttonStyle}>
                        {loading ? 'Working…' : 'Generate 7-day plan'}
                    </button>
                </div>
            </form>

            {error && <div style={errorStyle}>{error}</div>}

            {plan && (
                <div>
                    {console.log("Rendering plan meals", plan.mealPlanId)}

                    {plan.meals && plan.meals.length ? (
                        plan.meals.map((m, idx) => (
                            <PlannedMealCard
                                key={idx}
                                meal={m}
                                mealPlanId={plan.mealPlanId}
                            />
                        ))
                    ) : (
                        <div style={{ color: '#6b7280' }}>No meals in plan.</div>
                    )}
                </div>
            )}
        </div>
    );
}

const pageStyle = {
    padding: 20,
    fontFamily: 'Inter, Roboto, system-ui, -apple-system, "Segoe UI", Arial'
};

const formStyle = {
    display: 'flex',
    gap: 12,
    alignItems: 'flex-end',
    flexWrap: 'wrap'
};

const labelStyle = {
    display: 'flex',
    flexDirection: 'column',
    fontSize: 13
};

const inputStyle = {
    marginTop: 6,
    padding: '8px 10px',
    borderRadius: 6,
    border: '1px solid #e5e7eb',
    minWidth: 160
};

const buttonStyle = {
    padding: '10px 14px',
    background: '#2563eb',
    color: 'white',
    border: 'none',
    borderRadius: 8,
    cursor: 'pointer'
};

const errorStyle = {
    marginTop: 12,
    color: 'white',
    background: '#ef4444',
    padding: '8px 10px',
    borderRadius: 6
};