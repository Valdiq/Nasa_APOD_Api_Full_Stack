import React, {useState} from "react";
import axios from "axios";

function Apod() {
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [date, setDate] = useState(null);
    const [count, setCount] = useState(null);
    const [apodData, setApodData] = useState(null);
    const [error, setError] = useState(null);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const params = {};
        if (date) params.date = date;
        if (startDate) params.start_date = startDate;
        if (endDate) params.end_date = endDate;
        if (count) params.count = count;

        try {
            const response = await axios.get("http://localhost:8080/apod", {params});
            setApodData(response.data);
            setError(null);
        } catch (err) {
            setError("Failed to fetch APOD data");
            setApodData(null);
        }
    };

    return (
        <div
            className="container-fluid justify-content-center align-items-center text-light">
            <form className="row g-3 col-md-6 bg-dark p-4 rounded w-auto m-2" onSubmit={handleSubmit}>
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
                <div className="col-md-6 offset-md-3 text-center">
                    <label htmlFor="inputDate" className="form-label">Date</label>
                    <input
                        type="date"
                        className="form-control"
                        id="inputDate"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />
                </div>
                <div className="col-12">
                    <label htmlFor="count" className="form-label">Count</label>
                    <input
                        type="number"
                        className="form-control"
                        id="count"
                        value={count}
                        min="1"
                        max="15"
                        placeholder="Range: 1-15"
                        onChange={(e) => setCount(e.target.value)}
                    />
                </div>
                <div className="col-12">
                    <button type="submit" className="btn btn-success w-auto">Get APOD</button>
                </div>
            </form>

            {error && <div className="alert alert-danger">{error}</div>}

            {apodData && (
                <div className="col-md-6 w-auto">
                    {apodData.map((apod, index) => (
                        <div key={index} className="col-12 mb-4 m-2 w-auto">
                            <div className="card bg-dark" style={{
                                backgroundColor: "#343a40",
                                color: "#fff",
                                display: "flex",
                                flexDirection: "row"
                            }}>
                                <div className="col-4">
                                    {apod.hdurl && (
                                        <a href={apod.hdurl} target="_blank" rel="noopener noreferrer">
                                            <img src={apod.hdurl} alt={apod.title} className="img-fluid"
                                                 style={{width: "100%", height: "100%", objectFit: "cover"}}/>
                                        </a>
                                    )}
                                </div>

                                <div className="d-grid col-8 p-3">
                                    <h5 className="card-title text-warning"
                                        style={{fontWeight: "bold"}}>{apod.title}</h5>
                                    <p className="card-text">{apod.explanation}</p>
                                    <p className="text-center modal-footer"
                                       style={{fontSize: "14px", color: "#adb5bd"}}>{apod.date}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default Apod;
