import {BrowserRouter} from "react-router-dom";
import {Link, Routes, Route} from "react-router-dom";
import Home from "./api/Home.tsx";
import MealPlanPage from "./api/MealPlanPage.tsx";

function RecipePage() {
    return <div>Recipe Page - to be implemented</div>;
}

function App() {
    return (
        <BrowserRouter>
            <div style={{ padding: 12 }}>
                <nav style={{ marginBottom: 12 }}>
                    <Link to="/" style={{ marginRight: 12 }}>Home</Link>
                    <Link to="/mealplan">Meal Plan</Link>
                </nav>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/mealplan" element={<MealPlanPage />} />
                    <Route path="/recipe/:recipeId" element={<RecipePage />} />
                </Routes>
            </div>

        </BrowserRouter>

    );
}

export default App;