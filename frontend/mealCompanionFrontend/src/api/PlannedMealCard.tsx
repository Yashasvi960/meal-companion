// import {fetchMealPlan} from "./generateMealPlan.tsx";

import {fetchMealPlan} from "./generateMealPlan.tsx";

export default function PlannedMealCard({ meal, mealPlanId }) {
    // meal expected: { recipeId, date OR localDate, servings }
    const date = meal.date || meal.localDate || meal.localdate;
    return (
        <div style={cardStyle}>
            <div style={{ fontSize: 14, color: '#333' }}>{date}</div>
            <div style={{ fontWeight: 600, marginTop: 6 }}>Recipe: <code style={{ fontSize: 13 }}>{meal.title}</code></div>
            <div style={{ marginTop: 6, fontSize: 13 }}>Servings: {meal.servings || 1}</div>
            <div style={{ fontSize: 12, color: '#6b7280' }}>
                Plan ID: {mealPlanId}
            </div>
            <div style={{ marginTop: 8 }}>
                <a href={`/recipe/${meal.recipeId}`} style={linkStyle} onClick={async e => {
                    // allow default navigation - if you have router, convert to Link
                    console.log("Fetching meal plan for mealPlanId" );
                    e.preventDefault();
                    try {
                        await fetchMealPlan({mealPlanId}); // Call the function
                    } catch (error) {
                        console.error('Error fetching meal plan:', error);
                    }
                }}>
                    View recipe
                </a>
            </div>
        </div>
    );
}

const cardStyle = {
    border: '1px solid #e5e7eb',
    padding: 12,
    borderRadius: 8,
    background: '#fff',
    boxShadow: '0 1px 2px rgba(0,0,0,0.03)',
    marginBottom: 10,
};

const linkStyle = {
    color: '#2563eb',
    textDecoration: 'none',
    fontSize: 13
};