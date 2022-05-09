export const fetchProducts = async() => {
    return await fetch('http://localhost:8000/product/')
        .then(response => response.json());
}

export default fetchProducts()
