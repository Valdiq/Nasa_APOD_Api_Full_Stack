import './App.css';
import Navbar from "./navbar/Navbar";
import Apod from "./ApodApi/Apod";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

function App() {
    return (
        <div className="App bg-secondary">
            <Router>
                <Navbar/>
                <Routes>
                    <Route exact path="/apod" element={<Apod/>}/>
                </Routes>
            </Router>
        </div>
    );
}

export default App;
