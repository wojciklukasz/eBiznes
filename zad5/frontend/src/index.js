import React from "react";
import ReactDOM from "react-dom/client";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import App from "./App";
import {ShopContextProvider} from "./contexts/ShopContext";
import {Products} from "./components/Products";
import {Basket} from "./components/Basket"
import {Payment} from "./components/Payment";

const root = ReactDOM.createRoot(
    document.getElementById("root")
);
root.render(
    <ShopContextProvider>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App />} />
                <Route path="products" element={<Products />} />
                <Route path="basket" element={<Basket />} />
                <Route path="payment" element={<Payment />} />
            </Routes>
        </BrowserRouter>
    </ShopContextProvider>
);
