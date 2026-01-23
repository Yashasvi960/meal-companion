CREATE TABLE IF NOT EXISTS canonical_ingredients (

    name TEXT PRIMARY KEY,
    display_name TEXT NOT NULL,
    base_unit TEXT NOT NULL,
    category TEXT NOT NULL,
    nutrients JSONB,
    spice_tags TEXT[],
    created_at TIMESTAMP Default now()
);

CREATE TABLE IF NOT EXISTS recipes (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    servings INT NOT NULL DEFAULT 1,
    steps JSONB NOT NULL,
    tags TEXT[],
    nutrients_summary JSONB,
    created_at TIMESTAMP Default now()
);

CREATE TABLE IF NOT EXISTS recipe_ingredients (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipe_id UUID REFERENCES recipes(id) ON DELETE CASCADE,
    raw_text TEXT NOT NULL,
    canonical_name TEXT REFERENCES canonical_ingredients(name),
    qty_base NUMERIC,
    base_unit TEXT,
    notes TEXT
);

CREATE TABLE IF NOT EXISTS pantry_items (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id TEXT NOT NULL,
    canonical_name TEXT REFERENCES canonical_ingredients(name),
    qty_base NUMERIC NOT NULL,
    base_unit TEXT NOT NULL,
    updated_at TIMESTAMP Default now()

);