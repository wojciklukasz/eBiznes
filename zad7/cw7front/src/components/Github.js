import {useEffect, useState} from "react";

const Github = () => {
    const [link, setLink] = useState('none');
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8000/github")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setLink(result);
                },
                (error) => {
                    console.log(error);
                    setIsLoaded(true);
                }
            )
    }, []);

    if(!isLoaded)
        return (
            <>
                <h1>Github</h1>
                Loading...
            </>
        )
    return (
        <>
            <h1>Github</h1>
            Kliknij w poniższy link żeby przejść do strony logowania
            <br/>
            <a href={link.url}>Login</a>
        </>
    );
};

export default Github;