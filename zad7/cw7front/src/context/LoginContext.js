import React, {useState} from "react";

const defaultValue = {
    token: String,
    setToken: () => {return 'a';},
    email: String,
    setEmail: () => {return 'a';},
    isAuthenticated: false,
    setIsAuthenticated: () => {return false;}
};

export const LoginContext = React.createContext(defaultValue);

export const LoginContextProvider = ({children}) => {
    const [token, setToken] = useState('a');
    const [email, setEmail] = useState('a');
    const [isAuthenticated, setIsAuthenticated] = useState('false');

    const providerValue = {
        token,
        setToken,
        email,
        setEmail,
        isAuthenticated,
        setIsAuthenticated
    };

    return <LoginContext.Provider value={providerValue}>{children}</LoginContext.Provider>
};

export default LoginContext;