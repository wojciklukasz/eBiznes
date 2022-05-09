import React, {createContext} from "react";
import useBasket from "../hooks/useBasket";

const defaultValue = {
    basket: [],
    addProduct: () => {},
    removeProduct: () => {},
    total: Number
};

export const BasketContext = createContext(defaultValue);

export const BasketContextProvider = ({children}) => {
    const {basket, addProduct, removeProduct, total} = useBasket();

    return (
        <BasketContext.Provider value={{
            basket,
            addProduct,
            removeProduct,
            total}
        }>{children}</BasketContext.Provider>
    )
};

export default BasketContext;
