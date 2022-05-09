import React, {useContext, useState} from "react";
import ShopContextProvider from "../contexts/ShopContext";
import {Link} from "react-router-dom";

export const Payment = () => {
    const { total } = useContext(ShopContextProvider);
    const [ name, setName ] = useState('');
    const [ surname, setSurname ] = useState('');
    const [ email, setEmail ] = useState('');
    const [ road, setRoad ] = useState('');
    const [ nr, setNr ] = useState('');
    const [ code, setCode ] = useState('');
    const [ city, setCity ] = useState('');
    const [ phone, setPhone ] = useState('');

    const handleSubmit = async() => {
        const res = await fetch("http://localhost:8000/payment/", {
            method: "POST",
            body: JSON.stringify({
                name: {name},
                surname: {surname},
                email: {email},
                road: {road},
                nr: {nr},
                code: {code},
                city: {city},
                phone: {phone}
            })
        });

        if(res.status === 200) {
            alert("Zamówienie przyjęte");
        } else {
            alert("BŁĄD!");
        }
    }

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
            Do zapłaty: {total}<br/><br/>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={name}
                    placeholder="Imię"
                    onChange={(v) => setName(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={surname}
                    placeholder="Nazwisko"
                    onChange={(v) => setSurname(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={email}
                    placeholder="Email"
                    onChange={(v) => setEmail(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={road}
                    placeholder="Ulica"
                    onChange={(v) => setRoad(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={nr}
                    placeholder="Numer budynku"
                    onChange={(v) => setNr(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={code}
                    placeholder="Kod pocztowy"
                    onChange={(v) => setCode(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={city}
                    placeholder="Miasto"
                    onChange={(v) => setCity(v.target.value)}
                    required
                /><br/>
                <input
                    type="text"
                    value={phone}
                    placeholder="Telefon"
                    onChange={(v) => setPhone(v.target.value)}
                    required
                /><br/><br/>
                <input type="submit" value="Kontynuuj"/>
            </form>
        </div>
    );
}
