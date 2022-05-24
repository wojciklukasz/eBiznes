import {useContext, useEffect} from "react";
import loginContext from "../context/LoginContext";
import {Navigate, useParams} from "react-router-dom";

const GithubAccept = () => {
    const {setToken, setEmail, setIsAuthenticated} = useContext(loginContext);
    const {token, email} = useParams();

    useEffect(() => {
        setToken(token);
        setEmail(email);
        setIsAuthenticated(true);
    })

    return (
        <>
            <h1>Zalogowano pomyślnie</h1>
            <Navigate to='/'/>
        </>
    )
};

export default GithubAccept;