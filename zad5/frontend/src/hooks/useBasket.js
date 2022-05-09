import {useState} from "react";

const useBasket = () => {
    const [basket, setBasket] = useState([]);
    const [total, setTotal] = useState(0);

    const addProduct = (product) => {
        const filtered = basket.filter(item => item.ID === product.ID);

        if(filtered.length > 0) {
            alert("Limit 1 na osobę");
        } else {
            setBasket([
                ...basket,
                product
            ]);

            setTotal(total + product.Price);
        }
    }

    const removeProduct = (id) => {
        const filteredProduct = basket.filter(product => product.ID === id);
        const filteredBasket = basket.filter(product => product.ID !== id);
        setBasket([...filteredBasket]);

        setTotal(total - filteredProduct[0].Price);
    }

    return {
        basket,
        setBasket,
        addProduct,
        removeProduct,
        total,
        setTotal
    };
}

export default useBasket;
