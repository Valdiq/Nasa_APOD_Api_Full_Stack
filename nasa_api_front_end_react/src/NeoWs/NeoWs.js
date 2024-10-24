import React, {useState} from "react";
import axios from "axios";

function NeoWs() {

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [asteriodId, setAsteroidId] = useState(null);
    const [neowsData, setNeowsData] = useState(null);
    const [asteroidData, setAsteroidData] = useState(null);
    const [NeoError, setNeoError] = useState(null);
    const [AsteroidError, setAsteroidError] = useState(null);


    const handleFeedSubmit = async (event) => {
        event.preventDefault();

        const params = {};
        if (startDate) params.start_date = startDate;
        if (endDate) params.en_date = endDate;

        try {
            const response = await axios.get("http://localhost:8080/neows/feed", {params});
            setNeowsData(response.data);
            setNeoError(null);
        } catch (err) {
            setNeoError("Failed to fetch NEO data");
            setNeowsData(null);
        }

    };

    const handleAsteroidSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.get(`http://localhost:8080/neows/neo/${asteriodId}`);
            setAsteroidData(response.data);
            setAsteroidError(null);
        } catch (err) {
            setAsteroidError("Failed to fetch asteroid data");
            setAsteroidData(null);
        }

    };

    return (
        <div
            className="container-fluid justify-content-center align-items-center text-light">
            <form className="row g-3 col-md-6 bg-dark p-4 rounded w-auto m-2" onSubmit={handleFeedSubmit}>
                <div className="col-md-6">
                    <label htmlFor="inputStartDate" className="form-label">Start Date</label>
                    <input
                        type="date"
                        className="form-control"
                        id="inputStartDate"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                    />
                </div>
                <div className="col-md-6">
                    <label htmlFor="inputEndDate" className="form-label">End Date</label>
                    <input
                        type="date"
                        className="form-control"
                        id="inputEndDate"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                    />
                </div>
                <div className="col-12">
                    <button type="submit" className="btn btn-success w-auto">Get NEO</button>
                </div>
            </form>

            {NeoError && <div className="alert alert-danger">{NeoError}</div>}

            <div
                className="container-fluid justify-content-center align-items-center text-light">
                <form className="row g-3 col-md-6 bg-dark p-4 rounded w-auto m-2" onSubmit={handleAsteroidSubmit}>
                    <div className="col-12">
                        <label htmlFor="asteroidId" className="form-label">Asteroid ID</label>
                        <input
                            type="number"
                            className="form-control"
                            id="asteroidId"
                            value={asteriodId}
                            min="1"
                            onChange={(e) => setAsteroidId(e.target.value)}
                        />
                    </div>
                    <div className="col-12">
                        <button type="submit" className="btn btn-success w-auto">Get Asteroid</button>
                    </div>
                </form>
            </div>

            {AsteroidError && <div className="alert alert-danger">{AsteroidError}</div>}

            {neowsData && neowsData.near_earth_objects && (
                <div className="container w-auto">
                    {Object.entries(neowsData.near_earth_objects).map(([date, neos], index) => (
                        <div key={index} className="row row-cols-3 gap-2 justify-content-center">
                            {neos.map((neo, idx) => (
                                <div key={idx} className="col card bg-dark w-auto flex-md-fill align-content-center"
                                     style={{padding: "20px"}}>
                                    <h5 className="card-title text-warning">{neo.name}</h5>
                                    <p className="text-light"><strong>Estimated Diameter
                                        (km):</strong> {neo.estimated_diameter.kilometers.estimated_diameter_min.toFixed(3)} - {neo.estimated_diameter.kilometers.estimated_diameter_max.toFixed(3)}
                                    </p>
                                    <p className="text-light"><strong>Potentially
                                        Hazardous:</strong> {neo.is_potentially_hazardous_asteroid ? "Yes" : "No"}
                                    </p>
                                    <p className="text-light"><strong>Miss Distance
                                        (km):</strong> {neo.close_approach_data[0].miss_distance.kilometers}</p>
                                    <p className="text-light"><strong>Orbiting
                                        Body:</strong> {neo.close_approach_data[0].orbiting_body}</p>
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
            )}

            {asteroidData && (
                <div className="container w-auto">
                    <div className="card bg-dark mb-3" style={{padding: "20px"}}>
                        <h5 className="card-title text-warning">{asteroidData.name}</h5>
                        <p className="text-light"><strong>Asteroid ID:</strong> {asteroidData.id}</p>
                        <p className="text-light"><strong>Potentially
                            Hazardous:</strong> {asteroidData.is_potentially_hazardous_asteroid ? "Yes" : "No"}</p>
                        <p className="text-light">
                            <strong>Estimated Diameter
                                (km):</strong> {asteroidData.estimated_diameter.kilometers.estimated_diameter_min.toFixed(3)} - {asteroidData.estimated_diameter.kilometers.estimated_diameter_max.toFixed(3)}
                        </p>
                        <h6 className="mt-3 text-warning">Close Approach Data:</h6>
                        <ul className="list-group list-group-flush gap-2">
                            {asteroidData.close_approach_data.map((approach, index) => (
                                <li key={index} className="list-group-item bg-dark text-light">
                                    <p><strong>Miss Distance
                                        (km):</strong> {parseFloat(approach.miss_distance.kilometers).toLocaleString()}
                                    </p>
                                    <p><strong>Orbiting Body:</strong> {approach.orbiting_body}</p>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            )}
        </div>
    );
}

export default NeoWs;