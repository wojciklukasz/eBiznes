import React, {useContext} from "react";
import {Link} from "react-router-dom";
import ShopContextProvider from "../contexts/ShopContext";

export const Basket = () => {
    const { basket, removeProduct, total } = useContext(ShopContextProvider)

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
            <div className="products">
                <pre>   Produkt | Cena</pre>
                <ul>
                    { basket.map((product) => (
                        <li key={product.ID}>
                            <pre>
                                {product.Name} | {product.Price} |{" "}
                                <button onClick={() => removeProduct(product.ID)}>Usuń z koszyka</button>
                            </pre>
                        </li>
                    ))}
                </ul>
                <pre>   Do zapłaty: {total}</pre>
                <Link to="/payment/">Do kasy</Link>
            </div>
        </div>
    );
}
