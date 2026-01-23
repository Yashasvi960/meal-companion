INSERT INTO recipes(id, title, servings, steps, tags)
VALUES (
           gen_random_uuid(),
           'Simple Lentil Curry',
           4,
           '[ "Rinse lentils", "Boil lentils 15 minutes", "Temper mustard and cumin in oil, add onions and tomato", "Mix with lentils" ]'::jsonb,
           ARRAY['dinner','quick']
       );
