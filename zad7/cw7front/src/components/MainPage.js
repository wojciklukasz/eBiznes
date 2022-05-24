import {useContext} from "react";
import loginContextProvider from "../context/LoginContext";

const MainPage = () => {
    const {token, email, isAuthenticated} = useContext(loginContextProvider);

    if(isAuthenticated === true) {
        return (
            <>
                <h1>Zalogowano pomyślnie</h1>
                Witaj {email}, twój token na serwerze to {token}
            </>
        )
    } else {
        return (
            <>
                <h1>Logowanie za pomocą Oauth2</h1>
                Wybierz odpowiednią opcję z menu
                <hr/>
            </>
        );
    }
};

export default MainPage;