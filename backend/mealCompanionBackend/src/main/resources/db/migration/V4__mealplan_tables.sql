CREATE TABLE IF NOT EXISTS meal_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id TEXT NOT NULL,
    start_date DATE NOT NULL,
    days INT NOT NULL,
    servings INT NOT NULL,
    created_at TIMESTAMP Default now()
);

CREATE TABLE IF NOT EXISTS meal_plan_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    meal_plan_id UUID REFERENCES meal_plans(id) ON DELETE CASCADE,
    day_index INT NOT NULL,
    recipe_id UUID NOT NULL,
    servings INT NOT NULL,
    created_at TIMESTAMP Default now()
);

CREATE INDEX IF NOT EXISTS idx_mealplans_client ON meal_plans(client_id);
CREATE INDEX IF NOT EXISTS idx_mealplanitems_mealplan ON meal_plan_items(meal_plan_id);