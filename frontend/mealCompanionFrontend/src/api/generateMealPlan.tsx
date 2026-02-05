export async function generateMealPlan({clientId, startDate, days=7, servings=1, constraints}) {
    const body = {
        clientId,
        startDate,
        days,
        servings,
        constraints
    }

    const resp = await fetch('http://localhost:8080/api/mealplan/generate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });

    if(!resp.ok) {
        const text = await resp.text();
        throw new Error(`Error generating meal plan: ${text}`);
    }

    return resp.json();


}

export async function fetchMealPlan(mealPlanId) {
    const resp = await fetch(`http://localhost:8080/api/mealplan/${mealPlanId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if(!resp.ok) {
        const text = await resp.text();
        throw new Error(`Error fetching meal plan: ${text}`);
    }

    const data  = await resp.json;
    console.log("fetched meal data", data);

    return data;
}
