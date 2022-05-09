import React, {useContext} from "react";
import ShopContextProvider from "../contexts/ShopContext";
import {Link} from "react-router-dom";

export const Products = () => {
    const { products, addProduct } = useContext(ShopContextProvider);

    // console.log(products[0])
    // console.log(products.map((product) => product.Name))

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
                    { products.map((product) => (
                        <li key={product.ID}>
                            <pre>
                                {product.Name} | {product.Price} |{" "}
                                <button onClick={() => addProduct(product)}>Dodaj do koszyka</button>
                            </pre>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}
