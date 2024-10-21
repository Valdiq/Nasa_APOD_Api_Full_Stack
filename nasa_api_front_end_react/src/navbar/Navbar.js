import React from "react";

function Navbar() {
    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
            <div className="container-fluid">
                <a className="navbar-brand fs-3 p-3 badge" href="/">NASA API</a>
                <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div className="navbar-nav">
                        <a className="nav-link" href="/apod">Astronomy Picture of the Day (APOD)</a>
                        <a className="nav-link" href="/neows">Near Earth Object (NEO)</a>
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;