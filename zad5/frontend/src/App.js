import React from "react";
import { Link } from "react-router-dom";

export default function App() {
    return (
        <div>
            <nav
                style={{
                    borderBottom: "solid 1px",
                    paddingBottom: "1rem",
                }}
            >
                <Link to="/">Strona główna</Link> |{" "}
                <Link to="/products/">Produkty</Link> |{" "}
                <Link to="/basket/">Koszyk</Link>
            </nav>
            <h1>
                <center>
                    Sklep na eBiznes
                </center>
            </h1>
        </div>
    );
}
